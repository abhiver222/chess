package test_files;

import ChessGame.Error;
import chess_elements.Ferz;
import chess_elements.Board;
import chess_elements.Piece;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abhishek on 9/15/2016.
 */
public class FerzTest {

    Board chessBoard;

    /**
     * check basic moves
     */
    @Test
    public void checkIsMovePossible(){
        chessBoard = new Board(8, 8, true, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Ferz leftFerz = (Ferz) chessBoard.getPiece(2,0);

        assertFalse(leftFerz.isMovePossible(2,0,3,1,gameTiles));
        assertFalse(leftFerz.isMovePossible(2,0,1,1,gameTiles));

        Ferz whiteTempFerz = new Ferz(Color.white,3,3);
        Ferz blackTempFerz = new Ferz(Color.black,4,4);
        gameTiles[3][3] = whiteTempFerz;
        gameTiles[4][4] = blackTempFerz;

        assertTrue(whiteTempFerz.isMovePossible(3,3,4,4,gameTiles));
        assertTrue(whiteTempFerz.isMovePossible(3,3,4,2,gameTiles));
        assertTrue(whiteTempFerz.isMovePossible(3,3,2,4,gameTiles));
        assertTrue(whiteTempFerz.isMovePossible(3,3,2,2,gameTiles));
    }

    /**
     * check moves array
     */
    @Test
    public void checkPossibleMovesArray(){
        chessBoard = new Board(8, 8, true, new Error(""));
        Ferz testFerz = new Ferz(Color.white,4,4);
        Piece[][] gameTiles = chessBoard.getBoard();

        gameTiles[4][4] = testFerz;

        assertFalse(testFerz.movePiece(0,0,chessBoard));
        assertFalse(testFerz.movePiece(2,3,chessBoard));
        assertFalse(testFerz.movePiece(7,7,chessBoard));
        assertFalse(testFerz.movePiece(10,10,chessBoard));
    }

    public void runTests(){
        this.checkIsMovePossible();
        this.checkPossibleMovesArray();
    }
}
