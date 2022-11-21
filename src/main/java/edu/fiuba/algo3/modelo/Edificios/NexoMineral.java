package edu.fiuba.algo3.modelo.Edificios;

import edu.fiuba.algo3.modelo.*;

public class NexoMineral extends EdificioProtoss implements Minero {

	private final int COSTO_MINERAL = 50;
	private final int COSTO_GAS = 0;
	
	private int cantidadRecolectable;
    private int cantidadRecolectada;
    private NodoMineral nodo;

	
    public NexoMineral(NodoMineral unNodo, Recursos recursosJugador) {
        super(new Tiempo(-4),new Vida(250), new Escudo(250));
        
        recursosJugador.utilizar(COSTO_GAS, COSTO_MINERAL);
        
        this.cantidadRecolectable = 10;
        this.cantidadRecolectada = 0;
        this.nodo = unNodo;
    }
    
    @Override
    public void ejecutaOperable() {
    	this.cantidadRecolectada += this.recolectarMineralDe(this.nodo);
    }
    
	@Override
	public int recolectarMineralDe(Mineral unNodoMineral) {
		return (unNodoMineral.recolectarMineral(this.cantidadRecolectable));
	}
    
	@Override
    public int obtenerMineral() {
		int recolectado = this.cantidadRecolectada;
        this.cantidadRecolectada = 0;
        return recolectado;
    }

	@Override
	public boolean tieneMinero() {
		// TODO Auto-generated method stub
		return true;
	}
}