import java.util.Scanner;

public class JHumain extends Joueur{
    private Scanner sc;

    public JHumain(StatutEquipe st, int idEquipe){
        super(st, idEquipe);
        sc = new Scanner(System.in);
    }

    public Position placementNavire(int i, int largeurPlateau, int longueurPlateau){
        int x,y;
        System.out.println(equipe.listeNavire.get(i) + ": ");
        x = choixValeur(0,largeurPlateau);
        y = choixValeur(0,longueurPlateau);
        return new Position(x,y);
    }

    public int choixValeur(int borneInf, int borneSup){
        int choix = 0;
        boolean saisieIncorrecte = true;

        while (saisieIncorrecte) {
            saisieIncorrecte = false;
            try {
                choix = Integer.parseInt(sc.next()); //le next.int() me bouclait une erreur en permanance en cas de mauvaise saisie.
                if (choix < borneInf || choix > borneSup) saisieIncorrecte = true;
            } catch (Exception e) {
                saisieIncorrecte = true;
                e.printStackTrace();
            }
        }
        return choix;
    }
}
