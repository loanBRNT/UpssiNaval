import java.util.ArrayList;
import java.util.Scanner;

public abstract class Equipe implements IEquipe{
    protected StatutEquipe statut;
    protected ArrayList<Navire> listeNavire;


    public Equipe(){
        listeNavire = new ArrayList<>();
    }

    public void ajoutNavire(Navire n){
        listeNavire.add(n);
    }


    @Override
    public StatutEquipe getStatut() {
        return statut;
    }

    @Override
    public String toString(){
        return "Equipe " + statut + " | Sa flote est composée de : " + listeNavire;
    }

    public String stringListeNavire(){
        String s = "";
        for (int i = 1 ; i < listeNavire.size()+1 ; i++){
            s = s + i + ": " + listeNavire.get(i-1) + "\n";
        }
        return s;
    }

    public void initTour(){
        for (int i = 0 ; i < listeNavire.size() ; i++){
            listeNavire.get(i).actionJoue = false;
        }
    }

    public void supprimerNavire(Navire navire){
        listeNavire.remove(navire);
    }
}
