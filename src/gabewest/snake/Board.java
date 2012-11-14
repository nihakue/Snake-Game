package gabewest.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 6988143076493214755L;
	private final int WIDTH = 300;
	private final int HEIGHT = 300;
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = 900; 
	private final int RAND_POS = 29;
	private final int STARTDELAY = 90;
	private int delay = 70; 

	private int x[] = new int[ALL_DOTS];
	private int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;
	private boolean playAgain = false;
	
	private Timer timer;
	private Image ball;
	private Image apple;
	private Vector<Integer> keyVector;
	
	public Board(){
		
		addKeyListener(new TAdapter());
		keyVector = new Vector<Integer>(5);
		
		setBackground(Color.black);
		
		ImageIcon iid = new ImageIcon(this.getClass().getResource("dot2.png"));
		ball = iid.getImage();
		ImageIcon iia = new ImageIcon(this.getClass().getResource("apple3.png"));
		apple = iia.getImage();
		
		setFocusable(true);
		initGame();
		
		
	}
	
	public void initGame() {
		dots = 3;
		
		for (int i = 0; i<dots; i++){
			x[i] = 50-i*10;
			y[i] = 50;
		}
		
		locateApple();
		
		timer = new Timer(STARTDELAY, this);
		timer.start();
		inGame = true;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(inGame){
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i = 0; i<dots; i++){
				if (i==0){
					g.drawImage(ball, x[i], y[i], this);
				}
				else{ 
					g.drawImage(ball, x[i], y[i], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		else{
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g){
		String msg = "You got chlamydia.";
		String playAgainPrompt = "Play again?\n Press any key to start!";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT/2);
		g.drawString(playAgainPrompt, (WIDTH - metr.stringWidth(playAgainPrompt)) / 2, (HEIGHT/2) + 50);
		if(playAgain == true){
			resetGame();
		}
	}
	
	public void resetGame() {
		playAgain = false;
		delay = STARTDELAY;
		timer.setDelay(STARTDELAY);
		keyVector.clear();
		dots = 3;
	
		for (int i = 0; i<dots; i++){
			x[i] = 50-i*10;
			y[i] = 50;
		}
		right = true;
		left = false;
		up = false;
		down = false;
		
		locateApple();
		inGame = true;
		
	}

	public void checkApple(){
		
		if ((x[0] == apple_x && y[0] == apple_y)){
			dots++;
			delay--;
			timer.setDelay(delay);
			locateApple();
		}
	}
	
	public void move(){
		
		for (int i = dots; i>0; i--){
			x[i] = x[(i-1)];
			y[i] = y[(i-1)];
		}
		
		if (left){
			x[0] -= DOT_SIZE;
		}
		
		if(right){
			x[0] += DOT_SIZE;
		}
		if(up){
			y[0] -= DOT_SIZE;
		}
		
		if(down){
			y[0] += DOT_SIZE;
		}
		
	}
	
	public void checkCollision(){
		
		for ( int i  = dots; i>0; i--){
			if ((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
				inGame=false;
			}
		}
		
		if (y[0]>HEIGHT){
			inGame=false;
		}
		if (y[0]<0){
			inGame=false;
		}
		if (x[0]>WIDTH){
			inGame=false;
		}
		if (x[0]<0){
			inGame=false;
		}
		
	}
	
	public void controlBuffer(){
		for (int i = 0; i<keyVector.size(); i++){
			if ((keyVector.elementAt(0) == KeyEvent.VK_LEFT) && (!right)){
				while (x[1] == x[0]-10){
					if (keyVector.elementAt(1) == 38){
						up = true;
						down = false;
						right = false;
						left = false;
					}
					else if (keyVector.elementAt(1) == 40){
						down = true;
						up = false;
						right = false;
						left = false;
					}
				}
				up = false;
				down = false;
				right = false;
				left = true;
				}
			if ((keyVector.elementAt(0) == KeyEvent.VK_RIGHT) && (!left)){
				while (x[1] == x[0]+10){
					if (keyVector.elementAt(1) == 38){
						up = true;
						down = false;
						right = false;
						left = false;
					}
					else if (keyVector.elementAt(1) == 40){
						down = true;
						up = false;
						right = false;
						left = false;
					}
				}
				up = false;
				down = false;
				left = false;
				right = true;
			}
			if ((keyVector.elementAt(0) == KeyEvent.VK_UP) && (!down)){
				while (y[1] == y[0]-10){
					if (keyVector.elementAt(1) == 39){
						up = false;
						down = false;
						right = true;
						left = false;
					}
					else if (keyVector.elementAt(1) == 37){
						down = false;
						up = false;
						right = false;
						left = true;
					}
				}
				left = false;
				right = false;
				down = false;
				up = true;
			}
			if ((keyVector.elementAt(0) == KeyEvent.VK_DOWN) && (!up)){
				while (y[1] == y[0]+10){
					if (keyVector.elementAt(1) == 39){
						up = false;
						down = false;
						right = true;
						left = false;
					}
					else if (keyVector.elementAt(1) == 37){
						down = false;
						up = false;
						right = false;
						left = true;
					}
				}
				left = false;
				right = false;
				up = false;
				down = true;
				
			}
			keyVector.remove(0);
		}
	}
	
	public void locateApple(){
		int r = (int)(Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));
		r = (int)(Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
		for ( int i = 0; i<dots;i++){
			if (apple_x == x[i] && apple_y == y[i]){
				locateApple();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if (inGame){
			checkApple();
			checkCollision();
			move();
			if (keyVector.size()>0){
				controlBuffer();
			}
		}
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e){
		
			if(!inGame){
				playAgain = true;
			}
			else{
				keyVector.addElement(e.getKeyCode());
			}
		}
	}
	
	
}



	
