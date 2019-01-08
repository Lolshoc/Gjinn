import java.util.Scanner;

public class Game {

    private Scanner myScanner = new Scanner(System.in);

    private Deck gameDeck = new Deck(true);
    private Deck discardPile = new Deck(false);
    private Hand playerOne = new Hand();
    private Hand playerTwo = new Hand();

    private boolean exit = false;

    public static void main(String[] args){
        new Game();
    }

    private Game(){
        for(int i=0;i<7;i++){
            playerOne.draw(gameDeck.deal());
            playerTwo.draw(gameDeck.deal());
        }
        discardPile.shift(gameDeck.deal());
        playerOne.sortHand();
        playerTwo.sortHand();
        gameLoop();
    }

    private void turn(Hand hand){
        draw(hand);
        hand.sortHand();
        discard(hand);
    }

    private void gameLoop(){
        boolean one = true;
        while (!exit) {
            if(one){
                turn(playerOne);
                if(victory(playerOne)){
                    break;
                }
                one = false;
            }else{
                turn(playerTwo);
                if(victory(playerTwo)){
                    break;
                }
                one = true;
            }
        }
    }

    private void draw(Hand hand){
        hand.printHand(false);
        System.out.println("");
        System.out.print("Discard: ");
        discardPile.getCard(0).print();
        String answer="";
        while(!(answer.equals("discard")||answer.equals("draw"))){
            if(answer.equals("exit")){
                exit=true;
                break;
            }
            System.out.println("Would you like to pick up the *discard* or *draw*?");
            answer = myScanner.next();
        }
        if(answer.equals("discard")){
            hand.draw(discardPile.deal());
        }else if(answer.equals("draw")){
            hand.draw(gameDeck.deal());
        }
    }

    private void discard(Hand hand){
        int answer = 10;
        while(answer>8||answer<=0) {
            System.out.println("Pick a card in your hand to discard (pick by its corresponding number):");
            hand.printHand(true);
            answer = myScanner.nextInt();
        }
        discardPile.shift(hand.discard(answer-1));
    }

    private boolean victory(Hand hand){
        return false;
    }

}
