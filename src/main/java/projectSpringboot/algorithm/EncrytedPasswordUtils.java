package projectSpringboot.algorithm;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class EncrytedPasswordUtils {
	 // Encryte Password with BCryptPasswordEncoder
    public String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
//    public static String encrytePassword2(String password) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.encode(password);
//    }
//
//    public static void main(String[] args) {
//        String password = "Cong!1206";
//        String encrytedPassword = encrytePassword2(password);
//
//        System.out.println("Encryted Password: " + encrytedPassword);
//    }
}
