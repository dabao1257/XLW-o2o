package com.imooc.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.FileUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException{
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 店铺状态初始化
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				// RuntimeException跟事务结合的，抛出异常就会回滚
				throw new ShopOperationException("店铺创建失败");
			} else {
				// 存储图片
				try {
					addShopImg(shop, shopImgInputStream,fileName);

				} catch (Exception e) {
					throw new ShopOperationException("addShopImg error:" + e.getMessage());
				}
				// 更新店铺的图片地址
				effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					throw new ShopOperationException("店铺图片地址更新失败");
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}

		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		// 获取shop图片目录相对值路径
		String dest = FileUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		
		try{
		if(shop==null||shop.getShopId()==null){
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else{
		//1.判断是否需要处理图片
		Shop tempShop = shopDao.queryByShopId(shop.getShopId());
		if(tempShop.getShopImg()!=null&&fileName!=null&&"".equals(fileName)){
			ImageUtil.deleteFileOrPath(tempShop.getShopImg());
		}
		addShopImg(shop,shopImgInputStream,fileName);
	
		//2.更新店铺信息
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		if(effectedNum<=0){
			return new ShopExecution(ShopStateEnum.INNER_ERROR);
		}else{
			shop=shopDao.queryByShopId(shop.getShopId());
			return new ShopExecution(ShopStateEnum.SUCCESS,shop);
		}
		
		}
		
	}catch(Exception e){
		throw new ShopOperationException("modifyShop error:"+e.getMessage());
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if(shopList!=null){
			se.setShopList(shopList);
			se.setCount(count);
			
		}else{
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}

}
