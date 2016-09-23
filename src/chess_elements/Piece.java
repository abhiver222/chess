/**
 * Created by Abhishek on 8/29/2016.
 */
package chess_elements;
import java.awt.*;
import java.util.ArrayList;


public abstract class Piece
{
    public Color tileColor;
    public int X;
    public int Y;

    public Piece()
    {

    }

    /**
     *
     * @param color : color of the piece
     * @param x : x coordinate
     * @param y : y coordinate
     */
    public Piece(Color color, int x, int y)
    {
        setTileColor(color);
        setX(x);
        setY(y);
    }

    /**
     * finds is a move is possible
     * @param curX,curY : current coordinates
     * @param destX,destY : destination location
     * @param tiles : chessboard tiles
     * @return true if move possible else false
     */
    public abstract boolean isMovePossible(int curX, int curY, int destX, int destY, Piece[][] tiles);

    /**
     * gets a list of all possible moves for a piece
     * @param curX,curY : current coordinates
     * @param xLimit : board width
     * @param yLimit : board heigt
     * @return arraylist of all possible moves
     */
    public abstract ArrayList<Piece> getPossibleMoves(int curX, int curY, int xLimit, int yLimit);

    public abstract String getName();

    public Color getTileColor()
    {
        return tileColor;
    }

    public void setTileColor(Color tileColor)
    {
        this.tileColor = tileColor;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    /**
     * moves piece to destination and does necessary checks
     * @param destX,destY : destination coordinates
     * @param chessBoard : instance of the chessboard
     */
    public boolean movePiece(int destX, int destY, Board chessBoard)
    {
        int curX = this.getX();
        int curY = this.getY();

        // cant move piece to same place
        if (curX == destX && curY == destY) {
            return false;
        }

        Piece[][] tiles = chessBoard.getBoard();

        if(tiles[curX][curY] == null) {
            chessBoard.message.setMsg("samespot");
            return false;
        }

        // add a check to see if the board is in a position which is a stalemate config
        boolean isStaleMate = isStaleMate(chessBoard,this.getTileColor());
        if (isStaleMate){
            chessBoard.message.setMsg("stalemate");
            return false;
        }

        boolean movePossible = canPieceMove(curX,curY,destX,destY,chessBoard);
        if (!movePossible) {
            System.out.println("invalid move " + this.getName());
            chessBoard.message.setMsg("invalidmove");
            return false;
        }

        // add a check for seeing if final piece is of different color
        if (tiles[destX][destY] != null && tiles[destX][destY].getTileColor() == this.tileColor) {
            chessBoard.message.setMsg("invalidmove");
            return false;
        }

        Piece remPiece = tiles[destX][destY];

        if(remPiece != null){
            chessBoard.removePieceFromBoard(remPiece,remPiece.getTileColor());
        }

        tiles[destX][destY] = this;
        this.setX(destX);
        this.setY(destY);
        tiles[curX][curY] = null;

        // check if move will put king in check
        if (this instanceof King) {
                if(this.getTileColor() == Color.white){
                    chessBoard.setWhiteKingPos(this);
                }
                if(this.getTileColor() == Color.black){
                    chessBoard.setBlackKingPos(this);
                }
        }

        boolean isPuttingInCheck = isPuttingSelfInCheck(chessBoard,this.getTileColor());
        if (isPuttingInCheck){
            chessBoard.message.setMsg("checkself");
            return true;
        }

        boolean isInCheckMateOther = isCheckMate(chessBoard,this.getTileColor());
        boolean isInCheckMateSelf = false;
        if (this.tileColor == Color.black) {
            isInCheckMateSelf = isCheckMate(chessBoard, Color.white);
        }
        if (this.tileColor == Color.white){
            isInCheckMateSelf = isCheckMate(chessBoard, Color.black);
        }

        if (isInCheckMateOther || isInCheckMateSelf) {
            chessBoard.message.setMsg("checkmate");
            return true;
        }

        boolean isCheck = isKingInCheck(chessBoard,this.getTileColor());
        if (isCheck) {
            chessBoard.message.setMsg("check");
        }

        return true;

    }

    /**
     * checks if piece can move to a particular spot
     * @param curX,curY : current piece coordinates
     * @param destX,destY : destination coordinates
     * @param chessBoard : instance of the chessBoard
     * @return true if piece can move else false
     */
    public boolean canPieceMove(int curX, int curY, int destX, int destY, Board chessBoard){

        Piece[][] tiles = chessBoard.getBoard();
        boolean isLegitMove = false;
        int xLimit = chessBoard.getGlobalWidth();
        int yLimit = chessBoard.getGlobalHeight();

        ArrayList<Piece> possibleMoves = getPossibleMoves(curX,curY,xLimit,yLimit);

        for(Piece move : possibleMoves){
            if(move.getX() == destX && move.getY() == destY){
                isLegitMove = true;
                break;
            }
        }

        if(!isLegitMove) {
            return false;
        }

        isLegitMove = this.isMovePossible(curX,curY,destX,destY,tiles);
        return isLegitMove;
    }

    /**
     * checks if king of the other team is in checkmate
     * does so by simulating all possible moves of the other teams pieces and then
     * checking if the king is still in check
     * @param chessBoard : instance of the chessboard
     * @param pieceColor : current players color
     * @return true if king in checkmate else false
     */
    public boolean isCheckMate(Board chessBoard, Color pieceColor){
        // if piece color is white then we check for a checkmate on black king
        ArrayList<Piece> opponentPieces;

        if (pieceColor == Color.white){
            opponentPieces = chessBoard.blackPieces;
        }
        else{
            opponentPieces = chessBoard.whitePieces;
        }

        boolean isStillInCheck = pieceSimulator(opponentPieces,chessBoard,pieceColor);

        return isStillInCheck;
    }

    /**
     * Simulates all possible moves of the pieces in the given array and sees if
     * all of the moves put the king in check
     * helper for checkmate and stalemate
     * @param opponentPieces : opponent pieces array
     * @param chessBoard : instance of chessboard
     * @param pieceColor : !color of the king to consider
     * @return : returns true if all positions have the king in check
     */
    public boolean pieceSimulator(ArrayList<Piece> opponentPieces, Board chessBoard, Color pieceColor){
        boolean isStillInCheck = true;
        int xLimit = chessBoard.getGlobalWidth();
        int yLimit = chessBoard.getGlobalHeight();

        Piece[][] gameBoard = chessBoard.getBoard();

        for (Piece p : opponentPieces){
            // move pieces to each possible location and see if any one can block the check
            int curX = p.getX();
            int curY = p.getY();
            ArrayList<Piece> possibleMoves = p.getPossibleMoves(curX,curY,xLimit,yLimit);

            for (Piece move : possibleMoves){
                int destX = move.getX();
                int destY = move.getY();

                if (p.isMovePossible(curX,curY,destX,destY,gameBoard)){
                    Piece tempPiece = null;
                    if (gameBoard[destX][destY] != null){
                        tempPiece = gameBoard[destX][destY];
                    }
                    gameBoard[destX][destY] = p;
                    p.setX(destX);
                    p.setY(destY);


                    if(isKingInCheck(chessBoard,pieceColor))
                        isStillInCheck = true;
                    else {
                        isStillInCheck = false;

                    }

                    gameBoard[curX][curY] = p;
                    p.setX(curX);
                    p.setY(curY);
                    if (tempPiece != null)
                        gameBoard[destX][destY] = tempPiece;
                    else
                        gameBoard[destX][destY] = null;

                }
            }
        }
        return isStillInCheck;
    }

    /**
     * check if any of my current moves puts the other teams king in check
     * base condition for checkmate, finds if player of !pieceColor is in check
     * does so by simulating all possible moves of the current players pieces
     * and seeing if all of them put the king in check
     * @param chessBoard : instance of the chessboard
     * @param pieceColor : is the color of the current player
     * @return true if king in check else false
    */
    public boolean isKingInCheck(Board chessBoard, Color pieceColor){
        boolean isInCheck = false;
        King opponentKing;
        ArrayList<Piece> currentPieces;

        if (pieceColor == Color.white){
            opponentKing = (King) chessBoard.getBlackKingPos();
            currentPieces = chessBoard.whitePieces;
        }
        else{
            opponentKing = (King) chessBoard.getWhiteKingPos();
            currentPieces = chessBoard.blackPieces;
        }

        // check if any of current players moves can put king in check
        boolean canPutKingInCheck;
        for (Piece p : currentPieces){
            opponentKing.getX();
            canPutKingInCheck = canPieceMove(p.getX(),p.getY(),opponentKing.getX(),opponentKing.getY(),chessBoard);
            if (canPutKingInCheck){
                isInCheck = true;
                break;
            }
        }

        return isInCheck;
    }

    /**
     * function to check if king was put in check by the latest moves
     * This is done by seeing if any piece of the opponent color can
     * move to take the king
     * @param chessBoard : instance of the chessboard
     * @param kingColor : color of the current player
     * @return true if self in check else false
     */
    public boolean isPuttingSelfInCheck(Board chessBoard, Color kingColor){
        boolean inCheck = false;

        // check if if any piece of the other color can move to kings current position
        ArrayList<Piece> currentPieces;
        King curKing;
        if (kingColor == Color.white) {
            currentPieces = chessBoard.blackPieces;
            curKing = (King) chessBoard.getWhiteKingPos();
        }
        else {
            currentPieces = chessBoard.whitePieces;
            curKing = (King) chessBoard.getBlackKingPos();
        }

        int xLimit = chessBoard.getGlobalWidth();
        int yLimit = chessBoard.getGlobalHeight();

        for (Piece p : currentPieces){
            ArrayList<Piece> possibleMoves = p.getPossibleMoves(p.getX(),p.getY(),xLimit,yLimit);
            boolean isInMoveList = isCoorInList(possibleMoves,curKing);
            if (isInMoveList){
                if(p.isMovePossible(p.getX(),p.getY(),curKing.getX(),curKing.getY(),chessBoard.getBoard())){
                    inCheck = true;
                    break;
                }
            }
        }

        return inCheck;
    }

    /**
     *
     * @param possibleMoves : : the array of possible moves
     * @param piecePos : piece to be found in array
     * @return : returns true if piece in array, else false
     */
    public boolean isCoorInList(ArrayList<Piece> possibleMoves,Piece piecePos){
        int pieceX = piecePos.getX();
        int pieceY = piecePos.getY();
        boolean isMoveInList = false;

        for (Piece p : possibleMoves){
            if(p.getX() == pieceX && p.getY() == pieceY) {
                isMoveInList = true;
                break;
            }
        }
        return isMoveInList;
    }

    /**
     * Checks if the game is in stalemate condition
     * This is done by checking if the current player, no matter which
     * piece he moves, would put the king in a check.
     * This can be treated as a condition for a draw
     * @param chessBoard
     * @param curColor
     * @return
     */
    public boolean isStaleMate(Board chessBoard, Color curColor){
        boolean staleMate;

        ArrayList<Piece> curPieces;
        Color oppColor;
        if (curColor == Color.white) {
            curPieces = chessBoard.whitePieces;
            oppColor = Color.black;
        }
        else {
            curPieces = chessBoard.blackPieces;
            oppColor = Color.white;
        }

        // simulate all possible moves and see if after each move the king is still in check
        // for simulator loop, pass the opposite color of current

        staleMate = pieceSimulator(curPieces,chessBoard,oppColor);

        return staleMate;
    }
}
