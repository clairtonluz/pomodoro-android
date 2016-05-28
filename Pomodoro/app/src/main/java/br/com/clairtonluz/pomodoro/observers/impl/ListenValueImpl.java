package br.com.clairtonluz.pomodoro.observers.impl;

import android.os.Handler;
import android.widget.TextView;

import br.com.clairtonluz.pomodoro.observers.ListenValue;

/**
 * Created by clairton on 25/05/16.
 */
public class ListenValueImpl implements ListenValue {
    private Handler handler;
    private TextView textView;

    public ListenValueImpl(Handler handler, TextView textView) {
        this.handler = handler;
        this.textView = textView;
    }

    @Override
    public void newValue(final String value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(value);
            }
        });
    }
}
