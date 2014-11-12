import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ServerFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Server server;

	
	public ServerFrame(Server server) {
		super();
		this.server = server;
	}

	
}
