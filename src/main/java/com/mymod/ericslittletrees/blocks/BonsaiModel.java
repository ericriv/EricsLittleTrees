package com.mymod.ericslittletrees.blocks;

import net.minecraft.util.StringRepresentable;

public enum BonsaiModel implements StringRepresentable {
	
	BASE("base"),
	FIRST("first");
	
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
		default:
			return BonsaiModel.BASE;
		}
	}
}