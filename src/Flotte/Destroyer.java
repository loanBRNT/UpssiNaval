package Flotte;
import Statut.*;

public class Destroyer extends NavireSurface {

    public Destroyer(int idEquipe){
        super(idEquipe);
        this.statutNavire = StatutNavire.DESTROYER;
        this.degatAtt = 50;
        this.PV = 100;
        PVmax = PV;
    }
}
