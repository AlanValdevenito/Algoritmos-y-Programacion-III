package edu.fiuba.algo3.modelo;

public class Ataque {
	
	private int danio;
	private Superficie superficie;
	private int rango;
	
	public Ataque(int unDanio, Superficie unaSuperficie, int unRango) {
		this.danio = unDanio;
		this.superficie = unaSuperficie;
		this.rango = unRango;
	}
	
	public int rango() {
		return (this.rango);
	}
	
	public int danio() {
		return (this.danio);
	}
	
	public void atacarA(Atacable unAtacable) {

        // En caso de que el ataque y la unidad atacada no sean compatibles, devuelve false y entonces no se hace nada.
        // Este resultado se ve reflejado en el escudo o vida de la unidad atacada ya que permanecera igual.

        if(this.superficie.esIgualA(unAtacable.obtenerSuperficie())) {
            unAtacable.recibirAtaque(this.danio);
        }
    }
	
	public Superficie obtenerSuperficie() {
        return this.superficie;
    }

}