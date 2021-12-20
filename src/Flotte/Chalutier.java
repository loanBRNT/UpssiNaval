package Flotte;
import Statut.*;

public class Chalutier extends NavireSurface {

    public Chalutier(int idEquipe){
        super(idEquipe);
        this.statutNavire = StatutNavire.CHALUTIER;
        this.degatAtt = 0;
        this.PV = 25;
        PVmax = PV;
    }
}
