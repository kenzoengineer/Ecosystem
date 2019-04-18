public class Wolf extends Animal implements Comparable<Wolf>{
    public Wolf(int h) {
        super(h);
    }
    
    //eat a sheep
    public void eat(Object[][] map, int wA, int wB, int sA, int sB) {
        int health = ((Animal) map[sA][sB]).getHealth();
        ((Animal)map[wA][wB]).subHealth(-1 * health);
        move(map,wA,wB,sA,sB);
        System.out.println("Wolf ate a sheep");
    }
    
    public int compareTo(Wolf w) {
        return this.getHealth() - w.getHealth();
    }
    
    @Override
    public void moveRandom(Object[][] map, int a, int b) {
        int rand = (int) (Math.random() * 4);
        if (rand == 0) {
            if (a >= 1) {
                if (map[a-1][b] instanceof Sheep) {
                    eat(map, a, b, a-1, b);
                } else if (map[a-1][b] == null) {
                    move(map, a, b, a-1, b);
                }
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE-1) {
                if (map[a+1][b] instanceof Sheep) {
                    eat(map, a, b, a+1, b);
                } else if (map[a+1][b] == null) {
                    move(map, a, b, a+1, b);
                }
            }
        } else if (rand == 2) {
            if (b >= 1) {
                if (map[a][b-1] instanceof Sheep) {
                    eat(map, a, b, a, b-1);
                } else if (map[a][b-1] == null) {
                    move(map, a, b, a, b-1);
                }
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE-1) {
                if (map[a][b+1] instanceof Sheep) {
                    eat(map, a, b, a, b+1);
                } else if (map[a][b+1] == null) {
                    move(map, a, b, a, b+1);
                }
            }
        }
        this.subHealth(1);
    }
}