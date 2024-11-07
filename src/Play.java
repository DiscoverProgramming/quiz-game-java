import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Play {

    private static boolean isBetween(double value, int min, int max) {
        return value >= min && value <= max; // TODO: Write unit tests for this function
    }

    private static String calculateGrade(int incorrect, int total) {
        double percentage;

        if (incorrect < total) {
            percentage = ((double) (total - incorrect) / total) * 100;
        } else {
            percentage = 0;
        }

        if (isBetween(percentage, 97, 100)) {
            return "A+";
        } else if (isBetween(percentage, 93, 96)) {
            return "A";
        } else if (isBetween(percentage, 90, 92)) {
            return "A-";
        } else if (isBetween(percentage, 87, 89)) {
            return "B+";
        } else if (isBetween(percentage, 83, 86)) {
            return "B";
        } else if (isBetween(percentage, 80, 82)) {
            return "B-";
        } else if (isBetween(percentage, 77, 79)) {
            return "C+";
        } else if (isBetween(percentage, 73, 76)) {
            return "C";
        } else if (isBetween(percentage, 70, 72)) {
            return "C-";
        } else if (isBetween(percentage, 67, 69)) {
            return "D+";
        } else if (isBetween(percentage, 63, 66)) {
            return "D";
        } else if (isBetween(percentage, 60, 62)) {
            return "D-";
        } else if (isBetween(percentage, 0, 59)) {
            return "F";
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BufferedReader br = null;

        List<Question> questions = new ArrayList<Question>();

        int correct = 0;

        String line = "";
        String splitBy = ",";

        try {
            br = new BufferedReader(new FileReader("quiz_data.csv"));
        } catch (IOException e) {
            System.out.println("Error creating BufferedReader...");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            while ((line = br.readLine()) != null) {
                String[] questionData = line.split(splitBy);
                questions.add(new Question(Integer.parseInt(questionData[0]), questionData[1], questionData[2]));
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("------ QUIZ GAME - PLAY ------");
        System.out.println("Type \'!q\' to quit.");

        String user_in = null;

        Question currentQuestion = null;
        int quizIndex = 0;

        while (quizIndex < questions.size()) {
            currentQuestion = new Question(questions.get(quizIndex).getIndex(), questions.get(quizIndex).getProblem(),
                    questions.get(quizIndex).getAnswer());

            System.out.println("Question: " + currentQuestion.getProblem() + " (" + (currentQuestion.getIndex() + 1)
                    + "/" + questions.size() + ")");

            System.out.print("> ");
            user_in = input.nextLine();

            if (!user_in.equals("!q")) {
                if (user_in.equals(currentQuestion.getAnswer())) {
                    System.out.println("Correct.");
                    correct += 1;
                } else {
                    System.out.println("Incorrect.");
                }
            } else {
                System.out.println("Quiz has " + (questions.size() - currentQuestion.getIndex()) + " problems left.");
                System.out.println("Are you sure you want to quit? [y/N]");

                String doQuit = input.nextLine();
                if (!doQuit.equalsIgnoreCase("y")) {
                    System.out.println("Abortion canceled.");
                    continue; // Restar the while loop without increasing quizIndex, so we can restart the question.
                } else {
                    System.out.println("Quitting quiz...");
                    break;
                }
            }

            quizIndex++;
        }

        System.out.println("\nFinished quiz.");

        // We need to calculate incorrect here instead of inside if statement due to early quitting.
        int incorrect = questions.size() - correct;

        String percentage = null;
        if (correct == 0) {
            percentage = "0%";
        } else {
            percentage = Double.toString(((double) (questions.size() - incorrect) / questions.size()) * 100) + "%";
        }

        System.out.println("Amount correct: " + correct + "/" + questions.size() + " (" + percentage + ")");
        System.out.println("Grade: " + calculateGrade(incorrect, questions.size()));

        input.close();
    }
}