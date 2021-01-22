package Path;

import Models.Position;
import RepairTeam.Actions;
import Algorithm.PathFinding;
import Models.Etat;
import java.util.ArrayList;

public class VerifyPath {
    public static ArrayList<Etat> tabBris = new ArrayList<>();

    /**
     * Affiche le chemin d'un point depart vers un point destination.
     *
     * @param tabEtats une liste contenant tous les chemins visite dans l'algorithme aStar.
     * @param initial l'etat initial/debut
     * @return cheminFinal une liste contenant les directions du chemin
     */
    public static ArrayList<String> afficherChemin(ArrayList<Etat> tabEtats, Etat initial) {
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
                if (n.getParent().getyPos() < n.getyPos()) {
                    chemin.add("S ");
                } else if (n.getParent().getyPos() > n.getyPos()) {
                    chemin.add("N ");
                } else if (n.getParent().getxPos() > n.getxPos()) {
                    chemin.add("W ");
                } else if (n.getParent().getxPos() < n.getxPos()) {
                    chemin.add("E ");
                }
                n = n.getParent();
            } catch (NullPointerException e) {}
        }

        for(int i = chemin.size()-1; i >= 0; i--) {
            cheminFinal.add(chemin.get(i));
        }
        return cheminFinal;
    }

    /**
     * Trouve le chemin d'un interrupteur a un autre et les fermes.
     *
     *@param listeInterrupteurs une liste contenant les interrupteurs.
     */
    public static void versProchainInterrupteursAFerme(ArrayList<Etat> listeInterrupteurs, ArrayList<Position> obstacles) {
        for(int i = 0; i < listeInterrupteurs.size(); i++) {
            try {
                for(String s : PathFinding.aStar(listeInterrupteurs.get(i), listeInterrupteurs.get(i),
                        listeInterrupteurs.get(i + 1), obstacles)) {
                    System.out.print(s);
                }
                Actions.fermerInterrupteur();
            } catch (IndexOutOfBoundsException e) {
            }
        }
    }

    /**
     * Trouve le chemin d'un bris vers un autre.
     */
    public static void versProchainBris(ArrayList<Position> obstacles) {
        for(int i = 0; i < tabBris.size(); i++) {
            try {
                for(String s : PathFinding.aStar(tabBris.get(i), tabBris.get(i), tabBris.get(i + 1), obstacles)) {
                    System.out.print(s);
                }
                System.out.print(Actions.reparerConducteur());
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
    public static String trouverChemin(Etat start, Etat fin, ArrayList<Position> obstacles) {
        String chemin = "";
        try {
            for (String s : PathFinding.aStar(start, start, fin, obstacles)) {
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
    public String trouverCheminConduit(Etat start, Etat n, Etat fin,
                                       ArrayList<Position> obstacles,
                                       ArrayList<Etat> tabInterrupteursOuvert,
                                       ArrayList<Etat> interrupteursPositionsConduit,
                                       ArrayList<Etat> tabBris,
                                       ArrayList<Etat> brisPositionsConduit,
                                       ArrayList<Etat> sourcePositionsConduit,
                                       ArrayList<Etat> tabSource) {
        String chemin = "";

        try {
            for (String s : PathFinding.aStarConduits(start, n, fin,
                    null,
                    obstacles,
                    null,
                    tabInterrupteursOuvert,
                    interrupteursPositionsConduit,
                    tabBris,
                    brisPositionsConduit,
                    sourcePositionsConduit,
                    tabSource)) {
                chemin += s;
            }
        }catch (IndexOutOfBoundsException e) {}

        return chemin;
    }

    /**
     * Verifie si le chemin suit un conduit.
     *
     * @param e l'etat courant.
     * @return estValide la validite du mouvement
     */
    public static boolean estValideSurConduits(Etat e, ArrayList<Position> vide,
                                                       ArrayList<Position> obstacles,
                                                       Position equipe) {
        boolean estValide = true;

        for(Position p : vide) {
            if(e.getxPos() == p.getColonne() && e.getyPos() == p.getLigne()) {
                estValide = false;
                break;
            }
        }

        for(Position p : obstacles) {
            if(e.getxPos() == p.getColonne() && e.getyPos() == p.getLigne()) {
                estValide = false;
                break;
            }
        }

        if(e.getxPos() == equipe.getColonne() && e.getyPos() == equipe.getLigne()) {
            estValide = false;
        }

        return estValide;
    }
}
