package sk.stuba.fei.uim.oop.card.brown;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;

import java.util.ArrayList;

public class Diligenza extends Card {
    private static final String CARD_NAME = "Diligenza";
    public Diligenza() {
        super(CARD_NAME);
    }
    @Override
    public String getName() {
        return CARD_NAME;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.throwCardFromHandToPile(pile, this);
        System.out.println(Color.GREEN + "Player " + player.getName() + " draws 2 cards." + Color.RESET);
        player.drawCard(cards, 0);
        cards.remove(0);
        player.drawCard(cards, 0);
        cards.remove(0);
    }
}