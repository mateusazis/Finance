package com.lordx.finance;

import java.io.Serializable;

public class Movimentacao implements Serializable, Comparable<Movimentacao>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private int qtd;
	private Date data;
	private float valor;
	
	public Movimentacao(String nome, int qtd, float valor, Date data, boolean ehDespesa){
		this.nome = nome;
		this.qtd = qtd;
		this.valor = valor;
		this.data = data;
		if(!ehDespesa)
			this.valor *= -1;
	}
	
	
	
	public String getNome(){
		return nome;
	}
	
	public int getQtd(){
		return qtd;
	}
	
	public Date getData(){
		return data;
	}
	
	public float getValor(){
		return valor;
	}
	
	public boolean isDespesa(){
		return valor > 0;
	}
	
	@Override
	public String toString(){
		String formato = "%s\n%s: %d x %.2f = %.2f";
		return String.format(formato, data.toString(), nome, qtd, valor, qtd*valor);
	}
	
	public String toStringSemData(){
		String formato = "%s: %d x %.2f = %.2f";
		return String.format(formato, nome, qtd, valor, qtd*valor);
	}

	@Override
	public int compareTo(Movimentacao another) {
		return data.compareTo(another.getData());
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof Movimentacao))
			return false;
		Movimentacao outra = (Movimentacao)o;
		return getQtd() == outra.getQtd() && getValor() == outra.getValor() &&
				isDespesa() == outra.isDespesa() &&
				getNome().equals(outra.getNome()) &&
				getData().equals(outra.getData());
	}
}
