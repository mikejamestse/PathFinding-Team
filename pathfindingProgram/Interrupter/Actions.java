package Interrupter;

import Algorithm.PathFinding;
import Models.Etat;

import java.util.ArrayList;

/*
    This class is not used in Version 1.0. It is an extension class if the program supports
    more than one interrupter per "world".
 */
public class Actions {
    /**
     * Trouve l'interrupteur a fermer. Si un seul interrupteur doit etre fermee au lieu
     * d'en fermer plusieurs pour aucunes raisons.
     *
     * @param start l'etat de debut.
     * @param n l'etat courant.
     * @param fin l'etat destination.
     */
    /*
    public Etat trouverInterrupteursAFermer(Etat start, Etat n, Etat fin) {

        PathFinding.aStarConduits(start, n, fin);

        int distance = Math.abs((start.getxPos() - interrupteursPositionsConduit.get(0).getxPos())) +
                Math.abs((start.getyPos() - interrupteursPositionsConduit.get(0).getyPos()));

        Etat interrupteur = interrupteursPositionsConduit.get(0);

        for(int i = 1; i < interrupteursPositionsConduit.size(); i ++) {
            int distance2 = Math.abs((start.getxPos() - interrupteursPositionsConduit.get(i).getxPos())) +
                    Math.abs((start.getyPos() - interrupteursPositionsConduit.get(i).getyPos()));
            if(distance2 < distance) {
                distance = distance2;
                interrupteur = interrupteursPositionsConduit.get(i);
            }
        }
        return interrupteur;
    }
     */

    /*
    public static boolean verifierSiDeuxIntAGerer() {
        boolean deuxInt = false;

        if(sourcePositionsConduit.size() > 1) {
            for (Etat bris : brisPositionsConduit) {
                if ((bris.getxPos() > interrupteursPositionsConduit.get(0).xPos &&
                        bris.getxPos() < interrupteursPositionsConduit.get(interrupteursPositionsConduit.size() - 1).xPos) ||
                        (bris.getyPos() > interrupteursPositionsConduit.get(0).yPos &&
                                bris.getyPos() < interrupteursPositionsConduit.get(interrupteursPositionsConduit.size() - 1).yPos)) {
                    deuxInt = true;
                    break;
                }
            }
        }
        return deuxInt;
    }
     */
}
