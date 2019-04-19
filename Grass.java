public class Grass extends Object{
    //grass nutrition
    private int nutrition;
    
    public Grass(int a) {
       this.nutrition = a;
    }
    
    public int getNutrition() {
        return this.nutrition;
    }
    
    public void rot() {
        this.nutrition--;
    }
}