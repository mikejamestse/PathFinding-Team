package Convertion;

import Models.Etat;
import Models.Position;

import java.util.ArrayList;

public class Convert {
    /**
     * Convertie des listes de Models.Position en liste de Models.Etat.
     *
     * @param listeDebut liste de Models.Position.
     * @param listeFin liste d'Models.Etat.
     */
    public static void creerListeEtat(ArrayList<Position> listeDebut, ArrayList<Etat> listeFin) {

        for(int i = 0; i < listeDebut.size(); i++) {
            listeFin.add(new Etat(listeDebut.get(i).getColonne(),
                    listeDebut.get(i).getLigne(),
                    null, 0, 0, 0));
        }
    }
}
