package com.simple.StES.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.simple.StES.repository.basketRepository;
import com.simple.StES.vo.basketVo;
import com.simple.StES.vo.memVo;

@Controller
@RequestMapping("/itemBasket")
public class basketController {
	
	@Autowired 
	basketRepository ibr;
	
	@GetMapping("/list.do")
	public String list(Model model,HttpSession session) {
		memVo memVo=(memVo)session.getAttribute("memVo");
        List<basketVo> basketList = ibr.findByMemberId(memVo.getId());
        
        // Calculate total sum
        int totalSum = basketList.stream().mapToInt(b -> b.getCount() * b.getItem().getPrice()).sum();

        // Add attributes to the model
        model.addAttribute("basketList", basketList);
        model.addAttribute("totalSum", totalSum);
		return "/itemBasket/list";
	}
	
	@PostMapping("/insert.do")
	public String insert(@RequestParam("itemNum")int itemNum,@RequestParam("count") int count,HttpSession session,  Model model){
		memVo memVo=(memVo)session.getAttribute("memVo");
		basketVo basketVo=ibr.findByItemNumAndMemberId( itemNum, memVo.getId());
		if(basketVo==null) {
			basketVo=new basketVo();
			basketVo.setMemberId(memVo.getId());
			basketVo.setItemNum(itemNum);
			basketVo.setCount(count);
			ibr.save(basketVo); //기존의 값이 없으면 insert  
		}else {
			basketVo.setCount(basketVo.getCount()+count);
			ibr.save(basketVo); //기존의 값이 있고 basket_num이 정의되어 있으면  Update 
		}
		
		return "redirect:/itemBasket/list.do";
	}
	
	@PostMapping("/update.do")
	public String update(basketVo basketVo) {
		//System.out.println(basketVo);
		ibr.save(basketVo);
		return "redirect:/itemBasket/list.do";
	}
	
	@PostMapping("/delete.do")
	public String delete(basketVo basketVo) {
		System.out.println(basketVo);
		ibr.delete(basketVo);
		return "redirect:/itemBasket/list.do";
	}

}
