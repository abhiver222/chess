package chess_elements;

import chess_elements.EmptyTile;
import chess_elements.Piece;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Abhishek on 8/29/2016.
 */
public class Queen extends Piece
{
    public Queen(Color color, int x, int y){
        super(color,x,y);
    }

    @Override
    public ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit) {

        ArrayList<Piece> possibleMoves = new ArrayList<>();

        Color color = this.getTileColor();

        // horizontal
        for(int coorX = 0; coorX < xLimit; coorX++){
            EmptyTile rookMove = new EmptyTile(color,coorX,curY);
            possibleMoves.add(rookMove);
        }

        // vertical
        for(int coorY = 0; coorY < yLimit; coorY++){
            EmptyTile rookMove = new EmptyTile(color,curX,coorY);
            possibleMoves.add(rookMove);
        }

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
        for (int coorX = curX+1, coorY = curY-1; coorX < xLimit && coorY >= 0; coorX++, coorY--){
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
    public boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles) {

        boolean isLegitMove = true;

        if (curX == destX && curY == destY)
            return false;

        Color queenColor = this.getTileColor();

        if (curX == destX && curY == destY)
            isLegitMove = false;

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
        else if (curX == destX){
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
            if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() == queenColor){
                isLegitMove = false;
            }
        }

        return isLegitMove;
    }

    public String getName(){
        return "Queen";
    }
}
