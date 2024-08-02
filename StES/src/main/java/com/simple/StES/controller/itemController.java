package com.simple.StES.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.simple.StES.repository.itemRepository;
import com.simple.StES.vo.itemVo;

@Controller
@RequestMapping("/item")
public class itemController {
	
	@Value("${spring.servlet.multipart.location}")
	private String savePath;
	
	@Autowired
	private itemRepository ir;
	
	@GetMapping("/list.do")
	public String list(Model model){
//		Iterable<itemVo> itemList=ir.findAll();
		model.addAttribute("itemList",ir.findAll());
		return "list";
	}
	
	
	
	@GetMapping("/orderform")
	public String order(Model model){
		return "/itemBasket/order_form";
	}
	
	
	
	@GetMapping("/view/{itemNum}")
	public String boardd(Model model, @PathVariable("itemNum") Integer itemNum){
		System.out.println(itemNum);
		model.addAttribute("boardTest", ir.findByItemNum(itemNum));
		return "/item/detail";
	}
	
	@GetMapping("/view/cates/{cateNum}")
	public String catebb(Model model, @PathVariable("cateNum") int cateNum){
		System.out.println(cateNum);
		model.addAttribute("boardTests", ir.findByCateNum(cateNum));
		return "/item/listca";
	}
	
	@GetMapping("/view/names/{name}")
	public String catebb(Model model, @PathVariable("name") String name){
		System.out.println(name);
		model.addAttribute("boardNameTest", ir.findByName(name));
		return "/item/listna";
	}
	
	
	@GetMapping("/seller/insert.do")
	public String insertForm() {
		return "/item/insert";
	}
	
	@PostMapping("/seller/insert.do")
	public String insert(itemVo item,
			            @RequestParam("mainImgFile") MultipartFile mainImgFile,
//			            @RequestParam("detailImgFile") MultipartFile detailImgFile,
						HttpSession session) {
		
		boolean insert=true;
		if(!mainImgFile.isEmpty()) {
			String mainImgExt=mainImgFile.getContentType().split("/")[1]; //"image/jpeg"
			String mainImgType=mainImgFile.getContentType().split("/")[0]; //"image/jpeg"
			String mainFileName="item_main_"+System.currentTimeMillis()+"_"+(int)(Math.random()*10000);//		
			Path mainImgFilePath=Paths.get(savePath+"/"+mainFileName+"."+mainImgExt);
			
			if(mainImgType.equals("image")) {
				try {
					mainImgFile.transferTo(mainImgFilePath); //임시 버퍼로 생성된 파일을 실제 파일로 옮겨서 저장하는 함수 
					
					File mainFile=new File(savePath+"/"+mainFileName+"."+mainImgExt); //저장된 원본파일 불러오기
					BufferedImage mainImg=ImageIO.read(mainFile);
					BufferedImage thubmImg=new BufferedImage(100,100,BufferedImage.TYPE_3BYTE_BGR);
					thubmImg.getGraphics().drawImage(mainImg, 0, 0, 100, 100, null);
					ImageIO.write(thubmImg, mainImgExt, new File(savePath+"/thubm/"+mainFileName+"."+mainImgExt));
					item.setMainImg(mainFileName+"."+mainImgExt);
					ir.save(item);
					
				} catch (IllegalStateException | IOException e) {
					insert=false;
					e.printStackTrace();
				}
			}
		}else {
			insert=false;
		}
		if(insert) {
//			session.setAttribute("msg", "아이템 저장 성공");
			return "redirect:/";
		}else {
//			session.setAttribute("msg", "아이템 저장 실패");
			return "redirect:/item/seller/insert.do";
		}
		
	}

}
