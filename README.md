# UpssiNaval
Projet en Programmation Orienté Objet. Réalisé au cours de la première année à l'UPSSITECH en Système Robotique et Interactifs

Actuellement, l'interface graphique est réduit à la console.

***

## Contexte

3 équipes s'affrontent sur la mer : 2 sont joués par des joueurs humains, la dernière est joué "automatiquement" (via des random).

Les équipes sont divisées en deux parties : Les Bataillons (flotte militaire possédant de puissants Destroyer et Sous-Marin), les Pêcheurs (5 Chalutiers).
La répartition s'effectue de la manière suivante : 2 Bataillons et 1 Pêcheur. Les rôles des joueurs sont déterminés aléatoirement.

Le plateau de jeu peut être considére en 3D : Longueur - Largeur - Profondeur. Cette dernière permet à une case de contenir à la fois un navire en surface (Destroyer - Chalutier) et un navire en profondeur (Sous-Marin). Mais jamais 2 en surface, ou 2 en profondeur.

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

Action possible uniquement pour les Destroyers et Sous-Marin. Un navire peut en attaquer un autre du moment qu'il sont dans des cases adjacentes. Le navire attaquant inflige des dégats équivalents à sa valeur d'attaque au navire cible. Si le navire cible passe en dessous de la moitié de ces PV (points de vie), il devient "endommagé". S'il n'a plus de PV, il est détruit,et retiré du jeu.

Lorsque l'action est lancé, la console affiche au joueur la liste des navires ennemis adjacent au navire à partir duquel il lance l'action. Si elle est vide, on lève ici une exception. Le joueur entre une saisie, et recommence jusqu'à qu'elle soit correcte. Chaque erreur est ignorée ou bien, on lève une exception.

* Pêcher

Action possible uniquement pour les Chalutiers. Un navire peut pêcher sur la case en dessous de lui. Si la case sur laquelle le navire est, contient également un sous-Marin, alors ce dernier est "capturé" et appartient désormais au joueur ayant lancé l'action de pêcher. Il récupère le navire dans l'état (PV, ATT) dans lequel il se trouve. Il peut également lancer une action à partir de lui dans ce même tour.

Il n'y a aucune saisie à faire une fois que l'action a été lancé.

* Se déplacer

Un navire peut se déplacer sur une case adjacente du moment qu'elle n'est pas occupé par un navire du même "niveau" (Profondeur, Surface). 

Si aucune case n'est disponible autour, alors une exception est levée. Sinon La console affiche la liste des cases disponibles et le joueur saisit le numéro correspondant à la case où il souhaite se rendre. Il recoomence jusqu'à avoir une saisir valide. Chaque erreur lève une exception ou est ignorée.

* Se réparer

Un navire peut se réparer : c-à-d augmenter son nombre de PV de 20. Cependant il ne dépassera jamais son montant initial. Il peut donc quitter l'état endommagé s'il repasse au dessus de la moitié de ses PV initiaux.

Il n'y a aucune saisie à effectuer après avoir lancé l'action.

#### Gestion des Erreurs de Saisies

Le jeu utilise plusieurs exceptions personnalisées : Pour gérer un mauvais déplacement, un mauvais positionnement, une mauvaise action (liste de possibilitée vide).

Il utilise également les exceptions liées à la Classe JAVA Scanner ou bien à la Classe ArrayList en cas de mauvaise saisie (entier invalide, char ou string).

Chaque erreur est gérée et affiché. L'utilisateur est alors invité à retaper sa saisie. Cependant, si la saisie se déroule en plusieurs étapes (placement des navires), alors uniquement la saisie fausse est invalide (Exemple : je tape une ligne valide puis une colonne invalide, il n'y aura que la colonne à retaper).

D'une manière générale, quelques actions ne peut être annulé, et on ne peut revenir en arrière : Si vous lancer une action, elle est défintive. Vous ne pouvez quitter le menu d'attaquer ou de se déplacer sans en effectuer l'action (hormis s'il n'y a aucune possibilité).

#### Fin du Tour & Gagnant

A chaque fin du tour d'un joueur (ordinateur compris), le jeu recherche si, hormis le joueur qui vient de terminer son tour, les autres flottes ne possèdent plus de navires. Si c'est le cas, alors c'est gagné pour le joueur qui vient de terminer son tour. Sinon, on passe simplement le tour au joueur suivant.

#### Caractéristiques des Navires

TYPE : Le navirepeut être un Chalutier, un Destroyer ou un Sous-marin.

NIVEAU : Les navires ont deux niveaux (Pronfondeur, Surface). Deux du même niveau ne peuvent cohabiter sur la même case

PV : Les points de vie du navire (varie selon le type du navire), s'il tombe en dessous de la moitié il devient **enddomagé**. S'il tombe à 0, le navire est retiré du jeu.

DEGATS D'ATTAQUE : La valeur d'attaque (varie elle aussi selon le type du navire) est le montant que le navire infligera à sa cible lorsqu'il l'attaque.

CAPACITE DE DEPLACEMENT : fixé à 1 pour tous les navires, ils ne se déplacement dont que sur les cases adjacentes.

ETAT : un navire est endommagé s'il a moins de la moitié de ses PV. Un navire endommagé voit sa capacité d'attaque divisé par 2.

***

## Améliorations possibles

- Ajouter une capacité de déplacement variable selon les navires.
- Augmenter l'impact de l'état "endommagé" en réduisant les capacités de déplacement.
- Detecter un gagnant même si le tour du joueur n'est pas encore terminé (améliore la fluidité)
- Ne plus afficher le tour d'un joueur s'il n'a plus de navire dans sa flotte
- Permettre à l'ordinateur de ne plus jouer simplement aléatoirement