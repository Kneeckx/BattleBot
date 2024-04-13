

import battleship.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit - This is not a good solution to the problem!
 * @author Andrew.Rerecich
 * @author Nicolas.Chiasson
 */
public class BetterBot implements BattleShipBot {
    // The size of the game board
    private int gameSize;
    // A reference to the BattleShip game
    private BattleShip2 battleShip;
    // A random number generator
    private Random random;
    // An array list to store the shots fired
    static ArrayList<Point> shotsFired = new ArrayList<>();
    // A counter to keep track of the number of shots fired
    public long count = 0;

    /**
        * Initialize the AI with a reference to the BattleShip game
        *
        * @param b a BattleShip game instance
        */
    @Override
    public void initialize(BattleShip2 b) {
        battleShip = b;
        gameSize = b.BOARD_SIZE;

        random = new Random(0xAAAAAAAA);
    }

    /**
     * Fire a shot at a random location on the board
     */
    @Override
    public void fireShot() {

        boolean try_again = true;
        boolean hit = false;
        int x = 0, y = 0;
        while (try_again) {
            for (int i = 0; i < gameSize; i++) { //Iterates the length of the board
                x = i;
                for (int j = 0; j < gameSize; j++) { //Iterates the width of the board
                    y = j;

                    Point fire = new Point(x, y);
                    //If the shot has not been fired, fire
                    if (shotsFired == null) {
                        shotsFired.add(fire);
                        try_again = false;
                    } else {
                        //If the shot has already been fired, try again
                        if (shotsFired.contains(fire)) {
                            try_again = true;
                        } else {
                            shotsFired.add(fire);
                            try_again = false;
                        }
                        hit = battleShip.shoot(fire);
                        //If the shot hits a ship, increment the count
                        if (hit) {
                            count++;
                            if (count >= 24) {
                                shotsFired = new ArrayList<>();
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Return the name of the author of this bot
     *
     * @return the author of the bot
     */

    @Override
    public String getAuthors() {
        return "Andrew Rerecich, Nicolas Chiasson";
    }
}

