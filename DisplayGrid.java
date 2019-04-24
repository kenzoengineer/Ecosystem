/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.awt.GraphicsEnvironment;


class DisplayGrid { 
    private JFrame frame;
    private int maxX,maxY, GridToScreenRatio;
    private Object world[][] = new Object[GridTest.SIZE][GridTest.SIZE];
    Color darkGreen = new Color(31, 102, 28);
    DisplayGrid(Object[][] a) { 
        this.world = a; 
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
            g.setColor(Color.BLACK);
            for(int i = 0; i<world[0].length;i=i+1) { 
                for(int j = 0; j<world.length;j=j+1) { 
                    if (world[i][j] instanceof Sheep) {   //This block can be changed to match character-color pairs
                        g.setColor(Color.WHITE);
                    } else if (world[i][j] instanceof Grass) {
                        g.setColor(Color.GREEN);
                    } else if (world[i][j] instanceof Wolf)
                        g.setColor(Color.GRAY);
                    else {
                        g.setColor(darkGreen);
                    }
                    g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
                    if (world[i][j] != null) {
                        if (world[i][j] instanceof Animal) {
                            g.drawString(Integer.toString(((Animal)world[i][j]).getHealth()), j*GridToScreenRatio + GridToScreenRatio /2, i*GridToScreenRatio + GridToScreenRatio /2);
                        } else {
                            g.drawString(Integer.toString(((Grass)world[i][j]).getNutrition()), j*GridToScreenRatio + GridToScreenRatio /2, i*GridToScreenRatio + GridToScreenRatio /2);
                        }
                    }
                    //g.drawString("SHEEP COUNT: " + GridTest.sheepC, 900, 100);
                    //g.drawString("WOLF COUNT: " + GridTest.wolfC, 900, 200);
                }
            }
        }
    }//end of GridAreaPanel
} //end of DisplayGrid

