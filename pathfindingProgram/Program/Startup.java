package Program;

import Models.Etat;
import Models.Position;

public class Startup {

    /*
        Initialize the repair team starting state.
        It's x y coordinates is the '*' character in the input's text file.
     */
    public static Etat initializeTeam(Position equipe) {
        return new Etat(equipe.getColonne(), equipe.getLigne(), null, 0, 0, 0);
    }
}
