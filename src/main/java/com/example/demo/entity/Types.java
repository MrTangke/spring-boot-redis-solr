package com.example.demo.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Types implements Serializable {
	
	private Integer tid ;
	
	private String tname ;
	
	public Types() {
		// TODO Auto-generated constructor stub
	}
	
	public Types(Integer tid, String tname) {
		super();
		this.tid = tid;
		this.tname = tname;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	@Override
	public String toString() {
		return "Types [tid=" + tid + ", tname=" + tname + "]";
	}
	
	

}
