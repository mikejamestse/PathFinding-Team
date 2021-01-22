package Models;

public class Etat {

    private int xPos, yPos;
    private double f, g, h;
	private Etat parent;

    public Etat() {}

    public Etat(int xPos, int yPos, Etat parent, double g, double h, double f) {
        this.xPos = xPos;
        this.yPos = yPos;
		this.parent = parent;
		this.f = f;
        this.g = g;
        this.h = h;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Etat getParent() {
        return parent;
    }

    public void setParent(Etat parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        boolean same = false;

        if(obj instanceof Etat) {
            same = this.xPos == ((Etat) obj).xPos && this.yPos == ((Etat) obj).yPos;

        }
        return same;
    }
}
