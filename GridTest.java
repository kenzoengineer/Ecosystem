/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Ken Jiang
 */

import java.util.LinkedList;
import java.util.Queue;
class GridTest {
    //constants
    public static final int WOLF_NUMBER = 3;
    public static final int SHEEP_NUMBER = 100;
    public static final int GRASS_NUMBER = 100;
    public static final int SIZE = 25;
    public static final int DELAY = 100;
    public static Queue<String> queue = new LinkedList<>();
    //tracks how many of each entity exists
    public static int wolfC = 0;
    public static int sheepC = 0;
    
    /**
     * Spawns a set amount of each entity at random locations on the map
     * @param map a 2d array of the world
     */
    public static void setupGame(Organism[][] map) {
        int i = 0;
        while (i < WOLF_NUMBER) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Wolf(30);
                i++;
            }
        }
        i = 0;
        while (i < SHEEP_NUMBER) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Sheep(40);
                i++;
            }
        }
        i = 0;
        while (i < GRASS_NUMBER) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Grass(50);
                i++;
            }
        }
    }

    /**
     * Resets the map by setting all tiles to null
     * @param map a 2d array of the map
     */
    public static void resetGame(Organism[][] map) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = null;
            }
        }
    }
  
    /**
     * Advances the turn by moving all movable objects on the grid
     * Animals are checked for death and are moved, while grass is also checked for death
     * @param map a 2d array of the world
     */
    public static void moveItemsOnGrid(Organism[][] map) { 
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] instanceof Animal && !((Animal)map[i][j]).getTired()) {
                    if(((Animal)map[i][j]).dead()) {
                        //System.out.println( map[i][j].getClass().getName() + " died!");
                        map[i][j] = null;
                    } else {
                        ((Animal)map[i][j]).setTired(true);
                        if (map[i][j] instanceof Wolf) {
                            ((Wolf)map[i][j]).moveRandom(map,i,j);
                        } else {
                            ((Sheep)map[i][j]).moveRandom(map,i,j);
                        }
                    }
                } else if (map[i][j] instanceof Grass) {
                    if (((Grass)map[i][j]).getNutrition() == 0) {
                        map[i][j] = null;
                    } else {
                        ((Grass)map[i][j]).rot();
                    }
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] instanceof Animal) {
                    ((Animal)map[i][j]).setTired(false);
                }
            }
        }
    }
  
    /**
     * Spawns a set amount of grass randomly on the map, stops short if there is no room
     * @param map a 2d array of the world
     * @param a the amount of grass to be spawned
     */
    public static void moreGrass(Organism[][] map, int a) {
        for (int i = 0; i < a; i++) {
            if ((Animal.findEmpty(map)[0] >= 0)) {
                while (true) {
                    int rand1 = (int)(Math.random() * SIZE);
                    int rand2 = (int)(Math.random() * SIZE);
                    if (map[rand1][rand2] == null) {
                        map[rand1][rand2] = new Grass(50);
                        //System.out.println("Grass spawned");
                        break;
                    }
                }
            } else return;
        }
    }

    /**
     * Checks if one animal (sheep/wolf) no longer exists and the ecosystem is no longer stable
     * @param map a 2d array of the world
     * @return whether the ecosystem is still stable or not
     */
    public static boolean stable(Organism[][] map) {
        int sC = 0;
        int wC = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] instanceof Wolf) {
                    wC++;
                } else if (map[i][j] instanceof Sheep) {
                    sC++;
                }
            }
        }
        if (sC > 0 && wC > 0) {
            return true;
        }
          System.out.println(sC > wC ? "Sheep won" : "Wolf won");
        return false;
    }
    
    /**
     * Helper method to count how many of each entity still exists
     * @param map a 2d array of the world
     * @return an array of the amount of wolves and sheep
     */
    public static int[] count(Organism[][] map) {
        int[] arr = new int[2];
        int w = 0;
        int s = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] instanceof Wolf) {
                    w++;
                } else if (map[i][j] instanceof Sheep) {
                    s++;
                }
            }
        }
        arr[0] = w; arr[1] = s;
        return arr;
    }
  
    public static void main(String[] args) { 
        Organism map[][] = new Organism[SIZE][SIZE];
        int turn = 0;
        // Initialize Map
        setupGame(map);
        //Set up Grid Panel
        DisplayGrid grid = new DisplayGrid(map);
        System.out.println("Event log:");
            
        while(true) {
            //Display the grid on a Panel
            if(stable(map)) {
                turn++;
            } else {
                System.out.println("Turn : " + turn);
                resetGame(map);
                setupGame(map);
                turn = 0;
            }
            wolfC = count(map)[0];
            sheepC = count(map)[1];
            grid.refresh();

            //Small delay
            try {
                Thread.sleep(DELAY);
            }catch(Exception e) {
                e.printStackTrace();
            };

            // Initialize Map (Making changes to map)
            moveItemsOnGrid(map);
            int r = (int) (Math.random() * 2);
            if (r == 0) moreGrass(map,8);
            //Display the grid on a Panel
            while (!GridTest.queue.isEmpty()) {
                System.out.println(GridTest.queue.poll());
            }
            grid.refresh();
        }
  }
}