import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarSharingSuccessorFunction implements SuccessorFunction{

	@Override
    public List getSuccessors(Object aState) {
        ArrayList retVal= new ArrayList();
        CarSharingBoard board=(CarSharingBoard) aState;
        
//        System.out.println(")))))))))))))))))) ITERACION ((((((((((((((((((((((((");
//        board.printInitialSolution(board.getConductoresS1(),0);
//        System.out.println(")))))))))))))))))) ========= ((((((((((((((((((((((((");
        
        for(int i=0;i<board.usersSize(); i++){ //for que recorre todos los pasajeros
    		
            //creamos una copia del board para cada operador que tengamos y se lo aplicamos a todos los usuarios
            
            for (int j = 0; j < board.usersSize(); j++){
            	boolean isActive1 = false;
            	boolean isActive2 = false;
            	for (int k = 0; k < board.conductoresSize(); ++k){ //for que recorre todos los conductores 	
                	if(j == board.getConductoresS1().get(k).getConductorId()) {
                		isActive1 = board.getConductoresS1().get(k).isActive();
                	}
                	if(i == board.getConductoresS1().get(k).getConductorId()) {
                		isActive2 = board.getConductoresS1().get(k).isActive();
                	}
            	}
            	
            	
                if(!isActive1 && !isActive2 && i!=j) {//hay muchos inactivos q no se 
                	CarSharingBoard board1 = new CarSharingBoard(board.getUsuarios(), board.getConductoresS1());	//tomaran en cuenta
                	Boolean b1 = board1.swapUsuarios(i, j);
                    if (b1){ //se sigue cumpliendo lo de la distancia minima de cada conductor
                    	String S= "Intercambiar-" + i + "-con-" + j;
                        retVal.add(new Successor(S,board1));
                    }
                }
            	
            }

            for (int j = 0; j < board.conductoresSize(); ++j){ //for que recorre todos los conductores 	
            	if(i != board.getConductoresS1().get(j).getConductorId() && board.getConductoresS1().get(j).isActive()) {
            		CarSharingBoard board2 = new CarSharingBoard(board.getUsuarios(), board.getConductoresS1());
	                Boolean b2 = board2.moverUsuario(board2.getUsuarios().get(i), i, board2.getConductoresS1().get(j));
	               if (b2){
	                 String S= "Mover-" + i + "-al conductor-" + j;
	                 retVal.add(new Successor(S,board2));  
//	                 System.out.println("---> " + board2.getDistConductor(j));
	               }
            	}
            }

            CarSharingBoard board3 = new CarSharingBoard(board.getUsuarios(), board.getConductoresS1());	    
            Boolean b3 = board3.subirPrioridadRecoger(board3.getUsuarios().get(i), i); //intentamos subimos la prioridad de todos los usuarioss 
            if (b3){ //se sigue cumpliendo lo de la distancia minima de cada conductor
                String S= "Subir prioridad recoger-" + i;
                retVal.add(new Successor(S,board3));
            }
        
            CarSharingBoard board4 = new CarSharingBoard(board.getUsuarios(), board.getConductoresS1());
            Boolean b4 = board4.subirPrioridadDejar(board4.getUsuarios().get(i), i); //intentamos subimos la prioridad de todos los usuarioss
            if (b4){
                String S2= "Subir prioridad dejar-" + i;
                retVal.add(new Successor(S2,board4));
            }    
        }
        return retVal;
	}
    
    
}