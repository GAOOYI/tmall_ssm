<!-- 模仿天猫整站ssm 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="confirmPayPageDiv">
	<div class="confirmPayImageDiv">
		<img src="img/site/comformPayFlow.png">
		<div  class="confirmPayTime1">
			<fmt:formatDate value="${o.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
		<div  class="confirmPayTime2">
			<fmt:formatDate value="${o.paydate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
		<div class="confirmPayTime3">
			yyyy-MM-dd HH:mm:ss 
		</div>
		

	</div>
	<div class="confirmPayOrderInfoDiv">
		<div class="confirmPayOrderInfoText">我已收到货，同意支付宝付款</div>
	</div>
	<div class="confirmPayOrderItemDiv">
		<div class="confirmPayOrderItemText">订单信息</div>
		<table class="confirmPayOrderItemTable">
			<thead>
				<th colspan="2">宝贝</th>		
				<th width="120px">单价</th>		
				<th width="120px">数量</th>		
				<th width="120px">商品总价 </th>		
				<th width="120px">运费</th>		
			</thead>
			<c:forEach items="${o.orderitems}" var="oi">
				<tr>
					<td><img width="50px" src="img/productSingle_middle/${oi.product.firstProductImage.id}.jpg"></td>
					<td class="confirmPayOrderItemProductLink">
						<a href="#nowhere">${oi.product.name}</a>
					</td>
					<td>￥<fmt:formatNumber type="number" value="${oi.product.originalprice}" minFractionDigits="2"/></td>
					<td>1</td>
					<td><span class="conformPayProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promoteprice}" minFractionDigits="2"/></span></td>
					<td><span>快递 ： 0.00 </span></td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="confirmPayOrderItemText pull-right">
			实付款： <span class="confirmPayOrderItemSumPrice">￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2"/></span>
		</div>
		
		
	</div>
	<div class="confirmPayOrderDetailDiv">
		
		<table class="confirmPayOrderDetailTable">
			<tr>
				<td>订单编号：</td>
				<td>${o.ordercode} <img width="23px" src="img/site/confirmOrderTmall.png"></td>
			</tr>
			<tr>
				<td>卖家昵称：</td>
				<td>天猫商铺 <span class="confirmPayOrderDetailWangWangGif"></span></td>
			</tr>
			<tr>
				<td>收货信息： </td>
				<td>${o.address}，${o.receiver}， ${o.mobile}，${o.post} </td>
			</tr>
			<tr>
				<td>成交时间：</td>
				<td><fmt:formatDate value="${o.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</table>
		
	</div>
	<div class="confirmPayButtonDiv">
		<div class="confirmPayWarning">请收到货后，再确认收货！否则您可能钱货两空！</div>
		<a href="foreorderConfirmed?oid=${o.id}"><button class="confirmPayButton">确认支付</button></a>
	</div>
</div>