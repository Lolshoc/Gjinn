public class Card {

    public enum Suit{
        clubs,
        diamonds,
        spades,
        hearts
    }

    public enum Number{
        ace,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine,
        ten,
        jack,
        queen,
        king
    }

    private Suit suit;
    private Number number;

    private boolean booked = false;

    public Card(Suit suit, Number number){
        this.suit = suit;
        this.number = number;
    }

    //prints card
    public void print(){
        if(booked){
            System.out.println(number+" of "+suit+"  (booked)");
        }else {
            System.out.println(number + " of " + suit);
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public Number getNumber(){
        return number;
    }

    //gets int for Number value
    public int Number() {
        switch (number){
            case ace:
                return 1;
            case two:
                return 2;
            case three:
                return 3;
            case four:
                return 4;
            case five:
                return 5;
            case six:
                return 6;
            case seven:
                return 7;
            case eight:
                return 8;
            case nine:
                return 9;
            case ten:
                return 10;
            case jack:
                return 11;
            case queen:
                return 12;
            case king:
                return 13;
        }
        return 0;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

}
