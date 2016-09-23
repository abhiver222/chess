package test_files;

import ChessGame.Error;
import chess_elements.Board;
import chess_elements.King;
import chess_elements.Piece;
import chess_elements.Rook;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abhishek on 9/4/2016.
 */
public class RookTest {
    public static Board chessBoard;

    @Test
    public void checkInitialPositions() {
        chessBoard = new Board(8, 8, false, new Error(""));

        assertTrue(chessBoard.getPiece(0,0) != null);
        assertTrue(chessBoard.getPiece(7,0) != null);
        assertTrue(chessBoard.getPiece(0,7) != null);
        assertTrue(chessBoard.getPiece(7,7) != null);

        assertThat(chessBoard.getPiece(0,0), instanceOf(Rook.class));
        assertThat(chessBoard.getPiece(7,0), instanceOf(Rook.class));
        assertThat(chessBoard.getPiece(0,7), instanceOf(Rook.class));
        assertThat(chessBoard.getPiece(7,7), instanceOf(Rook.class));

        assertTrue(chessBoard.getPiece(0,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(7,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(0,7).getTileColor() == Color.black);
        assertTrue(chessBoard.getPiece(7,7).getTileColor() == Color.black);
    }

    @Test
    public void checkIsMovePossible(){
        chessBoard = new Board(8,8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Rook rookLeft = (Rook) chessBoard.getPiece(0,0);
        assertFalse(rookLeft.isMovePossible(0,0,3,0,gameTiles));
        assertFalse(rookLeft.isMovePossible(0,0,0,3,gameTiles));

        Rook tempBlackRook = new Rook(Color.black,3,3);
        Rook tempWhiteRook = new Rook(Color.white,3,4);

        gameTiles[3][3] = tempBlackRook;
        gameTiles[3][4] = tempWhiteRook;

        assertTrue(tempBlackRook.isMovePossible(3,3,3,4,gameTiles));
        assertTrue(tempBlackRook.isMovePossible(3,3,3,1,gameTiles));
        assertTrue(tempBlackRook.isMovePossible(3,3,4,3,gameTiles));
        assertFalse(tempBlackRook.isMovePossible(3,3,3,5,gameTiles));
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsMovePossible();
    }
}
