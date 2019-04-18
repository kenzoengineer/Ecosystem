/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Ken Jiang
 */

class GridTest { 
    public static final int ANIMAL_NUMBER = 100;
    public static final int SIZE = 25;
    public static final int DELAY = 1;
  
  public static void main(String[] args) { 
    Object map[][] = new Object[SIZE][SIZE];
    // Initialize Map
    setupGame(map);
    // display the fake grid on Console
    //DisplayGridOnConsole(map);
    
    //Set up Grid Panel
    DisplayGrid grid = new DisplayGrid(map);
    
    while(true) {
    //Display the grid on a Panel
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
      int rand1, rand2;
      for (int i = 0; i < ANIMAL_NUMBER * 15; i++) {
      rand1 = (int) (Math.random() * SIZE);
      rand2 = (int) (Math.random() * SIZE);
      if (map[rand1][rand2] == null && i <= ANIMAL_NUMBER - 1) map[rand1][rand2] = new Sheep(50);
      if (map[rand1][rand2] == null && i <= (ANIMAL_NUMBER * 2) - 1) map[rand1][rand2] = new Wolf(50);
      if (map[rand1][rand2] == null && i <= (ANIMAL_NUMBER * 15) - 1) map[rand1][rand2] = new Grass(15);
      }
  }
  
  // Method to simulate grid movement
  public static void moveItemsOnGrid(Object[][] map) { 
      for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
              if (map[i][j] instanceof Animal && !((Animal)map[i][j]).getTired()) {
                  if(((Animal)map[i][j]).dead()) {
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
      int rand = (int)(Math.random() * 10);
      if (rand == 7) {
          for (int i = 0; i < 5; i++) {
              int rand1 = (int)(Math.random() * SIZE);
              int rand2 = (int)(Math.random() * SIZE);
              if (map[rand1][rand2] == null) {
                  map[rand1][rand2] = new Grass(5);
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
}