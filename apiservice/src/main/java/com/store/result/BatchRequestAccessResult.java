package com.store.result;

import java.util.List;

public class BatchRequestAccessResult extends StatusResult {

	private List<String> blockList;
	
	public BatchRequestAccessResult(String _status) {
		super(_status);
		// TODO Auto-generated constructor stub
	}

	public List<String> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<String> blockList) {
		this.blockList = blockList;
	}

}
