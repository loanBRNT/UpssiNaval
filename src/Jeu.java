import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jeu {
    private Random random;

    private Plateau plateau;

    private JHumain j1;

    private JHumain j2;

    private IA j3;

    /**
     * Constructeur de la classe Jeu, il crée un attribut Random, et le plateau
     */
    public Jeu(){
        random = new Random();
        plateau = new Plateau();
    }

    /* ------------------------- Fonctions de gestion du jeu et des actions ---------------------------- */

    /**
     * Permet d'initialiser les différents éléments nécéssaires au lancement du jeu : Plateau, les équipes (rôles, + création des navires etc...)
     */
    public void initialisation(){

        plateau.initialisationPlateau();

        //CREATION D'UN TABLEAU POUR ATTRIBUER ALEATOIREMENT LES ROLES AUX JOUEURS
        StatutEquipe tableau[] = tirageAleatoireRole();

        //INITIALISATION DES Joueurs & EQUIPES
        j1 = new JHumain(tableau[0],1);
        j2 = new JHumain(tableau[1],2);
        j3 = new IA(tableau[2],3);
    }

    /**
     * Permet de donner un ordre aléatoire au rôle des joueurs
     * @return un tableau avec les rôles triés de façon aléatoire
     */
    private StatutEquipe[] tirageAleatoireRole(){
        StatutEquipe tableau[] = {null,null,null};
        int tirage = random.nextInt(100);
        if (tirage < 33){
            tableau[0] = StatutEquipe.PECHEUR;
            tableau[1] = StatutEquipe.BATAILLON;
            tableau[2] = StatutEquipe.BATAILLON;
        } else if (tirage < 66){
            tableau[0] = StatutEquipe.BATAILLON;
            tableau[1] = StatutEquipe.PECHEUR;
            tableau[2] = StatutEquipe.BATAILLON;
        } else {
                tableau[0] = StatutEquipe.BATAILLON;
                tableau[1] = StatutEquipe.BATAILLON;
                tableau[2] = StatutEquipe.PECHEUR;
            }
        return tableau;
    }

    /**
     * Fonction appelant les fonctions réalisant le placement des navires pour les joueurs et l'IA.
     * L'IA joue en premier pour éviter qu'elle donne des cases déjà occupés
     */
    public void placementNavireJoueursIA(){
        affichagePlacementNavireIA();
        placementNavireIA();
        affichagePlacementNavire(j1.id);
        placementNavireJoueur(j1);
        affichagePlacementNavire(j2.id);
        placementNavireJoueur(j2);
    }

    /**
     * Permet à l'IA de placer ces bateaux
     */
    public void placementNavireIA(){
        for (int i = 0 ; i < 3 ; i++){ //Pour chaque bateau, ici la rep fixe a 3 pour les tests
            try {
                plateau.getCase(j3.placementNavire(i,plateau.LARGEUR_PLATEAU,plateau.LONGUEUR_PLATEAU)).setNavire(j3.equipe.listeNavire.get(i));
            } catch (Exception e) {
                i--;
            }

        }
    }

    /**
     * Permet de placer les navires du joueur passé en param
     * @param j
     */
    public void placementNavireJoueur(JHumain j){
        for (int i = 0 ; i < 3 ; i++){ //Pour chaque bateau, ici la rep fixe a 3 pour les tests
            System.out.println(plateau);
            try {
                plateau.getCase(j.placementNavire(i)).setNavire(j.equipe.listeNavire.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                i--;
            }

        }
    }

    /* ------------------------- Fonctions d'affichage ---------------------------- */

    /**
     * Gere l'affichage du menu d'accueil
     */
    public void affichageAccueil(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--------------- BIENVENUE DANS UPSSINAVAL (1.00) -----------------\nPour un affichage optimal, nous recommandons, de mettre cet console en plein écran\n");
        System.out.println("Le joueur 1 joue l'" + j1 + "\nLe joueur 2 joue l'" + j2 + "\nL'IA joue l'" + j3);
        System.out.println("\nLes Dimensions du Plateau de jeu sont de " + plateau.LARGEUR_PLATEAU + "X" + plateau.LONGUEUR_PLATEAU);
        System.out.println("\n                                   [PRESS A KEY AND ENTER FOR NEXT]");
        System.out.println("-------------------------------------------------------------------");
        sc.next();
    }

    /**
     * Permet d'afficher les consignes de saisies
     * @param x (num du joueur)
     */
    public void affichagePlacementNavire(int x){
        System.out.println("-------------------- Placement bateaux J" + x + " -------------------------");
        System.out.println("Veuillez suivre la procédure suivante :\nTapez numéro de ligne et numéro de colonne a la suite du nom de bateau\n(Commence à 0)");
    }

    /**
     * Affiche la phrase de placement des navires par l'IA.
     */
    public void affichagePlacementNavireIA(){
        System.out.println("-------------------- Placement bateaux IA -------------------------");
        System.out.println("L'ordinateur est en train de placer ses bateaux...");
    }

    public void lancementTourDeJeuJoueur(Joueur j){
        j.equipe.initTour();
        affichageIntroTourDeJeu(j);
        tourDeJoueur(j);
    }

    public void tourDeJoueur(Joueur j){
        boolean finDeTour = false;
        Scanner sc = new Scanner(System.in);
        int choix=0;
        boolean saisieIncorrecte;
        do {
            if (j.tourFinis()) {
                finDeTour = true;
                continue;
            }
            if (j.estHumain()){
                affichagePrincipalTourDeJeu(j);
                choix = 0;
                saisieIncorrecte = true;
                while (saisieIncorrecte) {
                    saisieIncorrecte = false;
                    try {
                        choix = Integer.parseInt(sc.next()); //le next.int() me bouclait une erreur en permanance en cas de mauvaise saisie.
                        if (choix < 0 || choix > j.equipe.listeNavire.size()) saisieIncorrecte = true;
                    } catch (Exception e) {
                        saisieIncorrecte = true;
                        e.printStackTrace();
                    }
                }
            } else {
                choix++;
                if (choix > j.equipe.listeNavire.size()) choix = 1;
            }

            if (choix != 0) {
                Navire navire = j.equipe.listeNavire.get(choix - 1);
                if (j.estHumain()){
                    affichageInformationBateau(navire);
                }
                if (!navire.actionJoue) {
                    if (j.estHumain()){
                        saisieIncorrecte = true;
                        while (saisieIncorrecte) {
                            saisieIncorrecte = false;
                            try {
                                choix = Integer.parseInt(sc.next());
                                if (choix < 0 || choix > 3) saisieIncorrecte = true;
                            } catch (Exception e) {
                                saisieIncorrecte = true;
                                e.printStackTrace();
                            }
                        }
                    } else {
                        choix = random.nextInt(3) + 1;
                    }
                    if (choix != 0) {
                        lancerAction(j, navire, choix);;
                    }
                }
            } else {
                finDeTour = true;
                affichageFinDeTour(j);
            }
        } while (!finDeTour);
    }

    public boolean actionAttaquer(Joueur j, Navire navire){
        ArrayList<Navire> listeNavireEnemieCote = plateau.navireEnemieACote(navire);
        if (listeNavireEnemieCote.size() == 0){
            if (j.estHumain()) System.out.println("Aucun bateau a portee d'attaque du " + navire); //peut etre faire ça en exception
            return false;
        }
        Scanner sc = new Scanner(System.in);
        Navire nCible;
        int choix;
        boolean erreur;
        do {
            if (j.estHumain()) affichageAttaque(listeNavireEnemieCote);
            try {
                if (j.estHumain()) choix = Integer.parseInt(sc.next());
                else choix = random.nextInt(listeNavireEnemieCote.size())+1;
                nCible = listeNavireEnemieCote.get(choix-1);
                navire.attaque(nCible);
                if (nCible.PV <= 0){
                    majJeuNavireDetruit(nCible);
                }
                erreur = false;
            } catch (Exception e) {
                e.printStackTrace();
                erreur = true;
            }
        } while (erreur);
        return true;
    }

    public void majJeuNavireDetruit(Navire navire){
        plateau.supprimerNavire(navire);
        Joueur j;
        if (navire.idEquipe == 1) j=j1;
        else if (navire.idEquipe == 2) j=j2;
        else j=j3;
        j.equipe.supprimerNavire(navire);
        System.out.println(j.equipe.listeNavire);
    }

    public void affichageAttaque(ArrayList<Navire> listeNavire){
        System.out.println("Les navires a portée d'attaque sont :");
        for (int i = 0 ; i < listeNavire.size() ; i++){
            System.out.println( i+1 + ": " + listeNavire.get(i).stringStatsCible() + " " + plateau.stringCoordonee(listeNavire.get(i)));
        }
    }

    public void actionPecher(Joueur j, Navire navire) { //FAIRE ACTION PECHER
        if (j.estHumain()) System.out.println("Le bateau lance les filets...");
        if (plateau.getCase(navire).isOccupeProfondeur()){
            Navire nCible = plateau.getCase(navire).getOccupantProfondeur();
            if (j.estHumain()) System.out.println("ça Mord !\n"+ "Vous avez pris possesion du " + nCible);
            j.equipe.listeNavire.add(nCible);
            if (nCible.idEquipe == 1){
                j1.equipe.supprimerNavire(nCible);
            } else if (nCible.idEquipe == 2){
                j2.equipe.supprimerNavire(nCible);
            } else {
                j3.equipe.supprimerNavire(nCible);
            }
            nCible.idEquipe = navire.idEquipe;
        } else {
            if (j.estHumain()) System.out.println("...il n'y a rien ici");
        }
    }

    public void lancerAction(Joueur j,Navire navire, int choix){
        if (choix == 1){
            if (navire.statutNavire == StatutNavire.CHALUTIER){
                actionPecher(j,navire);
                navire.actionJoue = true;
            } else {
                navire.actionJoue = actionAttaquer(j,navire);
            }
        } else if (choix == 2){
            navire.actionJoue = actionSeDeplacer(j,navire);
        } else {
            navire.seReparer();
            navire.actionJoue = true;
        }
    }

    public boolean actionSeDeplacer(Joueur j, Navire navire){
        ArrayList<Position> listePosCase = plateau.PositionCaseDisponibleAutour(navire);
        int choix;
        if (j.estHumain()){
            if (listePosCase.size() == 0) {
                System.out.println("Aucun deplacement possible pour " + navire);
                return false;
            }
            Scanner sc = new Scanner(System.in);
            boolean erreur;
            do {
                System.out.println("Les cases libres pour se deplacer sont :");
                System.out.println(plateau.stringCaseDisponibleAutour(listePosCase));
                try {
                    choix = Integer.parseInt(sc.next());
                    plateau.deplacerNavireSurCase(navire,listePosCase.get(choix-1));
                    erreur = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    erreur = true;
                }
            } while (erreur);
        } else {
            plateau.deplacerNavireSurCase(navire,listePosCase.get(random.nextInt(listePosCase.size())));
        }
        return true;
    }

    public void affichageFinDeTour(Joueur j){
        System.out.println("Fin du tour du J" + j.id);
        System.out.println("-----------------------");
    }

    public void affichageIntroTourDeJeu(Joueur j){
        System.out.println("\n-------------------- C'est au tour du J" + j.id + " -------------------------");
        if (j.estHumain()) {
            System.out.println("Pour chacun de vos navires vous pouvez choisir entre 3 actions : Attaquer, Deplacer, Reparer\n" +
                    "Tapez le numero correspondant à un bateau pour entrer dans son menu d'action.\n" +
                    "Tapez 0 puis Entree pour finir votre tour");
        } else {
            System.out.println("L'IA joue ses coups...");
        }

    }

    public void affichagePrincipalTourDeJeu(Joueur j){
        System.out.println(plateau);
        System.out.println("=================");
        System.out.println(j.equipe.stringListeNavire());
        System.out.println("=================");
    }

    public void affichageInformationBateau(Navire navire){
        Position pos = plateau.getPosNavire(navire);
        System.out.println("\n============= " + navire + " =============");
        System.out.println(navire.stringStats() + "\nActuellement situé sur la case " + pos.x + "," + pos.y + "\n");
        if (navire.actionJoue){
            System.out.println("Ce bateau a deja joue pendant ce tour. Veuillez en selectionner un autre.");
        } else {
            System.out.println("Quelle action voulez vous faire ? (Tapez 0 pour retourner au plateau de jeux)");
            System.out.println("1: " + stringAffichageAttaquerOuPecher(navire) + "\n2: Se déplacer\n3: Réparer son bateau\n");
        }

    }

    public String stringAffichageAttaquerOuPecher(Navire navire){
        if (navire.statutNavire == StatutNavire.CHALUTIER){
            return "Pêcher";
        }
        return "Attaquer un navire";
    }

    public boolean testGagnant(Joueur[] tab , int x){
        boolean toutesLesAutreFlotteDetruit = true;
        for (int i = 0 ; i < 3 ; i++){
            if ((i != x) && (tab[i].equipe.listeNavire.size() != 0)){
                toutesLesAutreFlotteDetruit = false;
            }
        }
        return toutesLesAutreFlotteDetruit;
    }

    public static void main(String[] args) {

        //INITIALISATION GLOBALE DU JEU
        Jeu jeu = new Jeu();
        jeu.initialisation();

        //Initialisation des variables nécessaires au bon déroulement de la boucle principale du jeu
        boolean gagnant = false;
        Joueur tableauJoueur[] = {jeu.j1,jeu.j2,jeu.j3};
        int cpt = -1;

        //Placement Initial des navires
        jeu.affichageAccueil();
        jeu.placementNavireJoueursIA();

        //debut de tour (deplacement ou attaquer)
        while (!gagnant){
            cpt = (cpt + 1) % 3;
            jeu.lancementTourDeJeuJoueur(tableauJoueur[cpt]); //faire IA
            System.out.println(jeu.plateau);
            gagnant = jeu.testGagnant(tableauJoueur,cpt);
        }
        System.out.println("LE GAGNANT EST : J"+ tableauJoueur[cpt].id);
    }
}

/*
METTRE LES ATTRIBUTS IMPORTANT EN PRIVATE
EVITER LES . . . , utilsier une sous fonction
 */