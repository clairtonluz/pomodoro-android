package br.com.clairtonluz.pomodoro.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.clairtonluz.pomodoro.R;
import br.com.clairtonluz.pomodoro.daos.TaskDao;
import br.com.clairtonluz.pomodoro.models.Task;
import br.com.clairtonluz.pomodoro.utils.ViewUtils;

public class TaskActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private EditText pomodoros;
    private TaskDao taskDao;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        taskDao = new TaskDao(this);
        titulo = (EditText) findViewById(R.id.tituloField);
        descricao = (EditText) findViewById(R.id.descricaoField);
        pomodoros = (EditText) findViewById(R.id.pomodoroField);

        int id = getIntent().getIntExtra("id", 0);
        System.out.println(id);
        if (id > 0) {
            System.out.println("ID");
            System.out.println(id);
            task = taskDao.findById(id);
            System.out.println(task);
            System.out.println(task.getDescricao());
            System.out.println(task.getId());
        } else {
            task = new Task();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("start");
        System.out.println(task.getId());
        if (task.getId() != null && task.getId() > 0) {
            titulo.setText(task.getTitulo());
            descricao.setText(task.getDescricao());
            pomodoros.setText(String.valueOf(task.getPomodoros()));
        } else {
            Button deleteButton = (Button) findViewById(R.id.deleteButton);
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void salvar(View view) {
        if (ViewUtils.isFilled(titulo, descricao, pomodoros)) {
            task.setTitulo(titulo.getText().toString());
            task.setDescricao(descricao.getText().toString());
            task.setPomodoros(Integer.valueOf(pomodoros.getText().toString()));

            taskDao.salvar(task);
            finish();
        }
    }

    public void delete(View view) {
        if (task.getId() != null && task.getId() > 0) {
            taskDao.remove(task.getId());
            finish();
        }
    }
}
