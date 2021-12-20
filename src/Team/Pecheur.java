package Team;
import Statut.*;
import Flotte.*;

public class Pecheur extends Equipe {

    public Pecheur(){
        super();
        this.statut = StatutEquipe.PECHEUR;
    }

    @Override
    public void initEquipe(int idEquipe) {
        for (int i = 0 ; i < 5 ; i++){
            this.ajoutNavire(new Chalutier(idEquipe));
        }
    }
}
