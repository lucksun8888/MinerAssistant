package com.ma.tools;

public class ModelNBminer implements Model {

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "(Eff\\/Watt\\|.*?=============================================================================)";
	}

	@Override
	public String getSplit() {
		// TODO Auto-generated method stub
		return "\\|";
	}

	@Override
	public int getIdi() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getStartI() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getEndI() {
		// TODO Auto-generated method stub
		return 13;
	}

}
