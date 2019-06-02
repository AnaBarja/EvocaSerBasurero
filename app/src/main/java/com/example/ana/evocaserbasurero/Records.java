package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase de récords, muestra el récord de basura y logros
 *
 * @author Ana Barja Fernández
 */
public class Records extends Escena {

    /**
     * Bitmap del logro de la primera fase pasada
     */
    private Bitmap logroSegunda;

    /**
     * Bitmap del logro de la primera fase sin pasar
     */
    private Bitmap nologroSegunda;

    /**
     * Bitmap del logro de la segunda fase sin pasar
     */
    private Bitmap noProfesional;

    /**
     * Bitmap del logro de la segunda fase pasada
     */
    private Bitmap logroProfesional;

    /**
     * Bitmap del logro de conseguir 100 piezas en un partida pasado
     */
    private Bitmap logroExperto;

    /**
     * Bitmap del logro de conseguir 100 piezas en un partida sin pasar
     */
    private Bitmap noExperto;

    /**
     * String para el título de records
     */
    private String records;

    /**
     * String para el título de logros
     */
    private String logros;

    /**
     * String para el número máximo de basura recogida en una partida
     */
    private String record;

    /**
     * Constructor de la clase Records
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Records(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        fondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoOpciones.png"), anchoPantalla, altoPantalla, false);

        record = getContext().getString(R.string.Record);
        records = getContext().getString(R.string.records);
        logros = getContext().getString(R.string.Achievements);

        logroSegunda = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/logroSegundaPartida.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);
        nologroSegunda = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/nologroSegundaPartida.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);

        logroProfesional = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/profesional.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);
        noProfesional = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/noProfesional.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);

        logroExperto = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/experto.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);
        noExperto = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/noExperto.png"), altoPantalla * 2 / 10, altoPantalla * 2 / 10, false);

    }

    /**
     * Método de rutina de dibujo en el lienzo
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            super.dibujar(c);
            c.drawText(records, anchoPantalla * 5 / 10, altoPantalla * 2 / 10, textoTitulo);
            c.drawText(record + " " + Escena.record, anchoPantalla * 3 / 10, altoPantalla * 4 / 10, textoMediano);
            c.drawText(logros, anchoPantalla * 5 / 10, altoPantalla * 6 / 10, textoTitulo);
            if (Escena.segundaFase) {
                c.drawBitmap(logroSegunda, anchoPantalla * 2 / 10, altoPantalla * 7 / 10, null);
            } else {
                c.drawBitmap(nologroSegunda, anchoPantalla * 2 / 10, altoPantalla * 7 / 10, null);
            }

            if (Escena.profesional) {
                c.drawBitmap(logroProfesional, anchoPantalla * 9 / 20, altoPantalla * 7 / 10, null);
            } else {
                c.drawBitmap(noProfesional, anchoPantalla * 9 / 20, altoPantalla * 7 / 10, null);
            }

            if (Escena.experto) {
                c.drawBitmap(logroExperto, anchoPantalla * 7 / 10, altoPantalla * 7 / 10, null);
            } else {
                c.drawBitmap(noExperto, anchoPantalla * 7 / 10, altoPantalla * 7 / 10, null);
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para detectar la pulsación en la pantalla
     *
     * @param event evento de pulsación
     * @return devuelve el id de la Escena
     */
    public int onTouchEvent(MotionEvent event) {
        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;
    }
}