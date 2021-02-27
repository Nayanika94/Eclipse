package presentation;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;


import com.toedter.calendar.JDateChooser;

public class Validator {
	public static boolean dateValidate(JDateChooser d, String title) {
		if(d.getDate() == null) {
			showMessage(d,title+" is a required field.\nPlease re-enter.");
			d.requestFocusInWindow();
			//showMessage(d,title+" is a required field. \nPlease re-enter.");
			return false;	
		}
		return true;
	}
	private static void showMessage(JDateChooser d, String message) {
		JOptionPane.showMessageDialog(d, message,"Invalid Entry",JOptionPane.ERROR_MESSAGE);
	}
	public static boolean isPresent(JComboBox c, String city) {
		if(c.getSelectedItem().toString().equals("Select")) {
			showErrorMessage(c,city+" is a required field.\nPlease re-enter.");
			c.requestFocusInWindow();
			//showMessage(d,title+" is a required field. \nPlease re-enter.");
			return false;	
		}
		return true;
	}
	private static void showErrorMessage(JComboBox c, String message) {
		JOptionPane.showMessageDialog(c, message,"Invalid Entry",JOptionPane.ERROR_MESSAGE);
	}

}
