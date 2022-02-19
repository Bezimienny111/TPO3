package Zad1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class AdminGui {

	private JFrame frame;
	private JTextField textDodajTemat;
	private JTextField textWczytajTemat;
	private JTextField textUsunInt;
	private JTextField textAktualnyTemat;
	private JTextField txtWpiszNumer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGui window = new AdminGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public AdminGui() throws UnknownHostException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void initialize() throws UnknownHostException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 616, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[100px:n:100px,left][1px:n:1px][grow]", "[grow][::100px,bottom]"));
		
		
		Admin ad = new Admin("192.168.1.101", 6424);
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[100px:n:100px,grow]", "[][][][grow]"));
		
		textDodajTemat = new JTextField();
		panel.add(textDodajTemat, "cell 0 0,grow");
		textDodajTemat.setColumns(10);
		
		JButton buttonDodaj = new JButton("Dodaj temat");
		
		panel.add(buttonDodaj, "cell 0 1,grow");
		
		JButton buttonUsun = new JButton("Usun temat");
		
		panel.add(buttonUsun, "cell 0 2,grow");
		
		JTextArea textWyswietlTematy = new JTextArea();
		textWyswietlTematy.setLineWrap(true);
		textWyswietlTematy.setEditable(false);
		panel.add(textWyswietlTematy, "cell 0 3,grow");
		textWyswietlTematy.setText(ad.displayAllTopics());
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, "cell 2 0,grow");
		panel_1.setLayout(new MigLayout("", "[100px:n,grow][][grow]", "[][grow][grow][][]"));
		
		textWczytajTemat = new JTextField();
		panel_1.add(textWczytajTemat, "cell 0 0,grow");
		textWczytajTemat.setColumns(10);
		
		JButton ButtonWczytajTemat = new JButton("Wczytaj temat do edycji");
		panel_1.add(ButtonWczytajTemat, "cell 1 0");
		
		textAktualnyTemat = new JTextField();
		textAktualnyTemat.setEditable(false);
		panel_1.add(textAktualnyTemat, "cell 2 0,grow");
		textAktualnyTemat.setColumns(10);
		
		JTextArea textNewsy = new JTextArea();
		textNewsy.setEditable(false);
		panel_1.add(textNewsy, "cell 0 1 2 4,grow");
		
		JButton buttonDodajNews = new JButton("Dodaj News");
		
		panel_1.add(buttonDodajNews, "cell 2 1,grow");
		
		JTextArea textNews = new JTextArea();
		panel_1.add(textNews, "cell 2 2,grow");
		
		JButton buttonUsunNews = new JButton("Usun news");

		panel_1.add(buttonUsunNews, "flowx,cell 2 3");
		
		textUsunInt = new JTextField();
		panel_1.add(textUsunInt, "cell 2 4,growx");
		textUsunInt.setColumns(10);
		
		txtWpiszNumer = new JTextField();
		txtWpiszNumer.setText("Wpisz numer");
		txtWpiszNumer.setEditable(false);
		panel_1.add(txtWpiszNumer, "cell 2 3,grow");
		txtWpiszNumer.setColumns(10);
		
		JTextArea fieldKomunikat = new JTextArea();
		fieldKomunikat.setTabSize(3);
		fieldKomunikat.setEditable(false);
		frame.getContentPane().add(fieldKomunikat, "cell 0 1 3 1,growx,aligny bottom");
		
		buttonDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textDodajTemat.getText().isEmpty())
					try {
						ad.addTopic(textDodajTemat.getText());
						textWyswietlTematy.setText(ad.displayAllTopics());
					//	ad.connect();
						ad.reloadTopics();
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		
		buttonUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textDodajTemat.getText().isEmpty()) {
					ad.deleteTopic(textDodajTemat.getText());
					textWyswietlTematy.setText(ad.displayAllTopics());
					try {
					//	ad.connect();
						ad.reloadTopics();
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		ButtonWczytajTemat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textWczytajTemat.getText().isEmpty()) {
					
					ad.whatEdit(textWczytajTemat.getText());
					textAktualnyTemat.setText(ad.getEditor());
					textNewsy.setText(ad.MyString());
					//System.out.println(ad.getEditor());
				//	System.out.println(ad.displayAllTopics());
				}
					
			}
		});
		
		buttonUsunNews.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(textUsunInt.getText());
				int i = -1;
				if (!textUsunInt.getText().isEmpty()) {
				
			
					try {
				 i = Integer.parseInt(textUsunInt.getText());
			//	 System.out.println(i);
					 } catch (NumberFormatException ex)
				    {
				    }
				if (i!= -1) {
					System.out.println(i);
						 ad.deleteNews(i);
						 textNewsy.setText(ad.MyString());
				}
				
			}
			}
		});
		
		buttonDodajNews.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!textNews.getText().isEmpty()) {
				ad.addNews(textNews.getText());
				 textNewsy.setText(ad.MyString());
				}
			}
		});
		
		
		
	}

}
