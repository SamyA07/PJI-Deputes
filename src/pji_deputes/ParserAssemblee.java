package pji_deputes;

import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;

import org.jsoup.nodes.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

/**
 * Classe permettant d'étudier le fichier de l'assemblée récupéré et en extraire
 * un fichier tableur avec les données pertinentes
 * 
 * @author AMEUR Samy, SENHAJI Taha
 * 
 */
public class ParserAssemblee {

	// ATTRIBUTS

	/** Le document jsoup à parcourir */
	private Document doc;
	/** Le titre du document */
	private String title;
	/** Le nom du président */
	private String president;
	/** Les éléments pertinents du document */
	private Elements balise;
	/** Le premier fichier intermédiaire de sortie */
	private PrintWriter pw1;
	/** Le deuxième fichier intermédiaire de sortie */
	private PrintWriter pw2;
	/** Le tableau contenant les députés */
	private String[] lesDeputes;
	/** Le tableau contenant les interventions */
	private String[][] lesInterventions;
	/** Le tableau final contenant les lignes du fichier csv */
	private String[][] lesLignes;
	/** La liste contenant les lignes pour écriture */
	private ArrayList<String> listLignes;
	/** Le fichier csv de sortie */
	private CSVWriter cw;

	// CONSTRUCTEUR

	/**
	 * Constructeur
	 * 
	 * @param fichier
	 *            le fichier html à parcourir
	 * @param balise
	 *            le nom de la balise qui contient chaque député
	 * @param president
	 *            la balise contenant la presidence (nom du président)
	 * @throws IOException
	 */
	public ParserAssemblee(String fichier, String balise, String president)
			throws IOException {
		this.doc = Jsoup.parse(new File(fichier), "utf-8");
		this.title = this.doc.title();
		this.president = "";
		this.pw1 = new PrintWriter(new FileWriter("deputes.xml", true));
		this.pw2 = new PrintWriter(new FileWriter("interventions.xml", true));
		this.listLignes = new ArrayList<String>();
		this.cw = new CSVWriter(new FileWriter("resume.csv"), ',');
	}

	// METHODES

	/**
	 * Retourne le nom du président
	 * 
	 * @return le nom du président
	 */
	public String getPresident() {
		return this.president;
	}

	/**
	 * Definit le nom du président selon la balise du document où se trouve son
	 * nom
	 * 
	 * @param balise
	 *            la balise qui contient le nom du président
	 */
	public void setPresident(String balise) {
		Elements td = doc.getElementsByClass(balise);
		String pres = td.text();
		String[] result = pres.split(" ");// On split pour pouvoir selectionner
											// le nom et le prénom

		for (int i = 0; i < result.length; i++) {
			// On commence à concaténer au moment où l'on arrive aux infos du
			// président
			if (result[i].equals("M.") || result[i].equals("Mme.")) {
				for (int j = i; j < result.length; j++) {
					this.president = this.president.concat(result[j]);
					this.president = this.president.concat(" ");
				}
			}
		}
	}

	/**
	 * Permet de définir la balise du document qui contient les orateurs
	 * 
	 * @param balise
	 *            la balise qui contient le nom de des orateurs
	 */
	public void setBalise(String balise) {
		this.balise = this.doc.select(balise);
	}

	/**
	 * Récupère tous les députés qui ont pris la parole durant l'assemblée du
	 * document étudié
	 */
	public void setDeputes() {
		int h = 0;
		@SuppressWarnings("unused")
		int k = 0;

		this.lesDeputes = new String[this.balise.size() * 2];
		this.pw1.println("<title>" + this.title + "<title>");
		this.pw1.println("<orateurs>");
		for (Element link : this.balise) {
			// Si il s'agit du president, on remplace son nom directement
			if (link.text().contains("pr�sident")) {
				this.pw1.println("<orateur>" + this.getPresident()
						+ "</orateur>");
				this.lesDeputes[h] = this.getPresident();
				h++;
				this.pw1.println(link.nextSibling().toString() + "\n\n");
				this.lesDeputes[h] = link.nextSibling().toString();
				h++;
				k++;
			}
			// Le cas des autres députés
			else {
				this.pw1.println("<orateur>" + link.text() + "</orateur>");
				this.lesDeputes[h] = link.text();
				h++;
				this.pw1.println("<parole>" + link.nextSibling().toString()
						+ "</parole>" + "\n");
				this.lesDeputes[h] = link.nextSibling().toString();
				h++;
			}
		}
		this.pw1.println("</orateurs>");
	}

