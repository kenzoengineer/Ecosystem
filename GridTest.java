/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Ken Jiang
 */

import java.util.LinkedList;
import java.util.Queue;
class GridTest {
    //constants
    public static final int WOLF_NUMBER = 2;
    public static final int SHEEP_NUMBER = 50;
    public static final int GRASS_NUMBER = 60;
    public static final int SIZE = 30;
    public static final int DELAY = 10;
    public static Queue<String> queue = new LinkedList<>();
    //tracks how many of each entity exists
    public static int wolfC = 0;
    public static int sheepC = 0;
    public static int turn = 0;
    public static String season = " ";
    
    /**
     * Spawns a set amount of each entity at random locations on the map
     * @param map a 2d array of the world
     */
    public static void setupGame(Organism[][] map) {
        int i = 0;
        //loops until the number of wolves matches the constant
        while (i < WOLF_NUMBER) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Wolf(30);
                i++;
            }
        }
        i = 0;
        //loops until the number of sheep matches the constant
        while (i < SHEEP_NUMBER) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Sheep(40);
                i++;
            }
        }
        i = 0;
        //loops until the number of grass matches the constant
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
        //goes through entire map
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //checks if the object is an animal and isn't tired (can still move)
                //the second condition is necessary so an animal does not move twice
                if (map[i][j] instanceof Animal && !((Animal)map[i][j]).getTired()) {
                    //if the animal is dead remove it from the array
                    if(((Animal)map[i][j]).dead()) {
                        map[i][j] = null;
                    } else {
                        //make sure it doesn't move again
                        ((Animal)map[i][j]).setTired(true);
                        //move depending on the object's type
                        if (map[i][j] instanceof Wolf) {
                            ((Wolf)map[i][j]).moveRandom(map,i,j);
                        } else {
                            ((Sheep)map[i][j]).moveRandom(map,i,j);
                        }
                    }
                } else if (map[i][j] instanceof Grass) {
                    //checks if grass is dead
                    if (((Grass)map[i][j]).getNutrition() == 0) {
                        map[i][j] = null;
                    } else {
                        //lower it's nutritional value by one each turn
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
                        break;
                    }
                }
            } else return;
        }
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
    
    /**
     * Helper method to check whether the ecosystem is still stable
     * @param map a 2d array of the world
     * @return a boolean value whether it's stable or not
     */
    public static boolean stable(Organism[][] map) {
        return !(wolfC == 0 || sheepC == 0);
    }
  
    public static void main(String[] args) throws Exception{ 
        Organism map[][] = new Organism[SIZE][SIZE];
        String[] seasons = {"Spring","Summer","Fall","Winter"};
        // Initialize Map
        setupGame(map);
        //Set up Grid Panel
        DisplayGrid grid = new DisplayGrid(map);
        int seasonNum = 0;
        wolfC = count(map)[0];
        sheepC = count(map)[1];
        //runs game
        while(stable(map)) {
            //increment turn number
            turn++;
            //check the number of wolves
            wolfC = count(map)[0];
            //check the number of sheep
            sheepC = count(map)[1];

            //Small delay
            try {
                Thread.sleep(DELAY);
            }catch(Exception e) {
                e.printStackTrace();
            }
            
            //when the turn number reaches 30, it changes the season
            if (turn % 30 == 0) {
                if (seasonNum < 3) {
                    seasonNum++;
                } else {
                    //when it reaches 4, set it back to season 0
                    seasonNum = 0;
                }
            }
            //set the season string to the season at seasonNum
            season = seasons[seasonNum] + " " + (turn % 30 + 1);
            //change how much grass spawns depending on the season
            int abundance = 9 - (seasonNum * 3);
            //move animals
            moveItemsOnGrid(map);
            
            // 1/2 chance to spawn grass
            int r = (int) (Math.random() * 2);
            if (r == 0) {
                moreGrass(map,abundance);
            }
            grid.refresh();
        }
        //print to console the number of turns it lasted
        System.out.println(turn);
    }
}