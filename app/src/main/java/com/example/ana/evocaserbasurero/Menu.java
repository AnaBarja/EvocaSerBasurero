package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Clase menú, es la clase que une todas las escenas para poder acceder a una u otra
 *
 * @author Ana Barja Fernández
 */
public class Menu extends Escena {

    /**
     * Control para gestionar la pulsación de la opción ayuda
     */
    Controles ayuda;

    /**
     * Control para gestionar la pulsación de la opción opciones
     */
    Controles opciones;

    /**
     * Control para gestionar la pulsación de la opción jugar
     */
    Controles juego;

    /**
     * Control para gestionar la pulsación de la opción records
     */
    Controles records;

    /**
     * Control para gestionar la pulsación de la opción créditos
     */
    Controles creditos;

    /**
     * String para el título de jugar
     */
    String textJugar;

    /**
     * String para el título de ayuda
     */
    String textAyuda;

    /**
     * String para el título de opciones
     */
    String textOpciones;

    /**
     * String para el título de creditos
     */
    String textCreditos;

    /**
     * String para el título de records
     */
    String textRecords;

    /**
     * String para el título de titulo
     */
    String textTitulo;

    /**
     * Array de Fondo para que el fondo haga scroll en horizontal
     */
    Fondo[] fondo;

    /**
     * Bitmap para dibujar el fondo
     */
    Bitmap bitmapFondo;

    /**
     * Constructor del menú
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Menu(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoEntero.png"), anchoPantalla, altoPantalla, true);
        fondo = new Fondo[2];
        fondo[0] = new Fondo(bitmapFondo, anchoPantalla);
        fondo[1] = new Fondo(bitmapFondo, fondo[0].posicion.x + bitmapFondo.getWidth(), 0);

        juego = new Controles(bmCuadrado, ancho * 2, alto * 5, bmCuadrado.getWidth(), bmCuadrado.getHeight());
        opciones = new Controles(bmCuadrado, ancho * 11, alto * 5, bmCuadrado.getWidth(), bmCuadrado.getHeight());

        ayuda = new Controles(bmCuadrado, ancho * 11, alto * 10, bmCuadrado.getWidth(), bmCuadrado.getHeight());
        records = new Controles(bmCuadrado, ancho * 2, alto * 10, bmCuadrado.getWidth(), bmCuadrado.getHeight());

        creditos = new Controles(bmCuadrado, ancho * 13 / 2, alto * 15, bmCuadrado.getWidth(), bmCuadrado.getHeight());

        textJugar = getContext().getString(R.string.jugar);
        textAyuda = getContext().getString(R.string.ayuda);
        textOpciones = getContext().getString(R.string.opciones);
        textCreditos = getContext().getString(R.string.creditos);
        textRecords = getContext().getString(R.string.records);
        textTitulo = getContext().getString(R.string.app_name);
    }

    /**
     * Método para actualizar la física de los elementos de la pantalla
     * Hace scroll del fondo
     */
    public void actualizarFisica() {
        fondo[0].mover(-anchoPantalla / 200);
        fondo[1].mover(-anchoPantalla / 200);
        if (fondo[0].posicion.x < 0) {
            fondo[1].posicion.x = fondo[0].posicion.x + fondo[1].imagen.getWidth();
        }
        if (fondo[1].posicion.x < 0) {
            fondo[0].posicion.x = fondo[1].posicion.x + fondo[0].imagen.getWidth();
        }

    }

    /**
     * Método de la rutina de dibujo en el lienzo
     * Se dibujan el título de la aplicación, y las opciones de Jugar, Opciones, Ayuda, Records y Créditos
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            for (int i = 0; i < fondo.length; i++) {
                c.drawBitmap(fondo[i].imagen, fondo[i].posicion.x, fondo[i].posicion.y, null);
            }
            super.dibujar(c);
            c.drawText(textTitulo, anchoPantalla / 2, altoPantalla / 6, textoTitulo);

            c.drawBitmap(bmCuadrado, juego.posicion.x, juego.posicion.y, null);
            c.drawText(textJugar, juego.posicion.x + juego.ancho / 2, juego.posicion.y + juego.alto * 2 / 3, textoGrande);

            c.drawBitmap(bmCuadrado, ayuda.posicion.x, ayuda.posicion.y, null);
            c.drawText(textAyuda, ayuda.posicion.x + ayuda.ancho / 2, ayuda.posicion.y + ayuda.alto * 2 / 3, textoGrande);

            c.drawBitmap(bmCuadrado, opciones.posicion.x, opciones.posicion.y, null);
            c.drawText(textOpciones, opciones.posicion.x + opciones.ancho / 2, opciones.posicion.y + opciones.alto * 2 / 3, textoGrande);

            c.drawBitmap(bmCuadrado, records.posicion.x, records.posicion.y, null);
            c.drawText(textRecords, records.posicion.x + records.ancho / 2, records.posicion.y + records.alto * 2 / 3, textoGrande);

            c.drawBitmap(bmCuadrado, creditos.posicion.x, creditos.posicion.y, null);
            c.drawText(textCreditos, creditos.posicion.x + creditos.ancho / 2, creditos.posicion.y + creditos.alto * 2 / 3, textoGrande);
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
                if (pulsaBitmap(juego, event)) return 1;
                else if (pulsaBitmap(opciones, event)) return 99;
                else if (pulsaBitmap(records, event)) return 98;
                else if (pulsaBitmap(ayuda, event)) return 97;
                else if (pulsaBitmap(creditos, event)) return 96;
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }
        return idEscena;
    }
}
