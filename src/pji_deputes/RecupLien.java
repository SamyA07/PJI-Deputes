package pji;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecupLien {

	Document doc;
	Document doc1;
	Document doc3;
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<String> list2 = new ArrayList<String>();
	ArrayList<String> list3 = new ArrayList<String>();
	String lienini;

	public RecupLien(String lienini) {
		this.lienini = lienini;
	}

	public void Listann() throws Exception {

		doc = Jsoup.connect(lienini).get();
		Elements link1 = doc.select("a[href*=#20]");
		for (Element link : link1) {
			String n = link.attr("href");
			list1.add(n);

		}
	}

	public String Creationdate(String chaine5) {
		String chaine4;
		if (chaine5.contains("#20122013")) {
			chaine4 = "/13/cri/2011-2012";

		} else {
			String chaine = chaine5.substring(1, 9);
			String chaine1 = chaine.substring(0, 4);
			String chaine2 = chaine.substring(4, 8);
			String chaine3 = chaine1.concat("-".concat(chaine2));
			chaine4 = "/13/cri/".concat(chaine3);
		}
		return chaine4;

	}

	public void RecupCRIperio(String annee) throws Exception {

		doc1 = Jsoup.connect(
				"http://www.assemblee-nationale.fr/13/debats/index.asp"
						.concat(annee)).get();
		Elements link2 = doc1.select("a");

		for (Element link : link2) {
			if (link.attr("href").contains(Creationdate(annee))) {
				String n = link.attr("href");
				list2.add(n);
			}
		}

	}

	public void RecuplienCRI(String index) throws Exception {
		doc3 = Jsoup.connect("http://www.assemblee-nationale.fr".concat(index))
				.get();
		Elements link3 = doc3.select("h1[class*=seance]");
		for (Element link : link3) {
			String n = link.child(0).attr("href");
			list3.add("http://www.assemblee-nationale.fr".concat(index).concat(
					n));

		}

	}

	public void viderliste2() {

		list2.clear();
	}

}
