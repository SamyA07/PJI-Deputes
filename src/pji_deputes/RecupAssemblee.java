package pji_deputes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe permettant de récupérer le code source html d'un résumé d'assemblée à
 * partir de son url
 * 
 * @author AMEUR Samy, SENHAJI Taha
 * 
 */
public class RecupAssemblee {

	// ATTRIBUTS

	/** Connexion pour une url donnée */
	private HttpURLConnection conn;
	/** Fichier de sortie contenant le résumé de l'assemblée */
	private File file;
	/** Permet d'écrire dans notre fichier de sortie */
	private FileWriter filewriter;
	/** Buffer intermédiaire entre le contenu de l'url et le fichier de sortie */
	private BufferedInputStream bis;

	// CONSTRUCTEUR
	/**
	 * Se charge de récupérer le contenu d'une adresse url. Tout d'abord,
	 * établit une connexion http avec l'url donnée puis écrit dans un fichier
	 * de sortie le contenu. Puis, fermeture de la connexion.
	 * 
	 * @param url
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public RecupAssemblee(String url) throws MalformedURLException, IOException {
		this.connectURL(url);
		this.writeFile();
		this.unconnectURL();
	}

	// METHODES

	/**
	 * Permet de se connecter via une connexion par url
	 * 
	 * @param url
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void connectURL(String url) throws MalformedURLException,
			IOException {
		this.conn = (HttpURLConnection) new URL(url).openConnection();
		conn.connect();
	}

	/**
	 * Permet de se déconnecter d'une connexion par url
	 * 
	 */
	public void unconnectURL() {
		this.conn.disconnect();
	}

	/**
	 * Ecrit le contenu de là ou la connexion url est établie dans le fichier de
	 * sortie.
	 * 
	 * @throws IOException
	 */
	public void writeFile() throws IOException {
		this.file = new File("assemblee.html");
		this.filewriter = new FileWriter(file);
		this.bis = new BufferedInputStream(conn.getInputStream());

		byte[] bytes = new byte[1024];
		int tmp;
		while ((tmp = bis.read(bytes)) != -1) {
			String chaine = new String(bytes, 0, tmp);
			filewriter.write(chaine);
		}
		filewriter.close();
	}
}
