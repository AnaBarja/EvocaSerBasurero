package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Clase del cubo
 *
 * @author Ana Barja Fernández
 */
public class Cubo {

    /**
     * Posición x e y del cubo
     */
    public PointF posicion;

    /**
     * Bitmap de la imagen del cubo
     */
    public Bitmap imagen;

    /**
     * Hitbox del cubo para que al colisionar con el personaje pueda recoger la basura
     */
    public Rect rectangulo;

    /**
     * Boolean para saber si el cubo está vacio o lleno
     */
    public boolean hayBasura;

    /**
     * Constructor del cubo, se le asigna la hitbox con el setRectangulo
     *
     * @param imagen imagen del Cubo
     * @param x      posición del Cubo en la coordenada x
     * @param y      posición del Cubo en la coordenada y
     */
    public Cubo(Bitmap imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.hayBasura = true;
        setRectangulo();
    }

    /**
     * Hitbox del cubo
     */
    public void setRectangulo() {
        int anchoCubo = imagen.getWidth();
        int altoCubo = imagen.getHeight();
        float x = posicion.x;
        float y = posicion.y;
        rectangulo = new Rect(
                (int) (x),
                (int) y + altoCubo,
                (int) (x + anchoCubo),
                (int) (y));
    }

    /**
     * Método para mantener el cubo en la misma posición cuando se avanza con el camión, se va actualizando la hitbox
     *
     * @param velocidad velocidad a la que se mueve el cubo, la misma que la velocidad del fondo
     */
    public void mantenerCubo(int velocidad) {
        posicion.x += velocidad;
        setRectangulo();
    }

    /**
     * Indicar si el cubo tiene basura o no
     *
     * @param hayBasura indicador de basura
     */
    public void setHayBasura(boolean hayBasura) {
        this.hayBasura = hayBasura;
    }

    /**
     * Obtener la imagen del cubo
     *
     * @return devuelve el bitmap de la imagen
     */
    public Bitmap getImagen() {
        return imagen;
    }

    /**
     * Indicar la imagen del cubo, si está lleno o si está vacío
     *
     * @param imagen bitmap de la imagen del cubo
     */
    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
