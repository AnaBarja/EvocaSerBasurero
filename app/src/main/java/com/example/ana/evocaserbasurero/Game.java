package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase game
 *
 * @author Ana Barja Fernández
 */
public class Game extends Escena implements SensorEventListener {

    /**
     * Creación del camión
     */
    protected Camion camion;

    /**
     * Creación del enemigo
     */
    protected Enemigo enemigo;

    /**
     * Creación del personaje
     */
    protected Personaje personaje;

    /**
     * Bitamp para dibujar el camión
     */
    protected Bitmap bitmapCamion;

    /**
     * Bitamp para dibujar el fondo
     */
    protected Bitmap bitmapFondo;

    /**
     * Bitamp para dibujar el cubo lleno
     */
    protected Bitmap bitmapCuboLleno;

    /**
     * Bitamp para dibujar el cubo vacio
     */
    protected Bitmap bitmapCuboVacio;

    /**
     * Bitamp para dibujar el indicador de basura recogida
     */
    protected Bitmap bitmapBasuraRecogida;

    /**
     * Bitamp para dibujar el reloj
     */
    protected Bitmap bitmapReloj;

    /**
     * Bitamp para dibujar el indicador de basura en el inventario
     */
    protected Bitmap bitmapBolsillos;

    /**
     * Bitmaps del control para avanzar
     */
    protected Bitmap bmFlechaDerecha;

    /**
     * Bitmaps del control para retroceder
     */
    protected Bitmap bmFlechaIzquierda;

    /**
     * Bitmaps del control para subir al camión
     */
    protected Bitmap bmFlechaArriba;

    /**
     * Bitmaps del control para bajar del camión
     */
    protected Bitmap bmFlechaAbajo;

    /**
     * Bitmaps del control para tirar la basura
     */
    protected Bitmap bmTirarBasura;

    /**
     * Bitmaps del control para recoger la basura
     */
    protected Bitmap bmRecogerBasura;

    /**
     * Boolean para controlar si el personaje está subido al camión
     */
    protected boolean conduciendo = true;

    /**
     * Boolean para controlar si el personaje puede subir al camión al hacer colisión su hitbox superior y la de la derecha del camion
     */
    protected boolean puedeSubir = false;

    /**
     * Boolean para controlar si el personaje está conduciendo y mover el fondo
     */
    protected boolean moverFondo = false;

    /**
     * Boolean para controlar si el personaje se está moviendo o no
     */
    protected boolean moverPersonaje = false;

    /**
     * Boolean para controlar hacia que lado se mueve el personaje
     */
    protected boolean ladoPersonaje = true;

    /**
     * Boolean para controlar si el personaje puede recoger la basura al hacer colisión su hitbox inferior con el cubo de basura
     */
    protected boolean recogerBasura = false;

    /**
     * Int para saber de que cubo de la basura se está recogiendo la basura
     */
    protected int contCuboBasura = 0;

    /**
     * Array de fondos para hacer el scroll del fondo
     */
    protected Fondo[] fondo;

    /**
     * Random para que la cantidad de basura de cada cubo sea distinta, 1-3
     */
    protected Random rand = new Random();

    /**
     * Int de la velocidad del fondo
     */
    protected int velocidadFondo = -anchoPantalla / 100;

    /**
     * Int de la velocidad del enemigo
     */
    protected int velocidadEnemigo = anchoPantalla / 100;

    /**
     * Int de la velocidad del personaje
     */
    protected int velocidadPersonaje = anchoPantalla / 150;

    /**
     * Creación de los cubos
     */
    protected Cubo cubo1, cubo2, cubo3;

    /**
     * Arraylist de cubos a los que se le añaden los cubos creados
     */
    protected ArrayList<Cubo> cubos = new ArrayList<>();

    /**
     * Int para contar el número de piezas que lleva el personaje en el inventario
     */
    protected int numeroPiezasBolsillo;

    /**
     * Int con el número de piezas que tiene un cubo de basura, entre 1 y 3
     */
    protected int piezasContenedor;

    /**
     * Int para contar el número de piezas que lleva el camión
     */
    protected int numeroPiezasPartida = 0;

