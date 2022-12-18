package projectSpringboot.algorithm;

import projectSpringboot.dto.UserDTO;

public class ValidForm {

	
	/*
	 * Valid Register form
	 * */
	public String validRegisterForm(UserDTO userDTO) {

		/*check mail*/
		String mail = userDTO.getMailUser();
		if (!mail.contains("@") || !mail.contains(".")) {
			return "Your gmail is not valid";
		}

		/*check password*/
		String password = userDTO.getPasswordUser();
			//check symbol
		boolean check = true; 
		String[] symbol = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "=", "_", "+", ",", ".", "/", "<",
				">", "?", ";", ":", "[", "]", "{", "}" };
		for (int i = 0; i < symbol.length; i++) {
			if (password.contains(symbol[i])) {
				check = false;
				break;
			}
		}
		if (check) {
			return "Password must have a special symbol";
		}
		
			//check Lower character
		boolean checkLower = true;
		for (int i1 = 0; i1 < password.length(); i1++) {
			char ch1 = password.charAt(i1);
			if ((Character.isLowerCase(ch1))) {
				checkLower = false;
				break;
			}
		}
		if(checkLower) {
			return "Password must have a Lower Case character";
		}
		
			//check Upper character
		boolean checkUpper = true;
		for (int i = 0; i < password.length(); i++) {
			char ch2 = password.charAt(i);
			if ((Character.isUpperCase(ch2))) {
				checkUpper = false;
				break;
			}
		}
		if(checkUpper) {
			return "Password must have a Upper Case character";
		}

			//check length of password
		if (password.length() < 8) {
			return "Password must contain 8 charaters";
		}

		/*check user name*/ 
		String name = userDTO.getNameUser();
		if (name.length() < 10) {
			return "Name not valid";
		}

		/*check phone*/ 
			//check all characters are number
		String phone = userDTO.getPhone();
		for (char c : phone.toCharArray()) {
			if (!Character.isDigit(c)) {
				return "Phone not valid";
			}
		}
		if (phone.length() < 10) {
			return "Phone not valid";
		}

		// check address
		String address = userDTO.getAddressUser();
		if (address.length() < 4) {
			return "Address not valid";
		}

		return null;
	}

	
	/*
	 * Valid create lottery form
	 * */
	public String validCusHistoryForm(UserDTO userDTO) {
		
		String drawCode = userDTO.getCodeDrawCH();
		if (drawCode.length() != 6) {
			return "Mã vé không tồn tại !";
		}
		
		for (char c : drawCode.toCharArray()) {
			if (!Character.isDigit(c)) {
				return "Mã vé không tồn tại !";
			}
		}
		
		return null;
	}
}
