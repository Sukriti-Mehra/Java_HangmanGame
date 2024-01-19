
//Importing required packages 
import java.io.*;
import java.util.*;

public class HangmanGame {
    public static void main(String[] args) throws FileNotFoundException {

        // Initialize Scanner for user input
        Scanner keyboard = new Scanner(System.in);
        boolean continuePlaying;

        // Greet the user
        System.out.println("Here you go !! ");

        do {
            playHangman(keyboard);

            // Ask the user if they want to continue playing until a valid response is
            // provided
            while (true) {
                System.out.print("Do you want to continue? (yes/no): ");
                String continuePlayingInput = keyboard.nextLine().toLowerCase();

                if (continuePlayingInput.equals("yes") || continuePlayingInput.equals("y")) {
                    continuePlaying = true;
                    break;
                } else if (continuePlayingInput.equals("no") || continuePlayingInput.equals("n")) {
                    continuePlaying = false;
                    break;
                } else {
                    System.out.println("Invalid answer. Please enter 'yes' or 'no'.");
                }
            }

        }

        while (continuePlaying);

        System.out.println("Thanks for playing!");
    }

    private static void playHangman(Scanner keyboard) throws FileNotFoundException {
        String word;
        Scanner scanner = null;

        // Greet the user
        System.out.println("Here you go !! ");

        // Ask the user to enter the number of players
        System.out.print("Enter number of players: ");
        String players = keyboard.nextLine();

        if (players.equals("1")) {
            while (true) {
                System.out.println("You want to guess numbers(n) or words(w) ? ");
                String type = keyboard.next().toLowerCase();
                keyboard.nextLine();

                // Initialize a Scanner to read 'question' from a file based on the game type
                if (type.equals("n")) {
                    scanner = new Scanner(
                            new File("C:/Users/Sukriti/OneDrive/Desktop/JavaProjects/Hangman/Numbers.txt"));
                    break;
                } else if (type.equals("w")) {
                    scanner = new Scanner(
                            new File("C:/Users/Sukriti/OneDrive/Desktop/JavaProjects/Hangman/dictionary.txt"));
                    break;
                } else {
                    System.out.println("Invalid type! Please enter 'n' for numbers or 'w' for words.");
                }
            }

            // Read words from the file and select a random word/number
            ArrayList<String> words = new ArrayList<>();
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }

            Random random = new Random();// Creating a 'Random' type varible to randomly select a word from file.
            word = words.get(random.nextInt(words.size()));
        }

        else {
            // If there are two players, ask the first player to enter a word
            System.out.println("Player 1, please enter your word:");
            word = keyboard.nextLine();

            // Additional formatting for better user experience
            for (int i = 0; i < 30; i++) {
                System.out.println("\n");
            }
            System.out.println("Ready for player 2! Good luck!");
        }

        List<Character> playerGuesses = new ArrayList<>();
        Integer wrongCount = 0;
        boolean playNextRound = true;

        while (playNextRound) {

            // Print the hanged man based on the number of wrong guesses
            printHangedMan(wrongCount);

            // Check if the player has lost
            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The answer was: " + word);
                playNextRound = false;
            }

            // Check if the player has won
            else if (printWordState(word, playerGuesses)) {
                System.out.println("You win!");
                playNextRound = false;
            }

            else { // Get the player's guess and update the wrong count
                if (!getPlayerGuess(keyboard, word, playerGuesses)) {
                    wrongCount++;
                }

                // Check if the player has won
                if (printWordState(word, playerGuesses)) {
                    System.out.println("You win!");
                    playNextRound = false;
                }

                // Ask the player to guess the entire word
                System.out.println("Please enter your guess for the word:");
                if (keyboard.nextLine().equals(word)) {
                    System.out.println("You win!");
                    playNextRound = false;
                }

                else {
                    System.out.println("Nope! Try again.");
                }
            }
        }
    }

    // Print the hanged man based on the number of wrong guesses
    private static void printHangedMan(Integer wrongCount) {
        System.out.println(" -------");
        System.out.println(" | |");
        if (wrongCount >= 1) {
            System.out.println(" O");
        }
        if (wrongCount >= 2) {
            System.out.print("\\");
            if (wrongCount >= 3) {
                System.out.println(" /");
            } else {
                System.out.println("");
            }
        }
        if (wrongCount >= 4) {
            System.out.println(" |");
        }
        if (wrongCount >= 5) {
            System.out.print("/");
            if (wrongCount >= 6) {
                System.out.println(" \\");
            } else {
                System.out.println("");
            }
        }

        System.out.println("");
        System.out.println("");
    }

    // Get the player's guess for a letter
    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) {
        System.out.println("Please enter a letter:");
        String letterGuess = keyboard.nextLine();
        playerGuesses.add(letterGuess.charAt(0));
        return word.contains(letterGuess);
    }

    // Print the current state of the word with correct guesses revealed
    private static boolean printWordState(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
        return (word.length() == correctCount);
    }
}
