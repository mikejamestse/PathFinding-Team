package Math;
import Models.Etat;
import Models.Position;
import RepairTeam.Actions;

import java.util.ArrayList;

public class FunctionN {
    /**
     * Calcul la fonction f(n).
     * Formule : f(n) = g(n) + h(n) tel que g = distance reelle
     * et h = fonction heuristique.
     *
     * @param e l'etat courant.
     */
    public static double f(Etat e) {
        e.setF(e.getG() + e.getH());
        return e.getF();
    }

    /**
     * Calcul la fonction g(n).
     *
     * @param e1 l'etat courant.
     */
    public static double g(Etat e1, Etat e2) {
        e2.setG(Math.abs((e2.getxPos() - e1.getxPos()) +
                Math.abs(e2.getyPos() - e1.getyPos())));
        return e2.getG();
    }

    /**
     * Trouver l'etat ayant le plus petit f(n) dans une liste.
     */
    public static Etat trouverPPF(ArrayList<Etat> list, Etat n, ArrayList<Position> obstacles) {
        double min = list.get(0).getF();
        int pos = 0;

        Etat e = list.get(pos);

        if(list.size() == 1) {
            e = list.get(0);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i + 1 != list.size() && list.get(i + 1).getF()
                        < min && Actions.mouvementEstValide(n, list.get(i + 1), obstacles)) {
                    min = list.get(i + 1).getF();
                    pos = i + 1;
                    e = list.get(pos);
                }
            }
        }
        return e;
    }
}
