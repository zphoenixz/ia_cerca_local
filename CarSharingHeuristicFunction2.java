import aima.search.framework.HeuristicFunction;

public class CarSharingHeuristicFunction2 implements HeuristicFunction{
    public boolean equals(Object obj) {
	    boolean retValue;
	    
	    retValue = super.equals(obj);
	    return retValue;
	}

    public double getHeuristicValue(Object state) {
		 CarSharingBoard board=(CarSharingBoard)state;
		 double ponderacion = 100;
		 double distancia = board.getDistanciaRecorrida();
		 int num_cond = board.getCondActivos(board.getConductoresS1());
		 double heur = distancia + (num_cond*ponderacion);
//		 double heur = (30.0 - distancia/(double)num_cond)*1000;
//		 double heur = num_cond * -30.0;
//		 double heur = (num_cond * 30.0 - distancia)*100;
//		 double heur = num_cond;
		 
		 //System.out.println("heuristico: " + heur);
		 return heur;
	}
}