import java.util.Objects;
public class Card {
    private String rank, suit;
    Card(String rank, String suit) { this.rank = rank; this.suit = suit; }
    public String toString() { return rank + " of " + suit; }
    public boolean equals(Object o) {
        if (!(o instanceof Card c)) { return false; }
        return rank.equals(c.rank) && suit.equals(c.suit);
    }
    public int hashCode() { return Objects.hash(rank, suit); }
}
