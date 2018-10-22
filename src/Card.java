
 class Card {//this is a card class
	int rank; //the number
	int suit; //the suit
	int imaginarySuit; //used to emulate a fake suit when checking playable cards after suit change.
	
	static String[] ranks = { "narf", "Ace", "2", "3", "4", "5", "6","7", "8", "9", "10", "Jack", "Queen", "King" };
	static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
	
	Card() {
		suit = -1;
		rank = -1;
		imaginarySuit = -1;

	}
	
	Card(int s,int r){
		suit = s;
		rank = r;
		imaginarySuit = suit;
	}
	
	//the textbook questions kinda used both of these so I kind of just kept them both.
	public String toString (){
		return (ranks[this.rank] + " of " + suits[this.suit]);
	}

	public static void printCard(Card card) {
		System.out.println (ranks[card.rank] + " of " + suits[card.suit]);
		
	}
}
