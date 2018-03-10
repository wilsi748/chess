package chess.moves;

import chess.Coordinate;
import chess.board.ChessBoard;
import chess.pieces.Piece;

import java.util.Objects;

public abstract class Move
{
	private ChessBoard board;
	private Piece piece;
	private Coordinate cord;
	private final MoveType moveType;
	private boolean firstMove;

    public Move(ChessBoard board, Piece piece, Coordinate cord, MoveType moveType, boolean firstMove) {
	this.board = board;
	this.piece = piece;
	this.cord = cord;
	this.moveType = moveType;
	this.firstMove = firstMove;
    }

    @Override public boolean equals(final Object o) {
	if (this == o) return true;
	if (o == null || getClass() != o.getClass()) return false;
	final Move move = (Move) o;
	return firstMove == move.firstMove && Objects.equals(board, move.board) && Objects.equals(piece, move.piece) &&
	       Objects.equals(cord, move.cord) && moveType == move.moveType;
    }

    @Override public int hashCode() {

	return Objects.hash(board, piece, cord, moveType, firstMove);
    }

    public Piece getPiece() {
	return piece;
    }

    public Coordinate getCord() {
	return cord;
    }

    @Override public String toString() {
	return "Move{" + "board=" + board + ", piece=" + piece + ", cord=" + cord +" type: "+ moveType + '}';
    }

    public enum MoveType{
        TAKE,
	NORMAL,
	QUEEN_SIDE_CASTLING,
	KING_SIDE_CASTLING,
	ENPASSANT
    }

    public MoveType getMoveType() {
	return moveType;
    }

    public boolean isFirstMove() {
	return firstMove;
    }
}
