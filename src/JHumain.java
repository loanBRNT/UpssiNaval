import java.util.Scanner;

public class JHumain extends Joueur{

    public JHumain(StatutEquipe st, int idEquipe){
        super(st, idEquipe);
    }

    public Position placementNavire(int i){
        int x,y;
        Scanner sc = new Scanner(System.in);
        System.out.println(equipe.listeNavire.get(i) + ": ");
        x = sc.nextInt();
        y = sc.nextInt();
        return new Position(x,y);
    }
}
