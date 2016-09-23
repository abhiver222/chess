package chess_elements;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 9/1/2016.
 * an empty tile holder extending piece, used to store x and y coordinates of a tile
 */
public class EmptyTile extends Piece
{
    public EmptyTile(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {
        return false;
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {
        return null;
    }

    public String getName(){
        return "Empty";
    }
}
