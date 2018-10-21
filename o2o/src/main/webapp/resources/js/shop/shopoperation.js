/**
 * 
 */

$(function(){
	var shopId = getQueryString('shopId');
	var isEdit = shopId?true:false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl='/o2o/shopadmin/registershop';
	var shopInfoUrl ='/o2o/shopadmin/getshopbyid?shopId='+shopId;
	var editShopUrl="/o2o/shopadmin/modifyshop";
	
	//alert(initUrl);
	
	if (!isEdit) {
		getShopInitInfo();
		
	}else{
		
		getShopInfo(shopId);
	}

	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				//$('#area').attr('data-id',shop.areaId);此代码无法获取areaId
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");//修改成这样
			}
		});
	}
	
	
	
	
	function getShopInitInfo(){
		//此方法获取后台返回的商铺分类和所属区域
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml='';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item,index){
					tempHtml+='<option data-id="'+item.shopCategoryId
					+'">'+item.shopCategoryName+'</option>';
				});
				data.areaList.map(function(item,index){
					tempAreaHtml+='<option data-id="'+item.areaId+'">'
					+item.areaName+'</option>';
					
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
				
			}
		});
	}
		$('#submit').click(function(){
			var shop={};
			if(isEdit){
				shop.shopId=shopId;
			}
			shop.shopName=$('#shop-name').val();
			shop.shopAddr=$('#shop-addr').val();
			shop.phone=$('#shop-phone').val();
			shop.shopDesc=$('#shop-desc').val();
			shop.shopCategory={
					shopCategoryId:$('#shop-category').find('option').not(function(){
						return !this.selected;
					}).data('id')//获取select标签id=“shopcategory”对应的val
			};
			shop.area={
					areaId:$('#area').find('option').not(function(){
						return !this.selected;
					}).data('id')
			};
			
			
			var shopImg = $('#shop-img')[0].files[0];//文件流
			var formData=new FormData();
			formData.append('shopImg',shopImg);
			formData.append('shopStr',JSON.stringify(shop));//shop的json对象按字符流方式加入到formData对象
			
			$.ajax({
				url:(isEdit?editShopUrl:registerShopUrl),
				//url:editShopUrl,
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,
				success:function(data){
					if(data.success){
						$.toast('提交成功');
					}else{
						$.toast('提交失败!'+data.errMsg);
					}
					
				}
			});
			
		});
		
	
	
	
	
	
	
	
})