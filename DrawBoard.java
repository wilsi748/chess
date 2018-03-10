package chess;

import chess.board.ChessBoard;
import chess.moves.Move;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import chess.tile.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import static chess.board.BoardToTextConverter.convertToText;

import static chess.Alliance.WHITE;
import static chess.pieces.Piece.PieceType.PAWN;
import static chess.tile.Tile.TileType.*;
import static java.awt.Color.RED;
import static java.lang.Math.abs;

public class DrawBoard extends JComponent implements BoardListener
{

    private ChessBoard board;
    private BufferedImage lightTile;
    private BufferedImage darkTile;
    private BufferedImage selectedTile;
    private BufferedImage promotebkg;

    private BufferedImage blackBishop;
    private BufferedImage blackRook;
    private BufferedImage blackKnight;
    private BufferedImage blackKing;
    private BufferedImage blackQueen;
    private BufferedImage blackPawn;

    private BufferedImage whiteBishop;
    private BufferedImage whiteRook;
    private BufferedImage whiteKnight;
    private BufferedImage whiteKing;
    private BufferedImage whiteQueen;
    private BufferedImage whitePawn;

    private final static int TILE_SIZE = 50;
    private final static int FONT_SIZE = 20;





    public DrawBoard(ChessBoard board) throws IOException {
        this.board = board;
	loadImages();

	addMouseListener(new MouseAdapter() {
	  public void mousePressed(MouseEvent me) {
	      handlePress(me);
	  }
	});
    }

    public void handlePress(MouseEvent me){


	if(me.getX()/TILE_SIZE <8 && me.getY()/TILE_SIZE <8 ) {
	    Coordinate cord = new Coordinate(me.getX()/TILE_SIZE, me.getY()/TILE_SIZE);
	    Tile t = board.getTile(cord);

	    if (board.isPromote()) {
		int xCord = cord.getX();
		if (cord.getY() == 3 && (xCord > 1 && xCord < 6)) {
		    board.promotePawn(xCord);
		}
	    } else {
		Alliance turn = board.getTurn();

		if (t.getTileType() == EMPTY_TILE || board.getCurrentPiece() == null) {
		    board.resetSelectTile();
		    if (t.isOccupied()) {

			Piece p = t.getPiece();

			if(turn == p.getAlliance()) {
			    board.setCurrentPiece(p);
			    List<Move> currentPieceMoves = p.calculateLegalMoves(board);
			    System.out.println("Drawboard LegalMoves: " + board.getCurrentPlayer().getLegalMoves() + "\n" + "*************************************");
			    /*List<Move> currentPieceMoves = new ArrayList<>();

			    for (Move currentPlayerMove:board.getCurrentPlayer().getLegalMoves()) {
				if(currentPlayerMove.getPiece().getPieceCoordinate().equals(p.getPieceCoordinate())){
				    currentPieceMoves.add(currentPlayerMove);

				}
			    }*/

			    for (Move m : currentPieceMoves) {
				board.placeSelectTile(m);
			    }
			}
		    }
		} else {
		    System.out.println(board.getCurrentPlayer());
		    boolean firstMove = board.getCurrentPiece().isFirstMove();
		    boolean madeMoveThisTurn = false;
		    Piece currentPiece = board.getCurrentPiece();
		    Coordinate pieceCoordinate = currentPiece.getPieceCoordinate();
		    Move useThisMove = null;

		    if (t.getTileType() == ATTACK_TILE) {
			board.resetSelectTile();
			for (Move m : currentPiece.calculateLegalMoves(board)) {
			    if (m.getCord().equals(cord)) {
				useThisMove = m;
			    }
			}
			board.movePiece(useThisMove);
			if (firstMove) {
			    board.getCurrentPiece().setFirstMove(false);
			}
			madeMoveThisTurn = true;

		    } else if (t.getTileType() == NORMAL_TILE) {

			for (Move m : currentPiece.calculateLegalMoves(board)) {
			    if (m.getCord().equals(cord)) {
				useThisMove = m;
			    }
			}
			board.movePiece(useThisMove);
			if (firstMove) {
			    board.getCurrentPiece().setFirstMove(false);
			}
			madeMoveThisTurn = true;

		    }


		    if (madeMoveThisTurn && firstMove) {
			int deltaY = abs(cord.getY() - pieceCoordinate.getY());
			if (deltaY == 2 && currentPiece.getPieceType() == PAWN) {
			    board.setIsEnpassant(true);
			    board.setEnpassant(new Coordinate(pieceCoordinate.getX(),
							      pieceCoordinate.getY() + currentPiece.getAlliance().getDirection()));
			    board.setEnpassantPiece(currentPiece);
			} else {
			    board.setIsEnpassant(false);
			}
		    } else if (madeMoveThisTurn && currentPiece.getPieceType() == PAWN) {
			if (currentPiece.getPieceCoordinate().getY() == 0 || currentPiece.getPieceCoordinate().getY() == 7) {
			    board.setPromote(currentPiece);
			    board.setIsPromote(true);
			}
		    }
		    if(madeMoveThisTurn){
		        // change turn
			board.changeTurn();

		    }
		    board.resetSelectTile();
		    board.setCurrentPiece(null);
		}
	    }
	}//else side panel klicks
    }


