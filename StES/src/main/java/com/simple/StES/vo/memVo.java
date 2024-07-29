package com.simple.StES.vo;

import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="member")
public class memVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Mnum;
	private String id;
	private String pw;             
	private String phone;          
	private String email;          
	private String name;  
	private String address;
	@Column(name = "address_detail")
	private String addressDetail; 
	
	@Formula(value = "(SELECT COUNT(b.count) FROM item_basket b WHERE b.member_id=id)")//subquery
	Integer basketCount;
	
	
	public Integer getBasketCount() {
		return basketCount;
	}
	public void setBasketCount(Integer basketCount) {
		this.basketCount = basketCount;
	}
	public int getMnum() {
		return Mnum;
	}
	public void setMnum(int mnum) {
		Mnum = mnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	@Override
	public String toString() {
		return "memVo [Mnum=" + Mnum + ", id=" + id + ", pw=" + pw + ", phone=" + phone + ", email=" + email + ", name="
				+ name + ", address=" + address + ", addressDetail=" + addressDetail + ", basketCount=" + basketCount
				+ "]";
	}


}
