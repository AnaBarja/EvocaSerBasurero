package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

/**
 * Clase Ciudad, corresponde a la primera fase del juego, hereda de Game
 *
 * @author Ana Barja Fernández
 */
public class Ciudad extends Game {

    /**
     * Constructor de la fase Ciudad, se utiliza lo de la clase Game y se cambian los parámetros necesarios,
     * como son el tiempo de partida, el número mínimo para pasar la fase, los bitmaps de fondo y enemigo. Es necesario empezar de
     * cero cada vez que se crea para que no haya errores
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena que es
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Ciudad(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        parametrosPartida(60, 30);
        bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoEntero.png"), anchoPantalla, altoPantalla, true);
        fondo = new Fondo[2];
        fondo[0] = new Fondo(bitmapFondo, anchoPantalla);
        fondo[1] = new Fondo(bitmapFondo, fondo[0].posicion.x + bitmapFondo.getWidth(), 0);

        bmEnemigo = new Bitmap[2];
        bmEnemigo[0] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/perro1.png"), anchoPantalla / 15, altoPantalla / 15, true);
        bmEnemigo[1] = Bitmap.createScaledBitmap(getBitmapFromAssets("personajes/perro2.png"), anchoPantalla / 15, altoPantalla / 15, true);
        enemigo = new Enemigo(bmEnemigo, anchoPantalla, altoPantalla * 9 / 10 - bmEnemigo[0].getHeight());

        empezarDeCero();
    }

    /**
     * Método actualizar física, se utiliza el de la clase Game y se cambian los parámetros necesarios
     */
    @Override
    public void actualizarFisica() {
        super.actualizarFisica();
    }

    /**
     * Método dibujar, se utiliza el de la clase Game y se añaden cosas
     * Si se supera la fase se consigue el logro de la segunda fase y ya se puede jugar a ella
     *
     * @param c el lienzo donde se dibuja
     */
    @Override
    public void dibujar(Canvas c) {
        super.dibujar(c);
        if (finalPartida) {
            if (ganar && !perder) {
                Escena.segundaFase = true;
                g.saveBool("segundaFase", true);
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
