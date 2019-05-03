/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
class DisplayGrid { 
    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private Object world[][] = new Object[GridTest.SIZE][GridTest.SIZE];
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
  
  
    public void refresh() { 
        frame.repaint();
    }
  
  
  
    class GridAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {        
            //super.repaint();
            setDoubleBuffered(true); 
            Color bg = darkGreen;
            if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Winter")) {
                bg = new Color(140,179,242);
            } else if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Fall")) {
                bg = new Color(173,132,83);
            } else if (GridTest.season.substring(0,GridTest.season.indexOf(" ")).equals("Spring")) {
                bg = new Color(100,145,63);
            }
            for(int i = 0; i<world[0].length;i=i+1) { 
                for(int j = 0; j<world.length;j=j+1) { 
                    if (world[i][j] instanceof Sheep) {   //This block can be changed to match character-color pairs
                        g.setColor(bg);
                        g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                        g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    } else if (world[i][j] instanceof Grass) {
                        g.setColor(bg);
                        g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                        g.drawImage(grass,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    } else if (world[i][j] instanceof Wolf) {
                        g.setColor(bg);
                        g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                        g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,null);
                    } else {
                        g.setColor(bg);
                        g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    }

                }
            }
            
            
            final int LOG_SIZE = 10;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
            for (int i = 0; i < LOG_SIZE && !GridTest.queue.isEmpty(); i++) {
                log.add(GridTest.queue.poll());
            }
            while (log.size() > LOG_SIZE) {
                log.remove(0);
            }
            for (int i = 0; i < log.size(); i++) {
                g2d.drawString(log.get(i), GridTest.SIZE * GridToScreenRatio + 50, 50 + (i * 50));
            }
            
            g2d.setFont(new Font("Cambria", Font.PLAIN, 30));
            g2d.drawString(GridTest.turn + " " + GridTest.season, 10, 160 + (LOG_SIZE * 50));
            g2d.drawString("Wolf Count: " + GridTest.wolfC, GridTest.SIZE * GridToScreenRatio + 50, 100 + (LOG_SIZE * 50));
            g2d.drawString("Sheep Count: " + GridTest.sheepC, GridTest.SIZE * GridToScreenRatio + 50, 150 + (LOG_SIZE * 50));
            
            //g.setColor(new Color(0,0,0,127));
            //g.fillRect(0, 0, GridTest.SIZE * GridToScreenRatio, GridTest.SIZE * GridToScreenRatio);
        }
    }//end of GridAreaPanel
} //end of DisplayGrid

