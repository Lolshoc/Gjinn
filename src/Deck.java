import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Deck {

    private List<Card> deck = new ArrayList<>();
    private Card.Suit[] suits = Card.Suit.values();
    private Card.Number[] numbers = Card.Number.values();

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

    public void shuffle(){
        List<Card> temp = deck;
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        int randomNumber;
        for(Card card:deck){
            randomNumber = (int)(random.nextFloat() * 52);
            while(numbers.contains(randomNumber)){
                if (numbers.size()==52){
                    break;
                }
                randomNumber = (int)(random.nextFloat() * 52);
            }
            numbers.add(randomNumber);
            temp.set(randomNumber,card);
        }
        deck = temp;
    }

    public Card deal(){
        Card card = deck.get(0);
        deck.remove(card);
        return card;
    }

    public void printDeck(){
        for(Card card:deck){
            card.print();
        }
    }

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

}
