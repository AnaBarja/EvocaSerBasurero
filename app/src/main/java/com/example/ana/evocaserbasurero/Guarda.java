package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase guarda, guarda y recupera los datos de sharedPreferences
 *
 * @author Ana Barja Fernández
 */
public class Guarda {

    /**
     * Creación de la clase Guarda
     */
    private static Guarda preferencias;

    /**
     * SharedPreferences para guardar los records y opciones del juego
     */
    private SharedPreferences sharedPreferences;

    /**
     * Constructor de la clase Guarda
     *
     * @param context entorno de la aplicación
     */
    private Guarda(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
    }

    /**
     * Método para inicializar la clase Guarda, si es null se crea, si no se devuelve directamente
     *
     * @param context contexto
     * @return devuelve la clase inicializada
     */
    public static Guarda getInstance(Context context) {
        if (preferencias == null) {
            preferencias = new Guarda(context);
        }
        return preferencias;
    }

    /**
     * Método para borrar una KEY del XML
     *
     * @param key clave que se quiere borrar
     */
    public void Borrar(String key) {
        sharedPreferences.edit().remove(key).commit();
    }

    /**
     * Método para guardar booleanas
     *
     * @param key   clave en XML del dato a guardar
     * @param value valor en XML del valor a guardar, true o false
     */
    public void saveBool(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    /**
     * Método para guardar integers
     *
     * @param key   clave en XML del dato a guardar
     * @param value valor en XML del valor a guardar
     */
    public void saveInt(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    /**
     * Método para obtener la booleana guardada
     *
     * @param key clave en XML del dato a obtener
     * @return valor de la clave guardado, false si no existe
     */
    public boolean getBool(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, false);
        }
        return false;
    }

    /**
     * Método para obtener el integer guardado
     *
     * @param key clave en XML del dato a obtener
     * @return valor de la clave guardada, si no hay devuelve 0
     */
    public int getInt(String key) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }
}
