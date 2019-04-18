public class Sheep extends Animal {
    public Sheep(int h) {
        super(h);
    }
    
    @Override
    public void eat(Object[][] map, int sA, int sB, int gA, int gB) {
        int health = ((Grass) map[gA][gB]).getNutrition();
        ((Animal)map[sA][sB]).subHealth(-1 * health);
        move(map,sA,sB,gA,gB);
        System.out.println("Sheep ate grass");
    }
        
    public void moveRandom(Object[][] map, int a, int b) {
        int rand = (int) (Math.random() * 4);
        if (rand == 0) {
            if (a >= 1) {
                if (map[a-1][b] instanceof Grass) {
                    eat(map, a, b, a-1, b);
                } else if (map[a-1][b] == null) {
                    move(map, a, b, a-1, b);
                }
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE-1) {
                if (map[a+1][b] instanceof Grass) {
                    eat(map, a, b, a+1, b);
                } else if (map[a+1][b] == null) {
                    move(map, a, b, a+1, b);
                }
            }
        } else if (rand == 2) {
            if (b >= 1) {
                if (map[a][b-1] instanceof Grass) {
                    eat(map, a, b, a, b-1);
                } else if (map[a][b-1] == null) {
                    move(map, a, b, a, b-1);
                }
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE-1) {
                if (map[a][b+1] instanceof Grass) {
                    eat(map, a, b, a, b+1);
                } else if (map[a][b+1] == null) {
                    move(map, a, b, a, b+1);
                }
            }
        }
        this.subHealth(1);
    }
}