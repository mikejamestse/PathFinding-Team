import java.io.*;
import java.util.*;
import java.lang.Math;

public class Driver {
	public static ArrayList<Etat> tabInterrupteursOuvert        = new ArrayList<>();
	public static ArrayList<Etat> tabInterrupteursFerme         = new ArrayList<>();
	public static ArrayList<Etat> tabBris                       = new ArrayList<>();
	public static ArrayList<Etat> tabMaisons                    = new ArrayList<>();
	public static ArrayList<Etat> tabSource                     = new ArrayList<>();
	public static ArrayList<Etat> tabConduits                   = new ArrayList<>();
	public static ArrayList<Etat> interrupteursPositionsConduit = new ArrayList<>();
	public static ArrayList<Etat> brisPositionsConduit          = new ArrayList<>();
	public static ArrayList<Etat> sourcePositionsConduit        = new ArrayList<>();
	public static ArrayList<Position> vide                      = new ArrayList<>();
	public static Position equipe                               = null;
	public static ArrayList<Position> obstacles                 = new ArrayList<>();
	
	/**
	* Calcul la fonction f(n).
	* Formule : f(n) = g(n) + h(n) tel que g = distance reelle et h = fonction heuristique.
	*
	* @param e l'etat courant.
	*/
	private static double f(Etat e) {
		e.f = e.g + e.h;
		return e.f;
	}
	
	/**
	* Calcul la fonction g(n).
	*
	* @param e1 l'etat courant.
	*/
	private static double g(Etat e1, Etat e2) {
		e2.g = Math.abs((e2.xPos - e1.xPos) + Math.abs(e2.yPos - e1.yPos));
		return e2.g;
	}
	
	/**
	* Calcul d'une fonction heuristique euclidienne.
	* 
	* @param e1 etat depart.
	* @param e2 etat destination.
	* @return l'heuristique
	*/
	private static double hEuc(Etat e1, Etat e2) {
		// utilise le theoreme de pythagore
		e2.h = Math.sqrt(Math.pow((e2.xPos - e1.xPos), 2) + Math.pow((e2.yPos - e1.yPos), 2));
		return e2.h;
	}
	
	/**
	* Calcul d'une fonction heuristique avec la distance de Manhattan.
	* 
	* @param e1 etat depart.
	* @param e2 etat destination.
	* @return l'heuristique
	*/
	private static double hMan(Etat e1, Etat e2) {
		e2.h = Math.abs((e2.xPos - e1.xPos) + (e2.yPos - e1.yPos));
        return e2.h;
    }

	/**
	 * Trouve les voisins d'un noeud.
	 *
	 * @param e etat courant.
	 * @return une liste des voisins.
	 */
	private static ArrayList<Etat> trouverVoisins(Etat e) {
		ArrayList<Etat> listeVoisins = new ArrayList<>();
		
		Etat v_nord = new Etat(e.xPos, e.yPos - 1, null, 0, 0, 0);
        Etat v_sud = new Etat(e.xPos, e.yPos + 1, null, 0, 0, 0);
        Etat v_ouest = new Etat(e.xPos - 1, e.yPos, null, 0, 0, 0);
        Etat v_est = new Etat(e.xPos + 1, e.yPos, null, 0, 0, 0);
		
		listeVoisins.add(v_nord);
		listeVoisins.add(v_sud);
		listeVoisins.add(v_ouest);
		listeVoisins.add(v_est);
		
		return listeVoisins;
	}

	/**
	 * Verifie si un mouvement de l'equipe d'entretien est valide.
	 * Par exemple: ils ne peuvent pas sortir de la carte et ne peuvent traverser un obstacles.
	 *
	 * @param e1 etat courant.
	 * @param e2 etat successeur.
	 * @return estValide la validite du mouvement
	 */
	private static boolean estValide(Etat e1, Etat e2) {
		boolean estValide = true;

		for(Position p : obstacles) {
			if(e2.xPos == p.colonne && e2.yPos == p.ligne) {
				estValide = false;
				break;
			}
		}

		if(estValide && Math.abs(e2.xPos - e1.xPos) > 1 || Math.abs(e2.yPos - e1.yPos) > 1) {
			estValide = false;
		}
		
		if(estValide && e1.equals(e2)) {
			estValide = false;
		}
		return estValide;
	}

