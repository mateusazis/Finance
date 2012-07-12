package com.lordx.finance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.DatePicker;
import android.widget.Toast;

public class TelaAdd extends Activity {

	protected EditText campoNome, campoValor, campoQtd;
	protected DatePicker timePicker;
	protected RadioButton botaoDespesa;
	
	protected static final int DIALOG_NOME_VAZIO = 0, DIALOG_VALOR_INVALIDO = 1;
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.tela_add);
		campoNome = (EditText)findViewById(R.id.campoNome);
		campoValor = (EditText)findViewById(R.id.campoValor);
		campoQtd = (EditText)findViewById(R.id.campoQtd);
		timePicker = (DatePicker)findViewById(R.id.pickerData);
		botaoDespesa = (RadioButton)findViewById(R.id.botaoDespesa);
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
		int qtd = Integer.parseInt(campoQtd.getText().toString());
		boolean ehDespesa = botaoDespesa.isChecked();
		Date data = new Date(timePicker);
		Movimentacao m = new Movimentacao(nome, qtd, valor, data, ehDespesa);
		String msg;
		if(Operacoes.gravarMovimentacao(this, m))
			msg = "Despesa salva com sucesso.";
		else
			msg = "Erro salvando despesa.";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
	@Override
	public Dialog onCreateDialog(int id){
		AlertDialog.Builder b = new Builder(this);
		b.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		switch(id){
		case DIALOG_NOME_VAZIO:
			b.setMessage("Insira um nome para a despesa.");
			break;
		case DIALOG_VALOR_INVALIDO:
			b.setMessage(getString(R.string.string_erro_valor));
			break;
		}
		return b.create();
	}
	
	public void retornar(View v){
		finish();
	}
	
}
