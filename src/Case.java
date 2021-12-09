public class Case {
    private boolean occupeSurface;
    private boolean occupeProfondeur;

    private Navire occupantSurface;
    private Navire occupantProfondeur;

    public Case(){
        occupeProfondeur = false;
        occupeSurface = false;
        occupantSurface = null;
        occupantProfondeur = null;
    }

    @Override
    public String toString(){
        if (isOccupeSurface() && isOccupeProfondeur()) return getOccupantSurface().affichageCase() + "/" + getOccupantProfondeur().affichageCase();
        if (isOccupeSurface() &&!isOccupeProfondeur()) return getOccupantSurface().affichageCase();
        if (isOccupeProfondeur() && !isOccupeSurface()) return getOccupantProfondeur().affichageCase();
        return "~";
    }

    private void setNavireSurSurface(Navire navire){
        occupantSurface = navire;
        occupeSurface = true;
    }

    private void setNavireEnProfondeur(Navire navire){
        occupantProfondeur = navire;
        occupeProfondeur = true;
    }

    public void unsetNavireSurSurface(){
        occupantSurface = null;
        occupeSurface = false;
    }

    public void unsetNavireEnProfondeur(){
        occupantProfondeur = null;
        occupeProfondeur = false;
    }

    public void setNavire(Navire navire) throws IllegalCaseException {
        if (navire.statutNavire == StatutNavire.SOUS_MARIN){
            if (isOccupeProfondeur()) throw new OccupException(1);
            setNavireEnProfondeur(navire);
        } else {
            if (isOccupeSurface()) throw new OccupException(0);
            setNavireSurSurface(navire);
        }
    }

    public Navire getOccupantProfondeur() {
        return occupantProfondeur;
    }

    public Navire getOccupantSurface() {
        return occupantSurface;
    }

    public boolean isOccupeProfondeur() {
        return occupeProfondeur;
    }

    public boolean isOccupeSurface() {
        return occupeSurface;
    }

}
