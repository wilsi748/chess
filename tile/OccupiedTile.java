package chess.tile;

import chess.Coordinate;
import chess.pieces.*;

public final class OccupiedTile extends Tile
{

    private Piece pieceOnTile;

    public OccupiedTile(Coordinate cord, Piece pieceOnTile, TileType tileType) {
	super(cord, tileType);
	this.pieceOnTile = pieceOnTile;
    }

    @Override public boolean isOccupied() {
	return true;
    }

    @Override public Piece getPiece() {
	return this.pieceOnTile;
    }


}