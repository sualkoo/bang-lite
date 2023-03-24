package sk.stuba.fei.uim.oop.card.brown;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;
import java.util.Random;

public class CatBalou extends Card {
    private static final String CARD_NAME = "Cat Balou";
    public CatBalou() {
        super(CARD_NAME);
    }
    @Override
    public String getName() {
        return CARD_NAME;
    }

    private void selectCard(Player player, int handOrDeck, ArrayList<Card> pile) {
        if (handOrDeck == 1) {
            Random random = new Random();
            Card card = player.getCardInHand(random.nextInt(player.getAllCardsInHand().size()));
            player.throwCardFromHandToPile(pile, card);
            System.out.println(Color.GREEN + "You throw card " + card.getName() + " to pile" + Color.RESET);
        }
        else {
            System.out.println(Color.RED + "Select number of card: ");
            int i = 1;
            for (Card card :player.getAllCardsOnDeck()) {
                System.out.print(i + " -> " + card.getName() + " | ");
                i++;
            }
            System.out.println(Color.RESET);
            int numOfCard;
            do {
                numOfCard = KeyboardInput.readInt() - 1;
            } while (numOfCard < 0 || numOfCard > player.getAllCardsOnDeck().size());
            Card card = player.getCardOnDeck(numOfCard);
            player.throwCardFromDeckToPile(pile, card);
            System.out.println(Color.GREEN + "You throw card " + card.getName() + " to pile" + Color.RESET);
        }
    }

    private void chooseCardInfo(Player player) {
        System.out.println(Color.GREEN + "Info about enemy's cards" + Color.RESET);
        if (!player.getAllCardsInHand().isEmpty()) {
            System.out.println(Color.YELLOW + "Cards in hand: ");
            for (int i = 0; i < player.getAllCardsInHand().size(); i++) {
                System.out.print((i + 1) + " -> Card | ");
            }
            System.out.println(Color.RESET);
            System.out.println("Press 1, if you want to throw away card from hand to pile");
        }
        if (!player.getAllCardsOnDeck().isEmpty()) {
            System.out.println(Color.RESET + Color.BLUE + "Cards on deck: ");
            for (Card card :player.getAllCardsOnDeck()) {
                System.out.print(card.getName() + " | ");
            }
            System.out.println(Color.RESET);
            System.out.println("Press 2, if you want to throw away card from deck to pile");
        }
    }

    @Override
    public void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards) {
        player.throwCardFromHandToPile(pile,this);
        int numOfPlayer = selectPlayer(player,players);
        Player playerCatBaloued = players.get(numOfPlayer - 1);
        if (playerCatBaloued.getAllCardsOnDeck().size() != 0 || playerCatBaloued.getAllCardsInHand().size() != 0) {
            chooseCardInfo(playerCatBaloued);
            int handOrDeck;
            do {
                handOrDeck = KeyboardInput.readInt();
            } while (!(handOrDeck == 1 || handOrDeck == 2));
            selectCard(playerCatBaloued,handOrDeck,pile);
        }
        else {
            System.out.println("Player " + playerCatBaloued.getName() + " has no cards. Choose another player");
            play(player,pile,players,cards);
        }
    }
}