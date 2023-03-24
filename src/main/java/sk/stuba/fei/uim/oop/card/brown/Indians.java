package sk.stuba.fei.uim.oop.card.brown;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;

public class Indians extends Card {
    private static final String CARD_NAME = "Indians";
    public Indians() {
        super(CARD_NAME);
    }

    @Override
    public String getName() {
        return CARD_NAME;
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.removeCardFromHand(this);
        for (Player onePlayer : players) {
            if (player.getId() == onePlayer.getId())
                continue;
            else {
                boolean flag = false;
                for (Card card : onePlayer.getAllCardsInHand()) {
                    if (card instanceof Bang) {
                        System.out.println("Player " + onePlayer.getName() + " throws card Bang to the pile");
                        onePlayer.throwCardFromHandToPile(pile, card);
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    onePlayer.removeLife();
            }
        }
    }
}