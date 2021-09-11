package com.ma.Bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigBOList {
	private List<ConfigBO> cfgBoList;
	public ConfigBOList(HashMap<String,String> configMap){

		
		cfgBoList = new ArrayList<ConfigBO>();
        for (String field : configMap.keySet()) {
            try {
            	ConfigBO cfgBo = new ConfigBO();
            	cfgBo.setKey(field);
            	cfgBo.setValue(configMap.get(field).toString());
            	cfgBoList.add(cfgBo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public ConfigBOList(){
		cfgBoList = new ArrayList<ConfigBO>();
	}
	
	public List<ConfigBO> getCfgBoList() {
		return cfgBoList;
	}

	public void setCfgBoList(List<ConfigBO> cfgBoList) {
		this.cfgBoList = cfgBoList;
	}
}
