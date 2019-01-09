import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Deck {

    private List<Card> deck = new ArrayList<>();
    private Card.Suit[] suits = Card.Suit.values();
    private Card.Number[] numbers = Card.Number.values();

    //creates a full or empty deck
    public Deck(boolean gameDeck){
        if(gameDeck) {
            for (Card.Suit suit : suits) {
                for (Card.Number number : numbers) {
                    deck.add(new Card(suit, number));
                }
            }
            shuffle();
        }
    }

    public List<Card> getDeck(){
        return deck;
    }

    //randomizes deck order
    public void shuffle(){
        Random random = new Random();
        Card firstCard;
        Card secondCard;
        int firstIndex;
        int secondIndex;
        for(int i=0;i<deck.size();i++){
            firstCard = deck.get(i);
            secondCard = deck.get((int)(random.nextFloat()*(deck.size()-1)));
            firstIndex = deck.indexOf(firstCard);
            secondIndex = deck.indexOf(secondCard);
            deck.set(firstIndex,secondCard);
            deck.set(secondIndex,firstCard);
        }
    }

    //returns and removes top card of deck
    public Card deal(){
        Card card = deck.get(0);
        deck.remove(card);
        return card;
    }

    //prints all cards in deck
    public void printDeck(){
        for(Card card:deck){
            card.print();
        }
    }

    //moves everything in deck back one and adds card at begining
    public void shift(Card card){
        List<Card> temp = new ArrayList<>();
        temp.add(card);
        temp.addAll(deck);
        deck = temp;
    }

    public Card getCard(int index){
        if(index<deck.size()) {
            return deck.get(index);
        }else{
            System.err.print("Out of bounds in discard pile!");
        }
        return null;
    }

    //if deck runs out of cards, shuffles discard back into deck
    public void update(Deck discard){
        Card card;
        for(int i=1;i<discard.getDeck().size();i++){
            card = discard.getCard(i);
            deck.add(card);
            discard.getDeck().remove(card);
        }
        shuffle();
    }

    //checks deck for duplicate cards
    private void checkDuplicates(){
        for(Card card:deck){
            for(int i=0;i<deck.size();i++){
                if((!(deck.indexOf(card)==i))&&card==deck.get(i)){
                    System.out.println("EXTRA CARD");
                    card.print();
                }
            }
        }
    }

}
