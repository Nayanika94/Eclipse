package presentation;

import com.toedter.calendar.JDateChooser;
import data.Data;
import data.TextIOFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record_GUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Record_GUI frame = new Record_GUI();
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
	public Record_GUI() {
		setTitle("Daily Record");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 655, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDateOfReporting = new JLabel("Date of Reporting*");
		lblDateOfReporting.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDateOfReporting.setBounds(50, 52, 169, 18);
		contentPane.add(lblDateOfReporting);
		
		JLabel lblCityName = new JLabel("Name of city*");
		lblCityName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCityName.setBounds(50, 100, 136, 18);
		contentPane.add(lblCityName);
		
		JLabel lblCases = new JLabel("Number of cases");
		lblCases.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCases.setBounds(50, 146, 136, 18);
		contentPane.add(lblCases);
		
		JLabel lblDeaths = new JLabel("Number of deaths");
		lblDeaths.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDeaths.setBounds(50, 195, 152, 18);
		contentPane.add(lblDeaths);
		
		//DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
		
		JLabel lblRecovered = new JLabel("Number of cases recovered");
		lblRecovered.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRecovered.setBounds(50, 237, 220, 18);
		contentPane.add(lblRecovered);
		
		String[] choices = {"Select","Brampton", "Toronto", "Mississauga","Kitchener","Oakville","Cambridge"};
		JComboBox comboBox = new JComboBox(choices);
		comboBox.setBounds(328, 101, 168, 26);
		contentPane.add(comboBox);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(327, 52, 169, 26);
		dateChooser.setDateFormatString("MM/dd/yyyy");
		contentPane.add(dateChooser);
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JSpinner spinnerCases = new JSpinner();
		spinnerCases.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerCases.setBounds(327, 148, 65, 26);
		contentPane.add(spinnerCases);
		
		JSpinner spinnerDeaths = new JSpinner();
		spinnerDeaths.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerDeaths.setBounds(327, 193, 65, 26);
		contentPane.add(spinnerDeaths);
		
		JSpinner spinnerRecover = new JSpinner();
		spinnerRecover.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerRecover.setBounds(327, 239, 65, 26);
		contentPane.add(spinnerRecover);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Validator.dateValidate(dateChooser,"Date") && Validator.isPresent(comboBox,"City")) {
					Date date = dateChooser.getDate();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					String strDate= dateFormat.format(date);  
					String city = comboBox.getSelectedItem().toString();
					int cases = (int)spinnerCases.getValue();
					int deaths = (int)spinnerDeaths.getValue();
					int recovered = (int)spinnerRecover.getValue();
					
					try {
                        Data data = new Data(strDate,city,cases,deaths,recovered);
						TextIOFile.writeRecord(data);
						JOptionPane.showMessageDialog(null,"Data is saved to the File");
                        dateChooser.setDate(null);
                        comboBox.setSelectedItem("Select");
                        spinnerCases.setValue(0);
                        spinnerDeaths.setValue(0);
                        spinnerRecover.setValue(0);
						} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Error !" + e1.getMessage());
						//TextFileIO.readStudents();
					    } 
					//JOptionPane.showMessageDialog(null, strDate + "\n" + city + "\n" + cases + "\n" + deaths +"\n" + recovered);
					}
				}
			}
		);
		btnSubmit.setBounds(411, 286, 110, 37);
		contentPane.add(btnSubmit);
	}
}
