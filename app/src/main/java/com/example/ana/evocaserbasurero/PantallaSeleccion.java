package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Pantalla para la selección del nivel que se quiere jugar
 *
 * @author Ana Barja Fernández
 */
public class PantallaSeleccion extends Escena {

    /**
     * Control para gestionar la pulsación de la opción ciudad
     */
    Controles ciudad;

    /**
     * Control para gestionar la pulsación de la opción afueras
     */
    Controles afueras;

    /**
     * String para el texto de selección
     */
    String txtseleccion;

    /**
     * String para el texto de ciudad
     */
    String txtciudad;

    /**
     * String para el texto de afueras
     */
    String txtafueras;

    /**
     * String para el texto de bloqueado si no se ha pasado la primera fase
     */
    String txtBloqueado;

    /**
     * Bitmap de un saco para indicar cuanta basura se necesita para pasar la fase
     */
    Bitmap saco;

    /**
     * Constructor de la pantalla de selección
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public PantallaSeleccion(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        fondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoOpciones.png"), anchoPantalla, altoPantalla, false);

        ciudad = new Controles(bmCuadrado, ancho * 2, alto * 5, bmCuadrado.getWidth(), bmCuadrado.getHeight());

        afueras = new Controles(bmCuadrado, ancho * 11, alto * 10, bmCuadrado.getWidth(), bmCuadrado.getHeight());

        txtseleccion = getContext().getString(R.string.Level);
        txtciudad = getContext().getString(R.string.City);
        txtafueras = getContext().getString(R.string.Suburbs);
        txtBloqueado = getContext().getString(R.string.Blocked);

        saco = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/sacoBasura.png"), altoPantalla / 15, altoPantalla / 15, true);
    }

    /**
     * Método de la rutina de dibujo en el lienzo
     * Se dibujan las dos fases que hay y el mínimo que se necesita para superarlas
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            super.dibujar(c);
            c.drawText(txtseleccion, anchoPantalla / 2, altoPantalla / 6, textoTitulo);

            c.drawBitmap(bmCuadrado, ciudad.posicion.x, ciudad.posicion.y, null);
            c.drawText(txtciudad, ciudad.posicion.x + ciudad.ancho / 2, ciudad.posicion.y + ciudad.alto * 2 / 3, textoGrande);
            c.drawText("-->   30", ancho * 12, ciudad.posicion.y + ciudad.alto * 2 / 3, textoMediano);
            c.drawBitmap(saco, ancho * 14, ciudad.posicion.y + ciudad.alto / 3, null);

            if (Escena.segundaFase) {
                c.drawBitmap(bmCuadrado, afueras.posicion.x, afueras.posicion.y, null);
                c.drawText(txtafueras, afueras.posicion.x + afueras.ancho / 2, afueras.posicion.y + afueras.alto * 2 / 3, textoGrande);
                c.drawText("50       <--", ancho * 8, afueras.posicion.y + afueras.alto * 2 / 3, textoMediano);
                c.drawBitmap(saco, ancho * 7, afueras.posicion.y + afueras.alto / 3, null);
            } else {
                c.drawBitmap(bmCuadrado, afueras.posicion.x, afueras.posicion.y, null);
                c.drawText(txtBloqueado, afueras.posicion.x + afueras.ancho / 2, afueras.posicion.y + afueras.alto * 2 / 3, textoGrande);
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método que detecta la pulsación en la pantalla
     *
     * @param event evento de pulsación sobre la pantalla
     * @return devuelve el id de la Escena
     */
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pulsaBitmap(ciudad, event)) return 10;
                else if (pulsaBitmap(afueras, event) && Escena.segundaFase) return 11;
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;
    }
}
