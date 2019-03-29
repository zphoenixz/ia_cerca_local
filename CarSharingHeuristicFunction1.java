import aima.search.framework.HeuristicFunction;

public class CarSharingHeuristicFunction1 implements HeuristicFunction{
	
    public boolean equals(Object obj) {
	    boolean retValue;
	    
	    retValue = super.equals(obj);
	    return retValue;
	}
//	funcion heurística que minimiza la distancia recorrida por los conductores
    
	public double getHeuristicValue(Object state) {
	 CarSharingBoard board=(CarSharingBoard)state;
	 
	 double heur = 0;
	 double distancia = board.getDistanciaRecorrida();
	 int ncond = board.getNumConductores();
	 
//	 double max_distance = -ncond*300;
	 
	 heur = distancia;
//	 System.out.println(heur+" -------------");
//	 board.printSolution(board.getConductoresS1());
	 
	 return heur;
	}
}