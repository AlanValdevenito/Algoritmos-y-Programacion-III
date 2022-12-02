package edu.fiuba.algo3.modelo.Unidades.UnidadesZerg;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.Excepciones.AtacableFueraDeRangoError;
import edu.fiuba.algo3.modelo.Excepciones.RevelableFueraDeRangoError;
import edu.fiuba.algo3.modelo.Jugador.Jugador;
import edu.fiuba.algo3.modelo.Jugador.JugadorZerg;
import edu.fiuba.algo3.modelo.Unidades.TipoDeUnidad;
import edu.fiuba.algo3.modelo.Revelo;
import edu.fiuba.algo3.modelo.Revelador;
import edu.fiuba.algo3.modelo.Unidades.Unidad;


public class AmoSupremo implements TipoDeUnidad, Atacable, Revelador  {

    public static final int CUPO_AMO = 0;
    private final int POBLACION = 5;
    private final int COSTO_MINERAL = 50;
    private final int COSTO_GAS = 0;

    private Vida vida;
    private Jugador jugador;
    private Ubicacion ubicacion;
    private Superficie superficie;

    private Revelo revelo;

    public AmoSupremo(Ubicacion unaUbicacion, Jugador unJugador) {
        unJugador.utilizar(COSTO_GAS, COSTO_MINERAL);

        this.vida = new Vida(200);
        this.jugador = unJugador;
        this.ubicacion = unaUbicacion;
        this.superficie = new Superficie("Aire");
        this.revelo = new Revelo(new Superficie("Aire"), 4);
    }

    public AmoSupremo(Jugador unJugador) {
        unJugador.utilizar(COSTO_GAS, COSTO_MINERAL);

        this.vida = new Vida(200);
        this.jugador = unJugador;
        this.ubicacion = new Ubicacion();
        this.superficie = new Superficie("Aire");
        this.revelo = new Revelo(new Superficie("Aire"), 4);
    }


    @Override
    public int obtenerPoblacion() {
        return POBLACION;
    }

    @Override
    public void recibirAtaque(int unDanio) {
        this.vida.recibirDanioPor(unDanio);
    }

    public Ubicacion ubicacion() {
        return (this.ubicacion);
    }

    public int vidaRestante() {
        return (this.vida.restante());
    }

    @Override
    public Superficie obtenerSuperficie() {
        return this.superficie;
    }

    @Override
    public void atacar(Atacable unAtacable) {
        // Amo Supremo no entiende este mensaje.
    }

    @Override
    public void revelar(Revelable unRevelable) {
        if (!(this.estaEnRangoDeRevelo(unRevelable, this.revelo))) {
            throw new RevelableFueraDeRangoError();
        }

        revelo.revelarA(unRevelable);
    }

    public boolean estaEnRangoDeRevelo(Revelable unRevelable, Revelo unRevelo) {
        return (this.ubicacion.distanciaCon(unRevelable.ubicacion()) <= unRevelo.rango());
    }

    public void recuperarse() {
        this.vida.recuperarse();
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