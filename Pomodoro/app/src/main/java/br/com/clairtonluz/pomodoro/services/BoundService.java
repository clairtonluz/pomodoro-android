package br.com.clairtonluz.pomodoro.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import br.com.clairtonluz.pomodoro.observers.ListenValue;
import br.com.clairtonluz.pomodoro.observers.ServiceNotifier;
import br.com.clairtonluz.pomodoro.utils.Cronometro;

public class BoundService extends Service implements ServiceNotifier {

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

    public void startCounter() {
        if (!isCountStarted) {
            final Cronometro contagem = new Cronometro(0, 0, 0, 0, 25, 0, Cronometro.REGRESSIVA);
            isCountStarted = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!stop) {
                        try {
                            String valor = contagem.getTime();
                            notifyValue(valor);
                            Log.i("App", "Valor: " + valor);
                            Thread.sleep(1000);
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

    public void stopCounter() {
        this.stop = true;
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
