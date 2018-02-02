package hackathon.virustotal;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import hackathon.virustotal.exception.VTException;
import hackathon.virustotal.rest.VTController;

public class App {

	private static final String HASH_ALG = "SHA-256";

	public static void main(String[] args) {

		try {
			String filePath = "C:\\Users\\Administrator\\Downloads\\ChromeSetup.exe";
			String sha256ofChromeSetup = calculateSHA256(filePath);
			System.out.println(sha256ofChromeSetup);

		} catch (NoSuchAlgorithmException | IOException  ex) {
			System.out.println(ex.getClass() +" exception occurred");
			System.out.println("Exiting because of exception.");
		}
	}

	private static String calculateSHA256(String filePath) throws NoSuchAlgorithmException, IOException {
		FileInputStream fis = new FileInputStream(filePath);
		try {
			MessageDigest md = MessageDigest.getInstance(HASH_ALG);

			byte[] dataBytes = new byte[1024];

			int nread = 0;
			while ((nread = fis.read(dataBytes)) != -1) {
				md.update(dataBytes, 0, nread);
			}

			byte[] mdbytes = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			System.out.println("Hex format : " + sb.toString());
			return sb.toString();
		} finally {
			fis.close();
		}
	}
}
