import java.util.*;

public class UserExtended implements Cloneable{
	private int conductorId;
	private List<int[]> schedulePasajeros = new ArrayList<int[]>();
	
	private boolean isActive;
	
	public UserExtended(int conductorId) {
        this.conductorId = conductorId;
        this.isActive = true;
    }
	
	public UserExtended(int conductorId, boolean isActive, List<int[]> schedulePasajeros) {
        this.conductorId = conductorId;
        this.isActive = isActive;
        this.schedulePasajeros = schedulePasajeros;
    }


	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getConductorId() {
		return conductorId;
	}
        
    public int getID(int i){
        return this.schedulePasajeros.get(i)[0];
    }
    
    public Boolean getBoolean(int i){
        return this.schedulePasajeros.get(i)[1] != 0;
    }

	public void setConductorId(int conductorId) {
		this.conductorId = conductorId;
	}

	public List<int[]> getSchedulePasajeros() {
		return this.schedulePasajeros;
	}

	public void setSchedulePasajeros(List<int[]> ordenPasajeros) {
		this.schedulePasajeros = ordenPasajeros;
	}


	public void addPasajero(int pasajeroRD, int pos) {
//		miPair Recoger = new miPair(pasajeroRD, false);
//		miPair Dejar = new miPair(pasajeroRD, true);
		
		int[] miPairRecoger = new int[]{pasajeroRD,0};
		int miPairDejar[] = new int[]{pasajeroRD,1};
		
		
		if(schedulePasajeros.isEmpty()) {
			this.schedulePasajeros.add(miPairRecoger);
			this.schedulePasajeros.add(miPairDejar);
		}else if(pos == -1){//added last
			this.schedulePasajeros.add(miPairRecoger);
			this.schedulePasajeros.add(miPairDejar);
		}else{//added at the position
			this.schedulePasajeros.add(pos,miPairRecoger);
			this.schedulePasajeros.add(pos+1,miPairDejar);
		}
	}
	
	public void addPasajeros(int conductor2user, List<int[]> schedulePasajerosNew) {
		int[] miPairRecoger = new int[]{conductor2user,0};
		int miPairDejar[] = new int[]{conductor2user,1};
		
		this.schedulePasajeros.add(miPairRecoger);
		this.schedulePasajeros.add(miPairDejar);
		this.schedulePasajeros.addAll(schedulePasajerosNew);
	}
	
	public void removePasajero(int pasajeroID) {
		for(int i = 0  ; i < this.schedulePasajeros.size() ; i++) {
			int id = this.schedulePasajeros.get(i)[0];
			if( id == pasajeroID) {
				this.schedulePasajeros.remove(i);
				break;
			}
		}
	}
	
	public void removeConductor() {
		this.schedulePasajeros.clear();
		setActive(false);
	}
	
	public boolean subirPrioridadRecoger(int posPrioridadRecoger) {
		List<int[]> auxSchedule = new ArrayList<int[]>();
		auxSchedule = this.getSchedulePasajeros();
		
		if(posPrioridadRecoger > 0 && posPrioridadRecoger < auxSchedule.size()) {//Si esta en 0 ya es el 1ero a recogerse
			int idSubePrioridad = auxSchedule.get(posPrioridadRecoger)[0];
			int boolSubePrioridad = auxSchedule.get(posPrioridadRecoger)[1];
			
			int idBajaPrioridad = auxSchedule.get(posPrioridadRecoger-1)[0];
			int boolbajaPrioridad = auxSchedule.get(posPrioridadRecoger-1)[1];
			
			auxSchedule.set(posPrioridadRecoger-1, new int[]{idSubePrioridad, boolSubePrioridad});
			auxSchedule.set(posPrioridadRecoger, new int[]{idBajaPrioridad, boolbajaPrioridad});
			
			boolean seatsAvailable = this.checkSeatsAvailable(auxSchedule);
			if(seatsAvailable) {
				this.setSchedulePasajeros(auxSchedule);
				return true;
			}
		}
		return false;
	}
	public boolean subirPrioridadDejar(int posPrioridadDejar) {
		if(posPrioridadDejar > 0 && posPrioridadDejar < this.schedulePasajeros.size()) {
			
			int idSubePrioridad = this.getSchedulePasajeros().get(posPrioridadDejar)[0];
			int boolSubePrioridad = this.getSchedulePasajeros().get(posPrioridadDejar)[1];
			
			int idBajaPrioridad = this.getSchedulePasajeros().get(posPrioridadDejar-1)[0];
			int boolbajaPrioridad = this.getSchedulePasajeros().get(posPrioridadDejar-1)[1];
			
			if(idSubePrioridad != idBajaPrioridad) {//No puede dejarlo antes de recogerlo
				this.getSchedulePasajeros().set(posPrioridadDejar-1, new int[]{idSubePrioridad, boolSubePrioridad});
				this.getSchedulePasajeros().set(posPrioridadDejar, new int[]{idBajaPrioridad, boolbajaPrioridad});
				return true;
			}
			
		}
		return false;
	}
	public void reemplazarPasajero(int oldId, int newId) {
		for(int i = 0; i < this.schedulePasajeros.size();i++) {
			int id = this.schedulePasajeros.get(i)[0];
			if(oldId == id)	this.schedulePasajeros.get(i)[0] = newId;
		}
	}
	public void intercambiarPasajero(int userId1, int userId2) {
		for(int i = 0; i < this.schedulePasajeros.size();i++) {
			int id = this.schedulePasajeros.get(i)[0];
			if(userId1 == id)	this.schedulePasajeros.get(i)[0] = userId2;
			if(userId2 == id)	this.schedulePasajeros.get(i)[0] = userId1;
		}
	}
	public boolean foundPasajero(int buscarId) {
		boolean found = false;
		for(int i = 0; i < this.schedulePasajeros.size();i++) {
			int id = this.schedulePasajeros.get(i)[0];
			if(buscarId == id) {
				found = true;
				break;
			}
		}
		return found;
	}
	public boolean checkSeatsAvailable(List<int[]> Pasajeros ) {
		int asientosOcupados = 0;
		for(int i = 0; i <Pasajeros.size(); i++) {
			boolean estado = Pasajeros.get(i)[1] != 0;
			
			if(!estado) asientosOcupados ++;
			else asientosOcupados --;
			if(asientosOcupados>2) return false;
		}
		return true;
	}
	
	public void printPasajeros() {
		for(int i = 0; i < schedulePasajeros.size();i++) {
			System.out.print("-"+schedulePasajeros.get(i)[0]);	
		}
		System.out.println();
	}
	
	
	UserExtended(UserExtended original){
		this.conductorId = original.getConductorId();
		this.isActive = original.isActive();
		this.schedulePasajeros = new ArrayList<int[]>();
	
       for (int i=0; i<original.getSchedulePasajeros().size(); i++){
           this.schedulePasajeros.add(new int [] {original.getSchedulePasajeros().get(i)[0],original.getSchedulePasajeros().get(i)[1]});
       }
    }
    
}
