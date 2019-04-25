import java.util.Scanner;
public class ArrangementHandler {
    private static Arrangement arrangement;

    Arrangement a = new Arrangement("Firmafest", "Julefrokost", "Husk æbleskiver", 1, 5);

    public static void main(String[] args) {

        arrangement = Arrangement.makeArrangement();
        Scanner choice = new Scanner(System.in);
        int userChoice = choice.nextInt();

        while(userChoice > 0) {

            switch (userChoice) {
                case 1:
                    System.out.println("");

                break;

                case 2:

                break;

                case 3:

                break;

                default: System.out.println("Not defined.");
            }
            System.out.println("Not a value number, try again.");
            arrangement.makeArrangement();
            userChoice = choice.nextInt();
        }
    }

}
