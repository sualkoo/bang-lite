package sk.stuba.fei.uim.oop.card.brown;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Missed extends Card {
    private static final String CARD_NAME = "Missed";
    public Missed() {
        super(CARD_NAME);
    }
    @Override
    public String getName() {
        return CARD_NAME;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        System.out.println("You can't play this card, select another card");
    }
}