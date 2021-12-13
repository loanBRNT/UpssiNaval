import java.util.ArrayList;

public class Plateau {
    public static final int LARGEUR_PLATEAU = 5;
    public static final int LONGUEUR_PLATEAU = 5;

    private Case grille[][];

    /**
     * Constructeur du plateau, il créé une grille vide de dimension fixe (attribut fixé) servant de plateau de jeu
     */
    public Plateau(){
        grille = new Case[LARGEUR_PLATEAU][LONGUEUR_PLATEAU];
    }

    /**
     * Creer un objet case pour chaque élément de la grille
     */
    public void initialisationPlateau(){
        for (int i = 0 ; i < LONGUEUR_PLATEAU ; i++){
            for (int j = 0 ; j < LARGEUR_PLATEAU ; j++){
                grille[i][j] = new Case();
            }
        }
    }


    /**
     * Permet de récupérer l'objet Case correspond au paramètre
     * @param pos la position dont on veut la case
     * @return La case en question
     * @throws IllegalCaseException
     */
    public Case getCase(Position pos) throws IllegalCaseException{
        if (pos.x >= LARGEUR_PLATEAU || pos.y >= LONGUEUR_PLATEAU || pos.x < 0 || pos.y < 0) throw new OutOfPlateauException(pos);
        return grille[pos.x][pos.y];
    }

    /**
     * Permet de récupérer l'objet Case correspond au paramètre
     * @param x nb de ligne
     * @param y nb colonne
     * @return la case en question
     * @throws IllegalCaseException
     */
    public Case getCase(int x, int y) throws IllegalCaseException{
        if (x >= LARGEUR_PLATEAU || y >= LONGUEUR_PLATEAU || x < 0 || y < 0) throw new OutOfPlateauException(new Position(x,y));
        return grille[x][y];
    }

    /**
     * Permet de récupérer l'objet Case correspond au paramètre
     * @param navire le navire dont on veut récupérer la case
     * @return la case en question
     */
    public Case getCase(Navire navire) {
        try {
            return getCase(getPosNavire(navire));
        } catch (IllegalCaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Permet de récupérer la position du navire passé en paramètre
     * @param navire
     * @return
     */
    public Position getPosNavire(Navire navire) {
        for (int i = 0; i < LONGUEUR_PLATEAU ; i++ ){
            for (int j = 0 ; j < LARGEUR_PLATEAU ; j++){
                if (grille[i][j].getOccupantSurface() == navire || grille[i][j].getOccupantProfondeur() == navire) return new Position(i,j);
            }
        }
        return null;
    }

    /**
     * Construit une liste de position représentant les positions des cases libres autours.
     * @param navire le navir centre
     * @return la liste de position
     */
    public ArrayList<Position> PositionCaseDisponibleAutour(Navire navire){
        Position posBateau = getPosNavire(navire);
        ArrayList<Position> listePosCase = new ArrayList();
        for (int i = posBateau.x-1 ; i <= posBateau.x+1 ; i++){
            for (int j = posBateau.y-1; j <= posBateau.y+1; j++){
                try {
                    if (navire.estUnNavireDeProfondeur()) {
                        if (!getCase(i, j).isOccupeProfondeur()){
                            listePosCase.add(new Position(i,j));
                        }
                    } else {
                            if (!getCase(i, j).isOccupeSurface()){
                                listePosCase.add(new Position(i,j));
                            }
                    }
                } catch (IllegalCaseException e) {
                    //on ne fait rien
                }
            }
        }
        return listePosCase;
    }

    /**
     * Construit une liste de navires, représentant l'ensemble des navires sur une case avoisinantes du navire passé en paramètre.
     * @param navire navire centre
     * @return
     */
    public ArrayList<Navire> navireEnemieACote(Navire navire){
        ArrayList<Navire> listeNavire = new ArrayList<>();
        Position posBateau = getPosNavire(navire);
        Navire n;
        for (int i = posBateau.x-1 ; i <= posBateau.x+1 ; i++){
            for (int j = posBateau.y-1; j <= posBateau.y+1; j++){
                try {
                    n = getCase(i,j).getOccupantProfondeur();
                    if (n != null){
                        if (n.idEquipe != navire.idEquipe) listeNavire.add(n);
                    }
                    n = getCase(i,j).getOccupantSurface();
                    if (n != null){
                        if (n.idEquipe != navire.idEquipe) listeNavire.add(n);
                    }
                } catch (IllegalCaseException e) {
                    //on ne fait rien
                }
            }
        }
        return listeNavire;
    }

    /**
     * Permet de déplacer le navire à la position passé en paramètre
     * @param navire
     * @param pos
     */
    public void deplacerNavireSurCase(Navire navire, Position pos){
        Case caseInitiale = getCase(navire);
        try {
            Case c = getCase(pos);
            if (navire.estUnNavireDeProfondeur()){
                if (c.isOccupeProfondeur()){
                    throw new IllegalDeplacementException(pos);
                } else {
                    c.setNavire(navire);
                    caseInitiale.unsetNavireEnProfondeur();
                }
            } else {
                if (c.isOccupeSurface()){
                    throw new IllegalDeplacementException(pos);
                } else {
                    c.setNavire(navire);
                    caseInitiale.unsetNavireSurSurface();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Le " + navire + " se déplace sur la case " + stringCoordonee(navire));
    }

    /**
     * Retire un navire du plateau de jeu
     * @param navire le navire a supprimer
     */
    public void supprimerNavire(Navire navire){
        Case c = getCase(navire);
        if (navire.estUnNavireDeProfondeur()){
            c.unsetNavireEnProfondeur();
        } else {
            c.unsetNavireSurSurface();
        }
    }

    /* -------------- Fonction String --------------- */

    public String stringCaseDisponibleAutour(ArrayList<Position> listePosCase){
        String s = "";
        for (int i = 0 ; i < listePosCase.size() ; i++){
            s = s + (i+1) + ": (" + listePosCase.get(i).x + "," + listePosCase.get(i).y + ")\n";
        }
        return s;
    }

    public String stringCoordonee(Navire navire) {
        return "(" + getPosNavire(navire).x + ", " + getPosNavire(navire).x + ")";
    }

    @Override
    public String toString(){
        String s = "";
        for (int i = 0 ; i < LONGUEUR_PLATEAU ; i++){
            s = s + "\n| ";
            for (int j = 0 ; j < LARGEUR_PLATEAU ; j++){
                s = s + grille[i][j] + " | ";
            }
        }
        return s;
    }
}
