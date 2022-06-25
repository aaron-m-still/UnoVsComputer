import java.util.ArrayList;
public class Player {
    private ArrayList<Card> hand = new ArrayList<Card>();
    public Player ()
    {
        Card[] c = Deck.draw(7);
        for (int i = 0; i < 7; i++)
        {
            hand.add(c[i]);
        }
    }

    public ArrayList<Card> getHand()
    {
        return hand;
    }

    public void addCard(Card c)
    {
        hand.add(c);
    }

    /**
     *
     * @param n 1-n
     */
    public Card removeCard(int n)
    {
        Card c = hand.get(n-1);
        hand.remove(n-1);
        return c;
    }

    @Override
    public String toString() {
        String str = "";
        for (Card c:hand) {
            str += c.toString() + " ";
        }
        return str;
    }
}