    /**
     * Int para saber el cubo de la basura del que se saca la basura
     */
    protected int cuboSeleccionado;

    /**
     * Booleana para saber si se sobrepasa el limite de piezas del inventario
     */
    protected boolean limitePiezas = false;

    /**
     * Booleana para saber si se puede tirar la basura (colisionan las hitbox del cubo y del persona)
     */
    protected boolean puedeVaciar;

    /**
     * Booleana para saber si aparece el enemigo (aparece cada 10 segundos)
     */
    protected boolean apareceEnemigo;

    /**
     * Long que indica el tiempo que queda para que acabe la partida
     */
    protected long tiempo = 0;

    /**
     * Control para gestionar la pulsación de la flecha para subir al camión
     */
    protected Controles flechaArriba;

    /**
     * Control para gestionar la pulsación de la flecha para bajar del camión
     */
    protected Controles flechaAbajo;

    /**
     * Control para gestionar la pulsación de la flecha avanzar
     */
    protected Controles flechaDerecha;

    /**
     * Control para gestionar la pulsación de la flecha retroceder
     */
    protected Controles flechaIzquierda;

    /**
     * Control para gestionar la pulsación de la flecha para tirar la basura
     */
    protected Controles tirarB;

    /**
     * Control para gestionar la pulsación de la flecha para recoger la basura
     */
    protected Controles recogerB;

    /**
     * Creación del vibrador
     */
    protected Vibrator v;

    /**
     * Creación del sensor
     */
    protected SensorManager sensor;

    /**
     * Creación del sensor del acelerometro
     */
    protected Sensor acelerometro;

    /**
     * Long para calcular la diferencia de tiempo y saber si se ha agitado el móvil en ese tiempo
     */
    protected long ultimaVez = 0;

    /**
     * Float para controlar la inclinación del móvil
     */
    protected float ultimaX, ultimaY, ultimaZ;

    /**
     * Int para indicar la sacudida mínima del móvil para soltarse del enemigo
     */
    protected final int sacudidaMovil = 1800;

    /**
     * Booleana para controlar si el móvil se ha agitado
     */
    protected boolean agitar = true;

    /**
     * Booleana para poner el juego en pausa
     */
    protected boolean jugando = true;

    /**
     * Booleana para acabar la partida al perder o acabar el tiempo
     */
    protected boolean finalPartida = false;

    /**
     * Bitmap para dibujar el botón de reiniciar
     */
    protected Bitmap bmReiniciar;

    /**
     * Bitmap para dibujar el botón de seguir la partida
     */
    protected Bitmap bmSeguir;

    /**
     * Bitmap para dibujar el botón de salir de la partida
     */
    protected Bitmap bmSalir;

    /**
     * Bitmap para dibujar el fondo de los botones
     */
    protected Bitmap bmCuadro;

    /**
     * Bitmap para dibujar el fondo de los botones
     */
    protected Bitmap bmCuadroLleno;

    /**
     * Control para gestionar la pulsación de reiniciar
     */
    protected Controles reiniciar;

    /**
     * Control para gestionar la pulsación de seguir la partida
     */
    protected Controles seguir;

    /**
     * Control para gestionar la pulsación de salir de la partida
     */
    protected Controles salir;

    /**
     * Int para indicar el número mínimo de basura que se necesita para ganar la partida
     */
    protected int numParaGanar = 0;

    /**
     * Int para controlar que el vibrador sólo vibre una vez cuando el enemigo atrapa al personaje
     */
    protected int chocaUna = 0;

    /**
     * Booleana para controlar al acabar la partida si ha ganado la partida
     */
    protected boolean ganar = false;

    /**
     * Booleana para controlar al acabar la partida si ha perdido la partida
     */
    protected boolean perder = false;

    /**
     * CountDownTimer para controlar el tiempo de la partida, si se pausa la partida se para el tiempo
     */
    protected MyCountDownTimer timer;

    /**
     * Long auxiliar para cuando se para la partida guardar el tiempo en el que se pausa y al iniciar de nuevo volver
     * a crear el timer con el tiempo que quedaba en tiempoPausa
     */
    protected long tiempoPausa = 0;

