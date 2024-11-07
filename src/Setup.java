import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Setup {
    private static Scanner input = null;

    private static String getQuestion(int index) {
        System.out.print("Question #" + (index + 1) + ": ");

        return input.nextLine();
    }

    private static String getAnswer(int index) {
        System.out.print("Answer for #" + (index + 1) + ": ");

        return input.nextLine();
    }

    public static void main(String[] args) {
        String csv_path = "quiz_data.csv";
        File csv_file = new File(csv_path);
        PrintWriter out = null;

        input = new Scanner(System.in);

        String spacer = "------------------------------------------------------";

        // try {
        //     out = new PrintWriter(new BufferedWriter(new FileWriter(csv_path)));
        // } catch (IOException e) {
        //     System.out.println("Error creating PrintWriter with " + csv_file.getAbsolutePath());
        //     System.out.println("This could be due to the file name exists but is a directory, does not exist but cannot be created, or cannot be opened for any other reason.");
        //     System.out.println("Aborting...");
        //     System.exit(1);
        // }

        List<Question> questions = new ArrayList<Question>();

        String problem = null;
        String answer = null;
        int index = 0;

        System.out.println("------ QUIZ GAME - SETUP ------");
        System.out.println("Type \'!q\' to quit.");

        while (true) {
            problem = getQuestion(index);

            if (!problem.equals("!q")) { // Read input, if input is not equal to "!q", add it to the list.
                answer = getAnswer(index);
                questions.add(new Question(index, problem, answer));
                index += 1;
            } else {
                break;
            }
        }

        if (csv_file.exists() && csv_file.length() != 0) {
            // csv_file exists but it has data in it
            System.out.println("\n");
            System.out.println(spacer);
            System.out.println("The CSV output file already exists, and is not empty.");
            System.out.println("\n");
            System.out.println("After this operation, " + csv_file.getAbsolutePath() + " will be overriden.");
            System.out.print("Do you want to continue? [y/N] ");
            String doOverride = input.nextLine();
            if (!doOverride.equalsIgnoreCase("y")) {
                System.out.println(spacer);
                System.out.println("Operation failed. User did not grant permission.");
                System.out.println("Aborting...");
                System.exit(1);
            } else {
                System.out.println("\n");
                System.out.println(spacer);
                System.out.println("Permission granted. Overriding " + csv_file.getAbsolutePath());
                try {
                    new PrintWriter(csv_path).close();
                    out = new PrintWriter(new BufferedWriter(new FileWriter(csv_path)));
                } catch (IOException e) {
                    System.out.println("Error during CSV file erase operation.");
                    e.printStackTrace();
                    System.exit(1);
                }

                System.out.println("\nCSV file erase operation successful.");
            }
        } else if (!csv_file.isFile()) {
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(csv_path)));
            } catch (IOException e) {
                System.out.println("Error attempting to create " + csv_file);
                e.printStackTrace();
                System.exit(1);
            }

            if (!csv_file.isFile()) {
                System.out.println("\n" + spacer);
                System.out.println("Error creating file " + csv_file.getAbsolutePath());
                System.out.println("Aborting...");
                System.exit(1);
            } else {
                System.out.println("Operation succesful! File created succesfully!");
            }
        } else if (csv_file.length() == 0) {
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(csv_path)));
            } catch (IOException e) {
                System.out.println("Operation failed. Error creating file.");
                e.printStackTrace();
            }

            System.out.println("\n" + spacer);
            System.out.println("CSV file exists, but is empty.");
            System.out.println("Overriding...");
            
            try {
                new PrintWriter(csv_path).close();
            } catch (FileNotFoundException e) {
                System.out.println("Error. File was not found."); // Just for safety
            }

            System.out.println("\nOperation successful!");
        } else {
            System.out.println("\nA unknown error occurred."); // Don't know yet what would have to happen for this else to be called.
            System.out.println("Aborting...");
            System.exit(1);
        }

        if (questions.isEmpty()) {
            System.out.println("\nErorr, questions list is empty. This could be due to not adding any questions.");
            System.out.println("Aborting...");
            System.exit(1);
        }

        for (Question question : questions) {
            out.write(Integer.toString(question.getIndex()) + ',' + question.getProblem() + ',' + question.getAnswer());
            out.write("\n");
        }

        System.out.println(spacer);
        System.out.println("Operation successful! Data was successfully writen to the file " + csv_file.getAbsolutePath());
        System.out.println("Quitting...");

        out.close();
        input.close();
        System.exit(0);
    }
}