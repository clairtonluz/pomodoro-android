package br.com.clairtonluz.pomodoro.utils;

import android.widget.TextView;

/**
 * Created by clairton on 28/05/16.
 */
public abstract class ViewUtils {

    public static boolean isFilled(TextView... values) {
        boolean valido = true;

        for (TextView textView : values) {
            String valor = textView.getText().toString();
            if (valor == null || valor.isEmpty()) {
                valido = false;
                textView.setError("Campo obrigatório");
            }
        }

        return valido;
    }
}
