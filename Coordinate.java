package chess;

public class Coordinate
{
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }



    @Override public String toString() {
	return "Coordinate:" + "( x = " + x + " , y = " + y + ")";
    }

    public Coordinate addCoordinates(Coordinate cord2){
        return new Coordinate(this.x + cord2.x, this.y + cord2.y);
    }

    public boolean isCoordinateOnBoard(){
        return !((this.x <0 || this.x >7) || (this.y <0||this.y>7));
    }

    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }else if(!(other instanceof Coordinate)) {
            return false;
        }
        Coordinate othercord = (Coordinate) other;
        return this.x == othercord.x && this.y == othercord.y;
    }
}
