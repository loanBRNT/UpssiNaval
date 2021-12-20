public class IllegalSaisieException extends Exception{

    public IllegalSaisieException(int i){
        super("La saisie : " + i + " est invalide");
    }
}
