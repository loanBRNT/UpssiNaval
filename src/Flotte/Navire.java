package Flotte;

import Statut.*;

public abstract class Navire {
    public int PV;
    protected int PVmax;
    protected int capaciteDeplacement;
    protected int degatAtt;
    public boolean endommage;

    private boolean actionJoue;

    public int idEquipe;

    public StatutNavire statutNavire;

    public Navire(int _idEquipe){
        capaciteDeplacement = 1; //Pour le moment, tous les bateaux se déplacent que d'une case
        idEquipe = _idEquipe;
        actionJoue = false;
        endommage = false;
    }

    /**
     * Permet de remettre l'action a false pour le navire.
     */
    public void resetTour(){
        actionJoue = false;
    }

    /**
     * Permet de mettre l'action du bateau comme dejà effectué
     */
    public void setActionJoue(){
        actionJoue = true;
    }

    /**
     * Retourne si le navire a deja joue pendant ce tour
     * @return true si oui, false sinon
     */
    public boolean getActionJouer(){
        return actionJoue;
    }

    /**
     * Permet de réparer le bateau, ajouter 20 PV sans dépasser les PVmax.
     */
    public void seReparer(){
        PV = PV + 20;
        if ( PV > PVmax) {
            PV = PVmax;
        } if (PV > PVmax / 2){
            endommage = false;
            degatAtt = degatAtt*2;
        }
        System.out.println("Le " + this + " est en train de se reparer. Il a maintenant : " + PV + "PV");
    }

     /**
     * Permet de mettre à jour les stats du navire en paramètre après l'attaque du navire
     * @param navireCible le navire attaqué
     */
    public void attaque(Navire navireCible){
        System.out.println("Le " + this + " tire sur le " + navireCible + " et lui inflige " + degatAtt + " de degats");
        navireCible.seFaitAttaquer(this.degatAtt);
    }

    /**
     * Permet de mettre à jour le navire qui se fait attqué via le nombre de dégats qu'il subit
     * @param degat le nombre de dégats qu'il subit
     */
    public void seFaitAttaquer(int degat){
        PV = PV - degat;
        if (PV < PVmax/2){
            setEndommage();
        }
    }

    /**
     * Permet de savoir si un navire est un navire de type Profondeur
     * @return true si oui, false sinon.
     */
    public boolean estUnNavireDeProfondeur(){
        return this.statutNavire == StatutNavire.SOUS_MARIN;
    }

    /**
     * Permet de savoir si le navire est endommagé ou non
     * @return true si le navire est considére comme endommagé, false sinon
     */
    public void setEndommage(){
        if (!endommage) degatAtt = degatAtt / 2;
        endommage = true;
    }

    /* ----------------- FONCTIONS STRING --------------- */

    /**
     * Retourne un string correspondant à l'affiche sur le plateau de Jeu du navire
     * @return String
     */
    public String affichageCase(){
        if (statutNavire == StatutNavire.SOUS_MARIN){
            return idEquipe + "S";
        }
        if (statutNavire == StatutNavire.CHALUTIER){
            return idEquipe + "C";
        }
        return idEquipe + "D";
    }

    public String stringStats(){
        return "ETAT : " + stringEtat() + "\nDégats d'attaque : " + degatAtt + "\nPoints de vie : " + PV + "\nVitesse de Déplacement :" + capaciteDeplacement;
    }

    public String stringEtat(){
        if (endommage) return "endommage";
        return "normal";
    }

    public String stringStatsCible(){
        return stringType() + " du J" + idEquipe + " PV :" + PV;
    }

    /**
     * Permet de mettre sous forme de String le type du navire
     * @return "Chalutier", "Sous-Marin" ou "Destroyer"
     */
    public String stringType(){
        if (statutNavire == StatutNavire.CHALUTIER) return "Chalutier";
        if (statutNavire == StatutNavire.SOUS_MARIN) return "Sous-Marin";
        return "Destroyer";
    }

    @Override
    public String toString(){
        return stringType() + " J" + idEquipe;
    }
}
