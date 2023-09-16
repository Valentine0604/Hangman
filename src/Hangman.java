import java.util.*;

/**
 * Hangman is a word-guessing game where one player thinks of a word, and the other player tries
 * to guess it by suggesting letters. The game continues until the guessing player either
 * correctly guesses the word or makes too many incorrect guesses. This program implements
 * a text-based version of the Hangman game.
 *
 * Rules:
 * - The maximum number of allowed incorrect guesses is 6.
 * - Players can use a hint to reveal a random letter in the word (limited to one hint).
 * - Special characters and digits are not allowed in the word to be guessed.
 *
 * Instructions:
 * - Enter a word for your opponent to guess, ensuring it contains only letters.
 * - The game displays an initial empty gallows and starts the guessing phase.
 * - Players guess letters by entering them.
 * - Players can use a hint by entering '?' (limited to one hint).
 * - The game ends when the word is guessed correctly or the maximum incorrect guesses are reached.
 *
 * Enjoy playing Hangman!
 */

public class Hangman{

    /**
     * An array containing a list of words for the Hangman game.
     *
     * This array holds a predefined list of words that can be used as the target word
     * for the Hangman game. In the game, one of these words is randomly selected for
     * the player to guess.
     */
    public static final String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
            "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
            "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
            "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
            "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon",
            "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
            "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
            "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
            "wombat", "zebra"};

    /**
     * An array representing different stages of the Hangman gallows.
     *
     * This array contains ASCII art representations of the Hangman gallows at various stages of the game.
     * Each element in the array corresponds to a different number of incorrect guesses.
     * The gallows become progressively complete as the number of incorrect guesses increases.
     */
    public static final String[] gallows = {
            // ASCII art for the gallows with 0 incorrect guesses
            "+---+\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 1 incorrect guess
            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 2 incorrect guesses
            "+---+\n" +
                    "|   |\n" +
                    "O   |\n" +
                    "|   |\n" +
                    "    |\n" +
                    "    |\n" +
                    "=========",

            // ASCII art for the gallows with 3 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|   |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            // ASCII art for the gallows with 4 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "     |\n" +
                    "     |\n" +
                    " =========",

            // ASCII art for the gallows with 5 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/    |\n" +
                    "     |\n" +
                    " =========",


            // ASCII art for the gallows with 6 incorrect guesses
            " +---+\n" +
                    " |   |\n" +
                    " O   |\n" +
                    "/|\\  |\n" +
                    "/ \\  |\n" +
                    "     |\n" +
                    " ========="};

    public static final int MAX_MISSES = 6; //The maximum number of allowed incorrect guesses in the Hangman game. When a player reaches this limit, they lose the game.

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        char guess = ' '; //The current guessed letter in the Hangman game. It's initially set to a space character (' ').
        int hintCounter = 0; //The number of hints used by the player in the Hangman game. It's initially set to 0.
        int missed = 0; //The number of incorrect guesses made by the player in the Hangman game. It's initially set to 0.

        ArrayList<Character> misses = new ArrayList<>(); //Stores the missed letters in the Hangman game. It's initially an empty ArrayList.

        start();

        int mode = chooseGameMode();

        if(mode == 1){
            hangmanSinglePlayer(guess,missed,hintCounter,misses);
        }
        else{
            hangmanTwoPlayers(guess,missed,hintCounter,misses);
        }
        scanner.close();
    }

    /**
     * Function name: start
     *
     * Displays a welcome message and prompts the user to start the game.
     * Also, initializes the game display with an empty gallows.
     */

    public static void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hangman! Press anything to play!");
        printGallows(MAX_MISSES);
        scanner.nextLine();
    }

    /**
     * Function name: inputAWord
     *
     * Reads a word input from the user, ensuring it contains no special characters or digits.
     *
     * @return A valid word entered by the user.
     */

    public static String inputAWord(){
        Scanner scanner = new Scanner(System.in);

        String word = "";
        boolean isValid = false;

        while(!isValid){
            try{
                System.out.print("Your word: ");
                word = scanner.nextLine();
                NoSpecialCharactersOrDigitsException.validateWord(word);
                isValid = true;
            } catch(NoSpecialCharactersOrDigitsException e) {
                System.err.println(e.getMessage());
            }
        }

        return word;
    }

    /**
     * Function: chooseGameMode
     *
     * Allows the user to choose the game mode.
     *
     * This function displays a menu of available game modes and prompts the user to select one.
     * The user can choose between single player mode, where the word is chosen by the computer,
     * and two-player mode, where one player chooses the word, and the other player guesses it.
     *
     * @return The selected game mode: 1 for single player, 2 for two players.
     */
    public static int chooseGameMode(){
        int mode;
        Scanner scanner = new Scanner(System.in);

        do{
            try{
                System.out.println("Choose game mode: ");
                System.out.println("1. Single player [The word is being chosen by a computer]");
                System.out.println("2. Two players  [The word is being chosen by one of the players, and the other one tries to guess it]");
                System.out.print("Game mode: ");
                String input = scanner.nextLine();
                InvalidNumberException.validateNumber(input);

                mode = Integer.parseInt(input);
            } catch(InvalidNumberException e){
                System.err.println(e.getMessage());
                mode = -1;
            }
        }while(mode !=1 && mode != 2 && mode != 3);

        return mode;
    }

    /**
     * Function name: hangmanTwoPlayers
     *
     * Runs the two-player mode of the Hangman game.
     *
     * In the two-player mode, one player enters a word, and the other player attempts to guess it.
     * The game continues until the guessing player either correctly guesses the word or makes too many incorrect guesses.
     *
     * @param guess        The current guessed letter in the Hangman game.
     *                     It's initially set to a space character (' ').
     * @param missed       The number of incorrect guesses made by the guessing player.
     *                     It's initially set to 0.
     * @param hintCounter  The number of hints used by the guessing player in the Hangman game [max. 1].
     *                     It's initially set to 0.
     * @param misses       The list of letters that have been guessed incorrectly.
     *                     It's initially an empty ArrayList.
     */
    public static void hangmanTwoPlayers(char guess, int missed, int hintCounter, ArrayList<Character> misses){
        Scanner scanner = new Scanner(System.in);

        String word = inputAWord();
        char[] chosenWord = word.toCharArray();
        char[] guessArray = generateGuessArray(chosenWord);

        boolean checker;
        while(true) {
            if (guess == '?') {
                System.out.println("Current letter: ");
            } else {
                System.out.println("Current letter: " + guess);
            }
            System.out.print("Missed letters: ");
            printMissesArray(misses);
            System.out.print("\n");
            printGallows(missed);
            printGuessArray(guessArray);
            System.out.print("\n");
            if (hintCounter == 0) {
                System.out.println("Wanna use a hint? Press '?'");
            }
            System.out.print("\n");
            System.out.print("Your letter: ");

            try{
                guess = scanner.next().charAt(0);
                InvalidCharacterException.validateInput(guess);
            } catch(InvalidCharacterException e){
                System.err.println(e.getMessage());
                continue;
            }

            guess = Character.toLowerCase(guess);

            if (guess == '?' && hintCounter == 0) {
                hint(guessArray, chosenWord);
                hintCounter++;
                continue;
            } else if (guess == '?' && hintCounter != 0) {
                System.out.println("You can't use a hint once again :(");
                continue;
            }

            checker = checkGuess(guess, chosenWord);

            if (checker) {
                replaceLetters(guessArray, chosenWord, guess);
            } else {
                System.out.println("Wrong letter!");
                if (!checkMisses(guess, misses)) {
                    misses.add(guess);
                    missed++;
                }
            }

            if (isFinished(guessArray)) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You won!");
                break;
            }

            if (missed == 6) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You lost!");
                System.out.println("Word: " + word);
                break;
            }

        }

    }

    /**
     * Function name: hangmanSinglePlayer
     *
     * Runs the single-player mode of the Hangman game where the word is chosen by the computer.
     *
     * In the single-player mode, the computer randomly selects a word, and the player attempts to guess it.
     * The game continues until the player either correctly guesses the word or makes too many incorrect guesses.
     *
     * @param guess        The current guessed letter in the Hangman game.
     *                     It's initially set to a space character (' ').
     * @param missed       The number of incorrect guesses made by the player.
     *                     It's initially set to 0.
     * @param hintCounter  The number of hints used by the player in the Hangman game [max. 1].
     *                     It's initially set to 0.
     * @param misses       The list of letters that have been guessed incorrectly.
     *                     It's initially an empty ArrayList.
     */

    public static void hangmanSinglePlayer(char guess, int missed, int hintCounter, ArrayList<Character> misses){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int index = random.nextInt(words.length);
        char[] chosenWord = words[index].toCharArray();
        char[] guessArray = generateGuessArray(chosenWord);

        boolean checker;
        while(true) {
            if (guess == '?') {
                System.out.println("Current letter: ");
            } else {
                System.out.println("Current letter: " + guess);
            }
            System.out.print("Missed letters: ");
            printMissesArray(misses);
            System.out.print("\n");
            printGallows(missed);
            printGuessArray(guessArray);
            System.out.print("\n");
            if (hintCounter == 0) {
                System.out.println("Wanna use a hint? Press '?'");
            }
            System.out.print("\n");
            System.out.print("Your letter: ");

            try {
                guess = scanner.next().charAt(0);
                InvalidCharacterException.validateInput(guess);
            } catch (InvalidCharacterException e) {
                System.err.println(e.getMessage());
                continue;
            }

            guess = Character.toLowerCase(guess);

            if (guess == '?' && hintCounter == 0) {
                hint(guessArray, chosenWord);
                hintCounter++;
                continue;
            } else if (guess == '?' && hintCounter != 0) {
                System.out.println("You can't use a hint once again :(");
                continue;
            }

            checker = checkGuess(guess, chosenWord);

            if (checker) {
                replaceLetters(guessArray, chosenWord, guess);
            } else {
                System.out.println("Wrong letter!");
                if (!checkMisses(guess, misses)) {
                    misses.add(guess);
                    missed++;
                }
            }

            if (isFinished(guessArray)) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You won!");
                break;
            }

            if (missed == 6) {
                printGallows(missed);
                printGuessArray(guessArray);
                System.out.print("\n");
                System.out.println("You lost!");
                System.out.println("Word: " + words[index]);
                break;
            }

        }
    }

    /**
     * Function name: generateGuessArray
     *
     * Initializes and returns an array representing the current state of the word to guess.
     * The array is filled with underscores ('_') to represent unguessed letters.
     *
     * @param chosenWordArray The array containing the characters of the word to guess.
     * @return An array of underscores with the same length as the chosen word.
     */
    public static char[] generateGuessArray(char[] chosenWordArray) {
        char[] guessBoard = new char[chosenWordArray.length];

        for (int i = 0; i < chosenWordArray.length; i++) {
            guessBoard[i] = '_';
        }
        return guessBoard;
    }

    /**
     * Function name: checkGuess
     *
     * Checks if a guessed letter exists in the chosen word.
     *
     * @param guess The letter guessed by the player.
     * @param chosenWord The array containing the characters of the chosen word.
     * @return true if the guessed letter exists in the chosen word, otherwise false.
     */

    public static boolean checkGuess(char guess, char[] chosenWord) {

        for (char c : chosenWord) {
            if (guess == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function name: checkMisses
     *
     * Checks if a guessed letter has already been missed.
     *
     * @param guess The letter guessed by the player.
     * @param misses A list of previously missed letters.
     * @return true if the guessed letter has already been missed, otherwise true.
     */
    public static boolean checkMisses(char guess, ArrayList<Character> misses){
        if(misses.contains(guess)){
            return true;
        }
        return false;
    }

    /**
     * Funtion name: replaceLetters
     *
     * Replaces underscores in the guess board with the correct letter if the guess is correct.
     *
     * @param guessBoard The current state of the guess board.
     * @param chosenWord The word that is being guessed.
     * @param guess      The letter guessed by the player.
     */
    public static void replaceLetters(char[] guessBoard, char[] chosenWord, char guess) {

        for (int i = 0; i < chosenWord.length; i++) {
            if (guess == chosenWord[i] && guessBoard[i] == '_') {
                guessBoard[i] = guess;
            }
        }
    }

    /**
     * Function name: printGuessArray
     *
     * Prints the current state of the guess board.
     *
     * @param guessBoard The current state of the guess board to be printed.
     */

    public static void printGuessArray(char[] guessBoard) {
        for (int i = 0; i < guessBoard.length; i++) {
            System.out.print(guessBoard[i] + " ");
        }
    }

    /**
     * Funtion name: printMissesArray
     *
     * Prints the list of missed letters.
     *
     * @param misses The list of missed letters to be printed.
     */
    public static void printMissesArray(ArrayList<Character> misses) {
        for(Character c : misses){
            System.out.print(c + " ");
        }
    }

    /**
     * Funtion name: printGallows
     *
     * Prints the hangman gallows based on the number of misses.
     *
     * @param missed The number of missed guesses to determine the hangman gallows.
     */

    public static void printGallows(int missed) {
        System.out.println(gallows[missed]);
    }

    /**
     * Function name: hint
     *
     * Provides a hint in the hangman game by revealing a random letter in the word.
     *
     * @param guessBoard The current state of the guessing board.
     * @param chosenWord The word to guess.
     */

    public static void hint(char[] guessBoard, char[] chosenWord) {
        Random rand = new Random();
        boolean isFound = false;
        char letter = ' ';

        while (!isFound) {
            int index = rand.nextInt(chosenWord.length);
            if (guessBoard[index] == '_') {
                letter = chosenWord[index];
                replaceLetters(guessBoard,chosenWord,letter);
                isFound = true;
            }
        }
    }

    /**
     * Function name: isFinished
     *
     * Checks if the hangman game has been completed by verifying if there are no more underscores
     * in the guessing board.
     *
     * @param guessBoard The current state of the guessing board.
     * @return           True if the game is finished, false otherwise.
     */
    public static boolean isFinished(char[] guessBoard) {

        for (int i = 0; i < guessBoard.length; i++) {
            if (guessBoard[i] == '_') {
                return false;
            }
        }
        return true;
    }
}