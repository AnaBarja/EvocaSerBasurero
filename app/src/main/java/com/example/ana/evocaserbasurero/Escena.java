package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Clase Escena, es de donde heredan el resto
 *
 * @author Ana Barja Fernández
 */
public class Escena {

    /**
     * Entorno de la aplicación
     */
    Context context;

    /**
     * Int para saber el id de la Escena
     */
    public int idEscena;

    /**
     * Int del ancho de pantalla reducido para dibujar los botones con más facilidad
     */
    public int ancho;

    /**
     * Int del alto de pantalla reducido para dibujar los botones con más facilidad
     */
    public int alto;

    /**
     * Int del ancho de pantalla
     */
    public int anchoPantalla;

    /**
     * Int del alto de pantalla
     */
    public int altoPantalla;

    /**
     * Bitmap para dibujar el fondo de pantalla
     */
    Bitmap fondo;

    /**
     * Bitmap para dibujar a flecha para ir hacia atrás
     */
    Bitmap bmFlechaAtras;

    /**
     * Bitmap para dibujar el cuadrado para dibujar debajo de los textos
     */
    Bitmap bmCuadrado;

    /**
     * Paint para dibujar un texto con tamaño grande
     */
    Paint textoGrande;

    /**
     * Paint para dibujar un texto con tamaño mediano
     */
    Paint textoMediano;

    /**
     * Paint para dibujar el texto del título
     */
    Paint textoTitulo;

    /**
     * Control para controlar si se pulsa la fecha hacia atrás
     */
    Controles flechaAtras;

    /**
     * Booleana para guardar si se pasa la primera fase
     */
    public static boolean segundaFase;

    /**
     * Booleana para guardar si se pasa la segunda fase
     */
    public static boolean profesional;

    /**
     * Booleana para guardar si la vibración está activada
     */
    public static boolean vibracion;

    /**
     * Booleana para guardar si la música está activada
     */
    public static boolean musica;

    /**
     * Booleana para guardar si los efectos están activados
     */
    public static boolean efectos;

    /**
     * Booleana para guardar si la app está en español o inglés
     */
    public static boolean isSpanish;

    /**
     * Booleana para guardar si ha recogido más de 100 piezas en una partida
     */
    public static boolean experto;

    /**
     * Creación de Guarda para guardar las shared preferences
     */
    public static Guarda g;

    /**
     * Int para guardar el record de mayor basura recogida en una partida
     */
    public static int record = 0;

    /**
     * Constructor de la clase Escena
     * Se crean los distintos tipos de textos (diferentes tamaños y colores), se obtienen los datos guardados en SharedPreferences, se crea
     * una flecha atrás común a todas las escenas
     *
     * @param context       entorno de la aplicación
     * @param idEscena      id de la Escena
     * @param anchoPantalla ancho de pantalla
     * @param altoPantalla  alto de pantalal
     */
    public Escena(Context context, int idEscena, int anchoPantalla, int altoPantalla) {
        this.context = context;
        this.idEscena = idEscena;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        alto = altoPantalla / 20;
        ancho = anchoPantalla / 20;

        Typeface faw = Typeface.createFromAsset(context.getAssets(), "fonts/fontGuerrilla.ttf");

        textoGrande = new Paint();
        textoGrande.setColor(Color.BLACK);
        textoGrande.setTextAlign(Paint.Align.CENTER);
        textoGrande.setTextSize(altoPantalla / 8);
        textoGrande.setTypeface(faw);

        textoTitulo = new Paint();
        textoTitulo.setColor(Color.WHITE);
        textoTitulo.setTextAlign(Paint.Align.CENTER);
        textoTitulo.setTextSize(altoPantalla / 6);
        textoTitulo.setTypeface(faw);

        textoMediano = new Paint();
        textoMediano.setColor(Color.WHITE);
        textoMediano.setTextAlign(Paint.Align.CENTER);
        textoMediano.setTextSize(altoPantalla / 10);
        textoMediano.setTypeface(faw);

        bmFlechaAtras = Bitmap.createScaledBitmap(getBitmapFromAssets("controles/flechaAtras.png"), anchoPantalla / 15, altoPantalla / 15, true);
        flechaAtras = new Controles(bmFlechaAtras, anchoPantalla - anchoPantalla / 15, 0, bmFlechaAtras.getWidth(), bmFlechaAtras.getHeight());

        bmCuadrado = Bitmap.createScaledBitmap(getBitmapFromAssets("elementos/cuadro.png"), ancho * 7, alto * 4, true);

        isSpanish = g.getBool("español");
        if (isSpanish)
            changeLanguage("es");
        else
            changeLanguage("en");

        segundaFase = g.getBool("segundaFase");
        record = g.getInt("record");
        profesional = g.getBool("profesional");
        experto = g.getBool("experto");
        vibracion = g.getBool("vibracion");
    }

