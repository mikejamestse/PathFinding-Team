package RepairTeam;

import Models.Etat;
import Models.Position;

import java.util.ArrayList;

public class Actions {
    /**
     * Ouvrir un interrupteur.
     */
    public static String ouvrirInterrupteur() {
        return "1 ";
    }

    /**
     * Fermer un interrupteur.
     */
    public static String fermerInterrupteur() {
        return "0 ";
    }

    /**
     * Reparer un conducteur electrique.
     */
    public static String reparerConducteur() {
        return "R ";
    }

    /**
     * Verifie si un mouvement de l'equipe d'entretien est valide.
     * Par exemple: ils ne peuvent pas sortir de la carte et ne peuvent traverser un obstacles.
     *
     * @param e1 etat courant.
     * @param e2 etat successeur.
     * @return estValide la validite du mouvement
     */
    public static boolean mouvementEstValide(Etat e1, Etat e2, ArrayList<Position> obstacles) {
        boolean estValide = true;

        for(Position p : obstacles) {
            if(e2.getxPos() == p.getColonne() && e2.getyPos() == p.getLigne()) {
                estValide = false;
                break;
            }
        }

        if(estValide && Math.abs(e2.getxPos() - e1.getxPos()) > 1 || Math.abs(e2.getyPos() - e1.getyPos()) > 1) {
            estValide = false;
        }

        if(estValide && e1.equals(e2)) {
            estValide = false;
        }
        return estValide;
    }
}
