package test_files;

import ChessGame.Error;
import chess_elements.Board;
import chess_elements.Pawn;
import chess_elements.Piece;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Abhishek on 9/4/2016.
 */
public class PawnTest {
    public static Board chessBoard;

    @Test
    public void checkInitialPositions() {
        chessBoard = new Board(8, 8, false, new Error(""));

        Piece whitePawn,blackPawn;
        for(int i=0;i<7;i++){
            whitePawn = chessBoard.getPiece(i,1);
            assertTrue(whitePawn != null);
            assertThat(whitePawn, instanceOf(Pawn.class));
            assertTrue(whitePawn.getTileColor() == Color.white);

            blackPawn = chessBoard.getPiece(i,6);
            assertTrue(blackPawn != null);
            assertThat(blackPawn, instanceOf(Pawn.class));
            assertTrue(blackPawn.getTileColor() == Color.black);
        }
    }

    @Test
    public void checkIsMovePossible(){

        chessBoard = new Board(8, 8, false, new Error(""));
        Piece[][] gameTiles = chessBoard.getBoard();
        Pawn pawnLeft = (Pawn) chessBoard.getPiece(0,1);

        assertTrue(pawnLeft.isMovePossible(0,1,0,2,gameTiles)); // move up by one
        assertTrue(pawnLeft.isMovePossible(0,1,0,3,gameTiles)); // move up by two
        assertFalse(pawnLeft.isMovePossible(0,1,1,1,gameTiles)); // move right
        assertFalse(pawnLeft.isMovePossible(0,1,1,2,gameTiles)); // move top right

        Pawn whiteTempPawn = new Pawn(Color.white,4,4);
        Pawn blackTempPawn = new Pawn(Color.black,4,5);

        gameTiles[4][4] = whiteTempPawn;
        gameTiles[4][5] = blackTempPawn;

        assertFalse(whiteTempPawn.isMovePossible(4,4,4,5,gameTiles)); // try to take an opponent

        gameTiles[4][5] = null;
        gameTiles[5][5] = blackTempPawn;

        assertTrue(whiteTempPawn.isMovePossible(4,4,5,5,gameTiles)); // take an opponent on the side
        assertFalse(whiteTempPawn.isMovePossible(4,4,4,6,gameTiles)); // move two spaces
    }

    public void runTests(){
        this.checkInitialPositions();
        this.checkIsMovePossible();
    }
}
