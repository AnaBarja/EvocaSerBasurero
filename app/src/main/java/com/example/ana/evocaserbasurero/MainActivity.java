package com.example.ana.evocaserbasurero;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Clase MainActivity, es la clase con la que se inicia el juego
 *
 * @author Ana Barja Fernández
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método donde se inicia la clase Juego, se pone la pantalla activa y se pone en pantalla completa
     *
     * @param savedInstanceState el último estado guardado
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        try {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {

        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Juego juego = new Juego(this);
        juego.setKeepScreenOn(true);
        setContentView(juego);
    }

    /**
     * Método cuando se inicia la actividad, se vuelve a poner en pantalla completa
     */
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int opciones = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * Método cuando se vuelve a iniciar la actividad, si la música estaba activada se vuelve a iniciar
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (Escena.musica) Juego.activarMusica(this);
    }

    /**
     * Método cuando se pausa la actividad, se desactiva la música, si se pausa justo cuando el camión estaba avanzando se para también el sonido
     */
    @Override
    protected void onPause() {
        super.onPause();
        Juego.desactivarMusica();
        if (Juego.conduciendo) {
            Juego.pararCamion();
            Juego.conduciendo = false;
        }
    }

    /**
     * Método cuando se destruye la actividad, se desactiva la música y el ruido del camión
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Juego.desactivarMusica();
        if (Juego.conduciendo) {
            Juego.pararCamion();
            Juego.conduciendo = false;
        }
    }

    /**
     * Método cuando se para la actividad, se desactiva la música y el ruido del camión
     */
    @Override
    protected void onStop() {
        super.onStop();
        Juego.desactivarMusica();
        if (Juego.conduciendo) {
            Juego.pararCamion();
            Juego.conduciendo = false;
        }
    }

    /**
     * Método para evitar que se salga de la aplicación al pulsar la fecha hacia atrás del móvil
     */
    @Override
    public void onBackPressed() {
        // para evitar salir de la aplicación
    }
}
