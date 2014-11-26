import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String IP="192.168.1.10";
	static IClient client;
	public static void main(String[] args) {
		try {
			new Server().start();
			client=(IClient) Naming.lookup("rmi://"+IP+":1099/Client");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		new Thread(new Accept()).start();
		new Thread(new Frame()).start();
	}

	class Frame implements Runnable{
		@Override
		public void run() {
			launchFrame();
		}
	}

	DataOutputStream dos;
	Socket s;
	String str="";

	ServerSocket serverSocket;
	class Accept implements Runnable{

		public void run(){
			try {
				serverSocket=new ServerSocket(5050);
				s=serverSocket.accept();
				dos = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	Thread t;
	JTextField tfTxt=new JTextField();
	JTextArea taContent=new JTextArea();
	JPanel pa1=new JPanel();
	JPanel pa3=new JPanel();
	JPanel pa4=new JPanel();
	JButton jb1=new JButton("CONNECT");
	JButton jb2=new JButton("DISCONNECT");
	JLabel label=new JLabel("SERVER       STATUS: disconnected!");
	JScrollPane sp=null;
	String printString="";

	public void launchFrame() {
		this.setLocation(300,150);
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		sp=new JScrollPane(taContent);
		pa1.add(label);
		pa3.add(jb1); pa3.add(jb2); 
		pa4.setLayout(new GridLayout(3,1));//set Panel to GridLayout
		pa4.add(pa1);
		pa4.add(pa3);
		add(pa4, BorderLayout.NORTH);	
		add(sp, BorderLayout.CENTER);
		add(tfTxt, BorderLayout.SOUTH);

		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.startConnection();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				label.setText("SERVER       STATUS: connected!");
			}
		});

		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dos.close();
					s.close();
					client.closeConnection();
					label.setText("SERVER       STATUS: disconnected!");
				} catch (IOException e1) {
					//					e1.printStackTrace();
				}
			}
		});

		tfTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				str=tfTxt.getText().trim();//.trim delete whitespace
				taContent.setText(taContent.getText());
				printString+="Server: "+str+"\n";
				taContent.setText(printString);
				try {
					dos.writeUTF(str);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				if(str.startsWith("[file]")){
					new Thread(new FileTransfer(serverSocket, str)).start();
				}
				tfTxt.setText("");
			}
		});
		setVisible(true);
	}

	class FileTransfer implements Runnable{
		ServerSocket xferSocket;
		Socket s;

		DataOutputStream dos;
		FileInputStream fis;

		public FileTransfer(ServerSocket xferSocket, String str){
			this.xferSocket=xferSocket;
			String parseStr[]=str.split(" ");
			try {
				fis = new FileInputStream(parseStr[1]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				s=xferSocket.accept();
				dos = new DataOutputStream(s.getOutputStream());
				byte[] buffer= new byte[128];
				int len=0;
				while((len=fis.read(buffer))!=-1){
					dos.write(buffer, 0, len);
				}
				fis.close();
				dos.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
