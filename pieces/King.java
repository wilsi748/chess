package chess.pieces;


import chess.Alliance;
import chess.Coordinate;
import chess.board.*;
import chess.moves.CastlingMove;
import chess.moves.Move;
import chess.moves.NormalMove;
import chess.moves.TakeMove;
import chess.tile.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.Alliance.BLACK;
import static chess.Alliance.WHITE;
import static chess.moves.Move.MoveType.*;
import static java.lang.Math.abs;

public class King extends Piece
{

    private boolean isChecked;

    private final static Coordinate[] CANDIDATE_MOVES = {new Coordinate(0, 1),
    							    new Coordinate(0, -1),
    							    new Coordinate(-1, 0),
    							    new Coordinate(1, 0),
    							    new Coordinate(1, 1),
    							    new Coordinate(1, -1),
    							    new Coordinate(-1, 1),
    							    new Coordinate(-1, -1),
							    new Coordinate(2, 0),
							    new Coordinate(-2, 0)};

    public King(final Coordinate pieceCoordinate, final Alliance pieceAlliance) {
	super(PieceType.KING, pieceCoordinate, pieceAlliance, CANDIDATE_MOVES);
	this.isChecked = false;
    }


    public boolean isChecked() {
	return isChecked;
    }

    public void setChecked(final boolean checked) {
	isChecked = checked;
    }

    @Override public List<Move> allMoves(final ChessBoard chessBoard) {
	List<Move> legalMoves = new ArrayList<>();
	List<Move> enemyMoves;
	if(this.pieceAlliance == BLACK){
	    enemyMoves = chessBoard.getWhiteStandardLegalMoves();
	}else{
	    enemyMoves = chessBoard.getBlackStandardLegalMoves();
	}

	for(final Coordinate candidateMove : CANDIDATE_MOVES) {

	    int pieceX = this.pieceCoordinate.getX();
	    int pieceY = this.pieceCoordinate.getY();

	    int cordXDest = candidateMove.getX();
	    int cordYDest = candidateMove.getY();

	    int destX = pieceX + cordXDest;
	    int destY = pieceY + cordYDest;

	    Coordinate destinationCoordinate = new Coordinate(destX, destY);


	    if (destinationCoordinate.isCoordinateOnBoard()) {
		final Tile destinationTile = chessBoard.getTile(destinationCoordinate);


		if (canKingMove(enemyMoves, destinationCoordinate)) {
		    int deltaX = abs(cordXDest);
		    if(deltaX != 2) {
			if (!destinationTile.isOccupied()) {
			    legalMoves.add(new NormalMove(chessBoard, this, destinationCoordinate, NORMAL, this.isFirstMove()));
			} else {
			    if (destinationTile.getPiece().getAlliance() != this.pieceAlliance) {
				legalMoves.add(new TakeMove(chessBoard, this, destinationCoordinate, destinationTile.getPiece(),
							    TAKE, this.isFirstMove()));
			    }
			}
		    }else {
			//Castling
			if (this.isFirstMove()) {
			    //Check if castling is possible
			    boolean canCastle = true;
			    if (cordXDest > 0) {
				Coordinate cord = new Coordinate(7, pieceY);
				Tile rookTile = chessBoard.getTile(cord);
				Piece rookPiece = rookTile.getPiece();
				if (rookTile.isOccupied() && rookPiece.isFirstMove()) {
				    //check right for occupied tile or threated tile.
				    for (int x = pieceX + 1; x < 7; x++) {
					Coordinate checkCoordinate = new Coordinate(x, pieceY);
					Tile checkTile = chessBoard.getTile(checkCoordinate);
					if (checkTile.isOccupied() || !canKingMove(enemyMoves, checkCoordinate)) { // TODO add check for threated tile
					    canCastle = false;
					}
				    }
				    if (canCastle) {
					//add legal moves,
					legalMoves.add(new CastlingMove(chessBoard, this, destinationCoordinate, rookPiece,
									KING_SIDE_CASTLING, this.isFirstMove()));
				    }
				}

			    } else {
				//check left for occupied tile or threated tile
				Coordinate cord = new Coordinate(0, pieceY);
				Tile rookTile = chessBoard.getTile(cord);
				Piece rookPiece = rookTile.getPiece();

				if (rookTile.isOccupied() && rookPiece.isFirstMove()) {

				    for (int x = pieceX - 1; x > 0; x--) {
					Coordinate checkCoordinate = new Coordinate(x, pieceY);
					Tile checkTile = chessBoard.getTile(checkCoordinate);
					if (checkTile.isOccupied() || !canKingMove(enemyMoves, checkCoordinate)) { // TODO add check for threated tile
					    canCastle = false;
					}
				    }
				    if (canCastle) {
					//add legal moves,
					legalMoves.add(new CastlingMove(chessBoard, this, destinationCoordinate, rookPiece,
									QUEEN_SIDE_CASTLING, this.isFirstMove()));
				    }
				}
			    }

			}
		    }
		}
	    }
	}

	return legalMoves;
    }

    public boolean canKingMove(Collection<Move> enemyMoves, Coordinate destCord) {

	if (enemyMoves != null){
	    for (Move m : enemyMoves) {
		if (m.getCord().equals(destCord)) {
		    return false;
		}
	    }
    	}
	return true;
    }


    @Override public String toString() {
	return "King{" + "pieceCoordinate=" + pieceCoordinate + ", pieceAlliance=" + pieceAlliance + '}';
    }
}

