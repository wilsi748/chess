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

import static chess.moves.Move.MoveType.NORMAL;
import static chess.moves.Move.MoveType.TAKE;

public class Knight extends Piece
{
    private final static Coordinate[] CANDIDATE_MOVES = { new Coordinate(2, -1),
						    new Coordinate(1, -2),
						    new Coordinate(2, 1),
						    new Coordinate(1, 2),
						    new Coordinate(-1, 2),
						    new Coordinate(-2, 1),
						    new Coordinate(-2, -1),
						    new Coordinate(-1, -2)};

    public Knight(Coordinate pieceCoordinate,  final Alliance pieceAlliance) {
        super(PieceType.KNIGHT, pieceCoordinate, pieceAlliance, CANDIDATE_MOVES);

    }



    @Override public String toString() {
	return "Knight{" + "piecePosition" + pieceCoordinate +  ", pieceAlliance=" + pieceAlliance + '}';
    }



    @Override public List<Move> allMoves(final ChessBoard chessBoard) {

	List<Move> legalMoves = new ArrayList<>();

	for(final Coordinate candidateMove : CANDIDATE_MOVES) {
	    int destX = this.pieceCoordinate.getX() + candidateMove.getX();
	    int destY = this.pieceCoordinate.getY() + candidateMove.getY();

	    Coordinate destinationCoordinate = new Coordinate(destX, destY);

	    if (destinationCoordinate.isCoordinateOnBoard()) {
	        final Tile destinationTile = chessBoard.getTile(destinationCoordinate);

	        if(!destinationTile.isOccupied()) {
	            legalMoves.add(new NormalMove(chessBoard, this, destinationCoordinate, NORMAL, this.isFirstMove()));
		}else{
	            if(destinationTile.getPiece().getAlliance()!= this.pieceAlliance){
	                legalMoves.add(new TakeMove(chessBoard, this, destinationCoordinate, destinationTile.getPiece(), TAKE, this.isFirstMove()));
		    }
		}
	    }
	}
	return legalMoves;
    }
}


