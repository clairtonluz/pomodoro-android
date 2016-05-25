package br.com.clairtonluz.pomodoro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.clairtonluz.pomodoro.R;
import br.com.clairtonluz.pomodoro.models.Task;

/**
 * Created by clairton on 21/05/16.
 */
public class TaskAdapter extends BaseAdapter {
    public static final String POMODOROS = "pomodoros: ";
    Context context;
    List<Task> data;
    private static LayoutInflater inflater = null;

    public TaskAdapter(Context context, List<Task> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView titulo = (TextView) vi.findViewById(R.id.titulo);
        TextView descricao = (TextView) vi.findViewById(R.id.descricaoField);
        TextView pomodoros = (TextView) vi.findViewById(R.id.pomodorosLabel);

        Task task = data.get(position);
        titulo.setText(task.getTitulo());
        descricao.setText(task.getDescricao());
        pomodoros.setText(POMODOROS + task.getPomodoros());
        return vi;
    }
}
