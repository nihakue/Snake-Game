package gabewest.snake;

import javax.swing.JFrame;


public class Snake extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Snake(){
		
		add(new Board());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(320, 340);
		setLocationRelativeTo(null);
		setTitle("Sneaky Snakey Gabey");
		
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args){
		Snake s=new Snake();
	}

}
