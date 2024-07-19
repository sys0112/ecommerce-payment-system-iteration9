package com.simple.StES.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "item_basket",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"item_num","member_id"}) })
public class basketVo {
	
	@Id
	@Column(name = "basket_num")
	private int basketNum;
	@Column(name = "item_num")
	private int itemNum;
	@Column(name = "member_id")
	private String memberId;
	private int count;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_num",insertable = false, updatable = false)
	private itemVo item;
	

	public int getBasketNum() {
		return basketNum;
	}

	public void setBasketNum(int basketNum) {
		this.basketNum = basketNum;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public itemVo getItem() {
		return item;
	}

	public void setItem(itemVo item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "basketVo [basketNum=" + basketNum + ", itemNum=" + itemNum + ", memberId=" + memberId + ", count="
				+ count + ", item=" + item + "]";
	}
	
	
	
	
}
