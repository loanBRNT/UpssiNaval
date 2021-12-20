package Team;
import Flotte.*;
import Statut.*;

public interface IEquipe {

    void initEquipe(int idEquipe);

    StatutEquipe getStatut();

    void ajoutNavire(Navire navire);

}
