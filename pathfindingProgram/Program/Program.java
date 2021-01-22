package Program;

import Models.Etat;
import Models.Position;
import Path.VerifyPath;
import RepairTeam.Actions;

import java.util.ArrayList;

public class Program {
    /*
        The team doesn't find any damage.
     */
    public static void noDamageFound(ArrayList<Position> bris,
                                     ArrayList<Position> interrupteursOuvert,
                                     ArrayList<Position> interrupteursFerme) {
        if (bris.size() == 0 && interrupteursFerme.size() == 0 && interrupteursOuvert.size() == 0) {
            System.out.println("Aucunes reparations necessaires.");
            System.exit(0);
        }
    }

    /*
        The team doesn't find an interrupter, but still wants to repair the broken cable.
        They are then electrocuted.
     */
    public static void noInterrupterFound(ArrayList<Position> bris,
                                          ArrayList<Position> interrupteursOuvert,
                                          ArrayList<Position> interrupteursFerme) {
        if (bris.size() > 0 && interrupteursFerme.size() == 0 && interrupteursOuvert.size() == 0) {
            System.out.println("L'équipe s'est fait électrocutée!");
            System.exit(0);
        }
    }

    /*
        The team find's an interrupter and a broken cable.
        Outputs the actions and directions to take.
     */
    public static void oneInterrupterOneDamageFound(ArrayList<Etat> tabInterrupteursOuvert,
                                                    ArrayList<Etat> tabInterrupteursFerme,
                                                    Etat start,
                                                    ArrayList<Position> obstacles,
                                                    ArrayList<Etat> tabBris
    ) {
        if (tabInterrupteursOuvert.size() == 1) {
            System.out.print(VerifyPath.trouverChemin(start, tabInterrupteursOuvert.get(0), obstacles));
            System.out.print(Actions.fermerInterrupteur());
            System.out.print(VerifyPath.trouverChemin(tabInterrupteursOuvert.get(0), tabBris.get(0), obstacles));
            System.out.print(Actions.reparerConducteur());
            System.out.print(VerifyPath.trouverChemin(tabBris.get(tabBris.size() - 1), tabInterrupteursOuvert.get(0)
                    , obstacles));
            System.out.print(Actions.ouvrirInterrupteur());
        } else if (tabInterrupteursFerme.size() == 1) {
            System.out.print(VerifyPath.trouverChemin(start, tabBris.get(0), obstacles));
            System.out.print(Actions.reparerConducteur());
            System.out.print(VerifyPath.trouverChemin(tabBris.get(0), tabInterrupteursFerme.get(0), obstacles));
            System.out.print(Actions.ouvrirInterrupteur());
        }
    }

    /*
        This function is called if there are multiple damage points in the world.
        It is planned for version 1.1.
     */
    /*
    public static void oneInterrupterMultipleDamageFound(ArrayList<Etat> tabInterrupteursOuvert,
                                                    ArrayList<Etat> tabInterrupteursFerme,
                                                    Etat start,
                                                    ArrayList<Position> obstacles,
                                                    ArrayList<Etat> tabBris
    )
    {
        if(tabInterrupteursOuvert.size() == 1) {
            System.out.print(VerifyPath.trouverChemin(start, tabInterrupteursOuvert.get(0), obstacles));
            System.out.print(Actions.fermerInterrupteur());
            System.out.print(VerifyPath.trouverChemin(tabInterrupteursOuvert.get(0), tabBris.get(0), obstacles));
            System.out.print(Actions.reparerConducteur());
            VerifyPath.versProchainBris(obstacles);
            System.out.print(VerifyPath.trouverChemin(tabBris.get(tabBris.size() - 1),
                    tabInterrupteursOuvert.get(0), obstacles));
            System.out.print(Actions.ouvrirInterrupteur());
        } else if(tabInterrupteursFerme.size() == 1) {
            System.out.print(VerifyPath.trouverChemin(start, tabBris.get(0), obstacles));
            System.out.print(Actions.reparerConducteur());
            VerifyPath.versProchainBris(obstacles);
            System.out.print(VerifyPath.trouverChemin(tabBris.get(tabBris.size() - 1),
                    tabInterrupteursOuvert.get(0), obstacles));
            System.out.print(Actions.ouvrirInterrupteur());
        }
    }
     */
}
