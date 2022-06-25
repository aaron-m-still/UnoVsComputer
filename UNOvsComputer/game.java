import java.util.Locale;
import java.util.Scanner;

public class Game {

    private static Scanner in = new Scanner(System.in);
    final private static int totalPlayers = 2;
    private static Player[] players;
    private static int playerTurn;
    private static boolean win = false;
    private static Card topCard;
    private static boolean reverse;
    private static boolean specialWasPlayed = false;

    public static void main(String[] args) {
        boolean playAgain;
        do {
            game();

            System.out.println("Enter y to play again");
            playAgain = playAgain();
        } while (playAgain);
    }
    
    private static void game()
    {
        Deck.generate();
        topCard = Deck.draw();
        Deck.discard(topCard);
        if (topCard.getColor()==5) {
            topCard = Deck.draw();
            Deck.discard(topCard);
        }
        players = new Player[totalPlayers];
        for (int i = 0; i < totalPlayers; i++)
        {
            players[i] = new Player();
        }
        playerTurn = 1;
        while(!win) {
            nextPlayer();
        }
    }

    private static boolean playAgain()
    {
        if (in.hasNext())
        {
            String s = in.next();
            if (s.toLowerCase(Locale.ROOT).equals("y"))
            {
                return true;
            }
        }
        return false;
    }


    private static void playerMove(){
        Scanner in = new Scanner(System.in);
        System.out.println("Player " + (playerTurn) + "'s Hand:\n" + players[playerTurn-1].getHand());
        System.out.println("Top of deck " + topCard);
        System.out.println("What would you like to do?");
        System.out.println("1-Play Card, 2-Draw Card, -1-Quit");
        if (in.hasNextInt()){
            int playOrDraw = in.nextInt();
            if (playOrDraw==1){
                playCard();
            } else if (playOrDraw==2){
                drawCard();
            } else {
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        }
    }

    private static void aiMove(){
        boolean aiPlayedCard=false;
        System.out.println("Player " + (playerTurn) + "'s Hand:\n" + players[playerTurn-1].getHand());
        System.out.println("Top of deck " + topCard);
        for (int i=0; i<players[playerTurn-1].getHand().size(); i++){
            if(isValid(players[playerTurn-1].getHand().get(i), players[playerTurn-1])) {
                topCard = players[playerTurn-1].getHand().get(i);
                Deck.discard(players[playerTurn-1].getHand().get(i));
                players[playerTurn-1].getHand().remove(i);
                checkSpecialCards();
                aiPlayedCard=true;
                break;
            }
        }
        if (!aiPlayedCard){
            drawCard();
        }
    }
    
    private static void playCard(){
        boolean validMove = false;
        System.out.println("What card would you like to play?");
        System.out.println("Enter the number your card falls in your hand");
        System.out.println("So 1,2,3,4, or 5, etc...");
        do {
            if (in.hasNextInt()) {
                int cardToPlay = in.nextInt() - 1;
                if(isValid(players[playerTurn-1].getHand().get(cardToPlay), players[playerTurn-1])) {
                    validMove=true;
                    topCard = players[playerTurn-1].getHand().get(cardToPlay);
                    Deck.discard(players[playerTurn-1].getHand().get(cardToPlay));
                    players[playerTurn-1].getHand().remove(cardToPlay);
                    checkSpecialCards();
                }
            } else {
                in.nextLine();
            }
        } while(!validMove);
    }

    private static void drawCard(){
        players[playerTurn-1].addCard(Deck.draw());
    }

    private static boolean gameWon(){
        for(Player p:players){
            if (p.getHand().size() == 1){
                System.out.println("UNO!");
            } else if (p.getHand().size() == 0){
                win = true;
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(Card c, Player p){
        Card dis = topCard;
        if(c.getType().equals("Wild") || c.getType().equals("Wild Draw Four")){
            return true;
        }
        else if(c.getType().equals(dis.getType())){
            return true;
        }
        else if(c.getColor() == dis.getColor()){
            return true;
        }
        else{
            return false;
        }
    }

    private static void checkSpecialCards ()
    {
        String type = topCard.getType();
        switch (type) {
            case "Wild":
                wildCard();
                break;
            case "Draw Two":
                drawTwo();
                specialWasPlayed=true;
                break;
            case "Wild Draw Four":
                drawFour();
                specialWasPlayed=true;
                break;
            case "Reverse":
                reverse = !reverse;
                break;
            case "Skip":
                specialWasPlayed=true;
                break;
        }

    }

    private static void wildCard ()
    {
        System.out.println("What color would you like?");
        System.out.println("1-Red, 2-Green, 3-Blue, 4-Yellow");
        while (true) {
            if (in.hasNextInt())
            {
                int n = in.nextInt();

                if(n > 0 && n < 5)
                {
                    topCard.setColor(n);
                    break;

                }
            }

            in.nextLine();
            System.out.println("Invalid, try again");
        }
    }

    private static void drawTwo()
    {
        if (playerTurn==1) {
            players[playerTurn].getHand().add(Deck.draw());
            players[playerTurn].getHand().add(Deck.draw());
        } else if (playerTurn==2) {
            players[playerTurn - 2].getHand().add(Deck.draw());
            players[playerTurn - 2].getHand().add(Deck.draw());
        }
    }

    private static void drawFour()
    {
        wildCard();
        drawTwo();
        drawTwo();
    }

    private static void nextPlayer()
    {
        if (playerTurn==1) {
            playerMove();
            gameWon();
            if(specialWasPlayed){
                specialWasPlayed=false;
            } else {
                playerTurn=2;
            }
        } else if(playerTurn==2){
            aiMove();
            gameWon();
            if (specialWasPlayed){
                specialWasPlayed=false;
            } else {
                playerTurn=1;
            }
        }
    }
}


