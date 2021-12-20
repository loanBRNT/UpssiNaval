package Team;

import java.util.Scanner;
import Exception.*;
import Statut.*;
import Support.*;

public class JHumain extends Joueur {
    private Scanner sc;

    public JHumain(StatutEquipe st, int idEquipe){
        super(st, idEquipe);
        sc = new Scanner(System.in);
    }

    /**
     * Permet de demander au joueur les coordonnées du ième bateau
     * @param i rang du bateau
     * @param largeurPlateau Largeur max du plateau
     * @param longueurPlateau Longueur max du plateau
     * @return la position choisie
     */
    public Position placementNavire(int i, int largeurPlateau, int longueurPlateau){
        int x,y;
        System.out.println(equipe.listeNavire.get(i) + ": ");
        x = choixValeur(0,largeurPlateau);
        y = choixValeur(0,longueurPlateau);
        return new Position(x,y);
    }

    /**
     * Permet à l'utilisateur de saisir une valeur pour interagir avec le jeu
     * @param borneInf valeur min prenable par la saisie
     * @param borneSup valeur max prenable par la saisie
     * @return l'entier chosiit par l'utilisateur
     */
    public int choixValeur(int borneInf, int borneSup){
        int choix = 0;
        boolean saisieIncorrecte = true;

        while (saisieIncorrecte) {
            saisieIncorrecte = false;
            try {
                choix = Integer.parseInt(sc.next()); //le next.int() me bouclait une erreur en permanance en cas de mauvaise saisie.
                if (choix < borneInf || choix > borneSup) throw new IllegalSaisieException(choix);
            } catch (Exception e) {
                saisieIncorrecte = true;
                e.printStackTrace();
            }
        }
        return choix;
    }
}
