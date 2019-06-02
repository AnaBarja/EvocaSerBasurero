package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase Créditos donde de muestran tanto los datos del juego como los recursos usados
 *
 * @author Ana Barja Fernández
 */
public class Creditos extends Escena {

    /**
     * Array de Fondo para hacer el fondo con scroll vertical
     */
    protected Fondo[] fondo;

    /**
     * Bitmaps con las imágenes de los créditos
     */
    protected Bitmap credito1, credito2, credito3, credito4;

    /**
     * Constructor de la Escena créditos, dependiendo del idioma en la que está la aplicación aparecerán en español o inglés
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla  alto de pantalla
     */
    public Creditos(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        if (Escena.isSpanish) {
            credito1 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/primerCreditoEspanhol.png"), anchoPantalla, altoPantalla, true);
            credito2 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/segundoCreditoEspanhol.png"), anchoPantalla, altoPantalla, true);
            credito3 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/tercerCreditoEspanhol.png"), anchoPantalla, altoPantalla, true);
            credito4 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/creditoDividir.png"), anchoPantalla, altoPantalla, true);
        } else {
            credito1 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/primerCreditoIngles.png"), anchoPantalla, altoPantalla, true);
            credito2 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/segundoCreditoIngles.png"), anchoPantalla, altoPantalla, true);
            credito3 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/tercerCreditoIngles.png"), anchoPantalla, altoPantalla, true);
            credito4 = Bitmap.createScaledBitmap(getBitmapFromAssets("creditos/creditoDividir.png"), anchoPantalla, altoPantalla, true);
        }
        fondo = new Fondo[4];
        fondo[0] = new Fondo(credito1, altoPantalla, true);
        fondo[1] = new Fondo(credito2, 0, fondo[0].posicion.y + credito2.getHeight());
        fondo[2] = new Fondo(credito3, 0, fondo[1].posicion.y + credito3.getHeight());
        fondo[3] = new Fondo(credito4, 0, fondo[2].posicion.y + credito4.getHeight());
    }

    /**
     * Se actualizan las físicas de los elementos en pantalla, los créditos van hacia arriba, cuando acaban vuelven a salir
     */
    public void actualizarFisica() {
        if (fondo[0].posicion.y < 0) {
            fondo[1].posicion.y = fondo[0].posicion.y + fondo[1].imagen.getHeight();
        }

        if (fondo[1].posicion.y < 0) {
            fondo[2].posicion.y = fondo[1].posicion.y + fondo[2].imagen.getHeight();
        }
        if (fondo[2].posicion.y < 0) {
            fondo[3].posicion.y = fondo[2].posicion.y + fondo[3].imagen.getHeight();
        }
        if (fondo[3].posicion.y < 0) {
            fondo[0].posicion.y = fondo[3].posicion.y + fondo[0].imagen.getHeight();
        }
        fondo[0].moverY(altoPantalla / 500);
        fondo[1].moverY(altoPantalla / 500);
        fondo[2].moverY(altoPantalla / 500);
        fondo[3].moverY(altoPantalla / 500);
    }

    /**
     * Rutina de dibujo en el liezo de Escena Creditos, se van dibujando los distintos bitmaps a medida que cambia actualizar física
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            for (int i = 0; i < fondo.length; i++) {
                c.drawBitmap(fondo[i].imagen, fondo[i].posicion.x, fondo[i].posicion.y, null);
            }
            super.dibujar(c);
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para detectar la pulsación de pantalla
     *
     * @param event evento de pulsación en la pantalla
     * @return devuelve el número de escena
     */
    public int onTouchEvent(MotionEvent event) {
        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;
    }
}