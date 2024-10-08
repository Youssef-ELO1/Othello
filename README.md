# Othello

Othello est une implémentation du jeu de plateau Othello (également connu sous le nom de Reversi) en Java. Le jeu propose une interface graphique JavaFX pour une expérience utilisateur interactive.

## Installation

Assurez-vous d'avoir Java 17 installé sur votre système. Vous pouvez également utiliser un IDE compatible avec Maven pour importer et exécuter le projet.

1. Clonez ce dépôt :
git clone : https://git.esi-bru.be/2023-2024/atlir4/d221/g56065.git

2. Naviguez vers le répertoire du projet :

cd Projet\Othello_56065\src\main\java\g56065\atlir\othello.

3. Compilez et exécutez le projet à l'aide de Maven :
mvn clean compile 

mvn clean javafx:run



## Utilisation

Lancez l'application et suivez les instructions à l'écran pour jouer à Othello. Vous pouvez choisir les dimensions du tableau, le mode de jeu (joueur contre stratégie ou 2 joueurs), et même sélectionner une stratégie spécifique lorsque nécessaire.

## Structure du Projet

Le projet est organisé comme suit :

- `src/main/java`: Contient le code source Java.
- `g56065.atlir.othello`: Package racine du projet.
 - `Othello.java`: Classe principale pour exécuter le jeu Othello en mode console.
 - `OthelloJavaFx.java`: Classe principale pour exécuter le jeu Othello avec l'interface graphique JavaFX.
 - `model`: Package contenant les classes définissant le modèle de jeu (plateau, pièce, joueur, etc.).
 - `view`: Package contenant les classes définissant l'interface utilisateur (JavaFX).
 - `strategy`: Package contenant les classes définissant les différentes stratégies de jeu.
- `src/test/java`: Contient les tests unitaires Java.
- `pom.xml`: Fichier de configuration Maven pour la gestion des dépendances et le build du projet.

## Dépendances

Le projet utilise Maven pour la gestion des dépendances. Les dépendances principales sont les suivantes :

- `org.openjfx:javafx-controls:13`: Bibliothèque JavaFX pour la création d'interfaces graphiques.
- `org.openjfx:javafx-maven-plugin:0.0.4`: Plugin Maven pour exécuter des applications JavaFX.

## Contributeurs

- El Ouahabi Youssef G56065
