package chess_elements; /**
 * Created by Abhishek on 8/29/2016.
 */

import ChessGame.Error;

import java.awt.Color;
import java.util.ArrayList;

public class Board
{
    public Piece[][] tiles;
    public Error message;

    public ArrayList<Piece> whitePieces;
    public ArrayList<Piece> blackPieces;

    public int getGlobalHeight() {
        return globalHeight;
    }

    public void setGlobalHeight(int globalHeight) {
        this.globalHeight = globalHeight;
    }

    public int getGlobalWidth() {
        return globalWidth;
    }

    public void setGlobalWidth(int globalWidth) {
        this.globalWidth = globalWidth;
    }

    public Piece whiteKingPos;
    public Piece blackKingPos;

    public Piece[][] getBoard(){
        return this.tiles;
    }

    private int globalHeight;
    private int globalWidth;

    /**
     * white on top, black on bottom
     * constructor for board
     * @param width
     * @param height
     */
    public Board (int width, int height, boolean custom, Error msg)
    {
        this.message = msg;
        tiles = new Piece[width][height];
        setGlobalHeight(height);
        setGlobalWidth(width);
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        this.populateBoard();
        this.populatePieces(custom);

    }

    /**
    populates board with nulls
     */
    public void populateBoard()
    {
        int width = getGlobalWidth();
        int height = getGlobalHeight();
        for (int x=0;x<width-1;x++){
            for (int y=2;y<height-2;y++){
                tiles[x][y] = null;
                tiles[x+1][y] = null;
            }
        }
    }

    /**
    populate board with required pieces
     */
    public void populatePieces(boolean custom)
    {
        int width = getGlobalWidth();
        Color whiteColor = Color.white;
        Color blackColor = Color.black;

        // populate pawns
        for (int x=0;x<width;x++){
            Pawn whitePawn = new Pawn(whiteColor,x,1);
            Pawn blackPawn = new Pawn(blackColor,x,6);
            tiles[x][1] = whitePawn;
            tiles[x][6] = blackPawn;
            whitePieces.add(whitePawn);
            blackPieces.add(blackPawn);
        }

        // populate kings
        King whiteKing = new King(whiteColor,4,0);
        King blackKing = new King(blackColor,4,7);
        tiles[4][0] = whiteKing;
        tiles[4][7] = blackKing;
        setWhiteKingPos(whiteKing);
        setBlackKingPos(blackKing);
        whitePieces.add(whiteKing);
        blackPieces.add(blackKing);

        // populate queens
        Queen whiteQueen = new Queen(whiteColor,3,0);
        Queen blackQueen = new Queen(blackColor,3,7);
        tiles[3][0] = whiteQueen;
        tiles[3][7] = blackQueen;
        whitePieces.add(whiteQueen);
        blackPieces.add(blackQueen);

        // populate knights
        Knight whiteKnightLeft = new Knight(whiteColor,1,0);
        Knight whiteKnightRight = new Knight(whiteColor,6,0);
        Knight blackKnightLeft = new Knight(blackColor,1,7);
        Knight blackKnightRight = new Knight(blackColor,6,7);
        tiles[1][0] = whiteKnightLeft;
        tiles[6][0] = whiteKnightRight;
        tiles[1][7] = blackKnightLeft;
        tiles[6][7] = blackKnightRight;
        whitePieces.add(whiteKnightLeft);
        whitePieces.add(whiteKnightRight);
        blackPieces.add(blackKnightLeft);
        blackPieces.add(blackKnightRight);

        // populate rooks and bishops
        if (!custom) {
            addRooks();
            addBishops();
        }
        else{
            // populate wazirs and ferzs
            addWazirs();
            addFerzs();
        }
        
    }

    public void addRooks(){
        Color whiteColor = Color.white;
        Color blackColor = Color.black;
        Rook whiteRookLeft = new Rook(whiteColor, 0, 0);
        Rook whiteRookRight = new Rook(whiteColor, 7, 0);
        Rook blackRookLeft = new Rook(blackColor, 0, 7);
        Rook blackRookRight = new Rook(blackColor, 7, 7);
        tiles[0][0] = whiteRookLeft;
        tiles[7][0] = whiteRookRight;
        tiles[0][7] = blackRookLeft;
        tiles[7][7] = blackRookRight;
        whitePieces.add(whiteRookLeft);
        whitePieces.add(whiteRookRight);
        blackPieces.add(blackRookLeft);
        blackPieces.add(blackRookRight);
    }


