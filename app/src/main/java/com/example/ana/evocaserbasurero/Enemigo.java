package com.example.ana.evocaserbasurero;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Clase del enemigo
 *
 * @author Ana Barja Fernández
 */
public class Enemigo {

    /**
     * Posición x e y del enemigo
     */
    public PointF posicion;

    /**
     * Array de bitmaps con las imágenes del enemigo
     */
    public Bitmap[] imagen;

    /**
     * Hitbox del enemigo
     */
    public Rect rectangulo;

    /**
     * Boolean para saber si el enemigo está parado (ha atrapado al personaje) o no
     */
    public boolean parado;

    /**
     * Boolean para saber si la hitbox está activada o no, activada cuando entra en la pantalla, a false cuando se suelta el personaje
     */
    public boolean hitbox;

    /**
     * Tiempo que tiene cada frame para que el enemigo sea dinámico
     */
    int tiempoFrame = 100;

    /**
     * Auxiliar para guardar los milisegundo y poder realizar el cambio de imágenes en el tiempoFrame indicado
     */
    long tFrameAuxm = 0;

    /**
     * Índice del array para ir cambiando la imagen
     */
    int indice = 0;

    /**
     * Constructor del enemigo
     *
     * @param imagen imagen del enemigo
     * @param x      posición del enemigo en la coordenada x
     * @param y      posición del enemigo en la coordenada y
     */
    public Enemigo(Bitmap[] imagen, float x, float y) {
        this.imagen = imagen;
        this.posicion = new PointF(x, y);
        setHitbox(true);
        setRectangulo();
    }

    /**
     * Método para dibujar al enemigo
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibuja(Canvas c) {
        c.drawBitmap(imagen[indice], posicion.x, posicion.y, null);
    }

    /**
     * Método para cambiar los frames del enemigo y ser dinámico
     */
    public void cambiaFrame() {
        if (System.currentTimeMillis() - tFrameAuxm > tiempoFrame) {
            indice++;
            if (indice >= imagen.length) indice = 0;
            tFrameAuxm = System.currentTimeMillis();
        }
    }

    /**
     * Método para mover al enemigo
     *
     * @param velocidad velocidad a la que se mueve el enemigo
     */
    public void moverEnemigo(int velocidad) {
        if (!isParado()) {
            posicion.x -= velocidad;
            setRectangulo();
        }
    }

    /**
     * Htbox del enemigo
     */
    public void setRectangulo() {
        if (isHitbox()) {
            int anchoEnemigo = imagen[0].getWidth();
            int altoEnemigo = imagen[0].getHeight();
            float x = posicion.x;
            float y = posicion.y;
            rectangulo = new Rect(
                    (int) (x),
                    (int) y + altoEnemigo,
                    (int) (x + anchoEnemigo),
                    (int) (y));
        }
    }

    /**
     * Obtiene si el enemigo está parado o no
     *
     * @return devuelve si el enemigo está parado (ha atrapado al personaje) o no
     */
    public boolean isParado() {
        return parado;
    }

    /**
     * Indica si el enemigo está parado
     *
     * @param parado true cuando atrapa al personaje, false si no
     */
    public void setParado(boolean parado) {
        this.parado = parado;
    }

    /**
     * Obtiene si el enemigo tiene hitbox o no
     *
     * @return devuelve si cuando el personaje se acaba de soltar
     */
    public boolean isHitbox() {
        return hitbox;
    }

    /**
     * Indica si el enemigo tiene hitbox o no
     *
     * @param hitbox true si se le pone hitbox (al aparecer), false si se le quita (se acaba de soltar el personaje)
     */
    public void setHitbox(boolean hitbox) {
        this.hitbox = hitbox;
    }
}
