package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Clase fondo para poner los fondos a las distintas escenas que usan scroll
 *
 * @author Ana Barja Fernández
 */
public class Fondo {

    /**
     * Posición x e y del fondo
     */
    public PointF posicion;

    /**
     * Bitmap del fondo
     */
    public Bitmap imagen;

    /**
     * Primer fondo de pantalla para el scroll
     *
     * @param imagen bitmap de la imagen de fondo
     * @param x      coordenada x de la posición del fondo
     * @param y      coordenada y de la posición del fondo
     */
    public Fondo(Bitmap imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
    }

    /**
     * Siguientes fondos de pantalla para el scroll en horizontal
     *
     * @param imagen        bitmap de la imagen del fondo
     * @param anchoPantalla ancho de pantalla para saber donde empezar a aparecer
     */
    public Fondo(Bitmap imagen, int anchoPantalla) {
        this(imagen, anchoPantalla - imagen.getWidth(), 0);
    }

    /**
     * Siguientes fondos de pantalla para el scroll en vertical
     *
     * @param imagen       bitmap de la imagen del fondo
     * @param altoPantalla alto de la pantalla para saber donde empezar a aparecer
     * @param x            boolean para diferenciarlo del otro constructor de Fondo
     */
    public Fondo(Bitmap imagen, int altoPantalla, boolean x) {
        this(imagen, 0, altoPantalla - imagen.getHeight());
    }

    /**
     * Método para mover el fondo en la coordenada x
     *
     * @param velocidad velocidad a la que se mueven los fondos
     */
    public void mover(int velocidad) {
        posicion.x += velocidad;
    }

    /**
     * Método para mover el fondo en la coordenada y
     *
     * @param velocidad velocidad a la que se mueven los fondos
     */
    public void moverY(int velocidad) {
        posicion.y -= velocidad;
    }
}
