import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;


public class Interface extends JFrame implements ActionListener{

	/**
	 * Ramiro Valdez
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton go;
	private JLabel userLabel, conductLabel, solLabel, heuristicLabel, seedLabel, searchAlgLabel;
	private JTextField userTF, conductTF, seedTF, searchAlg;
	private JComboBox initSol, heuristic;
	private JCheckBox checkboxSeed;  
	private ButtonGroup G = new ButtonGroup();
	private JRadioButton hc, sa;
	private map plane2d;
	
	private int leftUpperCornerX = 30;
	private int leftUpperCornerY = 815;
	
	private Color F1 = new Color(128, 187, 193),F2 = new Color(249,250,237);
	
	public Interface() {

	}
	
	public void initGUI() {
		plane2d = new map(); 
		plane2d.setBounds(10, 10, 800, 800);
		this.add(plane2d);
		
		initExtras();
		init();
	}
	
	public void init(){
		setLayout(null);
		this.setBounds(0,0,820,1020);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);//Este centra las ventanas al abrirse
		this.setVisible(true);
		Container fondo = this.getContentPane();
		fondo.setBackground(F2);
		//-------------------------------------------
		this.revalidate();
		this.repaint();
	}
	
public void initExtras(){	
		Font f1 = new Font("Courier New", Font.BOLD, 25);
		Font f2 = new Font("Courier New", Font.BOLD, 20);
		Font f3 = new Font("Courier New", Font.BOLD, 45);
		
		userLabel = new JLabel();
		userLabel.setBounds(leftUpperCornerX, leftUpperCornerY, 150, 22);
		userLabel.setFont(f1);
		userLabel.setForeground(new Color(48, 107, 113));
		userLabel.setBackground(F1);
		this.add(userLabel);
		userLabel.setText("#Usuarios");
		//-------------------------------------------
		userTF = new JTextField();
		userTF.setBounds(leftUpperCornerX, leftUpperCornerY+30, 150, 30);
		userTF.setFont(f1);
		this.add(userTF);
		//-------------------------------------------
		conductLabel = new JLabel();
		conductLabel.setBounds(leftUpperCornerX, leftUpperCornerY+65, 200, 30);
		conductLabel.setFont(f1);
		conductLabel.setForeground(new Color(48, 107, 113));
		conductLabel.setBackground(F1);
		this.add(conductLabel);
		conductLabel.setText("#Conductores");
		//-------------------------------------------
		conductTF = new JTextField();
		conductTF.setBounds(leftUpperCornerX, leftUpperCornerY+95, 150, 30);
		conductTF.setFont(f1);
		this.add(conductTF);
		//-------------------------------------------
		G = new ButtonGroup( );
		
		hc = new JRadioButton("H. Climbing");
		hc.setBounds(leftUpperCornerX, leftUpperCornerY+140, 200, 25);
		hc.setFont(f1);
		hc.setForeground(new Color(48, 107, 113));
		hc.setBackground(F1);
		hc.setSelected(true);
		hc.addActionListener(this);
		this.add(hc);
		
		
		sa = new JRadioButton("Sim. Annealing");
		sa.setBounds(leftUpperCornerX+200, leftUpperCornerY+140, 250, 25);
		sa.setFont(f1);
		sa.setForeground(new Color(48, 107, 113));
		sa.setBackground(F1);
		sa.setSelected(false);
		sa.addActionListener(this);
		this.add(sa);
		
		G.add(hc);G.add(sa);
		
		//-------------------------------------------
		solLabel = new JLabel();
		solLabel.setBounds(leftUpperCornerX+270,leftUpperCornerY, 200, 30);
		solLabel.setFont(f1);
		solLabel.setForeground(new Color(48, 107, 113));
		solLabel.setBackground(F1);
		this.add(solLabel);
		solLabel.setText("Sol. Inicial");
		//-------------------------------------------
		initSol = new JComboBox();
		initSol.setBounds(leftUpperCornerX+270,leftUpperCornerY+30, 180, 35);
		initSol.setFont(f2);
		initSol.addItem("       1");initSol.addItem("       2");
		this.add(initSol);
		//-------------------------------------------
		heuristicLabel = new JLabel();
		heuristicLabel.setBounds(leftUpperCornerX+270,leftUpperCornerY+65, 190, 30);
		heuristicLabel.setFont(f1);
		heuristicLabel.setForeground(new Color(48, 107, 113));
		heuristicLabel.setBackground(F1);
		this.add(heuristicLabel);
		heuristicLabel.setText("Heuristico");
		//-------------------------------------------
		heuristic = new JComboBox();
		heuristic.setBounds(leftUpperCornerX+270,leftUpperCornerY+95, 180, 35);
		heuristic.setFont(f2);
		heuristic.addItem("       1");heuristic.addItem("       2");
		this.add(heuristic);
		//-------------------------------------------
		seedLabel = new JLabel();
		seedLabel.setBounds(leftUpperCornerX+500,leftUpperCornerY, 140, 30);
		seedLabel.setFont(f1);
		seedLabel.setForeground(new Color(48, 107, 113));
		seedLabel.setBackground(F1);
		this.add(seedLabel);
		seedLabel.setText("Seed Auto");
		//-------------------------------------------
		checkboxSeed = new JCheckBox("<-");
		checkboxSeed.setBounds(leftUpperCornerX+640,leftUpperCornerY, 30, 30);
		checkboxSeed.setFont(f2);
		checkboxSeed.addActionListener(this);
		this.add(checkboxSeed);
		//-------------------------------------------
		seedTF = new JTextField();
		seedTF.setBounds(leftUpperCornerX+670, leftUpperCornerY, 80, 30);
		seedTF.setFont(f1);
		this.add(seedTF);
		//-------------------------------------------
		go = new JButton("INICIAR");
		go.setBounds(leftUpperCornerX+500,leftUpperCornerY+40,250,130);
		go.setFont(f3);
		go.setBorderPainted(true);
		go.addActionListener(this);
		go.setVisible(true);
		this.add(go);
		

	


	}

	public void initSearch(int users, int cond, int seed, int solSel, int heuSel, int alg) {
		long startTime = System.nanoTime();
		CarSharingBoard board = new CarSharingBoard(users, cond, seed); //parámetros para llamar a la constructora del board
        board.solucionInicial(solSel);
        
        this.plane2d.setBoard(board);
        
//        System.out.println("INITIAL BOARD #####");
//        board.printInitialSolution(board.getConductoresS1(), 1);
        
        Search search=null; 
        Problem p;
           if (heuSel == 1)p = new Problem(board, new CarSharingSuccessorFunction(), new CarSharingGoalTest(), new CarSharingHeuristicFunction1());
           else p = new Problem(board, new CarSharingSuccessorFunction(), new CarSharingGoalTest(), new CarSharingHeuristicFunction2());
     
        if (alg==1) search =  new HillClimbingSearch();
        if (alg==2) search =  new SimulatedAnnealingSearch(2000,100,5,0.001); //parámetros simulated annealing segun digan experimentos
        // Instantiate the SearchAgent object
        SearchAgent agent;
		try {
			agent = new SearchAgent(p, search);
			System.out.println("FINAL BOARD #####");
	    	CarSharingBoard pf= (CarSharingBoard) search.getGoalState();
	    	pf.printInitialSolution(pf.getConductoresS1(), 1);
	    	
//	        printActions(agent.getActions(), board);
	        String resp = pf.alertSolution(pf.getConductoresS1());
	        resp += printInstrumentation(agent.getInstrumentation());
//	    	board.printInitialSolution(board.getConductoresS1(),1);	
	 
	    	this.plane2d.setBoard(pf);
	    	long estimatedTime = System.nanoTime() - startTime;
	        System.out.println("Execution Time [seg]: " + estimatedTime/1000000000);
	        System.out.println("######################################");
	    	JOptionPane.showMessageDialog(null, resp);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	}
	
    private static String printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        String S = "";
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
//            System.out.println(key + " : " + property);
            S += (key + " : " + property + "\n");
        }
        return S;
    }
    
//    private static void printActions(List<Object> actions, CarSharingBoard board) {
//    	System.out.println( actions.get(0));
//           for (Object action1 : actions) {
//               String action = (String) action1;
//               System.out.println(action);
//               applyOperator(action, board);    
//           }
//    }
	
    
//    public static void applyOperator(String action, CarSharingBoard board) {
//    	String[] actions = action.split("-");
//    	String operator = actions[0];
//    	int i = Integer.parseInt(actions[1]);
//    	
//    	System.out.println(action);
//    	if(operator.equals("Intercambiar")) {
//    		int j = Integer.parseInt(actions[3]);
//    		board.swapUsuarios(i, j);
//    	}else if(operator.equals("Mover")) {
//    		int j = Integer.parseInt(actions[3]);
//    		board.moverUsuario(board.getUsuarios().get(i), i, board.getConductoresS1().get(j));
//    	}else if(operator.equals("Subir prioridad recoger"))
//    		board.subirPrioridadRecoger(board.getUsuarios().get(i), i);
//    	else if(operator.equals("Subir prioridad dejar"))
//    		board.subirPrioridadDejar(board.getUsuarios().get(i), i);
//    	else 
//    		System.out.println("Operador no identificado");
//    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == go) {
			int users, cond, seed, searchAlg;
			int solSel = Integer.parseInt(((String)initSol.getSelectedItem()).trim()), heuSel = Integer.parseInt(((String)heuristic.getSelectedItem()).trim());
			String userAux = (String)userTF.getText(), conductAux = (String)conductTF.getText();
			
			
			if(!userAux.isEmpty()) users = Integer.parseInt(userTF.getText());
			else users = 200;
			
			if(!conductAux.isEmpty()) cond = Integer.parseInt(conductTF.getText());
			else cond = 100;
			
			if(!checkboxSeed.isSelected()) {
				String seedAux = seedTF.getText();
				if(!seedAux.isEmpty())seed = Integer.parseInt(seedAux);
				else seed = 1234;
			}else seed = 1234;
			
			if(hc.isSelected()) searchAlg = 1;
			else searchAlg = 2;
			
			System.out.println(users + " " +cond + " "+seed);
			System.out.println(solSel + " " +heuSel+ " "+searchAlg);
							
			initSearch(users, cond, seed, solSel, heuSel, searchAlg);
			
			
		}else if(e.getSource() == checkboxSeed) {
			if(checkboxSeed.isSelected()) {
				seedTF.setEnabled(false);
				seedTF.setBackground(new Color(180, 180, 180));
			}
			else {
				seedTF.setEnabled(true);
				seedTF.setBackground(new Color(255, 255, 255));
			}
		}else if(e.getSource() == hc) {
			hc.setSelected(true);
			sa.setSelected(false);
		}else if(e.getSource() == sa) {
			sa.setSelected(true);
			hc.setSelected(false);
		}

	}	
}


