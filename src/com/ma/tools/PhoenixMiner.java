package com.ma.tools;

public class PhoenixMiner implements Model {

	@Override
	public String getPattern() {
		// TODO Auto-generated method stub
		return "(main Eth speed:.*?\\n";
	}

	@Override
	public String getSplit() {
		// TODO Auto-generated method stub
		return ",";
	}

	@Override
	public int getIdi() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStartI() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEndI() {
		// TODO Auto-generated method stub
		return 0;
	}

}
