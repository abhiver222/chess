package chess_elements;

import chess_elements.EmptyTile;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 8/29/2016.
 */
public class Knight extends Piece
{
    public Knight(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {

        ArrayList<Piece> possibleMoves = new ArrayList<>();

        Color color = this.getTileColor();

        // top right
        if(curX < xLimit - 1 && curY >= 2){
            EmptyTile knightMove = new EmptyTile(color,curX+1,curY-2);
            possibleMoves.add(knightMove);
        }

        // top left
        if(curX >= 1 && curY >= 2){
            EmptyTile knightMove = new EmptyTile(color,curX-1,curY-2);
            possibleMoves.add(knightMove);
        }

        // bottom right
        if(curX < xLimit - 1 && curY < yLimit - 2){
            EmptyTile knightMove = new EmptyTile(color,curX+1,curY+2);
            possibleMoves.add(knightMove);
        }

        // bottom left
        if(curX >= 1 && curY < yLimit - 2){
            EmptyTile knightMove = new EmptyTile(color,curX-1,curY+2);
            possibleMoves.add(knightMove);
        }

        // right up
        if(curX < xLimit - 2 && curY >= 1){
            EmptyTile knightMove = new EmptyTile(color,curX+2,curY-1);
            possibleMoves.add(knightMove);
        }

        // right down
        if(curX < xLimit - 2 && curY < yLimit - 1){
            EmptyTile knightMove = new EmptyTile(color,curX+2,curY+1);
            possibleMoves.add(knightMove);
        }

        // left up
        if(curX >= 2 && curY >= 1){
            EmptyTile knightMove = new EmptyTile(color,curX-2,curY-1);
            possibleMoves.add(knightMove);
        }

        // left down
        if(curX >=  2 && curY < yLimit - 1){
            EmptyTile knightMove = new EmptyTile(color,curX-2,curY+1);
            possibleMoves.add(knightMove);
        }

        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {

        boolean isLegitMove = true;

        if (curX == destX && curY == destY)
            return false;

        Piece move = tiles[destX][destY];
        if(move != null && move.getTileColor() == this.getTileColor())
            isLegitMove = false;

        return isLegitMove;
    }

    public String getName(){
        return "Knight";
    }
}
