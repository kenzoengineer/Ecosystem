public class Wolf extends Animal implements Comparable<Wolf> {
    //there is a 1 / ERROR chance that the wolf will actively search for the sheep
    final int ERROR = 4;
    private int searchR = 2;
    public Wolf(int h) {
        super(h);
    }
    
    public int getRadius() {
        return searchR;
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
    public void eat(Organism[][] map, int wA, int wB, int sA, int sB) {
        int health = ((Animal) map[sA][sB]).getHealth();
        ((Animal)map[wA][wB]).subHealth(-1 * health);
        move(map,wA,wB,sA,sB);
        //System.out.println("Wolf ate a sheep");
        GridTest.queue.add("Turn " + GridTest.turn + ": Wolf ate sheep");
    }
    
    /**
     * Brute force finds location of prey within a radius
     * @param map a 2d array of the world
     * @param a the x coord
     * @param b the y coord
     * @param r the search radius
     */
    public void findClose(Organism[][] map, int a, int b, int r) {
        if (map[a][b] instanceof Sheep) {
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
     * When a wolf attacks another wolf. Biased towards attacker as there is an element of surprise
     * @param map a 2d array containing the world map
     * @param aA attacker x coord
     * @param aB attacker y coord
     * @param dA defender x coord
     * @param dB defender y coord
     */
    public void attack(Organism[][] map, int aA, int aB, int dA, int dB) {
        //attacker has more or equal hp
        if (this.compareTo((Wolf)map[dA][dB]) >= 0) {
            ((Animal)map[dA][dB]).subHealth(10);
            //defender is dead
            if (((Animal)map[dA][dB]).dead()) {
                move(map, aA, aB, dA, dB);
                //System.out.println("Defender lost and died");
                GridTest.queue.add("Turn " + GridTest.turn + ": Wolf killed defender");
            } else {
               GridTest.queue.add("Turn " + GridTest.turn + ": Wolf hurt defender");
            }
        //attacker has less hp
        } else {
            this.subHealth(10);
            GridTest.queue.add("Turn " + GridTest.turn + ": Wolf lost to defender");
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
    public void breed(Organism[][] map, int mA, int mB) {
        //if both animals have enought health to breed
        if (this.getHealth() > 20 && ((Animal)map[mA][mB]).getHealth() > 20 && (this.getCooldown() + ((Animal)map[mA][mB]).getCooldown() == 0) && findEmpty(map)[0] >= 0) {
            this.subHealth(10);
            ((Animal)map[mA][mB]).subHealth(10);
            this.setCooldown(10);
            GridTest.queue.add("Turn " + GridTest.turn + ": Wolves bred");
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
    public void moveRandom(Organism[][] map, int a, int b) {
        int rand;
        //resets closes prey value
        this.setXY(-1,-1);
        
        //if the animal is low hp, it lusts for blood and extends its search radius
        if (this.getHealth() < 34) { 
            searchR = 5;
        } else {
            searchR = 2;
        }
        
        //finds closest prey in the radius
        findClose(map, a, b, searchR);
        
        //if none is found, randomly move
        if (this.getXY()[0] != -1 && (((int) Math.random() * ERROR) == 0)) {
            rand = pathFind(a,b,getXY()[0], getXY()[1]);
        //else move towards the prey
        } else {
            rand = (int) (Math.random() * 4);
        }
        
        if (rand == 0) {
            if (a >= 1) {
                //if it's a sheep, eat it
                if (map[a-1][b] instanceof Sheep) {
                    eat(map, a, b, a-1, b);
                //if it's grass, trample it, or if it's empty, move to it
                } else if (map[a-1][b] == null || map[a-1][b] instanceof Grass) {
                    move(map, a, b, a-1, b);
                //if it's a wolf, attack or breed depending on gender
                } else if (map[a-1][b] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a-1][b])) {
                        attack(map, a, b, a-1, b);
                    } else {
                        breed(map, a-1, b);
                    }
                }
            } else {
                //move somewhere else if that move would be illegal
                rand = 1;
            }
        }
        if (rand == 1) {
            if (a < GridTest.SIZE-1) {
                if (map[a+1][b] instanceof Sheep) {
                    eat(map, a, b, a+1, b);
                } else if (map[a+1][b] == null || map[a+1][b] instanceof Grass) {
                    move(map, a, b, a+1, b);
                } else if (map[a+1][b] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a+1][b])) {
                        attack(map, a, b, a+1, b);
                    } else {
                        breed(map, a+1, b);
                    }
                }
            } else {
                rand = 0;
            }
        }
        if (rand == 2) {
            if (b >= 1) {
                if (map[a][b-1] instanceof Sheep) {
                    eat(map, a, b, a, b-1);
                } else if (map[a][b-1] == null || map[a][b-1] instanceof Grass) {
                    move(map, a, b, a, b-1);
                } else if (map[a][b-1] instanceof Wolf) {
                    if (this.sameGender((Animal)map[a][b-1])) {
                        attack(map, a, b, a, b-1);
                    } else {
                        breed(map, a, b-1);
                    }
                }
            } else {
                rand = 3;
            }
        }
        if (rand == 3) {
            if (b < GridTest.SIZE-1) {
                if (map[a][b+1] instanceof Sheep) {
                    eat(map, a, b, a, b+1);
                } else if (map[a][b+1] == null || map[a][b+1] instanceof Grass) {
                    move(map, a, b, a, b+1);
                } else if (map[a][b+1] instanceof Wolf){
                    if (this.sameGender((Animal)map[a][b+1])) {
                        attack(map, a, b, a, b+1);
                    } else {
                        breed(map, a, b+1);
                    }
                }
            } else {
                rand = 2;
            }
        }
        this.subHealth(1);
        if (this.getCooldown() > 0) {
            this.subCooldown(1);
        }
    }
}