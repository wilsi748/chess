package chess.pieces;

import chess.Alliance;
import chess.Coordinate;
import chess.board.ChessBoard;
import chess.moves.Move;
import chess.moves.NormalMove;
import chess.moves.TakeMove;
import chess.players.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static chess.moves.Move.MoveType.NORMAL;
import static chess.moves.Move.MoveType.TAKE;

public abstract class Piece
{
    protected final PieceType pieceType;
    protected Coordinate pieceCoordinate;
    protected List<Coordinate> oldPieceCoordinates;
    protected final Alliance pieceAlliance;
    private final Coordinate[] CANDIDATE_MOVES;
    private boolean firstMove;




    public Piece(final PieceType pieceType,
		 Coordinate pieceCoordinate,
		 final Alliance pieceAlliance,
		 Coordinate[] CANDIDATE_MOVES)
    {
	this.pieceType = pieceType;
	this.pieceCoordinate = pieceCoordinate;

	this.oldPieceCoordinates = new ArrayList<>();
	this.pieceAlliance = pieceAlliance;
	this.CANDIDATE_MOVES = CANDIDATE_MOVES;
	this.firstMove = true;
	setOldPieceCoordinate(pieceCoordinate);
    }

    public void setOldPieceCoordinate(final Coordinate oldPieceCoordinate) {
	this.oldPieceCoordinates.add(oldPieceCoordinate);
    }

    public Coordinate getOldPieceCoordinate() {
    	Coordinate oldPieceCoordinate = oldPieceCoordinates.get(oldPieceCoordinates.size() - 1);
    	oldPieceCoordinates.remove(oldPieceCoordinate);
    	return oldPieceCoordinate;
        }


    public List<Move> allMoves(final ChessBoard chessBoard){
	List<Move> legalMoves = new ArrayList<>();

	for (Coordinate cord : CANDIDATE_MOVES) {
	    Coordinate destinationCoordinate = this.pieceCoordinate;
	    while (destinationCoordinate.isCoordinateOnBoard()) {
		destinationCoordinate = destinationCoordinate.addCoordinates(cord);
		if (destinationCoordinate.isCoordinateOnBoard()) {
		    if (!chessBoard.getTile(destinationCoordinate).isOccupied()) {
			Move normal = new NormalMove(chessBoard, this, destinationCoordinate, NORMAL, firstMove);
			legalMoves.add(normal);
		    } else {
			Piece pieceAtCord = chessBoard.getTile(destinationCoordinate).getPiece();
			if (this.pieceAlliance != pieceAtCord.pieceAlliance) {
			    Move take = new TakeMove(chessBoard, this, destinationCoordinate, pieceAtCord, TAKE, firstMove);
			    legalMoves.add(take);
			}

			break;
		    }

		} else {
		    break;
		}
	    }
	}
	//System.out.println("calcing moves : "+legalMoves);
	return legalMoves;


    }


    public List<Move> calculateLegalMoves(final ChessBoard chessBoard) {
	List<Move> legalMoves = this.allMoves(chessBoard);
	List<Move> toDelete = new ArrayList<>();

	for (Move m:legalMoves) {
	    chessBoard.movePiece(m); //Vit flyttar pjäs
	    if(chessBoard.getOpponentPlayer().isChecked()){//Blir svarts tur som kollar ifall vit är schackad
		//System.out.println("Delete " + m);
	        toDelete.add(m);
	    }else{
		//System.out.println("Tar inte bort " + m);
	    }
	    chessBoard.undoMove();
	}

	legalMoves.removeAll(toDelete);

	return legalMoves;
    }

    public enum PieceType
    {
	PAWN("P") {

	    public boolean isKing() {
	    	return false;
	    }
	},
	KNIGHT("N") {

	    public boolean isKing() {
	        return false;
	    }
	},
	BISHOP("B") {

	    public boolean isKing() {
		return false;
	    }
	},
	KING("K") {

	    public boolean isKing() {
		return true;
	    }
	},
	QUEEN("Q") {

	    public boolean isKing() {
		return false;
	    }
	},
	ROOK("R") {

	    public boolean isKing() {
		return false;
	    }
	};

	private final String pieceName;

	PieceType(String pieceName) {
	    this.pieceName = pieceName;
	}

	@Override
	public String toString() {
	    return this.pieceName;
	}



	public abstract boolean isKing();
    }

    /*public boolean resultInCheck(ChessBoard board, Move m){
        /*List<Move> enemyMoves = board.getEnemyMoves(true);
	board.movePiece(m);
	Player userPlayer = board.getCurrentPlayer();
	King playerKing = userPlayer.getPlayerKing();

	for (Move enemyMove:enemyMoves) {
	    if(playerKing.getPieceCoordinate().equals(enemyMove.getCord())){
		board.undoMove();
	        return true;
	    }
	}

	board.undoMove();
        return false;
    }*/


    public boolean isFirstMove() {
	return firstMove;
    }

    public void setFirstMove(final boolean firstMove) {
	this.firstMove = firstMove;
    }

    public PieceType getPieceType() {
    	return this.pieceType;
    }

    public Alliance getAlliance() {
        return this.pieceAlliance;
    }

    public Coordinate getPieceCoordinate() {
        return this.pieceCoordinate;
    }

    public void setPieceCoordinate(final Coordinate pieceCoordinate) {
	this.pieceCoordinate = pieceCoordinate;
    }



}
