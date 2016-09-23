package chess_elements;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 8/29/2016.
 */
public class Bishop extends Piece
{
    public Bishop(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {

        ArrayList<Piece> possibleMoves = new ArrayList<>();

        Color color = this.getTileColor();
        // get all the tiles on the two diagonals of current position

        // bottom right
        for (int coorX = curX, coorY = curY; coorX < xLimit && coorY < yLimit; coorX++, coorY++){
            EmptyTile bishopMove = new EmptyTile(color,coorX,coorY);
            possibleMoves.add(bishopMove);
        }

        // bottom left
        for (int coorX = curX, coorY = curY; coorX >= 0 && coorY < yLimit; coorX--, coorY++){
            EmptyTile bishopMove = new EmptyTile(color,coorX,coorY);
            possibleMoves.add(bishopMove);
        }

        // top right
        for (int coorX = curX, coorY = curY; coorX < xLimit && coorY >= 0; coorX++, coorY--){
            EmptyTile bishopMove = new EmptyTile(color,coorX,coorY);
            possibleMoves.add(bishopMove);
        }

        // top left
        for (int coorX = curX, coorY = curY; coorX >= 0 && coorY >= 0; coorX--, coorY--){
            EmptyTile bishopMove = new EmptyTile(color,coorX,coorY);
            possibleMoves.add(bishopMove);
        }

        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles)
    {
        boolean isLegitMove = true;

        if (curX == destX && curY == destY)
            return false;

        Color bishopColor = this.getTileColor();

        // if move is to bottom right
        if (curX < destX && curY < destY) {
            for (int coorX = curX + 1, coorY = curY + 1; coorX < destX && coorY < destY; coorX++, coorY++) {
                Piece move = tiles[coorX][coorY];
                if (move != null) {
                    isLegitMove = false;
                    break;
                }
            }
        }

        // if move to bottom left
        if (curX > destX && curY < destY) {
            for (int coorX = curX - 1, coorY = curY + 1; coorX > destX && coorY < destY; coorX--, coorY++) {
                Piece move = tiles[coorX][coorY];
                if (move != null) {
                    isLegitMove = false;
                    break;
                }
            }
        }

        // if move to top right
        if (curX < destX && curY > destY) {
            for (int coorX = curX + 1, coorY = curY - 1; coorX < destX && coorY > destY; coorX++, coorY--) {
                Piece move = tiles[coorX][coorY];
                if (move != null) {
                    isLegitMove = false;
                    break;
                }
            }
        }

        // if move to top left
        if (curX > destX && curY > destY) {
            for (int coorX = curX - 1, coorY = curY - 1; coorX > destX && coorY > destY; coorX--, coorY--) {
                Piece move = tiles[coorX][coorY];
                if (move != null) {
                    isLegitMove = false;
                    break;
                }
            }
        }

        if (Math.abs(destX - curX) == 1 || Math.abs(destY - curY) == 1) {
            if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() == bishopColor) {
                isLegitMove = false;
            }
        }

        return isLegitMove;
    }

    public String getName(){
        return "Bishop";
    }
}
