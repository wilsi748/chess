package chess.board;

import chess.DrawBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessWindow extends JFrame
{


    private ChessBoard b;
    //private JTextArea jta;

    public ChessWindow(final ChessBoard b) throws HeadlessException, IOException {
	super("Chess");
	this.b = b;

	//JTextArea jta = new JTextArea("name");

	this.setLayout(new BorderLayout());


	menu();

	DrawBoard drawBoard = new DrawBoard(b);
	b.addBoardListener(drawBoard);
	this.add(drawBoard);

	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.pack();
	this.setVisible(true);
    }



    private void menu(){
    	final JMenuBar menuBar = new JMenuBar();

    	final JMenu file = new JMenu("Options");

    	final JMenuItem cont = new JMenuItem("New Game", 'C');
    	file.add(cont);
    	cont.addActionListener(this::newGame);


	final JMenuItem undo = new JMenuItem("Undo last move", 'U');
	file.add(undo);
	undo.addActionListener(this::undoMove);


    	final JMenuItem quit = new JMenuItem("Quit",'Q');
    	file.add(quit);
    	quit.addActionListener(this::quitApp);


    	//cont.addActionListener(contin);
    	menuBar.add(file);


    	final JMenu help = new JMenu("Help");
    	help.add(new JMenuItem("This is Tetris, move the block with the arrows."));
    	menuBar.add(Box.createHorizontalGlue()); // "Glue" fungerar som en fjäder, trycker "help" till höger
    	menuBar.add(help);

    	this.setJMenuBar(menuBar);
    }

    //Needs ActionEvent so it can get called from menu bar
    public void quitApp(ActionEvent e){
	System.exit(0);
}
    //Needs ActionEvent so it can get called from menu bar
    public void newGame(ActionEvent e){
	b.newGame();
    }

    public void undoMove(ActionEvent e){b.undoMove();}

}
