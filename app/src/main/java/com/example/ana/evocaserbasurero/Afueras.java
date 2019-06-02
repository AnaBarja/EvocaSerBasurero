package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

/**
 * Clase Afueras, corresponde a la segunda fase del juego, hereda de Game
 *
 * @author Ana Barja Fernández
 */
public class Afueras extends Game {

    /**
     * Constructor de la fase Afueras, se utiliza lo de la clase Game y se cambian los parámetros necesarios,
     * como son el tiempo de partida, el número mínimo para pasar la fase, los bitmaps de fondo y enemigo. Es necesario empezar de
     * cero cada vez que se crea para que no haya errores
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena que es
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Afueras(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);

        parametrosPartida(90, 50);

        bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoEnteroAfueras.png"), anchoPantalla, altoPantalla, true);
        fondo = new Fondo[2];
        fondo[0] = new Fondo(bitmapFondo, anchoPantalla);
        fondo[1] = new Fondo(bitmapFondo, fondo[0].posicion.x + bitmapFondo.getWidth(), 0);

        bmEnemigo = new Bitmap[2];
        bmEnemigo[0] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/cocodrilo1.png"), anchoPantalla / 15, altoPantalla / 15, true);
        bmEnemigo[1] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/cocodrilo2.png"), anchoPantalla / 15, altoPantalla / 15, true);
        enemigo = new Enemigo(bmEnemigo, anchoPantalla, altoPantalla * 9 / 10 - bmEnemigo[0].getHeight());

        empezarDeCero();

    }

    /**
     * Método actualizar física, se utiliza el de la clase Game y se cambian los parámetros necesarios
     * La velocidad de este enemigo es mayor que en la fase anterior
     */
    @Override
    public void actualizarFisica() {
        super.actualizarFisica();
        enemigo.moverEnemigo(anchoPantalla / 300);
    }

    /**
     * Método dibujar, se utiliza el de la clase Game y se añaden cosas
     * Si se supera la fase se consigue el logro de Profesional
     *
     * @param c el lienzo donde se dibuja
     */
    @Override
    public void dibujar(Canvas c) {
        super.dibujar(c);
        if (finalPartida) {
            if (ganar && !perder) {
                Escena.profesional = true;
                g.saveBool("profesional", true);
            }
        }
    }

    /**
     * Método para detectar la pulsación en la pantalla, se utiliza el de la clase Game
     *
     * @param event evento de pulsación de la pantalla
     * @return devuelve el id de la Escena, que usa de la clase Game
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * Método para detectar los cambios en el sensor de acelerómetro
     *
     * @param event evento del sensor
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        super.onSensorChanged(event);
    }

    /**
     * Método para detectar la precisión del sensor
     *
     * @param sensor   sensor que se usa
     * @param accuracy precisión del sensor
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        super.onAccuracyChanged(sensor, accuracy);
    }
}
