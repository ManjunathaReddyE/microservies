package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "txndetail")
public class TxnDetail {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "txnId")
	private Long txnId;
	
	@Column(name = "accountId")
	private Long accountId;
	
	
	@Column(name = "amount")
	private double amount;
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@OneToOne
	@JoinColumn(name ="accountNumber", nullable = false)
	private Baccount baccount;
	
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name ="a", nullable = false) private Account account;
	 */

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}


	
	public Baccount getBaccount() {
		return baccount;
	}

	public void setBaccount(Baccount baccount) {
		this.baccount = baccount;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	

	

}
