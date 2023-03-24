package sk.stuba.fei.uim.oop.card.brown;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.card.blue.Barrel;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;

import java.util.ArrayList;

public class Bang extends Card {
    private static final String CARD_NAME = "Bang";
    public Bang() {
        super(CARD_NAME);
    }
    @Override
    public String getName() {
        return CARD_NAME;
    }

    private boolean checkIfMissed(Player player, ArrayList<Card> pile) {
        boolean barrelFlag = false;
        boolean missedFlag = false;
        Missed missed = null;

        for (Card card : player.getAllCardsOnDeck()) {
            if (card instanceof Barrel) {
                if (((Barrel) card).isBarrel(player, pile)) {
                    barrelFlag = true;
                    break;
                }                    
            }
        }
        if (!barrelFlag) {
            for (Card card : player.getAllCardsInHand()) {
                if (card instanceof Missed) {
                    missed = (Missed) card;
                    missedFlag = true;
                    break;
                }
            }
            if (missedFlag)
                player.throwCardFromHandToPile(pile, missed);
        }
        return barrelFlag || missedFlag;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.throwCardFromHandToPile(pile, this);
        int numOfPlayer = selectPlayer(player,players);
        Player playerShot = players.get(numOfPlayer - 1);
        boolean flag = checkIfMissed(playerShot, pile);

        if (flag)
            System.out.println(Color.GREEN + "Missed!" + Color.RESET);
        else {
            System.out.println(Character.toString(127797) + Character.toString(129327)
                    +  "............................."
                    + Character.toString(128299) + Character.toString(129312));

            System.out.println(Color.RED + "Player " + player.getName() + " shot player " + playerShot.getName() + Color.RESET);
            playerShot.removeLife();
        }
    }
}