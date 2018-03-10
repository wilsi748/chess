package chess.board;

import chess.Alliance;
import chess.Coordinate;
import chess.moves.Move;
import chess.pieces.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.io.IOException;

import static chess.board.BoardToTextConverter.convertToText;


public class ChessMain extends JPanel
{
    public static void main(String[] args) throws IOException {
	ChessBoard chessBoard = new ChessBoard();

	Piece p = selectPawn(chessBoard, true);

	Coordinate cord = new Coordinate(1,2);
	Piece p2 = new Pawn(cord, Alliance.WHITE);


	//chessBoard.placePiece(p2);
	// TODO bonden dubble move hotar kungen även fast den inte bör göra det.
	// DONE: schack matt men den är inte flawless kungen förstår inte att den kan röra sig från schack mat


	ChessWindow w = new ChessWindow(chessBoard);

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		// Gå ett steg i spelet!

		if(!chessBoard.isGameOver()){
		    chessBoard.tick();
		}
	    }
	};

	final Timer clockTimer = new Timer(200,doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();

    }



    public static Piece selectRook(ChessBoard chessBoard, boolean black){
	Coordinate cord = new Coordinate(0,0);
	return chessBoard.getPieceAt(chessBoard.getTile(cord));
    }

    public static Piece selectKnight(ChessBoard chessBoard, boolean black){
    	Coordinate cord = new Coordinate(1,0);
    	return chessBoard.getPieceAt(chessBoard.getTile(cord));
    }

    public static Piece selectBishop(ChessBoard chessBoard, boolean black){
	Coordinate cord = new Coordinate(2,0);
	return chessBoard.getPieceAt(chessBoard.getTile(cord));
    }

    public static Piece selectQueen(ChessBoard chessBoard, boolean black){
   	Coordinate cord = new Coordinate(3,0);
   	return chessBoard.getPieceAt(chessBoard.getTile(cord));
   }
    public static Piece selectKing(ChessBoard chessBoard, boolean black){
       	Coordinate cord = new Coordinate(4,0);
       	return chessBoard.getPieceAt(chessBoard.getTile(cord));
   }

    public static Piece selectPawn(ChessBoard chessBoard, boolean black){
	Coordinate cord = new Coordinate(0,1);
	return chessBoard.getPieceAt(chessBoard.getTile(cord));
    }

}
