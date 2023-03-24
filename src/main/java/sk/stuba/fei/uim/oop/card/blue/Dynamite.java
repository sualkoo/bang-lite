package sk.stuba.fei.uim.oop.card.blue;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class Dynamite extends Card {
    private static final String CARD_NAME = "Dynamite";

    public Dynamite() {
        super(CARD_NAME);
    }

    private void dynamiteCycle(Player player, ArrayList<Player> players) {
        int previousPlayerIndex;
        if (players.indexOf(player) == 0) {
            previousPlayerIndex = players.size() - 1;
        }
        else {
            previousPlayerIndex = players.indexOf(player) - 1;
        }
        Player previousPlayer = players.get(previousPlayerIndex);
        player.moveDynamiteToPrevious(previousPlayer, this);
    }

    public void isDynamite(Player player, ArrayList<Player> players, ArrayList<Card> pile) {
        Random random = new Random();
        int chance = random.nextInt(8);
        if (chance == 1){
            player.throwCardFromDeckToPile(pile, this);
            System.out.println(Character.toString(128163) + Character.toString(128165) + " BOOOOOOOOOOM! " + Character.toString(128165) + Character.toString(128163));
            player.removeLife();
            player.removeLife();
            player.removeLife();
        }
        else {
            System.out.println("Dynamite didn't explode, moving to the previous player " + Character.toString(128556));
            dynamiteCycle(player, players);
        }
    }

    @Override
    public String getName() {
        return CARD_NAME;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        System.out.println("You've put dynamite to your deck");
        player.addCardToDeck(this);
        player.removeCardFromHand(this);
    }
}