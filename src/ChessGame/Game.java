package ChessGame;

import GUI.BoardGUI;
import chess_elements.Board;
import chess_elements.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * Created by Abhishek on 9/16/2016.
 * Main controller for the game, creates a new GUI, board and handles all
 * the communication between the GUI [view] and the model [Pieces]
 */
public class Game {

    public JPanel[][] panels;
    public BoardGUI   gui;
    public String     player1name;
    public String     player2name;
    public Error      message;
    public int        p1Score;
    public int        p2Score;

    /**
     * ctor gets all the player info
     * sets up a new GUI, Board and runs the Game
     * loop to start the game
     * @param custom : whether custom board or not
     * @param p1Score : player 1 score
     * @param p2Score : player 2 score
     */
    public Game(boolean custom, int p1Score, int p2Score, String p1Name, String p2Name){
        // create a chessboard
        message = new Error("");

        player1name = p1Name;
        player2name = p2Name;
        // get the names of the players
        if (p1Name.equals("")) {
            player1name = getName(1);
            if (player1name != null && player1name.equals("")) {
                player1name = "Player1";
            }
        }
        if (player1name != null &&  p2Name.equals("")) {
            player2name = getName(2);
            if (player2name.equals("") || player2name.equals(player1name)) {
                player2name = "Player2";
            }
        }

        this.p1Score = p1Score;
        this.p2Score = p2Score;

        gui = new BoardGUI(custom, message, player1name, player2name, p1Score, p2Score);
        this.panels = gui.panels;
        runLoop();
    }

    /**
     * called to set up the chess panel as the
     * main screen in the JFrame
     */
    public void runGame(){
        gui.createChessBoardScreen();
    }

    /**
     * Gets the name from a text field popup
     * Does so by creating a JPanel with a Jlabel
     * @param num : player number to get name for
     * @return : name entered as string
     */
    public String getName(int num){
        JPanel namePane = new JPanel();
        JLabel nameLabel = new JLabel("Enter Player" + num + " name:");
        JTextField nameText = new JTextField(20);
        namePane.add(nameLabel);
        namePane.add(nameText);
        String[] buttonOptions = {"next"};
        int ret = JOptionPane.showOptionDialog(null,namePane,"Name",JOptionPane.NO_OPTION
                , JOptionPane.WARNING_MESSAGE, null, buttonOptions, buttonOptions[0]);
        if (ret == 0){
            return nameText.getText();
        }
        else{
            return null;
        }

    }

