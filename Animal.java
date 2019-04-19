abstract public class Animal extends Object{
    //animal health
    private int health;
    //reproduction cooldown time
    private boolean tired;
    
    public Animal(int h) {
        this.health = h;
        this.tired = false;
    }
    
    /**
     * Checks if animal is dead (health is at 0)
     * @return a boolean value depend on whether it's dead or not
     */
    public boolean dead() {
        return health <= 0;
    }
    
    public boolean getTired() {
        return tired;
    }
    
    public void setTired(boolean b) {
        this.tired = b;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int h) {
        this.health = h;
    }
    
    public void subHealth(int h) {
        if (this.health <= 100) {
            this.health -= h;
        }
    }
    
    public static int[] findEmpty(Object[][] map) {
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
     * Moves an object at (a,b) to (x,y)
     * @param map a 2d array holding the world map
     * @param a the x coord of the object
     * @param b the y coord of the object
     * @param x the x coord of the destination
     * @param y the y coord of the destination
     */
    public void move(Object[][] map, int a, int b, int x, int y) {
        map[x][y] = map[a][b];
        map[a][b] = null;
    }
    
    abstract public void eat(Object[][] map, int a, int b, int c, int d);
    
    /**
     * Methods which handles random movement, eating and breeding
     * @param map 2d array map of the world
     * @param a a x coordinate
     * @param b a y coordinate
     */
    abstract public void moveRandom(Object[][] map, int a, int b);
}