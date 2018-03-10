package chess.pieces;


import chess.Alliance;
import chess.Coordinate;
import chess.board.*;
import chess.moves.Move;
import chess.moves.NormalMove;
import chess.moves.TakeMove;
import chess.tile.Tile;

import java.util.ArrayList;
import java.util.List;

import static chess.moves.Move.MoveType.ENPASSANT;
import static chess.moves.Move.MoveType.NORMAL;
import static chess.moves.Move.MoveType.TAKE;
import static java.lang.Math.abs;

public class Pawn extends Piece
{

    private final static Coordinate[] CANDIDATE_MOVES = {new Coordinate(0, 1),
							    new Coordinate(0,2),
							    new Coordinate(1,1),
							    new Coordinate(-1,1)};


    public Pawn(final Coordinate pieceCoordinate,  final Alliance pieceAlliance) {
	super(PieceType.PAWN, pieceCoordinate, pieceAlliance, CANDIDATE_MOVES);
    }

    @Override public List<Move> allMoves(final ChessBoard chessBoard) {
	List<Move> legalMoves = new ArrayList<>();
	boolean first_clear =  true;

	for(final Coordinate candidateMove : CANDIDATE_MOVES) {
	    int destX = this.pieceCoordinate.getX() + candidateMove.getX();
	    int destY = this.pieceCoordinate.getY() + candidateMove.getY()*this.pieceAlliance.getDirection();
	    int deltaY = abs(candidateMove.getY());
	    int deltaX = abs(candidateMove.getX());

	    Coordinate destinationCoordinate = new Coordinate(destX, destY);

	    if(destinationCoordinate.isCoordinateOnBoard()) {
	        Tile destTile = chessBoard.getTile(destinationCoordinate);

		if(deltaY==1 && deltaX==0) {
		    if(!destTile.isOccupied()) {
			legalMoves.add(new NormalMove(chessBoard, this, destinationCoordinate, NORMAL, this.isFirstMove()));
		    }else{
			first_clear = false;
		    }

		}

		if(this.isFirstMove()&&deltaY==2 && !destTile.isOccupied() && first_clear && deltaX == 0) {

		    legalMoves.add(new NormalMove(chessBoard, this, destinationCoordinate, NORMAL, this.isFirstMove()));
		}

		if(deltaX==1 && destTile.isOccupied() && destTile.getPiece().getAlliance() != this.pieceAlliance) {
			legalMoves.add(new TakeMove(chessBoard, this, destinationCoordinate, destTile.getPiece(), TAKE, this.isFirstMove()));
		}


		//enpassant
		if(deltaX==1 && !destTile.isOccupied() && chessBoard.isEnpassant() && chessBoard.getEnpassant().equals(destinationCoordinate) && chessBoard.getEnpassantPiece().getAlliance() != this.getAlliance()){
		    legalMoves.add(new TakeMove(chessBoard, this, destinationCoordinate, chessBoard.getEnpassantPiece(), ENPASSANT, this.isFirstMove()));
		}

	    }
	}
    	return legalMoves;
    }

    @Override public String toString() {
	return "Pawn{" + "piecePosition=" + pieceCoordinate + ", pieceAlliance=" + pieceAlliance + '}';
    }


}
