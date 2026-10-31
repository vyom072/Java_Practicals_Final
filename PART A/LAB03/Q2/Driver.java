public class Driver {
    public static void main(String[] args) {
        Card[] input = {new Card("Ace", "Spades"), new Card("Queen", "Hearts"),
                        new Card("Ace", "Spades")};
        Card[] cards = new Card[input.length];
        int size = 0;
        for (Card c : input) {
            boolean duplicate = false;
            for (int i = 0; i < size; i++) {
                if (c.equals(cards[i])) { duplicate = true; break; }
            }
            if (duplicate) {
                System.out.println("Duplicate found: " + c); break;
            }
            cards[size++] = c;
        }
    }
}
