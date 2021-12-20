package Team;

import java.util.ArrayList;
import Flotte.*;
import Statut.*;

public abstract class Equipe implements IEquipe {
    protected StatutEquipe statut;
    public ArrayList<Navire> listeNavire;


    public Equipe(){
        listeNavire = new ArrayList<>();
    }

    /**
     * Renvoie le navire au rang i de la flotte du joueur
     * @param i le rang
     * @return le navire
     */
    public Navire getNavireAvecRang(int i){
        return listeNavire.get(i);
    }

    /**
     * Ajoute un navire a la liste de l'équipe
     * @param n le navire a ajouté
     */
    public void ajoutNavire(Navire n){
        listeNavire.add(n);
    }

    /**
     * Initialise les navires de la liste afin de commencer le tour.
     */
    public void initTour(){
        for (int i = 0 ; i < listeNavire.size() ; i++){
            listeNavire.get(i).resetTour();
        }
    }

    /**
     * Supprime un navire de la liste de l'équipe.
     * @param navire le navire a supprimer
     */
    public void supprimerNavire(Navire navire){
        listeNavire.remove(navire);
    }

    @Override
    /**
     * Retourne le statut de l'équipe : Bataillon ou Pecheur
     */
    public StatutEquipe getStatut() {
        return statut;
    }

    /* ----------- FONCTION STRING --------------- */

    @Override
    public String toString(){
        return "Equipe " + statut + " | Sa flotte est composée de : " + listeNavire;
    }

    public String stringListeNavire(){
        String s = "";
        for (int i = 1 ; i < listeNavire.size()+1 ; i++){
            s = s + i + ": " + listeNavire.get(i-1) + "\n";
        }
        return s;
    }
}