    public void addWazirs(){
        Color whiteColor = Color.white;
        Color blackColor = Color.black;
        Wazir whiteWazirLeft = new Wazir(whiteColor,0,0);
        Wazir whiteWazirRight = new Wazir(whiteColor,7,0);
        Wazir blackWazirLeft = new Wazir(blackColor,0,7);
        Wazir blackWazirRight = new Wazir(blackColor,7,7);
        tiles[0][0] = whiteWazirLeft;
        tiles[7][0] = whiteWazirRight;
        tiles[0][7] = blackWazirLeft;
        tiles[7][7] = blackWazirRight;
        whitePieces.add(whiteWazirLeft);
        whitePieces.add(whiteWazirRight);
        blackPieces.add(blackWazirLeft);
        blackPieces.add(blackWazirRight);
    }
    
    public void addBishops(){
        // populate bishops
        Color whiteColor = Color.white;
        Color blackColor = Color.black;
        Bishop whiteBishopLeft = new Bishop(whiteColor,2,0);
        Bishop whiteBishopRight = new Bishop(whiteColor,5,0);
        Bishop blackBishopLeft = new Bishop(blackColor,2,7);
        Bishop blackBishopRight = new Bishop(blackColor,5,7);
        tiles[2][0] = whiteBishopLeft;
        tiles[5][0] = whiteBishopRight;
        tiles[2][7] = blackBishopLeft;
        tiles[5][7] = blackBishopRight;
        whitePieces.add(whiteBishopLeft);
        whitePieces.add(whiteBishopRight);
        blackPieces.add(blackBishopLeft);
        blackPieces.add(blackBishopRight);
    }
    
    public void addFerzs(){
        Color whiteColor = Color.white;
        Color blackColor = Color.black;
        Ferz whiteFerzLeft = new Ferz(whiteColor,2,0);
        Ferz whiteFerzRight = new Ferz(whiteColor,5,0);
        Ferz blackFerzLeft = new Ferz(blackColor,2,7);
        Ferz blackFerzRight = new Ferz(blackColor,5,7);
        tiles[2][0] = whiteFerzLeft;
        tiles[5][0] = whiteFerzRight;
        tiles[2][7] = blackFerzLeft;
        tiles[5][7] = blackFerzRight;
        whitePieces.add(whiteFerzLeft);
        whitePieces.add(whiteFerzRight);
        blackPieces.add(blackFerzLeft);
        blackPieces.add(blackFerzRight);
    }

    /**
     * prints current config of the board
     */
    public void printBoard(){
        for (int j = 0; j< globalHeight; j++){
            for (int i = 0; i < globalWidth; i++){
                Piece p = tiles[i][j];
                if (p == null) {
                    System.out.print(" -- ");
                    continue;
                }
                if (p.getTileColor() == Color.black)
                    System.out.print("B-" + p.getName().substring(0,2) + " ");
                else
                    System.out.print("W-" + p.getName().substring(0,2) + " ");
            }
            System.out.println("");
        }
    }

    /**
     * removes piece from the borad and its that colors global piece array
     * @param piece : piece to be removed
     * @param curColor : color of piece to be removed
     */
    public void removePieceFromBoard(Piece piece, Color curColor){
        if (piece == null )
            return;
        ArrayList<Piece> curPieces;
        if (curColor == Color.white)
            curPieces = this.whitePieces;
        else
            curPieces = this.blackPieces;

        this.tiles[piece.getX()][piece.getY()] = null;

        curPieces.remove(piece);
    }

    public Piece getPiece(int x, int y){
        return this.tiles[x][y];
    }

    public Piece getWhiteKingPos() {
        return whiteKingPos;
    }

    public void setWhiteKingPos(Piece whiteKingPos) {
        this.whiteKingPos = whiteKingPos;
    }

    public Piece getBlackKingPos() {
        return blackKingPos;
    }

    public void setBlackKingPos(Piece blackKingPos) {
        this.blackKingPos = blackKingPos;
    }
}
