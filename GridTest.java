/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Ken Jiang
 */


class GridTest { 
    public static final int WOLF_NUMBER = 5;
    public static final int SHEEP_NUMBER = 70;
    public static final int GRASS_NUMBER = 100;
    public static final int SIZE = 25;
    public static final int DELAY = 10;
    
  public static void main(String[] args) { 
    Object map[][] = new Object[SIZE][SIZE];
    int turn = 0;
    // Initialize Map
    setupGame(map);
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    
    //Set up Grid Panel
    DisplayGrid grid = new DisplayGrid(map);
      System.out.println("Event log:");
    while(true) {
    //Display the grid on a Panel
    if(stable(map)) {
        turn++;
    } else {
        System.out.println("Turn : " + turn);
        break;
    }
    grid.refresh();
    
    
    //Small delay
    try {
        Thread.sleep(DELAY);
    }catch(Exception e) {
        e.printStackTrace();
    };
    
    
    // Initialize Map (Making changes to map)
    moveItemsOnGrid(map);
    moreGrass(map);
    //Display the grid on a Panel
    grid.refresh();
    }
  }
  
  public static void setupGame(Object[][] map) {
      int i = 0;
      while (i < WOLF_NUMBER) {
          int a = (int) (Math.random() * SIZE);
          int b = (int) (Math.random() * SIZE);
          if (map[a][b] == null) {
              map[a][b] = new Wolf(20);
              i++;
          }
      }
      i = 0;
      while (i < SHEEP_NUMBER) {
          int a = (int) (Math.random() * SIZE);
          int b = (int) (Math.random() * SIZE);
          if (map[a][b] == null) {
              map[a][b] = new Sheep(30);
              i++;
          }
      }
      i = 0;
      while (i < GRASS_NUMBER) {
          int a = (int) (Math.random() * SIZE);
          int b = (int) (Math.random() * SIZE);
          if (map[a][b] == null) {
              map[a][b] = new Grass(30);
              i++;
          }
      }
  }
  
  // Method to simulate grid movement
  public static void moveItemsOnGrid(Object[][] map) { 
      for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
              if (map[i][j] instanceof Animal && !((Animal)map[i][j]).getTired()) {
                  if(((Animal)map[i][j]).dead()) {
                      System.out.println( map[i][j].getClass().getName() + " died!");
                      map[i][j] = null;
                  } else {
                      ((Animal)map[i][j]).setTired(true);
                      if (map[i][j] instanceof Wolf) {
                          ((Wolf)map[i][j]).moveRandom(map,i,j);
                      } else {
                          ((Sheep)map[i][j]).moveRandom(map,i,j);
                      }
                  }
              }
          }
      }
      for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
              if (map[i][j] instanceof Animal) {
                  ((Animal)map[i][j]).setTired(false);
              }
          }
      }
  }
  
  public static void moreGrass(Object[][] map) {
      int rand = (int)(Math.random() * 7);
      if (rand == 1) {
          for (int i = 0; i < 5; i++) {
              int rand1 = (int)(Math.random() * SIZE);
              int rand2 = (int)(Math.random() * SIZE);
              if (map[rand1][rand2] == null) {
                  map[rand1][rand2] = new Grass(30);
                  System.out.println("Grass spawned");
                  return;
              }
          }
      }
  }
  
  //method to display grid a text for debugging
  public static void DisplayGridOnConsole(String[][] map) { 
    for(int i = 0; i< SIZE;i++){        
      for(int j = 0; j< SIZE;j++) 
        System.out.print(map[i][j]+" ");
      System.out.println("");
    }
  }
  
  public static boolean stable(Object[][] map) {
    int sC = 0;
    int wC = 0;
    for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map.length; j++) {
            if (map[i][j] instanceof Wolf) {
                wC++;
            } else if (map[i][j] instanceof Sheep) {
                sC++;
            }
        }
    }
    return sC > 0 && wC > 0;
  }
}