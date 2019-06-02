package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase ayuda en la que se enseña la historia, como se juega y la finalidad del juego
 *
 * @author Ana Barja Fernández
 */
public class Ayuda extends Escena {

    /**
     * Array de bitmaps con las imágenes de las instrucciones
     */
    protected Bitmap[] ayuda;

    /**
     * Int para pasar por cada imagen del array
     */
    protected int numero = 0;

    /**
     * Constructor de la Escena ayuda, dependiendo de si el juego está en español o inglés cambia el idioma de las imágenes
     *
     * @param context       entorno de la aplicación
     * @param idEscena      Id de la escena en la que estamos
     * @param anchoPantalla ancho de la pantalla en dps
     * @param altoPantalla  alto de la pantalla en dps
     */
    public Ayuda(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        ayuda = new Bitmap[5];

        if (Escena.isSpanish) {
            ayuda[0] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/inicioES.png"), anchoPantalla, altoPantalla, true);
            ayuda[1] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/recogerES.png"), anchoPantalla, altoPantalla, true);
            ayuda[2] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/tirarES.png"), anchoPantalla, altoPantalla, true);
            ayuda[3] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/perroAtrapaES.png"), anchoPantalla, altoPantalla, true);
            ayuda[4] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/instruccionesES.png"), anchoPantalla, altoPantalla, true);
        } else {
            ayuda[0] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/inicioEN.png"), anchoPantalla, altoPantalla, true);
            ayuda[1] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/recogerEN.png"), anchoPantalla, altoPantalla, true);
            ayuda[2] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/tirarEN.png"), anchoPantalla, altoPantalla, true);
            ayuda[3] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/perroAtrapaEN.png"), anchoPantalla, altoPantalla, true);
            ayuda[4] = Bitmap.createScaledBitmap(getBitmapFromAssets("ayuda/instruccionesEN.png"), anchoPantalla, altoPantalla, true);
        }
    }

    /**
     * Rutina de dibujo en el liezo de Escena Ayuda, por cada click en pantalla dibuja una ayuda distinta
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(ayuda[numero], 0, 0, null);
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para detectar la pulsación de la pantalla, por cada pulsación sube el número para cambiar de imagen en el array de bitmap
     *
     * @param event evento de pulsación en la pantalla
     * @return devuelve el número de escena
     */
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!pulsaBitmap(flechaAtras, event)) {
                    numero++;
                    if (numero == ayuda.length) {
                        numero = 0;
                    }
                }
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;
    }
}