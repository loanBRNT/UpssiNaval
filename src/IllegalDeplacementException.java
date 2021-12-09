public class IllegalDeplacementException extends IllegalActionException{

    public IllegalDeplacementException(Position pos){
        super("Déplacement non autorisé\n" +
                "Cause : La case " + pos.x + "," + pos.y + " est deja occupe");
    }
}
