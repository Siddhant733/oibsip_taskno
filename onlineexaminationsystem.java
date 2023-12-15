import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    String username;
    String password;
    String profile;

    User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }
}

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class OnlineExaminationSystem {
    static Map<String, User> users = new HashMap<>();
    static Map<String, Question> questions = new HashMap<>();
    static String currentUser;
    static int score = 0;

    public static void main(String[] args) {
        initializeUsers();
        initializeQuestions();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Examination System!");

        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                login(scanner);
                showMenu(scanner);
            } else if (choice == 2) {
                System.out.println("Thank you for using the Online Examination System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (users.containsKey(username) && users.get(username).password.equals(password)) {
            currentUser = username;
            System.out.println("Login successful. Welcome, " + currentUser + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
            login(scanner);
        }
    }

    private static void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Update Profile and Password\n2. Start Exam\n3. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateProfileAndPassword(scanner);
                    break;
                case 2:
                    startExam(scanner);
                    break;
                case 3:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateProfileAndPassword(Scanner scanner) {
        System.out.print("Enter new profile: ");
        String newProfile = scanner.next();
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();

        users.get(currentUser).profile = newProfile;
        users.get(currentUser).password = newPassword;

        System.out.println("Profile and password updated successfully!");
    }

    private static void startExam(Scanner scanner) {
        score = 0;
        Timer timer = new Timer();
        int timeLimit = 300; 

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Exam will be auto-submitted.");
                showResult();
                scanner.close();
                System.exit(0);
            }
        }, timeLimit * 1000);

        for (Question question : questions.values()) {
            displayQuestion(question);
            int userAnswer = scanner.nextInt();
            if (userAnswer == question.correctAnswer) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
            }
        }

        timer.cancel();
        showResult();
    }

    private static void displayQuestion(Question question) {
        System.out.println("\n" + question.question);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println((i + 1) + ". " + question.options[i]);
        }
        System.out.print("Select your answer: ");
    }

    private static void showResult() {
        System.out.println("\nExam completed!");
        System.out.println("Your score: " + score + " out of " + questions.size());
        currentUser = null;
    }

    private static void logout() {
        System.out.println("Logout successful. Goodbye, " + currentUser + "!");
        currentUser = null;
    }

    private static void initializeUsers() {
        users.put("user1", new User("user1", "password1", "Student"));
        users.put("admin", new User("admin", "admin123", "Administrator"));
    }

    private static void initializeQuestions() {
        questions.put("q1", new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Rome"}, 1));
        questions.put("q2", new Question("Which programming language is Java?", new String[]{"C++", "Java", "Python", "Ruby"}, 2));
        questions.put("q3", new Question("What is the largest planet in our solar system?", new String[]{"Mars", "Jupiter", "Earth", "Venus"}, 2));
    }
}
