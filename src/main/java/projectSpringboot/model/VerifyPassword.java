package projectSpringboot.model;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VerifyPassword {
	public static boolean verify(String inputPassword, String hashPassWord) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(inputPassword.getBytes());
		byte[] digest = md.digest();
		String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hashPassWord.equals(myChecksum);
	}
}
