import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ClientFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Client client;
	public ClientFrame(Client client){
		this.client=client;
	}

	JTextArea taContent=new JTextArea();
	JPanel pa1=new JPanel();
//	JPanel pa3=new JPanel();
	JPanel pa4=new JPanel();
//	JButton jb1=new JButton("CONNECT");
//	JButton jb2=new JButton("DISCONNECT");
	JLabel label=new JLabel("STATUS: disconnected!");
	JScrollPane sp=null;

	public void launchFrame() {
		this.setLocation(300,150);
		this.setSize(400,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		sp=new JScrollPane(taContent);
		pa1.add(label);
//		pa3.add(jb1); pa3.add(jb2); 
		pa4.setLayout(new GridLayout(3,1));//set Panel to GridLayout
		pa4.add(pa1);
//		pa4.add(pa3);

		add(pa4, BorderLayout.NORTH);	
		add(sp, BorderLayout.CENTER);
		
//		jb1.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				t=new Thread(client.new Connect());
//				t.start();
//				label.setText("STATUS: connected!");
//			}
//		});
//
//		jb2.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					client.dis.close();
//					client.s.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				label.setText("STATUS: disconnected!");
//			}
//		});
//		
		setVisible(true);
	}
}
