/**
 * GridTest.java
 * Simulates an ecosystem environment with grass, sheep and wolves.
 * @author Ken Jiang
 * @version 1.2
 * @since April 16th, 2019
 * 
 * Additional Features:
 * Gender            Animals have gender (M/F) which determine whether they can breed or fight
 * Pathfinding       Animals have pathfinding within a fixed radius to prey around them
 * We breed too!     Wolves can also breed
 * Final stand       Wolves extend their search radius when low health
 * Heavy feet        Wolves trample grass and remove them from the map
 * *Sniffs*          Wolves have a chance to not follow their prey as they lose their scent
 * Seasons           The simulation goes through seasons, affecting plant growth decreasing from spring to winter
 * Visuals           Sheep, wolves and grass have pictures to represent them on the map. Seasons change the color of the background
 * Event log         A rolling event log which shows breeding, attacking and eating
 * Counts            Counters for how many of each organism exists
 * Graphs            Real time graphs which visually represent the amount of grass and sheep
 * Flexibility       Tested to work on screens from 11" to 23.5"
 * 
 * Recommended values: 
 * Wolves   2
 * Sheep    50
 * Grass    60
 * These values make the program last an average of ~800 turns (with outliers ommited).
 * @see avg.jpg
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
class GridTest {
    //constants
    public static int wolfNumber = 2;
    public static int sheepNumber = 50;
    public static int grassNumber = 60;
    public static final int SIZE = 30;
    public static final int DELAY = 100;
    public static Queue<String> queue = new LinkedList<>();
    //tracks how many of each entity exists
    public static int wolfC = 0;
    public static int sheepC = 0;
    public static int grassC = 0;
    public static int turn = 0;
    public static String season = " ";
    
    /**
     * Spawns a set amount of each entity at random locations on the map
     * @param map a 2d array of the world
     */
    public static void setupGame(Organism[][] map) {
        int i = 0;
        //loops until the number of wolves matches the constant
        while (i < wolfNumber) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Wolf(30);
                i++;
            }
        }
        i = 0;
        //loops until the number of sheep matches the constant
        while (i < sheepNumber) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Sheep(40);
                i++;
            }
        }
        i = 0;
        //loops until the number of grass matches the constant
        while (i < grassNumber) {
            int a = (int) (Math.random() * SIZE);
            int b = (int) (Math.random() * SIZE);
            if (map[a][b] == null) {
                map[a][b] = new Grass(50);
                i++;
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
        //loop through entire array and allow all animals to move again
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
        int[] arr = new int[3];
        int w = 0;
        int s = 0;
        int g = 0;
        //loop through entire array
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                //increment wolf count
                if (map[i][j] instanceof Wolf) {
                    w++;
                //increment sheep count
                } else if (map[i][j] instanceof Sheep) {
                    s++;
                } else if (map[i][j] instanceof Grass) {
                    g++;
                }
            }
        }
        arr[0] = w;
        arr[1] = s; 
        arr[2] = g;
        return arr;
    }
    
    /**
     * Helper method to check whether the ecosystem is still stable
     * @param map a 2d array of the world
     * @return a boolean value whether it's stable or not
     */
    public static boolean stable(Organism[][] map) {
        if((wolfC == 0 || sheepC == 0)) {
            queue.add("Turn " + turn + ": Game ended");
            return false;
        }
        return true;
    }
  
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        Organism map[][] = new Organism[SIZE][SIZE];
        String[] seasons = {"Spring","Summer","Fall","Winter"};
        System.out.println("How many sheep?");
        sheepNumber = sc.nextInt();
        System.out.println("How many wolves?");
        wolfNumber = sc.nextInt();
        System.out.println("How much grass?");
        grassNumber = sc.nextInt();
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
            //set the number of wolves
            wolfC = count(map)[0];
            //set the number of sheep
            sheepC = count(map)[1];
            //set the number of grass
            grassC = count(map)[2];
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
            //change how much grass spawns depending on the season (9, 6, 3, 0)
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