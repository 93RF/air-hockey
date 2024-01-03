/* GamePanel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener, ActionListener{

  /**
	 * 
	 */
	private static final long serialVersionUID = -5939662253794625448L;
//dimensions of window
  public static final int GAME_WIDTH = 500;
  public static final int GAME_HEIGHT = 666;

  int di;
  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public PlayerBall ball;
  public PlayerBall2 ball2;
  public Puck ball3;
  public Points score;
  public Timer timer;
  int timePassed=0;
  
  //Sound Effects
  Clip goal = AudioSystem.getClip();
  Clip wall = AudioSystem.getClip();
  Clip hit = AudioSystem.getClip();
  Clip gameOver = AudioSystem.getClip();
  
  //Collision Angles
  double yAng = 0;
  double xAng = 0;
  
  //Score
  int blueScore = 0;
  int redScore = 0;
  
  Image background;

  public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
    ball = new PlayerBall((GAME_WIDTH/2)-27, 0); //create a player controlled ball, set start location to middle of screen
    ball2 = new PlayerBall2((GAME_WIDTH/2)-27, GAME_HEIGHT); //create a player controlled ball, set start location to middle of screen
    ball3 = new Puck((GAME_WIDTH/2)-25, (GAME_HEIGHT/2)-25); //create a game controlled puck
    score = new Points(blueScore, redScore); //create score
    this.setFocusable(true); //make everything in this class appear on the screen
    this.addKeyListener(this); //start listening for keyboard input
    timer = new Timer(1000,this);
    timer.start();
    
    /*Give declared clips above audio*/
    File file = new File("Assets/applause.wav");
	AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
	goal.open(audioStream); //Sound effect for when either side scores a goal
	
	File file2 = new File("Assets/wall.wav");
	audioStream = AudioSystem.getAudioInputStream(file2);
	wall.open(audioStream); //Sound effect for when the puck bounces off the boundaries of the window
	
	File file3 = new File("Assets/collide.wav");
	audioStream = AudioSystem.getAudioInputStream(file3);
	hit.open(audioStream); //Sound effect for when either paddles make contact with the puck
	
	File file4 = new File("Assets/gameover.wav");
	audioStream = AudioSystem.getAudioInputStream(file4);
	gameOver.open(audioStream);
	
    
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); //Make the window on which things like the background, paddles, and puck go

    //make this class run at the same time as other classes (without this each class would "pause" while another class runs). By using threading we can remove lag, and also allows us to do features like display timers in real time!
    gameThread = new Thread(this);
    gameThread.start();
  }

  //paint is a method in java.awt library that we are overriding. It is a special method - it is called automatically in the background in order to update what appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g){
	Graphics2D g2D = (Graphics2D) g;
	g2D.drawImage(background, 0, 0, this);
    //we are using "double buffering here" - if we draw images directly onto the screen, it takes time and the human eye can actually notice flashes of lag as each pixel on the screen is drawn one at a time. Instead, we are going to draw images OFF the screen, then simply move the image on screen as needed. 
    image = createImage(GAME_WIDTH, GAME_HEIGHT); //draw off screen
    graphics = image.getGraphics();
    draw(graphics);//update the positions of everything on the screen 
    g2D.drawImage(image, 0, 0, this); //move the image on the screen

  }

  //call the draw methods in each class to update positions as things move
  public void draw(Graphics g){
    ball.draw(g);
    ball2.draw(g);
    ball3.draw(g);
    score.draw(g);
    if(timePassed<1) {
        score.condition(g);
        }
    
	if(blueScore==10) {
		audio(gameOver);
		score.winner(g);
	}
	
	if(redScore==10) {
		score.winner(g);
		audio(gameOver);
	}
  }

  //call the move methods in other classes to update positions
  //this method is constantly called from run(). By doing this, movements appear fluid and natural. If we take this out the movements appear sluggish and laggy
  public void move(){
    ball.move();
    ball2.move();
    ball3.move();
  }

  /*handles all collision detection and responds accordingly*/
  public void checkCollision(){
	if(blueScore==10 || redScore==10) {
		//Resets the paddles and pucks current position to the position where they started off and locks it there
        ball.x = (GAME_WIDTH/2)-27; 
        ball.y = 0; 
        ball2.x = (GAME_WIDTH/2)-27;
        ball2.y = (GAME_HEIGHT)-54;
        ball3.x = (GAME_WIDTH/2)-25;
        ball3.y = (GAME_HEIGHT/2)-25;
     }
	//Resets Positions if the red paddle scores on the blue paddle
	if((ball3.y + 30>=GAME_HEIGHT-40) && (ball3.x + 30 >185 && ball3.x + 30<308)) {
		stop(goal, hit, wall); //Stops any audio currently playing //Stops any audio currently playing
		audio(goal); //Plays audio for when a goal is scored
        redScore++; //Adds a goal to red
        score.update(blueScore, redScore); //Updates score that is printed on the screen
        
        /*Resets red and blue paddles' current position to the position where they started off*/
		ball.x = (GAME_WIDTH/2)-27; 
        ball.y = 0; 
        ball2.x = (GAME_WIDTH/2)-27;
        ball2.y = (GAME_HEIGHT)-54;
        
        /*Resets the puck's position on the blue paddle's side*/
        ball3.x = GAME_WIDTH/2-28;
        ball3.y = GAME_HEIGHT - 150;
        ball3.xVelocity = 0;
        ball3.yVelocity = 0;
    }

    if((ball3.y + 30<=40) && (ball3.x + 30 >185 && ball3.x + 30<308)) {
    	stop(goal, hit, wall); //Stops any audio currently playing //Stops any audio currently playing
    	audio(goal); //Plays audio for when a goal is scored
    	blueScore++; //Adds a goal to blue
    	score.update(blueScore, redScore); //Updates score that is printed on the screen
    	
    	/*Resets red and blue paddles' current position to the position where they started off*/
        ball.x = GAME_WIDTH/2-27;
        ball.y = 0;
        ball2.x = GAME_WIDTH/2-27;
        ball2.y = GAME_HEIGHT-54;
        
        /*Resets the puck's position on the red paddle's side*/
        ball3.x = GAME_WIDTH/2-28;
        ball3.y = 140;
        ball3.xVelocity = 0;
        ball3.yVelocity = 0;
    }
	
    /*If ball is in the range of puck, the puck gets hit with a velocity facing away from the ball*/
	if (Math.abs(((ball.x + 30)- (ball3.x + 25)) * ((ball.x + 30) - (ball3.x + 25)) + ((ball.y + 30) - (ball3.y + 25)) * ((ball.y + 30) - (ball3.y + 25))) < (30 + 25) * (30 + 25)) {
		stop(goal, hit, wall); //Stops any audio currently playing
		audio(hit); //Plays audio for when there is a paddle-puck collision
		yAng = ball.y - ball3.y;
		xAng = ball.x - ball3.x;
		if (ball.x < ball3.x) {
			di = 1;
		}
		if (ball.x > ball3.x) {
			di = -1;
		}
		ball3.collide(1, xAng, yAng, di);
	}
	
	/*Does the same thing as the if statement above, but for the blue paddle*/
	if (Math.abs(((ball2.x + 30)- (ball3.x + 25)) * ((ball2.x + 30) - (ball3.x + 25)) + ((ball2.y + 30) - (ball3.y + 25)) * ((ball2.y + 30) - (ball3.y + 25))) < (30 + 25) * (30 + 25)) {
		stop(goal, hit, wall); //Stops any audio currently playing
		audio(hit); //Plays audio for when there is a paddle-puck collision
		yAng = ball2.y - ball3.y;
		xAng = ball2.x - ball3.x;
		if (ball2.x < ball3.x) {
			di = 1;
		}
		if (ball2.x > ball3.x) {
			di = -1;
		}
		ball3.collide2(1, xAng, yAng, di);
	}
	  
    /*Prevents paddles and puck from going off the screen*/
    if(ball.y<= 0){
      ball.y = 0;
    }
    if(ball.y >= (GAME_HEIGHT/2) - 30 - PlayerBall.BALL_DIAMETER){
      ball.y = (GAME_HEIGHT/2) - 30 -PlayerBall.BALL_DIAMETER; //Prevents red paddle from going to the blue paddle's side
    }
    if(ball.x <= 0){
      ball.x = 0;
    }
    if(ball.x + PlayerBall.BALL_DIAMETER >= GAME_WIDTH){
      ball.x = GAME_WIDTH-PlayerBall.BALL_DIAMETER;
    }
    
    if(ball2.y<= 0){
        ball2.y = 0;
      }
      if(ball2.y >= GAME_HEIGHT - PlayerBall2.BALL_DIAMETER){
        ball2.y = GAME_HEIGHT-PlayerBall2.BALL_DIAMETER;
      }
      
      if(ball2.y <= (GAME_HEIGHT/2)+30){
          ball2.y = (GAME_HEIGHT/2)+30; //Prevents blue paddle from going to the red paddle's side
        }
      if(ball2.x <= 0){
        ball2.x = 0;
      }
      if(ball2.x + PlayerBall2.BALL_DIAMETER >= GAME_WIDTH){
        ball2.x = GAME_WIDTH-PlayerBall2.BALL_DIAMETER;
      }
      
        if(ball3.x > GAME_WIDTH - (Puck.BALL_DIAMETER+10)) {
        	audio(wall); //Plays audio for when the puck bounces off the borders of the play area
            ball3.xVelocity = ball3.xVelocity * -1;
        }
        
        /*Makes it so the puck bounces off the boundaries of the screen*/
        if(ball3.x < 0) {
        	stop(goal, hit, wall); //Stops any audio currently playing
        	audio(wall); //Plays audio for when the puck bounces off the borders of the play area
        	ball3.xVelocity = ball3.xVelocity * -1;
        }

        if(ball3.y > GAME_HEIGHT-(Puck.BALL_DIAMETER+30)) {
        	stop(goal, hit, wall); //Stops any audio currently playing
        	audio(wall); //Plays audio for when the puck bounces off the borders of the play area
        	ball3.yVelocity = ball3.yVelocity * -1;
        }

        if(ball3.y < 0) {
        	stop(goal, hit, wall); //Stops any audio currently playing
        	audio(wall); //Plays audio for when the puck bounces off the borders of the play area
        	ball3.yVelocity = ball3.yVelocity * -1;
        }
  }

  //run() method is what makes the game continue running without end. It calls other methods to move objects,  check for collision, and update the screen
  public void run(){
    //the CPU runs our game code too quickly - we need to slow it down! The following lines of code "force" the computer to get stuck in a loop for short intervals between calling other methods to update the screen. 
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        move();
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  //if a key is pressed, we'll send it over to the PlayerBall class for processing
  public void keyPressed(KeyEvent e){
	if(timePassed>=1) {
		ball.keyPressed(e);
    	ball2.keyPressed(e);
	}
	if(timePassed>=1 && blueScore!=10 && redScore!=10) {
	    ball.keyPressed(e);
	    ball2.keyPressed(e);
	}
  }

  //if a key is released, we'll send it over to the PlayerBall class for processing
  public void keyReleased(KeyEvent e){
    ball.keyReleased(e);
    ball2.keyReleased(e);
	
  }
  
  //plays any given audio clip
  public void audio(Clip clip) {
	  clip.stop();
	  clip.setMicrosecondPosition(0);
	  clip.start();
  }
  
  //stops all audio clips to fix the issue of audio clips not being played since previous audio clips were still playing
  public void stop(Clip goal, Clip hit, Clip wall) {
	  goal.stop();
	  hit.stop();
	  wall.stop();
  }
  
  
  //left empty because we don't need it; must be here because it is required to be overridded by the KeyListener interface
  public void keyTyped(KeyEvent e){

  }

  public void actionPerformed(ActionEvent e) {
		timePassed++;
		if(timePassed>1) {
			timePassed=1;
		}
}
}