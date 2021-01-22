import Models.*;
import Algorithm.*;
import Convertion.Convert;
import RepairTeam.Actions;
import Path.VerifyPath;
import Program.*;
import java.io.*;
import java.util.*;

public class Driver {
	public static ArrayList<Etat> tabInterrupteursOuvert        = new ArrayList<>();
	public static ArrayList<Etat> tabInterrupteursFerme         = new ArrayList<>();
	public static ArrayList<Etat> tabBris                       = new ArrayList<>();
	public static ArrayList<Etat> tabMaisons                    = new ArrayList<>();
	public static ArrayList<Etat> tabSource                     = new ArrayList<>();
	public static ArrayList<Etat> tabConduits                   = new ArrayList<>();
	public static ArrayList<Position> vide                      = new ArrayList<>();
	public static ArrayList<Position> obstacles                 = new ArrayList<>();
	public static Position equipe                               = null;

	/**
	 * Methode d'entree du programme.
	 *
	 * @param args le fichier texte representant la carte du monde.
	 * @throws Exception si le fichier n'existe pas ou si args == null.
	 */
    public static void main(String[] args) throws Exception {
    	ArrayList<Position> interrupteursOuvert = new ArrayList<>();
		ArrayList<Position> interrupteursFerme = new ArrayList<>();
        ArrayList<Position> bris = new ArrayList<>();
		ArrayList<Position> maisons = new ArrayList<>();
		ArrayList<Position> conduits = new ArrayList<>();
		ArrayList<Position> sources = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String ligne=br.readLine();
        for(int l=0;ligne!=null;l++){
            for(int c=0;c<ligne.length();c++){
                switch(ligne.charAt(c)){
					case ' ':
						vide.add(new Position(l, c));
						break;
                    case '*':
                        equipe = new Position(l, c);
                        break;
                    case 'I':
                    case 'i':
                        interrupteursOuvert.add(new Position(l, c));
                        break;
					case 'J':
					case 'j':
						interrupteursFerme.add(new Position(l, c));
						break;
					case 'B':
                    case 'b':
                        bris.add(new Position(l, c));
                        break;
					case '#':
						obstacles.add(new Position(l, c));
						break;
					case 'M':
					case 'm':
						maisons.add(new Position(l, c));
						break;
					case 'C':
					case 'c':
						conduits.add(new Position(l, c));
						break;
					case 'S':
					case 's':
						sources.add(new Position(l, c));
						break;
                }
            }
            ligne=br.readLine();
        }

		// Initialiser l'etat de l'equipe
		Etat start = Startup.initializeTeam(equipe);

		//Si aucun bris sur la carte
		Program.noDamageFound(bris, interrupteursOuvert, interrupteursFerme);

		//Si bris existe mais aucun interrupteur
		Program.noInterrupterFound(bris, interrupteursOuvert, interrupteursFerme);

		//initialisation des listes d'etats des objets
		Convert.creerListeEtat(interrupteursOuvert, tabInterrupteursOuvert);
		Convert.creerListeEtat(interrupteursFerme, tabInterrupteursFerme);
		Convert.creerListeEtat(bris, tabBris);
		Convert.creerListeEtat(maisons, tabMaisons);
		Convert.creerListeEtat(sources, tabSource);
		Convert.creerListeEtat(conduits, tabConduits);

		//Si aucuns bris mais interrupteur ferme
		if(bris.size() == 0 && interrupteursFerme.size() >= 1) {
			for(String s : PathFinding.aStar(start, start, tabInterrupteursFerme.get(0), obstacles)) {
				System.out.print(s);
			}
			System.out.print(Actions.ouvrirInterrupteur());
			System.exit(0);
		}

		//Si seulement un interrupteur et un bris
		if((tabInterrupteursOuvert.size() == 1 || tabInterrupteursFerme.size() == 1) && tabBris.size() == 1
		&& tabSource.size() == 1) {
			Program.oneInterrupterOneDamageFound(
					tabInterrupteursOuvert,
					tabInterrupteursFerme,
					start,
					obstacles,
					tabBris
			);
			//Si un seul interrupteur et plusieurs bris
		}
		/*	This part will be implemented in version 1.1. Multiple damage points
		if((tabInterrupteursOuvert.size() == 1 || tabInterrupteursFerme.size() == 1) && tabBris.size() > 1
				&& tabSource.size() == 1) {
			Program.oneInterrupterMultipleDamageFound(
					tabInterrupteursOuvert,
					tabInterrupteursFerme,
					start,
					obstacles,
					tabBris
			);
		}
		 */
    }
}
