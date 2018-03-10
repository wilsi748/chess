package chess.moves;

import chess.Coordinate;
import chess.board.ChessBoard;
import chess.pieces.Piece;

public class NormalMove extends Move
{
    private ChessBoard board;
    private Piece piece;
    private Coordinate destCord;
    private MoveType moveType;


    public NormalMove(ChessBoard board, Piece piece, Coordinate destCord, MoveType moveType, boolean firstMove) {
	super(board, piece, destCord, moveType, firstMove);
    }



}
