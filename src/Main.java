import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class Main extends JFrame implements ActionListener{
  
  public Graphics graphics;
  public Image image;
  JButton b;
  Image background;
 
  public Main(){

    this.setTitle("Air Hockey");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(500, 500);
    this.setVisible(true);
    b = new JButton("Click to start");
    Container c = getContentPane();
    b.addActionListener(this);
    c.setLayout(null);
    b.setSize(100, 50);
    b.setLocation(200, 230); 
    this.setLocationRelativeTo(null);
    c.add(b);
    JLabel label;  
    label=new JLabel("Red Paddle Controls: W, A, S, D"); 
    label.setBounds(169,120, 275,100);  
    c.add(label);
    c.setSize(400,400);  
    c.setLayout(null);  
    c.setVisible(true);  
    label=new JLabel("Blue Paddle Controls: I, J, K, L"); 
    label.setBounds(173,135, 275,125);  
    c.add(label);
    c.setSize(400,400);  
    c.setLayout(null);  
    c.setVisible(true);  
    }

  public static void main(String[] args)
  {
    new Main();
    
  }

public void actionPerformed(ActionEvent evt){
    try {
		new GameFrame();
	} catch (UnsupportedAudioFileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}