package br.com.clairtonluz.pomodoro.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.clairtonluz.pomodoro.R;
import br.com.clairtonluz.pomodoro.adapters.TaskAdapter;
import br.com.clairtonluz.pomodoro.daos.TaskDao;
import br.com.clairtonluz.pomodoro.models.Task;
import br.com.clairtonluz.pomodoro.observers.ListenValue;
import br.com.clairtonluz.pomodoro.observers.impl.ListenValueImpl;
import br.com.clairtonluz.pomodoro.services.BoundService;
import br.com.clairtonluz.pomodoro.services.BoundServiceConnection;

public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private List<Task> tasks;
    private TaskDao taskDao;
    private BoundServiceConnection serviceConnection;
    private Intent mBoundServiceIntent;
    private ListenValue listValue;
    private TextView timeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = new TaskDao(this);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        listview = (ListView) findViewById(R.id.listview);

        mBoundServiceIntent = new Intent(this, BoundService.class);
        startService(mBoundServiceIntent);

        listValue = new ListenValueImpl(new Handler(), timeLabel);
        serviceConnection = new BoundServiceConnection(listValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarTasks();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(mBoundServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceConnection.isConnected()) {
            unbindService(serviceConnection);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection.isConnected()) {
            ((BoundService) serviceConnection.getServiceNotifier()).closeService();
        }
    }

    private void consultarTasks() {
        tasks = taskDao.findAll();
        listview.setAdapter(new TaskAdapter(this, tasks));
    }

    public void iniciar(View view) {
        int position = listview.getPositionForView(view);
        Task task = (Task) listview.getItemAtPosition(position);
        if (task != null) {
            ((BoundService) serviceConnection.getServiceNotifier()).startCounter(this, task);
        }
    }

    public void editar(View view) {
        int position = listview.getPositionForView(view);
        Task task = (Task) listview.getItemAtPosition(position);
        if (task != null) {
            Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra("id", task.getId());
            startActivity(intent);
        }
    }

    public void concluir(View view) {
        ((BoundService) serviceConnection.getServiceNotifier()).stopCounter();
    }

    public void addTask(View view) {
        startActivity(new Intent(this, TaskActivity.class));
    }
}
