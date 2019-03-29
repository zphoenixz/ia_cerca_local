import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class map extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CarSharingBoard board;
	
	public map() {
		setDoubleBuffered(false);
	}
	
	public List<UserExtended> getConductoresS1() {
		return conductoresS1;
	}

	public CarSharingBoard getBoard() {
		return board;
	}

	public void setBoard(CarSharingBoard board) {
		this.board = board;
	}

	public void setConductoresS1(List<UserExtended> conductoresS1) {
		this.conductoresS1 = conductoresS1;
	}

	Timer t1 = new Timer(1000, this);
	int x = 0, y = 0, velX = 1, velY = 1;
	private List<UserExtended> conductoresS1 = new ArrayList<UserExtended>();
	private Graphics2D g2;
	
	
	@Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);

//        g.fillRect(x, y, 50, 30);
        t1.start();      
        
        for(int i = 0; i <= 100; i++) {
        	g.setColor(Color.LIGHT_GRAY);
        	g.drawLine(i*8, 0, i*8, 800);
        	g.drawLine(0, i*8, 800, i*8);
        }
        
        try {
        	if(!board.getConductoresS1().isEmpty()) {
//            	System.out.println("Ya no estoy vacio!!!!!!!!!!!!!!!!");
            	int numUsers = board.getUsuarios().size();
//            	Color origenColor = new Color(128, 187, 193);
//            	Color destinoColor = new Color(128, 187, 193);
            	
      
            	g.setColor(Color.red);
            	
//            	System.out.println(numUsers);
            	for(int i = 0; i < board.getConductoresS1().size(); i++) {
            		
            		if(board.getConductoresS1().get(i).isActive()) {
            			printFrom2Coord(board.getConductoresS1().get(i), g);
            		}
            	}
            	
            	
              	for(int i = 0; i < numUsers; i++) {
            		int x0 = board.getUsuarios().get(i).getCoordOrigenX();
            		int y0 = board.getUsuarios().get(i).getCoordOrigenY();
            		
            		int x1 = board.getUsuarios().get(i).getCoordDestinoX();
            		int y1 = board.getUsuarios().get(i).getCoordDestinoY();
            		
            		String number = Integer.toString(i);
            				
            		if(board.getUsuarios().get(i).isConductor()) g.setColor(Color.blue);
            		else 										 g.setColor(new Color(17, 59, 8));
   
            		Font f = new Font("Courier New", Font.BOLD, 10);
            		g.setFont(f);
            		g.drawString(number, x0*8+4, y0*8+4);
            		g.drawString(number, x1*8+4, y1*8+4);
            		
            		g.fillOval(x0*8+4, y0*8+4, 8, 8);//Origen
        			g.fillRect(x1*8+4, y1*8+4, 8, 8);//Destino		
            	}
            	
            	
            }
		} catch (Exception e) {
//			System.out.println("Estoy vacio!!!!!!!!!!!!!!!!");
		} 
        
        
        
    }
	
	public void printFrom2Coord(UserExtended conductor, final Graphics g) {
//		 int xEnd, int yEnd;
		
//		super.paintComponent(g);
		
		int xStart = board.getUsuarios().get(conductor.getConductorId()).getCoordOrigenX();
		int yStart = board.getUsuarios().get(conductor.getConductorId()).getCoordOrigenY();
		
		
		int xEnd = 0, yEnd = 0;
		
		for(int i = 0; i < conductor.getSchedulePasajeros().size();i++) {
			int boolStartOrEnd = conductor.getSchedulePasajeros().get(i)[1];
			
			if(boolStartOrEnd == 0) {
				xEnd = board.getUsuarios().get(conductor.getSchedulePasajeros().get(i)[0]).getCoordOrigenX();
				yEnd = board.getUsuarios().get(conductor.getSchedulePasajeros().get(i)[0]).getCoordOrigenY();
			}else {
				xEnd = board.getUsuarios().get(conductor.getSchedulePasajeros().get(i)[0]).getCoordDestinoX();
				yEnd = board.getUsuarios().get(conductor.getSchedulePasajeros().get(i)[0]).getCoordDestinoY();
			}
			
			int xDiff = xEnd - xStart;
			int sentidoX = 1;

			if(xDiff>0) sentidoX = 1;
			else sentidoX = -1;

			while(xStart != xEnd) {
				g.fillRect(xStart*8+6, yStart*8+6, 4, 4);
				xStart += sentidoX;
			}
			
			int yDiff = yEnd - yStart;
			int sentidoY = 1;
			
			if(yDiff>0) sentidoY = 1;
			else sentidoY = -1;
			
			while(yStart != yEnd) {
				g.fillRect(xStart*8+6, yStart*8+6, 4, 4);
				yStart += sentidoY;
			}
			
		}
		//-------------------------
		xEnd = board.getUsuarios().get(conductor.getConductorId()).getCoordDestinoX();
		yEnd = board.getUsuarios().get(conductor.getConductorId()).getCoordDestinoY();
		
		int xDiff = xEnd - xStart;
		int sentidoX = 1;

		if(xDiff>0) sentidoX = 1;
		else sentidoX = -1;

		while(xStart != xEnd) {
			g.fillRect(xStart*8+6, yStart*8+6, 4, 4);
			xStart += sentidoX;
		}
		
		int yDiff = yEnd - yStart;
		int sentidoY = 1;
		
		if(yDiff>0) sentidoY = 1;
		else sentidoY = -1;
		
		while(yStart != yEnd) {
			g.fillRect(xStart*8+6, yStart*8+6, 4, 4);
			yStart += sentidoY;
		}
		
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(x<0||x>800) {
			velX = -velX;

		}
		if(y<0||y>800) velY = -velY;
		
		x += velX; 
		y += velY;
		repaint();	
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
}
