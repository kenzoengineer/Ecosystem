public class Wolf extends Animal implements Comparable<Wolf>{
    public Wolf(int h) {
        super(h);
    }
    
    //eat a sheep
    public boolean eat() {
        return true;
    }
    
    public int compareTo(Wolf w) {
        return this.getHealth() - w.getHealth();
    }
    
    @Override
    public void moveRandom(Object[][] map, int a, int b) {
        int rand = (int) (Math.random() * 4);
        if (rand == 0) {
            if (a >= 1 && (map[a-1][b] == null || map[a-1][b] instanceof Sheep)) {
                if (map[a-1][b] instanceof Sheep) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Animal)map[a-1][b]).getHealth());
                    System.out.println("NOMNOMNOM " + a + " " + b);
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a-1][b] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE - 1 && (map[a+1][b] == null || map[a+1][b] instanceof Sheep)) {
                if (map[a+1][b] instanceof Sheep) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Animal)map[a+1][b]).getHealth());
                    System.out.println("NOMNOMNOM " + a + " " + b);
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a+1][b] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else if (rand == 2) {
            if (b >= 1 && (map[a][b-1] == null || map[a][b-1] instanceof Sheep)) {
                if (map[a][b-1] instanceof Sheep) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Animal)map[a][b-1]).getHealth());
                    System.out.println("NOMNOMNOM " + a + " " + b);
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a][b-1] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE - 1 && (map[a][b+1] == null || map[a][b+1] instanceof Sheep)) {
                if (map[a][b+1] instanceof Sheep) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Animal)map[a][b+1]).getHealth());
                    System.out.println("NOMNOMNOM " + a + " " + b);
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a][b+1] = map[a][b];
                map[a][b] = null;
                return;
            }
        }
        ((Animal)map[a][b]).subHealth(1);
        return;
    }
}