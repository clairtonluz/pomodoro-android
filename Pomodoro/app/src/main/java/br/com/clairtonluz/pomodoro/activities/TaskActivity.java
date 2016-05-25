package br.com.clairtonluz.pomodoro.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.com.clairtonluz.pomodoro.R;
import br.com.clairtonluz.pomodoro.daos.TaskDao;
import br.com.clairtonluz.pomodoro.models.Task;

public class TaskActivity extends AppCompatActivity {

    private EditText titulo;
    private EditText descricao;
    private EditText pomodoros;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        taskDao = new TaskDao(this);
        titulo = (EditText) findViewById(R.id.tituloField);
        descricao = (EditText) findViewById(R.id.descricaoField);
        pomodoros = (EditText) findViewById(R.id.pomodoroField);
    }

    public void salvar(View view) {
        Task task = new Task(
                titulo.getText().toString(),
                descricao.getText().toString(),
                Integer.valueOf(pomodoros.getText().toString()));
        taskDao.salvar(task);
        finish();
    }
}
