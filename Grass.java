public class Grass extends Object{
    //grass nutrition
    private int nutrition;
    
    public Grass(int a) {
       this.nutrition = a;
    }
    
    /**
     * Gets the hp of the plant
     * @return the hp of the plant
     */
    public int getNutrition() {
        return this.nutrition;
    }
    
    /**
     * Ticks down the hp of the plant by one, due to it rotting
     */
    public void rot() {
        this.nutrition--;
    }
}