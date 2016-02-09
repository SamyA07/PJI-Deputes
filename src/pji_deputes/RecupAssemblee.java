package pji_deputes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecupAssemblee {

	public static void main(String[] args) throws Exception {
		File file = new File("assemblee.html");
		FileWriter filewriter = new FileWriter(file);
		HttpURLConnection conn = (HttpURLConnection) new URL(
				"http://www.assemblee-nationale.fr/13/cri/2006-2007-extra/20071028.asp#P76_3923").openConnection();
		conn.connect();

		BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

		byte[] bytes = new byte[1024];
		int tmp;
		while ((tmp = bis.read(bytes)) != -1) {
			String chaine = new String(bytes, 0, tmp);
			filewriter.write(chaine);
		}
		filewriter.close();
		conn.disconnect();
	}
}
