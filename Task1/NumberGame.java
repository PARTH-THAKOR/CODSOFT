// Number Game

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class NumberGame {

    // Random Number Genrator Function
    public static int randomNumberGenrator() {
        int randomNumber = new Random().nextInt(100);
        return randomNumber;
    }

    // User Input Accepter Function
    public static int userInputAccepter() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = bufferedReader.readLine();
        try {

            // Convert string input into int.
            int userInputNumber = Integer.parseInt(userInput);

            // Check number in range
            if (userInputNumber > 100 || userInputNumber < 0) {
                System.out.println("You Input Number is Not Between 1 - 100");

                // Here we return -0007 because function return type is int.
                return -0007;
            } else {
                return userInputNumber;
            }
        } catch (Exception e) {
            System.out.println("You Input is not a Number");

            // Here we return -0003 because function return type is int.
            return -0003;
        }
    }

    // Game Restart Function
    public static void restartGame(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("To restart the game, press enter");
        bufferedReader.readLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        main(args);
    }

    public static void main(String[] args) throws IOException {
        int randomNumber = randomNumberGenrator();

        // To win this game we need to guess the number in 7 turns.
        int turns = 0;
        System.out.println("GUESS THE NUMBER IN 7 TURNS.");

        while (true) {
            // Icrement turn value
            turns++;

            // check turns value
            if (turns < 7) {
                System.out.print("\nEnter Number between 1 - 100 :- ");
                int input = userInputAccepter();

                // Check input conditions
                if (input != -0007 && input != -0003) {
                    if (input > randomNumber) {
                        System.out.println("RESULT : Your Number is Greater...");
                    } else if (input < randomNumber) {
                        System.out.println("RESULT : Your Number is Smaller...");
                    } else {
                        System.out.println("\nRESULT : COGRETULATION YOU GUESSED THE NUMBER IN " + turns + " turns.");
                        restartGame(args);
                    }
                } else {
                    restartGame(args);
                }
            } else {
                System.out.println("\nRESULT : SORRY YOU CAN'T GEUSS THE NUMBER IN 7 TURNS.");
                restartGame(args);
            }
        }
    }

}