    /**
     * Long para controlar cuanto tiempo pasa desde que el enemigo choca hasta que se suelta, si sobrepasa el tiempo puesto se pierde
     */
    protected long tiempoChoca = 0;

    /**
     * Long para indicar el tiempo que dura la partida
     */
    protected long tiempoPartida = 0;

    /**
     * String para el texto de pausa
     */
    protected String pausa;

    /**
     * String para el texto de perder
     */
    protected String pierdes;

    /**
     * String para el texto de ganar
     */
    protected String ganas;

    /**
     * String para el texto de inventario lleno
     */
    protected String lleno;

    /**
     * String para el texto de fin
     */
    protected String fin;

    /**
     * Bitmaps para dibujar al personaje con movimiento
     */
    protected Bitmap[] bmPersonaje;

    /**
     * Bitmaps para dibujar al enemigo con movimiento
     */
    protected Bitmap[] bmEnemigo;

    /**
     * Constructor de la clase game
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Game(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        bitmapCamion = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/camion.png"), anchoPantalla * 5 / 20, altoPantalla * 5 / 20, true);
        camion = new Camion(bitmapCamion, anchoPantalla / 25, altoPantalla * 8 / 10 - bitmapCamion.getHeight());

        bmPersonaje = new Bitmap[2];
        bmPersonaje[0] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/personaje1.png"), altoPantalla / 10, altoPantalla * 2 / 10, true);
        bmPersonaje[1] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/personaje2.png"), altoPantalla / 10, altoPantalla * 2 / 10, true);
        personaje = new Personaje(bmPersonaje, anchoPantalla * 2 / 10, altoPantalla * 9 / 10 - bmPersonaje[0].getHeight());

        bitmapCuboLleno = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuboBasuraLleno.png"), altoPantalla / 13, altoPantalla / 11, true);
        bitmapCuboVacio = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuboBasuraVacio.png"), altoPantalla / 13, altoPantalla / 11, true);
        cubo1 = new Cubo(bitmapCuboLleno, anchoPantalla * 4 / 10, altoPantalla * 9 / 10 - bitmapCuboLleno.getHeight());
        cubo2 = new Cubo(bitmapCuboLleno, anchoPantalla * 6 / 10, altoPantalla * 9 / 10 - bitmapCuboLleno.getHeight());
        cubo3 = new Cubo(bitmapCuboLleno, anchoPantalla * 9 / 10, altoPantalla * 9 / 10 - bitmapCuboLleno.getHeight());
        cubos.add(cubo1);
        cubos.add(cubo2);
        cubos.add(cubo3);

        numeroPiezasBolsillo = 0;
        lleno = getContext().getString(R.string.Full);

        bitmapBasuraRecogida = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuboBasura.png"), altoPantalla / 10, altoPantalla / 10, true);
        bitmapReloj = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/reloj.png"), altoPantalla / 10, altoPantalla / 10, true);
        bitmapBolsillos = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/sacoBasura.png"), altoPantalla / 10, altoPantalla / 10, true);

        bmFlechaIzquierda = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/flechaIzquierda.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        flechaIzquierda = new Controles(bmFlechaIzquierda, anchoPantalla / 20, altoPantalla * 17 / 21, bmFlechaIzquierda.getWidth(), bmFlechaIzquierda.getHeight());

        bmFlechaDerecha = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/flechaDerecha.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        flechaDerecha = new Controles(bmFlechaDerecha, anchoPantalla * 3 / 20, altoPantalla * 17 / 21, bmFlechaDerecha.getWidth(), bmFlechaDerecha.getHeight());

        bmFlechaArriba = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/flechaArriba.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        flechaArriba = new Controles(bmFlechaArriba, anchoPantalla * 17 / 20, altoPantalla * 17 / 21, bmFlechaArriba.getWidth(), bmFlechaArriba.getHeight());

        bmFlechaAbajo = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/flechaAbajo.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        flechaAbajo = new Controles(bmFlechaAbajo, anchoPantalla * 17 / 20, altoPantalla * 17 / 21, bmFlechaAbajo.getWidth(), bmFlechaAbajo.getHeight());

        bmRecogerBasura = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/recogerBasura.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        recogerB = new Controles(bmFlechaArriba, anchoPantalla * 17 / 20, altoPantalla * 17 / 21, bmRecogerBasura.getWidth(), bmRecogerBasura.getHeight());

        bmTirarBasura = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/tirarBasura.png"), altoPantalla * 3 / 20, altoPantalla * 3 / 20, true);
        tirarB = new Controles(bmFlechaAbajo, anchoPantalla * 17 / 20, altoPantalla * 17 / 21, bmTirarBasura.getWidth(), bmTirarBasura.getHeight());

        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        sensor = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        bmSalir = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/salir.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, true);
        salir = new Controles(bmSalir, anchoPantalla * 2 / 10, altoPantalla / 2, bmSalir.getWidth(), bmSalir.getHeight());
        bmSeguir = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/iniciar.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, true);
        seguir = new Controles(bmSeguir, anchoPantalla * 9 / 20, altoPantalla / 2, bmSeguir.getWidth(), bmSeguir.getHeight());
        bmReiniciar = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/reiniciar.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, true);
        reiniciar = new Controles(bmReiniciar, anchoPantalla * 7 / 10, altoPantalla / 2, bmReiniciar.getWidth(), bmReiniciar.getHeight());

        bmCuadro = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuadro.png"), anchoPantalla * 8 / 10, altoPantalla * 8 / 10, true);
        bmCuadroLleno = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuadro.png"), anchoPantalla * 6 / 10, altoPantalla * 4 / 10, true);

        timer = new MyCountDownTimer(tiempoPartida * 1000, 1000);
        timer.start();

        ganas = getContext().getString(R.string.Win);
        pierdes = getContext().getString(R.string.Lost);
        pausa = getContext().getString(R.string.Pause);
        fin = getContext().getString(R.string.End);
    }

    /**
     * Clase MyCountDownTimer para controlar el tiempo de la partida
     */
    private class MyCountDownTimer extends CountDownTimer {

