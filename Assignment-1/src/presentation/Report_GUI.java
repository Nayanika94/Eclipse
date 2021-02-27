package presentation;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JRadioButton;
import data.TextIOFile;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;


public class Report_GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Report_GUI frame = new Report_GUI();
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
	public Report_GUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Reporting");
		setBounds(100, 100, 682, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] choices = {"Select","Brampton", "Toronto", "Mississauga","Kitchener","Oakville","Cambridge"};
		JComboBox cmboxCity = new JComboBox(choices);
		cmboxCity.setBackground(UIManager.getColor("Button.background"));
		cmboxCity.setEnabled(false);
		cmboxCity.setBounds(37, 115, 132, 27);
		contentPane.add(cmboxCity);

		JRadioButton rdbtnAll = new JRadioButton("All");
		
		rdbtnAll.setSelected(true);
		buttonGroup.add(rdbtnAll);
		rdbtnAll.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAll.setBounds(37, 50, 103, 21);
		contentPane.add(rdbtnAll);

		JDateChooser datePicker = new JDateChooser();
		datePicker.setDateFormatString("MM/dd/yyyy");
		datePicker.setBounds(37, 184, 134, 27);
		contentPane.add(datePicker);

		JRadioButton rdbtnDate = new JRadioButton("Date");
		datePicker.setEnabled(false);
		
		buttonGroup.add(rdbtnDate);
		rdbtnDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnDate.setBounds(37, 148, 103, 21);
		contentPane.add(rdbtnDate);

		JRadioButton rdbtnCity = new JRadioButton("City");
		cmboxCity.setEnabled(false);
		
		buttonGroup.add(rdbtnCity);
		rdbtnCity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnCity.setBounds(37, 83, 103, 21);
		contentPane.add(rdbtnCity);

		JLabel lblNewLabel = new JLabel("Categories :-");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(37, 20, 150, 24);
		contentPane.add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(368, 28, 276, 256);
		contentPane.add(textArea);
		
		rdbtnDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnDate.isSelected()) {
					datePicker.setEnabled(true);
					cmboxCity.setEnabled(false);
					cmboxCity.setSelectedIndex(0);
					textArea.setText("");	
				}
			}
		});
		
		rdbtnCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCity.isSelected()) {
					cmboxCity.setEnabled(true);
					textArea.setText("");
					datePicker.setEnabled(false);
					datePicker.setDate(null);
				}
			}
		});
		
		rdbtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnAll.isSelected()) {
					cmboxCity.setEnabled(false);
					cmboxCity.setSelectedIndex(0);
					textArea.setText("");
					datePicker.setEnabled(false);
					datePicker.setDate(null);
				}
			}
		});

		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String report="" ;
				
				if (rdbtnAll.isSelected()) {
					
					try {		
						Object[] recList;
						recList = TextIOFile.findAll();
						textArea.setText("");//clear data
						report = getReport(recList);
					}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage(),"Find All", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
				else if(rdbtnCity.isSelected()) {
					if(Validator.isPresent(cmboxCity, "City")) {
					String findCity="";
					findCity = (String) cmboxCity.getSelectedItem();
					textArea.setText("");
					Object[] recList;
					try {
						recList = TextIOFile.findCity(findCity);
						report = getReport(recList);
					}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage(),"Find Records by City", JOptionPane.ERROR_MESSAGE);
					}
				}
				}
				 
				else if(rdbtnDate.isSelected()) {
				    	if(Validator.dateValidate(datePicker, "Date")) {
					Date date_value = datePicker.getDate();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String strDate= dateFormat.format(date_value);  
					textArea.setText("");
					
					try {
						Object[] recList = TextIOFile.findDate(strDate);
						report = getReport(recList);
					}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1.getMessage(),"Find Records by Date", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
				textArea.append(report);
	}
		});
		btnGenerateReport.setBounds(29, 253, 158, 31);
		contentPane.add(btnGenerateReport);
		}
	private String getReport(Object[] recList) {
		StringBuilder report = new StringBuilder();
		if(recList.length==0) {
			report.append("NO DATA");
		}else {
			int totalcases=0;
			int totaldeaths=0;
			int totalrecovered=0;
			report.append("DATA RECORDS").append(System.lineSeparator());
			for(Object r : recList) {
				String[] fields = r.toString().split(",");
				totalcases=totalcases+ Integer.parseInt(fields[2]);
				totaldeaths=totaldeaths+ Integer.parseInt(fields[3]);
				totalrecovered=totalrecovered+ Integer.parseInt(fields[4]);
				report.append(r).append(System.lineSeparator());
			}
			report.append("TOTALS").append(System.lineSeparator())
				.append("Cases: ").append(totalcases).append(';')
				.append("Deaths: ").append(totaldeaths).append(';')
				.append("Recovered: ").append(totalrecovered);
			
		}
		return report.toString();
	}
	}
