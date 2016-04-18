package pji;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecupLienCommission {
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<String> list2 = new ArrayList<String>();

	Document doc;

	public RecupLienCommission() {
		list1.add("http://www.assemblee-nationale.fr/13/budget/plf2008/commissions_elargies/cri/");
		list1.add("http://www.assemblee-nationale.fr/13/budget/plf2009/commissions_elargies/cr/");
		list1.add("http://www.assemblee-nationale.fr/13/budget/plf2010/commissions_elargies/cr/");
	}

	public void Recuplien() throws Exception {

		for (String a : list1) {

			doc = Jsoup.connect(a).get();

			Elements link1 = doc.select("h1 > a[href*=c0]");
			for (Element link : link1) {
				String n = link.attr("href");
				list2.add(a.concat(n));
			}

		}

	}

}
