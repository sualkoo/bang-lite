package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.card.Card;
import sk.stuba.fei.uim.oop.card.blue.Barrel;
import sk.stuba.fei.uim.oop.card.blue.Dynamite;
import sk.stuba.fei.uim.oop.card.blue.Prison;
import sk.stuba.fei.uim.oop.card.brown.*;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private void initPlayers(ArrayList<Player> players) {
        int playersCount;
        do {
            System.out.println("Type number of players: ");
            playersCount = KeyboardInput.readInt();
        } while (playersCount < 2 || playersCount > 4);

        for (int i = 0; i < playersCount; i++) {
            System.out.println("Type name of the player number " + (i + 1));
            String playerName = KeyboardInput.readString();
            players.add(new Player(i, playerName,4,false, new ArrayList<>(), new ArrayList<>()));
        }
    }

    private void initCards(ArrayList<Card> cards) {

        for (int i = 0; i < 30; i++) {
            cards.add(new Bang());
            if (i % 2 == 0)
                cards.add(new Missed());
            if (i % 5 == 0)
                cards.add(new CatBalou());
            if (i % 15 == 0){
                cards.add(new Indians());
                cards.add(new Barrel());
            }
            if (i % 10 == 0)
                cards.add(new Prison());
            if (i == 0)
                cards.add(new Dynamite());
        }

        for (int i = 0; i < 8; i++) {
            cards.add(new Beer());
            if (i % 2 == 0)
                cards.add(new Diligenza());
        }
    }

    private void dealCards(Player player, ArrayList<Card> cards) {
        System.out.println("Dealing cards for player " + player.getName() + " " + Character.toString(9203));
        for (int i = 0; i < 4; i++) {
            player.drawCard(cards, i);
            cards.remove(i);
        }
    }

    private void drawTwoCards(Player player, ArrayList<Card> cards) {
        player.drawCard(cards,0);
        cards.remove(0);
        player.drawCard(cards,0);
        cards.remove(0);
        System.out.println("Player " + player.getName() + " draws 2 cards.");
    }

    private void checkBlueCards(Player player, ArrayList<Player> players, ArrayList<Card> pile) {
        ArrayList<Card> cardsOnDeck = player.getAllCardsOnDeck();
        boolean prisonFlag = false;
        boolean dynamiteFlag = false;
        Dynamite dynamite = null;
        Prison prison = null;

        for (Card card : cardsOnDeck) {
            if (card instanceof Prison) {
                prisonFlag = true;
                prison = (Prison) card;
            }
            if (card instanceof Dynamite) {
                dynamiteFlag = true;
                dynamite = (Dynamite) card;
            }
        }
        if (dynamiteFlag)
            dynamite.isDynamite(player,players, pile);
        if (prisonFlag)
            prison.isPrison(player, pile);
    }

    private void compareLivesToCards(Player player, ArrayList<Card> cards) {
        int lives = player.getLives();
        int cardsCount = player.getAllCardsInHand().size();
        while (cardsCount > lives) {
            System.out.println("You have to throw away " + (cardsCount - lives) + " card(s) from your hand");
            cardsInHand(player);
            Card card = player.getCardInHand(selectCard(player) - 1);
            player.throwCardFromHandToPile(cards, card);
            cardsCount--;
        }
    }

    private void cardsInHand (Player player) {
        System.out.println(Color.YELLOW + "Cards in hand: ");
        int count = 1;
        for (Card card :player.getAllCardsInHand()) {
            System.out.print(count + " -> " + card.getName() + " | ");
            count++;
        }
        System.out.println(Color.RESET);
    }

    private void gameInfo (Player player) {
        System.out.println(Color.GREEN + "------------------------------------------------" );
        System.out.println("Player: " + player.getName());
        System.out.print("Lives: ");
        for (int i = 0; i < player.getLives(); i++) {
            System.out.print(Character.toString(129505));
        }
        System.out.println();
        System.out.println(Color.RESET + Color.BLUE + "Cards on deck: " + Color.RESET);
        for (Card card :player.getAllCardsOnDeck()) {
            System.out.print(Color.BLUE + card.getName() + " | ");
        }
        System.out.println(Color.RESET);
        cardsInHand(player);
        System.out.println(Color.GREEN + "------------------------------------------------" + Color.RESET);
    }

    private int selectCard(Player player) {
        int numberOfCard;
        do {
            System.out.println("Pick a number of the card you want to play (or end your turn pressing 0): ");
            numberOfCard = KeyboardInput.readInt();
        } while (numberOfCard < 0 || numberOfCard > player.getAllCardsInHand().size());

        if (numberOfCard == 0) {
            System.out.println(Color.GREEN + "Player " + player.getName() + " ends his turn." + Color.RESET);
        }
        else {
            System.out.println(Color.GREEN + "Player " + player.getName() + " plays card " + player.getCardInHand(numberOfCard - 1).getName() + Color.RESET);
        }
        return numberOfCard;
    }

    private boolean isBlueCardDuplicate(Player player, Card card) {
        ArrayList<Card> blueCards = player.getAllCardsOnDeck();
        if (card instanceof Barrel || card instanceof Prison) {
            for (Card duplicate : blueCards) {
                if (!duplicate.equals(card)) {
                    System.out.println("You already have this card on your deck!");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCatBalouPlayable(Card card, ArrayList<Player> players) {
        if (card instanceof CatBalou) {
            int count = 0;
            for (Player player : players) {
                if (player.getAllCardsInHand().isEmpty() && player.getAllCardsOnDeck().isEmpty())
                    count++;
            }
            if ((players.size() - 1) == count) {
                System.out.println(Color.RED + "You can't play this card, because other players don't have any cards" + Color.RESET);
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }

    private void bounty(ArrayList<Player> players, Player player, ArrayList<Card> cards) {
        int count = 0;
        for (Player onePlayer : players) {
            if (onePlayer.isPlaying())
                count++;
        }
        if (count != players.size())
            drawTwoCards(player,cards);
    }

    private boolean playerRound(Player player, ArrayList<Card> cards, ArrayList<Card> pile, ArrayList<Player> players) {
        if (player.isOnTurn()) {
            System.out.println(Color.GREEN + "Player " + player.getName() + " is on turn.");
            drawTwoCards(player,cards);
            System.out.println();

            Card card;
            int keyPressed;

            do {
                gameInfo(player);
                System.out.println(Color.RESET);
                keyPressed = selectCard(player);
                if (!(keyPressed == 0)) {
                    card = player.getCardInHand(keyPressed - 1);
                    if (!isBlueCardDuplicate(player,card) && isCatBalouPlayable(card,players))
                        player.playCard(card, pile, players, cards);
                    else
                        continue;
                }
                bounty(players,player,cards);
                if (isWinner(players))
                    return true;
            } while (!(keyPressed == 0));

            compareLivesToCards(player, cards);
            player.setTurn(false);
        }
        else {
            for (Card card : player.getAllCardsOnDeck()) {
                if (card instanceof Prison) {
                    player.throwCardFromDeckToPile(cards, card);
                    break;
                }
            }
        }
        return false;
    }

    private boolean isWinner(ArrayList<Player> players) {
        int winner = players.size();
        for (Player player : players) {
            if (!player.isPlaying())
                winner--;
        }
        if (winner < 2)
        {
            for (Player player : players) {
                if (player.isPlaying())
                    System.out.println(Character.toString(127881) + Color.PURPLE + " Player " + player.getName() + " is winner! " + Color.RESET + Character.toString(127881));
            }
            return true;
        }
        return false;
    }

    private void shuffleCards(ArrayList<Card> cards, ArrayList<Card> pile) {
        cards.addAll(pile);
        pile.removeAll(cards);
        Collections.shuffle(cards);
    }

    private void removeCardsFromDeadPlayer(Player player) {
        player.getAllCardsOnDeck().removeAll(player.getAllCardsOnDeck());
        player.getAllCardsInHand().removeAll(player.getAllCardsInHand());
    }

    private void gameCycle(ArrayList<Player> players, ArrayList<Card> cards, ArrayList<Card> pile) {
        Player player;
        int cycleCounter = 0;
        boolean winner = false;

        while (!winner) {
            if (cycleCounter == players.size()) {
                cycleCounter = 0;
            }
            player = players.get(cycleCounter);
            cycleCounter++;
            player.setTurn(true);

            checkBlueCards(player, players, pile);

            if (player.isPlaying())
                winner = playerRound(player, cards, pile, players);
            else {
                System.out.println(Character.toString(128128) + Color.RED + " Player " + player.getName() + " has been killed " + Color.RESET + Character.toString(128128));
                removeCardsFromDeadPlayer(player);
                winner = isWinner(players);
            }
            if (cards.isEmpty())
                shuffleCards(cards, pile);
        }
    }

    public Game() {
        ArrayList<Player> players = new ArrayList<>();
        initPlayers(players);

        ArrayList<Card> cards = new ArrayList<>();
        initCards(cards);
        Collections.shuffle(cards);

        ArrayList<Card> pile = new ArrayList<>();

        for (Player player : players) {
            dealCards(player, cards);
        }
        gameCycle(players,cards,pile);
    }
}