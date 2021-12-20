import org.jetbrains.annotations.NotNull;

public abstract class Navire {
    protected int PV;
    protected int PVmax;
    protected int capaciteDeplacement;
    protected int degatAtt;

    private boolean actionJoue;

    public int idEquipe;

    protected StatutNavire statutNavire;

    public Navire(int _idEquipe){
        capaciteDeplacement = 1; //Pour le moment, tous les bateaux se déplacent que d'une case
        idEquipe = _idEquipe;
        actionJoue = false;
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
        }
        System.out.println("Le " + this + " est en train de se reparer. Il a maintenant : " + PV + "PV");
    }

     /**
     * Permet de mettre à jour les stats du navire en paramètre après l'attaque du navire
     * @param navireCible le navire attaqué
     */
    public void attaque(@NotNull Navire navireCible){
        System.out.println("Le " + this + " tire sur le " + navireCible + " et lui inflige " + degatAtt + " de degats");
        navireCible.PV = navireCible.PV - this.degatAtt;
    }

    /**
     * Permet de savoir si un navire est un navire de type Profondeur
     * @return true si oui, false sinon.
     */
    public boolean estUnNavireDeProfondeur(){
        return this.statutNavire == StatutNavire.SOUS_MARIN;
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
        return "Dégats d'attaque : " + degatAtt + "\nPoints de vie : " + PV + "\nVitesse de Déplacement :" + capaciteDeplacement;
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
