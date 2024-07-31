package com.simple.StES.DTO;



public class PayRequest {
	
	private String paymentMethod;
    private String buyerName;
    private String buyerTel;
    private Address address;
    private Item[] items;
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public String getBuyerTel() { return buyerTel; }
    public void setBuyerTel(String buyerTel) { this.buyerTel = buyerTel; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public Item[] getItems() { return items; }
    public void setItems(Item[] items) { this.items = items; }



	public static class Address {
        private String postcode;
        private String address;
        private String detailAddress;

        // getters and setters

        public String getPostcode() { return postcode; }
        public void setPostcode(String postcode) { this.postcode = postcode; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getDetailAddress() { return detailAddress; }
        public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }
    }

    public static class Item {
        private String name;
        private int count;
        private int price;

        // getters and setters

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }

        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }

}
