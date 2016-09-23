package chess_elements;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 9/13/2016.
 * exactly like a rook, only that it can
 * move one step vertically or horizontally
 */
public class Wazir extends Piece{

    public Wazir(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {
        // go up, down, left or right by one step

        ArrayList<Piece> possibleMoves = new ArrayList<>();
        Color color = this.getTileColor();
        // go left
        if (curX > 0) {
            EmptyTile wazirMove = new EmptyTile(color,curX - 1,curY);
            possibleMoves.add(wazirMove);
        }
        // go right
        if (curX < xLimit - 2) {
            EmptyTile wazirMove = new EmptyTile(color,curX + 1,curY);
            possibleMoves.add(wazirMove);
        }
        // go up
        if (curY > 0) {
            EmptyTile wazirMove = new EmptyTile(color,curX,curY - 1);
            possibleMoves.add(wazirMove);
        }
        // go down
        if (curY < yLimit - 2) {
            EmptyTile wazirMove = new EmptyTile(color,curX,curY + 1);
            possibleMoves.add(wazirMove);
        }
        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {

        boolean isLegitMove = false;
        Color wazirColor = this.getTileColor();

        if (Math.abs(destX - curX) > 1 || Math.abs(destY - curY) > 1)
            return false;

        if (curX == destX && curY == destY)
            return isLegitMove;

        if (tiles[destX][destY] == null)
            isLegitMove = true;

        if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() != wazirColor)
            isLegitMove = true;

        return isLegitMove;
    }

    @Override
    public String getName() {
        return "Wazir";
    }
}