    /**
     * main game loop, sets mouseclicklisteners on each
     * JPanel, sets up the logic for each click, moves pieces
     * changes the GUI and sets up error message popups
     */
    public void runLoop(){
        final boolean[] playerTurn = new boolean[1]; // true means white player, else black player
        playerTurn[0] = true;
        final Piece[] curPiece = {null};
        final int[] destX = new int[1];
        final int[] destY = new int[1];
        BoardGUI.undoInfo undInfo = gui.undInf;

        for (int coorX = 0 ; coorX < 8 ; coorX++){
            for (int coorY = 0 ; coorY < 8 ; coorY++){

                JPanel curPanel = gui.chessBoard.panels[coorX][coorY];
                final int[] finalCoorXX = new int[1];
                finalCoorXX[0] = coorX;
                final int[] finalCoorYY = new int[1];
                finalCoorYY[0] = coorY;

                // add a mouslistener to each tile [JPanel]
                curPanel.addMouseListener(new MouseListener() {
                    Board board = gui.board;
                    final int finalCoorX = finalCoorXX[0];
                    final int finalCoorY = finalCoorYY[0];

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        if (isRightMouseButton(e)){
                            curPiece[0] = null;
                            curPanel.setBorder(new EmptyBorder(0,0,0,0));
                        }
                        else{
                            curPanel.setBorder(BorderFactory.createLineBorder(Color.green));
                            if(curPiece[0] == null){
                                // first mouse click
                                curPiece[0] = board.getPiece(finalCoorX, finalCoorY);
                                if (curPiece[0] != null){
                                    undInfo.setSrcX(finalCoorX);
                                    undInfo.setSrcY(finalCoorY);
                                }
                            }
                            else{
                                // second mouse click
                                secondClick();
                            }

                        }
                    }

                    /**
                     * decide if the piece moves
                     * on the second mouse click
                     */
                    public void secondClick(){
                        String msg = "";
                        destX[0] = finalCoorX;
                        destY[0] = finalCoorY;
                        boolean playerTurn = gui.playerTurn[0];
                        undInfo.setDstPc(board.getPiece(destX[0],destY[0]));

                        // alternate between white and black players
                        if(playerTurn) {
                            boolean ret = false;
                            if (curPiece[0].getTileColor() == Color.white) {
                                ret = (curPiece[0].movePiece(destX[0], destY[0], board));
                                gui.playerTurn[0] = !ret;
                            }
                            msg = gui.message.getMsg();
                            if(curPiece[0].getTileColor() != Color.white){
                                msg = "color";
                            }
                        }
                        else if(!playerTurn) {
                            boolean ret = false;
                            if (curPiece[0].getTileColor() == Color.black) {
                                ret = (curPiece[0].movePiece(destX[0], destY[0], board));
                                gui.playerTurn[0] = ret;
                            }
                            msg = gui.message.getMsg();
                            if (curPiece[0].getTileColor() != Color.black){
                                msg = "color";
                            }
                        }

                        undInfo.setDstX(destX[0]);
                        undInfo.setDstY(destY[0]);
                        undInfo.setSrcPc(curPiece[0]);
                        curPiece[0] = null;

                        // check if any error messages and refresh the board
                        checkMsg(msg);
                        gui.message.setMsg("");
                        SwingUtilities.invokeLater(() -> gui.changeIcons());

                        curPanel.removeAll();
                        curPanel.revalidate();
                        curPanel.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        curPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        curPanel.setBorder(new EmptyBorder(0,0,0,0));
                    }
                });
            }
        }
    }

    /**
     * Error Checking waterfall
     * Gets a message string and displays a popup
     * accordingly
     * @param msg : error type
     */
    public void checkMsg(String msg){
        if (msg.equals("color")){
            popDialog("other players turn");
        }
        if (msg.equals("stalemate")){
            popDialog("Game Draw : Stalemate");
            // call function to restart game
        }
        else if(msg.equals("checkself")){
            int ret = popDialogUndo("putting self in check");
            if (ret == 0){
                gui.undInf.undo();
                gui.playerTurn[0] = !gui.playerTurn[0];
            }
        }
        else if (msg.equals("checkmate")){
            popDialog("king in checkmate, Game Over. Press Continue to reset.");
            gui.giveup();
        }
        else if(msg.equals("check")){
            popDialog("king in check");
        }
        else if (msg.equals("invalidmove")){
            popDialog("invalid move");
        }
    }

    /**
     * function to create a popup given a string message
     * Creates a popup by creating a warning JPanel
     * @param msg
     */
    public void popDialog(String msg){
        JPanel msgPane = new JPanel();
        JLabel nameLabel = new JLabel(msg);
        msgPane.add(nameLabel);
        String[] buttonOptions = {"Continue"};
        JOptionPane.showOptionDialog(null,msgPane,"Name",JOptionPane.NO_OPTION
                , JOptionPane.WARNING_MESSAGE, null, buttonOptions, buttonOptions[0]);
    }

    public int popDialogUndo(String msg){
        JPanel msgPane = new JPanel();
        JLabel nameLabel = new JLabel(msg);
        msgPane.add(nameLabel);
        String[] buttonOptions = {"Undo","Die!"};
        int optionSeleccted = JOptionPane.showOptionDialog(null,msgPane,"Name",JOptionPane.NO_OPTION
                , JOptionPane.WARNING_MESSAGE, null, buttonOptions, buttonOptions[0]);
        return optionSeleccted;
    }

    /**
     * main runner program
     * @param args
     */
    public static void main(String[] args){
        Game game = new Game(false,0,0,"","");
        game.runGame();

    }
}
