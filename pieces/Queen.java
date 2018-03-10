package chess.pieces;

import chess.Alliance;
import chess.Coordinate;
import chess.board.*;
import chess.moves.Move;

import java.util.List;

public class Queen extends Piece
{
    private final static Coordinate[] CANDIDATE_MOVES = { new Coordinate(0, 1),
							    new Coordinate(0, -1),
							    new Coordinate(-1, 0),
							    new Coordinate(1, 0),
							    new Coordinate(1, 1),
							    new Coordinate(1, -1),
							    new Coordinate(-1, 1),
							    new Coordinate(-1, -1)};


    public Queen(final Coordinate pieceCoordinate,  final Alliance pieceAlliance) {
	super(PieceType.QUEEN, pieceCoordinate,  pieceAlliance, CANDIDATE_MOVES);
    }

    @Override public String toString() {
	return "Queen{" + "pieceCoordinate=" + pieceCoordinate + ", pieceAlliance=" + pieceAlliance + '}';
    }
}
