import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;

import java.util.*;

import static java.lang.Math.abs;

public final class CarSharingBoard {
    private final Usuarios users;

    private List<UserExtended> conductoresS1 = new ArrayList<UserExtended>();//Sol1

    
    private static int numusuarios;
    private static int numconductores;
    private static int seed;
    private static int sol_inicial;
    //private Random r;
    
    
    public CarSharingBoard(int nusuarios, int ncond, int seed){ //constructora
        //this.r = new Random();
        CarSharingBoard.seed = seed;
        users = new Usuarios(nusuarios, ncond, seed);
//        
//        System.out.println("------");
//        for(int i = 0; i < users.size(); i++ ) {
//        	System.out.println("Person #" + i + ":: Origen -> X: "+users.get(i).getCoordOrigenX() +" - Y: "+users.get(i).getCoordOrigenY() +
//        											" -- Destino -> X: "+users.get(i).getCoordDestinoX()+" - Y: "+users.get(i).getCoordDestinoY() +
//        											"Es conductor? " + users.get(i).isConductor());
//        }
//        System.out.println("------");
//        
        numusuarios = nusuarios; 
        numconductores = ncond;
        
        
    }
    
    public CarSharingBoard(Usuarios users, List<UserExtended> conductoresS1){        
		List<UserExtended> copyList = new ArrayList<>();
		
		for (UserExtended cop : conductoresS1){
		   copyList.add(new UserExtended(cop));
		}
        
        this.users = users;
        this.numusuarios = users.size();
        this.numconductores = conductoresS1.size();
        this.setConductoresS1(copyList);
        
    }
    
    public void solucionInicial(int tipo) {
        switch(tipo) {
            case 1: //solución inicial 0
            	createSolution1();
                break;
            case 2: //solución inicial 1
            	createSolution2();
                break;
        }
    }
    
    //operadores
    public Boolean moverUsuario(Usuario user, int userId, UserExtended conductornew){ //ahora recibe UserExtended en vez de conductor
    	int conductornewId = conductornew.getConductorId();
    	int posConductorNew = -1;
    	int posUserIfConductor = -1;
    	
    	boolean userIsConductor = user.isConductor();
    	boolean userIsActiveConductor = false, conductornewIsActiveConductor = false;
    	
    	if(userId == conductornew.getConductorId()) return false;
    	
    	for(int i = 0; i < conductoresS1.size(); i++) {
    		if(conductoresS1.get(i).getConductorId() == conductornewId) {
    			conductornewIsActiveConductor = conductoresS1.get(i).isActive();
    			posConductorNew = i;
    		}
    		if(conductoresS1.get(i).getConductorId() == userId) {
    			userIsActiveConductor = conductoresS1.get(i).isActive();
    			posUserIfConductor = i;
    		}
    	}
    	if((!userIsConductor || !userIsActiveConductor) && conductornewIsActiveConductor) {
	    	for(int i = 0; i < conductoresS1.size(); i++) {
	    		boolean found = conductoresS1.get(i).foundPasajero(userId);
	    		int conductorId = conductoresS1.get(i).getConductorId();
	    		if(found) {
	    			//System.out.println(""+conductorId +" "+ conductornewId);
	    			if(conductorId != conductornewId)	{
	    				conductoresS1.get(i).removePasajero(userId);//lo saco de ese auto	
	    				conductoresS1.get(i).removePasajero(userId);//lo saco de ese auto	
	    			}
	    		}
	    	}
	    	for(int i = 0; i < conductoresS1.size(); i++) {
	    		boolean found = conductoresS1.get(i).foundPasajero(userId);
	    		int conductorId = conductoresS1.get(i).getConductorId();
	    		//lo agrego a su nuevo auto, con la prioridad mas baja (al final)
	    		if(!found) 
		    		if(conductorId == conductornewId)	
		    			conductoresS1.get(i).addPasajero(userId, -1);
	    	}
    	}else if(userId != conductornewId && userIsActiveConductor && conductornewIsActiveConductor){//No es el mismo conductor
    		
    		conductoresS1.get(posConductorNew).addPasajeros(userId, conductoresS1.get(posUserIfConductor).getSchedulePasajeros());
    		conductoresS1.get(posUserIfConductor).removeConductor();
    	}else return false;
    	
        double dist1 = getDistConductor(conductornewId);

        
        if (dist1 <= 30) {
//        	System.out.println("--> " + dist1);
        	return true;
        }
        else return false;
    }
    
