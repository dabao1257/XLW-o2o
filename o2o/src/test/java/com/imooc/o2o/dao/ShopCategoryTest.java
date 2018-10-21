package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.entity.ShopCategory;

public class ShopCategoryTest extends BaseTest {

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void queryShopCategory(){
		ShopCategory shopCategory = new ShopCategory();
		
		
		//shopCategory.setShopCategoryId(10L);
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
		
		assertEquals(12,shopCategoryList.size());
		//两个ShopCategory对象，将parantCategory对象设置shopCategoryId属性，
		//再将parantCategory对象当作parentId参数设置到testCategory对象中
		ShopCategory testCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(10L);
		testCategory.setParentId(parentCategory);
		List<ShopCategory> shopCategoryList1 = shopCategoryDao.queryShopCategory(testCategory);
		
		assertEquals(2,shopCategoryList1.size());
		System.out.println(shopCategoryList1.get(0).getShopCategoryName());
	}
}
