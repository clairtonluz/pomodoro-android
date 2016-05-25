package br.com.clairtonluz.pomodoro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import br.com.clairtonluz.pomodoro.R;
import br.com.clairtonluz.pomodoro.adapters.TaskAdapter;
import br.com.clairtonluz.pomodoro.daos.TaskDao;
import br.com.clairtonluz.pomodoro.models.Task;

public class TaskListActivity extends AppCompatActivity {

    private ListView listview;
    private List<Task> tasks;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        taskDao = new TaskDao(this);
        listview = (ListView) findViewById(R.id.listview);
//        Task t1 = new Task("Desenvolver app", "Criar app para genrenciar pomodoros", 10);
//        Task t2 = new Task("Desenvolver outra app", "Criar app para baixar musicas", 8);
//        tasks = new ArrayList<>();
//        tasks.add(t1);
//        tasks.add(t2);
    }

    private void consultarTasks() {
        tasks = taskDao.findAll();
        listview.setAdapter(new TaskAdapter(this, tasks));
    }

    @Override
    protected void onResume() {
        super.onResume();
        consultarTasks();
    }

    public void iniciar(View view) {
        int position = listview.getPositionForView(view);
        Task task = (Task) listview.getItemAtPosition(position);
        System.out.println("INICIAR");
        if (task != null) {
            System.out.println(task.getDescricao());
        }
    }

    public void concluir(View view) {
        System.out.println("CONCLUIR");
    }

    public void addTask(View view) {
        System.out.println("TASK");
        startActivity(new Intent(this, TaskActivity.class));
    }
}
