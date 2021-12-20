package Team;

import Flotte.Navire;
import Support.Position;

public interface IJoueur {

    boolean tourFinis();

    boolean estHumain();

    Navire getNavireAvecRang(int i);

    int choixValeur(int borneMoins, int borneSup);

    Position placementNavire(int i, int largeurPlateau, int longueurPlateau);
}
