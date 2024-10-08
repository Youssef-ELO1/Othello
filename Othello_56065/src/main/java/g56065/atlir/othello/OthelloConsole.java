package g56065.atlir.othello;

import g56065.atlir.othello.controller.Controler;
import g56065.atlir.othello.model.ColorPiece;
import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.strategy.RandomStrategy;
import g56065.atlir.othello.view.console.View;
import java.util.Scanner;


import g56065.atlir.othello.model.GameOthello;
import g56065.atlir.othello.strategy.RandomStrategy;
import g56065.atlir.othello.view.console.View;
import java.util.Scanner;


public class OthelloConsole {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        while (size < 3 || size > 15) {
            size = scanner.nextInt();
        }

        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            choice = scanner.nextInt();
        }

        GameOthello game;
        if (choice == 2) {
            game = new GameOthello(size, new RandomStrategy());
        } else {
            game = new GameOthello(size);
        }

        View view = new View(game);
        game.setView(view); 
        view.displayBoard(); 


        while (!game.isGameOver()) {
            if (game.getCurrentPlayer() == ColorPiece.BLACK) { 
                view.requestUserInput(); 
            } else if (choice == 2) { 
                game.getStrategy().strat(game);
            } 
        }
        scanner.close(); 
    }
}





