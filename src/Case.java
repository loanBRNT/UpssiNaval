public class Case {
    private boolean occupeSurface;
    private boolean occupeProfondeur;

    private Navire occupantSurface;
    private Navire occupantProfondeur;

    /**
     * Constructeur d'une Case. Elle possède 4 attributs, 2 pour la surface et 2 autres en profondeur.
     */
    public Case(){
        occupeProfondeur = false;
        occupeSurface = false;
        occupantSurface = null;
        occupantProfondeur = null;
    }

    /**
     * permet de placer un navire sur la case (en profondeur ou en surface, selon son type)
     * @param navire Le navire en question
     * @throws OccupException
     */
    public void setNavire(Navire navire) throws OccupException {
        if (navire.estUnNavireDeProfondeur()){
            if (isOccupeProfondeur()) throw new OccupException(1);
            setNavireEnProfondeur(navire);
        } else {
            if (isOccupeSurface()) throw new OccupException(0);
            setNavireSurSurface(navire);
        }
    }

    /**
     * Permet de mettre un navire sur la case en surface
     * @param navire le navire en question
     */
    private void setNavireSurSurface(Navire navire){
        occupantSurface = navire;
        occupeSurface = true;
    }

    /**
     * Permet de mettre un navire sur la case en Profondeur
     * @param navire le navire en question
     */
    private void setNavireEnProfondeur(Navire navire){
        occupantProfondeur = navire;
        occupeProfondeur = true;
    }

    /**
     * Permet de retirer un navire de la case en surface
     */
    public void unsetNavireSurSurface(){
        occupantSurface = null;
        occupeSurface = false;
    }

    /**
     * Permet de retirer un navire de la case en profondeur
     */
    public void unsetNavireEnProfondeur(){
        occupantProfondeur = null;
        occupeProfondeur = false;
    }

    /**
     * Renvoie l'occupant en profondeur de la case
     * @return null s'il n'y en a pas, sinon Le Navire qui l'occupe
     */
    public Navire getOccupantProfondeur() {
        return occupantProfondeur;
    }

    /**
     * Renvoie l'occupant en surface de la case
     * @return null s'il n'y en a pas, sinon Le Navire qui l'occupe
     */
    public Navire getOccupantSurface() {
        return occupantSurface;
    }

    /**
     * Permet de savoir si la case est occupé en profondeur
     * @return true si oui, false sinon
     */
    public boolean isOccupeProfondeur() {
        return occupeProfondeur;
    }

    /**
     * Permet de savoir si la case est occupé en surface
     * @return true si oui, false sinon
     */
    public boolean isOccupeSurface() {
        return occupeSurface;
    }

/* -------------- FONCTIONS STRING --------------- */

    @Override
    public String toString(){
        if (isOccupeSurface() && isOccupeProfondeur()) return getOccupantSurface().affichageCase() + "/" + getOccupantProfondeur().affichageCase();
        if (isOccupeSurface() &&!isOccupeProfondeur()) return getOccupantSurface().affichageCase();
        if (isOccupeProfondeur() && !isOccupeSurface()) return getOccupantProfondeur().affichageCase();
        return "~";
    }

}
