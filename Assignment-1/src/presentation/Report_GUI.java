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
import java.util.ArrayList;
import java.util.Date;


public class Report_GUI extends JFrame {

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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Reporting");
        setBounds(100, 100, 682, 347);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JRadioButton rdbtnAll = new JRadioButton("All");
        rdbtnAll.setSelected(true);
        buttonGroup.add(rdbtnAll);
        rdbtnAll.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnAll.setBounds(37, 50, 103, 21);
        contentPane.add(rdbtnAll);

        JRadioButton rdbtnCity = new JRadioButton("City");
        buttonGroup.add(rdbtnCity);
        rdbtnCity.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnCity.setBounds(37, 83, 103, 21);
        contentPane.add(rdbtnCity);

        JRadioButton rdbtnDate = new JRadioButton("Date");
        buttonGroup.add(rdbtnDate);
        rdbtnDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnDate.setBounds(37, 148, 103, 21);
        contentPane.add(rdbtnDate);

        JLabel lblNewLabel = new JLabel("Categories :-");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(37, 20, 150, 24);
        contentPane.add(lblNewLabel);

        String[] choices = {"Select", "Brampton", "Toronto", "Mississauga", "Kitchener", "Oakville", "Cambridge"};
        JComboBox cmboxCity = new JComboBox(choices);
        cmboxCity.setEnabled(false);
        cmboxCity.setBounds(37, 115, 132, 27);
        contentPane.add(cmboxCity);

        JDateChooser datePicker = new JDateChooser();
        datePicker.setDateFormatString("MM/dd/yyyy");
        datePicker.setEnabled(false);
//        datePicker.getCalendarButton().addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
        datePicker.setBounds(37, 184, 134, 27);
        contentPane.add(datePicker);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(368, 28, 276, 256);
        contentPane.add(textArea);

        rdbtnCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rdbtnCity.isSelected()){
                    cmboxCity.setEnabled(true);
                    datePicker.setEnabled(false);
                    datePicker.setDate(null);
                    textArea.setText(null);
                }
            }
        });

        rdbtnDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rdbtnDate.isSelected()){
                    datePicker.setEnabled(true);
                    cmboxCity.setEnabled(false);
                    cmboxCity.setSelectedItem("Select");
                    textArea.setText(null);
                }
            }
        });

        rdbtnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rdbtnAll.isSelected()){
                    datePicker.setEnabled(false);
                    datePicker.setDate(null);
                    cmboxCity.setEnabled(false);
                    cmboxCity.setSelectedItem("Select");
                    textArea.setText(null);
                }
            }
        });

        JButton btnGenerateReport = new JButton("Generate Report");
        btnGenerateReport.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnGenerateReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String report;
                    if (rdbtnAll.isSelected()) {
                         report = getReport(TextIOFile.findAll());
                    } else if (rdbtnCity.isSelected()) {
                        String city = cmboxCity.getSelectedItem().toString();
                        report = getReport(TextIOFile.findForCity(city));
                    }else{
                        Date date = datePicker.getDate();
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String strDate= dateFormat.format(date);
                        report = getReport(TextIOFile.findForDate(strDate));
                    }
                    textArea.setText(report);
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error !" + ioException.getMessage());
                }

            }
        });
        btnGenerateReport.setBounds(29, 253, 158, 31);
        contentPane.add(btnGenerateReport);
    }

    private String getReport(ArrayList<Data> list){
        StringBuilder report = new StringBuilder();
        if(list.isEmpty()){
            report.append("NO DATA");
        }else{
            int totalCases=0, totalDeaths=0, totalRecovered=0;
            report.append("DATA RECORDS").append(System.lineSeparator());
            for(Data data:list) {
                report.append(data.toString()).append(System.lineSeparator());
                totalCases=totalCases+data.getCases();
                totalDeaths=totalDeaths+data.getDeaths();
                totalRecovered= totalRecovered+data.getRecovered();
            }
            report.append("TOTALS").append(System.lineSeparator())
                    .append("Cases:").append(totalCases).append(';')
                    .append("Deaths:").append(totalDeaths)
                    .append("Recovered:").append(totalRecovered);
        }
        return report.toString();
    }
}
