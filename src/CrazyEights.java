import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

///$$$$$$                                               /$$$$$$$$ /$$           /$$         /$$             
///$$__  $$                                             | $$_____/|__/          | $$        | $$             
//| $$  \__/  /$$$$$$  /$$$$$$  /$$$$$$$$ /$$   /$$      | $$       /$$  /$$$$$$ | $$$$$$$  /$$$$$$   /$$$$$$$
//| $$       /$$__  $$|____  $$|____ /$$/| $$  | $$      | $$$$$   | $$ /$$__  $$| $$__  $$|_  $$_/  /$$_____/
//| $$      | $$  \__/ /$$$$$$$   /$$$$/ | $$  | $$      | $$__/   | $$| $$  \ $$| $$  \ $$  | $$   |  $$$$$$ 
//| $$    $$| $$      /$$__  $$  /$$__/  | $$  | $$      | $$      | $$| $$  | $$| $$  | $$  | $$ /$$\____  $$
////|  $$$$$$/| $$     |  $$$$$$$ /$$$$$$$$|  $$$$$$$      | $$$$$$$$| $$|  $$$$$$$| $$  | $$  |  $$$$//$$$$$$$/
//\______/ |__/      \_______/|________/ \____  $$      |________/|__/ \____  $$|__/  |__/   \___/ |_______/ 
//                                      /$$  | $$                     /$$  \ $$                             
//                                     |  $$$$$$/                    |  $$$$$$/                             
//                                      \______/                      \______/          


// Matthew M-B
// 02/20/2018
//
//Welcome to the my game of crazy eight's	
//This version heavily uses recursion to operate.
//

public class CrazyEights {
    static int intPlayerNum; //Number of players in the game.
    static Deck deck = new Deck(); //the main deck
    static Deck playedPile = new Deck(true); //0 because size should start at 0.
    static int intPlayerTurn = -1; //current players turn
    static ArrayList < Deck > hands = new ArrayList();
    final static short CARDPERPLAYER = 8; //constant for the number of cards each player gets
    final static short QUEENPICKUP = 5; //how many you pick up when queen is played
    final static short TWOPICKUP = 2; // how many you pick up when 2 is played
    final static short SWITCHPLAYER = 1; // value added to switch turn

    /////////////SORRY METHODS ARE NOT ALL IN ORDER

