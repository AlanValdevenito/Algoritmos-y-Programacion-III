package edu.fiuba.algo3.vista;

import edu.fiuba.algo3.controlador.*;
import edu.fiuba.algo3.modelo.AlgoStar;
import edu.fiuba.algo3.modelo.Edificios.Edificio;
import edu.fiuba.algo3.modelo.Jugador.Jugador;
import edu.fiuba.algo3.modelo.Jugador.JugadorZerg;
import edu.fiuba.algo3.modelo.Recursos.Gas.Volcan;
import edu.fiuba.algo3.modelo.Recursos.Minerales.NodoMineral;
import edu.fiuba.algo3.modelo.Ubicacion;
import edu.fiuba.algo3.modelo.Unidades.Unidad;
import edu.fiuba.algo3.modelo.Unidades.UnidadesZerg.Zangano;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaJuego extends BorderPane {

    public static int ANCHO = 1400;
    public static int ALTURA = 700;

    public static int TAMAÑO_CELDA = 50;

    private int numeroTurno;

    private int coordenadaX;
    private int coordenadaY;

    private int unidadCoordenadaX;
    private int unidadCoordenadaY;

    Stage stage;
    Scene proximaExcena;
    VistaMapa vistaMapa;
    Canvas canvasCentral;

    public PantallaJuego(Stage stage, Scene proximaEscena, AlgoStar algoStar) {
        this.stage = stage;
        this.proximaExcena = proximaEscena;
        this.numeroTurno = 1;

        this.setFondo();
        this.setCentro(algoStar);
        this.setArriba(algoStar);

        this.canvasCentral.setOnMouseClicked(e -> {
            this.coordenadaX = (int) e.getX();
            this.coordenadaY = (int) e.getY();
            this.setDerecha(algoStar);

        });
    }

    public int convertirCoordenada(int unaCoordenada) {
        int nuevaCoordenada = (int) Math.ceil(unaCoordenada/TAMAÑO_CELDA);
        return nuevaCoordenada;
    }

    public void setUnidadCoordenadas(int x, int y) {
        this.unidadCoordenadaX = x;
        this.unidadCoordenadaY = y;
    }

    public int getCoordenadaX() {
        return this.coordenadaX;
    }

    public int getCoordenadaY() {
        return this.coordenadaY;
    }

    public int getUnidadCoordenadaX() {
        return this.unidadCoordenadaX;
    }

    public int getUnidadCoordenadaY() {
        return this.unidadCoordenadaY;
    }

    public void aumentarTurno() {
        this.numeroTurno++;
    }

    public void setFondo() {
        Image imagen = new Image("file:src/main/resources/images/background-juego.png", 1920, 1080, false, false);
        BackgroundImage imagenDeFondo = new BackgroundImage(imagen, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(imagenDeFondo));
    }

    public void setDerecha(AlgoStar algoStar) {
        // Informacion de unidades, edificios y recursos

        Label primerInformacion = new Label();
        primerInformacion.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        primerInformacion.setTextFill(Color.web("#ffffff"));

        Label segundaInformacion = new Label();
        segundaInformacion.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        segundaInformacion.setTextFill(Color.web("#ffffff"));

        VBox contenedorVerticalEntidad = new VBox(primerInformacion, segundaInformacion);
        contenedorVerticalEntidad.setSpacing(10);
        contenedorVerticalEntidad.setTranslateX(-120);
        contenedorVerticalEntidad.setTranslateY(125);


        if(algoStar.obtenerMapa().verificarEdificioEn(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)))) {
            Edificio edificio = algoStar.obtenerMapa().obtenerEdificioEn(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)));

            primerInformacion.setText("Vida: " + edificio.obtenerVida());
            primerInformacion.setTranslateX(20);

            segundaInformacion.setText("Escudo: " + edificio.obtenerEscudo());
            segundaInformacion.setTranslateX(20);
        }

        else if(algoStar.obtenerMapa().verificarVolcanEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)))) {
            Volcan volcan = algoStar.obtenerMapa().volcanEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)));

            primerInformacion.setText("Gas: " + volcan.getCantidadDeGasVespenoDisponible());
            primerInformacion.setTranslateX(20);
        }

        else if(algoStar.obtenerMapa().verificarNodoMineralEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)))) {
            NodoMineral nodo = algoStar.obtenerMapa().nodoEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)));

            primerInformacion.setText("Mineral: " + nodo.getCantidadDeMineralDisponible());
            primerInformacion.setTranslateX(20);
        }

        else if(algoStar.obtenerMapa().verificarUnidadEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)))) {
            Unidad unidad = algoStar.obtenerMapa().obtenerUnidadEnUbicacion(new Ubicacion(convertirCoordenada(this.coordenadaX), convertirCoordenada(this.coordenadaY)));

            primerInformacion.setText("Vida: " + unidad.vidaRestante());
            primerInformacion.setTranslateX(20);

            segundaInformacion.setText("Escudo: " + unidad.escudoRestante());
            segundaInformacion.setTranslateX(20);
        }

        this.setRight(contenedorVerticalEntidad);
    }

    public void setArriba(AlgoStar algoStar) {

        // Boton para avanzar turno

        Button botonAvanzarTurno = new Button();
        botonAvanzarTurno.setText("Avanzar turno");
        botonAvanzarTurno.setTranslateX(-10);
        botonAvanzarTurno.setTranslateY(10);

        BotonAvanzarTurnoEventHandler botonAvanzarTurnoEventHandler = new BotonAvanzarTurnoEventHandler(this.stage, this.proximaExcena, this, this.vistaMapa, algoStar);
        botonAvanzarTurno.setOnAction(botonAvanzarTurnoEventHandler);

        VBox contenedorAvanzarTurno = new VBox(botonAvanzarTurno);
        contenedorAvanzarTurno.setSpacing(5);
        contenedorAvanzarTurno.setTranslateX(1600);
        contenedorAvanzarTurno.setTranslateY(50);

        // Informacion del mouse sobre el canvas

        Label coordenadaX = new Label();
        coordenadaX.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        coordenadaX.setTextFill(Color.web("#ffffff"));

        Label coordenadaY = new Label();
        coordenadaY.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        coordenadaY.setTextFill(Color.web("#ffffff"));

        HBox contenedorHorizontalCoordenadas = new HBox(coordenadaX, coordenadaY);
        contenedorHorizontalCoordenadas.setSpacing(10);
        contenedorHorizontalCoordenadas.setTranslateX(780);
        contenedorHorizontalCoordenadas.setTranslateY(20);

        this.canvasCentral.setOnMouseMoved(e -> {
            coordenadaX.setText("x: " + e.getX());
            coordenadaY.setText("y: " + e.getY());
        });

        // Contenedor

        HBox contenedorVertical = new HBox(contenedorAvanzarTurno, contenedorHorizontalCoordenadas);
        this.setTop(contenedorVertical);
    }

    // Se crea el menu donde estara la informacion del jugador: Nombre, Color, Raza, Recursos, Poblacion, Suministro.
    public void setInformacion(AlgoStar algoStar) {

        // Etiqueta turnos

        Label turnos = new Label();
        turnos.setText("Turno " + this.numeroTurno);
        turnos.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        turnos.setTextFill(Color.web("#ffffff"));

        VBox contenedorVerticalTurnos = new VBox(turnos);
        contenedorVerticalTurnos.setTranslateX(80);
        contenedorVerticalTurnos.setTranslateY(125);

        // Informacion del jugador

        Jugador jugadorActual = algoStar.obtenerJugadorTurno();

        Label nombre = new Label();
        nombre.setText("Jugador: " + jugadorActual.obtenerNombre());
        nombre.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        nombre.setTextFill(Color.web("#ffffff"));

        Label color = new Label();
        color.setText("Color: " + jugadorActual.obtenerColor());
        color.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        color.setTextFill(Color.web("#ffffff"));

        Label raza = new Label();
        raza.setText("Raza: " + jugadorActual.obtenerRaza());
        raza.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        raza.setTextFill(Color.web("#ffffff"));

        Label gas = new Label();
        gas.setText("Gas: " + jugadorActual.obtenerGas());
        gas.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        gas.setTextFill(Color.web("#ffffff"));

        Label mineral = new Label();
        mineral.setText("Mineral: " + jugadorActual.obtenerMineral());
        mineral.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        mineral.setTextFill(Color.web("#ffffff"));

        Label poblacion = new Label();
        poblacion.setText("Poblacion: " + jugadorActual.calcularPoblacion());
        poblacion.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        poblacion.setTextFill(Color.web("#ffffff"));

        Label suministro = new Label();
        suministro.setText("Suministro: " + jugadorActual.calcularSuministro());
        suministro.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        suministro.setTextFill(Color.web("#ffffff"));

        // Contenedor de informacion

        VBox contenedorVerticalInformacion = new VBox(nombre, color, raza, gas, mineral, poblacion, suministro);
        contenedorVerticalInformacion.setSpacing(10);
        contenedorVerticalInformacion.setTranslateX(80);
        contenedorVerticalInformacion.setTranslateY(125);

        // Contenedor

        VBox contenedorVertical = new VBox(contenedorVerticalTurnos, contenedorVerticalInformacion);
        contenedorVertical.setSpacing(30);

        this.setLeft(contenedorVertical);
    }

    public void setBotonera(AlgoStar algoStar) {
        Jugador jugadorActual = algoStar.obtenerJugadorTurno();

        if (jugadorActual.obtenerRaza().equals("Zerg")) {
            HBox contenedorHorizontalBotonera = new HBox(this.setBotoneraEdificiosZerg(algoStar), this.setBotoneraUnidadesZerg(algoStar), this.setBotoneraMovimiento(algoStar), this.setBotoneraAtaque(algoStar));
            contenedorHorizontalBotonera.setSpacing(50);
            contenedorHorizontalBotonera.setTranslateX(50);
            contenedorHorizontalBotonera.setTranslateY(-80);
            this.setBottom(contenedorHorizontalBotonera);
        } else {
            HBox contenedorHorizontalBotonera = new HBox(this.setBotoneraEdificiosProtoss(algoStar), this.setBotoneraUnidadesProtoss(algoStar), this.setBotoneraMovimiento(algoStar), this.setBotoneraAtaque(algoStar));
            contenedorHorizontalBotonera.setSpacing(50);
            contenedorHorizontalBotonera.setTranslateX(50);
            contenedorHorizontalBotonera.setTranslateY(-80);
            this.setBottom(contenedorHorizontalBotonera);
        }
    }

    // Se crea la botonera Zerg con los edificios correspondientes.
    public GridPane setBotoneraEdificiosZerg(AlgoStar algoStar) {
        // Botonera edificios

        Button criadero = new Button();
        criadero.setText("Crear criadero ");
        criadero.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearCriaderoEventHandler = new BotonCrearEntidadEventHandler("Criadero", algoStar, vistaMapa, this);
        criadero.setOnAction(botonCrearCriaderoEventHandler);

        Button reserva = new Button();
        reserva.setText(" Crear reserva ");
        reserva.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearReservaEventHandler = new BotonCrearEntidadEventHandler("ReservaDeReproduccion", algoStar, vistaMapa, this);
        reserva.setOnAction(botonCrearReservaEventHandler);

        Button extractor = new Button();
        extractor.setText("Crear extractor");
        extractor.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearExtractorEventHandler = new BotonCrearEntidadEventHandler("Extractor", algoStar, vistaMapa, this);
        extractor.setOnAction(botonCrearExtractorEventHandler);

        Button guarida = new Button();
        guarida.setText(" Crear guarida ");
        guarida.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearGuaridaEventHandler = new BotonCrearEntidadEventHandler("Guarida", algoStar, vistaMapa, this);
        guarida.setOnAction(botonCrearGuaridaEventHandler);

        Button espiral = new Button();
        espiral.setText(" Crear espiral ");
        espiral.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearEspiralEventHandler = new BotonCrearEntidadEventHandler("Espiral", algoStar, vistaMapa, this);
        espiral.setOnAction(botonCrearEspiralEventHandler);

        GridPane panel = new GridPane();
        GridPane.setConstraints(criadero, 0, 0);
        GridPane.setConstraints(reserva, 1, 0);
        GridPane.setConstraints(extractor, 0, 1);
        GridPane.setConstraints(guarida, 1, 1);
        GridPane.setConstraints(espiral, 0, 2);
        panel.getChildren().addAll(criadero, reserva, extractor, guarida, espiral);

        return panel;
    }

    // Se crea la botonera Zerg con los edificios correspondientes.
    public GridPane setBotoneraUnidadesZerg(AlgoStar algoStar) {
        Jugador jugadorZerg = algoStar.obtenerJugadorTurno();

        Button amo = new Button();
        amo.setText("   Crear Amo Supremo   ");
        amo.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearAmoSupremoEventHandler = new BotonCrearEntidadEventHandler("AmoSupremo", algoStar, vistaMapa, this);
        amo.setOnAction(botonCrearAmoSupremoEventHandler);

        Button zangano = new Button();
        zangano.setText("     Crear Zangano     ");
        zangano.setMinWidth(177);

        BotonCrearEntidadEventHandler botonCrearZanganoEventHandler = new BotonCrearEntidadEventHandler("Zangano", algoStar, vistaMapa, this);
        zangano.setOnAction(botonCrearZanganoEventHandler);

        Button zerling = new Button();
        zerling.setText("     Crear Zerling     ");
        zerling.setMinWidth(183);

        BotonCrearEntidadEventHandler botonCrearZerlingEventHandler = new BotonCrearEntidadEventHandler("Zerling", algoStar, vistaMapa, this);
        zerling.setOnAction(botonCrearZerlingEventHandler);

        Button hidralisco = new Button();
        hidralisco.setText("   Crear Hidralisco    ");
        hidralisco.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearHidraliscoEventHandler = new BotonCrearEntidadEventHandler("Hidralisco", algoStar, vistaMapa, this);
        hidralisco.setOnAction(botonCrearHidraliscoEventHandler);

        Button mutalisco = new Button();
        mutalisco.setText("    Crear Mutalisco    ");
        mutalisco.setMinWidth(180);

        BotonCrearEntidadEventHandler botonCrearMutaliscoEventHandler = new BotonCrearEntidadEventHandler("Mutalisco", algoStar, vistaMapa, this);
        mutalisco.setOnAction(botonCrearMutaliscoEventHandler);

        Button guardian = new Button();
        guardian.setText("Evolucionar a Guardian ");
        guardian.setMinWidth(130);

        guardian.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ((JugadorZerg) jugadorZerg).evolucionarMutaliscoAGuardian(new Ubicacion(convertirCoordenada(coordenadaX), convertirCoordenada(coordenadaY)));
            }
        });

        Button devorador = new Button();
        devorador.setText("Evolucionar a Devorador");
        devorador.setMinWidth(130);

        devorador.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ((JugadorZerg) jugadorZerg).evolucionarMutaliscoADevorador(new Ubicacion(convertirCoordenada(coordenadaX), convertirCoordenada(coordenadaY)));
            }
        });

        GridPane panel = new GridPane();
        GridPane.setConstraints(amo, 0, 0);
        GridPane.setConstraints(zangano, 1, 0);
        GridPane.setConstraints(zerling, 2, 0);
        GridPane.setConstraints(hidralisco, 3, 0);
        GridPane.setConstraints(mutalisco, 0, 1);
        GridPane.setConstraints(guardian, 1, 1);
        GridPane.setConstraints(devorador, 2, 1);
        panel.getChildren().addAll(amo, zangano, zerling, hidralisco, mutalisco, guardian, devorador);

        return panel;
    }

    // Se crea la botonera Protoss con los edificios correspondientes.
    public GridPane setBotoneraEdificiosProtoss(AlgoStar algoStar) {

        Button nexo = new Button();
        nexo.setText("   Crear nexo   ");
        nexo.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearNexoEventHandler = new BotonCrearEntidadEventHandler("NexoMineral", algoStar, vistaMapa, this);
        nexo.setOnAction(botonCrearNexoEventHandler);

        Button pilon = new Button();
        pilon.setText("  Crear pilon   ");
        pilon.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearPilonEventHandler = new BotonCrearEntidadEventHandler("Pilon", algoStar, vistaMapa, this);
        pilon.setOnAction(botonCrearPilonEventHandler);

        Button asimilador = new Button();
        asimilador.setText("Crear asimilador");
        asimilador.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearAsimiladorEventHandler = new BotonCrearEntidadEventHandler("Asimilador", algoStar, vistaMapa, this);
        asimilador.setOnAction(botonCrearAsimiladorEventHandler);

        Button acceso = new Button();
        acceso.setText("  Crear acceso  ");
        acceso.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearAccesoEventHandler = new BotonCrearEntidadEventHandler("Acceso", algoStar, vistaMapa, this);
        acceso.setOnAction(botonCrearAccesoEventHandler);

        Button puerto = new Button();
        puerto.setText("  Crear puerto  ");
        puerto.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearPuertoEventHandler = new BotonCrearEntidadEventHandler("PuertoEstelar", algoStar, vistaMapa, this);
        puerto.setOnAction(botonCrearPuertoEventHandler);

        GridPane panel = new GridPane();
        GridPane.setConstraints(nexo, 0, 0);
        GridPane.setConstraints(pilon, 1, 0);
        GridPane.setConstraints(asimilador, 0, 1);
        GridPane.setConstraints(acceso, 1, 1);
        GridPane.setConstraints(puerto, 0, 2);
        panel.getChildren().addAll(nexo, pilon, asimilador, acceso, puerto);

        return panel;
    }

    // Se crea la botonera Protoss con los edificios correspondientes.
    public GridPane setBotoneraUnidadesProtoss(AlgoStar algoStar) {

        Button zealot = new Button();
        zealot.setText("Crear Zealot");
        zealot.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearZealotEventHandler = new BotonCrearEntidadEventHandler("Zealot", algoStar, vistaMapa, this);
        zealot.setOnAction(botonCrearZealotEventHandler);

        Button dragon = new Button();
        dragon.setText("Crear Dragon");
        dragon.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearDragonEventHandler = new BotonCrearEntidadEventHandler("Dragon", algoStar, vistaMapa, this);
        dragon.setOnAction(botonCrearDragonEventHandler);

        Button scout = new Button();
        scout.setText("Crear Scout ");
        scout.setMinWidth(130);

        BotonCrearEntidadEventHandler botonCrearScoutEventHandler = new BotonCrearEntidadEventHandler("Scout", algoStar, vistaMapa, this);
        scout.setOnAction(botonCrearScoutEventHandler);

        GridPane panel = new GridPane();
        GridPane.setConstraints(zealot, 0, 0);
        GridPane.setConstraints(dragon, 1, 0);
        GridPane.setConstraints(scout, 2, 0);
        panel.getChildren().addAll(zealot, dragon, scout);

        return panel;
    }

    // Se crea la botonera de movimiento
    public HBox setBotoneraMovimiento(AlgoStar algoStar) {
        // Boton de movimiento

        Button mover = new Button();
        mover.setText("Mover");

        BotonMoverEventHandler botonMoverEventHandler = new BotonMoverEventHandler(vistaMapa, algoStar.obtenerJugadorTurno(), this);
        mover.setOnAction(botonMoverEventHandler);

        // Boton de movimiento

        Button direccion = new Button();
        direccion.setText("Cambiar de direccion");

        BotonDireccionEventHandler botonDireccionEventHandler = new BotonDireccionEventHandler(algoStar.obtenerJugadorTurno(), this);
        direccion.setOnAction(botonDireccionEventHandler);

        HBox contenedorHorizontalBMovimiento = new HBox(mover, direccion);

        return contenedorHorizontalBMovimiento;
    }

    // Se crea la botonera ataque y trabajo
    public HBox setBotoneraAtaque(AlgoStar algoStar) {
        // Boton elegir unidad

        Button unidad = new Button();
        unidad.setText("Elegir unidad");

        BotonElegirUnidadEventHandler botonElegirUnidadEventHandler = new BotonElegirUnidadEventHandler(algoStar, this);
        unidad.setOnAction(botonElegirUnidadEventHandler);

        // Boton atacar

        Button atacar = new Button();
        atacar.setText(" Atacar ");

        BotonAtacarEventHandler botonAtacarEventHandler = new BotonAtacarEventHandler(algoStar, this, vistaMapa);
        atacar.setOnAction(botonAtacarEventHandler);

        // Boton trabajar

        Button trabajar = new Button();
        trabajar.setText("Trabajar");

        BotonTrabajarEventHandler botonTrabajarEventHandler = new BotonTrabajarEventHandler(algoStar, this, vistaMapa);
        trabajar.setOnAction(botonTrabajarEventHandler);

        // Contenedores

        VBox contenedorHorizontalAcciones = new VBox(atacar, trabajar);
        HBox contenedorHorizontal = new HBox(unidad, contenedorHorizontalAcciones);

        return contenedorHorizontal;
    }

    // Se crea la pantalla central donde estara el mapa
    public void setCentro(AlgoStar algostar) {

        // Se crea el canvas donde se dibujara

        this.canvasCentral = new Canvas(ANCHO, ALTURA); // Ancho, Altura

        // Se dibuja el mapa

        this.vistaMapa = new VistaMapa(algostar.obtenerMapa(), canvasCentral);
        this.vistaMapa.dibujar();

        // Se ubica el canvas en el centro del BorderPane

        this.setCenter(canvasCentral);
    }
}