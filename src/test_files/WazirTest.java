package test_files;

import ChessGame.Error;
import chess_elements.Board;
import chess_elements.Piece;
import chess_elements.Wazir;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abhishek on 9/15/2016.
 */
public class WazirTest {

    Board chessBoard;

    /**
     * check basic moves
     */
    @Test
    public void checkIsMovePossible(){
        chessBoard = new Board(8,8,true, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();

        Wazir WazirLeft = (Wazir) chessBoard.getPiece(0,0);
        assertFalse(WazirLeft.isMovePossible(0,0,3,0,gameTiles));
        assertFalse(WazirLeft.isMovePossible(0,0,0,3,gameTiles));

        Wazir tempBlackWazir = new Wazir(Color.black,3,3);
        Wazir tempWhiteWazir = new Wazir(Color.white,3,4);

        gameTiles[3][3] = tempBlackWazir;
        gameTiles[3][4] = tempWhiteWazir;

        assertTrue(tempBlackWazir.isMovePossible(3,3,3,4,gameTiles));
        assertTrue(tempBlackWazir.isMovePossible(3,3,3,2,gameTiles));
        assertTrue(tempBlackWazir.isMovePossible(3,3,4,3,gameTiles));
        assertFalse(tempBlackWazir.isMovePossible(3,3,3,5,gameTiles));
    }

    public void runTests(){
        checkIsMovePossible();
    }
}
