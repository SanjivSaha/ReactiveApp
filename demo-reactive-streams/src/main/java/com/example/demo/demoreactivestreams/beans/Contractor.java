package com.example.demo.demoreactivestreams.beans;

public class Contractor extends Employee {

	private int fid;

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}
	
	public Contractor(int id, int fid, String name) {
		super(id, name);
		this.fid = fid;
	}
	
	@Override
	public String toString() {
		return "[id="+super.getId()+",name="+super.getName()+",fid="+fid+"]";
	}
}
