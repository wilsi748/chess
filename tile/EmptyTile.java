package chess.tile;

import chess.Coordinate;
import chess.pieces.*;

public final class EmptyTile extends Tile
{

	public EmptyTile(Coordinate cord, TileType tileType) {
	    super(cord, tileType);
	}

	@Override public boolean isOccupied() {
	    return false;
	}

	@Override public Piece getPiece() {
	    return null;
	}
    }