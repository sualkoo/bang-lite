package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.utility.Color;

import java.util.ArrayList;

public class Player {
    private final int id;
    private final String name;
    private int lives;
    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> cardsOnDeck;
    protected boolean onTurn;
    public Player(int id, String name, int lives, boolean onTurn, ArrayList<Card> cardsInHand, ArrayList<Card> cardsOnDeck) {
        this.id = id;
        this.name = name;
        this.lives = lives;
        this.cardsInHand = cardsInHand;
        this.cardsOnDeck = cardsOnDeck;
        this.onTurn = onTurn;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getLives() {
        return lives;
    }
    public Card getCardInHand(int indexOfCard) {
        return cardsInHand.get(indexOfCard);
    }
    public Card getCardOnDeck(int indexOfCard) {
        return cardsOnDeck.get(indexOfCard);
    }
    public void removeLife(){
        System.out.println(Color.RED + "Player " + this.name + " looses life " + Character.toString(128148) + Color.RESET);
        this.lives--;
    }
    public void addLife() {
        System.out.println(Color.GREEN + "Player " + this.name + " adds life " + Character.toString(127866) + Color.RESET);
        this.lives++;
    }
    public ArrayList<Card> getAllCardsInHand() {
        return this.cardsInHand;
    }
    public ArrayList<Card> getAllCardsOnDeck() { return this.cardsOnDeck; }
    public void addCardToDeck(Card card) {
        this.cardsOnDeck.add(card);
    }
    public void drawCard(ArrayList<Card> cards, int indexOfCard) {
        this.cardsInHand.add(cards.get(indexOfCard));
    }
    public void throwCardFromDeckToPile(ArrayList<Card> pile, Card card) {
        this.cardsOnDeck.remove(card);
        pile.add(card);
    }
    public void throwCardFromHandToPile(ArrayList<Card> pile, Card card) {
        this.cardsInHand.remove(card);
        pile.add(card);
    }
    public void removeCardFromHand(Card card) {
        this.cardsInHand.remove(card);
    }
    public void playCard(Card card, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        card.play(this, pile, players,cards);
    }
    public void moveDynamiteToPrevious(Player player, Card card) {
        player.addCardToDeck(card);
        this.cardsOnDeck.remove(card);
    }
    public boolean isPlaying() {
        return this.lives > 0;
    }
    public void setTurn(boolean onTurn) {
        this.onTurn = onTurn;
    }
    public boolean isOnTurn() {
        return onTurn;
    }
}