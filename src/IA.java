import java.util.Random;

public class IA extends Joueur{
    private Random random;

    public IA(StatutEquipe st, int idEquipe){
        super(st, idEquipe);
        random = new Random();
    }

    @Override
    public int choixValeur(int borneMoins, int borneSup) {
        int temp = 0;
        if (borneMoins == 0) temp = 1;
        return random.nextInt(borneSup) + borneMoins + temp;
    }

    public Position placementNavire(int i,int maxLargeur, int maxLongueur){
        int x,y;
        x = random.nextInt(maxLargeur);
        y = random.nextInt(maxLongueur);
        return new Position(x,y);
    }


}
