package com.lordx.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public final class Operacoes {

	public static boolean modificar(Activity a, Movimentacao velha, Movimentacao nova){
		return deletarMovimentacao(a, velha) && gravarMovimentacao(a, nova);
	}

	public static String getNomeArquivo(Date d){
		return "mov_" + d.getMonth() + "_" + d.getYear();
	}

	public static boolean deletarMovimentacao(Activity a, Movimentacao velha) {
		String nomeArquivo = getNomeArquivo(velha.getData());
		try{
			FileInputStream i = a.openFileInput(nomeArquivo);
			FileOutputStream o = a.openFileOutput("temp", Context.MODE_PRIVATE);
			ObjectInputStream in = new ObjectInputStream(i);
			ObjectOutputStream out = new ObjectOutputStream(o);
			Movimentacao lida;
			try{
				while(true){
					lida = (Movimentacao)in.readObject();
					if(!lida.equals(velha))
						out.writeObject(lida);
				}
			}catch(IOException e){}
			in.close();
			out.close();
			a.deleteFile(nomeArquivo);
			File temp = a.getFileStreamPath("temp");
			temp.renameTo(a.getFileStreamPath(nomeArquivo));
			return true;
		}
		catch(Exception e){
			Log.e("erro", e.getMessage());
			return false;
		}
	}
	
	/**
	 * Salva as modificaoes (assumindo que todas tem a mesma data e apagando o que já está salvo).
	 * @param m vetor com modificações a serem salvas.
	 */
	public static void salvaMovimentacoes(Activity a, Movimentacao m[], Date data){
		String nomeArq = getNomeArquivo(data);
		try{
			ObjectOutputStream out = new ObjectOutputStream(a.openFileOutput(nomeArq, Context.MODE_PRIVATE));
			for(Movimentacao mov : m)
				out.writeObject(mov);
			out.close();
		}catch(Exception e){ Log.e("erro", e.getMessage()); }
	}
	
	public static Movimentacao[] carregaMovimentacoes(Activity a, Date d){
		String nomeArq = getNomeArquivo(d);
		ArrayList<Movimentacao> list = new ArrayList<Movimentacao>();
		ObjectInputStream in = null;
		try{
			FileInputStream i = a.openFileInput(nomeArq);
			in = new ObjectInputStream(i);
			while(true)
				list.add((Movimentacao)in.readObject());
		}catch(Exception e){
			try{
				in.close();
			}catch(Exception ex){}
		}
		return list.toArray(new Movimentacao[0]);
	}
	
	public static boolean gravarMovimentacao(Activity a, Movimentacao m){
		String nomeArquivo = getNomeArquivo(m.getData());
		try{
			if(a.getFileStreamPath(nomeArquivo).exists()){
				FileInputStream i = a.openFileInput(nomeArquivo);
				FileOutputStream o = a.openFileOutput("temp", Context.MODE_PRIVATE);
				ObjectInputStream in = new ObjectInputStream(i);
				ObjectOutputStream out = new ObjectOutputStream(o);
				Movimentacao lida;
				boolean jaEscreveu = false;
				try{
					while(true){
						lida = (Movimentacao)in.readObject();
						if(!jaEscreveu && m.compareTo(lida) == -1){
							out.writeObject(m);
							jaEscreveu = true;
						}
						out.writeObject(lida);
					}
				}catch(Exception e){}
				in.close();
				if(!jaEscreveu)
					out.writeObject(m);
				out.close();
				a.deleteFile(nomeArquivo);
				File temp = a.getFileStreamPath("temp");
				temp.renameTo(a.getFileStreamPath(nomeArquivo));
			} else {
				FileOutputStream o = a.openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
				ObjectOutputStream out = new ObjectOutputStream(o);
				out.writeObject(m);
				out.close();
			}
			return true;
		}
		catch(Exception e){
			Log.e("erro", e.getMessage());
			return false;
		}
	}	
}