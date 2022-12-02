package edu.fiuba.algo3.modelo.Unidades.UnidadesProtoss;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.Atacable;
import edu.fiuba.algo3.modelo.Atacante;
import edu.fiuba.algo3.modelo.Ataque;
import edu.fiuba.algo3.modelo.Escudo;
import edu.fiuba.algo3.modelo.Jugador.Jugador;
import edu.fiuba.algo3.modelo.Jugador.JugadorProtoss;
import edu.fiuba.algo3.modelo.Superficie;
import edu.fiuba.algo3.modelo.Ubicacion;
import edu.fiuba.algo3.modelo.Unidades.TipoDeUnidad;
import edu.fiuba.algo3.modelo.Unidades.Unidad;
import edu.fiuba.algo3.modelo.Vida;
import edu.fiuba.algo3.modelo.Excepciones.AtacableFueraDeRangoError;

public class Scout implements TipoDeUnidad, Atacante, Atacable {

	public static final int SUMINISTRO_SCOUT = 4;
	private final int POBLACION = 0;
	private final int COSTO_MINERAL = 300;
	private final int COSTO_GAS = 150;
	public static final int CONSTRUCCION_SCOUT = -9;

	private Vida vida;
	private Escudo escudo;
	private Jugador jugador;
	private Unidad unidad;
	private Ubicacion ubicacion;
	private Superficie superficie;
	private ArrayList<Ataque> ataques;
	
	public Scout(Ubicacion unaUbicacion, Jugador unJugador) {
		unJugador.utilizar(COSTO_GAS, COSTO_MINERAL);

		this.vida = new Vida(150);
		this.escudo = new Escudo(100);
		this.jugador = unJugador;
		this.unidad = null;
		this.ubicacion = unaUbicacion;
		this.superficie = new Superficie("Aire");
		this.ataques = new ArrayList<Ataque>() {{add(new Ataque(8,new Superficie("Tierra"),4));
		 										 add(new Ataque(14,new Superficie("Aire"),4));}};
	}
	
	public Scout(Jugador unJugador) {
		unJugador.utilizar(COSTO_GAS, COSTO_MINERAL);

		this.vida = new Vida(150);
		this.escudo = new Escudo(100);
		this.jugador = unJugador;
		this.unidad = null;
		this.ubicacion = new Ubicacion();
		this.superficie = new Superficie("Aire");
		this.ataques = new ArrayList<Ataque>() {{add(new Ataque(8,new Superficie("Tierra"),4));
		 										 add(new Ataque(14,new Superficie("Aire"),4));}};
	}

	public void setComportamientoUnidad(Unidad unaUnidad) {
		this.unidad = unaUnidad;
	}

	@Override
	public int obtenerPoblacion() {
		return POBLACION;
	}

	@Override
	public int obtenerSuministro() {
		return SUMINISTRO_SCOUT;
	}

	@Override
	public void recibirAtaque(int unDanio) {
		if(unDanio > this.escudo.restante()) {
			int danioRestante = this.escudo.restante() - unDanio;
			this.vida.recibirDanioPor(danioRestante, this.unidad, this.jugador);
		}
		this.escudo.recibirDanioPor(unDanio);
	}

	@Override
	public Superficie obtenerSuperficie() {
		return this.superficie;
	}

	@Override
	public void atacar(Atacable unAtacable) {

		for (Ataque ataque : ataques) {
			if(! (this.estaEnRangoDeAtaque(unAtacable, ataque))) {
				throw new AtacableFueraDeRangoError();
			}

			ataque.atacarA(unAtacable);
		}
	}

	@Override
	public void recuperarse() {
		this.escudo.recuperarse();
	}

	public boolean estaEnRangoDeAtaque(Atacable unAtacable, Ataque unAtaque) {
		return (this.ubicacion.distanciaCon(unAtacable.ubicacion()) <= unAtaque.rango());
	}
	
	@Override
	public Ubicacion ubicacion() {
		return (this.ubicacion);
	}
	
	public int vidaRestante() {
		return (this.vida.restante());
	}

	public int escudoRestante() {
		return (this.escudo.restante());
	}

	@Override
	public boolean compararSuperficie(String unTipoDeSuperficie) {
		return this.superficie.compararTipos(unTipoDeSuperficie);
	}

	@Override
	public void evolucionarAGuardian(Unidad unaUnidad) {
		// No hace nada ya que es un mensaje particular que entiende solo Mutalisco.
	}

	@Override
	public void evolucionarADevorador(Unidad unaUnidad) {
		// No hace nada ya que es un mensaje particular que entiende solo Mutalisco.
	}
}
