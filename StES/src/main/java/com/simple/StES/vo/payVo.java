package com.simple.StES.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="payments_account")
public class payVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pay_num;
	private String payname;
	private int count;
	private int price;
	
	public Integer getPay_num() {
		return pay_num;
	}
	public void setPay_num(Integer pay_num) {
		this.pay_num = pay_num;
	}
	public String getPayname() {
		return payname;
	}
	public void setPayname(String payname) {
		this.payname = payname;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "payVo [pay_num=" + pay_num + ", payname=" + payname + ", count=" + count + ", price=" + price + "]";
	}
	
	

}