    /**
     * Método para clonar un Rect para usar en las comprobaciones de las colisiones
     *
     * @param r rectangulo que se quiere clonar
     * @return devuelve un nuevo Rect con las mismas posiciones que el Rect pasado
     */
    public Rect clonaRect(Rect r) {
        return new Rect(r.left, r.top, r.right, r.bottom);
    }

    /**
     * Método para actualizar las físicas del juego
     */
    public void actualizarFisica() {

    }

    /**
     * Establece el idioma del juego
     *
     * @param languageCode codigo del lenguaje
     */
    public void changeLanguage(String languageCode) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(languageCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    /**
     * Rutina de dibujo en el lienzo
     *
     * @param c el lienzo donde se dibuja
     */
    public void dibujar(Canvas c) {
        try {
            if (idEscena != 0 && idEscena != 10 && idEscena != 11)
                c.drawBitmap(bmFlechaAtras, flechaAtras.posicion.x, flechaAtras.posicion.y, null);
        } catch (Exception e) {
            Log.i("Error al dibujar", e.getLocalizedMessage());
        }
    }

    /**
     * Método para conseguir las imagenes de las carpeta assets
     *
     * @param fichero string del nombre de la imagen
     * @return devuelve null si se produce excepción, si no, devuelve la imagen
     */
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Método para detectar la pulsación en la pantalla
     *
     * @param event evento de pulsación sobre la pantalla
     * @return devuelve la id de la Escena
     */
    public int onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        switch (accion) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (pulsaBitmap(flechaAtras, event) && idEscena != 0 && idEscena != 10 && idEscena != 11)
                    return 0;
                break;
            default:
                Log.i("Otra acción", "Acción no definida: " + accion);
        }
        return idEscena;
    }

    /**
     * Método para detectar si se ha pulsado sobre un bitmap asociado a un Control
     *
     * @param control control que se quiere comprobar si ha sido pulsado
     * @param event   evento de pulsación
     * @return devuelve true si ha sido pulsado, false si no ha sido pulsado
     */
    public boolean pulsaBitmap(Controles control, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (x >= control.posicion.x && x < (control.posicion.x + control.getAncho())
                && y >= control.posicion.y && y < (control.posicion.y + control.getAlto())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtener el contexto
     *
     * @return devuelve el contexto obtenido
     */
    public Context getContext() {
        return context;
    }

    /**
     * Indicar el contexto
     *
     * @param context contexto que se quiere
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Obtener el id de la Escena
     *
     * @return devuelve el id de la Escena
     */
    public int getIdEscena() {
        return idEscena;
    }

    /**
     * Indicar el id de la Escena
     *
     * @param idEscena id de la Escena que se quiere
     */
    public void setIdEscena(int idEscena) {
        this.idEscena = idEscena;
    }

    /**
     * Obtener el ancho de pantala
     *
     * @return devuelve el ancho de pantala
     */
    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    /**
     * Indicar el ancho de pantalla
     *
     * @param anchoPantalla ancho de pantalla que se quiere
     */
    public void setAnchoPantalla(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
    }

    /**
     * Obtener el alto de pantala
     *
     * @return devuelve el alto de pantala
     */
    public int getAltoPantalla() {
        return altoPantalla;
    }

    /**
     * Indicar el alto de pantalla
     *
     * @param altoPantalla alto de pantalla que se quiere
     */
    public void setAltoPantalla(int altoPantalla) {
        this.altoPantalla = altoPantalla;
    }

    /**
     * Obtener el fondo de la Escena
     *
     * @return devuelve el fondo de la Escena
     */
    public Bitmap getFondo() {
        return fondo;
    }

    /**
     * Indicar el fondo de la Escena
     *
     * @param fondo fondo que se quiere
     */
    public void setFondo(Bitmap fondo) {
        this.fondo = fondo;
    }

}

