package com.lordx.finance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class TelaSelecionaDetalhes extends Activity {

	private DatePicker pickerData;
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.tela_seleciona_detalhes);
		pickerData = (DatePicker)findViewById(R.id.pickerData);
	}
	
	public void carregarMovimentacoes(View v){
		Date d = new Date(pickerData);
		TelaMostraDetalhes.data = d;
		Intent i = new Intent(getApplicationContext(), TelaMostraDetalhes.class);
		startActivity(i);
	}
	
	public void exportar(View v){
		String estadoSD = Environment.getExternalStorageState();
		if(estadoSD.equals(Environment.MEDIA_MOUNTED)){
			Date date = new Date(pickerData);
			Movimentacao[] list = Operacoes.carregaMovimentacoes(this, date);
			String nomeArquivo = Operacoes.getNomeArquivo(date);
			File f = Environment.getExternalStorageDirectory();
			
			f = new File(f, nomeArquivo + ".txt");
			BufferedWriter writer = null;
			try{
				writer = new BufferedWriter(new FileWriter(f));
				writer.write(getDetalhes(list));
			}catch(Exception e){
				Log.e("erro", e.getMessage());
			}
			try{
				writer.close();
			}catch(Exception ex){}
			Toast.makeText(this, "Exportado para " + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(this, "Falha: cartão SD não montado!", Toast.LENGTH_SHORT).show();
	}
	
	private String getDetalhes(Movimentacao[] l){
		float saldo = 0;
		String resp = "";
		Movimentacao anterior = null;
		for(Movimentacao m : l){
			saldo += m.getValor()*m.getQtd();
			if(anterior == null || !anterior.getData().equals(m.getData()))
				resp += m + "\n";
			else
				resp += m.toStringSemData() + "\n";
			anterior = m;
		}
		//lembrando que salarios tem valor negativo
		resp += "\nSaldo: " + String.format("%.2f", saldo);
		return resp;
	}
//
//	public static void deletar(Activity a, Movimentacao velho[], int position) {
//		Movimentacao novo[] = new Movimentacao[velho.length - 1];
//		for(int i = 0; i < novo.length; i++){
//			int indice = i < position ? i : i+1;
//			novo[i] = velho[indice];
//		} 
//		Operacoes.salvaMovimentacoes(a, novo, velho[0].getData());
//	}
}
