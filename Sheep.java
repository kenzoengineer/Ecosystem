public class Sheep extends Animal {
    public Sheep(int h) {
        super(h);
    }
    public void moveRandom(Object[][] map, int a, int b) {
        int rand = (int) (Math.random() * 4);
        if (rand == 0) {
            if (a >= 1 && (map[a-1][b] == null || map[a-1][b] instanceof Grass || map[a-1][b] instanceof Sheep)) {
                if (map[a-1][b] instanceof Grass) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Grass)map[a-1][b]).getNutrition());
                    ((Animal)map[a][b]).subHealth(1);
                    map[a-1][b] = map[a][b];
                    map[a][b] = null;
                    return;
                } else if (map[a-1][b] instanceof Sheep) {
                    if (this.getHealth() > 20 && ((Sheep)map[a-1][b]).getHealth() > 20 && this.getCooldown() <= 0 && ((Sheep)map[a-1][b]).getCooldown() <= 0) {
                        this.setCooldown(5);
                        ((Sheep)map[a-1][b]).setCooldown(5);
                        this.subHealth(10);
                        ((Sheep)map[a-1][b]).subHealth(10);
                        boolean e = false;
                        while (!e) {
                            int rand1 = (int) (Math.random() * GridTest.SIZE);
                            int rand2 = (int) (Math.random() * GridTest.SIZE);
                            if (map[rand1][rand2] == null) {
                                map[rand1][rand2] = new Sheep(20);
                                e = true;
                            }
                        }
                    }
                } else {
                    map[a-1][b] = map[a][b];
                    map[a][b] = null;
                }
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE - 1 && (map[a+1][b] == null || map[a+1][b] instanceof Grass)) {
                if (map[a+1][b] instanceof Grass) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Grass)map[a+1][b]).getNutrition());
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a+1][b] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else if (rand == 2) {
            if (b >= 1 && (map[a][b-1] == null || map[a][b-1] instanceof Grass)) {
                if (map[a][b-1] instanceof Grass) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Grass)map[a][b-1]).getNutrition());
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a][b-1] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE - 1 && (map[a][b+1] == null || map[a][b+1] instanceof Grass)) {
                if (map[a][b+1] instanceof Grass) {
                    ((Animal)map[a][b]).subHealth(-1 * ((Grass)map[a][b+1]).getNutrition());
                }
                ((Animal)map[a][b]).subHealth(1);
                map[a][b+1] = map[a][b];
                map[a][b] = null;
                return;
            }
        } else {
            ((Animal)map[a][b]).subHealth(1);
        }
        return;
    }
}