package sk.stuba.fei.uim.oop.card.blue;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;

import java.util.ArrayList;
import java.util.Random;

public class Barrel extends Card {
    private static final String CARD_NAME = "Barrel";
    public Barrel() {
        super(CARD_NAME);
    }

    public boolean isBarrel(Player player, ArrayList<Card> pile) {
        Random random = new Random();
        int chance = random.nextInt(4);
        if (chance == 1) {
            System.out.println(Color.GREEN + "Player " + player.getName() + " used Barrel card!" + Color.RESET);
            player.throwCardFromDeckToPile(pile, this);
            return true;
        }
        else
            return false;
    }

    @Override
    public String getName() {
        return CARD_NAME;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.addCardToDeck(this);
        player.removeCardFromHand(this);
    }
}