    public Boolean swapUsuarios(int idPasajero1, int idPasajero2){
    	boolean found1 = false, found2 = false;
    	int posConductor1 = -1, posConductor2 = -1;
    	boolean user1IsActiveConductor = false;
    	boolean user2IsActiveConductor = false;
    	
    	if(idPasajero1 == idPasajero2) return false;
    	
    	for(int i = 0; i < conductoresS1.size(); i++) {
    		if(conductoresS1.get(i).getConductorId() == idPasajero1) {
    			user1IsActiveConductor = conductoresS1.get(i).isActive();
    		}
    		if(conductoresS1.get(i).getConductorId() == idPasajero2) {
    			user2IsActiveConductor = conductoresS1.get(i).isActive();
    		}
    	}
    	
    	if((!users.get(idPasajero1).isConductor() || !user1IsActiveConductor) && (!users.get(idPasajero2).isConductor()|| !user2IsActiveConductor)) {//Ninguno es pasajero
	    	for (int i = 0; i < conductoresS1.size(); ++i){//Iteramos en todos los conductores
	    		found1 = conductoresS1.get(i).foundPasajero(idPasajero1);
	    		found2 = conductoresS1.get(i).foundPasajero(idPasajero2);
	    		if(found1)	posConductor1 = i;
	    		if(found2) 	posConductor2 = i;
	    		if(found1 && found2) break;
	    	}
	    	if(posConductor1 != posConductor2) {
	    		conductoresS1.get(posConductor1).reemplazarPasajero(idPasajero1, idPasajero2);
		    	conductoresS1.get(posConductor2).reemplazarPasajero(idPasajero2, idPasajero1);
	    	}else {
	    		conductoresS1.get(posConductor1).intercambiarPasajero(idPasajero1, idPasajero2);
	    	}
    	}
        double dist1 = getDistConductor(posConductor1);
        double dist2 = getDistConductor(posConductor2);
        if (dist1 <= 30 && dist2 <= 30) return true;
        else return false;
    }
    
    public Boolean subirPrioridadRecoger(Usuario user, int idPrioridadRecoger){
        double dist = -1;
        int it = -1;
    	boolean userIsActiveConductor = false;
    	
    	for(int i = 0; i < conductoresS1.size(); i++) {
    		if(conductoresS1.get(i).getConductorId() == idPrioridadRecoger) {
    			userIsActiveConductor = conductoresS1.get(i).isActive();
    		}
    	}
    	if(!user.isConductor() || !userIsActiveConductor) {//Solo subimos prioridad de pasajeros, no de conductores, ellos son siempre  primeros y ultimos
	    	for (int i = 0; i < conductoresS1.size(); ++i){//Iteramos en todos los conductores
	        	int scheduleSize = conductoresS1.get(i).getSchedulePasajeros().size(); 	
	            for (int j = 0; j < scheduleSize; ++j){
	            	int id = conductoresS1.get(i).getID(j);
	                Boolean b = conductoresS1.get(i).getBoolean(j);
	                if(!b && idPrioridadRecoger == id)               
	                	if(conductoresS1.get(i).subirPrioridadRecoger(j)) it = i;
	            }
	    	}
    	}
        if (it == -1) return false;
        dist = getDistConductor(it);
        if (dist <= 30) return true;
        else return false;
    }
    
