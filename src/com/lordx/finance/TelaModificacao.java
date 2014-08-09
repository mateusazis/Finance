package com.lordx.finance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TelaModificacao extends TelaAdd {

	private RadioGroup group;
	public static Movimentacao velha;
	private Button botaoAdd;
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		botaoAdd = (Button)findViewById(R.id.botaoAdd);
		botaoAdd.setText("Modificar");
		group = (RadioGroup)findViewById(R.id.radioGroup);
		boolean ehDepesa = velha.isDespesa();
		group.check(ehDepesa ? R.id.botaoDespesa : R.id.botaoLucro);
		campoNome.setText(velha.getNome());
		float valor = velha.getValor();
		if(!ehDepesa)
			valor *= -1;
		campoValor.setText(valor + "");
		campoQtd.setValue(velha.getQtd());
		Date data = velha.getData();
		timePicker.updateDate(data.getYear(), data.getMonth() - 1, data.getDay());
	}

	public void adicionarDespesa(View v){
		String nome = campoNome.getText().toString();
		if(nome.length() == 0){
			showDialog(DIALOG_NOME_VAZIO);
			return;
		}
		String valorStr = campoValor.getText().toString();
		float valor;
		try{
			valor = Float.parseFloat(valorStr);
		}catch(Exception e){
			showDialog(DIALOG_VALOR_INVALIDO);
			return;
		}
		int qtd = campoQtd.getValue();
		boolean ehDespesa = botaoDespesa.isChecked();
		Date data = new Date(timePicker);
		Movimentacao nova = new Movimentacao(nome, qtd, valor, data, ehDespesa);
		Log.v("mod", "trocando " + velha + " por " + nova);
		String msg;
		if(Operacoes.modificar(this, velha, nova))
			msg = "Despesa modificada com sucesso.";
		else
			msg = "Erro na modifica��o da despesa.";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		finish();
	}	
}
