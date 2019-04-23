public class Wolf extends Animal implements Comparable<Wolf>{
    public Wolf(int h) {
        super(h);
    }
    
    /**
     * Makes the wolf eat the sheep and moves the wolf to it's location
     * @param map a 2d array of the world
     * @param wA the wolf's x coordinate
     * @param wB the wolf's y coordinate
     * @param sA the sheep's x coordinate
     * @param sB the sheep's y coordinate
     */
    @Override
    public void eat(Object[][] map, int wA, int wB, int sA, int sB) {
        int health = ((Animal) map[sA][sB]).getHealth();
        ((Animal)map[wA][wB]).subHealth(-1 * health);
        move(map,wA,wB,sA,sB);
        //System.out.println("Wolf ate a sheep");
    }
    
    /**
     * Prioritizes sheep over moving to a null tile
     * @param map a 2d array of the world
     * @param a is the wolf's x coord
     * @param b is the wolf's x coord
     */
    @Override
    public int priority(Object[][] map, int a, int b) {
        if (a >= 1 && map[a-1][b] instanceof Sheep) {
            return 0;
        } else if (a < GridTest.SIZE - 1 && map[a+1][b] instanceof Sheep) {
            return 1;
        } else if (b >= 1 && map[a][b-1] instanceof Sheep) {
            return 2;
        } else if (b < GridTest.SIZE - 1 && map[a][b+1] instanceof Sheep) {
            return 3;
        }
        return -1;
    }
    
    /**
     * When a wolf attacks another wolf. Biased towards attacker as there is an element of surprise
     * @param map a 2d array containing the world map
     * @param aA attacker x coord
     * @param aB attacker y coord
     * @param dA defender x coord
     * @param dB defender y coord
     */
    public void attack(Object[][] map, int aA, int aB, int dA, int dB) {
        //attacker has more or equal hp
        if (this.compareTo((Wolf)map[dA][dB]) >= 0) {
            ((Animal)map[dA][dB]).subHealth(10);
            //defender is dead
            if (((Animal)map[dA][dB]).dead()) {
                move(map, aA, aB, dA, dB);
                //System.out.println("Defender lost and died");
            } else {
                //System.out.println("Defender lost but survived");
            }
        //attacker has less hp
        } else {
            this.subHealth(10);
            //System.out.println("Attacker lost!!");
        }
    }
    
    /**
     * Implements comparable to allow wolves to be sorted and compared to
     * @param w the other wolf
     * @return a positive or negative integer or 0 depending on whether the wolf's hp is
     * greater, less than or equal to the other
     */
    @Override
    public int compareTo(Wolf w) {
        return this.getHealth() - w.getHealth();
    }
    
    /**
     * Breeds two wolves of the opposite gender, creating a new wolf of hp 20 at
     * a random location on the map and subtracting 10 hp from both parents
     * @param map a 2d array of the world
     * @param mA the mate's x coordinate
     * @param mB the mate's y coordinate
     */
    @Override
    public void breed(Object[][] map, int mA, int mB) {
        //if both animals have enought health to breed
        if (this.getHealth() > 20 && ((Animal)map[mA][mB]).getHealth() > 20 && findEmpty(map)[0] >= 0) {
            this.subHealth(10);
            ((Animal)map[mA][mB]).subHealth(10);
            while (true) {
                int r1 = (int) (Math.random() * GridTest.SIZE);
                int r2 = (int) (Math.random() * GridTest.SIZE);
                if (map[r1][r2] == null) {
                    map[r1][r2] = new Wolf(20);
                    //System.out.println("Wolves bred " + r1 +  " " + r2);
                    return;
                }
            }
        }
    } 
    
    /**
     * Chooses a random direction for the wolf to move to. It then handles
     * breeding and eating.
     * @param map a 2d array of the world
     * @param a the wolf's x coordinate
     * @param b the wolf's y coordinate
     */
    @Override
    public void moveRandom(Object[][] map, int a, int b) {
        int rand;
        if (priority(map, a, b) == -1) {
            rand = (int) (Math.random() * 4);
        } else {
            rand = priority(map, a, b);
        }
        if (rand == 0) {
            if (a >= 1) {
                if (map[a-1][b] instanceof Sheep) {
                    eat(map, a, b, a-1, b);
                } else if (map[a-1][b] == null) {
                    move(map, a, b, a-1, b);
                } else if (map[a-1][b] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a-1][b])) {
                        attack(map, a, b, a-1, b);
                    } else {
                        breed(map, a-1, b);
                    }
                }
            }
        } else if (rand == 1) {
            if (a < GridTest.SIZE-1) {
                if (map[a+1][b] instanceof Sheep) {
                    eat(map, a, b, a+1, b);
                } else if (map[a+1][b] == null) {
                    move(map, a, b, a+1, b);
                } else if (map[a+1][b] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a+1][b])) {
                        attack(map, a, b, a+1, b);
                    } else {
                        breed(map, a+1, b);
                    }
                }
            }
        } else if (rand == 2) {
            if (b >= 1) {
                if (map[a][b-1] instanceof Sheep) {
                    eat(map, a, b, a, b-1);
                } else if (map[a][b-1] == null) {
                    move(map, a, b, a, b-1);
                } else if (map[a][b-1] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a][b-1])) {
                        attack(map, a, b, a, b-1);
                    } else {
                        breed(map, a, b-1);
                    }
                }
            }
        } else if (rand == 3) {
            if (b < GridTest.SIZE-1) {
                if (map[a][b+1] instanceof Sheep) {
                    eat(map, a, b, a, b+1);
                } else if (map[a][b+1] == null) {
                    move(map, a, b, a, b+1);
                } else if (map[a][b+1] instanceof Wolf){
                    if (this.sameGender((Animal)map[a][b+1])) {
                        attack(map, a, b, a, b+1);
                    } else {
                        breed(map, a, b+1);
                    }
                }
            }
        }
        this.subHealth(1);
    }
}