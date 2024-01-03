/*Points class handles and prints the score for each player*/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Timer;

public class Points {
    
    public String blue="";
    public String red="";
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 666;
    Timer timer;
    
    //Constructor takes integer values of score and converts them into a String
    public Points(int blueScore, int redScore) {
        blue = String.valueOf(blueScore);
        red = String.valueOf(redScore);
    }
    
    //Called from GamePanel whenever a goal is scored to add points to whoever score
    public void update(int blueScore, int redScore) {
        /*Changes integer values into a String*/
        blue = String.valueOf(blueScore);
        red = String.valueOf(redScore);
    }
    
    //Called frequently from GamePanel to print the current score
    public void draw(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(Color.red);
        g2D.drawString("red: "+red, 30, 40);
        g2D.setColor(Color.blue);
        g2D.drawString("blue: "+blue, 375, 636);
    }
    
    //Prints the win condition
    public void condition(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(Color.black);
        g2D.fillRect(GAME_WIDTH/2-150, GAME_HEIGHT/2-30, 317,40);
        g2D.setColor(new Color(120, 255, 120));
        g2D.drawString("First to 10 goals Wins!", GAME_WIDTH/2-137, GAME_HEIGHT/2);
    }
    
    //Shows text indicating that the game is over
        public void winner(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(Color.black);
            g2D.fillRect(GAME_WIDTH/2-150, GAME_HEIGHT/2-30, 317,40);
            g2D.setColor(new Color(120, 255, 120));
            
            //Prints certain text depending on who won
            if(Integer.parseInt(blue)==10) {
            	g2D.drawString("Game over, Blue Wins!", GAME_WIDTH/2-137, GAME_HEIGHT/2);
            }
            
            else if(Integer.parseInt(red)==10) {
                g2D.drawString("Game over, Red Wins!", GAME_WIDTH/2-137, GAME_HEIGHT/2);
            }
        }

}