    public static void main(String[] args) {
        setupGame(); //Methods in this method setup the game
        turn(intPlayerTurn + SWITCHPLAYER, 0); //pass the turn to next player(in this case first player)
    }
    public static void setupGame() { //method sets up game (shuffles, creates hands etc.)
        System.out.println("Welcome to Crazy Eights!"); //welcome message
        System.out.println("-------------------------------------------------------------------------------------");
        howManyPlayers(); // ask how many players
        shuffleDeck(deck); //shuffle the deck
        CreateHands(); //fill the hands array with the same number of decks as players
        FillHands(); // fill the hands with cards
        pickFirstCard(); //flip top card off deck
    }
    public static void howManyPlayers() { //asks how many players
        int i = 0; //temp variable to hold inut
        System.out.print("How many players would you like to play with?(4-6):"); // prompt

        //catch anything that isnt a number between 4 and 6... if its invalid call the method again.
        try {
            Scanner in = new Scanner(System.in); //scanner to take input
            i = in .nextInt(); //take input
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number, enter a valid number");
            howManyPlayers();
        } catch (InputMismatchException ex) {
            System.out.println("Invalid number, enter a valid number.");
            howManyPlayers();
        }
        if (i > 6 || i < 4) {
            System.out.println("Invalid number, enter a valid number.");
            howManyPlayers();
        }

        intPlayerNum = i; // if the input is valid, the total players is equal to the input
    }
    public static void turn(int playerNum, int numberOfPickup) { //called to start a players turn


        //**** this is important to understanding how I wrote the code
        //**** playerNum is a variable that will go up with each turn, so it represents the index of the current player.
        //**** numberOfPickup is a parameter so I could enable stacking with the recursive method.
        //**** pickedUp boolean exists because my program compares the players deck to the last card. If a turn is skipped, and an 8 was the last card down, '
        //     it with insinuate the current player played an 8... so I added this to make sure that doesnt happen


        boolean pickedUp = false; // if this player picked up this will become true.

        System.out.println("-------------------------------------------------------------------------------------"); //format message
        System.out.println("-------------------------------------------------------------------------------------"); //format message
        System.out.println("It is player " + (playerNum + 1) + "'s turn."); //format message... index starts at 0 so I display players starting at 1... so 1-4
        //   System.out.println("---"); //format message


        if (numberOfPickup != 0) { // if last player placed a card that is a pickup card, 
        //    if (numberOfPickup > playedPile.cards.size()) { // check to see if theres enough cards to pick up
                for (int i = 0; i <= numberOfPickup - 1; i++) { //pick up the number of cards specified by last card
                    pickUp(playerNum); //pick up that many cards...
         //       }
         //   } else {
          //      for (int i = 0; i <= playedPile.cards.size() - 1; i++) { // if there are not enough cards to pick up, pick up as many as possible.
            //        pickUp(playerNum);
           //    }
            }
        }
        System.out.println("---"); //format message

        if (deck.cards.size() <= 0) { //if deck is empty reset it.
            resetDeck();
        }

        displayTopCard(); //say which card is on the top of discard pile.
        printHand(playerNum); //print hand so player knows what cards it has.

        if (canPlay(playerNum) == true) { // if you can play... call play card
            System.out.print("Enter index of card you'd like to play:"); //format message
            playCard(playerNum);
        } else {
            System.out.println("You can't play any cards, pick up."); //format message
            pickUp(playerNum); //if you cant play pick up a card
            pickedUp = true; // you have picked up
        }


        //YOUR TURN IS OVER, SO CHECK IF YOUR OUR OF CARDS


        /////***** THIS IS SUPER IMPORTANT. TURN IS RECALLED IN checkMove AFTER IT CHECKS WHAT EFFECTS THE NEXT PLAYER WILL HAVE. EX PICK UP 2, SKIP TURN, ETC.
        if (isWinner(playerNum) == false) {
            checkMove(playerNum, numberOfPickup, pickedUp); // check what effects your played card will have on the next turn. ****TURN IS CALLED IN HERE
        } else { // otherwise this player won
            System.out.println("----------------------------------------------"); //format message
            System.out.println("Player " + (playerNum + 1) + "'has WON THE GAME!!!!!!!!!");
            System.out.println("----------------------------------------------"); //format message
        }
    }
    public static boolean isWinner(int playerNum) { //check if the current players hand is out of cards.
        if (hands.get(playerNum).cards.size() <= 0) { //if theres nothing in the hand of that player...
            return true; // they won!!!!
        } else {
            return false; // if they still have stuff they didnt
        }

    }
    public static void checkMove(int playerNum, int numberOfPickup, boolean hasPickedUp) { //Check what the effects of the last card play are. THIS IS CALLED IN TURN!!!

        Card topCard = playedPile.cards.get(playedPile.cards.size() - 1); // this is the last played card. we can use this to know how many the next player should pick up.

        if (playerNum < intPlayerNum - 1) { //if adding 1 to player number does not exceed array length
            if (topCard.rank == 12 && topCard.suit == 3) { //if it was a queen of spades
                if (numberOfPickup != 0) { // if the pickup num was not 0...
                    turn(playerNum + SWITCHPLAYER, 5 + numberOfPickup); //stack the pickup!!!!!!
                } else {
                    turn(playerNum + SWITCHPLAYER, 5); // if there isnt, its just the queen pickup
                }

            } else if (topCard.rank == 2) { //if last played card had a 2
                if (numberOfPickup != 0) { //if the pick up num was not 0
                    turn(playerNum + SWITCHPLAYER, 2 + numberOfPickup); // then stack the pickup!!!
                } else {
                    turn(playerNum + SWITCHPLAYER, 2); //otherwise just make the pickup 2
                }

            } else if (topCard.rank == 8 && hasPickedUp == false) { //if card is an 8 then you can change suit
                switchSuit(); //change suit
                turn(playerNum + SWITCHPLAYER, 0); //then change turn

            } else if (topCard.rank == 11) { // if jack
                System.out.println("Jack has been played!.. next player's turn has been skipped");
                if (playerNum + 2 > intPlayerNum - 1) { //if adding 2 would go beyond the index length... there is only one possible case of this and therefore we jump to the only possible outcome. 
                    turn(0, 0);
                } else {
                    turn(playerNum + 2, 0);
                }
            } else { //if your card isnt special then just pass the turn
                turn(playerNum + SWITCHPLAYER, 0);
            }


            /////// if its last player... send it back to player 0.////////////////////////   (comments are same just now the player resets to player 0.)
        } else if (playerNum >= intPlayerNum - 1) {
            if (topCard.rank == 12 && topCard.suit == 3) {
                if (numberOfPickup != 0) {
                    turn(0, 5 + numberOfPickup);
                } else {
                    turn(0, 5);
                }

            } else if (topCard.rank == 2) {
                if (numberOfPickup != 0) {
                    turn(0, 2 + numberOfPickup);
                } else {
                    turn(0, 2);
                }

            } else if (topCard.rank == 8 && hasPickedUp == false) {
                switchSuit();
                turn(0, 0);
            } else if (topCard.rank == 11) {
                System.out.println("Jack has been played!.. next player's turn has been skipped");
                turn(SWITCHPLAYER, 0); //your the last player.. therefore you skip player one and force it to the player 2 slot
            } else {
                turn(0, 0);
            }
        }
    }
    public static void pickUp(int playerNum) { //pick up card from deck method
        if (deck.cards.size() > 0) { // of deck has cards
            hands.get(playerNum).cards.add(deck.cards.get(0)); //take the card
            System.out.print("Picked up..");
            System.out.println(deck.cards.get(0));
            deck.cards.remove(0); //remove it from deck
        } else {
            resetDeck(); //if not enough cards, put discards back in deck.
        }

    }
    public static void switchSuit() { // if you play an eight, this method will change your suit.
        int i = 0; // temp variable to hold input
        System.out.println("[0] Clubs");
        System.out.println("[1] Diamonds");
        System.out.println("[2] Hearts");
        System.out.println("[3] Spades");
        System.out.print("Enter the index of the new suit:");

        //catch anything thats not one of the indexes, if invalid recall method
        try {
            Scanner in = new Scanner(System.in); //scanner to take input
            i = in .nextInt(); //take input
        } catch (NumberFormatException ex) {
            System.out.print("Invalid index, enter a valid index.");
            switchSuit();
        } catch (InputMismatchException ex) {
            System.out.print("Invalid index, enter a valid index.");
            switchSuit();
        }
        if (i > 3 || i < 0) {
            System.out.print("Invalid index, enter a valid index.");
            switchSuit();
        }

        chooseSuit(i); //if valid confirm the suit change


    }
    public static void chooseSuit(int aSuit) { //give the card an imaginary suit
        //**** cards have an imaginary suit that is given to them when an eight is played. It is reset when the turn it is played on is over.
        Card lastCard = playedPile.cards.get(playedPile.cards.size() - 1);
        lastCard.imaginarySuit = aSuit;
    }
    public static void playCard(int playerNum) { // play your card
        int i = 0;
        //catch the non indexes, recall if its not an index.
        try {
            Scanner in = new Scanner(System.in); //scanner to take input
            i = in .nextInt(); //take input
        } catch (NumberFormatException ex) {
            System.out.print("Invalid index, enter a valid index.");
            playCard(playerNum);
        } catch (InputMismatchException ex) {
            System.out.print("Invalid index, enter a valid index.");
            playCard(playerNum);
        }
        if (i > (hands.get(playerNum).cards.size() - 1) || i < 0) {
            System.out.print("Invalid index, enter a valid index.");
            playCard(playerNum);
        }

        Card topCard = playedPile.cards.get(playedPile.cards.size() - 1); // last played card
        System.out.println("---");
        System.out.println("Entered.... " + i);


        if (isNotImaginary(topCard)) { // if no eights were played last turn, and you dont need to match an imaginary suit
            if (hands.get(playerNum).cards.get(i).suit == topCard.suit || hands.get(playerNum).cards.get(i).rank == topCard.rank || hands.get(playerNum).cards.get(i).rank == 8) {
                System.out.print("Played...");
                System.out.println(hands.get(playerNum).cards.get(i));
                playedPile.cards.add(hands.get(playerNum).cards.get(i)); //discard pile gets this card
                hands.get(playerNum).cards.remove(i); //remove from hand

            } else {
                System.out.print("Invalid index, enter a valid index.");
                playCard(playerNum);
            }
        } else { // if you need to compare imaginary suits
            if (hands.get(playerNum).cards.get(i).suit == topCard.imaginarySuit || hands.get(playerNum).cards.get(i).rank == topCard.rank || hands.get(playerNum).cards.get(i).rank == 8) {
                System.out.print("Played...");
                System.out.println(hands.get(playerNum).cards.get(i));
                playedPile.cards.get(playedPile.cards.size() - 1).imaginarySuit = topCard.suit; //Reset the imaginary suit so if the card comes up again it acts as a normal card.
                playedPile.cards.add(hands.get(playerNum).cards.get(i));
                hands.get(playerNum).cards.remove(i);

            } else {
                System.out.print("Invalid index, enter a valid index."); // else recall cus its invalid input
                playCard(playerNum);
            }
        }




    }
    public static boolean canPlay(int playerNum) { //returns true if you can play a card
        //*** if a player doesnt have cards and needs to pick up or not. If it returns true you can play a card.

        Card topCard = playedPile.cards.get(playedPile.cards.size() - 1);
        if (isNotImaginary(topCard)) {
            for (int i = 0; i < hands.get(playerNum).cards.size(); i++) {
                if (hands.get(playerNum).cards.get(i).suit == topCard.suit || hands.get(playerNum).cards.get(i).rank == topCard.rank) {
                    return true;
                } else if (hands.get(playerNum).cards.get(i).rank == 8) { //you can always play an 8.
                    return true;
                }
            }

        } else { //if 
            for (int i = 0; i < hands.get(playerNum).cards.size(); i++) {
                if (hands.get(playerNum).cards.get(i).suit == topCard.imaginarySuit || hands.get(playerNum).cards.get(i).rank == topCard.rank) {
                    return true;

                } else if (hands.get(playerNum).cards.get(i).rank == 8) { //you can always play an 8.
                    return true;

                }
            }
        }

        return false;
    }
    public static void printDeck(Deck deck) { //test Method to print a deck (test)
        for (int i = 0; i < deck.cards.size(); i++) {
            Card.printCard(deck.cards.get(i));
        }
    }
    public static void pickFirstCard() { // takes first card off top of deck when game starts
        playedPile.cards.add(deck.cards.get(0)); // flip top card so first player knows what to play.
        deck.cards.remove(0); //remove top card from main deck
    }
    public static void displayTopCard() { //aethetic method that prints an instruction to the player. It tells you what card was played last turn.
        Card lastCard = playedPile.cards.get(playedPile.cards.size() - 1);
        String[] suits = {
            "Clubs",
            "Diamonds",
            "Hearts",
            "Spades"
        };

        if (isNotImaginary(lastCard)) {
            System.out.println("The card in the play pile is...");
            Card.printCard(lastCard);
        } else { // if an eight was played display a special message.
            System.out.println("The new suit is.." + suits[lastCard.imaginarySuit] + ".");
        }
    }
    public static void shuffleDeck(Deck d) { //shuffles the deck
        for (int i = 0; i < d.cards.size() - 1; i++) {
            int x = (int) Math.floor(Math.random() * d.cards.size());
            Collections.swap(d.cards, i, x); //swap a card with a random array index, the length of the array-th times.
        }
    }
    public static void FillHands() { //fills the hands with cards at start of game.
        int CardsPerHand = 0; //how many cards you have put into the current hand
        for (int i = 0; i < hands.size();) { //add cards to each hand
            if (CardsPerHand < CARDPERPLAYER) { //if you have less then the amount of cards each player should have
                hands.get(i).cards.add(deck.cards.get(0)); // take top card
                deck.cards.remove(0); //remove top card from main deck
                CardsPerHand++; // there is now one more card in the current hand

            } else {
                CardsPerHand = 0; // if you have the right number of cards you switch to the next hand by increasing i.
                i++;
            }


        }

    }
    public static void CreateHands() { //creates the same number of hands as their are players

        for (int i = 0; i < intPlayerNum; i++) {
            hands.add(new Deck(true));
        }
    }
    public static void printHands() { //test Method for printing out all hands
        for (int i = 0; i < hands.size(); i++) {
            System.out.println("hand " + (i + 1) + " ----------------");
            for (int j = 0; j < hands.get(i).cards.size() - 1; j++) {
                System.out.println(hands.get(i).cards.get(j));
            }
        }
    }
    public static void printHand(int playerNum) { //prints the hand for the player in console
        Card topCard = playedPile.cards.get(playedPile.cards.size() - 1);
        System.out.println("---");
        System.out.println("Player " + (playerNum + 1) + "'s Current Hand:");
        for (int j = 0; j < hands.get(playerNum).cards.size(); j++) {
            if (isNotImaginary(topCard)) {
                if (hands.get(playerNum).cards.get(j).suit == topCard.suit || hands.get(playerNum).cards.get(j).rank == topCard.rank) {
                    System.out.println("[" + (j) + "] " + hands.get(playerNum).cards.get(j));
                } else if (hands.get(playerNum).cards.get(j).rank == 8) { //you can always play an 8.
                    System.out.println("[" + (j) + "] " + hands.get(playerNum).cards.get(j)); /////////////8 is an exception.. you can always play 8.
                } else {
                    System.out.println("[ ] " + hands.get(playerNum).cards.get(j));
                }
            } else {
                if (hands.get(playerNum).cards.get(j).suit == topCard.imaginarySuit || hands.get(playerNum).cards.get(j).rank == topCard.rank) {
                    System.out.println("[" + (j) + "] " + hands.get(playerNum).cards.get(j));
                } else if (hands.get(playerNum).cards.get(j).rank == 8) { //you can always play an 8.
                    System.out.println("[" + (j) + "] " + hands.get(playerNum).cards.get(j)); /////////////8 is an exception.. you can always play 8.
                } else {
                    System.out.println("[ ] " + hands.get(playerNum).cards.get(j));
                }
            }
        }

    }
    public static void resetDeck() { //if deck is empty take discarded cards and re add them
        for (int i = 0; i < playedPile.cards.size() - 1; i++) { //for all discarded cards..
            System.out.println(playedPile.cards.size());
            deck.cards.add(playedPile.cards.get(i)); //add to deck
            playedPile.cards.remove(i); //remove from discards

        }
        shuffleDeck(deck); //reshuffle deck once discards are added.
    }
    public static boolean isNotImaginary(Card card) { //was an eight played, "the boolean"

        if (card.suit == card.imaginarySuit) { // if there wasnt an eight.. return true.
            return true;
        } else {
            return false;
        }
    }

}