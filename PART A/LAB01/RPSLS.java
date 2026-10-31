import java.util.Random;
import java.util.Scanner;
enum Move { ROCK, PAPER, SCISSORS, LIZARD, SPOCK }
public class RPSLS {
    static int winner(Move a, Move b) {
        if (a == b) { return 0; }
        boolean win = switch (a) {
            case ROCK -> b == Move.SCISSORS || b == Move.LIZARD;
            case PAPER -> b == Move.ROCK || b == Move.SPOCK;
            case SCISSORS -> b == Move.PAPER || b == Move.LIZARD;
            case LIZARD -> b == Move.PAPER || b == Move.SPOCK;
            case SPOCK -> b == Move.ROCK || b == Move.SCISSORS;
        };
        if (win) { return 1; }
        return -1;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        int player = 0, computer = 0;
        for (int i = 1; i <= 5; i++) {
            System.out.print("Your move: ");
            Move a = Move.valueOf(sc.next().toUpperCase());
            Move b = Move.values()[random.nextInt(5)];
            System.out.println("You: " + a + " Computer: " + b);
            int result = winner(a, b);
            if (result == 1) { player++; System.out.println("You win round"); }
            else if (result == -1) { computer++; System.out.println("Computer wins round"); }
            else { System.out.println("Tie"); }
        }
        if (player > computer) { System.out.print("You win "); }
        else if (computer > player) { System.out.print("Computer wins "); }
        else { System.out.print("Draw "); }
        System.out.println(player + "-" + computer);
    }
}
