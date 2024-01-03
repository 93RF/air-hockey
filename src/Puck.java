/* Puck class defines behaviours for the puck  */ 
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Puck extends Rectangle{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  double temp = 0;
  int tempTwo = 0;
  public double yVelocity;
  public double xVelocity;
  public final int SPEED = 5; //movement speed of ball
  public static final int BALL_DIAMETER = 40; //size of ball
  Image puck;
  String blueScore="";
  String redScore="";

  //constructor creates puck at given location with given dimensions
  public Puck(int x, int y){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
    puck = new ImageIcon("Assets/puck.png").getImage();
  }
  
  //called frequently from both PlayerBall class and GamePanel class
  //updates the current location of the ball
  public void move(){
      if (yVelocity < 0) {
        y = y + (int) Math.round(yVelocity);
        x = x + (int) Math.round(xVelocity);
      }
      if (yVelocity > 0) {
        y = y + (int) Math.round(yVelocity);
        x = x + (int) Math.round(xVelocity);
      }
      if (yVelocity == 0) {
        y = y + (int) Math.round(yVelocity);
        x = x + (int) Math.round(xVelocity);
      }
  }

  //called frequently from the GamePanel class
  //draws the current location of the ball to the screen
  public void draw(Graphics g){
    Graphics2D g2D = (Graphics2D) g;
    g2D.drawImage(puck, x, y, null);
  }
  
  //changes the direction and velocity of the puck when hit
  public void collide(int l, double ex, double why, int direction){
      if(l == 1){
          if (direction == 1) {
              yVelocity = -why / 7;
              xVelocity = -ex / 7;
          }
          if (direction == -1) {
              yVelocity = -why / 7;
              xVelocity = -ex / 7;
          }
          if (direction == 0) {
              xVelocity = ex / 7;
              yVelocity = -why / 7;
          }
          move();
        }
    }
  
//changes the direction and velocity of the puck when hit
  public void collide2(int l, double ex, double why, int direction){
      if(l == 1){
          if (direction == 1) {
              yVelocity = -why / 7;
              xVelocity = -ex / 7;
          }
          if (direction == -1) {
              yVelocity = -why / 7;
              xVelocity = -ex / 7;
          }
          if (direction == 0) {
              xVelocity = ex / 7;
              yVelocity = -why / 7;
          }
          move();
        }
    }
  

  
  
}