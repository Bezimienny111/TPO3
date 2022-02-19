package Zad1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ClientGui {

	private JFrame frame;
	private JTextField textLogin;
	private JTextField fieldTopic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui window = new ClientGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		 Client c = new Client("192.168.1.101", 6424);
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 591, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[200px:n:200px,grow][grow]", "[grow]"));
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[90px:n,grow][5px:n:5px][90px:n:90px]", "[][][grow]"));
		
		JButton buttonLog = new JButton("Zaloguj");
	
		panel_1.add(buttonLog, "cell 0 0,grow");
		
		textLogin = new JTextField();
		panel_1.add(textLogin, "cell 2 0,grow");
		textLogin.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Dostępne Tematy");
		panel_1.add(lblNewLabel, "cell 0 1");
		
		JLabel lblNewLabel_1 = new JLabel("Wybrane Tematy");
		panel_1.add(lblNewLabel_1, "cell 2 1");
		
		JTextArea textAll = new JTextArea();
		textAll.setEditable(false);
		textAll.setWrapStyleWord(true);
		panel_1.add(textAll, "cell 0 2,grow");
		
		JTextArea textChoose = new JTextArea();
		textChoose.setWrapStyleWord(true);
		panel_1.add(textChoose, "cell 2 2,grow");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 1 0,grow");
		panel.setLayout(new MigLayout("", "[100px:n:100px][100px:n:100px][100px:n:100px][]", "[][grow]"));
		
		fieldTopic = new JTextField();
		panel.add(fieldTopic, "cell 0 0,grow");
		fieldTopic.setColumns(10);
		
		JButton butAdd = new JButton("Dodaj");

		panel.add(butAdd, "cell 1 0,grow");
		
		JButton butLoad = new JButton("Usun");

		panel.add(butLoad, "cell 2 0,grow");
		
		JButton buttRefresh = new JButton("Odświerz");

		panel.add(buttRefresh, "cell 3 0");
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		panel.add(textArea, "cell 0 1 4 1,grow");
		

		buttonLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textLogin.getText().isEmpty()){
			try {
				c.reqforAll(c.setLogin(textLogin.getText()));
				textAll.setText(c.allTopics);
			
				c.reqforLoad(c.getMyTopics());
				textChoose.setText(c.choosenTopics);
				c.reqForNews(c.getNews());
			///	System.out.println("hallo");
				//System.out.println(c.outer);
				textArea.setText(c.outer);
				
				
			//	c.makeRequest(null)
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
			}
		});
		
		butLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
					if (!fieldTopic.getText().isEmpty()){
					try {
						c.reqForEdit(c.deleteTopic(fieldTopic.getText()));
						c.reqforLoad(c.getMyTopics());
						textChoose.setText(c.choosenTopics);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textChoose.setText(c.choosenTopics);
					try {
						c.reqForNews(c.getNews());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					///	System.out.println("hallo");
						//System.out.println(c.outer);
						textArea.setText(c.outer);
					}
			}
		});
		
		butAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!fieldTopic.getText().isEmpty()){
				try {
					c.reqForEdit(c.addTopic(fieldTopic.getText()));
					c.reqforLoad(c.getMyTopics());
					textChoose.setText(c.choosenTopics);
					c.reqForNews(c.getNews());
					///	System.out.println("hallo");
						//System.out.println(c.outer);
						textArea.setText(c.outer);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textChoose.setText(c.choosenTopics);
				try {
					c.reqForNews(c.getNews());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				///	System.out.println("hallo");
					//System.out.println(c.outer);
					textArea.setText(c.outer);
				}
		}
	});
		buttRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					c.reqForNews(c.getNews());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				///	System.out.println("hallo");
					//System.out.println(c.outer);
					textArea.setText(c.outer);
			}
		});
	}

}
