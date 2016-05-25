package br.com.clairtonluz.pomodoro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.clairtonluz.pomodoro.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showTarefas(View view) {
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }
}
