package br.com.clairtonluz.pomodoro.models;

/**
 * Created by clairton on 21/05/16.
 */
public class Task {
    public static final String TABELA = "task";
    public static final String ID = "_id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String POMODOROS = "pomodoros";

    private Integer id;
    private String titulo;
    private String descricao;
    private Integer pomodoros;

    public Task() {
    }

    public Task(String titulo, String descricao, Integer pomodoros) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.pomodoros = pomodoros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPomodoros() {
        return pomodoros;
    }

    public void setPomodoros(Integer pomodoros) {
        this.pomodoros = pomodoros;
    }
}
