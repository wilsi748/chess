package chess.pieces;

import chess.Alliance;
import chess.Coordinate;
import chess.board.*;
import chess.moves.Move;
import chess.moves.NormalMove;
import chess.moves.TakeMove;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece
{

    private final static Coordinate[] CANDIDATE_MOVES = { new Coordinate(0, 1),
        						    new Coordinate(0, -1),
        						    new Coordinate(-1, 0),
        						    new Coordinate(1, 0)};



    public Rook(final Coordinate pieceCoordinate, final Alliance pieceAlliance) {
	super(PieceType.ROOK, pieceCoordinate, pieceAlliance, CANDIDATE_MOVES);
    }

    @Override public String toString() {
	return "Rook{" + "pieceCoordinate=" + pieceCoordinate + ", pieceAlliance=" + pieceAlliance + '}';
    }
}
