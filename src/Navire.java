public abstract class Navire {
    protected int PV;
    protected int PVmax;
    protected int capaciteDeplacement;
    protected int degatAtt;

    public boolean actionJoue;

    public int idEquipe;

    protected StatutNavire statutNavire;

    public Navire(int _idEquipe){
        capaciteDeplacement = 1; //Pour le moment, tous les bateaux se déplacent que d'une case
        idEquipe = _idEquipe;
        actionJoue = false;
    }

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

    public void seReparer(){ //passer en string pr ne pas avoir de print ici
        PV = PV + 20;
        if ( PV > PVmax) {
            PV = PVmax;
        }
        System.out.print("Le " + this + " est en train de se reparer. Il a maintenant : " + PV + "PV");
    }

    public String stringStatsCible(){
        return stringType() + " du J" + idEquipe + " PV :" + PV;
    }

    public String stringType(){
        if (statutNavire == StatutNavire.CHALUTIER) return "Chalutier";
        if (statutNavire == StatutNavire.SOUS_MARIN) return "Sous-Marin";
        return "Destroyer";
    }

    public void attaque(Navire navireCible){ //passer en string pr ne pas avoir de print ici
        System.out.println("Le " + this + " tire sur le " + navireCible + " et lui inflige " + degatAtt + " de degats");
        navireCible.PV = navireCible.PV - this.degatAtt;
    }

    @Override
    public String toString(){
        return stringType() + " J" + idEquipe;
    }
}
