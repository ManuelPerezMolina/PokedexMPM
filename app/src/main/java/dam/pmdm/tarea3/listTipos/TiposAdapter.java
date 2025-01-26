package dam.pmdm.tarea3.listTipos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dam.pmdm.tarea3.R;

public class TiposAdapter extends ArrayAdapter<Tipos> {

    private ArrayList<Tipos> tipos;
    private Activity context;
    private int layout;

    public TiposAdapter(Activity context, int layout, ArrayList<Tipos> tipos) {
        super(context, layout, tipos);
        this.tipos = tipos;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(layout, null);

        Tipos accionActual = tipos.get(position);

        TextView nombreAccion =  convertView.findViewById(R.id.lvTipos);

        nombreAccion.setText(accionActual.getTipo());

        return convertView;
    }
}
