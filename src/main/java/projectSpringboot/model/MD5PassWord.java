package projectSpringboot.model;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class MD5PassWord {
	public String setMd5(String input) { 
        try { 
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
	
	
	//test
	public void testSetMd5() {
	    System.out.print("Input password: ");
	    Scanner kb = new Scanner(System.in);
	    String pass = kb.nextLine();
	    System.out.println("Your Password generated by MD5 is: " + setMd5(pass));
	    kb.close();
	}
	
	
	
}
