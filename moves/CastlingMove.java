package chess.moves;

import chess.Coordinate;
import chess.board.ChessBoard;
import chess.pieces.Piece;

public class CastlingMove extends Move
{
    private Piece castlingRook;

    public CastlingMove(ChessBoard board, Piece piece, Coordinate destCord, Piece castlingRook, MoveType moveType, boolean firstMove) {
	super(board, piece, destCord, moveType, firstMove);
	this.castlingRook = castlingRook;

    }



    public Piece getCastlingRook() {
	return castlingRook;
    }
}