    public void loadImages() throws IOException{
	lightTile = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/lightTile.png"));
	darkTile = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/darkTile.png"));
	selectedTile = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/wood2.png"));
	promotebkg = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/promotebkg.png"));

	blackBishop = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BB.png"));
	blackRook = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BR.png"));
	blackKnight = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BN.png"));
	blackKing = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BK.png"));
	blackQueen = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BQ.png"));
	blackPawn = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/BP.png"));

	whiteBishop = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WB.png"));
	whiteRook = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WR.png"));
	whiteKnight = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WN.png"));
	whiteKing = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WK.png"));
	whiteQueen = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WQ.png"));
	whitePawn = ImageIO.read(new File("/home/wilsi748/tddd78-projekt-u1p2-18/src/chess/resources/WP.png"));
    }


    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	BufferedImage prev = null;




	for (int y = 0; y < 8; y++) {
	    for (int x = 0; x < 8; x++) {
		Coordinate cord = new Coordinate(x, y);
		Tile t = board.getTile(cord);
		Piece p = board.getPieceAt(t);


		BufferedImage cur;
		if (prev == lightTile) {
		    cur = darkTile;
		} else {
		    cur = lightTile;
		}
		if (t.getTileType() == EMPTY_TILE || t.getTileType() == OCCUPIED_TILE) {
		    g2d.drawImage(cur, x * TILE_SIZE, y * TILE_SIZE, null);
		} else {
		    g2d.drawImage(selectedTile, x * TILE_SIZE, y * TILE_SIZE, null);
		}

		prev = cur;


		if (t.isOccupied()) {
		    BufferedImage pieceToDraw = null;

		    if (p != null && p.getAlliance() == WHITE) {
			switch (p.getPieceType()) {
			    case PAWN:
				pieceToDraw = whitePawn;
				break;
			    case ROOK:
				pieceToDraw = whiteRook;
				break;
			    case KNIGHT:
				pieceToDraw = whiteKnight;
				break;
			    case BISHOP:
				pieceToDraw = whiteBishop;
				break;
			    case QUEEN:
				pieceToDraw = whiteQueen;
				break;
			    case KING:
				pieceToDraw = whiteKing;
				break;
			    default:
				pieceToDraw = cur;
			}
		    } else if (p != null) {
			switch (p.getPieceType()) {
			    case PAWN:
				pieceToDraw = blackPawn;
				break;
			    case ROOK:
				pieceToDraw = blackRook;
				break;
			    case KNIGHT:
				pieceToDraw = blackKnight;
				break;
			    case BISHOP:
				pieceToDraw = blackBishop;
				break;
			    case QUEEN:
				pieceToDraw = blackQueen;
				break;
			    case KING:
				pieceToDraw = blackKing;
				break;
			    default:
				pieceToDraw = cur;
			}

		    }

		    g2d.drawImage(pieceToDraw, x * TILE_SIZE, y * TILE_SIZE, null);
		}

		if (board.isPromote()) {
		    g2d.drawImage(promotebkg, x * TILE_SIZE, y * TILE_SIZE, null);
		    if (y == 3 && (x > 1 && x < 6)) {
			g2d.drawImage(selectedTile, x * TILE_SIZE, y * TILE_SIZE, null);
			BufferedImage pieceToDraw = null;
			if (board.getPromote().getAlliance() == WHITE) {
			    switch (x) {
				case 2:
				    pieceToDraw = whiteRook;
				    break;
				case 3:
				    pieceToDraw = whiteKnight;
				    break;
				case 4:
				    pieceToDraw = whiteBishop;
				    break;
				case 5:
				    pieceToDraw = whiteQueen;
				    break;
			    }

			} else {
			    switch (x) {
				case 2:
				    pieceToDraw = blackRook;
				    break;
				case 3:
				    pieceToDraw = blackKnight;
				    break;
				case 4:
				    pieceToDraw = blackBishop;
				    break;
				case 5:
				    pieceToDraw = blackQueen;
				    break;
			    }
			}
			g2d.drawImage(pieceToDraw, x * TILE_SIZE, y * TILE_SIZE, null);
		    }
		}


		if (board.getCurrentPlayer().isCheckMate()) {
		    g2d.drawImage(promotebkg, x * TILE_SIZE, y * TILE_SIZE, null);
		    if (y == 3 && x == 2) {
		        write(g2d,x*TILE_SIZE,y*TILE_SIZE,FONT_SIZE,RED,"CHECKMATE");
		    }

		}
	    }


	    if(prev == lightTile){
		prev = darkTile;
	    }else{
		prev = lightTile;
	    }


	}


    }

    @Override public void boardChanged() {
	repaint();
    }




    public void write(Graphics2D g2d, int x, int y, int size, Color color, String text){
    	g2d.setFont(new Font("Monospaced", Font.PLAIN, size));
    	g2d.setColor(color);
    	g2d.drawString(text, x, y);
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension((TILE_SIZE*8*3)/2,TILE_SIZE*8);
    }



}
