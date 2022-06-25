import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private static ArrayList<Card> deck = new ArrayList<>();
    private static ArrayList<Card> discard = new ArrayList<>();
    private static int size;
    private static int random;

    /**
     * Generates a new deck of 108 cards.
     * Will reset <font color="orange">discard</font> as well as <font color="orange">deck</font> before generating.
     * After generation, <font color="orange">deck</font> will automatically be shuffled.
     */
    public static void generate(){
        // Checks in deck and discard are empty
        if (!deck.isEmpty()){
            deck.clear();
        }
        if (!discard.isEmpty()){
            discard.clear();
        }

        //Adds zeros
        for (int i = 1; i < 5; i++) {
            deck.add(new Card(i, "0"));
        }
        //Adds numbers 1-9
        for (int i = 0; i < 2; i++) {
            for (int v = 1; v < 5; v++) {
                for (int j = 1; j < 10; j++) {
                    deck.add(new Card(v,String.valueOf(j)));
                }
            }
        }
        //Adds special Cards
        for (int i = 0; i < 2; i++) {
            for (int v = 1; v < 5; v++) {
                deck.add(new Card(v,"Draw Two"));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int v = 1; v < 5; v++) {
                deck.add(new Card(v,"Reverse"));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int v = 1; v < 5; v++) {
                deck.add(new Card(v,"Skip"));
            }
        }
        //Adds wild cards
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(5,"Wild"));
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(5,"Wild Draw Four"));
        }

        shuffle();
    }

    /**
     * Shuffles the deck using {@link Collections#shuffle(List)} to shuffle in order to make game more fair.
     */
    public static void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * Randomly selects card from ArrayList <font color="orange">deck</font> and removes from deck.
     * @return Drawn card
     */
    public static Card draw()
    {
        // Should it just draw from the top of the deck? Cause it's already shuffled
        random = (int) (Math.random() * deck.size());
        Card c = deck.get(random);
        deck.remove(random);
        return c;
    }

    /**
     * Randomly selects <font color="orange">num</font> card from deck and removes from deck.
     * @param num Number of cards to draw
     * @return List of cards drawn
     */
    public static Card[] draw(int num)
    {
        Card[] cards = new Card[num];
        for (int i = 0; i < num; i++)
        {
            random = (int) (Math.random()*deck.size());
            cards[i] = deck.get(random);
            deck.remove(random);
        }
        return cards;
    }

    /**
     * Adds a card to discarded list where card will be out of play.
     * @param card Card to be added to the discard list
     */
    public static void discard(Card card)
    {
        discard.add(0,card);
    }
    
    /**
     * Adds multiple cards to the discarded list where cards will be out of play.
     * @param cards Cards to be added to the discard list
     */
    public static void discard(Card[] cards)
    {
        for (Card c:cards) {
            discard.add(0,c);
        }
    }

    /**
     * Recycles cards from discard into deck, leaving top card in discard.
     */
    public static void recycleDiscard(){
        Card topCard = getTopCard();
        discard.remove(0);
        deck.addAll(discard);
        discard.clear();
        discard(topCard);
        for (Card c:deck) {
            if (c.getType().equals("Wild") || c.getType().equals("Draw Four")){
                c.setColor(5);
            }
        }
        shuffle();
    }
    
    public static Card getTopCard(){
        return discard.get(0);
    }

    public static ArrayList<Card> getDeck()
    {
        return deck;
    }

    public static ArrayList<Card> getDiscarded()
    {
        return discard;
    }

    /**
     * Static version of toString
     * @return ArrayList deck as a string
     */
    public static String deckString(){
        String deckString = "";
        for (Card c:deck) {
            deckString += c.toString() + " ";
        }
        return deckString;
    }

    /**
     * Static version of toString
     * @return ArrayList discard as a string
     */
    public static String discardString(){
        String discardString = "";
        for (Card c:discard) {
            discardString += c.toString() + " ";
        }
        return discardString;
    }

}













