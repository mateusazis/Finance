package com.lordx.finance;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<Movimentacao> {

	private Movimentacao [] objs;
	private Activity act;
	private int layoutID;
	
	public Adapter(Activity a, int layoutResourceID,
			Movimentacao[] objects) {
		super(a.getApplicationContext(), layoutResourceID, objects);
		objs = objects;
		act = a;
		layoutID = layoutResourceID;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(act.getApplicationContext());
            row = inflater.inflate(layoutID, parent, false);
        }
        TextView tvData = (TextView)row.findViewById(R.id.tvData);
        TextView tvNome = (TextView)row.findViewById(R.id.tvNome);
        TextView tvValor = (TextView)row.findViewById(R.id.tvValor);
        Movimentacao m = objs[position];
        int cor = m.isDespesa() ? Color.RED : Color.GREEN;
        tvData.setTextColor(cor);
        tvNome.setTextColor(cor);
        tvValor.setTextColor(cor);
        tvData.setText(m.getData().toString());
        tvNome.setText(m.getNome());
        int qtd = m.getQtd();
        float val = m.getValor();
        tvValor.setText(String.format("%d x %.2f = R$ %.2f", qtd, val, qtd*val));
        
        return row;
    }

}
