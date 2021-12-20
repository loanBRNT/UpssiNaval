package Exception;
import Support.*;

public class OutOfPlateauException extends IllegalCaseException {

        public OutOfPlateauException(Position pos) {super(pos.x + ", " + pos.y + " est hors limite");}

}
