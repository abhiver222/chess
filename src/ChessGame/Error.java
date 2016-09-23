package ChessGame;

/**
 * Created by Abhishek on 9/22/2016.
 * Class to store an error message which the model
 * may occur when trying to move a piece
 * this may include checkmate, check, stalemate etc
 */
public class Error {

    public String msg;
    public Error(String message){
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
