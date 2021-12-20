public abstract class Joueur {
    protected Equipe equipe;
    public int id;

    public Joueur(StatutEquipe st, int idEquipe){
        id = idEquipe;
        if (st == StatutEquipe.BATAILLON) equipe = new Bataillon();
        else equipe = new Pecheur();
        equipe.initEquipe(idEquipe);
    }

    @Override
    public String toString(){
        return equipe.toString();
    }

    /**
     * Permet de détecter si tous les bateaux du joueur ont joués.
     * @return true si oui, false sinon
     */
    public boolean tourFinis(){
        boolean toutLesBateauxOntJoue = true;
        for (int i = 0 ; i < equipe.listeNavire.size() ; i++){
            if (!equipe.listeNavire.get(i).getActionJouer()){
                toutLesBateauxOntJoue = false;
            }
        }
        return toutLesBateauxOntJoue;
    }

    /**
     * Permet de savoir si le joueur est humain
     * @return true si oui, false sinon
     */
    public boolean estHumain(){
        return this.getClass() == JHumain.class;
    }

    /**
     * Renvoie le navire au rang i de la flotte du joueur
     * @param i le rang
     * @return le navire
     */
    public Navire getNavireAvecRang(int i){
        return equipe.getNavireAvecRang(i);
    }

    public abstract int choixValeur(int borneMoins, int borneSup);

    public abstract Position placementNavire(int i, int largeurPlateau, int longueurPlateau);
}
