package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopList(){
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(8L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0,5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表大小:"+shopList.size());
		System.out.println("店铺列表总数:"+count);
		
		Shop shopCondition1 = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(10L);
		shopCondition1.setShopCategory(shopCategory);
		List<Shop> shopList1 = shopDao.queryShopList(shopCondition1, 0,5);
		int count1 = shopDao.queryShopCount(shopCondition1);
		System.out.println("111店铺列表大小:"+shopList1.size());
		System.out.println("111店铺列表总数:"+count1);
		
		
		
	}
	
	
	
	@Test
	@Ignore
	public void testQueryByShopId(){
		long shopId= 15;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaName:"+shop.getArea().getAreaName());
		System.out.println("areaId:"+shop.getArea().getAreaId());
		System.out.println(shop.getShopCategory().getShopCategoryName());
	}
	
	
	
	@Test
	@Ignore
	public void testInsertShop(){
		
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
		shop.setShopName("test店铺");
		shop.setShopDesc("test");
		shop.setPhone("177");
		shop.setShopImg("pic");
		shop.setPriority(13);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("w");
		
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1,effectedNum);
		
		
		
		
	}
	
	@Test
	@Ignore
	public void testUpdateShop(){
		
		Shop shop = new Shop();
		shop.setShopId(40L);

		shop.setShopAddr("更新地址");
		shop.setShopName("更新店铺店铺");
		shop.setShopDesc("更新描述");
		shop.setPhone("188");
		shop.setLastEditTime(new Date());

		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
		
		
		
		
	}
}