    public Boolean subirPrioridadDejar(Usuario user, int idPrioridadDejar){
        double dist = -1;
        int it = -1;
    	boolean userIsActiveConductor = false;
    	for(int i = 0; i < conductoresS1.size(); i++) {
    		if(conductoresS1.get(i).getConductorId() == idPrioridadDejar) {
    			userIsActiveConductor = conductoresS1.get(i).isActive();
    		}
    	}
    	if(!user.isConductor() || !userIsActiveConductor) {//Solo subimos prioridad de pasajeros, no de conductores, ellos son siempre  primeros y ultimos
	    	for (int i = 0; i < conductoresS1.size(); ++i){//Iteramos en todos los conductores
	        	int scheduleSize = conductoresS1.get(i).getSchedulePasajeros().size(); 	
	            for (int j = 0; j < scheduleSize; ++j){
	            	int id = conductoresS1.get(i).getID(j);
	                Boolean b = conductoresS1.get(i).getBoolean(j);
	                if(b && idPrioridadDejar == id) {
                                it = i;
	                	conductoresS1.get(i).subirPrioridadDejar(j);
	                }
	            }
	    	}
    	}
        if (it == -1) return false;
        dist = getDistConductor(it);
        if (dist <= 30) return true;
        else return false;
    }
    
    public boolean isGoalState() {
    	return(false);
    }
    
    public double getDistanciaRecorrida(){
        int dist = 0;
        //int distRecorridaConductor;
        for (int i = 0; i < conductoresS1.size(); ++i){
        	//distRecorridaConductor = 0;
                if(conductoresS1.get(i).isActive()) {
                	int scheduleSize = conductoresS1.get(i).getSchedulePasajeros().size();
                	if(scheduleSize != 0) {
	                    for (int j = 0; j < scheduleSize - 1; ++j){                
	                        int id1 = conductoresS1.get(i).getID(j);
	                        Boolean b1 = conductoresS1.get(i).getBoolean(j);
	                        int id2 = conductoresS1.get(i).getID(j+1);
	                        Boolean b2 = conductoresS1.get(i).getBoolean(j+1);
	                        int x1, y1;
	                        int x2, y2;
	                        if (!b1){ //es coordenada de origen
	                            x1 = users.get(id1).getCoordOrigenX();
	                            y1 = users.get(id1).getCoordOrigenY();
	                        }
	                        else{ //es coordenada destino
	                            x1 = users.get(id1).getCoordDestinoX();
	                            y1 = users.get(id1).getCoordDestinoY();
	                        }
	                        if (!b2){ //es coordenada de origen
	                            x2 = users.get(id2).getCoordOrigenX();
	                            y2 = users.get(id2).getCoordOrigenY();
	                        }
	                        else{ //es coordenada destino
	                            x2 = users.get(id2).getCoordDestinoX();
	                            y2 = users.get(id2).getCoordDestinoY();
	                        }
	                        if (j == 0){ //primera iteración, debemos sumar distancia origen del conductor a distancia origen primer pasajero
	                            int x3, y3;
	                            int id3 = conductoresS1.get(i).getConductorId();
	                            x3 = users.get(id3).getCoordOrigenX();
	                            y3 = users.get(id3).getCoordOrigenY();
	                            double distparcial2 = getDist(x3, y3, x1, y1);
	                            dist += distparcial2;
	                            //distRecorridaConductor += distparcial2;
	                        } 
	                        if(j == (scheduleSize - 2)) {//ultima iteración, debemos sumar distancia destino ultimo pasajero a destino conductor
	                                int x3, y3;
	                            int id3 = conductoresS1.get(i).getConductorId();
	                            x3 = users.get(id3).getCoordDestinoX();
	                            y3 = users.get(id3).getCoordDestinoY();
	                            double distparcial2 = getDist(x3, y3, x2, y2);
	                            dist += distparcial2;
	                          //distRecorridaConductor += distparcial2;
	                        }
	                        double distparcial = getDist(x1, y1, x2, y2);
	                        dist += distparcial;
	
	                        //distRecorridaConductor += distparcial;
	                    }
                	}else {
    	            	int x1, y1;
                        int id1 = conductoresS1.get(i).getConductorId();
                        x1 = users.get(id1).getCoordOrigenX();
                        y1 = users.get(id1).getCoordOrigenY();
                        
                        int x2, y2;
                        int id2 = conductoresS1.get(i).getConductorId();
                        x2 = users.get(id2).getCoordDestinoX();
                        y2 = users.get(id2).getCoordDestinoY();
                        double distparcial2 = getDist(x1, y1, x2, y2);
                        dist += distparcial2;
    	            }
                    
                }
            //if(distRecorridaConductor != 0) //Retornar la distancia del conductor de esa pos
            //else(distRecorridaConductor<30) //este conductor conduce mas del limite
            //else //este conducntor ya no conduce
        }
        return dist/10;
    }
    
