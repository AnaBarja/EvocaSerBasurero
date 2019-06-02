package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Clase del camión
 *
 * @author Ana Barja Fernández
 */
public class Camion {

    /**
     * Posición x e y del camión
     */
    public PointF posicion;

    /**
     * Bitmap del dibujo del camión
     */
    public Bitmap imagen;

    /**
     * Dos rectángulos para las hitbox del camión, uno detŕas para tirar la basura y otro delante para poder subir
     */
    public Rect[] rectangulos = new Rect[2];

    /**
     * Constructor del camión, se le asocia la hitbox del método setRectangulos
     *
     * @param imagen imagen del camion
     * @param x      posicion del camión en la coordenada x
     * @param y      posicion del camión en la coordenada y
     */
    public Camion(Bitmap imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.setRectangulos();
    }

    /**
     * Hitbox del camión dividido en dos rectangulos, uno para subir y otro para tirar la basura
     */
    public void setRectangulos() {
        int anchoCamion = imagen.getWidth();
        int altoCamion = imagen.getHeight();
        float x = posicion.x;
        float y = posicion.y;
        rectangulos[0] = new Rect(
                (int) (x + anchoCamion * 2 / 3),
                (int) y - altoCamion / 6,
                (int) (x + anchoCamion),
                (int) (y + altoCamion));
        rectangulos[1] = new Rect(
                (int) x,
                (int) (y - altoCamion / 6),
                (int) (x + anchoCamion * 2 / 3),
                (int) (y + altoCamion));
    }
}
