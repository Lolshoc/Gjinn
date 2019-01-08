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
        boolean after;
        List<Card> temp = new ArrayList<>();
        for(Card card:hand){
            if(card == hand.get(0)){
                temp.add(card);
                continue;
            }
            after = false;
            for(Card c:temp){
                if(card.Number()<c.Number()){
                    temp.add(temp.indexOf(c),card);
                    after = false;
                    break;
                }else{
                    after = true;
                }
            }
            if(after){
                temp.add(card);
            }
        }
        hand = temp;
    }

}
