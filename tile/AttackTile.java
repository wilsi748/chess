package chess.tile;

import chess.Coordinate;
import chess.pieces.Piece;

public class AttackTile extends Tile
{

    private Piece piece;

    public AttackTile(final Coordinate cord, Piece piece, TileType tileType) {
	super(cord, tileType);
	this.piece = piece;

    }

    @Override public boolean isOccupied() {
	return true;
    }

    @Override public Piece getPiece() {
	return this.piece;
    }

}
