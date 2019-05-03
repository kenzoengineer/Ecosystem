abstract public class Animal extends Organism{
    //whether the animal has moved or not, true if it has and is now tired
    private boolean tired;
    //the animals gender, true if male, false if female
    private boolean isMale;
    //breeding cooldown
    private int cooldown;
    //
    private int preyX;
    private int preyY;
    //constructor
    public Animal(int h) {
        this.health = h;
        this.tired = false;
        int r = (int) (Math.random() * 2);
        isMale = (r == 0);
        this.cooldown = 20;
    }
    
    /**
     * Checks if animal is dead (health is at 0)
     * @return a boolean value depend on whether it's dead or not
     */
    public boolean dead() {
        return health <= 0;
    }
    
    /**
     * Gets the tiredness of an animal
     * @return if it is tired
     */
    public boolean getTired() {
        return tired;
    }
    
    /**
     * Sets the tiredness of the animal to an amount
     * @param h whether the animal is tired or not
     */
    public void setTired(boolean b) {
        this.tired = b;
    }
    
    /**
     * Gets the hp of an animal
     * @return the hp of the animal
     */
    public int getHealth() {
        return this.health;
    }
    
    /**
     * Sets the health of the animal to an amount
     * @param h the amount for the health to be set to
     */
    public void setHealth(int h) {
        this.health = h;
    }
    
    /**
     * Subtracts the health of the animal by an integer amount
     * @param h the amount of hp to be subtracted from the animal
     */
    public void subHealth(int h) {
        this.health -= h;
        if (this.health >= 100) {
            this.health = 100;
        }
    }
   
    /**
     * Sets the value of a cooldown to an integer
     * @param cooldown the value to set the cooldown to
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * Gets the value of the cooldown
     * @return cooldown value
     */
    public int getCooldown() {
        return cooldown;
    }
    
    /**
     * Subtracts the cooldown value by integer s
     * @param s the amount to subtract from cooldown
     */
    public void subCooldown(int s) {
        this.cooldown -= s;
    } 
    
    /**
     * Sets the x and y coords for the closest prey
     * @param x the prey x coord
     * @param y the prey y coord
     */
    public void setXY(int x, int y) {
        this.preyX = x;
        this.preyY = y;
    }
    
    /**
     * Returns the values of preyX and preyY as an integer array
     * @return an integer array with preyX and preyY
     */
    public int[] getXY() {
        int[] arr = {preyX, preyY};
        return arr;
    }
    
    /**
     * Checks if there is an empty location in the map
     * @param map a 2d array of the world
     * @return the coordinates of the empty location, (-1,-1) if none exists
     */
    public static int[] findEmpty(Organism[][] map) {
        int[] arr = new int[2]; 
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == null) {
                    arr[0] = i;
                    arr[1] = j;
                    return arr;
                }
            }
        }
        arr[0] = -1;
        arr[1] = -1;
        return arr;
    }
    
    /**
     * Path from a,b towards x,y. Biased to move up, then down, then right and left.
     * @param a the x of the start position
     * @param b the y of the start position
     * @param x the x of the destination
     * @param y the y of the destination
     * @return an integer 1,2,3 or 4 depending on which direction to move. A switch case decodes this information outside of this method.
     */
    public int pathFind(int a, int b, int x, int y) {
        if (a < x) {
            return 1;
        } else if (a > x) {
            return 0;
        } else if (b < y) {
            return 3;
        } else {
            return 2;
        }
    }
    
    /**
     * Helper method which checks if 2 animals are the same gender
     * @param a the second animal
     * @return true if the genders are the same, false if not
     */
    public boolean sameGender(Animal a) {
        return this.isMale == a.isMale;
    }
    
    /**
     * Moves an object at (a,b) to (x,y)
     * @param map a 2d array of the world
     * @param a the x coord of the object
     * @param b the y coord of the object
     * @param x the x coord of the destination
     * @param y the y coord of the destination
     */
    public void move(Organism[][] map, int a, int b, int x, int y) {
        map[x][y] = map[a][b];
        map[a][b] = null;
    }
    
    /**
     * Moves an animal to the location of its prey, killing the prey and increasing the hp of the predator
     * @param map a 2d array of the world
     * @param a the x coord of the predator
     * @param b the y coord of the predator
     * @param c the x coord of the prey
     * @param d the y coord of the prey
     */
    abstract public void eat(Organism[][] map, int a, int b, int c, int d);
    
    /**
     * Methods which handles random movement, eating and breeding
     * @param map a 2d array of the world
     * @param a a x coordinate
     * @param b a y coordinate
     */
    abstract public void moveRandom(Organism[][] map, int a, int b);
    
    /**
     * Breeds this and another animal, spawning a 20 hp animal and reducing the parent's health by 20
     * @param map a 2d array of the world
     * @param a the x coordinate of the mate
     * @param b the y coordinate of the mate
     */
    abstract public void breed(Organism[][] map, int a, int b);
    
    /**
     * Finds the closest prey in a radius and sets the x and y variables for it to be read by a pathfinding method
     * @param map a 2d array of the world
     * @param a the x coordinate of the prey
     * @param b the y coordinate of the prey
     * @param r search distance radius
     */
    abstract public void findClose(Organism[][] map, int a, int b, int r);
}