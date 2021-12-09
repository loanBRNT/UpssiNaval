public class SousMarin extends NavireProfondeur{

    public SousMarin(int idEquipe){
        super(idEquipe);
        this.statutNavire = StatutNavire.SOUS_MARIN;
        this.degatAtt = 75;
        this.PV = 70;
        PVmax = PV;
    }

}
