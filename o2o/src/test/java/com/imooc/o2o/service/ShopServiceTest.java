package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.dao.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{

	@Autowired
	private ShopService shopService;
	
	@Test
	public  void testQueryShopList(){
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(10L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition,1,2);
		
		System.out.println("店铺列表数为:"+se.getShopList().size());
		System.out.println("店铺总数为:"+se.getCount());
		
		
	}
	
	
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(47L);
		shop.setShopName("修改后");
		//定义源文件地址
		File shopImg = new File("D:\\setup\\timg.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution shopExecution=shopService.modifyShop(shop, is, "timg.jpg");
		System.out.println("新的图片地址:"+shopExecution.getShop().getShopImg());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException{
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(8L);
		area.setAreaId(3);
		shopCategory.setShopCategoryId(10L);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setOwner(owner);
		shop.setShopAddr("woyebuzhidao");
		shop.setShopName("test店铺333");
		shop.setShopDesc("test1333");
		shop.setPhone("177test3333");
		//shop.setShopImg("pic");
		shop.setPriority(13);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("w");
		//定义源文件地址
		File shopImg = new File("D:\\setup\\timg.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop, is,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
		
	}
}



