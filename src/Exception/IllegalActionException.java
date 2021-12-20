package Exception;

public class IllegalActionException extends Exception{

    public IllegalActionException(String message){
        super("L'action est invalide : " + message);
    }
}
