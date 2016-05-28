package br.com.clairtonluz.pomodoro.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import br.com.clairtonluz.pomodoro.activities.MainActivity;
import br.com.clairtonluz.pomodoro.models.Task;
import br.com.clairtonluz.pomodoro.observers.ListenValue;
import br.com.clairtonluz.pomodoro.observers.ServiceNotifier;
import br.com.clairtonluz.pomodoro.utils.Cronometro;
import br.com.clairtonluz.pomodoro.utils.ViewUtils;

public class BoundService extends Service implements ServiceNotifier {

    public static final String VALOR_INICIAL = "25:00";
    public static final String VALOR_FINAL = "00:00";
    private ListenValue obj;
    private IBinder binder;
    private boolean stop;
    private boolean isCountStarted;

    public BoundService() {
        this.stop = false;
        this.isCountStarted = false;
        this.binder = new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startCounter(final Context context, final Task task) {
        if (!isCountStarted) {
            final Cronometro cronometro = new Cronometro(0, 0, 0, 0, 25, 0, Cronometro.REGRESSIVA);
            isCountStarted = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!stop) {
                        try {
                            String valor = cronometro.getTime();
                            notifyValue(valor);
                            Log.i("App", "Valor: " + valor);
                            if (timeOver(valor)) {
                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                ViewUtils.notificar(task.getTitulo(), "Hora de dar uma pausa nessa tarefa.", context, MainActivity.class, notificationManager);
                                stopCounter();
                            } else {
                                Thread.sleep(1000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    stop = false;
                    isCountStarted = false;
                }
            }).start();
        }

    }

    private boolean timeOver(String valor) {
        return valor.equals(VALOR_FINAL);
    }

    public void stopCounter() {
        this.stop = true;
        notifyValue(VALOR_INICIAL);
    }

    public void closeService() {
        stopSelf();
    }

    @Override
    public void add(ListenValue obj) {
        this.obj = obj;
    }

    @Override
    public void notifyValue(String value) {
        obj.newValue(value);
    }

    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

}
