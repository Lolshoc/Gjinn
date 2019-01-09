import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> hand = new ArrayList<>();

    public List<Card> getHand(){
        return hand;
    }

    //returns and removes a card from hand
    public Card discard(int index){
        Card discard = hand.get(index);
        hand.remove(index);
        return discard;
    }

    //adds card to hand
    public void draw(Card card){
        hand.add(card);
    }

    //prints each card in hand
    public void printHand(boolean discarding){
        if(discarding){
            int number;
            for (int i=0;i<hand.size();i++){
                number = i+1;
                System.out.print(number+": ");
                hand.get(i).print();
            }
        }else {
            for (Card card : hand) {
                card.print();
            }
        }
    }

    //sorts cards lowest to highest
    public void sortHand(){
        List<Card> temp = new ArrayList<>();
        for(int i=1;i<14;i++){
            for(Card card:hand){
                if (card.Number() == i) {
                    temp.add(card);
                }
            }
        }
        hand = temp;
    }

    //shifts list based on the index given
    public void shift(int index, Card card, List<Card> cards){
        for(int i=cards.size()-1;i>index;i--){
            cards.add(cards.get(i));
        }
        cards.set(index,card);
    }
}