    public double getDistConductor(int id_conductor){
        double dist = 0;
        for (int i = 0; i < conductoresS1.size(); ++i){
            if (conductoresS1.get(i).getConductorId() == id_conductor){
                int scheduleSize = conductoresS1.get(i).getSchedulePasajeros().size();
                if(scheduleSize != 0) {
                	
	                for (int j = 0; j < scheduleSize - 1; ++j){                
	                    int id1 = conductoresS1.get(i).getID(j);
	                    Boolean b1 = conductoresS1.get(i).getBoolean(j);
	                    int id2 = conductoresS1.get(i).getID(j+1);
	                    Boolean b2 = conductoresS1.get(i).getBoolean(j+1);
	                    int x1, y1;
	                    int x2, y2;
	                    if (!b1){ //es coordenada de origen
	                        x1 = users.get(id1).getCoordOrigenX();
	                        y1 = users.get(id1).getCoordOrigenY();
	                    }
	                    else{ //es coordenada destino
	                        x1 = users.get(id1).getCoordDestinoX();
	                        y1 = users.get(id1).getCoordDestinoY();
	                    }
	                    if (!b2){ //es coordenada de origen
	                        x2 = users.get(id2).getCoordOrigenX();
	                        y2 = users.get(id2).getCoordOrigenY();
	                    }
	                    else{ //es coordenada destino
	                        x2 = users.get(id2).getCoordDestinoX();
	                        y2 = users.get(id2).getCoordDestinoY();
	                    }
	                    if (j == 0){ //primera iteración, debemos sumar distancia origen del conductor a distancia origen primer pasajero
	                        int x3, y3;
	                        int id3 = conductoresS1.get(i).getConductorId();
	                        x3 = users.get(id3).getCoordOrigenX();
	                        y3 = users.get(id3).getCoordOrigenY();
	                        double distparcial2 = getDist(x3, y3, x1, y1);
	                        dist += distparcial2;
	                        //distRecorridaConductor += distparcial2;
	                    }
	                    if(j == (scheduleSize - 2)) {//ultima iteración, debemos sumar distancia destino ultimo pasajero a destino conductor
	                        int x3, y3;
	                        int id3 = conductoresS1.get(i).getConductorId();
	                        x3 = users.get(id3).getCoordDestinoX();
	                        y3 = users.get(id3).getCoordDestinoY();
	                        double distparcial2 = getDist(x3, y3, x2, y2);
	                        dist += distparcial2;
	                      //distRecorridaConductor += distparcial2;
	                    }
	                    double distparcial = getDist(x1, y1, x2, y2);
	                    dist += distparcial;
	                }
	            }else {
	            	int x1, y1;
                    int id1 = conductoresS1.get(i).getConductorId();
                    x1 = users.get(id1).getCoordOrigenX();
                    y1 = users.get(id1).getCoordOrigenY();
                    
                    int x2, y2;
                    int id2 = conductoresS1.get(i).getConductorId();
                    x2 = users.get(id2).getCoordDestinoX();
                    y2 = users.get(id2).getCoordDestinoY();

                    dist = getDist(x1, y1, x2, y2);
	            }
            }
        } 
        return dist/10;
    }
    
    
    
    public double getDist(int x1, int y1, int x2, int y2){
        double x = abs(x2-x1);
        double y = abs(y2-y1);
        double dist = x+y;
        return dist;
    }
    
    
    public void createSolution1() {
    	Queue<Integer> pasajerosEsperando = new LinkedList<>(); 
    	int j = 0;
    	
    	for(int i = 0;i < numusuarios; i++) { 
    		if(!users.get(i).isConductor()) pasajerosEsperando.add(i);
    		else {
    			conductoresS1.add((new UserExtended(i)));
    		}
    	}
    	
    	//Los conductores llevan pasajeros
    	while(!pasajerosEsperando.isEmpty()) {
    		int pasajeroId = pasajerosEsperando.remove();
    		conductoresS1.get(j).addPasajero(pasajeroId, 1);
    		j++; 		
    		if(j >= numconductores) j = 0;	
    	}
    }
	
