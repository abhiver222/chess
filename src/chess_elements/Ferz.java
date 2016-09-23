package chess_elements;

import java.awt.*;
import java.util.ArrayList;

/**
 * exactly like a Bishop, only that it
 * can move on step along each diagonal
 * Created by Abhishek on 9/13/2016.
 */
public class Ferz extends Piece{

    public Ferz(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {
        ArrayList<Piece> possibleMoves = new ArrayList<>();
        Color color = this.getTileColor();

        // top left
        if (curX > 0 && curY > 0){
            EmptyTile ferzMove = new EmptyTile(color,curX - 1, curY - 1);
            possibleMoves.add(ferzMove);
        }
        // top right
        if (curX < xLimit - 1 && curY > 0){
            EmptyTile ferzMove = new EmptyTile(color,curX + 1, curY - 1);
            possibleMoves.add(ferzMove);
        }
        // bottom left
        if (curX > 0 && curY < yLimit - 1){
            EmptyTile ferzMove = new EmptyTile(color,curX - 1, curY + 1);
            possibleMoves.add(ferzMove);
        }
        // bottom right
        if (curX < xLimit - 1 && curY < yLimit - 1){
            EmptyTile ferzMove = new EmptyTile(color,curX + 1, curY + 1);
            possibleMoves.add(ferzMove);
        }

        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {
        boolean isLegitMove = false;

        if (curX == destX && curY == destY)
            return false;

        Color ferzColor = this.getTileColor();

        if (tiles[destX][destY] == null)
            isLegitMove = true;

        if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() != ferzColor)
            isLegitMove = true;

        return isLegitMove;
    }

    @Override
    public String getName() {
        return "Ferz";
    }
}
