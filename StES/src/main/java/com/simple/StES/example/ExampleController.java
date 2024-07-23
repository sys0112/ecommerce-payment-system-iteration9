package com.simple.StES.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.ArrayList;

@Controller
public class ExampleController {

    @GetMapping("/example")
    public String example(Model model) {
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "Apple", 1.2, 4));
        itemList.add(new Item(2, "Banana", 0.8, 6));
        itemList.add(new Item(3, "Cherry", 2.5, 10));
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String itemListJson = objectMapper.writeValueAsString(itemList);
            model.addAttribute("itemListJson", itemListJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "example";
    }

    static class Item {
        private int id;
        private String name;
        private double price;
        private int quantity;

        public Item(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
