import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> hand = new ArrayList<>();

    public Card discard(int index){
        Card discard = hand.get(index);
        hand.remove(index);
        return discard;
    }

    public void draw(Card card){
        hand.add(card);
    }

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

    public void sortHand(){
        List<Card> temp = new ArrayList<>();
        for(Card card:hand){
            for(Card c:temp){
                if(card.Number()<c.Number()){
                    temp.add(c.Number()-1,card);
                    break;
                }
            }
        }
    }

}
