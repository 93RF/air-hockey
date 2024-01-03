/* PlayerBall2 class defines behaviours for the blue paddle  */ 
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class PlayerBall2 extends Rectangle{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  public int yVelocity;
  public int xVelocity;
  public final int SPEED = 5; //movement speed of ball
  public static final int BALL_DIAMETER = 54; //size of ball
  Image paddle;

  //constructor creates blue paddle at given location with given dimensions
  public PlayerBall2(int x, int y){
    super(x, y, BALL_DIAMETER, BALL_DIAMETER);
    paddle = new ImageIcon("Assets/airhockey2.png").getImage();
  }

  //called from GamePanel when any keyboard input is detected
  //updates the direction of the ball based on user input
  //if the keyboard input isn't any of the options (d, a, w, s), then nothing happens
  public void keyPressed(KeyEvent e){
    if(e.getKeyChar() == 'l'){
      setXDirection(SPEED);
      move();
    }

    if(e.getKeyChar() == 'j'){
      setXDirection(SPEED*-1);
      move();
    }

    if(e.getKeyChar() == 'i'){
      setYDirection(SPEED*-1);
      move();
    }

    if(e.getKeyChar() == 'k'){
      setYDirection(SPEED);
      move();
    }
  }

  //called from GamePanel when any key is released (no longer being pressed down)
  //Makes the ball stop moving in that direction
  public void keyReleased(KeyEvent e){
    if(e.getKeyChar() == 'l'){
      setXDirection(0);
      move();
    }

    if(e.getKeyChar() == 'j'){
      setXDirection(0);
      move();
    }

    if(e.getKeyChar() == 'i'){
      setYDirection(0);
      move();
    }

    if(e.getKeyChar() == 'k'){
      setYDirection(0);
      move();
    }
  }

  //called whenever the movement of the ball changes in the y-direction (up/down)
  public void setYDirection(int yDirection){
    yVelocity = yDirection;
  }

  //called whenever the movement of the ball changes in the x-direction (left/right)
  public void setXDirection(int xDirection){
    xVelocity = xDirection;
  }

  //called frequently from both PlayerBall class and GamePanel class
  //updates the current location of the ball
  public void move(){
    y = y + yVelocity;
    x = x + xVelocity;
  }

  //called frequently from the GamePanel class
  //draws the current location of the blue paddle to the screen
  public void draw(Graphics g){
	Graphics2D g2D = (Graphics2D) g;
	g2D.drawImage(paddle, x, y, null); //Draws image of blue paddle
  }
  
}