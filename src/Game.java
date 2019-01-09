import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private Scanner myScanner = new Scanner(System.in);

    private Deck gameDeck = new Deck(true);
    private Deck discardPile = new Deck(false);
    private Hand playerOne = new Hand();
    private Hand playerTwo = new Hand();
    private int playerOneScore = 0;
    private int playerTwoScore = 0;

    private boolean exit = false;

    public static void main(String[] args){
        new Game();
    }

    //initiates the game state
    private Game(){
        init();
    }

    //sets up the game
    private void init(){
        playerOne.getHand().clear();
        playerTwo.getHand().clear();
        gameDeck.getDeck().clear();
        discardPile.getDeck().clear();
        for(int i=0;i<7;i++){
            playerOne.draw(gameDeck.deal());
            playerTwo.draw(gameDeck.deal());
        }
        discardPile.shift(gameDeck.deal());
        playerOne.sortHand();
        playerTwo.sortHand();
        gameLoop();
    }

    //one turn for one player
    private void turn(Hand hand){
        draw(hand);
        if(!exit) {
            hand.sortHand();
            discard(hand);
        }
    }

    //repeats players talking alternating turns and checks for a winner
    private void gameLoop(){
        boolean one = true;
        while (!exit) {
            gameDeck.update(discardPile);
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
        if(victory(playerOne)){
            System.out.println("PLAYER ONE WINS!");
            for(Card card:playerTwo.getHand()){
                if(!card.isBooked()){
                    playerTwoScore += card.Number();
                }
            }
        }else if(victory(playerTwo)){
            System.out.println("PLAYER TWO WINS!");
            for(Card card:playerOne.getHand()){
                if(!card.isBooked()){
                    playerOneScore += card.Number();
                }
            }
        }
        printScore();
        String answer = "";
        while(!(answer.equals("y")||answer.equals("n"))){
            System.out.print("Play again? y/n: ");
            answer = myScanner.next();
        }
        if(answer.equals("y")){
            init();
        }else{
            if(playerOneScore<playerTwoScore){
                System.out.println("PLAYER ONE WINS!");
            }else if(playerTwoScore<playerOneScore){
                System.out.println("PLAYER TWO WINS!");
            }else{
                System.out.println("ITS A TIE!");
            }
            System.out.println("Goodbye!");
        }
    }

    //simulates the start of a players turn
    private void draw(Hand hand){
        hand.printHand(false);
        System.out.println("");
        System.out.print("Discard: ");
        discardPile.getCard(0).print();
        String answer="";
        while(!(answer.equals("discard")||answer.equals("draw")||answer.equals("book")||answer.equals("unbook"))){
            if(answer.equals("exit")){
                exit=true;
                break;
            }
            System.out.println("Would you like to pick up the *discard*, *draw*, create a *book*, or delete/*unbook* a book?");
            answer = myScanner.next();
        }
        if(answer.equals("discard")){
            hand.draw(discardPile.deal());
        }else if(answer.equals("draw")){
            hand.draw(gameDeck.deal());
        }else if(answer.equals("book")){
            formBook(hand);
        }else if(answer.equals("unbook")){
            deleteBook(hand);
        }
    }

    //simulates player discarding to end turn
    private void discard(Hand hand){
        int answer = 10;
        while(answer>8||answer<=0) {
            System.out.println("Pick a card in your hand to discard (pick by its corresponding number):");
            hand.printHand(true);
            answer = myScanner.nextInt();
        }
        if(hand.getHand().get(answer-1).isBooked()){
            System.out.println("That card is in a book!");
            discard(hand);
        }else {
            discardPile.shift(hand.discard(answer - 1));
        }
    }

    //checks if a certain player has won
    private boolean victory(Hand hand){
        for(Card card:hand.getHand()){
            if(!card.isBooked()){
                return false;
            }
        }
        return true;
    }

    //forms a book out of some cards.  All cards must be in a book to win
    private void formBook(Hand hand){
        List<Card> unbooked = new ArrayList<>();
        List<Card> book = new ArrayList<>();
        int index = 1;
        int answer = 0;
        int selection;
        for(Card card:hand.getHand()){
            if (!card.isBooked()) {
                unbooked.add(card);
                System.out.print(index+": ");
                card.print();
                index++;
            }
        }
        while(answer<3||answer>=8) {
            System.out.println("Enter how many cards are in the book!");
            answer = myScanner.nextInt();
        }
        int i=0;
        while(i<answer){
            selection = 0;
            while(selection>unbooked.size()||selection<=0){
                for(Card card:unbooked){
                    System.out.print((unbooked.indexOf(card)+1)+": ");
                    card.print();
                }
                System.out.println("What number is in the book?");
                selection = myScanner.nextInt();
            }
            book.add(unbooked.get(selection-1));
            unbooked.remove(selection-1);
            i++;
        }
        if(checkIfBook(book)){
            for(Card card:book){
                card.setBooked(true);
            }
        }else{
            System.out.println("Sorry that wasn't a book!");
        }
        draw(hand);
    }

    //checks if it is a legal book
    private boolean checkIfBook(List<Card> book){
        boolean number = true;
        boolean suit = true;
        boolean straight = true;
        for(Card card:book){
            for(Card otherCards:book){
                if(!(card.getNumber() == otherCards.getNumber())){
                    number = false;
                }
                if(!(card.getSuit() == otherCards.getSuit())){
                    suit = false;
                }
            }
        }
        for(int i=1;i<book.size()-1;i++){
            if(!(book.get(0).Number() + i == book.get(i).Number())){
                straight = false;
            }
        }
        return (number || (straight && suit));
    }

    //will remove a book so the cards can be repurposed
    private void deleteBook(Hand hand){
        int answer = 0;
        int selection;
        List<Card> booked = new ArrayList<>();
        List<Card> unbook = new ArrayList<>();
        for(Card card:hand.getHand()){
            if(card.isBooked()){
                booked.add(card);
            }
        }
        printNumbers(booked, false);
        while(answer<3||answer>=8) {
            System.out.println("How many cards in book?");
            answer = myScanner.nextInt();
        }
        int i=0;
        boolean quit = false;
        while(i<answer){
            selection = 0;
            while(selection<=0||selection>booked.size()){
                printNumbers(booked, false);
                System.out.println("Which number is in the book or do -1 to quit");
                selection = myScanner.nextInt();
                if(selection==-1){
                    quit = true;
                    break;
                }
            }
            if(!quit) {
                unbook.add(booked.get(selection - 1));
                booked.remove(selection - 1);
                i++;
            }else{
                break;
            }
        }
        if(!quit) {
            if (checkIfBook(unbook)) {
                for (Card card : unbook) {
                    card.setBooked(false);
                }
            }
        }
        draw(hand);
    }

    //prints a group of cards with index selection numbers
    private void printNumbers(List<Card> list, boolean booked){
        int index = 1;
        for(Card card:list){
            if (!card.isBooked() == booked) {
                System.out.print(index+": ");
                card.print();
                index++;
            }
        }
    }

    //prints the score
    private void printScore(){
        System.out.println("Player One has "+playerOneScore+" points!");
        System.out.println("Player Two has "+playerTwoScore+" points!");
    }

}
