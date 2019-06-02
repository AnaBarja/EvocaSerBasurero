package com.example.ana.evocaserbasurero;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Clase juego, es la que aparece al iniciar la actividad
 */
public class Juego extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Superficie de visualización
     */
    private SurfaceHolder surfaceHolder;

    /**
     * Entorno de la aplicación
     */
    private Context context;

    /**
     * Int para obtener el ancho de pantalla en el surfaceChange
     */
    private int anchoPantalla = 1;

    /**
     * Int para obtener el alto de pantalla en el surfaceChange
     */
    private int altoPantalla = 1;

    /**
     * Hilo para dibujar y actualizar la física del juego
     */
    private Hilo hilo;

    /**
     * Boolean para controlar el hilo
     */
    private boolean funcionando = false;

    /**
     * Creación de la clase Escena para saber en qué escena se está
     */
    Escena escenaActual;

    /**
     * MediaPlayer para implementar música
     */
    public static MediaPlayer mediaPlayer;

    /**
     * AudioManager para implementar el sonido
     */
    public static AudioManager audioManager;

    /**
     * SoundPool para los efectos de sonido
     */
    protected static SoundPool efectos;

    /**
     * Int para guardar el sonido del camión
     */
    protected static int sonidoCamion;

    /**
     * Int para guardar el sonido de la puerta
     */
    protected static int sonidoPuerta;

    /**
     * Int para guardar el sonido de la basura
     */
    protected static int sonidoBasura;

    /**
     * Int para indicar el número maximo de sonidos simultaneos que puede haber
     */
    final static protected int maxSonidosSimultaneos = 5;

    /**
     * Int para indicar el volumen del sonido
     */
    static int vol;

    /**
     * Boolean para controlar el sonido del camión
     */
    static boolean conduciendo;

    /**
     * Constructor de la clase Juego
     *
     * @param context entorno de la aplicación
     */
    public Juego(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
        this.context = context;
        hilo = new Hilo();
        setFocusable(true);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if ((android.os.Build.VERSION.SDK_INT) >= 21) {
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
            spb.setMaxStreams(maxSonidosSimultaneos);
            efectos = spb.build();
        } else {
            efectos = new SoundPool(maxSonidosSimultaneos, AudioManager.STREAM_MUSIC, 0);
        }

        sonidoCamion = efectos.load(context, R.raw.camion, 1);
        sonidoPuerta = efectos.load(context, R.raw.puerta, 1);
        sonidoBasura = efectos.load(context, R.raw.basura, 1);

        Escena.g = Guarda.getInstance(context);

        Escena.musica = Escena.g.getBool("musica");
        Escena.efectos = Escena.g.getBool("efectos");


        if (Escena.musica)
            activarMusica(context);

        conduciendo = true;
    }

    /**
     * Método para hacer sonar al camión dependiendo de si los efectos están activados o no
     * Si ya estaba conduciendo se reactiva el sonido, si no, se pone desde cero
     */
    public static void sonarCamion() {
        if (Escena.efectos)
            if (!conduciendo) {
                efectos.resume(sonidoCamion);
                conduciendo = true;
            } else {
                efectos.play(sonidoCamion, vol / 2, vol / 2, 1, -1, 1);
                conduciendo = true;
            }
    }

    /**
     * Método para parar el sonido del camión dependiendo de si los efectos están activados o no
     * Se ponen el sonidoCamion en pausa
     */
    public static void pararCamion() {
        if (Escena.efectos) {
            efectos.pause(sonidoCamion);
            conduciendo = false;
        }
    }

    /**
     * Método para hacer sonar la puerta cuando sube y baja el personaje dependiendo de si los efectos están activados o no
     */
    public static void sonarPuerta() {
        if (Escena.efectos)
            efectos.play(sonidoPuerta, vol / 2, vol / 2, 1, 0, 1);
    }

    /**
     * Método para hacer sonar la basura cuando se recoge
     */
    public static void sonarBasura() {
        if (Escena.efectos)
            efectos.play(sonidoBasura, vol / 2, vol / 2, 1, 0, 1);
    }

    /**
     * Método para activar la música
     * Se crea el mediaPlayer y se prepara cada vez que se activa, el bucle es infinito
     *
     * @param c entorno de la aplicación
     */
    public static void activarMusica(Context c) {
        mediaPlayer = MediaPlayer.create(c, R.raw.cancionfondo);
        mediaPlayer.setVolume(vol / 2, vol / 2);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer player) {
                player.start();
                player.setLooping(true);
            }
        });
    }

    /**
     * Método para desactivar la música
     * Si no es null, si está sonando se para, si no se desactiva y se pone a null
     */
    public static void desactivarMusica() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * Método para detectar la pulsación en la pantalla
     *
     * @param event evento de pulsación en la pantalla
     * @return devuelve el id de la Escena
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (surfaceHolder) {
            int nuevaEscena = escenaActual.onTouchEvent(event);
            if (nuevaEscena != escenaActual.idEscena) {
                switch (nuevaEscena) {
                    case 0:
                        escenaActual = new Menu(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 1:
                        escenaActual = new PantallaSeleccion(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 10:
                        escenaActual = new Ciudad(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 11:
                        escenaActual = new Afueras(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 99:
                        escenaActual = new Opciones(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 98:
                        escenaActual = new Records(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 97:
                        escenaActual = new Ayuda(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                    case 96:
                        escenaActual = new Creditos(context, nuevaEscena, anchoPantalla, altoPantalla);
                        break;
                }
            }
        }
        return true;
    }

    /**
     * Método que se lanza cuando se crea la superficie
     *
     * @param surfaceHolder Superficie de visualización
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    /**
     * Método que se lanza cuando se destruye la superficie
     *
     * @param surfaceHolder Superficie de visualización
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        hilo.setFuncionando(false);
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se lanza cuando se cambia la superficie
     *
     * @param holder Superficie de visualización
     * @param format PixelFormat de la superficie
     * @param width  anchura de la pantalla
     * @param height altura de la pantalla
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        anchoPantalla = width;
        altoPantalla = height;

        escenaActual = new Menu(context, 0, anchoPantalla, altoPantalla);

        hilo.setSurfaceSize(width, height);
        hilo.setFuncionando(true);
        if (hilo.getState() == Thread.State.NEW)
            hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo = new Hilo();
            hilo.start();
        }

    }

    /**
     * Clase hilo, se implementa el método de dibujo y de las físicas para que se haga en paralelo con la gestión de la interfaz de usuario
     */
    class Hilo extends Thread {
        /**
         * Constructor del Hilo
         */
        public Hilo() {

        }

        /**
         * Método en el que funciona el hilo si está a true el parámetro, actualiza las físicas y dibuja en la escena en la que se está en el momento
         */
        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null;
                try {
                    if (!surfaceHolder.getSurface().isValid())
                        continue;
                    c = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        escenaActual.actualizarFisica();
                        escenaActual.dibujar(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        /**
         * Indicar si se activa el funcionamiento del hilo o se desactiva
         *
         * @param flag parámetro para activarlo o desactivarlo
         */
        void setFuncionando(boolean flag) {
            funcionando = flag;
        }


        /**
         * Indicar el alto y ancho de la pantalla
         *
         * @param width  anchura de la pantalla
         * @param height altura de la pantalla
         */
        public void setSurfaceSize(int width, int height) {
            synchronized (surfaceHolder) {

            }
        }
    }
}