        /**
         * Constructor del timer
         *
         * @param startTime tiempo de la partida
         * @param interval  intervalo por cada tick del timer
         */
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        /**
         * Método que se lanza al finalizar el tiempo o al perder por no soltarse del enemigo
         */
        public void onFinish() {
            finalPartida = true;
            jugando = false;
        }

        /**
         * Método que realiza un tick del timer interval del constructor
         *
         * @param millisUntilFinished tiempo que queda para que finalice el tiempo inicial
         */
        public void onTick(long millisUntilFinished) {
            if (Math.round((float) millisUntilFinished / 1000.0f) != tiempo) {
                tiempo = Math.round((float) millisUntilFinished / 1000.0f);
                if (tiempo % 10 == 0 && tiempo != tiempoPartida) {
                    apareceEnemigo = true;
                    enemigo.setHitbox(true);
                    enemigo.posicion.x = anchoPantalla;
                }
            }
        }
    }

    /**
     * Método para asignar los parámetros de las fases
     *
     * @param tiempo tiempo que dura la fase
     * @param piezas número de piezas que tiene que conseguir para pasar la fase
     */
    public void parametrosPartida(int tiempo, int piezas) {
        numParaGanar = piezas;
        tiempoPartida = tiempo;
    }

    /**
     * Método que actualiza las físicas de la escena
     */
    public void actualizarFisica() {
        if (jugando && !finalPartida) {
            if (apareceEnemigo) {
                if (enemigo.posicion.x <= anchoPantalla) {
                    if (moverFondo)
                        enemigo.moverEnemigo(velocidadEnemigo * 2);
                    else
                        enemigo.moverEnemigo(velocidadEnemigo);
                } else {
                    apareceEnemigo = false;
                }
            }

            if (fondo[0].posicion.x < 0) {
                fondo[1].posicion.x = fondo[0].posicion.x + fondo[1].imagen.getWidth();
            }
            if (fondo[1].posicion.x < 0) {
                fondo[0].posicion.x = fondo[1].posicion.x + fondo[0].imagen.getWidth();
            }

            if (clonaRect(camion.rectangulos[0]).intersect(personaje.rectangulo[0])) {
                puedeSubir = true;
            } else {
                puedeSubir = false;
            }

            if (clonaRect(camion.rectangulos[1]).intersect(personaje.rectangulo[0])) {
                puedeVaciar = true;
            } else {
                puedeVaciar = false;
            }

            if (enemigo.isHitbox())
                if (clonaRect(enemigo.rectangulo).intersect(clonaRect(personaje.rectangulo[1])) && !conduciendo) {
                    chocaUna++;
                    if (chocaUna == 1) {
                        tiempoChoca = tiempo;
                        perder = true;
                        if (Escena.vibracion)
                            v.vibrate(500);
                    }
                    sensor.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
                    enemigo.setParado(true);
                    moverPersonaje = false;
                }

            if (tiempoChoca - tiempo > 2 && perder) {
                timer.onFinish();
            }

            if (!conduciendo) {
                contCuboBasura = 0;
                for (int i = 0; i < cubos.size(); i++) {
                    if (clonaRect(personaje.rectangulo[1]).intersect(cubos.get(i).rectangulo)) {
                        contCuboBasura++;
                        cuboSeleccionado = i;
                    }
                }
                if (contCuboBasura == 1) recogerBasura = true;
                else recogerBasura = false;
            }

            if (moverFondo) {
                for (int i = 0; i < cubos.size(); i++) {
                    cubos.get(i).mantenerCubo(velocidadFondo);
                }
                fondo[0].mover(velocidadFondo);
                fondo[1].mover(velocidadFondo);
            }

            if (moverPersonaje) {
                personaje.moviendo = true;
                if (ladoPersonaje)
                    personaje.andarDerecha(anchoPantalla, velocidadPersonaje);
                else personaje.andarIzquierda(velocidadPersonaje);
            } else personaje.moviendo = false;
        }

        if (!jugando && finalPartida) {
            if (numeroPiezasPartida >= numParaGanar) {
                ganar = true;
            } else {
                ganar = false;
            }
        }
    }

