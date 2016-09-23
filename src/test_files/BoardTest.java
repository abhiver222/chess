package test_files;

import ChessGame.Error;
import chess_elements.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Abhishek on 9/5/2016.
 */
public class BoardTest {

    public Board chessBoard;

    // moves pieces around too see if normal functions are correct
    @Test
    public void checkInitialMoves(){
        chessBoard = new Board(8,8, false, new Error(""));

        chessBoard.printBoard();

        Piece[][] gameTiles = chessBoard.getBoard();

        // move pawn one step
        //System.out.println("\npawn move to 0,2\n");
        gameTiles[0][1].movePiece(0,2,chessBoard);
        //chessBoard.printBoard();
        // move second pawn two steps
        //System.out.println("\npawn move to 1,3\n");
        gameTiles[1][1].movePiece(1,3,chessBoard);

        //System.out.println("\n\n");
        //chessBoard.printBoard();

        assertTrue(gameTiles[0][1] == null);
        assertTrue(gameTiles[1][1] == null);
        assertThat(gameTiles[0][2],instanceOf(Pawn.class));
        assertThat(gameTiles[1][3],instanceOf(Pawn.class));

        Rook whiteRook = (Rook) chessBoard.getPiece(0,0);

        System.out.println("\nrook move to 1,0\n");
        whiteRook.movePiece(1,0,chessBoard);
        assertThat(gameTiles[0][0],instanceOf(Rook.class));
        assertThat(gameTiles[1][0],instanceOf(Knight.class));

        whiteRook.movePiece(1,1,chessBoard);
        System.out.println("\nrook move to 1,1\n");
        assertThat(gameTiles[0][0],instanceOf(Rook.class));

        System.out.println("\nrook move to 0,1\n");
        whiteRook.movePiece(0,1,chessBoard);
        assertThat(gameTiles[0][1],instanceOf(Rook.class));
        assertTrue(gameTiles[0][0] == null);

//        System.out.println("\none\n");
//        chessBoard.printBoard();

        whiteRook.movePiece(1,1,chessBoard);
        assertThat(gameTiles[1][1],instanceOf(Rook.class));

//        System.out.println("\ntwo\n");
//        chessBoard.printBoard();

        whiteRook.movePiece(1,2,chessBoard);
        whiteRook.movePiece(2,2,chessBoard);

        chessBoard.printBoard();

        assertThat(gameTiles[2][6],instanceOf(Pawn.class));
        whiteRook.movePiece(2,3,chessBoard);
        assertThat(gameTiles[2][3],instanceOf(Rook.class));

        whiteRook.movePiece(2,6,chessBoard);

        chessBoard.printBoard();

        // to check if check and checkmate work
        whiteRook.movePiece(2,7,chessBoard);
        whiteRook.movePiece(3,7,chessBoard);
        whiteRook.movePiece(2,7,chessBoard);
        chessBoard.printBoard();

    }

    public void runTests(){
        checkInitialMoves();
    }

}

