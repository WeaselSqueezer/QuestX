package com.adamki11s.reputation;

public enum GenericRepLevel {

	EVIL(-600, -1000), BAD(-200, -600), ORDINARY(-200, 200), GOOD(200, 600), HERO(600, 1000), ANY(-1000, 1000);

	final int minRep, maxRep;

	GenericRepLevel(int minRep, int maxRep) {
		this.minRep = minRep;
		this.maxRep = maxRep;
	}
	
	public int getMinRep(){
		return this.minRep;
	}
	
	public int getMaxRap(){
		return this.minRep;
	}

	public static GenericRepLevel getGenericReputation(int rep) {
		if (rep <= -600) {
			return EVIL;
		} else if (rep <= -200) {
			return BAD;
		} else if (rep <= 200) {
			return ORDINARY;
		} else if (rep <= 600) {
			return GOOD;
		} else {
			return HERO;
		}
	}

	public static GenericRepLevel parseRepLevel(String s){
		if(s.equalsIgnoreCase("a")){
			return ANY;
		} else if(s.equalsIgnoreCase("e")){
			return EVIL;
		} else if(s.equalsIgnoreCase("b")){
			return BAD;
		} else if(s.equalsIgnoreCase("o")){
			return ORDINARY;
		} else if(s.equalsIgnoreCase("g")){
			return GOOD;
		} else if(s.equalsIgnoreCase("h")){
			return HERO;
		} else {
			return null;
		}
	}

	public boolean equals(GenericRepLevel grl) {
		if(this == ANY){
			return true;
		}
		return (grl.toString().toLowerCase().equalsIgnoreCase(this.toString().toLowerCase()));
	}

}
