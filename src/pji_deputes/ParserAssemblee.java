package pji_deputes;

import java.io.File;
import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserAssemblee {

	public static void main(String[] args) {

		Document doc;
		File file = new File("assemblee.html");
		try {
			doc = Jsoup.parse(file, "iso-8859-1");

			// get doc title
			String title = doc.title();
			System.out.println("Titre : " + title);

			// get all links
			Elements links = doc.select("ORATEUR");
			for (Element link : links) {
				System.out.println("Orateur : " + link.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
