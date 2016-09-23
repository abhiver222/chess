package test_files;

import ChessGame.Error;
import chess_elements.Board;
import chess_elements.Knight;
import chess_elements.Piece;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abhishek on 9/4/2016.
 */
public class KnightTest {

    public static Board chessBoard;

    @Test
    public void checkInitialPositions() {
        chessBoard = new Board(8, 8, false, new Error(""));

        assertTrue(chessBoard.getPiece(1,0) != null);
        assertTrue(chessBoard.getPiece(6,0) != null);
        assertTrue(chessBoard.getPiece(1,7) != null);
        assertTrue(chessBoard.getPiece(6,7) != null);

        assertThat(chessBoard.getPiece(1,0), instanceOf(Knight.class));
        assertThat(chessBoard.getPiece(6,0), instanceOf(Knight.class));
        assertThat(chessBoard.getPiece(1,7), instanceOf(Knight.class));
        assertThat(chessBoard.getPiece(6,7), instanceOf(Knight.class));

        assertTrue(chessBoard.getPiece(1,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(6,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(1,7).getTileColor() == Color.black);
        assertTrue(chessBoard.getPiece(6,7).getTileColor() == Color.black);
    }

    @Test
    public void checkIsLegitMove() {
        chessBoard = new Board(8, 8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Knight whiteKnight = (Knight) chessBoard.getPiece(1,0);

        assertTrue(whiteKnight.isMovePossible(1,0,0,2,gameTiles));
        assertTrue(whiteKnight.isMovePossible(1,0,2,2,gameTiles));
        assertFalse(whiteKnight.isMovePossible(1,0,3,1,gameTiles));

        Knight whiteKnightTemp = new Knight(Color.white,3,4);

        assertTrue(whiteKnightTemp.isMovePossible(3,4,4,6,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,3,6,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,2,2,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,2,4,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,1,3,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,1,5,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,6,3,gameTiles));
        assertTrue(whiteKnightTemp.isMovePossible(3,4,6,5,gameTiles));
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsLegitMove();
    }

}
