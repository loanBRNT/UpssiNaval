package Team;
import Statut.*;
import Support.*;

import java.util.Random;

public class IA extends Joueur {
    private Random random;

    public IA(StatutEquipe st, int idEquipe){
        super(st, idEquipe);
        random = new Random();
    }

    /**
     * Permet à l'ia de chosiir aléatoirement une valeur
     * @param borneMoins maximum possible de la valeur
     * @param borneSup minimum de la valeur
     * @return l'entier choisit
     */
    @Override
    public int choixValeur(int borneMoins, int borneSup) {
        int temp = 0;
        if (borneMoins == 0) temp = 1;
        return random.nextInt(borneSup) + borneMoins + temp;
    }

    /**
     * Permet à l'Team.IA de placer son navire du rang i
     * @param i le rang du navire à placer
     * @param maxLargeur Largeur max du plateau
     * @param maxLongueur Longueur max du plateau
     * @return la position choisie
     */
    public Position placementNavire(int i, int maxLargeur, int maxLongueur){
        int x,y;
        x = random.nextInt(maxLargeur);
        y = random.nextInt(maxLongueur);
        return new Position(x,y);
    }


}
