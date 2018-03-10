package chess.players;

import chess.board.ChessBoard;
import chess.moves.Move;
import chess.pieces.Piece;

import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player
{
    public BlackPlayer(ChessBoard board,
		       List<Move> whiteStandardLegalMoves,
		       List<Move> blackStandardLegalMoves,
		       boolean turn) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves, turn);

    }

    @Override public List<Piece> getActivePieces() {
	return this.board.getBlackPieces();
    }

    @Override public String toString() {
	return "BlackPlayer=" + '}';
    }
}
