# pji-deputes

AMEUR Samy
SENHAJI Taha

Sujet : Que font les députés ?

Classes :
- RecupAssemblee.java : récupère le contenu d'un résumé d'assemblée dans un fichier html à partir de son url.
- ParserAssemblee : parcourt le fichier html récupéré et en extrait les informations pertinentes dans un fichier de sortie csv.
- Main : éxécute l'opération des classes RecupAssemblee et ParserAssemblee

Fichiers :
- assemblee.html : le code html du resume de l'assemblée
- deputes.xml : le code xml contenant tous les députés de l'assemblée
- interventions.xml : le code xml contenant toutes les interventions de l'assemblée
- resume.csv : le résumé de l'assemblée contenant les informations pertinentes à extraire du résumé de l'assemblée

Bibliothèques :
- jsoup-1.8.3.jar : pour récupérer le code html et l'étudier
- opencsv-3.7.jar : pour créer un fichier csv et le remplir via java