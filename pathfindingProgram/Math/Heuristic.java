package Math;

import Models.Etat;

public class Heuristic {
    /**
     * Calcul d'une fonction heuristique euclidienne.
     *
     * @param e1 etat depart.
     * @param e2 etat destination.
     * @return l'heuristique
     */
    public static double heuristicEuclidian(Etat e1, Etat e2) {
        // utilise le theoreme de pythagore
        e2.setH(Math.sqrt(Math.pow((e2.getxPos() - e1.getxPos()), 2) +
                Math.pow((e2.getyPos() - e1.getyPos()), 2)));
        return e2.getH();
    }

    /**
     * Calcul d'une fonction heuristique avec la distance de Manhattan.
     *
     * @param e1 etat depart.
     * @param e2 etat destination.
     * @return l'heuristique
     */
    public static double heuristicManhattan(Etat e1, Etat e2) {
        e2.setH(Math.abs((e2.getxPos() - e1.getxPos()) +
                (e2.getyPos() - e1.getyPos())));
        return e2.getH();
    }
}