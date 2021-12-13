public abstract class Joueur {
    public Equipe equipe;

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

    public boolean tourFinis(){
        boolean toutLesBateauxOntJoue = true;
        for (int i = 0 ; i < equipe.listeNavire.size() ; i++){
            if (!equipe.listeNavire.get(i).getActionJouer()){
                toutLesBateauxOntJoue = false;
            }
        }
        return toutLesBateauxOntJoue;
    }

    public boolean estHumain(){
        return this.getClass() == JHumain.class;
    }
}
