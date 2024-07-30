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
	@Column(name = "member_id")
	private String memberId;
	private String address;
	private String postcode;
	private String detailAddress;
	private String buyerName;
	private String buyerTel;
	private String paymentMethod;
	
	
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerTel() {
		return buyerTel;
	}
	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	@Override
	public String toString() {
		return "payVo [pay_num=" + pay_num + ", payname=" + payname + ", count=" + count + ", price=" + price
				+ ", memberId=" + memberId + ", address=" + address + ", postcode=" + postcode + ", detailAddress="
				+ detailAddress + ", buyerName=" + buyerName + ", buyerTel=" + buyerTel + ", paymentMethod="
				+ paymentMethod + "]";
	}

	
	

	
	
	

	

	
	

}
