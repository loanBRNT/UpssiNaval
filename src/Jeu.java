import Flotte.*;
import Statut.*;
import Support.*;
import Team.*;
import Exception.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jeu {
    private final Random random;

    private final Plateau plateau;

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
     * Permet d'initialiser les différents éléments nécéssaires au lancement du jeu : Support.Plateau, les équipes (rôles, + création des navires etc...)
     */
    public void initialisation(){

        plateau.initialisationPlateau();

        //CREATION D'UN TABLEAU POUR ATTRIBUER ALEATOIREMENT LES ROLES AUX JOUEURS
        StatutEquipe[] tableau = tirageAleatoireRole();

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
        StatutEquipe[] tableau = {null,null,null};
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
     * Fonction appelant les fonctions réalisant le placement des navires pour les joueurs et l'Team.IA.
     * L'Team.IA joue en premier pour éviter qu'elle donne des cases déjà occupés
     */
    public void placementNavireGen(Joueur[] tab){
        for (int i = 2 ; i >= 0 ; i--){
            affichagePlacementNavire(tab[i]);
            placementNavireJoueur(tab[i]);
        }
    }

    /**
     * Permet de placer les navires du joueur passé en param
     * @param j
     */
    public void placementNavireJoueur(Joueur j){
        for (int i = 0 ; i < 5 ; i++){
            if (j.estHumain()) System.out.println(plateau);
            try {
                plateau.getCase(j.placementNavire(i, Plateau.LARGEUR_PLATEAU, Plateau.LONGUEUR_PLATEAU)).setNavire(j.getNavireAvecRang(i));
            } catch (Exception e) {
                if (j.estHumain()) e.printStackTrace();
                i--;
            }
        }
    }

    /**
     * Initialie le tour du Team.Joueur avant de le lancer
     * @param j
     */
    public void lancementTourDeJeuJoueur(Joueur j){
        j.equipe.initTour();
        affichageIntroTourDeJeu(j);
        tourDeJoueur(j);
    }

    /**
     * La fonction principale du tour de jeu d'un joueur
     * @param j Le joueur dont c'est le tour
     */
    public void tourDeJoueur(Joueur j){
        boolean finDeTour = false;
        Scanner sc = new Scanner(System.in);
        int choix=0;
        boolean saisieIncorrecte;


        do {
            //on teste la fin de tour automatique
            if (j.tourFinis()) {
                finDeTour = true;
                continue;
            }

            //On recup ici le bateau qu'il veut jouer (grace à son rang+1). S'il tape 0, c'est la fin de tour prématuré
            if (j.estHumain()){
                affichagePrincipalTourDeJeu(j);
                choix = j.choixValeur(0,j.equipe.listeNavire.size());
            } else {
                //on fait cette manipulation pour s'assurer que l'Team.IA joue tous ses bateaux
                choix++;
                if (choix > j.equipe.listeNavire.size()) choix = 1;
            }

            //On regarde l'action qu'il veut faire avec son bateau

            if (choix != 0) {
                Navire navire = j.equipe.listeNavire.get(choix - 1);
                if (j.estHumain()){
                    affichageInformationBateau(navire);
                }

                //S'il n'a pas joué, on lui propose une liste d'action et il choisit
                if (!navire.getActionJouer()) {
                    choix = j.choixValeur(0,3);

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

    /**
     * Permet à un joueur de choisir quel navire attaquer à partir de son navire
     * @param j le jouueur dont c'est le tour
     * @param navire le navire du joueur
     * @throws IllegalActionException
     */
    public void actionAttaquer(Joueur j, Navire navire) throws IllegalActionException {
        ArrayList<Navire> listeNavireEnemieCote = plateau.navireEnemieACote(navire);
        if (listeNavireEnemieCote.size() == 0){
            throw new IllegalActionException("Aucun bateau a portee d'attaque du " + navire);
        }
        Navire nCible;
        int choix;
        boolean erreur;
        do {
            if (j.estHumain()) affichageAttaque(listeNavireEnemieCote);
            try {
                choix = j.choixValeur(1,listeNavireEnemieCote.size());
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
    }

    /**
     * Permet de mettre à jour les différents éléments du jeu lorsqu'un navire est détruit
     * @param navire le navire détruit
     */
    public void majJeuNavireDetruit(Navire navire){
        plateau.supprimerNavire(navire);
        Joueur j;
        if (navire.idEquipe == 1) j=j1;
        else if (navire.idEquipe == 2) j=j2;
        else j=j3;
        j.equipe.supprimerNavire(navire);
    }

    /**
     * Permet à un navire de pêcher
     * @param j le joueur dont c'est le tour
     * @param navire le navire en question
     */
    public void actionPecher(Joueur j, Navire navire) {
        System.out.println("Le " + navire + " lance les filets...");
        if (plateau.getCase(navire).isOccupeProfondeur()){
            Navire nCible = plateau.getCase(navire).getOccupantProfondeur();
            System.out.println("ça Mord !\n"+ "Le Team.Joueur " + j.id + " prend possesion du " + nCible);
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
            System.out.println("...il n'y a rien ici");
        }
    }

    /**
     * Lance l'action du navire selon choix
     * @param j le joueur dont c'est le tour
     * @param navire le navire à partir duquel on veut faire l'action
     * @param choix le numéro de l'action
     */
    public void lancerAction(Joueur j,Navire navire, int choix){
        try {
            if (choix == 1){
                if (navire.statutNavire == StatutNavire.CHALUTIER){
                    actionPecher(j,navire);
                    navire.setActionJoue();
                } else {
                    actionAttaquer(j,navire);
                }
            } else if (choix == 2){
                actionSeDeplacer(j,navire);
            } else {
                navire.seReparer();
            }
            navire.setActionJoue();
        } catch (IllegalActionException e){
            if (j.estHumain()) e.printStackTrace();
        }
    }

    /**
     * Permet à un navire de se déplacer
     * @param j le joueur dont c'est le tour
     * @param navire le navire en question
     * @throws IllegalActionException
     */
    public void actionSeDeplacer(Joueur j, Navire navire) throws IllegalActionException {
        ArrayList<Position> listePosCase = plateau.PositionCaseDisponibleAutour(navire);
        int choix;
        if (listePosCase.size() == 0) {
            throw new IllegalActionException("Aucun deplacement possible pour " + navire);
        }
        boolean erreur;
        do {
            if (j.estHumain()){
                System.out.println("Les cases libres pour se deplacer sont :");
                System.out.println(plateau.stringCaseDisponibleAutour(listePosCase));
            }
            try {
                choix = j.choixValeur(1,listePosCase.size());
                plateau.deplacerNavireSurCase(navire,listePosCase.get(choix-1));
                erreur = false;
            } catch (Exception e) {
                e.printStackTrace();
                erreur = true;
            }
        } while (erreur);
    }

    /**
     * Détermine si le joueur dont c'est le tour gagne la partie à l'issu de son tour
     * @param tab Le tableau des joueurs
     * @param x le rang du joueur dont c'était le tour
     * @return true s'il gagne, false sinon
     */
    public boolean testGagnant(Joueur[] tab , int x){
        boolean toutesLesAutreFlotteDetruit = true;
        for (int i = 0 ; i < 3 ; i++){
            if ((i != x) && (tab[i].equipe.listeNavire.size() != 0)){
                toutesLesAutreFlotteDetruit = false;
            }
        }
        return toutesLesAutreFlotteDetruit;
    }

    /* ------------------------- Fonctions d'affichage ---------------------------- */

    /**
     * Gere l'affichage du menu d'accueil
     */
    public void affichageAccueil(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--------------- BIENVENUE DANS UPSSINAVAL (1.00) -----------------\nPour un affichage optimal, nous recommandons, de mettre la console en plein écran\n");
        System.out.println("Le joueur 1 joue l'" + j1 + "\nLe joueur 2 joue l'" + j2 + "\nL'IA joue l'" + j3);
        System.out.println("\nLes Dimensions du Plateau de jeu sont de " + Plateau.LARGEUR_PLATEAU + "X" + Plateau.LONGUEUR_PLATEAU);
        System.out.println("\n                                   [PRESS A KEY AND ENTER FOR NEXT]");
        System.out.println("-------------------------------------------------------------------");
        sc.next();
    }

    /**
     * Permet d'afficher les consignes de saisies
     * @param j le joueur dont c'est le tour
     */
    public void affichagePlacementNavire(Joueur j){
        if (j.estHumain()){
            System.out.println("-------------------- Placement bateaux J" + j.id + " -------------------------");
            System.out.println("Veuillez suivre la procédure suivante :\nTapez numéro de ligne et numéro de colonne a la suite du nom de bateau\n(Commence à 0)");
        } else {
            System.out.println("-------------------- Placement bateaux IA -------------------------");
            System.out.println("L'ordinateur est en train de placer ses bateaux...");
        }
    }


    /**
     * Affiche la liste des navires a portee de l'attaque du navire
     * @param listeNavire la liste des navires a portee
     */
    public void affichageAttaque(ArrayList<Navire> listeNavire){
        System.out.println("Les navires a portée d'attaque sont :");
        for (int i = 0 ; i < listeNavire.size() ; i++){
            System.out.println( i+1 + ": " + listeNavire.get(i).stringStatsCible() + " " + plateau.stringCoordonee(listeNavire.get(i)));
        }
    }

    /**
     * Affiche la fin de tour
     * @param j le joueur dont le tour est terminé
     */
    public void affichageFinDeTour(Joueur j){
        System.out.println("Fin du tour du J" + j.id);
        System.out.println("-----------------------");
    }

    /**
     * Affiche l'introduction du tour de jeu selon si c'est un humain ou un Team.IA
     * @param j le joueur dont le tour commence
     */
    public void affichageIntroTourDeJeu(Joueur j){
        System.out.println("\n-------------------- C'est au tour du J" + j.id + " -------------------------");
        if (j.estHumain()) {
            System.out.println("""
                    Pour chacun de vos navires vous pouvez choisir entre 3 actions : Attaquer, Deplacer, Reparer
                    Tapez le numero correspondant à un bateau pour entrer dans son menu d'action.
                    Tapez 0 puis Entree pour finir votre tour""");
        } else {
            System.out.println("L'IA joue ses coups...");
        }

    }

    /**
     * Affichage du plateau de jeu et la liste des navires du joueur dont c'est le tour
     * @param j le joueur en question
     */
    public void affichagePrincipalTourDeJeu(Joueur j){
        System.out.println(plateau);
        System.out.println("=================");
        System.out.println(j.equipe.stringListeNavire());
        System.out.println("=================");
    }

    /**
     * Affiche les informations du navire + la liste de ces actions disponibles
     * @param navire le navire dont on veut afficher les informations
     */
    public void affichageInformationBateau(Navire navire){
        Position pos = plateau.getPosNavire(navire);
        System.out.println("\n============= " + navire + " =============");
        System.out.println(navire.stringStats() + "\nActuellement situé sur la case " + pos.x + "," + pos.y + "\n");
        if (navire.getActionJouer()){
            System.out.println("Ce bateau a deja joue pendant ce tour. Veuillez en selectionner un autre.");
        } else {
            System.out.println("Quelle action voulez vous faire ? (Tapez 0 pour retourner au plateau de jeux)");
            System.out.println("1: " + stringAffichageAttaquerOuPecher(navire) + "\n2: Se déplacer\n3: Réparer son bateau\n");
        }

    }

    /**
     * Permet d'afficher soit l'action pêcher, soit attaquer
     * @param navire le navire dont on veut afficher l'action
     * @return "Pêcher" ou "Attaquer un navire"
     */
    public String stringAffichageAttaquerOuPecher(Navire navire){
        if (navire.statutNavire == StatutNavire.CHALUTIER){
            return "Pêcher";
        }
        return "Attaquer un navire";
    }

    public static void main(String[] args) {

        //INITIALISATION GLOBALE DU JEU
        Jeu jeu = new Jeu();
        jeu.initialisation();

        //Initialisation des variables nécessaires au bon déroulement de la boucle principale du jeu
        boolean gagnant = false;
        Joueur[] tableauJoueur = {jeu.j1,jeu.j2,jeu.j3};
        int cpt = -1;

        //Placement Initial des navires
        jeu.affichageAccueil();
        jeu.placementNavireGen(tableauJoueur);

        //debut de tour (deplacement ou attaquer)
        while (!gagnant){
            cpt = (cpt + 1) % 3;
            jeu.lancementTourDeJeuJoueur(tableauJoueur[cpt]); //faire Team.IA
            System.out.println(jeu.plateau);
            gagnant = jeu.testGagnant(tableauJoueur,cpt);
        }
        System.out.println("LE GAGNANT EST : J"+ tableauJoueur[cpt].id);
    }
}