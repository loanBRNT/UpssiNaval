public class Bataillon extends Equipe{

    public Bataillon(){
        super();
        this.statut = StatutEquipe.BATAILLON;
    }

    @Override
    public void initEquipe(int idEquipe) {
        this.ajoutNavire(new Destroyer(idEquipe));
        for (int i = 0 ; i < 2 ; i++){
            this.ajoutNavire(new SousMarin(idEquipe));
            this.ajoutNavire(new Destroyer(idEquipe));
        }
    }
}
