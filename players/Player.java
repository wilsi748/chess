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


    public void isChecked(){
        board.loadAllMoves();
	boolean check = false;
	for(Move m :this.oppenentMoves) {
	    if(m.getCord().equals(this.playerKing.getPieceCoordinate())){
		System.out.println("checked");
	        check = true;
	    }
	}
	this.playerKing.setChecked(check);
    }

    public List<Move> getOppenentMoves() {
	return oppenentMoves;
    }

    public boolean isStalemate(){
        //otestad
        return legalMoves.isEmpty() && !this.playerKing.isChecked();

    }

    public boolean isCheckMate(){
        //board.loadAllMoves();
        this.isChecked();
	/*for (Move m:this.playerKing.calculateLegalMoves(board)) {
	    Coordinate cord = m.getCord();
	    if(this.playerKing.canKingMove(this.oppenentMoves,cord)){
		System.out.println("king can move");
	        return false;
	    }
	}
	/*
        if(this.playerKing.isChecked()){

	    for (Move m: this.legalMoves) {
	        boolean firstMove = m.getPiece().isFirstMove();
	        board.movePiece(m);
		this.isChecked();
	        if(!this.playerKing.isChecked()){
	            board.undoMove();
	            return false;
		}
		board.undoMove();
	    }
	    return true;

	}*/
        return false;
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
