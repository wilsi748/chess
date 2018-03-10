package chess.board;

import chess.Alliance;
import chess.BoardListener;
import chess.Coordinate;
import chess.moves.CastlingMove;
import chess.moves.Move;
import chess.moves.TakeMove;
import chess.pieces.*;
import chess.players.Player;
import chess.tile.EmptyTile;
import chess.tile.OccupiedTile;
import chess.tile.Tile;
import chess.players.BlackPlayer;
import chess.players.WhitePlayer;

import java.util.ArrayList;
import java.util.List;


import static chess.Alliance.BLACK;
import static chess.Alliance.WHITE;
import static chess.moves.Move.MoveType.*;
import static chess.tile.Tile.TileType.*;

public class ChessBoard
{

    private boolean checkMate;


    private boolean whiteTurn = true;
    private boolean blackTurn = false;

    private List<Move> listOfMadeMoves;

    private Piece currentPiece;
    private List<Move> currentMoves;
    private boolean hasMoved = false;

    private boolean isPromote = false;
    private Piece promote;

    private Coordinate enpassant;
    private boolean isEnpassant;
    private Piece enpassantPiece;

    private List<Piece> whitePieces;
    private List<Piece> blackPieces;

    private boolean gameOver;
    private List<BoardListener> boardListener = new ArrayList<>();

    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;

    private List <Move> whiteStandardLegalMoves;
    private List <Move> blackStandardLegalMoves;

    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private Tile[][] squares;

