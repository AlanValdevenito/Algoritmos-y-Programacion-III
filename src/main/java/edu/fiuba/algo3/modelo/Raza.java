package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Jugador.Jugador;

public abstract class Raza implements Atacable, Revelable{
	
	protected Tiempo tiempo;
	protected Ubicacion ubicacion;
	protected Jugador jugador;
	
	public Raza(Tiempo unTiempo, Ubicacion unaUbicacion) {
		this.tiempo = unTiempo;
		this.ubicacion = unaUbicacion;
	}

	public int tiempoRestante() {
		return (this.tiempo.restante());
	}

	public abstract int obtenerPoblacion();
	
	public abstract void avanzarTurno();
    
    public void avanzarTurno(int cantidad) {
    	if(cantidad > 0) {
    		for(int i=0; i< cantidad; i++) {
    			this.avanzarTurno();
    		}
    	}
    }

    @Override
   	public Ubicacion ubicacion() {
    	return (this.ubicacion);
    }

	public boolean estaEn(Ubicacion unaUbicacion) {
		//System.out.println("Ubicacion buscada x: " + unaUbicacion.obtenerX());
		//System.out.println("Ubicacion buscada y: " + unaUbicacion.obtenerY());
		//System.out.println("Ubicacion unidad x: " + this.ubicacion().obtenerX());
		//System.out.println("Ubicacion unidad y: " + this.ubicacion().obtenerY());

		return (this.ubicacion().esIgualA(unaUbicacion));
	}
}
