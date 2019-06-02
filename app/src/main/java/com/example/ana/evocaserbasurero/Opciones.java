package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Clase opciones donde se puede elegir el sonido, efectos, vibración, idioma y borrar los records
 *
 * @author Ana Barja Fernández
 */
public class Opciones extends Escena {

    /**
     * Bitmap del fondo
     */
    protected Bitmap bitmapFondo;

    /**
     * Bitmap de la vibración activada
     */
    protected Bitmap bmVibracionON;

    /**
     * Bitmap de la vibración desactivada
     */
    protected Bitmap bmVibracionOFF;

    /**
     * Bitmap de la música activada
     */
    protected Bitmap bmMusicaON;

    /**
     * Bitmap de la música desactivada
     */
    protected Bitmap bmMusicaOFF;

    /**
     * Bitmap del idioma español seleccionado
     */
    protected Bitmap bmSP;

    /**
     * Bitmap del idioma ingles seleccionado
     */
    protected Bitmap bmEN;

    /**
     * Bitmap del idioma español deseleccionado
     */
    protected Bitmap bmNoSP;

    /**
     * Bitmap del idioma ingles deseleccionado
     */
    protected Bitmap bmNoEN;

    /**
     * Bitmap de los efectos activados
     */
    protected Bitmap bmEfectosON;

    /**
     * Bitmap de los efectos desactivados
     */
    protected Bitmap bmEfectosOFF;

    /**
     * Bitmap del botón para resetear los records y logros
     */
    protected Bitmap bmReset;

    /**
     * Control para gestionar la pulsación de la música
     */
    protected Controles musica;

    /**
     * Control para gestionar la pulsación de la vibracion
     */
    protected Controles vibracion;

    /**
     * Control para gestionar la pulsación del idioma español
     */
    protected Controles spanish;

    /**
     * Control para gestionar la pulsación del idioma ingles
     */
    protected Controles english;

    /**
     * Control para gestionar la pulsación de los efectos
     */
    protected Controles efectos;

    /**
     * Control para gestionar la pulsación de borrar los records
     */
    protected Controles borrarRecords;

    /**
     * Int para el tamaño de los bitmaps de las opciones
     */
    protected int tamañoControles = altoPantalla * 2 / 10;

    /**
     * String que le pone título a la Escena
     */
    protected String opciones;

    /**
     * Constructor de la clase OPciones
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de la pantalla
     * @param altoPantalla  alto de la pantalla
     */
    public Opciones(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        super(context, idEscena, anchoPantalla, altoPantalla);
        bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("fondos/fondoOpciones.png"), anchoPantalla, altoPantalla, true);
        fondo = Bitmap.createScaledBitmap(bitmapFondo, anchoPantalla, altoPantalla, false);

        bmVibracionON = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/vibracionActivada.png"), tamañoControles, tamañoControles, true);
        bmVibracionOFF = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/vibracionDesactivada.png"), tamañoControles, tamañoControles, true);

        bmMusicaON = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/musicaActivada.png"), tamañoControles, tamañoControles, true);
        bmMusicaOFF = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/musicaDesactivada.png"), tamañoControles, tamañoControles, true);

        bmEfectosON = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/efectosActivados.png"), tamañoControles, tamañoControles, true);
        bmEfectosOFF = bitmapFondo = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/efectosDesactivados.png"), tamañoControles, tamañoControles, true);

        bmSP = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/spanish.png"), tamañoControles, tamañoControles, true);
        bmEN = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/english.png"), tamañoControles, tamañoControles, true);

        bmNoSP = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/spanishNo.png"), tamañoControles, tamañoControles, true);
        bmNoEN = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/englishNo.png"), tamañoControles, tamañoControles, true);


        efectos = new Controles(bmEfectosON, anchoPantalla * 2 / 10, altoPantalla * 3 / 10, bmEfectosOFF.getWidth(), bmEfectosOFF.getHeight());
        musica = new Controles(bmMusicaON, anchoPantalla * 9 / 20, altoPantalla * 3 / 10, bmMusicaON.getWidth(), bmMusicaON.getHeight());
        vibracion = new Controles(bmVibracionON, anchoPantalla * 7 / 10, altoPantalla * 3 / 10, bmVibracionON.getWidth(), bmVibracionON.getHeight());

