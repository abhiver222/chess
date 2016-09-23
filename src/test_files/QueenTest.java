package test_files;

import ChessGame.Error;
import chess_elements.Bishop;
import chess_elements.Board;
import chess_elements.Piece;
import chess_elements.Queen;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Abhishek on 9/4/2016.
 */
public class QueenTest {
    public static Board chessBoard;

    @Test
    public void checkInitialPositions() {
        chessBoard = new Board(8, 8, false,new Error(""));

        assertTrue(chessBoard.getPiece(3,0) != null);
        assertTrue(chessBoard.getPiece(3,7) != null);

        assertThat(chessBoard.getPiece(3,0),instanceOf(Queen.class));
        assertThat(chessBoard.getPiece(3,7),instanceOf(Queen.class));

        assertTrue(chessBoard.getPiece(3,0).getTileColor() == Color.white);
        assertTrue(chessBoard.getPiece(3,7).getTileColor() == Color.black);
    }

    @Test
    public void checkIsValidMove(){
        chessBoard = new Board(8, 8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Queen whiteQueen = (Queen) chessBoard.getPiece(3,0);

        assertFalse(whiteQueen.isMovePossible(3,0,4,0,gameTiles));
        assertFalse(whiteQueen.isMovePossible(3,0,3,1,gameTiles));
        assertFalse(whiteQueen.isMovePossible(3,0,2,0,gameTiles));

        Queen whiteTempQueen = new Queen(Color.white,4,4);
        Queen blackTempQueen = new Queen(Color.black,5,5);

        gameTiles[4][4] = whiteTempQueen;
        gameTiles[5][5] = blackTempQueen;

        assertTrue(whiteTempQueen.isMovePossible(4,4,5,5,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,5,4,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,5,3,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,4,3,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,3,3,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,3,4,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,3,5,gameTiles));
        assertTrue(whiteTempQueen.isMovePossible(4,4,4,5,gameTiles));
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsValidMove();
    }
}
