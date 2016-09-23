package chess_elements;

import chess_elements.EmptyTile;
import chess_elements.Piece;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 8/29/2016.
 */
public class Rook extends Piece
{
    public Rook(Color color, int x, int y)
    {
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit){
        // rook can go straight, left or right as many steps as it wants;

        ArrayList<Piece> possibleMoves = new ArrayList<>();

        Color color = this.getTileColor();
        for(int coorX = 0; coorX < xLimit; coorX++){
            EmptyTile rookMove = new EmptyTile(color,coorX,curY);
            possibleMoves.add(rookMove);
        }
        for(int coorY = 0; coorY < yLimit; coorY++){
            EmptyTile rookMove = new EmptyTile(color,curX,coorY);
            possibleMoves.add(rookMove);
        }
        return possibleMoves;
    }

    @Override
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {
        // check if the move if horizontal
        boolean isLegitMove = true;
        Color rookColor = this.getTileColor();

        if (curX == destX && curY == destY)
            return false;

        if (curY == destY) {
            // if move is to go to the left
            if (destX < curX) {
                for (int coorX = curX - 1; coorX > destX; coorX--) {
                    Piece move = tiles[coorX][destY];
                    if (move != null) {
                        isLegitMove = false;
                        break;
                    }
                }
            }
            if (destX > curX) {
                for (int coorX = curX + 1; coorX < destX; coorX++) {
                    Piece move = tiles[coorX][destY];
                    if(move != null){
                        isLegitMove = false;
                        break;
                    }
                }
            }
        }
        // if move is vertical
        else{
            // if move to go up
            if(destY < curY){
                for (int coorY = curY - 1; coorY > destY; coorY--){
                    Piece move = tiles[destX][coorY];
                    if (move != null){
                        isLegitMove = false;
                        break;
                    }
                }
            }
            // if move is to go down
            if (destY > curY){
                for (int coorY = curY + 1; coorY < destY; coorY++){
                    Piece move = tiles[destX][coorY];
                    if (move != null){
                        isLegitMove = false;
                    }
                }
            }
        }
        if (Math.abs(destX - curX) == 1 || Math.abs(destY - curY) == 1){
            if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() == rookColor){
                isLegitMove = false;
            }
        }

        return isLegitMove;
    }

    public String getName(){
        return "Rook";
    }

}