    public ChessBoard() {
        this.checkMate = false;
        this.gameOver = false;
        this.squares = new Tile[HEIGHT][WIDTH];
        this.listOfMadeMoves = new ArrayList<>();
        setBoard();

        this.whitePieces = calculateActivePieces(this.squares, WHITE);
        this.blackPieces = calculateActivePieces(this.squares, BLACK);

        this.whiteStandardLegalMoves = allMoves(this.whitePieces);
        this.blackStandardLegalMoves = allMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves, !whiteTurn);
        this.blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves, !blackTurn);


        //loadAllMoves();
    }



    public void loadActivePieces(){
        this.whitePieces = calculateActivePieces(this.squares, WHITE);
        this.blackPieces = calculateActivePieces(this.squares, BLACK);
    }

    public void loadLegalMoves(){
        Player currentPlayer = getCurrentPlayer();
        List<Piece> currentPieces;
        if(currentPlayer.equals(whitePlayer)){
            currentPieces = whitePieces;
        }else{
            currentPieces = blackPieces;
        }
        currentPlayer.setLegalMoves(calculateLegalMoves(currentPieces));

        /*
        if(this.calculateLegalMoves(this.whitePieces).isEmpty()){
            System.out.println("WHITE CHECKMATE");
            checkMate = true;
        }else if(this.calculateLegalMoves(this.blackPieces).isEmpty()){
            System.out.println("BLACK CHECKMATE");
            checkMate = true;

        }
        */
    }

    public void loadAllMoves(){

        this.whiteStandardLegalMoves = allMoves(this.whitePieces);
        this.blackStandardLegalMoves = allMoves(this.blackPieces);

        this.whitePlayer.setLegalMoves(this.whiteStandardLegalMoves);
        this.blackPlayer.setLegalMoves(this.blackStandardLegalMoves);

        this.whitePlayer.setOppenentMoves(this.blackStandardLegalMoves);
        this.blackPlayer.setOppenentMoves(this.whiteStandardLegalMoves);

    }

    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    private List<Move> calculateLegalMoves(List<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();

        for (Piece piece: pieces) {
            System.out.println(piece.calculateLegalMoves(this));
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return legalMoves;
    }

    private List<Move> allMoves(List<Piece> pieces) {
        List<Move> legalMoves = new ArrayList<>();
        for (Piece piece: pieces) {
            legalMoves.addAll(piece.allMoves(this));
        }
        return legalMoves;
    }

    private List<Piece> calculateActivePieces(Tile[][] squares, final Alliance alliance) {
        List<Piece> activePieces = new ArrayList<>();

        for (Tile[] tiles: squares) {
            for(Tile tile : tiles) {
                if (tile.isOccupied()) {
                    Piece piece = tile.getPiece();

                    if (piece.getAlliance() == alliance) {
                        activePieces.add(piece);
                    }
                }
            }
        }
        return activePieces;
    }

    public void setBoard(){
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x,y);
                Alliance alliance;
                if(y==0||y==1) {
                    alliance = BLACK;
                }else{
                    alliance = WHITE;
                }

                if(y==1 || y==6) {
                    placePiece(new Pawn(coordinate, alliance));
                }else if(y==0 || y==7) {
                    if (x == 0 || x == 7) {
                        placePiece(new Rook(coordinate, alliance));
                    } else if (x == 1 || x == 6) {
                        placePiece(new Knight(coordinate, alliance));
                    } else if (x == 2 || x == 5) {
                        placePiece(new Bishop(coordinate, alliance));
                    }else if (x == 3) {
                        placePiece( new Queen(coordinate,alliance));
                    }else{
                        placePiece( new King(coordinate,alliance));
                    }
                }else {
                    squares[y][x] = new EmptyTile(coordinate, EMPTY_TILE);
                }

            }
        }
    }

    public Tile getTile(Coordinate cord){
        return squares[cord.getY()][cord.getX()];
    }

    public Piece getPieceAt(Tile t){
        return t.getPiece();
    }

    public void placeSelectTile(Move move){

        Coordinate cord = move.getCord();
        Tile t = squares[cord.getY()][cord.getX()];

        if(move.getMoveType() == TAKE){
            squares[cord.getY()][cord.getX()] = new OccupiedTile(cord, t.getPiece(), ATTACK_TILE);
        }else{
            squares[cord.getY()][cord.getX()] = new EmptyTile(cord, NORMAL_TILE);
        }

    }

    public void replaceTile(Coordinate cord){
        Tile t = squares[cord.getY()][cord.getX()];
        if(t.isOccupied() && t.getPiece() !=null){
            placeOccupiedTile(cord,t.getPiece());
        }else{
            placeEmptyTile(cord);
        }

    }


    public void resetSelectTile(){

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Coordinate cord = new Coordinate(x, y);
                Tile t = getTile(cord);
                if (t.getTileType() == NORMAL_TILE || t.getTileType() == ATTACK_TILE) {
                    replaceTile(cord);
                }
            }
        }

    }

    public void placePiece(Piece p){
        Coordinate cord = p.getPieceCoordinate();
        squares[cord.getY()][cord.getX()] = new OccupiedTile(cord, p, OCCUPIED_TILE);
    }

    public void newGame(){
    	//TODO add reset of time and everything
        setBoard();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void tick(){
        //System.out.println(checkMate);
        notifyListeners();
    }


    public void addBoardListener(BoardListener bl){
        boardListener.add(bl);
    }

    private void notifyListeners(){
        for(BoardListener boardList: boardListener){
            boardList.boardChanged();
        }
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(final Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void placeEmptyTile(Coordinate cord){
        squares[cord.getY()][cord.getX()] = new EmptyTile(cord, EMPTY_TILE);
    }


    public void placeOccupiedTile(Coordinate cord, Piece p){
        squares[cord.getY()][cord.getX()] = new OccupiedTile(cord,p , OCCUPIED_TILE);
    }

    public void movePiece(Move m){
        Piece p = m.getPiece();
        Coordinate oldCord = p.getPieceCoordinate();
        p.setOldPieceCoordinate(oldCord);
        placeEmptyTile(oldCord);
        Coordinate destCord = m.getCord();
        p.setPieceCoordinate(destCord);
        placeOccupiedTile(destCord,p);

        if (m.getMoveType() == TAKE ){
            TakeMove tm = (TakeMove)m;

            addListOfMadeMoves(tm);
            loadActivePieces();

        }else if(m.getMoveType() == ENPASSANT){
            TakeMove tm = (TakeMove)m;
            Piece takenPiece = tm.getTakenPiece();
            placeEmptyTile(takenPiece.getPieceCoordinate());

            addListOfMadeMoves(tm);
            loadActivePieces();
        }

        else if(m.getMoveType() == QUEEN_SIDE_CASTLING || m.getMoveType() == KING_SIDE_CASTLING){

            placeOccupiedTile(destCord, p);
            CastlingMove cm = (CastlingMove)m;

            Piece rookPiece = cm.getCastlingRook();
            Coordinate rookCord = rookPiece.getPieceCoordinate();

            rookPiece.setOldPieceCoordinate(rookCord);
            placeEmptyTile(rookCord);
            Coordinate castlingCord;

            if(m.getMoveType() == QUEEN_SIDE_CASTLING) {
                castlingCord = new Coordinate(3, oldCord.getY());
            }else{
                castlingCord = new Coordinate(5,oldCord.getY());
            }
            rookPiece.setPieceCoordinate(castlingCord);
            placeOccupiedTile(castlingCord, rookPiece);
            addListOfMadeMoves(cm);

        }else{
            placeOccupiedTile(destCord, p);
            addListOfMadeMoves(m);
        }

        blackTurn = !blackTurn;
        whiteTurn = !whiteTurn;
    }

    public Coordinate getEnpassant() {
        return enpassant;
    }

    public void setEnpassant(final Coordinate enpassant) {
        this.enpassant = enpassant;
    }

    public boolean isEnpassant() {
        return isEnpassant;
    }

    public void setIsEnpassant(final boolean enpassant) {
        isEnpassant = enpassant;
    }

    public Piece getEnpassantPiece() {
        return enpassantPiece;
    }

    public void setEnpassantPiece(final Piece enpassantPiece) {
        this.enpassantPiece = enpassantPiece;
    }


    public boolean isPromote() {
        return isPromote;
    }

    public void setIsPromote(final boolean promote) {
        isPromote = promote;
    }

    public Piece getPromote() {
        return promote;
    }

    public void setPromote(final Piece promote) {
        this.promote = promote;
    }



    public List<Move> getListOfMadeMoves() {
        return listOfMadeMoves;
    }

    public void addListOfMadeMoves(Move madeMove) {
        this.listOfMadeMoves.add(madeMove);
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }

    public List<Move> getWhiteStandardLegalMoves() {
        return whiteStandardLegalMoves;
    }

    public List<Move> getBlackStandardLegalMoves() {
        return blackStandardLegalMoves;
    }


    public Alliance getTurn(){
        if(whiteTurn){
            return WHITE;
        }else{
            return BLACK;
        }
    }

    public Player getCurrentPlayer(){
        if (blackTurn){
            return blackPlayer;
        }else{
            return whitePlayer;
        }
    }

    public Player getOpponentPlayer(){
        if(!blackTurn){
            return blackPlayer;
        }else{
            return whitePlayer;
        }
    }

    public void playerUndo(){
        undoMove();
        changeTurn();
    }

    public void undoMove(){

        if(!listOfMadeMoves.isEmpty()) {

            checkMate = false;
            Move m = listOfMadeMoves.get(listOfMadeMoves.size() - 1);
            listOfMadeMoves.remove(m);


            Piece piece = m.getPiece();
            Coordinate oldCord = piece.getOldPieceCoordinate();

            piece.setPieceCoordinate(oldCord);
            piece.setFirstMove(m.isFirstMove());
            placeOccupiedTile(oldCord, piece);
            placeEmptyTile(m.getCord());

            currentPiece = piece;

            if (m.getMoveType() == TAKE || m.getMoveType() == ENPASSANT) {

                TakeMove tm = (TakeMove) m;
                Piece takenPiece = tm.getTakenPiece();
                Coordinate takenCord = takenPiece.getPieceCoordinate();
                placeOccupiedTile(takenCord, takenPiece);

            }

            else if (m.getMoveType() == QUEEN_SIDE_CASTLING || m.getMoveType() == KING_SIDE_CASTLING) {

                CastlingMove cm = (CastlingMove) m;

                Piece rookPiece = cm.getCastlingRook();
                Coordinate rookOldCord = rookPiece.getOldPieceCoordinate();

                placeEmptyTile(rookPiece.getPieceCoordinate());
                rookPiece.setPieceCoordinate(rookOldCord);
                placeOccupiedTile(rookOldCord, rookPiece);

            }

            blackTurn = !blackTurn;
            whiteTurn = !whiteTurn;
        }
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(final boolean checkMate) {
        this.checkMate = checkMate;
    }


    public void promotePawn(int xCord){
        Piece promPiece = promote;
        Piece newPiece;
        if (xCord == 2) {
            newPiece = new Rook(promPiece.getPieceCoordinate(), promPiece.getAlliance());
        } else if (xCord == 3) {
            newPiece = new Knight(promPiece.getPieceCoordinate(), promPiece.getAlliance());
        } else if (xCord == 4) {
            newPiece = new Bishop(promPiece.getPieceCoordinate(), promPiece.getAlliance());
        } else {
            newPiece = new Queen(promPiece.getPieceCoordinate(), promPiece.getAlliance());
        }
        placePiece(newPiece);
        isPromote = false;
        loadActivePieces();
    }

    public void changeTurn(){
        loadActivePieces();
        loadAllMoves();    //Hämtar alla moves för båda
        loadLegalMoves(); //räkna ut alla lagliga moves som currentPlayer har.
        Player currentPlayer = getCurrentPlayer();
        System.out.println("changeTurn LegalMoves: " + getCurrentPlayer().getLegalMoves() + "\n" + "*************************************");


        if(currentPlayer.isChecked()){
            if(currentPlayer.isCheckMate()) {
                this.checkMate = true;
                //this.gameOver();
            }else if(currentPlayer.isStalemate()){
                //kod för oavgjort
            }
        //}else {//if(this.hasMoveBeenMade()) {
            //changePlayer();
        }

    }


}
