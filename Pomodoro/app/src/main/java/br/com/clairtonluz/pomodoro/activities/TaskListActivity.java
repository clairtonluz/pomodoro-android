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

public class TaskListActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_task_list);

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
        System.out.println("INICIAR2");
        if (task != null) {

            System.out.println(task.getDescricao());
            ((BoundService) serviceConnection.getServiceNotifier()).startCounter();
        }
    }

    public void concluir(View view) {
        System.out.println("CONCLUIR");
        ((BoundService) serviceConnection.getServiceNotifier()).stopCounter();
    }

    public void addTask(View view) {
        System.out.println("TASK");
        startActivity(new Intent(this, TaskActivity.class));
    }
}
