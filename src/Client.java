import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Client extends UnicastRemoteObject implements IClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String IP="192.168.1.10";

	ClientFrame cf;
	Thread t;
	Socket s;
	DataInputStream dis;
	protected Client() throws RemoteException {
		super();
	}

	public void startConnection(){
		cf=new ClientFrame(this);
		cf.launchFrame();
		t=new Thread(new Connect());
		t.start();
		cf.label.setText("CLIENT       STATUS: connected!");
	}

	static boolean status=false; 

	class Connect implements Runnable{
		Socket s;
		String printString="";
		@Override
		public void run() {
			try {
				status=true;
				s = new Socket(IP, 5050);
				dis=new DataInputStream(s.getInputStream());
				while(status){
					String s=dis.readUTF();
					printString+="Server: "+s+"\n";
					cf.taContent.setText(printString);
					if(s.startsWith("[file]")){
						new Thread(new FileTransfer(s)).start();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closeConnection(){
		try {
			status=false;
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cf.label.setText("CLIENT       STATUS: disconnected!");
	}

	class FileTransfer implements Runnable{
		Socket s;
		DataInputStream dis;
		FileOutputStream fos;
		public FileTransfer(String str){
			try {
				s=new Socket(IP, 5050);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			String parseStr[]=str.split(" ");
			try {
				fos = new FileOutputStream(parseStr[3]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				dis=new DataInputStream(s.getInputStream());
				byte[] buffer= new byte[128];
				int len=0;
				while((len=dis.read(buffer))!=-1){
					fos.write(buffer, 0, len);
				}
				dis.close();
				fos.close();
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
