package chess.pieces;

import chess.Alliance;
import chess.Coordinate;


public class Bishop extends Piece
{

    private final static Coordinate[] CANDIDATE_MOVES = { new Coordinate(1, 1),
    						    new Coordinate(1, -1),
    						    new Coordinate(-1, 1),
    						    new Coordinate(-1, -1),};

    public Bishop(final Coordinate pieceCoordinate, final Alliance pieceAlliance) {
	super(PieceType.BISHOP, pieceCoordinate, pieceAlliance, CANDIDATE_MOVES);
    }

    @Override public String toString() {
	return "Bishop{" + "pieceCoordinate=" + pieceCoordinate + ", pieceAlliance=" + pieceAlliance + '}';
    }

}

