import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Setup {

    private static String get_question(Scanner in, int index) {
        System.out.print("Q" + index + ": ");
        return in.nextLine();
    }

    private static String get_answer(Scanner in, int index) {
        System.out.print("A" + index + ": ");
        return in.nextLine();
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("QA.txt", true)));

        // Initialize the main variables.
        List<String> list = new ArrayList<String>();
        int index = 0;

        System.out.println("Please input quiz questions.");

        while (true) {
            String question = get_question(in, index);
            if (!question.equals("!q")) { // Read input, if input is not equal to "!q", add it to the list.
                String answer = get_answer(in, index);
                list.add(question + "<>" + answer);
                index += 1;
            } else { // else break the loop.
                break;
            }
        }

        new PrintWriter("QA.txt").close();

        for (int i = 0; i < list.size(); i++) {
            out.println(list.get(i));
        }

        out.close();
        in.close();
    }
}