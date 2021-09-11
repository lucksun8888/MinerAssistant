package com.ma.Bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 给挖矿列表注入数据
 * @author lucksun
 *
 */
public class LogViewBOList {
	private List<LogViewBO> lvBoList;
	public LogViewBOList(HashMap<String,HashMap> lvMap){
		lvBoList = new ArrayList<LogViewBO>();
        for (String field : lvMap.keySet()) {
            try {
            	LogViewBO LvBo = new LogViewBO();
            	LvBo.setID(field);
            	Map m = lvMap.get(field);
            	LvBo.setDevice((String) m.get(3));
            	LvBo.setHashrate((String) m.get(4));
            	LvBo.setAccept((String) m.get(5));
            	LvBo.setReject((String) m.get(6));
            	LvBo.setInv((String) m.get(7));
            	LvBo.setPowr((String) m.get(8));
            	LvBo.setTemp((String) m.get(9));
            	LvBo.setFan((String) m.get(10));
            	LvBo.setCClk((String) m.get(11));
            	LvBo.setGMClk((String) m.get(12));
            	LvBo.setMUtl((String) m.get(13));
            	LvBo.setEffWatt((String) m.get(14));
            	lvBoList.add(LvBo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public LogViewBOList(){
		lvBoList = new ArrayList<LogViewBO>();
	}
	
	public List<LogViewBO> getLVBoList() {
		return lvBoList;
	}

	public void setLVBoList(List<LogViewBO> LVBoList) {
		this.lvBoList = LVBoList;
	}
}
