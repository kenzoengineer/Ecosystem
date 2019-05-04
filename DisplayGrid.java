/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
class DisplayGrid { 
    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private Object world[][] = new Object[GridTest.SIZE][GridTest.SIZE];
    private int[] sheepGraph = new int[500];
    private int[] grassGraph = new int[500];
    Color darkGreen = new Color(31, 102, 28);
    Image sheep = Toolkit.getDefaultToolkit().getImage("sheep.png");
    Image wolf = Toolkit.getDefaultToolkit().getImage("wolf.png");
    Image grass = Toolkit.getDefaultToolkit().getImage("grass.png");
    List<String> log = new ArrayList<>();
    DisplayGrid(Object[][] a) { 
        this.world = a; 
        log.add("Game Started");
        //get fonts
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map

        System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);

        this.frame = new JFrame("Map of World");

        GridAreaPanel worldPanel = new GridAreaPanel();

        frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }
  
    /**
    * Refresh the map
    */
    public void refresh() { 
        frame.repaint();
    }
    
    /**
     * Helper method to an integer up an index
     * @param arr the array containing the integers
     * @param a the index of the integer
     */
    public void push(int[] arr, int a) {
        arr[a+1] = arr[a];
        arr[a] = 0;
    }
  
    class GridAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {        
            //super.repaint();
            setDoubleBuffered(true); 
            //sets the background color depending on the season
            Color bg = darkGreen;
            if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Winter")) {
                bg = new Color(140,179,242);
            } else if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Fall")) {
                bg = new Color(173,132,83);
            } else if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Spring")) {
                bg = new Color(100,145,63);
            }
            
            //draw background
            g.setColor(bg);
            g.fillRect(0,0,GridTest.SIZE * GridToScreenRatio, GridTest.SIZE * GridToScreenRatio);
            
            for(int i = 0; i<world[0].length;i=i+1) { 
                for(int j = 0; j<world.length;j=j+1) { 
                    //drawing images depending on the object type
                    if (world[i][j] instanceof Sheep) {
                        g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    } else if (world[i][j] instanceof Grass) {
                        g.drawImage(grass,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    } else if (world[i][j] instanceof Wolf) {
                        g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    }
                }
            }
            
            //how many events to display in the event log
            final int LOG_SIZE = 10;
            //create a graphics2d object to antialias text and make it look better
            Graphics2D g2d = (Graphics2D) g;
            //enable antialiasing
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            
            //add all events in the queue to the arraylist
            for (int i = 0; i < LOG_SIZE && !GridTest.queue.isEmpty(); i++) {
                log.add(GridTest.queue.poll());
            }
            //remove all extraneous items
            while (log.size() > LOG_SIZE) {
                log.remove(0);
            }
            //display all items in the arraylist
            for (int i = 0; i < log.size(); i++) {
                g2d.drawString(log.get(i), GridTest.SIZE * GridToScreenRatio + 50, 25 + (i * 25));
            }
            
            //display text for turns, season, wolf count and sheep count
            g2d.setFont(new Font("Cambria", Font.PLAIN, 30));
            g2d.drawString("Turn " + GridTest.turn + ", " + GridTest.season, 10, GridTest.SIZE * GridToScreenRatio + 30);
            g.drawLine(GridTest.SIZE * GridToScreenRatio + 50, 15 + (LOG_SIZE * 25), GridTest.SIZE * GridToScreenRatio + 300, 15 + (LOG_SIZE * 25));
            g2d.setFont(new Font("Cambria", Font.PLAIN, 20));
            g2d.drawString("Wolf Count: " + GridTest.wolfC, GridTest.SIZE * GridToScreenRatio + 50, 50 + (LOG_SIZE * 25));
            g2d.drawString("Grass Count: " + GridTest.grassC, GridTest.SIZE * GridToScreenRatio + 50, 80 + (LOG_SIZE * 25));
            g2d.drawString("Sheep Count: " + GridTest.sheepC, GridTest.SIZE * GridToScreenRatio + 50, 400 + (LOG_SIZE * 25));
            
            //GRASS COUNT GRAPH
            g.setColor(Color.BLACK);
            g.drawRect(GridTest.SIZE * GridToScreenRatio + 50, 90 + (LOG_SIZE * 25), 485, 148);
            //push all datapoints forward
            for (int i = 478; i >=0 ; i--) {
                push(grassGraph,i);
            }
            //set the latest datapoint to the current grass count
            grassGraph[0] = GridTest.grassC;
            //draw the points onto the screen
            g.setColor(Color.RED);
            for (int i = 0; i < 500; i++) {
                //don't draw the point if the SHEEPGRAPH value is 0
                //this is because those points symbolize no data, as integer arrays are
                //initialized at 0. We cannot use grassGraph[i] = 0 as sometimes there will be no grass.
                //if (sheepGraph[i] != 0) {
                    g.fillOval(GridTest.SIZE * GridToScreenRatio + 530 - i, 235 + (LOG_SIZE * 25) - (grassGraph[i]/2), 2, 5);
                //}
            }
            //SHEEP COUNT GRAPH
            g.setColor(Color.BLACK);
            g.drawRect(980, 675, 485, 148);
            //push all datapoints forward
            for (int i = 478; i >=0 ; i--) {
                push(sheepGraph,i);
            }
            //set the latest datapoint to the current sheep count
            sheepGraph[0] = GridTest.sheepC;
            //draw the points onto the screen
            g.setColor(Color.RED);
            for (int i = 0; i < 500; i++) {
                //don't draw the point if it's value is 0
                //at the beginning all values are initiated to 0, so this is to not draw those points
                //also, when sheep go to 0, the program will end anyways
                if (sheepGraph[i] != 0) {
                    g.fillOval(1460 - i, 820 - (sheepGraph[i] * 2), 2, 5);
                }
            }
            
        }
    }//end of GridAreaPanel
} //end of DisplayGrid

