import java.util.ArrayList;


class Deck {//this is a deck class
	ArrayList<Card> cards = new ArrayList();
	
	
	public Deck (boolean empty) {
	//contructor that makes an empty deck if you put true.
	}
	
	public Deck () { //a default deck adds the normal 52 cards
		
		for (int suit = 0; suit <= 3; suit++) {
			for (int rank = 1; rank <= 13; rank++) {
				cards.add(new Card (suit, rank));

			}
		}
	}
}