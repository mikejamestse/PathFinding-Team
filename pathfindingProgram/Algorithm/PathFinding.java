package Algorithm;

import Models.*;
import Math.*;
import Path.VerifyPath;
import java.util.ArrayList;
import MyNode.Action;
import RepairTeam.Actions;

public class PathFinding {


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
    public static ArrayList<String> aStar(Etat start, Etat n, Etat goal, ArrayList<Position> obstacles) {
        ArrayList<Etat> openList = new ArrayList<>();
        ArrayList<Etat> closedList = new ArrayList<>();

        // ajouter etat inital dans openList
        openList.add(n);

        while(!n.equals(goal)) {
            n = FunctionN.trouverPPF(openList, n, obstacles);

            // si trouverPPF(n) egale au noeud destination, on a termine!
            if(n.equals(goal)) {
                closedList.add(n);
                VerifyPath.afficherChemin(closedList, start);
            } else {
                closedList.add(n);
                openList.remove(n);

                for(Etat v : Action.trouverVoisins(n)) {
                    if (!closedList.contains(v) && Actions.mouvementEstValide(n, v, obstacles)) {
                        v.setG(FunctionN.g(start, v));
                        v.setH(Heuristic.heuristicManhattan(v, goal));
                        v.setF(FunctionN.f(v));
                        if (v.getG() < n.getG() && closedList.contains(v)) {
                            n.setG(v.getG());
                            v.setParent(n);
                        } else if (n.getG() < v.getG() && openList.contains(v)) {
                            v.setG(n.getG());
                            v.setParent(n);
                        } else if (!closedList.contains(v) && !openList.contains(v)) {
                            v.setG(FunctionN.g(start, v));
                            v.setParent(n);
                            openList.add(v);
                        }
                    }
                }
            }
        }
        return VerifyPath.afficherChemin(closedList, start);
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
    public static ArrayList<String> aStarConduits(Etat start, Etat n, Etat goal,
                                                  Position equipe,
                                                  ArrayList<Position> obstacles,
                                                  ArrayList<Position> vide,
                                                  ArrayList<Etat> tabInterrupteursOuvert,
                                                  ArrayList<Etat> interrupteursPositionsConduit,
                                                  ArrayList<Etat> tabBris,
                                                  ArrayList<Etat> brisPositionsConduit,
                                                  ArrayList<Etat> sourcePositionsConduit,
                                                  ArrayList<Etat> tabSource) {
        ArrayList<Etat> openList = new ArrayList<>();
        ArrayList<Etat> closedList = new ArrayList<>();

        // ajouter etat inital dans openList
        openList.add(n);

        while(!n.equals(goal)) {
            n = FunctionN.trouverPPF(openList, n, obstacles);

            // si trouverPPF(n) egale au noeud destination, on a termine!
            if(n.equals(goal)) {
                closedList.add(n);
                VerifyPath.afficherChemin(closedList, start);
            } else {
                closedList.add(n);
                openList.remove(n);
                for(Etat v : Action.trouverVoisins(n)) {
                    if (!closedList.contains(v) && VerifyPath.estValideSurConduits(v,vide, obstacles, equipe)) {
                        v.setG(FunctionN.g(start, v));
                        v.setH(Heuristic.heuristicManhattan(v, goal));
                        v.setF(FunctionN.f(v));
                        if (v.getG() < n.getG() && closedList.contains(v)) {
                            n.setG(v.getG());
                            v.setParent(n);
                        } else if (n.getG() < v.getG() && openList.contains(v)) {
                            v.setG(n.getG());
                            v.setParent(n);
                        } else if (!closedList.contains(v) && !openList.contains(v)) {
                            v.setG(FunctionN.g(start, v));
                            v.setParent(n);
                            openList.add(v);
                        }
                        //trouve les postitions des interrupteurs sur le conduit
                        for(int i = 0; i < tabInterrupteursOuvert.size(); i++) {
                            if(v.getxPos() == tabInterrupteursOuvert.get(i).getxPos() &&
                                    v.getyPos() == tabInterrupteursOuvert.get(i).getyPos()) {
                                interrupteursPositionsConduit.add(v);
                            }
                        }
                        //trouve les postitions des bris sur le conduit
                        for(int i = 0; i < tabBris.size(); i++) {
                            if(v.getxPos() == tabBris.get(i).getxPos() &&
                                    v.getyPos() == tabBris.get(i).getyPos()) {
                                brisPositionsConduit.add(v);
                            }
                        }
                        //trouve les postitions des sources electriques sur un conduit
                        for(int i = 0; i < tabSource.size(); i++) {
                            if(n.getxPos() == tabSource.get(i).getxPos() &&
                                    n.getyPos() == tabSource.get(i).getyPos()) {
                                sourcePositionsConduit.add(n);
                            }
                            if(v.getxPos() == tabSource.get(i).getxPos() &&
                                    v.getyPos() == tabSource.get(i).getyPos()) {
                                sourcePositionsConduit.add(v);
                            }
                        }
                    }
                }
            }
        }
        return VerifyPath.afficherChemin(closedList, start);
    }

}
