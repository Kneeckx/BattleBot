
import battleship.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit - This is not a good solution to the problem!
 *
 * @author mark.yendt@mohawkcollege.ca (Dec 2021)
 */
public class BetterBot implements BattleShipBot {
    private int gameSize;
    private BattleShip2 battleShip;
    private Random random;
    static ArrayList<Point> shotsFired = new ArrayList<>();
    public long count = 0;

    /**
     * Constructor keeps a copy of the BattleShip instance
     * Create instances of any Data Structures and initialize any variables here
     *
     * @param b previously created battleship instance - should be a new game
     */

    @Override
    public void initialize(BattleShip2 b) {
        battleShip = b;
        gameSize = b.BOARD_SIZE;

        // Need to use a Seed if you want the same results to occur from run to run
        // This is needed if you are trying to improve the performance of your code

        random = new Random(0xAAAAAAAA);   // Needed for random shooter - not required for more systematic approaches
    }

    /**
     * Create a random shot and calls the battleship shoot method
     * Put all logic here (or in other methods called from here)
     * The BattleShip API will call your code until all ships are sunk
     */

    @Override
    public void fireShot() {

        boolean try_again = true;
        boolean hit = false;
        int x = 0, y = 0;
        boolean[][] map = new boolean[15][15]; //The map index becomes true for every shot that hits
            for (int i = 0; i < gameSize; i++) {
                x = i;
                for (int j = 0; j < gameSize; j++) {
                    y = j;

                    Point fire = new Point(x, y);
                    if (shotsFired.isEmpty()) {
                        System.out.println("First shot");
                        shotsFired.add(fire);
                        map[x][y] = true;
                    } else if (shotsFired.contains(fire)) {
                        System.out.println("Shot already taken");
                    } else {
                        shotsFired.add(fire);
                        hit = battleShip.shoot(fire);
                        if (hit) {
                            count++;
                            map[x][y] = true;
                            smartSelectShot(fire, map);
                        }
                    }
                    //System.out.println(count);
                    if (count >= 24) {
                        shotsFired = new ArrayList<>();
                        count = 0;
                    }
                }
            }
        for(int k = 0; k < map.length; k++) {
            for(int l = 0; l < map[k].length; l++) {
                System.out.print(map[k][l]);
            }
            System.out.println();
        }
        System.out.println("Count: " + count);
            }
        // Will return true if we shot a ship

    public void smartSelectShot(Point plots, boolean[][] map) {
        int x = plots.x;
        int y = plots.y;

        Point up = new Point(x, y - 1);
        Point down = new Point(x, y + 1);
        Point left = new Point(x - 1, y);
        Point right = new Point(x + 1, y);

        hitUp(up, map);
        hitDown(down, map);
        hitLeft(left, map);
        hitRight(right, map);

    }
    public void hitLeft(Point plots, boolean[][] map) {
        int x = plots.x;
        int y = plots.y;
        Point left = new Point(x - 1, y);
        if(left.getX() > 0) {
            if(!map[(int) left.getX()][(int) left.getY()]) {
                boolean hitLeft = battleShip.shoot(left);
                if(hitLeft) {
                    count++;
                    map[(int) left.getX()][(int) left.getY()] = true;
                    smartSelectShot(left, map);
                }
            }
        }
    }

    public void hitRight(Point plots, boolean[][] map) {
        int x = plots.x;
        int y = plots.y;
        Point right = new Point(x + 1, y);
        if(right.getX() < map.length - 1) {
            if(!map[(int) right.getX()][(int) right.getY()]) {
                boolean hitRight = battleShip.shoot(right);
                if(hitRight) {
                    count++;
                    map[(int) right.getX()][(int) right.getY()] = true;
                    smartSelectShot(right, map);
                }
            }
        }
    }

    public void hitUp(Point plots, boolean[][] map) {
        int x = plots.x;
        int y = plots.y;
        Point up = new Point(x, y - 1);
        if(up.getY() > 0) {
            if(!map[(int) up.getX()][(int) up.getY()]) {
                boolean hitUp = battleShip.shoot(up);
                if(hitUp) {
                    count++;
                    map[(int) up.getX()][(int) up.getY()] = true;
                    smartSelectShot(up, map);
                }
            }
        }
    }

    public void hitDown(Point plots, boolean[][] map) {
        int x = plots.x;
        int y = plots.y;
        Point down = new Point(x, y + 1);
        if(down.getY() < map[0].length) {
            if(!map[(int) down.getX()][(int) down.getY()]) {
                boolean hitDown = battleShip.shoot(down);
                if(hitDown) {
                    map[(int) down.getX()][(int) down.getY()] = true;
                    smartSelectShot(down, map);
                    count++;
                }
            }
        }
    }


    /**
     * Authorship of the solution - must return names of all students that contributed to
     * the solution
     *
     * @return names of the authors of the solution
     */

    @Override
    public String getAuthors() {
        return "Andrew Rerecich, Nicolas Chaisson";
    }
}
