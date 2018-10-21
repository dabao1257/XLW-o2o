package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

	@RequestMapping("/shopoperation")
	public String shopOperation(){
		
		return "shop/shopoperation";
	}
	
	
	@RequestMapping("/shoplist")
	public String shopList(){
		
		return "shop/shoplist";
	}
	
	@RequestMapping("/shopmanage")
	public String shopManage(){
		
		return "shop/shopmanage";
	}
	

}
