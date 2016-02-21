package pji_deputes;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import org.jsoup.nodes.*;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class ParserAssemblee {

	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		
		String[] result;
		
		int k = 0;
		String b ;
		
		String c ="";
		Document doc;
		File file = new File("assemblee.html");
		FileWriter fwriter = new FileWriter("orateur.txt", true);
        PrintWriter outputfile = new PrintWriter(fwriter);
		try {
			doc = Jsoup.parse(file, "iso-8859-1");

			// get doc title
			String title = doc.title();
			System.out.println("Titre : " + title);
			
			//recuperation de l'element contenant le nom du president
			Elements td = doc.getElementsByClass("sompresidence");
			String presi = td.text();
			System.out.println("le president : " + presi);
			
			//recuperation du nom et prenom du president de l'assise
			
			 result = presi.split("\\s");
		     for (int x=0; x<result.length; x++)
		    	 
		         System.out.println(result[x]);
		     System.out.println(result.length);
		     
		     
		    for(int i=0;i<result.length;i++){
		    		   if (result[i].equals("M.")||result[i].equals("Mme")){
		    		         for(int j=i;j<result.length;j++){ 
		    		       	 
		    		      c=c.concat(result[j]);
		    		      c=c.concat(" ");
		    		      
		    		         }    
		    		   }			
	         }
		    
		    		
			
		    System.out.println(c);
		    
			
			
		 
			// get all links
			Elements links = doc.select("ORATEUR");
			String[] deputes = new String[links.size()];
			for (Element link : links) {
				
				/**
				deputes[k]=link.text();
				k++;
				
			
				if(link.text().equals("M. le président.")||link.text().equals("M. le président")||link.text().equals("M. le président.")||link.text().equals("M. le président.")||link.text().equals("M. le président.")||link.text().equals("M. le président.")){
					outputfile.println("Orateur : " + c);
					
					 k++;
					 System.out.println(k);
				}
				
				else {
				**/
				outputfile.println("Orateur : " + link.text());
			//}
			}
			
			
				/**
				
				 System.out.println(deputes[2]);
				
				**/
			
			
			outputfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