    /**
     * Método de rutina de dibujo en el lienzo
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            if (jugando && !finalPartida) {
                for (int i = 0; i < fondo.length; i++) {
                    c.drawBitmap(fondo[i].imagen, fondo[i].posicion.x, fondo[i].posicion.y, null);
                }

                super.dibujar(c);

                c.drawBitmap(bmFlechaAtras, flechaAtras.posicion.x, flechaAtras.posicion.y, null);
                c.drawBitmap(camion.imagen, camion.posicion.x, camion.posicion.y, null);
                c.drawBitmap(cubo1.imagen, cubo1.posicion.x, cubo1.posicion.y, null);
                c.drawBitmap(cubo2.imagen, cubo2.posicion.x, cubo2.posicion.y, null);
                c.drawBitmap(cubo3.imagen, cubo3.posicion.x, cubo3.posicion.y, null);

                if (!conduciendo) {
                    personaje.dibuja(c);
                    personaje.cambiaFrame();
                }

                if (!enemigo.isParado())
                    c.drawBitmap(bmFlechaDerecha, flechaDerecha.posicion.x, flechaDerecha.posicion.y, null);

                if (!conduciendo && !enemigo.isParado())
                    c.drawBitmap(bmFlechaIzquierda, flechaIzquierda.posicion.x, flechaIzquierda.posicion.y, null);

                if (!conduciendo && puedeSubir && !enemigo.isParado()) {
                    c.drawBitmap(bmFlechaArriba, flechaArriba.posicion.x, flechaArriba.posicion.y, null);
                }

                if (conduciendo && !moverFondo) {
                    c.drawBitmap(bmFlechaAbajo, flechaAbajo.posicion.x, flechaAbajo.posicion.y, null);
                }

                if (apareceEnemigo) {
                    enemigo.dibuja(c);
                    enemigo.cambiaFrame();
                }

                if (recogerBasura && !conduciendo && !puedeSubir && !enemigo.isParado())
                    c.drawBitmap(bmRecogerBasura, recogerB.posicion.x, recogerB.posicion.y, null);

                if (puedeVaciar && !conduciendo && !puedeSubir && !recogerBasura && !enemigo.isParado())
                    c.drawBitmap(bmTirarBasura, tirarB.posicion.x, tirarB.posicion.y, null);

                for (int i = 0; i < cubos.size(); i++) {
                    if (cubos.get(i).posicion.x < 0) {
                        cubos.get(i).posicion.x = anchoPantalla + anchoPantalla * 4 / 10;

                        cubos.get(i).hayBasura = true;
                        cubos.get(i).setImagen(bitmapCuboLleno);
                    }
                }

                c.drawBitmap(bitmapBasuraRecogida, anchoPantalla / 50, altoPantalla / 40, null);
                c.drawText(String.valueOf(numeroPiezasPartida), anchoPantalla * 5 / 50, altoPantalla * 4 / 40, textoMediano);

                c.drawBitmap(bitmapBolsillos, anchoPantalla * 8 / 50, altoPantalla / 40, null);
                c.drawText(String.valueOf(numeroPiezasBolsillo), anchoPantalla * 12 / 50, altoPantalla * 4 / 40, textoMediano);

                c.drawBitmap(bitmapReloj, anchoPantalla * 20 / 50, altoPantalla / 40, null);

                if (limitePiezas) {
                    c.drawBitmap(bmCuadroLleno, anchoPantalla * 2 / 10, altoPantalla / 9, null);
                    c.drawText(lleno, anchoPantalla / 2, altoPantalla / 3, textoMediano);
                }
            }
            if (!jugando && !finalPartida) {
                c.drawBitmap(bmCuadro, anchoPantalla / 10, altoPantalla / 9, null);
                c.drawText(pausa, anchoPantalla / 2, altoPantalla / 3, textoMediano);
                c.drawBitmap(bmSalir, salir.posicion.x, salir.posicion.y, null);
                c.drawBitmap(bmSeguir, seguir.posicion.x, seguir.posicion.y, null);
                c.drawBitmap(bmReiniciar, reiniciar.posicion.x, reiniciar.posicion.y, null);
            }
            if (!finalPartida) {
                c.drawText(tiempo + "", anchoPantalla / 2, altoPantalla * 4 / 40, textoMediano);
            }
            if (finalPartida) {
                c.drawText(fin, anchoPantalla / 2, altoPantalla * 4 / 40, textoMediano);
                c.drawBitmap(bmCuadro, anchoPantalla / 10, altoPantalla / 9, null);
                if (ganar && !perder) {
                    c.drawText(ganas, anchoPantalla / 2, altoPantalla / 3, textoMediano);
                    c.drawBitmap(bmSalir, salir.posicion.x, salir.posicion.y, null);
                    c.drawBitmap(bmSeguir, seguir.posicion.x, seguir.posicion.y, null);
                    c.drawBitmap(bmReiniciar, reiniciar.posicion.x, reiniciar.posicion.y, null);
                    if (Escena.record < numeroPiezasPartida)
                        g.saveInt("record", numeroPiezasPartida);
                    if (numeroPiezasPartida >= 100)
                        g.saveBool("experto", true);
                } else {
                    c.drawText(pierdes, anchoPantalla / 2, altoPantalla / 3, textoMediano);
                    c.drawBitmap(bmSalir, salir.posicion.x, salir.posicion.y, null);
                    c.drawBitmap(bmReiniciar, reiniciar.posicion.x, reiniciar.posicion.y, null);
                }
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método que detecta la pulsación de la pantalla
     *
     * @param event evento de pulsación sobre la pantalla
     * @return devuelve el id de la Escena
     */
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                if (pulsaBitmap(flechaDerecha, event) && conduciendo) {
                    moverFondo = true;
                    Juego.sonarCamion();
                } else if (pulsaBitmap(flechaIzquierda, event) && !conduciendo && !enemigo.isParado()) {
                    moverPersonaje = true;
                    ladoPersonaje = false;
                } else if (pulsaBitmap(flechaDerecha, event) && !conduciendo && !enemigo.isParado()) {
                    moverPersonaje = true;
                    ladoPersonaje = true;
                } else if (pulsaBitmap(flechaAbajo, event) && conduciendo && !moverFondo) {
                    conduciendo = !conduciendo;
                    Juego.sonarPuerta();
                } else if (pulsaBitmap(flechaArriba, event) && !conduciendo && puedeSubir && !enemigo.isParado()) {
                    conduciendo = !conduciendo;
                    Juego.sonarPuerta();
                } else if (pulsaBitmap(recogerB, event) && !conduciendo && !puedeSubir && !puedeVaciar && recogerBasura && !enemigo.isParado()) {
                    if (cubos.get(cuboSeleccionado).hayBasura)
                        if (numeroPiezasBolsillo < 9) {
                            piezasContenedor = rand.nextInt(3) + 1;
                            numeroPiezasBolsillo += piezasContenedor;
                            limitePiezas = false;
                            cubos.get(cuboSeleccionado).hayBasura = false;
                            cubos.get(cuboSeleccionado).setImagen(bitmapCuboVacio);
                            Juego.sonarBasura();
                        } else {
                            limitePiezas = true;
                        }
                } else if (pulsaBitmap(tirarB, event) && !conduciendo && !puedeSubir && puedeVaciar && !enemigo.isParado()) {
                    numeroPiezasPartida += numeroPiezasBolsillo;
                    numeroPiezasBolsillo = 0;
                    limitePiezas = false;
                } else if (pulsaBitmap(flechaAtras, event)) {
                    jugando = false;
                    tiempoPausa = tiempo;
                    timer.cancel();
                } else if (pulsaBitmap(seguir, event) && !jugando && !ganar) {
                    jugando = true;
                    timer = new MyCountDownTimer(tiempoPausa * 1000, 1000);
                    timer.start();
                } else if (pulsaBitmap(reiniciar, event) && !jugando) {
                    empezarDeCero();
                } else if (pulsaBitmap(salir, event) && !jugando) {
                    return 0;
                } else if (pulsaBitmap(seguir, event) && ganar) {
                    return 1;
                }
                break;

