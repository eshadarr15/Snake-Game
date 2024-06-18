
import java.awt.*;
//import javax.swing.JPanel;

import java.awt.event.*;

import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener{

    //declare everything you need for this program before working on constructors
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; //how big are objects in game
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; 
    static final int DELAY = 75;

    //create two arrays to hold coordinates for body parts of snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6; //initial body part units of the snake
    int applesEaten = 0;
    int appleX; //x coordinate of where apple is, appears randomly each time
    int appleY; //y coordinate of where apple is 
    char direction = 'R'; //Snake begins by going rights, l for left, r for right, u for up, d for down
    boolean running = false;
    Timer timer;
    Random random; 
    
    GamePanel(){
        random = new Random();
        
        //set preferred size for game panel 
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       
        //set background color 
        this.setBackground(Color.black);

        //set focusability
        this.setFocusable(true);

        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple(); // creates a new apple on the screen
        running = true;
        timer = new Timer(DELAY,this); //dictates how fast the game is running
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if (running) {
             //create a grid for visibility
             for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
                 g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                 g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH , i*UNIT_SIZE);
                    }

                   g.setColor(Color.red);
                   g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                    //draw the head and body of the snake
                    for (int i = 0; i < bodyParts; i++){
                          if (i == 0){
                           g.setColor(Color.green);
                           g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                         }
                             else {
                             g.setColor(new Color(45, 180, 0));
                             g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                         }
                    }
                       g.setColor(Color.red);
                       g.setFont(new Font("Ink Free", Font.BOLD, 40));
                       FontMetrics metrics = getFontMetrics(g.getFont());
                       g.drawString("Score: "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());


                }
                else {
                    gameOver(g); //g is the graphic recieved within  the parameter
                }
      }

    public void newApple(){
        //generates the coordinates of a new apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void move(){
        for (int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) 
        {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;

    }
 }
    


    public void checkApple(){

        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions(){

        //this method helps to end the game if head touches borders or any body part

        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--){
            if ((x[0]==x[i]) && (y[0]==y[i])){
                running = false;
            }
        } 
        //checks if head touches left border 
        if (x[0] < 0){
            running = false; 
        }

        //checks if head touches right border 
        if (x[0] > SCREEN_WIDTH){
            running = false; 
        }

        //checks if head touches top border
        if (y[0] < 0){
            running = false; 
        }

        //check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT){
            running = false; 
        }

        if (!running){
            timer.stop();
        }

    }

    public void gameOver(Graphics g){
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten , (SCREEN_WIDTH - metrics2.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());


    }

    public void actionPerformed(ActionEvent e){
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    } 

    public class MyKeyAdapter extends KeyAdapter{ //import this so it doesnt give any problems 
        @Override
        public void keyPressed(KeyEvent e){

            switch(e.getKeyCode()) {
            
                //limit user to 90 degree turns
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                         direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                         direction = 'R';
                    }
                    break;
                    
                 case KeyEvent.VK_UP:
                    if (direction != 'D'){
                         direction = 'U';
                    }
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                         direction = 'D';
                    }
                    break;

            }
        }

    }

}
