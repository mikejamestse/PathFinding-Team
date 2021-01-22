package MyNode;

import Models.Etat;

import java.util.ArrayList;

public class Action {
    /**
     * Trouve les voisins d'un noeud.
     *
     * @param e etat courant.
     * @return une liste des voisins.
     */
    public static ArrayList<Etat> trouverVoisins(Etat e) {
        ArrayList<Etat> listeVoisins = new ArrayList<>();

        Etat v_nord = new Etat(e.getxPos(), e.getyPos() - 1, null, 0, 0, 0);
        Etat v_sud = new Etat(e.getxPos(), e.getyPos() + 1, null, 0, 0, 0);
        Etat v_ouest = new Etat(e.getxPos() - 1, e.getyPos(), null, 0, 0, 0);
        Etat v_est = new Etat(e.getxPos() + 1, e.getyPos(), null, 0, 0, 0);

        listeVoisins.add(v_nord);
        listeVoisins.add(v_sud);
        listeVoisins.add(v_ouest);
        listeVoisins.add(v_est);

        return listeVoisins;
    }
}
