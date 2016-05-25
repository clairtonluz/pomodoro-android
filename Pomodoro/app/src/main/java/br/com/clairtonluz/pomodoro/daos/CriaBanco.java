package br.com.clairtonluz.pomodoro.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.clairtonluz.pomodoro.models.Task;

/**
 * Created by clairton on 21/05/16.
 */
public class CriaBanco extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "banco.db";
    public static final int VERSAO = 2;

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(Task.TABELA).append(" ( ")
                .append("_id integer primary key autoincrement,")
                .append("titulo text,")
                .append("descricao text,")
                .append("pomodoros integer")
                .append(")");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABELA);
        onCreate(db);
    }
}
