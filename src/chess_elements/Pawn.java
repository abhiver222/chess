package chess_elements;

import chess_elements.EmptyTile;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 8/29/2016.
 */
public class Pawn extends Piece
{
    public Pawn(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit)
    {
        ArrayList<Piece> possibleMoves = new ArrayList<>();

        // a chess_elements.Pawn can move one/two steps in the front if first move
        // can move one step only if not first move

        Color whiteColor = Color.white;
        Color blackColor = Color.black;

        // check for the first move

        // white pawn
        int whitePawnInitRow = 1;
        int blackPawnInitRow = 6;
        if(this.getTileColor() == whiteColor){
            // go down
            if(curY < yLimit-1) {
                EmptyTile pawnMoveSingleStraight = new EmptyTile(whiteColor, curX, curY + 1);
                possibleMoves.add(pawnMoveSingleStraight);
            }
            if(curX > 0 && curY < yLimit - 1) {
                EmptyTile pawnMoveSingleLeft = new EmptyTile(whiteColor, curX - 1, curY + 1);
                possibleMoves.add(pawnMoveSingleLeft);
            }
            if(curX < xLimit- 1 && curY < yLimit - 1){
                EmptyTile pawnMoveSingleRight = new EmptyTile(whiteColor, curX + 1, curY + 1);
                possibleMoves.add(pawnMoveSingleRight);
            }
            if(curY == whitePawnInitRow) {
                EmptyTile pawnMoveDouble = new EmptyTile(whiteColor, curX, whitePawnInitRow + 2);
                possibleMoves.add(pawnMoveDouble);
            }
        }
        // black pawn
        else{
            if(curY > 0){
                //System.out.println("added one");
                EmptyTile pawnMoveSingleStraight= new EmptyTile(blackColor, curX, curY - 1);
                possibleMoves.add(pawnMoveSingleStraight);
            }
            if(curX > 0 && curY > 0){
                //System.out.println("added two");
                EmptyTile pawnMoveSingleLeft = new EmptyTile(blackColor, curX - 1, curY - 1);
                possibleMoves.add(pawnMoveSingleLeft);
            }
            if(curX < xLimit-1 && curY > 0){
                //System.out.println("added 3");
                EmptyTile pawnMoveSingleRight = new EmptyTile(blackColor, curX + 1, curY - 1);
                possibleMoves.add(pawnMoveSingleRight);
            }
            if(curY == blackPawnInitRow){
                //System.out.println("added 4");
                EmptyTile pawnMoveDouble = new EmptyTile(blackColor, curX, blackPawnInitRow - 2);
                possibleMoves.add(pawnMoveDouble);
            }
        }

        return possibleMoves;
    }

    @Override
    // make function to get all possible locations while checking for allowed spaces and same color pieces
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles){
        boolean isLegitMove = true;

        Piece move = tiles[destX][destY];

        if (curX == destX && curY == destY)
            return false;

        if(move != null && move.getTileColor() == this.getTileColor())
            isLegitMove = false;

        // if going straight onto another piece
        if(move != null && (destY - curY == 1 || destY - curY == 2) && destX == curX)
            isLegitMove = false;

        // if two spaces forward
        if(destY - curY == 2) {
            for (int coorY = curY + 1; coorY <= destY; coorY++) {
                move = tiles[curX][coorY];
                if (move != null) {
                    isLegitMove = false;
                }
            }
        }

        // if going diagonal
        if (curX != destX && curY != destY){
            move = tiles[destX][destY];
            if (move == null)
                isLegitMove = false;
        }
        return isLegitMove;
    }

    public String getName(){
        return "Pawn";
    }
}
