package com.lordx.finance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinanceActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_principal);
    }
    
    public void carregarTelaAdd(View v){
    	Intent i =  new Intent(getApplicationContext(), TelaAdd.class);
    	startActivity(i);
    }
    
    public void carregarTelaDetalhes(View v){
    	Intent i =  new Intent(getApplicationContext(), TelaSelecionaDetalhes.class);
    	startActivity(i);
    }
}