	/**
	 * Verifie si le chemin suit un conduit.
	 *
	 * @param e l'etat courant.
	 * @return estValide la validite du mouvement
	 */
	public static boolean estValideSurConduits(Etat e) {
		boolean estValide = true;

		for(Position p : vide) {
			if(e.xPos == p.colonne && e.yPos == p.ligne) {
				estValide = false;
				break;
			}
		}

		for(Position p : obstacles) {
			if(e.xPos == p.colonne && e.yPos == p.ligne) {
				estValide = false;
				break;
			}
		}

		if(e.xPos == equipe.colonne && e.yPos == equipe.ligne) {
			estValide = false;
		}

		return estValide;
	}
	
	/**
	* Trouver l'etat ayant le plus petit f(n) dans une liste.  
	*/
	private static Etat trouverPPF(ArrayList<Etat> list, Etat n) {
		double min = list.get(0).f;
		int pos = 0;

		Etat e = list.get(pos);

		if(list.size() == 1) {
			e = list.get(0);
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 != list.size() && list.get(i + 1).f < min && estValide(n, list.get(i + 1))) {
					min = list.get(i + 1).f;
					pos = i + 1;
					e = list.get(pos);
				}
			}
		}
		return e;
	}

	/**
	 * Convertie des listes de Position en liste d'Etat.
	 *
	 * @param listeDebut liste de Position.
	 * @param listeFin liste d'Etat.
	 */
	public static void creerListeEtat(ArrayList<Position> listeDebut, ArrayList<Etat> listeFin) {

		for(int i = 0; i < listeDebut.size(); i++) {
			listeFin.add(new Etat(listeDebut.get(i).colonne,
					listeDebut.get(i).ligne,
					null, 0, 0, 0));
		}
	}

    /**
    * Ouvrir un interrupteur.
    */
	private static String ouvrirInterrupteur() {
		return "1 ";
    }

    /**
    * Fermer un interrupteur.
    */
	private static String fermerInterrupteur() {
		return "0 ";
    }

    /**
    * Reparer un conducteur electrique.
    */
	private static String reparerConducteur() {
		return "R ";
    }

	/**
	* Affiche le chemin d'un point depart vers un point destination.
	*
	* @param tabEtats une liste contenant tous les chemins visite dans l'algorithme aStar.
	* @param initial l'etat initial/debut
	* @return cheminFinal une liste contenant les directions du chemin
	*/
	private static ArrayList<String> afficherChemin(ArrayList<Etat> tabEtats, Etat initial) {
		ArrayList<Etat> newTabEtats = new ArrayList<>();
		ArrayList<String> chemin = new ArrayList<>();
		ArrayList<String> cheminFinal = new ArrayList<>();
		Etat n;

    	for(int i = tabEtats.size()-1; i >= 0; i--) {
    		newTabEtats.add(tabEtats.get(i));
		}

		n = newTabEtats.get(0);

		while(n != initial) {
			try {
				if (n.parent.yPos < n.yPos) {
					chemin.add("S ");
				} else if (n.parent.yPos > n.yPos) {
					chemin.add("N ");
				} else if (n.parent.xPos > n.xPos) {
					chemin.add("W ");
				} else if (n.parent.xPos < n.xPos) {
					chemin.add("E ");
				}
				n = n.parent;
			} catch (NullPointerException e) {}
		}

		for(int i = chemin.size()-1; i >= 0; i--) {
			cheminFinal.add(chemin.get(i));
		}
		return cheminFinal;
	}

	/**
	 * Trouve le chemin le plus court entre deux positions.
	 * Utilise un algorithme de style A*.
	 *
	 * Pseudocode : brilliant.org/wiki/a-star-search/#the-a-algorithm
	 *
	 * @param start la position de depart.
	 * @param n la position courante.
	 * @param goal la position destination.
	 * @return une liste contenant les directions a prendre.
	 */
	private static ArrayList<String> aStar(Etat start, Etat n, Etat goal) {
		ArrayList<Etat> openList = new ArrayList<>();
		ArrayList<Etat> closedList = new ArrayList<>();

		// ajouter etat inital dans openList
		openList.add(n);
		
		while(!n.equals(goal)) {
			n = trouverPPF(openList, n);
			
			// si trouverPPF(n) egale au noeud destination, on a termine!
			if(n.equals(goal)) {
				closedList.add(n);
				afficherChemin(closedList, start);
			} else {
				closedList.add(n);
				openList.remove(n);
				
				for(Etat v : trouverVoisins(n)) {
					if (!closedList.contains(v) && estValide(n, v)) {
						v.g = g(start, v);
						v.h = hMan(v, goal);
						v.f = f(v);
						if (v.g < n.g && closedList.contains(v)) {
							n.g = v.g;
							v.parent = n;
						} else if (n.g < v.g && openList.contains(v)) {
							v.g = n.g;
							v.parent = n;
						} else if (!closedList.contains(v) && !openList.contains(v)) {
							v.g = g(start, v);
							v.parent = n;
							openList.add(v);
						}
					}
				}
			}
		}
		return afficherChemin(closedList, start);
    }

	/**
	 * Algorithme A* specifique pour suivre le chemin sur les conduits.
	 *
	 * Pseudocode : brilliant.org/wiki/a-star-search/#the-a-algorithm
	 *
	 * @param start la position de depart.
	 * @param n la position courante.
	 * @param goal la position destination.
	 * @return une liste contenant les directions a prendre.
	 */
	private static ArrayList<String> aStarConduits(Etat start, Etat n, Etat goal) {
		ArrayList<Etat> openList = new ArrayList<>();
		ArrayList<Etat> closedList = new ArrayList<>();

		// ajouter etat inital dans openList
		openList.add(n);

		while(!n.equals(goal)) {
			n = trouverPPF(openList, n);

			// si trouverPPF(n) egale au noeud destination, on a termine!
			if(n.equals(goal)) {
				closedList.add(n);
				afficherChemin(closedList, start);
			} else {
				closedList.add(n);
				openList.remove(n);
				for(Etat v : trouverVoisins(n)) {
					if (!closedList.contains(v) && estValideSurConduits(v)) {
						v.g = g(start, v);
						v.h = hMan(v, goal);
						v.f = f(v);
						if (v.g < n.g && closedList.contains(v)) {
							n.g = v.g;
							v.parent = n;
						} else if (n.g < v.g && openList.contains(v)) {
							v.g = n.g;
							v.parent = n;
						} else if (!closedList.contains(v) && !openList.contains(v)) {
							v.g = g(start, v);
							v.parent = n;
							openList.add(v);
						}
						//trouve les postitions des interrupteurs sur le conduit
						for(int i = 0; i < tabInterrupteursOuvert.size(); i++) {
							if(v.xPos == tabInterrupteursOuvert.get(i).xPos &&
									v.yPos == tabInterrupteursOuvert.get(i).yPos) {
								interrupteursPositionsConduit.add(v);
							}
						}
						//trouve les postitions des bris sur le conduit
						for(int i = 0; i < tabBris.size(); i++) {
							if(v.xPos == tabBris.get(i).xPos &&
									v.yPos == tabBris.get(i).yPos) {
								brisPositionsConduit.add(v);
							}
						}
						//trouve les postitions des sources electriques sur un conduit
						for(int i = 0; i < tabSource.size(); i++) {
							if(n.xPos == tabSource.get(i).xPos &&
									n.yPos == tabSource.get(i).yPos) {
								sourcePositionsConduit.add(n);
							}
							if(v.xPos == tabSource.get(i).xPos &&
									v.yPos == tabSource.get(i).yPos) {
								sourcePositionsConduit.add(v);
							}
						}
					}
				}
			}
		}
		return afficherChemin(closedList, start);
	}

	/**
	 * Trouve le chemin d'un interrupteur a un autre et les fermes.
	 *
	 *@param listeInterrupteurs une liste contenant les interrupteurs.
	 */
	private static void versProchainInterrupteursAFerme(ArrayList<Etat> listeInterrupteurs) {
		for(int i = 0; i < listeInterrupteurs.size(); i++) {
			try {
				for(String s : aStar(listeInterrupteurs.get(i), listeInterrupteurs.get(i),
						listeInterrupteurs.get(i + 1))) {
					System.out.print(s);
				}
				fermerInterrupteur();
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	/**
	 * Trouve le chemin d'un bris vers un autre.
	 */
	private static void versProchainBris() {
    	for(int i = 0; i < tabBris.size(); i++) {
    		try {
    			for(String s : aStar(tabBris.get(i), tabBris.get(i), tabBris.get(i + 1))) {
					System.out.print(s);
				}
				System.out.print(reparerConducteur());
			} catch (IndexOutOfBoundsException e) {}
		}
	}

	/**
	 * Trouve le chemin entre deux points.
	 *
	 * @param start etat debut.
	 * @param fin etat destination.
	 * @return le chemin en forme de direction.
	 */
	public static String trouverChemin(Etat start, Etat fin) {
		String chemin = "";
		try {
			for (String s : aStar(start, start, fin)) {
				chemin += s;
			}
		} catch (IndexOutOfBoundsException e) {}
		return chemin;
	}

	/**
	 * Trouve le chemin entre deux points en suivant un conduit.
	 *
	 * @param start etat debut.
	 * @param n etat courant.
	 * @param fin etat destination.
	 * @return le chemin en forme de direction.
	 */
	public static String trouverCheminConduit(Etat start, Etat n, Etat fin) {
		String chemin = "";

		try {
			for (String s : aStarConduits(start, n, fin)) {
				chemin += s;
			}
		}catch (IndexOutOfBoundsException e) {}

		return chemin;
	}

	/**
	 * Trouve l'interrupteur a fermer. Si un seul interrupteur doit etre fermee au lieu
	 * d'en fermer plusieurs pour aucunes raisons.
	 *
	 * @param start l'etat de debut.
	 * @param n l'etat courant.
	 * @param fin l'etat destination.
	 */
	public static Etat trouverInterrupteursAFermer(Etat start, Etat n, Etat fin) {

		aStarConduits(start, n, fin);

		int distance = Math.abs((start.xPos - interrupteursPositionsConduit.get(0).xPos)) +
				Math.abs((start.yPos - interrupteursPositionsConduit.get(0).yPos));

		Etat interrupteur = interrupteursPositionsConduit.get(0);

		for(int i = 1; i < interrupteursPositionsConduit.size(); i ++) {
			int distance2 = Math.abs((start.xPos - interrupteursPositionsConduit.get(i).xPos)) +
					Math.abs((start.yPos - interrupteursPositionsConduit.get(i).yPos));
			if(distance2 < distance) {
				distance = distance2;
				interrupteur = interrupteursPositionsConduit.get(i);
			}
		}
		return interrupteur;
	}

	public static boolean verifierSiDeuxIntAGerer() {
		boolean deuxInt = false;

		if(sourcePositionsConduit.size() > 1) {
			for (Etat bris : brisPositionsConduit) {
				if ((bris.xPos > interrupteursPositionsConduit.get(0).xPos &&
						bris.xPos < interrupteursPositionsConduit.get(interrupteursPositionsConduit.size() - 1).xPos) ||
						(bris.yPos > interrupteursPositionsConduit.get(0).yPos &&
								bris.yPos < interrupteursPositionsConduit.get(interrupteursPositionsConduit.size() - 1).yPos)) {
					deuxInt = true;
					break;
				}
			}
		}
		return deuxInt;
	}

	/**
	 * Methode main.
	 *
	 * @param args le fichier texte representant la carte du monde.
	 * @throws Exception si le fichier entre en parametre n'existe pas ou si aucun parametre en entre.
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

		//Si aucun bris sur la carte
		if(bris.size() == 0 && interrupteursFerme.size() == 0 && interrupteursOuvert.size() == 0) {
			System.out.println("Aucunes reparations necessaires.");
			System.exit(0);
		}

		//Si bris existe mais aucun interrupteur
		if(bris.size() > 0 && interrupteursFerme.size() == 0 && interrupteursOuvert.size() == 0) {
			System.out.println("L'équipe s'est fait électrocutée!");
			System.exit(0);
		}

		//etat initial de l'equipe
        Etat start = new Etat(equipe.colonne, equipe.ligne, null, 0, 0, 0);

		//initialisation des listes d'etats des objets
		creerListeEtat(interrupteursOuvert, tabInterrupteursOuvert);
		creerListeEtat(interrupteursFerme, tabInterrupteursFerme);
		creerListeEtat(bris, tabBris);
		creerListeEtat(maisons, tabMaisons);
		creerListeEtat(sources, tabSource);
		creerListeEtat(conduits, tabConduits);

		//Si aucuns bris mais interrupteur ferme
		if(bris.size() == 0 && interrupteursFerme.size() >= 1) {
			for(String s : aStar(start, start, tabInterrupteursFerme.get(0))) {
				System.out.print(s);
			}
			System.out.print(ouvrirInterrupteur());
			System.exit(0);
		}

		//Si seulement un interrupteur et un bris
		if((tabInterrupteursOuvert.size() == 1 || tabInterrupteursFerme.size() == 1) && tabBris.size() == 1
		&& tabSource.size() == 1) {
			if(tabInterrupteursOuvert.size() == 1) {
				System.out.print(trouverChemin(start, tabInterrupteursOuvert.get(0)));
				System.out.print(fermerInterrupteur());
				System.out.print(trouverChemin(tabInterrupteursOuvert.get(0), tabBris.get(0)));
				System.out.print(reparerConducteur());
				System.out.print(trouverChemin(tabBris.get(tabBris.size() - 1), tabInterrupteursOuvert.get(0)));
				System.out.print(ouvrirInterrupteur());
			} else if(tabInterrupteursFerme.size() == 1) {
				System.out.print(trouverChemin(start, tabBris.get(0)));
				System.out.print(reparerConducteur());
				System.out.print(trouverChemin(tabBris.get(0), tabInterrupteursFerme.get(0)));
				System.out.print(ouvrirInterrupteur());
			}
			//Si un seul interrupteur et plusieurs bris
		} else if((tabInterrupteursOuvert.size() == 1 || tabInterrupteursFerme.size() == 1) && tabBris.size() > 1
				&& tabSource.size() == 1) {
			if(tabInterrupteursOuvert.size() == 1) {
				System.out.print(trouverChemin(start, tabInterrupteursOuvert.get(0)));
				System.out.print(fermerInterrupteur());
				System.out.print(trouverChemin(tabInterrupteursOuvert.get(0), tabBris.get(0)));
				System.out.print(reparerConducteur());
				versProchainBris();
				System.out.print(trouverChemin(tabBris.get(tabBris.size() - 1), tabInterrupteursOuvert.get(0)));
				System.out.print(ouvrirInterrupteur());
			} else if(tabInterrupteursFerme.size() == 1) {
				System.out.print(trouverChemin(start, tabBris.get(0)));
				System.out.print(reparerConducteur());
				versProchainBris();
				System.out.print(trouverChemin(tabBris.get(tabBris.size() - 1), tabInterrupteursOuvert.get(0)));
				System.out.print(ouvrirInterrupteur());
			}
		}
    }
}
