package sk.stuba.fei.uim.oop.card.blue;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;

import java.util.ArrayList;
import java.util.Random;

public class Prison extends Card {
    private static final String CARD_NAME = "Prison";
    public Prison() {
        super(CARD_NAME);
    }

    public void isPrison(Player player, ArrayList<Card> pile) {
        Random random = new Random();
        int chance = random.nextInt(4);
        if (chance == 1) {
            System.out.println(Color.RED + "Player " + player.getName() + " escaped from prison." + Color.RESET);
            player.throwCardFromDeckToPile(pile, this);
        }
        else {
            System.out.println(Color.RED + "Player " + player.getName() + " stays in prison." + Color.RESET);
            player.setTurn(false);
        }
    }

    @Override
    public String getName() {
        return CARD_NAME;
    }
    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.removeCardFromHand(this);
        int numOfPlayer = selectPlayer(player,players);
        players.get(numOfPlayer - 1).addCardToDeck(this);
        System.out.println(Color.GREEN + "You've put player " + players.get(numOfPlayer - 1).getName() + " in prison" + Color.RESET);
    }
}