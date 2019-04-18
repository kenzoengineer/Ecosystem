abstract public class Animal extends Object{
    //animal health
    private int health;
    //reproduction cooldown time
    private int cooldown;
    //if already moved this turn
    private boolean tired;
    public Animal(int h) {
        this.health = h;
        this.cooldown = 2;
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
        this.health -= h;
    }
    
    public int getCooldown() {
        return cooldown;
    }
    
    public void setCooldown(int c) {
        this.cooldown = c;
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
    
    /**
     * Methods which handles random movement, eating and breeding
     * @param map 2d array map of the world
     * @param a a x coordinate
     * @param b a y coordinate
     */
    abstract public void moveRandom(Object[][] map, int a, int b);
}