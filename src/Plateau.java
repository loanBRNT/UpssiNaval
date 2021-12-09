import javax.swing.*;
import java.util.ArrayList;

public class Plateau {
    public static final int LARGEUR_PLATEAU = 4;
    public static final int LONGUEUR_PLATEAU = 4;

    public Case grille[][];

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

    public Case getCase(Position pos) throws IllegalCaseException{
        if (pos.x >= LARGEUR_PLATEAU || pos.y >= LONGUEUR_PLATEAU || pos.x < 0 || pos.y < 0) throw new OutOfPlateauException(pos);
        return grille[pos.x][pos.y];
    }

    public Case getCase(int x, int y) throws IllegalCaseException{
        if (x >= LARGEUR_PLATEAU || y >= LONGUEUR_PLATEAU || x < 0 || y < 0) throw new OutOfPlateauException(new Position(x,y));
        return grille[x][y];
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

    public Case getCase(Navire navire) {
        try {
            return getCase(getPosNavire(navire));
        } catch (IllegalCaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Position getPosNavire(Navire navire) { //Peut etre ameliorer
        for (int i = 0; i < LONGUEUR_PLATEAU ; i++ ){
            for (int j = 0 ; j < LARGEUR_PLATEAU ; j++){
                if (grille[i][j].getOccupantSurface() == navire || grille[i][j].getOccupantProfondeur() == navire) return new Position(i,j);
            }
        }
        return null;
    }

    public ArrayList<Position> PositionCaseDisponibleAutour(Navire navire){
        Position posBateau = getPosNavire(navire);
        ArrayList<Position> listePosCase = new ArrayList();
        for (int i = posBateau.x-1 ; i <= posBateau.x+1 ; i++){
            for (int j = posBateau.y-1; j <= posBateau.y+1; j++){
                try {
                    if (navire.statutNavire == StatutNavire.SOUS_MARIN) {
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

    public String stringCaseDisponibleAutour(ArrayList<Position> listePosCase){
        String s = "";
        for (int i = 0 ; i < listePosCase.size() ; i++){
            s = s + (i+1) + ": (" + listePosCase.get(i).x + "," + listePosCase.get(i).y + ")\n";
        }
        return s;
    }

    public ArrayList<Navire> navireEnemieACote(Navire navire, int idAllie){
        ArrayList<Navire> listeNavire = new ArrayList<>();
        Position posBateau = getPosNavire(navire);
        Navire n;
        for (int i = posBateau.x-1 ; i <= posBateau.x+1 ; i++){
            for (int j = posBateau.y-1; j <= posBateau.y+1; j++){
                try {
                    n = getCase(i,j).getOccupantProfondeur();
                    if (n != null){
                        if (n.idEquipe != idAllie) listeNavire.add(n);
                    }
                    n = getCase(i,j).getOccupantSurface();
                    if (n != null){
                        if (n.idEquipe != idAllie) listeNavire.add(n);
                    }
                } catch (IllegalCaseException e) {
                    //on ne fait rien
                }
            }
        }
        return listeNavire;
    }

    public void deplacerNavireSurCase(Navire navire, Position pos){
        Case caseInitiale = getCase(navire);
        try {
            Case c = getCase(pos);
            if (navire.statutNavire == StatutNavire.SOUS_MARIN){
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

    public void supprimerNavire(Navire navire){
        Case c = getCase(navire);
        if (navire.statutNavire == StatutNavire.SOUS_MARIN){
            c.unsetNavireEnProfondeur();
        } else {
            c.unsetNavireSurSurface();
        }
    }

    public String stringCoordonee(Navire navire) {
        return "(" + getPosNavire(navire).x + ", " + getPosNavire(navire).x + ")";
    }
}
