import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//import java.util.Arrays;
//import org.apache.commons.lang.ArrayUtils;

public class Agario extends JPanel implements ActionListener, KeyListener {
	//size of jframe
	int screen_width = 1900;
	int screen_height = 1000;
	
	int num_particles = 300;
	
	//player variables
	int p_size = 30;
	double p_x = screen_width/2;
	double p_y = screen_height/2;
	
	//lists used to create the particles
	int[] x = new int[num_particles];
	int[] y = new int[num_particles];
	
	//lists to make rgb values for random colors
	int[] r = new int[num_particles];
	int[] gr = new int[num_particles];
	int[] b = new int[num_particles];
	
	//speed that everything in the background moves
	int speed = 8;
	
	//variables used for the grid lines
	int spacing = 30;
	int space = 0;
	int _space = 0;
	int x_spacing = screen_height/spacing;
	int y_spacing = screen_width/spacing;
	
	//array coordinated for the grid lines
	int[] xlines = new int[x_spacing+2];
	int[] ylines = new int[y_spacing+2];
	
	//flags for arrow keys
	boolean up = false;
	boolean down = false;
	boolean left = false;
	boolean right = false;
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		//sets the background of the screen to black
		g.setColor(Color.black);
		g.fillRect(0, 0, screen_width, screen_height);
		
		//horizontal grid lines
		g.setColor(Color.gray);
		for(int i=0; i<xlines.length; i++) {
			g.drawLine(0, xlines[i], screen_width, xlines[i]);
		}
		
		//vertical grid lines
		for(int i=0; i<ylines.length; i++) {
			g.drawLine(ylines[i], 0, ylines[i], screen_height);
		}
		
		//creation of different colored particles
		for(int i=0; i<num_particles; i++) {	
			Color c = new Color(r[i], gr[i], b[i]);
			g.setColor(c);
			g.fillOval(x[i], y[i], 10, 10);
		}
		
		//creates a white circle that is the player
		g.setColor(Color.white);
		g.fillOval((int)p_x, (int)p_y, p_size, p_size);
		
		//writes the number of the players size in the center
		g.setColor(Color.black);
		g.drawString(Integer.toString(p_size), (int)p_x+(p_size/2)-6, (int)p_y+(p_size/2)+5);
		
	}//end of paint method - put code above for anything dealing with drawing -
	
	
	public void update() {
		if(up) {
			for(int i=0; i<y.length; i++) {
				y[i]+=speed;
			}
			for(int i=0; i<xlines.length; i++) {
				xlines[i]+=speed;
			}
		}
		
		if(down) {
			for(int i=0; i<y.length; i++) {
				y[i]-=speed;
			}
			for(int i=0; i<xlines.length; i++) {
				xlines[i]-=speed;
			}	
		}
		
		if(right) {
			for(int i=0; i<y.length; i++) {
				x[i]-=speed;
			}
			for(int i=0; i<ylines.length; i++) {
				ylines[i]-=speed;
			}
		}
		
		if(left) {
			for(int i=0; i<y.length; i++) {
				x[i]+=speed;
			}
			for(int i=0; i<ylines.length; i++) {
				ylines[i]+=speed;
			}
		}
		
		if(xlines[0]>p_y) {
			for(int i=0; i<xlines.length; i++) {
				xlines[i] -= speed;
			}
			for(int i=0; i<x.length; i++) {
				y[i] -= speed;
			}
		}
		
		if(ylines[0]>p_x) {
			for(int i=0; i<ylines.length; i++) {
				ylines[i] -= speed;
			}
			for(int i=0; i<x.length; i++) {
				x[i] -= speed;
			}
		}
		
		if(xlines[xlines.length-1]<p_y+p_size) {
			for(int i=0; i<xlines.length; i++) {
				xlines[i] += speed;
			}
			for(int i=0; i<x.length; i++) {
				y[i] += speed;
			}
		}
		if(ylines[ylines.length-1]<p_x+p_size) {
			for(int i=0; i<ylines.length; i++) {
				ylines[i] += speed;
			}
			for(int i=0; i<x.length; i++) {
				x[i] += speed;
			}
		}
		
		//collision detection for player and particle
		for(int i=0; i<num_particles; i++) {
			if(x[i]>p_x&&x[i]<(p_x)+p_size&&y[i]>p_y&&y[i]<(p_y)+p_size) {
				p_x-=0.5;
				p_y-=0.5;
				p_size++;
				x[i] = 1000000;
				y[i] = 1000000;
			}
		}
	}//end of update method - put code above for any updates on variable
	
			
	//==================code above ===========================
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
	
	public static void main(String[] arg) {
		Agario d = new Agario();
		
		//Random rnd = new Random();
		//rnd.nextInt(range+1)+min;
		
		//double x = Math.sqrt(16);
		//x = Math.pow(2,3)
		
		//Math.random();	//[0 1)
		
	}
	public Agario(){
		
		JFrame f = new JFrame();
		f.setTitle("Pong");
		f.setSize(screen_width, screen_height);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		//array for the x and y coordinates of every particle
		for(int i=0; i<x.length; i++) {
			x[i]=(int)(Math.random()*(screen_width+1));
			y[i]=(int)(Math.random()*(screen_height+1));
			
		}
		
		//array for rbg values to make random colors for each particle
		for(int i=0; i<r.length; i++) {
			r[i] = (int)(Math.random()*255);
			gr[i] = (int)(Math.random()*255);
			b[i] = (int)(Math.random()*255);
			
		}
		
		//array of cooridinates for the grid
		for(int i=0; i<xlines.length; i++) {
			xlines[i] = space;
			space += spacing;
		}
		
		for(int i=0; i<ylines.length; i++) {
			ylines[i] = _space;
			_space += spacing;
		}
	}
	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right =true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left =true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			up =false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right =false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left =false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
