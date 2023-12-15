import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int rangeStart = 1;
        int rangeEnd = 100;
        int maxAttempts = 8;
        int totalRounds = 3;
        int totalScore = 0;

        System.out.println("Welcome to Guess the Number!");

        for (int round = 1; round <= totalRounds; round++) {
            int targetNumber = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
            int attempts = 0;
            
            System.out.println("\nRound " + round + " - Guess a number between " + rangeStart + " and " + rangeEnd);

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                
                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    int roundScore = maxAttempts - attempts;
                    totalScore += roundScore;
                    System.out.println("Round Score: " + roundScore + " | Total Score: " + totalScore);
                    break;
                } else {
                    System.out.println("Incorrect guess. Try again.");

                    if (userGuess < targetNumber) {
                        System.out.println("Hint: The number is higher.");
                    } else {
                        System.out.println("Hint: The number is lower.");
                    }
                }

                attempts++;
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry! You have reached the maximum number of attempts. The correct number was: " + targetNumber);
            }

        }

        System.out.println("\nGame Over! Your final score is: " + totalScore);
        scanner.close();
    }
}
