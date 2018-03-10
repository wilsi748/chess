package chess.tile;

import chess.Coordinate;
import chess.pieces.Piece;

public class NormalTile extends Tile
{

    public NormalTile(final Coordinate cord, TileType tileType) {
	super(cord, tileType);
    }

    @Override public boolean isOccupied() {
	return false;
    }

    @Override public Piece getPiece() {
	return null;
    }

}
