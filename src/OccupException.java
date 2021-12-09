public class OccupException extends IllegalCaseException{
    private static String message [] = {
            "surface",
            "profondeur"
    };

    public OccupException(int idErreur) {super("déjà occupée en " + message[idErreur]);}
}
