package com.lordx.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TelaMostraDetalhes extends Activity implements OnItemClickListener{
	
	private Movimentacao movs[];
	public static Date data;
	private ListView lv;
	private TextView tvTotal, tvDataDesps;
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.tela_mostra_detalhes);
		lv = (ListView)findViewById(R.id.lista_movimentacoes);
		tvTotal = (TextView)findViewById(R.id.tvTotal);
		tvDataDesps = (TextView)findViewById(R.id.tvDataDespesas);
		registerForContextMenu(lv);
		lv.setOnItemClickListener(this);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		exibirDados();
	}
	
	private void exibirDados(){
		movs = Operacoes.carregaMovimentacoes(this, data);
		Adapter a = new Adapter(this, R.layout.layout_view_detalhes, movs);
		lv.setAdapter(a);
		float total = calculaTotal();
		tvTotal.setText(String.format("Total: R$ %.2f", total));
		tvDataDesps.setText(String.format("Exibindo movimentações de: %d/%d", data.getMonth(), data.getYear()));
	}
	
	private float calculaTotal(){
		float resp = 0;
		for(Movimentacao m : movs)
			resp += m.getQtd() * m.getValor();
		return resp;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_mov, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterContextMenuInfo adp = (AdapterContextMenuInfo)item.getMenuInfo();
		switch(item.getItemId()){
		case R.id.remover:
			if(!Operacoes.deletarMovimentacao(this, movs[adp.position]))
				Toast.makeText(getApplicationContext(), "Erro ao deletar.", Toast.LENGTH_SHORT).show();
			exibirDados();
			break;
		case R.id.modificar:
			TelaModificacao.velha = movs[adp.position];
			Intent i = new Intent(getApplicationContext(), TelaModificacao.class);
			startActivity(i);
			break;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		openContextMenu(arg1);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.details_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent i = new Intent(getApplicationContext(), MonthDetails.class);
		MonthDetails.setInitialData(movs, data);
		startActivity(i);
		return true;
	}
}