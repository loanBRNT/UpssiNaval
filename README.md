# UpssiNaval
Projet en Programmation Orienté Objet. Réalisé au cours de la première année à l'UPSSITECH en Système Robotique et Interactifs

Actuellement, l'interface graphique est réduit à la console.

***

## Contexte

3 équipes s'affrontent sur la mer : 2 sont joués par des joueurs humains, la dernière est joué "automatiquement" (via des random).

Les équipes sont divisées en deux parties : Les Bataillons (flotte militaire possédant de puissants Destroyer et Sous-Marin), les Pêcheurs (5 Chalutiers).
La répartition s'effectue de la manière suivante : 2 Bataillons et 1 Pêcheur. Les rôles des joueurs sont déterminés aléatoirement.

**Le but est d'être la dernière équipe dont la flotte n'est pas entièrement détruite**. Pour se faire, les navires des Bataillons peuvent attaquer et infliger des dommages aux autres bateaux. Les Chalutiers, quant à eux, peuvent pécher en dessous de leur position, si un Sous-Marin ennemi s'y trouve, ils en prennent possesion.
Sinon, tous les navires ont la capacité de se déplacer et de se réparer.

***

## Règles du jeu

### Déroulement

La partie commence en désignant les rôles des 2 joueurs et celui de l'ordinateur. Les joueurs sont ensuite invités à saisir les positions de leurs navires. Pour des soucis d'éfficacité, l'ordinateur place ses navires en premier, suvit du joueur 2, puis du joueur 1.

Le premier à jouer sera le joueur 1, puis le joueur 2, puis l'ordinateur.

Un tour se déroule de la manière suivante : 
1. _Le joueur voit ses navires affichés sous forme de liste avec un numéro._
   1. Il entre le numéro du navire duquel il souhaite effectuer une action.
      1. _Le joueur voit une liste d'action numérotée et adaptée au type du navire sélectionné_
         1. Il entre le numéro d'une action -> **Lancement de l'action** sélectionné à partir du Navire. A l'issue, retour au 1
         2. Il entre 0 et reviens à l'étape 1
      2. _Le navire a déjà effectué une action pendant ce tour, il est renvoyé au 1_
   2. Il entre 0 -> **Fin à son tour**.
2. _Le joueur n'a plus de navire à jouer_ -> **Fin du tour**.

L'ordinateur choisit une action aléatoirement pour chacun de ses navires.

### Les éléments clés

#### Placement des Navires

La console affiche le plateau ainsi que le navire à positionner. Le joueur saisit donc un premier entier correspondant au rang de la Ligne (commence à 0). Puis un entier correspondant au rang de la colonne (commence à 0).

Si la case n'est pas occupée (En surface si le navire est un Destroyer, Chalutier ou en profondeur si c'est un Sous-Marin [cf **Caractéristiques des Navires**]) le navire est positionné, sinon une exception est levée [cf **Gestion des Erreurs de Saisies**],et la saisie doit être recommencée.

#### Actions

* Attaquer
* Pêcher
* Se déplacer
* Se réparer

#### Gestion des Erreurs de Saisies

#### Fin du Tour & Gagnant

A chaque fin du tour d'un joueur (ordinateur compris), le jeu recherche si, hormis le joueur qui vient de terminer son tour, les autres flottes ne possèdent plus de navires. Si c'est le cas, alors c'est gagné pour le joueur qui vient de terminer son tour. Sinon, on passe simplement le tour au joueur suivant.

#### Caractéristiques des Navires

***

## Améliorations possibles

- Ajouter une capacité de déplacement variable selon les navires.
- Ajouter un état "endommagé" aux navires, réduisant leurs dégats d'attaque.