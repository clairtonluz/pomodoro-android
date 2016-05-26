package br.com.clairtonluz.pomodoro.services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import br.com.clairtonluz.pomodoro.observers.ListenValue;
import br.com.clairtonluz.pomodoro.observers.ServiceNotifier;

/**
 * Created by clairton on 25/05/16.
 */
public class BoundServiceConnection implements ServiceConnection {

    private ServiceNotifier serviceNotifier;
    private ListenValue listenValue;
    private boolean connected;

    public BoundServiceConnection(ListenValue listenValue) {
        this.listenValue = listenValue;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
        serviceNotifier = binder.getService();
        serviceNotifier.add(listenValue);
        connected = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public ServiceNotifier getServiceNotifier() {
        return serviceNotifier;
    }
}
