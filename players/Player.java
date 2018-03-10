package chess.players;

import chess.Coordinate;
import chess.board.BoardToTextConverter;
import chess.board.ChessBoard;
import chess.moves.Move;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.tile.Tile;


import java.util.Collection;
import java.util.List;

public abstract class Player
{
    protected ChessBoard board;
    protected King playerKing;
    protected List<Move> legalMoves;
    private List<Move> oppenentMoves;
    private boolean turn;

    Player(ChessBoard board,
	   List<Move> legalMoves,
	   List<Move> oppenentMoves,
	   boolean turn) {

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.oppenentMoves = oppenentMoves;
        this.turn = turn;
    }

    public King getPlayerKing() {
	return playerKing;
    }

    private King establishKing() {
	for (Piece piece: getActivePieces()) {
	    if(piece.getPieceType().isKing()) {
		return (King) piece;
	    }
	}
	return null;
    }

    public abstract List<Piece> getActivePieces();

    public boolean isChecked(){
        board.loadAllMoves(); // TA INTE BORT!!!!!!
	for(Move m :this.oppenentMoves) {
	    if(m.getCord().equals(this.playerKing.getPieceCoordinate())){
	        return true;
	    }
	}
	return false;
    }

    public List<Move> getOppenentMoves() {
	return oppenentMoves;
    }

    public boolean isStalemate(){
        //otestad
        return legalMoves.isEmpty() && !this.isChecked();

    }

    public boolean isCheckMate(){

        return legalMoves.isEmpty() && this.isChecked();
    }


    public void setLegalMoves(final List<Move> legalMoves) {
	this.legalMoves = legalMoves;
    }

    public void setOppenentMoves(final List<Move> oppenentMoves) {
	this.oppenentMoves = oppenentMoves;
    }


    public List<Move> getLegalMoves() {
	return legalMoves;
    }

    public void setTurn(final boolean turn) {
	this.turn = turn;
    }

}
