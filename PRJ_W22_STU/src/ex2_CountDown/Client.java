package ex2_CountDown;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.*;
import javax.swing.JScrollPane;

public class Client extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField startField;
	private JTextField delayField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		setTitle("Countdown Client by E. Sesa");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 571, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		connectButton = new JButton("Connect and Start");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		connectButton.setBounds(10, 121, 152, 23);
		contentPane.add(connectButton);
		
		goButton = new JButton("GO");
		goButton.setEnabled(false);
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				go();
			}
		});
		goButton.setBounds(10, 155, 152, 23);
		contentPane.add(goButton);
		
		exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		exitButton.setBounds(10, 329, 152, 23);
		contentPane.add(exitButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(194, 21, 352, 331);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 27, 46, 14);
		contentPane.add(nameLabel);
		
		JLabel startLabel = new JLabel("Start:");
		startLabel.setBounds(10, 52, 46, 14);
		contentPane.add(startLabel);
		
		JLabel delayLabel = new JLabel("Delay");
		delayLabel.setBounds(10, 77, 46, 14);
		contentPane.add(delayLabel);
		
		nameField = new JTextField();
		nameField.setText("NiceClient");
		nameField.setBounds(55, 24, 107, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		startField = new JTextField();
		startField.setText("15");
		startField.setBounds(55, 52, 107, 20);
		contentPane.add(startField);
		startField.setColumns(10);
		
		delayField = new JTextField();
		delayField.setText("1000");
		delayField.setBounds(55, 77, 107, 20);
		contentPane.add(delayField);
		delayField.setColumns(10);
	}
	
	private Socket connection;
	private PrintWriter outChannel;
	private BufferedReader inChannel;
	private JButton connectButton;
	private JButton goButton;
	private JTextArea textArea;
	private JButton exitButton;
	
	private void connect () {
		// get in touch with the server
		try {
			connection = new Socket("localhost", 5566);
			outChannel =  new PrintWriter(this.connection.getOutputStream(), true);
			inChannel = new BufferedReader( new InputStreamReader(this.connection.getInputStream()));
			
			// once connected, say hello..., start and delay
			outChannel.println("HELLO "+this.nameField.getText());
			outChannel.println("START "+Integer.parseInt(this.startField.getText()));
			outChannel.println("DELAY "+Integer.parseInt(this.delayField.getText()));
			
			// and enable / disabled buttons
			this.connectButton.setEnabled(false);
			this.goButton.setEnabled(true);
			this.exitButton.setEnabled(false);
			
			this.textArea.append("Connected to countdown server. \nPress Go to start de countdown\n\n");
			
		}
		catch (Exception ex) {
			// Exit if something goes wrong
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Something went wrong. Client will close", "UUUPS!!!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} 
	} // end of connect
	
	private void go () {
		this.goButton.setEnabled(false);
		try {
			outChannel.println("GO");
		}
		catch (Exception ex) {
			// Exit if something goes wrong
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Something went wrong. Client will close", "UUUPS!!!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		(new Worker()).start();
		
	} // end of go
	
	private void exit () {
		System.exit(0);
	}
	
	class Worker extends Thread {
		
		public void run () {
			try {
				innerRun();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				EventQueue.invokeLater(()->{
					JOptionPane.showMessageDialog(contentPane, "Something went wrong. Client will close", "UUUPS!!!", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				});
			}
		}
		
		public void innerRun () throws Exception {
			
			boolean end = false;
			do {
				String line = inChannel.readLine();
				end = line.equals("GOODBYE");
				if (!end) EventQueue.invokeLater(()->{textArea.append(line+"\n");
				                                      textArea.setCaretPosition(textArea.getDocument().getLength());});
			}
			while (!end);
			
			EventQueue.invokeLater(()->{textArea.append("\nServer said GOODBYE\n\n");
			                            textArea.setCaretPosition(textArea.getDocument().getLength());});
			
			
			
			
			connection.close();
			inChannel.close();
			outChannel.close();
			
			EventQueue.invokeLater(()->{connectButton.setEnabled(true);
			                            exitButton.setEnabled(true);});	
			                         
		}
	}
}
