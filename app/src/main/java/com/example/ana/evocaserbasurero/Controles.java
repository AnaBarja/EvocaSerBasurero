package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Clase controles para poder usar los bitmaps como "botones"
 *
 * @author Ana Barja Fernández
 */
public class Controles {

    /**
     * Posición x e y del control
     */
    public PointF posicion;

    /**
     * Bitmap asociado al control
     */
    public Bitmap imagen;

    /**
     * Ancho de la imagen del control
     */
    public float ancho;

    /**
     * Alto de la imagen del control
     */
    public float alto;

    /**
     * Controles que aparecen en la pantalla con la función de ser un botón
     *
     * @param imagen bitmap del control a dibujar
     * @param x      posición x del control
     * @param y      posicion y del control
     * @param ancho  ancho del control
     * @param alto   alto del control
     */
    public Controles(Bitmap imagen, float x, float y, float ancho, float alto) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.ancho = ancho;
        this.alto = alto;
    }

    /**
     * Obtener el ancho del control
     *
     * @return el ancho del control
     */
    public float getAncho() {
        return ancho;
    }

    /**
     * Indicar el ancho del control
     *
     * @param ancho del control
     */
    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    /**
     * Obtener el alto del control
     *
     * @return el alto del control
     */
    public float getAlto() {
        return alto;
    }

    /**
     * Indicar el alto del control
     *
     * @param alto del control
     */
    public void setAlto(float alto) {
        this.alto = alto;
    }
}
