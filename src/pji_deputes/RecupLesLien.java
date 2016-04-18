package pji;

import java.io.FileWriter;
import java.io.PrintWriter;

import com.opencsv.CSVWriter;

public class RecupLesLien {

	public static void main(String[] args) throws Exception {

		CSVWriter writer1 = new CSVWriter(new FileWriter("users1.csv"), ',');
		// FileWriter fwriter = new FileWriter("lien.txt", true);
		// PrintWriter outputfile = new PrintWriter(fwriter);

		// FileWriter fwriter1 = new FileWriter("lien2.txt", true);
		// PrintWriter outputfile1 = new PrintWriter(fwriter1);
		/**
		 * Recuplie adresse =new
		 * Recuplie("http://www.assemblee-nationale.fr/13/debats/index.asp");
		 * 
		 * adresse.Listann();
		 * 
		 * for (String b: adresse.list1){
		 * 
		 * adresse.RecupCRIperio(b); for(String c: adresse.list2){
		 * adresse.RecuplienCRI(c); }
		 * 
		 * adresse.viderliste2(); }
		 * 
		 * 
		 * for (String e: adresse.list3){
		 * 
		 * //System.out.println(e); outputfile.println(e); }
		 * 
		 * outputfile.close();
		 * 
		 * RecupLiCom commission= new RecupLiCom(); commission.Recuplien();
		 * 
		 * for(String h: commission.list2){
		 * 
		 * outputfile1.println(h);
		 * 
		 * 
		 * } outputfile1.close();
		 **/

		Parser p = new Parser("http://www.assemblee-nationale.fr/13/cri/2010-2011-extra/20111013.asp");
		p.datenumAssemble();
		p.titreAssemble();
		p.nompresi();
		p.parole();
		p.espace();
		p.remplissagefinal();

		for (String elem[] : p.deputes1) {
			writer1.writeNext(elem);

		}

		writer1.close();

	}

}
