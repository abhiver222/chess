package test_files;

import ChessGame.Error;
import chess_elements.Board;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import chess_elements.*;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Abhishek on 9/4/2016.
 */
public class KingTest {

    public static Board chessBoard;

    @Test
    public void checkInitialPositions() {
        chessBoard = new Board(8, 8, false, new Error(""));

        // check if both kings have been created
        assertTrue(chessBoard.getPiece(4,0) != null);
        assertTrue(chessBoard.getPiece(4,7) != null);

        // check if they're king instances
        assertThat(chessBoard.getPiece(4,0), instanceOf(King.class));
        assertThat(chessBoard.getPiece(4,7), instanceOf(King.class));

        // check if one is black other is white
        assertTrue(chessBoard.getPiece(4,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(4,7).getTileColor() == Color.black);

    }

    @Test
    public void checkIsMovePossible(){
        chessBoard = new Board(8, 8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        King whiteKing = (King) gameTiles[4][0];

        assertFalse(whiteKing.isMovePossible(4,0,4,1,gameTiles));
        assertFalse(whiteKing.isMovePossible(4,0,3,0,gameTiles));
        assertFalse(whiteKing.isMovePossible(4,0,5,0,gameTiles));

        King tempWhiteKing = new King(Color.white,4,4);
        King tempBlackKing = new King(Color.black,4,5);

        gameTiles[4][4] = tempWhiteKing;
        gameTiles[4][5] = tempBlackKing;

        assertTrue(whiteKing.isMovePossible(4,4,4,5,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,4,3,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,3,4,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,5,4,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,5,5,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,3,3,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,3,5,gameTiles));
        assertTrue(whiteKing.isMovePossible(4,4,5,3,gameTiles));
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsMovePossible();
    }
}
