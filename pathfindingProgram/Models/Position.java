package Models;

public class Position {

    private int ligne = 0, colonne = 0;

    public Position() {}

    public Position(int l, int c){
        ligne = l;
        colonne = c;
    }

    public int getColonne() {
        return colonne;
    }

    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
}