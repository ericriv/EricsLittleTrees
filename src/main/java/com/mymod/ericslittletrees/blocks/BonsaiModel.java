package com.mymod.ericslittletrees.blocks;

import net.minecraft.util.StringRepresentable;

public enum BonsaiModel implements StringRepresentable {
	
	BASE("basic"),
	FIRST("first"),
	SECOND("second");
	
	private final String name;
	
	BonsaiModel(String name) {
		this.name = name;
	}
	
	@Override
    public String getSerializedName() {
        return this.name;
    }
	
	public BonsaiModel getNextModel() {
		switch(this) {
		case BASE:
			return BonsaiModel.FIRST;
		case FIRST:
			return BonsaiModel.SECOND;
		case SECOND:
		default:
			return BonsaiModel.BASE;
		}
	}
}