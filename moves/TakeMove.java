package chess.moves;

import chess.Coordinate;
import chess.board.ChessBoard;
import chess.pieces.Piece;

public class TakeMove extends Move
{
    private Piece takenPiece;

    public TakeMove(ChessBoard board, Piece piece, Coordinate destCord, Piece takenPiece, MoveType moveType, boolean firstMove) {
	super(board, piece, destCord, moveType, firstMove);
	this.takenPiece = takenPiece;

    }

    public Piece getTakenPiece() {
	return takenPiece;
    }
}