    public void createSolution2() {
    	Queue<Integer> pasajerosEsperando = new LinkedList<>();   	
    	for(int i = 0;i < numusuarios; i++) { 
    		if(!users.get(i).isConductor()) pasajerosEsperando.add(i);
    		else {
    			conductoresS1.add((new UserExtended(i)));
    		}
    	}
    	
    	//Los conductores llevan pasajeros
    	int min = 0, max = conductoresS1.size();
    	Random r = new Random();
    	
    	while(!pasajerosEsperando.isEmpty()) {
    		int pasajeroId = pasajerosEsperando.remove();
    		int j = r.nextInt(max - min) + min;
    		conductoresS1.get(j).addPasajero(pasajeroId, 1);
    	}
    }
    
//    public void printSolution(List<UserExtended> Solucion) {
//    	int i = 0;
//        System.out.println("Solucion despues de ejecucion:");
//        System.out.println("Número de conductores: #" + conductoresSize());
//        System.out.println("Distancia recorrida por los conductores #" + getDistanciaRecorrida());
//    	/*for(;i < numconductores;i++) {
//    		if(Solucion.get(i).isActive()) {
//	    		System.out.println("Conductor #" + Solucion.get(i).getConductorId()); 
//                System.out.println("Distancia recorrida por el conductor " + Solucion.get(i).getConductorId() + " : #" + getDistConductor(Solucion.get(i).getConductorId()));
//	    		System.out.println( "Pasajeros # ");
//	    		Solucion.get(i).printPasajeros();
//    		}
//    	}*/
//    }
    
    public void printInitialSolution(List<UserExtended> Solucion, int op) {
    	int i = 0;
        System.out.println("Solucion inicial:");
        int conductoresActivos = getCondActivos(Solucion);
        
        System.out.println("Número de conductores activos: #" + conductoresActivos);
        System.out.println("Distancia recorrida por los conductores #" + getDistanciaRecorrida());
        if(op == 1)
	    	for(;i < numconductores;i++)
	    		if(Solucion.get(i).isActive()) {        
		    		System.out.println("Conductor #" + Solucion.get(i).getConductorId()); 
		    		System.out.println("Distancia recorrida por el conductor " + Solucion.get(i).getConductorId() + " : #" + getDistConductor(Solucion.get(i).getConductorId()));
		    		System.out.println( "Pasajeros # ");
		    		Solucion.get(i).printPasajeros();
	    		}
    }
    
    public String alertSolution(List<UserExtended> Solucion) {
    	int i = 0;
    	String S = "Conductores Activos: "+getCondActivos(Solucion) + "\n";
        S += "Distancia recorrida por los conductores; " + getDistanciaRecorrida() + " Km.\n";
        
        return S;
    }
    
    public int usersSize(){
        return numusuarios;
    }
    
    public int getNumConductores(){
        return numconductores;
    }
    
    public int conductoresSize(){
        return conductoresS1.size();
    }
    
    public int getSeed(){
        return seed;
    }
    
    public int getSol(){
        return sol_inicial;
    }
    
    public int getCondActivos(List<UserExtended> Solucion){
        int conductoresActivos = 0;
        for(int j = 0;j < conductoresSize();j++) {
        	if(Solucion.get(j).isActive())
        		conductoresActivos++;
        }
        return conductoresActivos;
    }
    
    public Usuarios getUsuarios(){
        return users;
    }
    
    public List<UserExtended> getConductoresS1(){
        return conductoresS1;
    }
    
	public void setConductoresS1(List<UserExtended> conductoresS1) {
		this.conductoresS1 = conductoresS1;
	}

	public CarSharingBoard getBoard(){
        return this;
    }
}