package br.com.clairtonluz.pomodoro.observers;

public interface ServiceNotifier {

    void add(ListenValue obj);

    void notifyValue(long value);

}
