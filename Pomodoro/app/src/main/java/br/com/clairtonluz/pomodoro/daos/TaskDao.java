package br.com.clairtonluz.pomodoro.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.clairtonluz.pomodoro.models.Task;

/**
 * Created by clairton on 21/05/16.
 */
public class TaskDao {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public TaskDao(Context context) {
        banco = new CriaBanco(context);
    }

    public Task salvar(Task task) {
        return task.getId() != null && task.getId() > 0 ? update(task) : insert(task);
    }

    private Task update(Task task) {
        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Task.TITULO, task.getTitulo());
        valores.put(Task.DESCRICAO, task.getDescricao());
        valores.put(Task.POMODOROS, task.getPomodoros());

        String where = Task.ID + "=" + task.getId();
        String[] values = {String.valueOf(task.getId())};

        long newRowId = db.update(Task.TABELA, valores, where, null);
        db.close();
        return task;
    }

    private Task insert(Task task) {
        ContentValues valores;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(Task.TITULO, task.getTitulo());
        valores.put(Task.DESCRICAO, task.getDescricao());
        valores.put(Task.POMODOROS, task.getPomodoros());

        long newRowId = db.insert(Task.TABELA, null, valores);
        db.close();


        if (newRowId == -1) {
            throw new RuntimeException("Erro ao inserir a tarefa");
        }

        task.setId((int) newRowId);
        return task;

    }

    public Task atualizar(Task task) {
        SQLiteDatabase db = banco.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(Task.TITULO, task.getTitulo());
        values.put(Task.DESCRICAO, task.getDescricao());
        values.put(Task.POMODOROS, task.getPomodoros());

// Which row to update, based on the ID
        String selection = Task.ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(task.getId())};

        int count = db.update(
                Task.TABELA,
                values,
                selection,
                selectionArgs);
        return task;
    }

    public List<Task> findAll() {
        SQLiteDatabase db = banco.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                Task.ID,
                Task.TITULO,
                Task.DESCRICAO,
                Task.POMODOROS
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                Task.TITULO + " ASC";

        Cursor c = db.query(
                Task.TABELA,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        List<Task> result = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Task t = new Task();
                t.setId(c.getInt(c.getColumnIndexOrThrow(Task.ID)));
                t.setTitulo(c.getString(c.getColumnIndexOrThrow(Task.TITULO)));
                t.setDescricao(c.getString(c.getColumnIndexOrThrow(Task.DESCRICAO)));
                t.setPomodoros(c.getInt(c.getColumnIndexOrThrow(Task.POMODOROS)));
                result.add(t);
            } while (c.moveToNext());
        } else {
            System.out.println("Não tem item");
        }
        return result;
    }

    public void remove(Integer id) {
        db = banco.getWritableDatabase();
        String selection = Task.ID + " LIKE ?";
        String[] selectionArgs = {id.toString()};
        db.delete(Task.TABELA, selection, selectionArgs);
        db.close();
    }


    public Task findById(Integer id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String[] projection = {
                Task.ID,
                Task.TITULO,
                Task.DESCRICAO,
                Task.POMODOROS
        };

        Cursor c = db.query(
                Task.TABELA,  // The table to query
                projection,                              // The columns to return
                null,                                    // The columns for the WHERE clause
                null,                                    // The values for the WHERE clause
                null,                                    // don't group the rows
                null,                                    // don't filter by row groups
                null                                     // The sort order
        );

        Task result = null;

        if (c.moveToFirst()) {
            result = new Task();
            result.setId(c.getInt(c.getColumnIndexOrThrow(Task.ID)));
            result.setTitulo(c.getString(c.getColumnIndexOrThrow(Task.TITULO)));
            result.setDescricao(c.getString(c.getColumnIndexOrThrow(Task.DESCRICAO)));
            result.setPomodoros(c.getInt(c.getColumnIndexOrThrow(Task.POMODOROS)));
        }

        return result;
    }
}
