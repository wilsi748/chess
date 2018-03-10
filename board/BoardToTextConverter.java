package chess.board;

import chess.Coordinate;
import chess.pieces.*;
import chess.tile.EmptyTile;
import chess.tile.Tile;

import static chess.tile.Tile.TileType.ATTACK_TILE;

/**
* early string representation of the board
*/


public final class BoardToTextConverter
{


    private static final int HEIGHT = 8;
    private static final int WIDTH = 8;

    private BoardToTextConverter() {}

    public static String convertToText(ChessBoard chessBoard){


	StringBuilder builder = new StringBuilder();

	for(int y = 0; y < HEIGHT; y++){

	    for(int x = 0; x < WIDTH; x++){

		Coordinate cord = new Coordinate(x,y);
		Tile t = chessBoard.getTile(cord);
		Piece p = chessBoard.getPieceAt(t);

		if(t.getTileType() == ATTACK_TILE || !t.isOccupied()){
		    builder.append(t.getTileType());
		}else{
		    builder.append(p.getPieceType());
		}
	    }
	    builder.append('\n');
	}
	return builder.toString();
    }

}
