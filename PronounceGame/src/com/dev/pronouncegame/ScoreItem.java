package com.dev.pronouncegame;

public class ScoreItem {
	
	private String Nama;
	private String Nilai;
	private String Nomer;
	
	public ScoreItem(String Nama, String Nilai,String Nomer) {
		// TODOs Auto-generated constructor stub
		this.Nama = Nama;
		this.Nilai = Nilai;
		this.Nomer = Nomer;
	}
	
	
	public String getNama() {
		return Nama;
	}
	public void setNama(String nama) {
		Nama = nama;
	}
	public String getNilai() {
		return Nilai;
	}
	public void setNilai(String nilai) {
		Nilai = nilai;
	}


	public String getNomer() {
		return Nomer;
	}


	public void setNomer(String nomer) {
		Nomer = nomer;
	}

}
