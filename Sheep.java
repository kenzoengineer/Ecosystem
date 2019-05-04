public class Sheep extends Animal {
    public Sheep(int h) {
        super(h);
    }
    
    /**
     * Makes the sheep eat the grass and moves the sheep to it's location
     * @param map a 2d array of the world
     * @param sA the sheep's x coordinate
     * @param sB the sheep's y coordinate
     * @param gA the grass's x coordinate
     * @param gB the grass's y coordinate
     */
    @Override
    public void eat(Organism[][] map, int sA, int sB, int gA, int gB) {
        int health = ((Grass) map[gA][gB]).getNutrition();
        ((Animal)map[sA][sB]).subHealth(-1 * health);
        move(map,sA,sB,gA,gB);
        //System.out.println("Sheep ate grass");
    }
    
    /**
     * Brute force finds location of prey within a radius
     * @param map a 2d array of the world
     * @param a the x coord
     * @param b the y coord
     * @param r the search radius
     */
    public void findClose(Organism[][] map, int a, int b, int r) {
        if (map[a][b] instanceof Grass) {
            this.setXY(a,b);
        } else if (r >= 0) {
            if (a >= 1) {
                findClose(map, a-1, b, r-1);
            }
            if (a < GridTest.SIZE - 1) {
                findClose(map, a+1, b, r-1);
            }
            if (b >= 1) {
                findClose(map, a, b-1, r-1);
            }
            if (b < GridTest.SIZE - 1) {
                findClose(map, a, b+1, r-1);
            }
        }
    }
    
    /**
     * Breeds two sheep of the opposite gender, creating a new sheep of hp 20 at
     * a random location on the map and subtracting 10 hp from both parents
     * @param map a 2d array of the world
     * @param mA the mate's x coordinate
     * @param mB the mate's y coordinate
     */
    @Override
    public void breed(Organism[][] map, int mA, int mB) {
        //if both animals have enought health to breed
        if (this.getHealth() > 20 && ((Animal)map[mA][mB]).getHealth() > 20 && (this.getCooldown() + ((Animal)map[mA][mB]).getCooldown() == 0) && findEmpty(map)[0] >= 0) {
            this.subHealth(10);
            ((Animal)map[mA][mB]).subHealth(10);
            this.setCooldown(10);
            GridTest.queue.add("Turn " + GridTest.turn + ": Sheep bred");
            while (true) {
                int r1 = (int) (Math.random() * GridTest.SIZE);
                int r2 = (int) (Math.random() * GridTest.SIZE);
                if (map[r1][r2] == null) {
                    map[r1][r2] = new Sheep(20);
                    //System.out.println("Sheeps bred " + r1 +  " " + r2);
                    return;
                }
            }
        }
    }
    
    /**
     * Chooses a random direction for the sheep to move to. It then handles
     * breeding and eating.
     * @param map a 2d array of the world
     * @param a the sheep's x coordinate
     * @param b the sheep's y coordinate
     */
    @Override
    public void moveRandom(Organism[][] map, int a, int b) {
        int rand;
        //resets closest prey value
        this.setXY(-1,-1);
        //finds the closest prey
        findClose(map, a, b, 2);
        //if none is found, randomly move
        if (this.getXY()[0] == -1) {
            rand = (int) (Math.random() * 4);
        //else move towards the prey
        } else {
            rand = pathFind(a,b,getXY()[0], getXY()[1]);
        }
        
        if (rand == 0) {
            if (a >= 1) {
                //if it's grass, eat it
                if (map[a-1][b] instanceof Grass) {
                    eat(map, a, b, a-1, b);
                //if it's empty, move to it
                } else if (map[a-1][b] == null) {
                    move(map, a, b, a-1, b);
                //if it's a sheep, breed with it (if it's not the same gender)
                } else if (map[a-1][b] instanceof Sheep && !this.sameGender((Animal)map[a-1][b])) {
                    breed(map, a-1, b);
                }
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE-1) {
                if (map[a+1][b] instanceof Grass) {
                    eat(map, a, b, a+1, b);
                } else if (map[a+1][b] == null) {
                    move(map, a, b, a+1, b);
                } else if (map[a+1][b] instanceof Sheep && !this.sameGender((Animal)map[a+1][b])) {
                    breed(map, a+1, b);
                }
            }
        } else if (rand == 2) {
            if (b >= 1) {
                if (map[a][b-1] instanceof Grass) {
                    eat(map, a, b, a, b-1);
                } else if (map[a][b-1] == null) {
                    move(map, a, b, a, b-1);
                } else if (map[a][b-1] instanceof Sheep && !this.sameGender((Animal)map[a][b-1])) {
                    breed(map, a, b-1);
                }
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE-1) {
                if (map[a][b+1] instanceof Grass) {
                    eat(map, a, b, a, b+1);
                } else if (map[a][b+1] == null) {
                    move(map, a, b, a, b+1);
                } else if (map[a][b+1] instanceof Sheep && !this.sameGender((Animal)map[a][b+1])) {
                    breed(map, a, b+1);
                }
            }
        }
        this.subHealth(1);
        if (this.getCooldown() > 0) {
            this.subCooldown(1);
        }
    }
}