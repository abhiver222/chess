/**
 * Created by Abhishek on 9/4/2016.
 */
package test_files;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import ChessGame.Error;
import chess_elements.*;
import org.junit.Test;

import java.awt.*;

public class BishopTest {

    public static Board chessBoard;


    @Test
    public void checkInitialPositions(){
        chessBoard = new Board(8,8,false, new Error(""));

        // check if all four bishops have been created
        assertTrue(chessBoard.getPiece(2,0) != null);
        assertTrue(chessBoard.getPiece(5,0) != null);
        assertTrue(chessBoard.getPiece(2,7) != null);
        assertTrue(chessBoard.getPiece(5,7) != null);

        assertThat(chessBoard.getPiece(2,0),instanceOf(Bishop.class));
        assertThat(chessBoard.getPiece(5,0),instanceOf(Bishop.class));
        assertThat(chessBoard.getPiece(2,7),instanceOf(Bishop.class));
        assertThat(chessBoard.getPiece(5,7),instanceOf(Bishop.class));

        assertTrue(chessBoard.getPiece(2,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(5,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(2,7).getTileColor() == Color.black);
        assertTrue(chessBoard.getPiece(5,7).getTileColor() == Color.black);
    }

    @Test
    public void checkIsMovePossible(){
        chessBoard = new Board(8, 8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Bishop leftBishop = (Bishop) chessBoard.getPiece(2,0);

        assertFalse(leftBishop.isMovePossible(2,0,3,1,gameTiles));
        assertFalse(leftBishop.isMovePossible(2,0,1,1,gameTiles));

        Bishop whiteTempBishop = new Bishop(Color.white,3,3);
        Bishop blackTempBishop = new Bishop(Color.black,4,4);
        gameTiles[3][3] = whiteTempBishop;
        gameTiles[4][4] = blackTempBishop;

        assertTrue(whiteTempBishop.isMovePossible(3,3,4,4,gameTiles));
        assertTrue(whiteTempBishop.isMovePossible(3,3,4,2,gameTiles));
        assertTrue(whiteTempBishop.isMovePossible(3,3,0,6,gameTiles));
        assertTrue(whiteTempBishop.isMovePossible(3,3,2,2,gameTiles));
    }

    @Test
    public void checkPossibleMovesArray(){
        chessBoard = new Board(8, 8, false, new Error(""));
        Bishop testBishop = new Bishop(Color.white,4,4);
        Piece[][] gameTiles = chessBoard.getBoard();

        gameTiles[4][4] = testBishop;

        assertFalse(testBishop.movePiece(0,0,chessBoard));
        assertFalse(testBishop.movePiece(2,3,chessBoard));
        assertFalse(testBishop.movePiece(7,7,chessBoard));
        assertFalse(testBishop.movePiece(10,10,chessBoard));
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsMovePossible();
        this.checkPossibleMovesArray();
    }
}