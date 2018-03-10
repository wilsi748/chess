package chess.players;

import chess.board.ChessBoard;
import chess.moves.Move;
import chess.pieces.Piece;

import java.util.List;

public class WhitePlayer extends Player
{
    public WhitePlayer(ChessBoard board,
		       List<Move> whiteStandardLegalMoves,
		       List<Move> blackStandardLegalMoves,
		       boolean turn) {
	super(board, whiteStandardLegalMoves, blackStandardLegalMoves, turn);
    }

    @Override public List<Piece> getActivePieces() {
	return this.board.getWhitePieces();
    }

    @Override public String toString() {
	return "WhitePlayer{ " + '}';
    }




}