        spanish = new Controles(bmSP, anchoPantalla * 3 / 10, altoPantalla * 6 / 10, bmSP.getWidth(), bmSP.getHeight());
        english = new Controles(bmEN, anchoPantalla * 6 / 10, altoPantalla * 6 / 10, bmEN.getWidth(), bmEN.getHeight());

        bmReset = Bitmap.createScaledBitmap(getBitmapFromAssets("opciones/resetearRecords.png"), altoPantalla / 10, altoPantalla / 10, true);
        borrarRecords = new Controles(bmSP, anchoPantalla / 15, altoPantalla / 15, bmReset.getWidth(), bmReset.getHeight());
    }


    /**
     * Método que actualiza las físicas del juego, cambio dinámico del título de la Escena dependiendo del idioma
     */
    public void actualizarFisica() {
        opciones = getContext().getString(R.string.opciones);
    }

    /**
     * Rutina de dibujo en el lienzo
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            c.drawBitmap(fondo, 0, 0, null);
            super.dibujar(c);

            c.drawText(opciones, anchoPantalla * 5 / 10, altoPantalla * 2 / 10, textoTitulo);
            c.drawBitmap(bmReset, borrarRecords.posicion.x, borrarRecords.posicion.y, null);

            if (Escena.musica) {
                c.drawBitmap(bmMusicaON, musica.posicion.x, musica.posicion.y, null);
            } else {
                c.drawBitmap(bmMusicaOFF, musica.posicion.x, musica.posicion.y, null);
            }

            if (Escena.vibracion) {
                c.drawBitmap(bmVibracionON, vibracion.posicion.x, vibracion.posicion.y, null);
            } else {
                c.drawBitmap(bmVibracionOFF, vibracion.posicion.x, vibracion.posicion.y, null);
            }

            if (Escena.efectos) {
                c.drawBitmap(bmEfectosON, efectos.posicion.x, efectos.posicion.y, null);
            } else {
                c.drawBitmap(bmEfectosOFF, efectos.posicion.x, efectos.posicion.y, null);
            }

            if (Escena.isSpanish) {
                c.drawBitmap(bmSP, spanish.posicion.x, spanish.posicion.y, null);
                c.drawBitmap(bmNoEN, english.posicion.x, english.posicion.y, null);
            }

            if (!Escena.isSpanish) {
                c.drawBitmap(bmEN, english.posicion.x, english.posicion.y, null);
                c.drawBitmap(bmNoSP, spanish.posicion.x, spanish.posicion.y, null);
            }
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método que detecta la pulsación en la pantalla
     *
     * @param event evento de pulsación de la pantalla
     * @return devuelve el id de la escena
     */
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pulsaBitmap(vibracion, event)) {
                    Escena.vibracion = !Escena.vibracion;
                    g.saveBool("vibracion", Escena.vibracion);
                }
                if (pulsaBitmap(musica, event)) {
                    Escena.musica = !Escena.musica;
                    g.saveBool("musica", Escena.musica);
                    if (!Escena.musica) {
                        Juego.desactivarMusica();
                    } else {
                        Juego.activarMusica(context);
                    }
                }
                if (pulsaBitmap(spanish, event)) {
                    super.changeLanguage("es");
                    Escena.isSpanish = true;
                    g.saveBool("español", true);
                }
                if (pulsaBitmap(english, event)) {
                    super.changeLanguage("en");
                    Escena.isSpanish = false;
                    g.saveBool("español", false);
                }
                if (pulsaBitmap(efectos, event)) {
                    Escena.efectos = !Escena.efectos;
                    g.saveBool("efectos", Escena.efectos);
                }
                if (pulsaBitmap(borrarRecords, event)) {
                    g.Borrar("segundaFase");
                    g.Borrar("profesional");
                    g.Borrar("record");
                    g.Borrar("experto");
                    if (Escena.isSpanish) {
                        Toast.makeText(context, "Récords y logros reseteados", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Achievements and records reset", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }

        int idPadre = super.onTouchEvent(event);
        if (idPadre != idEscena) return idPadre;
        return idEscena;
    }
}