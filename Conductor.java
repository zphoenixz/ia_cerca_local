import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;

import java.util.*;
import java.lang.*;

public class Conductor {
    private Usuario conductor;
    private ArrayList<Usuario> pasajeros = new ArrayList<Usuario>();
    private int numpasajeros;
    
    private int distRestante = 30; // Kilometros en 1 hora
    private ArrayList<Usuario> pasajerosTransportados = new ArrayList<Usuario>(); 
    
    public Conductor(Usuario conductor) {
        if (conductor.isConductor()){
            this.conductor = conductor;
            numpasajeros = 0;
        }        
        else System.out.println("El usuario pasado como conductor no es conductor"); 
    }
    
    public Conductor(Usuario conductor, Usuario first) {
        if (conductor.isConductor()){
            this.conductor = conductor;
            this.pasajeros.add(0, first);
            numpasajeros = 1;
        }        
        else System.out.println("El usuario pasado como conductor no es conductor"); 
    }

    public Conductor(Usuario conductor, Usuario first, Usuario second) {
        if (conductor.isConductor()){
            this.conductor = conductor;
            this.pasajeros.add(0, first);
            this.pasajeros.add(1, second);
            numpasajeros = 2;
        }        
        else System.out.println("El usuario pasado como conductor no es conductor"); 
    }
    
    public void setConductor(Usuario c){
        if (c.isConductor()) this.conductor = c;
        else System.out.println("El usuario pasado como conductor no es conductor");
    }
    
    public void setFirst(Usuario f) {
        this.pasajeros.add(0, f);
    }
    
    public void setSecond (Usuario s) {
        this.pasajeros.add(1, s);
    }
    
    
    public Usuario getConductor(){
        return this.conductor;
    }
    
    public Usuario getFirst() {
        return this.pasajeros.get(0);
    }
    
    public Usuario getSecond() {
        return this.pasajeros.get(1);
    }
     
    public ArrayList<Usuario> getPasajerosTransportados() {
		return pasajerosTransportados;
	}


	public void eliminarPasajero1(){ //elimina el pasajero1 y mueve el 
		if(numpasajeros == 2) {
			Usuario aux1 = this.getSecond();
	        this.setFirst(aux1);
		}
        this.pasajeros.remove(0);
        numpasajeros--;
        
    }
    public void eliminarPasajero2(){
    	if(numpasajeros == 2) {
    		this.pasajeros.remove(1);
            numpasajeros--;
    	}
        
    }
    
    public void eliminarPasajero(Usuario u){ //elimina el pasajero1 y mueve el 
        if (u == this.getFirst())	this.eliminarPasajero1();  
        else if (u == this.getSecond())	this.eliminarPasajero2();
    }
    
    public void dejarPasajero(Usuario u) {
    	if (u == this.getFirst())	this.eliminarPasajero1();
    	else if (u == this.getSecond())	this.eliminarPasajero2();
          
    	pasajerosTransportados.add(u);
    }
    
    public void addPasajero(Usuario u){
        this.pasajeros.add(u);
        numpasajeros++;
    }
    public int size(){
        return numpasajeros;
    }
}
