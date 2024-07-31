package com.simple.StES.vo;

import java.time.LocalDateTime;
import java.util.Objects;

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
	@Column(name = "pay_num")
	private Integer payNum;
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
	@Column(name = "pay_time")
	private LocalDateTime payTime;
	
	
	public Integer getPayNum() {
		return payNum;
	}
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
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
	public LocalDateTime getPayTime() {
		return payTime;
	}
	public void setPayTime(LocalDateTime payTime) {
		this.payTime = payTime;
	}
	
	@Override
	public String toString() {
		return "payVo [payNum=" + payNum + ", payname=" + payname + ", count=" + count + ", price=" + price
				+ ", memberId=" + memberId + ", address=" + address + ", postcode=" + postcode + ", detailAddress="
				+ detailAddress + ", buyerName=" + buyerName + ", buyerTel=" + buyerTel + ", paymentMethod="
				+ paymentMethod + ", payTime=" + payTime + "]";
	}
	

}
