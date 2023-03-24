package sk.stuba.fei.uim.oop.card;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.Color;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;

public abstract class Card {
    protected String name;
    public Card(String name) {
        this.name = name;
    }

    public abstract String getName();

    public abstract void play(Player player, ArrayList<Card> pile, ArrayList<Player> players, ArrayList<Card> cards);

    protected int selectPlayer(Player player, ArrayList<Player> players) {
        System.out.println(Color.RED + "Select number of player (You can't choose yourself) :" + Color.RESET);
        int i = 1;
        for (Player onePlayer : players) {
            if (player.getId() == onePlayer.getId()) {
                System.out.print(i + " -> You | ");
                i++;
                continue;
            }
            System.out.print(i + " -> " + onePlayer.getName() + " | ");
            i++;
        }
        System.out.println(Color.RESET);

        int selectedPlayer = KeyboardInput.readInt();
        while (selectedPlayer == (player.getId() + 1) || (selectedPlayer <= 0 || selectedPlayer > players.size())) {
            System.out.println("You typed wrong number of player!");
            selectedPlayer = selectPlayer(player,players);
        }
        return selectedPlayer;
    }
}