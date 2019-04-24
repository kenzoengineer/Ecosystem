public class Grass extends Organism{
    
    public Grass(int a) {
       this.health = a;
    }
    
    /**
     * Gets the hp of the plant
     * @return the hp of the plant
     */
    public int getNutrition() {
        return this.health;
    }
    
    /**
     * Ticks down the hp of the plant by one, due to it rotting
     */
    public void rot() {
        this.health--;
    }
}