	/**
	 * Met à jour le président par son vrai nom dans le tableau contenant tous
	 * les députés
	 */
	public void updateNamePresident() {
		for (int e = 0; e < this.lesDeputes.length; e++) {
			if (this.lesDeputes[e].contains("pr�sident")) {
				this.lesDeputes[e] = this.president;
			}
		}
	}

	/** Structure dans un fichier toutes les interventions des députés */
	public void getInterventions() {
		this.pw2.println("<assise>");
		for (int e = 0; e < this.lesDeputes.length; e = e + 2) {
			this.pw2.println("<intervention>");
			this.pw2.println("<orateur>" + this.lesDeputes[e].toString()
					+ "</orateur>");
			this.pw2.println("<parole>" + this.lesDeputes[e + 1].toString()
					+ "</parole>");
			this.pw2.println("</intervention>" + "\n" + "\n");
		}
		this.pw2.println("</assise>");
	}

	/**
	 * Récupère toutes les interventions faites par chaque député Dans le
	 * tableau, on ajoute le nom du député et les phrases qu'il aura prononcé
	 */

	public void setInterventions() {
		this.lesInterventions = new String[this.balise.size()][2];
		int d = 0;
		for (Element link : this.balise) {
			// Si il s'agit du président, on remplace son nom directement
			if (link.text().contains("pr�sident")) {
				this.lesInterventions[d][0] = this.getPresident();
				this.lesInterventions[d][1] = link.nextSibling().toString();
				d++;
			}
			// Le cas des autres députés
			else {
				this.lesInterventions[d][0] = link.text();
				this.lesInterventions[d][1] = link.nextSibling().toString();
				d++;
			}
		}
	}

	/**
	 * Compte le nombre de mots prononcés par chaque député à partir de leurs
	 * interventions On stocke le nom, le nombre de prises de parole et le
	 * nombre de mots prononcés pour chaque député dans la liste d'écriture du
	 * fichier de sortie
	 */
	public void comptageDeputes() {
		char ch;
		int space;

		for (int f = 0; f < this.balise.size(); f++) {
			space = 0; // compteur de mots
			for (int s = 0; s < this.lesInterventions[f][1].length(); s++) {
				ch = this.lesInterventions[f][1].charAt(s);
				if (Character.isWhitespace(ch)) {
					space++; // des qu'il y a un espace on incrémente
				}
			}
			space = space + 1;
			this.lesInterventions[f][1] = Integer.toString(space);
		}
		for (int s = 0; s < this.lesInterventions.length; s++) {
			if (!this.listLignes.contains(this.lesInterventions[s][0])) {
				this.listLignes.add(this.lesInterventions[s][0]);
			}
		}
		Collections.sort(this.listLignes);
	}

	/** Effectue l'écriture des informations pertinentes dans le fichier csv */
	public void remplissageFichier() {
		this.lesLignes = new String[this.listLignes.size()][3];
		this.lesLignes[0][0] = "Identité";
		this.lesLignes[0][1] = "Nombre de prises de parole";
		this.lesLignes[0][2] = "Nombre de mots prononcés";
		for (int g = 1; g < this.lesLignes.length; g++) {
			int z = 0;
			int w = 0;
			for (int l = 0; l < this.lesInterventions.length; l++) {
				if (this.lesInterventions[l][0]
						.contains(this.listLignes.get(g))) {
					z = z + 1;
					w = w + Integer.parseInt(this.lesInterventions[l][1]);
				}
			}
			this.lesLignes[g][0] = this.listLignes.get(g);
			this.lesLignes[g][1] = Integer.toString(z);
			this.lesLignes[g][2] = Integer.toString(w);
		}

		for (String elem[] : this.lesLignes) {
			this.cw.writeNext(elem);
		}
	}

	/** Ferme tous les fichiers après écriture */
	public void fermetureFichiers() throws IOException {
		this.cw.close();
		this.pw1.close();
		this.pw2.close();
	}
}