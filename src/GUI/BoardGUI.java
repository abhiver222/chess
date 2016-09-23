package GUI;

import ChessGame.Error;
import ChessGame.Game;
import chess_elements.Board;
import chess_elements.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Abhishek on 9/10/2016.
 * contains all the elements for a chess board
 * screen including JFrame , JPanels etc
 */
public class BoardGUI implements ActionListener{

    public JFrame          screen;
    public JMenuBar        menu;
    public JMenuItem       restart;
    public JMenuItem       giveup;
    public JPanel[][]      panels;
    public ChessBoard      chessBoard;
    public undoInfo        undInf;
    public JMenu           options;
    public JMenuItem       customGame;
    public JMenuItem       undo;
    public final boolean[] playerTurn;
    public boolean         customBoardFlag;
    public Board           board;
    public Error           message;
    public String          name1,name2;
    public int             p1Score;
    public int             p2Score;

    /**
     * constructor to set up a JPanel and a JBoard, sets up sizes and Borders
     */
    public BoardGUI(boolean flag, Error msg, String n1, String n2, int p1Score, int p2Score) {
        this.customBoardFlag = flag;
        this.message = msg;
        this.board = createNewBoard(customBoardFlag, msg);
        this.p1Score = p1Score;
        this.p2Score = p2Score;

        screen = new JFrame();
        undInf = new undoInfo();

        // to keep a tab on which player plays
        // true means white plays
        // to be used by the game class
        playerTurn = new boolean[1];
        playerTurn[0] = true;

        this.name1 = n1;
        this.name2 = n2;

        setNamePanels();

        panels = new JPanel[8][8];
        screen.setSize(1000,1000);

        setMenuItems();
        screen.setJMenuBar(menu);

        setChessBoardScreen();

        createBoard();

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * sets top/bottom panel for player names
     */
    public void setNamePanels(){
        JLabel namePanel1 = new JLabel(name1, SwingConstants.CENTER);
        namePanel1.setPreferredSize(new Dimension(screen.getContentPane().getWidth(),30));
        namePanel1.setFont(namePanel1.getFont().deriveFont(22f));
        screen.add(namePanel1,BorderLayout.NORTH);
        JLabel namePanel2 = new JLabel(name2, SwingConstants.CENTER);
        namePanel2.setPreferredSize(new Dimension(screen.getContentPane().getWidth(),30));
        namePanel2.setFont(namePanel2.getFont().deriveFont(22f));
        screen.add(namePanel2,BorderLayout.SOUTH);
    }

    /**
     * sets up a top menu bar with menu options
     */
    public void setMenuItems(){
        options = new JMenu("Options");
        menu = new JMenuBar();
        menu.setPreferredSize(new Dimension(screen.getWidth(),40));

        // add an undo button
        undo = new JMenuItem("undo last move");
        undo.setFont(undo.getFont().deriveFont(18f));
        undo.addActionListener(e -> {
            this.undInf.undo();
            this.playerTurn[0] = !this.playerTurn[0];
        });

        // add a restart button
        restart = new JMenuItem("Restart Game");
        restart.setFont(restart.getFont().deriveFont(15f));
        restart.addActionListener(e -> {
            int ret = popDialog("will reset complete game, continue?");
            if (ret == 0)
                this.restartGame(customBoardFlag);
        });
        options.add(restart);

        // add a give up button
        giveup = new JMenuItem("Give Up!");
        giveup.setFont(giveup.getFont().deriveFont(15f));
        giveup.addActionListener(e -> {
            int ret = popDialog("Are you sure you want to give up??");
            if (ret == 0)
                giveup();
        });
        options.add(giveup);

        // add a custom game button
        customGame = new JMenuItem("Special Game");
        customGame.setFont(customGame.getFont().deriveFont(15f));
        customGame.addActionListener(e -> {
            int ret = popDialog("This will rest the board, all progress will be lost, continue?\n" +
                    "This functionality adds two new kinds of pieces, Ferz and Wazir\n" +
                    "Ferz is like a Bishop but can only move one step\n" +
                    "Wazir is like a Rook but can only move one step\n");
            if (ret == 0)
                customGame();
        });
        options.add(customGame);

        menu.add(options);
        options.setFont(options.getFont().deriveFont(18f));
        menu.add(undo);
        menu.add(Box.createGlue());

        // add a score view
        String scoreString = this.name1 + " : " + this.p1Score + "  |  " + this.p2Score + " : " + this.name2;
        JLabel scString = new JLabel(scoreString,SwingConstants.CENTER);
        scString.setFont(scString.getFont().deriveFont(18f));
        menu.add(scString);
    }

    /**
     * This function sets up the chessBoard Jframe
     * adds a grid layout, sets size, background etc
     */
    public void setChessBoardScreen(){
        GridLayout cells = new GridLayout(8,8);
        chessBoard = new ChessBoard(cells);
        Dimension dim = new Dimension(950,950);

        EmptyBorder chessOutline = new EmptyBorder(8,8,8,8);
        LineBorder lineCol = new LineBorder(Color.black);
        CompoundBorder chessBorder = new CompoundBorder(chessOutline,lineCol);

        chessBoard.setPreferredSize(dim);
        chessBoard.setBorder(chessBorder);
        chessBoard.setBackground(Color.gray);
    }

    /**
     * Sets the chessboard Jpanel as the main panel for the
     * Screen JFrame
     */
    public void createChessBoardScreen(){
        //screen.setContentPane(chessBoard);
        screen.add(chessBoard);
        screen.setVisible(true);
    }

    /**
     * creates a new instance of the Board class
     * @param custom : to decide whether the board is custom or normal
     *               true for custom, false for normal
     * @param msg : An Error message object to handle GUI messages
     * @return : an instance of the board class
     */
    public Board createNewBoard(boolean custom, Error msg){
        return new Board(8,8,custom,msg);
    }

    /**
     * Restarts the Game, asks for the player names again
     * Master Restart
     * @param custom : type of game, normal or special
     */
    public void restartGame(boolean custom){
        this.board = createNewBoard(custom, new Error(""));
        this.changeIcons();
        this.playerTurn[0] = true;
        screen.dispose();
        Game game = new Game(false, 0, 0, "", "");
        game.runGame();
    }

    /**
     * called to reset the game to be a custom game
     * series. Completely restarts the game
     */
    public void customGame(){
        screen.dispose();
        int curScoreP1 = this.p1Score;
        int curScoreP2 = this.p2Score;
        String name1 = this.name1;
        String name2 = this.name2;
        Game game = new Game(true, curScoreP1, curScoreP2, name1, name2);
        game.runGame();
    }

    /**
     * creates a new board withh updated values for the
     * player scores, increments the opponent players score
     */
    public void giveup(){
        int curScoreP1 = this.p1Score;
        int curScoreP2 = this.p2Score;
        String name1 = this.name1;
        String name2 = this.name2;
        boolean flag = this.customBoardFlag;

            if (playerTurn[0])
                curScoreP2++;
            else
                curScoreP1++;

        screen.dispose();
        Game game = new Game(flag,curScoreP1,curScoreP2,name1,name2);
        game.runGame();
    }

    /**
     * Create a chessboard panel with JPanels
     * alternate with black and white colors and set pieces according to their
     * positions on the current chessboard
     */
    public void createBoard(){
        // make the initial chessBoard
        for (int coorY = 0; coorY < 8; coorY++){
            for (int coorX = 0;coorX < 8; coorX++){
                JPanel tile = new JPanel();
                if ((coorX % 2 == 0 && coorY % 2 == 0) || (coorX % 2 == 1 && coorY % 2 == 1))
                    tile.setBackground(new Color(170, 104, 66));
                else
                    tile.setBackground(new Color(255, 187, 68));
                chessBoard.panels[coorX][coorY] = tile;
                chessBoard.add(tile);
            }
        }
        changeIcons();
    }

    /**
     * Repaints the current JFrame with new icons for
     * the constituent JLables. This function is used to
     * update the GUI for the chessboard
     */
    public void changeIcons(){
        for (int coorY = 0; coorY < 8; coorY++) {
            for (int coorX = 0; coorX < 8; coorX++) {
                JPanel tile = chessBoard.panels[coorX][coorY];
                Piece pos = this.board.getPiece(coorX,coorY);
                tile.removeAll();
                if (pos != null){
                    if(pos.getTileColor() == Color.black) {
                        tile.add(new JLabel(getImage("black", pos.getName())));
                    }
                    else {
                        tile.add(new JLabel(getImage("white", pos.getName())));
                    }
                }
                tile.repaint();
                tile.revalidate();
            }
        }
    }

    /**
     * get path of image for a particular chesspiece
     * @param color : color of the piece
     * @param type : type of the piece
     * @return : imageicon for the piece
     */
    public ImageIcon getImage(String color, String type){
        String path = "\\Images\\" + color + type + ".png";
        ImageIcon imgIcon = null;
        try {
            BufferedImage bImage = ImageIO.read(getClass().getResource(path));
            imgIcon = new ImageIcon(bImage.getScaledInstance(90,90,Image.SCALE_SMOOTH));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imgIcon;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Inner Class to represent a chess board
     * Extends JPanel
     */
    public class ChessBoard extends JPanel{
        public JPanel[][] panels;
        public ChessBoard(GridLayout g){
            super(g);
            panels = new JPanel[8][8];
        }
    }

    /**
     * creates a popup Dialog
     * @param msg : message to show on dialog
     */
    public int popDialog(String msg){
        JPanel msgPane = new JPanel();
        JLabel nameLabel = new JLabel(msg);
        msgPane.add(nameLabel);
        String[] buttonOptions = {"Continue", "cancel"};
        int ret = JOptionPane.showOptionDialog(null,msgPane,"Warning",JOptionPane.DEFAULT_OPTION
                , JOptionPane.WARNING_MESSAGE, null, buttonOptions, buttonOptions[0]);

        return ret;
    }

    /**
     * Inner class to store all the information for performing
     * and undo function. Stores cur coordinates, dest coordinates,
     * cur piece and destination piece
     */
    public class undoInfo{

        public int srcX;
        public int srcY;
        public int dstX;
        public int dstY;
        public Piece srcPc;
        public Piece dstPc;

        public void setSrcX(int srcX) {
            this.srcX = srcX;
        }

        public void setSrcY(int srcY) {
            this.srcY = srcY;
        }

        public void setDstX(int dstX) {
            this.dstX = dstX;
        }

        public void setDstY(int dstY) {
            this.dstY = dstY;
        }

        public void setSrcPc(Piece srcPc) {
            this.srcPc = srcPc;
        }

        public void setDstPc(Piece dstPc) {
            this.dstPc = dstPc;
        }

        public undoInfo(){
            setSrcX(-1);
        }

        public void undo(){
            if (this.srcX == -1)
                return;
            Piece[][] boardTiles = board.getBoard();
            boardTiles[this.srcX][this.srcY] = this.srcPc;
            this.srcPc.setX(this.srcX);
            this.srcPc.setY(this.srcY);
            boardTiles[this.dstX][this.dstY] = this.dstPc;
            if (this.dstPc != null){
                this.dstPc.setX(this.dstX);
                this.dstPc.setY(this.dstY);
            }
            changeIcons();
        }
    }


}
