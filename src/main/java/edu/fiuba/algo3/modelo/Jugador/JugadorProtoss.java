package edu.fiuba.algo3.modelo.Jugador;

import edu.fiuba.algo3.modelo.Excepciones.CantidadInsuficienteDeRecursosException;
import edu.fiuba.algo3.modelo.Excepciones.SinCupoSuficienteException;
import edu.fiuba.algo3.modelo.Unidades.Unidad;
import edu.fiuba.algo3.modelo.Unidades.UnidadesProtoss.Zealot;

import java.util.ArrayList;
import java.util.List;

import static edu.fiuba.algo3.util.Constantes.*;

public class JugadorProtoss implements IJugador {
    public static final int MAX_POBLACION = 200;

    private int cantidadMineral;
    private int cantidadGas;
    private int cupo;
    private int cantidadDeZealots;
    private int cantidadDeDragones;
    private int cantidadDeScouts;

    public JugadorProtoss() {
        this.cantidadMineral = 0;
        this.cantidadGas = 0;
        this.cupo = 0;
        this.cantidadDeZealots = 0;
        this.cantidadDeDragones = 0;
        this.cantidadDeScouts = 0;
    }

    public void crearPilon() {
        if (this.cantidadMineral < COSTO_PILON) {
            throw new CantidadInsuficienteDeRecursosException("No hay recursos suficientes");
        }
        this.cantidadMineral -= COSTO_PILON;
        this.cupo += 5;
    }

    public void incrementarMineral(int cantidad) {
        if (cantidad > 0) {
            this.cantidadMineral += cantidad;
        }
    }

    public void crearNexo() {
        if (this.cantidadMineral < COSTO_NEXO) {
            throw new CantidadInsuficienteDeRecursosException("No hay recursos suficientes");
        }
        this.cantidadMineral -= COSTO_NEXO;
    }

    public void crearAsimilador() {
        if (this.cantidadMineral < COSTO_ASIMILADOR) {
            throw new CantidadInsuficienteDeRecursosException("No hay recursos suficientes");
        }
        this.cantidadMineral -= COSTO_ASIMILADOR;
    }

    public void crearAcceso() {
        if (this.cantidadMineral < COSTO_ACCESO) {
            throw new CantidadInsuficienteDeRecursosException("No hay recursos suficientes");
        }
        this.cantidadMineral -= COSTO_ACCESO;
    }

    public void crearPuertoEstelar() {
        if (this.cantidadMineral < COSTO_MINERAL_PUERTO || this.cantidadGas < COSTO_GAS_PUERTO) {
            throw new CantidadInsuficienteDeRecursosException("No hay recursos suficientes");
        }
        this.cantidadMineral -= COSTO_MINERAL_PUERTO;
        this.cantidadGas -= COSTO_GAS_PUERTO;
    }

    public void incrementarGas(int cantidad) {
        if (cantidad > 0) {
            this.cantidadGas += cantidad;
        }
    }

    public void crearZealot() {
        if (this.cupo < 2) {
            throw new SinCupoSuficienteException("Se necesitan 2 cupos");
        }
        this.cupo -= 2;
        this.cantidadDeZealots++;
    }

    public int cantidadDeUnidades(UNIDADES_PROTOSS tipoUnidad) {
        if (tipoUnidad == UNIDADES_PROTOSS.ZEALOT) {
            return this.cantidadDeZealots;
        }
        if (tipoUnidad == UNIDADES_PROTOSS.DRAGON) {
            return this.cantidadDeDragones;
        }
        if (tipoUnidad == UNIDADES_PROTOSS.SCOUT) {
            return this.cantidadDeScouts;
        }
        return 0;
    }

    public void crearDragon() {
        if (this.cupo < 3) {
            throw new SinCupoSuficienteException("Se necesitan 3 cupos");
        }
        this.cupo -= 3;
        this.cantidadDeDragones++;
    }

    public void crearScout() {
        if (this.cupo < 4) {
            throw new SinCupoSuficienteException("Se necesitan 4 cupos");
        }
        this.cupo -= 4;
        this.cantidadDeScouts++;
    }
}
