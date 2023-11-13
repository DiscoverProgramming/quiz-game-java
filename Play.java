import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

class Play {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner read = new Scanner(new File("QA.txt"));
        Scanner in = new Scanner(System.in);

        List<String> list = new ArrayList<String>();
        Random random = new Random();

        int correct = 0;
        int wrong = 0;

        while (true) {
            try {
                list.add(read.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }

        while (true) {
            int randomNumber = random.nextInt((list.size() - 1) + 1);
            System.out.print(list.get(randomNumber).split("<>")[0] + "\n: ");
            String input = in.nextLine();
            if (!input.equals("!q")) {
                if (input.equals(list.get(randomNumber).split("<>")[1])) {
                    System.out.println("Correct!");
                    correct += 1;
                } else {
                    System.out.println("Wrong!");
                    wrong += 1;
                }
            } else {
                System.out.println("Correct: " + correct + ", Wrong: " + wrong + ", Average: " + Math.multiplyExact(Math.divideExact(correct, Math.addExact(correct, wrong)), 100));
                break;
            }
        }
        

        in.close();
    }
}