            case MotionEvent.ACTION_UP:
                moverFondo = false;
                moverPersonaje = false;
                Juego.pararCamion();
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;

    }

    /**
     * Método para resetar todos los parámetros al darle a reiniciar y al iniciar cada partida
     */
    public void empezarDeCero() {
        timer.cancel();
        finalPartida = false;
        jugando = true;
        perder = false;
        chocaUna = 0;
        limitePiezas = false;
        numeroPiezasPartida = 0;
        numeroPiezasBolsillo = 0;
        tiempo = tiempoPartida;
        enemigo.posicion.x = anchoPantalla;
        apareceEnemigo = false;
        enemigo.setHitbox(false);
        enemigo.setParado(false);
        personaje.setPosicion(anchoPantalla * 2 / 10);
        conduciendo = true;
        for (int i = 0; i < cubos.size(); i++) {
            cubos.get(i).setHayBasura(true);
            cubos.get(i).setImagen(bitmapCuboLleno);
        }
        timer = new MyCountDownTimer(tiempoPartida * 1000, 1000);
        timer.start();
    }

    /**
     * Método para detectar los cambios en el sensor de acelerómetro
     * El sensor se activa cuando el enemigo atrapa al personaje, si no se agita pierde, si se agita se desactiva el vibrador
     * (si lo agita antes de que acabe por si solo), se reinicia el enemigo y se desactiva el sensor
     *
     * @param event evento del sensor
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor;

        if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long tiempoAhora = System.currentTimeMillis();

            if ((tiempoAhora - ultimaVez) > 100) {
                long diferenciaTiempo = (tiempoAhora - ultimaVez);
                ultimaVez = tiempoAhora;

                float speed = Math.abs(x + y + z - ultimaX - ultimaY - ultimaZ) / diferenciaTiempo * 10000;

                if (speed > sacudidaMovil) {
                    agitar = true;
                    v.cancel();
                    enemigo.setHitbox(false);
                    enemigo.setParado(false);
                    perder = false;
                    chocaUna = 0;
                    sensor.unregisterListener(this);
                }
                ultimaX = x;
                ultimaY = y;
                ultimaZ = z;
            }
        }
    }

    /**
     * Método para detectar la precisión del sensor
     *
     * @param sensor   sensor que se usa
     * @param accuracy precisión del sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}