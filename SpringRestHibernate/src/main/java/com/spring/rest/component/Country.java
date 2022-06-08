package com.spring.rest.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Country_Tbl")
@Component
public class Country {

	@Id	
	@Column(name="Country_id")
	private int cid;

	@Column(name="Country_name")
	private String cname;

	@Column(name="Country_population")
	private long cpopulation;

	public Country() { }

	public Country(int cid, String cname, long cpopulation) {	
		this.cid = cid;
		this.cname = cname;
		this.cpopulation = cpopulation;
	}

	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public long getCpopulation() {
		return cpopulation;
	}
	public void setCpopulation(long cpopulation) {
		this.cpopulation = cpopulation;
	}

	@Override
	public String toString() {
		return "Country [cid=" + cid + ", cname=" + cname + ", cpopulation=" + cpopulation + "]";
	}
}