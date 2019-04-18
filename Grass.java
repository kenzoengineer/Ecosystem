public class Grass extends Object{
    //how long until it releases seeds
    private int seed;
    //grass nutrition
    private int nutrition;
    
    public Grass(int a) {
       this.nutrition = a;
    }
    
    public int getNutrition() {
        return this.nutrition;
    }
}