package chess.tile;

import chess.Coordinate;
import chess.pieces.*;

public abstract class Tile
{

    protected TileType tileType;
    private Coordinate cord;

    public Tile(final Coordinate cord, TileType tileType) {
	this.cord = cord;
	this.tileType = tileType;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public Coordinate getCord() {
	return cord;
    }



    public enum TileType{
        ATTACK_TILE("A"),
	EMPTY_TILE("E"),
	NORMAL_TILE("M"),
	OCCUPIED_TILE("O");


	private final String tileName;

	TileType(String tileName) {
	    this.tileName = tileName;
	}

	@Override
	public String toString() {
	    return this.tileName;
	}

    }

    public TileType getTileType() {
	return tileType;
    }
}
