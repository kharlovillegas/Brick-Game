import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener,
  ActionListener, Runnable {
 // movement keys
 static boolean right = false;
 static boolean left = false;

int GameState=0;
 // variables declaration for ball
 int ballx = 160;
 int bally = 218;
 // variables declaration for ball
 // variables declaration for bat
 int batx = 160;
 int baty = 388;

 // variables declaration for bat
 // variables declaration for brick
 int brickx = 70;
 int bricky = 50;

 //score
 int score =0;

 // variables declaration for brick
 // declaring ball, paddle,bricks
 Rectangle Ball = new Rectangle(ballx, bally, 5, 5);
 Rectangle Bat = new Rectangle(batx, baty, 40, 5);
 Rectangle[] Brick = new Rectangle[12];

 Game() {
 	addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                    if(GameState == 0) {
                        GameState = 1;
                    }
                }
            }
	});

 }

 public static void main(String[] args) {
  JFrame frame = new JFrame();
  Game game = new Game();
  JButton button = new JButton("restart");
  frame.setSize(350, 450);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  frame.add(game);
  frame.add(button, BorderLayout.SOUTH);
  frame.setLocationRelativeTo(null);
  frame.setResizable(false);
  frame.setVisible(true);
  button.addActionListener(game);

  game.addKeyListener(game);
  game.setFocusable(true);
  Thread t = new Thread(game);
  t.start();


 }

 // declaring ball, paddle,bricks

 public void paint(Graphics g) {
  g.fillRect(0,0, getWidth(),getHeight());
  g.setColor(Color.WHITE);
  Image img=Toolkit.getDefaultToolkit().getImage("brick.jpg");
  g.drawImage(img,0,0,null);
  g.fillRect(0, 400, 450, 200); //border when collides the rect
  g.fillRect(Bat.x,388, Bat.width, Bat.height); //location declaring the paddle or bat
  g.setColor(Color.ORANGE);//color of the paddle or bat
  g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
  g.setColor(Color.LIGHT_GRAY);
  g.drawString("Score: " + score, 5, 15);//appear the score in the frame
  g.setColor(Color.WHITE);//color of the bricks


	if(GameState==0){
	 	g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());

                Font fnt = new Font("Arial", Font.BOLD, 20);
                g.setFont(fnt);
                g.setColor(Color.WHITE);
                g.drawString("PRESS SPACEBAR TO START", 25, 210);
	}
	if(GameState==2){
	 	g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());

                Font fnt = new Font("Arial", Font.BOLD, 20);
                g.setFont(fnt);
                g.setColor(Color.WHITE);
                g.drawString("GAME OVER", 120, 230);
	}
  for (int i = 0; i < Brick.length; i++) {
   if (Brick[i] != null) {
    g.fill3DRect(Brick[i].x, Brick[i].y, Brick[i].width,
      Brick[i].height, true);

   }
  }

  /*if (ballFallDown == true || bricksOver == true) {
   Font f = new Font("Arial", Font.BOLD, 20);
   g.setFont(f);
   g.drawString(status, 70, 120);
   ballFallDown = false;
   bricksOver = false;
  }*/


 }

 //Game Loop
 //When ball strikes borders it reverses
 int movex = -1;
 int movey = -1;
 boolean ballFallDown = false;
 boolean bricksOver = false;
 int count = 0;
 String status;
 public void run() {
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                    if(GameState == 0) {
                        GameState = 1;
                    }
                }
            }
	});
  //Creating bricks for the game
  for (int i = 0; i < Brick.length; i++) {
   Brick[i] = new Rectangle(brickx, bricky, 30, 10);
   if (i == 5) {
    brickx = 70;
    bricky = 62;
   }
   if (i == 9) {
    brickx = 100;
    bricky = 74;
   }
   brickx += 31;
  }
  //bricks created for the game new ready to use
  //ball reverses when touches the brick
  while (true) {
   for (int i = 0; i < Brick.length; i++) {
    if (Brick[i] != null) {
     if (Brick[i].intersects(Ball)) {
      Brick[i] = null;
      score = score+10;
      movey = -movey;
      count++;
     }// end of 2nd if
    }// end of 1st if
   }// end of for loop


   if (count == Brick.length) {// check if ball hits all bricks
    bricksOver = true;
    status = "YOU WON THE GAME";
    repaint();
   }
   repaint();
   Ball.x += movex;
   Ball.y += movey;

   if (left == true) {

    Bat.x -= 3;
    right = false;
   }
   if (right == true) {
    Bat.x += 3;
    left = false;
   }
   if (Bat.x <= 4) {
    Bat.x = 4;
   } else if (Bat.x >= 298) {
    Bat.x = 298;
   }
   //Ball reverses when strikes the bat
   if (Ball.intersects(Bat)) {
    movey=-movey;
   }
   //ball reverses when touches left and right boundary
   if (Ball.x <= 0 || Ball.x + Ball.height >= 343) {
    movex = -movex;
   }// if ends here
   if (Ball.y <= 0) {
    movey = -movey;
   }// if ends here
   if (Ball.y >= 400) {// when ball falls below bat game is over
   gameover();
    }

   try {
    Thread.sleep(10);
   } catch (Exception ex) {
   }// try catch ends here

  }// while loop ends here

 }

 // loop ends here

 //handling key events
 @Override
 public void keyPressed(KeyEvent e) {


  int keyCode = e.getKeyCode();
  if (keyCode == KeyEvent.VK_LEFT) {
   left = true;
  }

  if (keyCode == KeyEvent.VK_RIGHT) {
   right = true;
  }
 }

 @Override
 public void keyReleased(KeyEvent e) {
  int keyCode = e.getKeyCode();
  if (keyCode == KeyEvent.VK_LEFT) {
   left = false;
  }

  if (keyCode == KeyEvent.VK_RIGHT) {
   right = false;
  }
 }

 @Override
 public void keyTyped(KeyEvent arg0) {

 }

 @Override
 public void actionPerformed(ActionEvent e) {
  String str = e.getActionCommand();
  if (str.equals("restart")) {
  	score =0;
   this.restart();

  }
 }
 public void gameover(){
 	GameState=2;
 	 if(GameState==2){
 	ballFallDown = true;
    //status = "YOU LOST THE GAME";
    //repaint();

     addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                    if(GameState == 2) {

                        GameState = 0;
                    }

 }
            }
     });
    }
 }


 public void restart() {

  requestFocus(true);
  // variables declaration for ball
  ballx = 160;
  bally = 218;
  // variables declaration for ball
  // variables declaration for bat
  batx = 160;
  baty = 388;
  // variables declaration for bat
  // variables declaration for brick
  brickx = 70;
  bricky = 50;
  // variables declaration for brick
  // declaring ball, paddle,bricks
  Ball = new Rectangle(ballx, bally, 5, 5);
  Bat = new Rectangle(batx, baty, 40, 5);
  Brick = new Rectangle[12];

  movex = -1;
  movey = -1;
  ballFallDown = false;
  bricksOver = false;
  count = 0;
  status = null;

  //Creating bricks for the game
  /*
   * creating bricks again because this for loop is out of while loop in
   * run method
   */
  for (int i = 0; i < Brick.length; i++) {
   Brick[i] = new Rectangle(brickx, bricky, 30, 10);
   if (i == 5) {
    brickx = 70;
    bricky = 62;
   }
   if (i == 9) {
    brickx = 100;
    bricky = 74;
   }
   brickx += 31;
  }
  repaint();
 }
}