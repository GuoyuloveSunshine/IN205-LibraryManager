package com.ensta.librarymanager.modele;

import java.util.NoSuchElementException;

public enum Abonnement {
	BASIC(0,"BASIC"),
	PREMIUM(1,"PREMIUM"),
	VIP(2,"VIP");
	
	private String string;
	private Abonnement(int aNum,String aStr) {
		this.string = aStr;
	}
	public static Abonnement valueof(String aStr) {
		for(Abonnement abonn : Abonnement.values()) {
			if(aStr.equals(abonn.string)) return abonn;
		}
		throw new NoSuchElementException("no enum for type " + aStr);
	}
	public String toString() {
		return this.string;
	}
}
