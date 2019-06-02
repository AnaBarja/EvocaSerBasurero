package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Clase del personaje
 *
 * @author Ana Barja Fernández
 */
public class Personaje {

    /**
     * Posición x e y del Personaje
     */
    public PointF posicion;

    /**
     * Array de bitmaps con las imágenes del enemigo
     */
    public Bitmap[] imagen;

    /**
     * Rect para indicar la hitbox del personaje, una superior para tirar la basura y subir al camión, y la inferior para recoger la basura y que le
     * atrape el enemigo
     */
    public Rect rectangulo[] = new Rect[2];

    /**
     * Boolean para saber si el personaje va hacia la derecha o hacia la izquierda
     */
    boolean derecha;

    /**
     * Boolean para saber si el personaje se está moviendo o no
     */
    boolean moviendo;

    /**
     * Tiempo que tiene cada frame para que el enemigo sea dinámico
     */
    int tiempoFrame = 100;

    /**
     * Auxiliar para guarda los milisegundoS y poder realizar el cambio de imágenes en el tiempoFrame indicado
     */
    long tFrameAuxm = 0;

    /**
     * Imagen que está puesta
     */
    int indice = 0;

    /**
     * Constructor del personaje
     *
     * @param imagen imagen del personaje
     * @param x      posicion del personaje en la coordenada x
     * @param y      posicion del personaje en la coordenada y
     */
    public Personaje(Bitmap[] imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        this.setRectangulos();
    }

    /**
     * Método para dibujar el personaje, dependiendo de si va a la derecha o a la izquierda
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibuja(Canvas c) {
        if (derecha) {
            c.drawBitmap(imagen[indice], posicion.x, posicion.y, null);
        } else {
            c.drawBitmap(espejo(imagen[indice], true), posicion.x, posicion.y, null);
        }
    }

    /**
     * Método para darle la vuelta a las imágenes
     *
     * @param imagen     imagen a la que se le quiere dar la vuelta
     * @param horizontal boolean para indicar en que sentido se le quiere dar la vuelta
     * @return devuelve el bitmap nuevo que se ha creado
     */
    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(), imagen.getHeight(), matrix, false);
    }

    /**
     * Método para cambiar los frames y que el personaje sea dinámico
     */
    public void cambiaFrame() {
        if (moviendo)
            if (System.currentTimeMillis() - tFrameAuxm > tiempoFrame) {
                indice++;
                if (indice >= imagen.length) indice = 0;
                tFrameAuxm = System.currentTimeMillis();
            }
    }

    /**
     * Indica la posición al personaje
     *
     * @param posicion posición que le se asigna
     */
    public void setPosicion(float posicion) {
        this.posicion.x = posicion;
        this.setRectangulos();
    }

    /**
     * Método para hacer andar al personaje a la derecha
     *
     * @param ancho     ancho de la pantalla
     * @param velocidad velocidad a la que se mueve el personaje
     */
    public void andarDerecha(int ancho, int velocidad) {
        if (posicion.x + imagen[0].getWidth() < ancho) {
            posicion.x += velocidad;
            derecha = true;
            this.setRectangulos();
        }
    }

    /**
     * Método para hacer andar al personaje a la izquierda
     *
     * @param velocidad velocidad a la que se mueve el personaje
     */
    public void andarIzquierda(int velocidad) {
        if (posicion.x > 0) {
            posicion.x -= velocidad;
            derecha = false;
            this.setRectangulos();
        }
    }

    /**
     * Hitbox del personaje, son dos rectangulos, uno para recoger la basura y colisión con el enemigo y otro para tirar la basura y subir al camión
     */
    public void setRectangulos() {
        int anchoPersonaje = imagen[0].getWidth();
        int altoPersonaje = imagen[0].getHeight();
        float x = posicion.x;
        float y = posicion.y;
        rectangulo[0] = new Rect(
                (int) (x),
                (int) (y + altoPersonaje / 3),
                (int) (x + anchoPersonaje),
                (int) (y));
        rectangulo[1] = new Rect(
                (int) (x),
                (int) (y + altoPersonaje / 3),
                (int) (x + anchoPersonaje),
                (int) (y + altoPersonaje / 2) * 2);
    }
}
