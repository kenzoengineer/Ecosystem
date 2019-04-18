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
        if (health <= 0) {
            return true;
        }
        return false;
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
    
    abstract public void moveRandom(Object[][] map, int a, int b);
}