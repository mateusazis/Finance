package com.lordx.finance;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class MonthDetails extends Activity {

	private static Date detailedMonth;
	private static Movimentacao [] movs;
	
	private TextView dateTV, inputTV, outputTV, highestExpenseTV, highestInputTV;
	
	public static void setInitialData(Movimentacao [] movs, Date month){
		MonthDetails.movs = movs;
		detailedMonth = month;
	}
	
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.month_details);
		dateTV = (TextView)findViewById(R.id.tvMonth);
		inputTV = (TextView)findViewById(R.id.tvInput);
		outputTV = (TextView)findViewById(R.id.tvOutput);
		highestExpenseTV = (TextView)findViewById(R.id.tvHighestExpense);
		highestInputTV = (TextView)findViewById(R.id.tvHighestInput);
		fillData();
	}
	
	private void fillData(){
		dateTV.setText(String.format("Data: %2d/%d", detailedMonth.getMonth(), detailedMonth.getYear()));
		float input = 0, output = 0;
		Movimentacao highestExpense = null, highestInput = null;
		for(Movimentacao m : movs){
			if(m.isDespesa()){
				output += m.getValor();
				if(highestExpense == null || Math.abs(m.getValor()) > Math.abs(highestExpense.getValor()))
					highestExpense = m;
			}
			else{
				input += m.getValor();
				if(highestInput == null || Math.abs(m.getValor()) > Math.abs(highestInput.getValor()))
					highestInput = m;
			}
		}
		input = Math.abs(input);
		output = Math.abs(output);
		inputTV.setTextColor(Color.GREEN);
		inputTV.setText(String.format("Ganhos: R$ %.2f", input));
		outputTV.setTextColor(Color.RED);
		outputTV.setText(String.format("Gastos: R$ %.2f", output));
		String highestExpenseDetailed = "Maior gasto: " + describeMov(highestExpense);
		
		Spannable text = new SpannableString(highestExpenseDetailed);
		text.setSpan(new ForegroundColorSpan(Color.RED), 12, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		highestExpenseTV.setText(text);
		
		String highestInputDetailed = "Maior ganho: " + describeMov(highestInput);
		text = new SpannableString(highestInputDetailed);
		text.setSpan(new ForegroundColorSpan(Color.GREEN), 12, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		highestInputTV.setText(text);
	}
	
	private static String describeMov(Movimentacao m){
		if(m == null)
			return "nenhum"; 
		return String.format("%s (R$ %.2f) @ %s", m.getNome(), Math.abs(m.getValor()),m.getData());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		detailedMonth = null;
		movs = null;
	}
}
