package chess_elements; /**
 * Created by Abhishek on 8/29/2016.
 */

import java.awt.Color;
import java.util.ArrayList;

public class King extends Piece {
    public King(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {

        ArrayList<Piece> possibleMoves = new ArrayList<>();

        Color color = this.getTileColor();

        // if going up
        if (curY > 0){
            if (curX > 0){
                EmptyTile kingMove = new EmptyTile(color,curX-1,curY-1);
                possibleMoves.add(kingMove);
            }
            if (curX < xLimit -1){
                EmptyTile kingMove = new EmptyTile(color,curX+1,curY-1);
                possibleMoves.add(kingMove);
            }
            EmptyTile kingMove = new EmptyTile(color,curX,curY-1);
            possibleMoves.add(kingMove);
        }

        // if going down
        if (curY < yLimit - 1){
            if (curX > 0){
                EmptyTile kingMove = new EmptyTile(color,curX-1,curY+1);
                possibleMoves.add(kingMove);
            }
            if (curX < xLimit -1){
                EmptyTile kingMove = new EmptyTile(color,curX+1,curY+1);
                possibleMoves.add(kingMove);
            }
            EmptyTile kingMove = new EmptyTile(color,curX,curY+1);
            possibleMoves.add(kingMove);
        }

        // if going left
        if (curX > 0){
            EmptyTile kingMove = new EmptyTile(color,curX-1,curY);
            possibleMoves.add(kingMove);
        }

        if (curX < xLimit - 1){
            EmptyTile kingMove = new EmptyTile(color,curX+1,curY);
            possibleMoves.add(kingMove);
        }
        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {

        boolean isLegitMove = true;

        if (curX == destX && curY == destY)
            return false;

        Piece move = tiles[destX][destY];
        if (move != null && move.getTileColor() == this.getTileColor())
            isLegitMove = false;

        return isLegitMove;
    }

    public String getName(){
        return "King";
    }
}
