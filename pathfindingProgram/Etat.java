public class Etat {

    int xPos, yPos;
	Etat parent;
    double f, g, h;

    public Etat() {}

    public Etat(int xPos, int yPos, Etat parent, double g, double h, double f) {
        this.xPos = xPos;
        this.yPos = yPos;
		this.parent = parent;
		this.f = f;
        this.g = g;
        this.h = h;
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
