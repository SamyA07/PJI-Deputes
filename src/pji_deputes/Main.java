package pji_deputes;

import java.io.IOException;

/**
 * Classe d'éxécution pour récupérer les infos d'un résumé d'assemblée
 * 
 * @author AMEUR Samy, SENHAJI Taha
 * 
 */
public class Main {

	/**
	 * @param 1. l'adresse url de l'assemblée 
	 * 		  2. le nom du fichier à parcourir
	 *        3. la balise contenant les députés 
	 *        4. la balise contenant le
	 *        président
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new RecupAssemblee(args[0]);
		ParserAssemblee pa = new ParserAssemblee(args[1], args[2], args[3]);
		pa.setPresident(args[3]);
		pa.setBalise(args[2]);
		pa.setDeputes();
		pa.updateNamePresident();
		pa.getInterventions();
		pa.setInterventions();
		pa.comptageDeputes();
		pa.remplissageFichier();
		pa.fermetureFichiers();
	}

}
