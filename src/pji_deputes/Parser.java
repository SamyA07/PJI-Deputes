package pji;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	String[] result;
	String[] result1;
	int k = 0;
	int h = 0;
	int t;
	String b;
	String lien;
	String c = "";
	Document doc;
	String title;
	String date;
	String num_s;
	String nom_pres;
	String[][] deputes1;

	public Parser(String lien) throws Exception {

		this.lien = lien;
		doc = Jsoup.connect(lien).get();
	}

	public String titreAssemble() {
		title = doc.title();
		return title;
	}

	public void datenumAssemble() {

		Elements link1 = doc.select("meta[name=QUANTIEME_SEANCE]");
		String n = link1.get(0).attr("content");
		result1 = n.split("\\s");
		if (result1.length == 7) {
			date = result1[4].concat(" ").concat(
					result1[5].concat(" ").concat(result1[6]));
			num_s = result1[0].concat(" ").concat(result1[1]);
		}

		else {
			date = result1[3].concat(" ").concat(
					result1[4].concat(" ").concat(result1[5]));
			num_s = "Premi�re".concat(" ").concat(result1[0]);

		}
	}

	public String nompresi() {

		Elements td = doc.getElementsByClass("sompresidence");
		nom_pres = td.text();
		result = nom_pres.split("\\s");

		for (int i = 0; i < result.length; i++) {
			if (result[i].contains("M.") || result[i].contains("Mme")) {
				for (int j = i; j < result.length; j++) {

					c = c.concat(result[j]);

				}
			}
		}
		return c;
	}

	public void parole() {
		int i = 0;
		Elements links;
		if (!lien.contains("2006-2007")) {

			links = doc.select("p > b");
		} else {
			links = doc.select("orateur");
		}

		for (Element link : links) {
			if (link.text().contains("M.") || link.text().contains("Mme")) {
				i++;
			}

		}
		t = i;
		deputes1 = new String[i][6];

		int d = 0;

		for (Element link : links) {
			if (link.text().contains("M.") || link.text().contains("Mme")) {

				Element e;

				String s = null;
				if (link.parent().toString().contains("<p>")) {
					e = link.parent();
				} else {

					e = link.parent().parent();
				}

				Element a = link.parent();
				if (link.text().contains("pr�sident")) {
					deputes1[d][0] = c;

				} else {
					deputes1[d][0] = link.text();

				}

				if (link.nextSibling().toString().contains("<i>")) {
					s = link.nextSibling().nextSibling().toString();
				}

				else {
					s = link.nextSibling().toString();
				}

				int t = 1;
				while (t > 0) {
					if (!(e.nextElementSibling().toString().contains("<b>"))
							&& !(e.nextElementSibling().toString()
									.contains("<ul>"))) {

						s = s.concat(" ").concat(e.nextElementSibling().text());
						t = 1;
						e = e.nextElementSibling();
					}

					else {
						t = 0;
					}

				}

				deputes1[d][1] = s;
				d++;
			}

			else {

			}

		}
	}

	public void espace() {
		char ch;
		int space;

		for (int f = 0; f < t; f++) {
			space = 0;

			for (int s = 0; s < deputes1[f][1].length(); s++) {
				ch = deputes1[f][1].charAt(s);
				if (Character.isWhitespace(ch)) {

					space++;

				}

			}
			space = space + 1;

			deputes1[f][2] = Integer.toString(space);

		}

	}

	public void remplissagefinal() {
		for (int g = 0; g < deputes1.length; g++) {

			for (int l = 0; l < deputes1.length; l++) {

				deputes1[g][4] = date;

				deputes1[g][5] = num_s;

				if (deputes1[g][0].contains(c)) {
					deputes1[g][3] = "yes";
				} else {
					deputes1[g][3] = "no";
				}

			}

		}

	}
}
