<!-- 模仿天猫整站ssm 教程 为how2j.cn 版权所有-->
<!-- 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关-->
<!-- 供购买者学习，请勿私自传播，否则自行承担相关法律责任-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	
<div class="reviewDiv">
	<div class="reviewProductInfoDiv">
		<div class="reviewProductInfoImg"><img width="400px" height="400px" src="img/productSingle/${p.firstProductImage.id}.jpg"></div>
		<div class="reviewProductInfoRightDiv">
			<div class="reviewProductInfoRightText">
				${p.name}
			</div>
			<table class="reviewProductInfoTable">
				<tr>
					<td width="75px">价格:</td>
					<td><span class="reviewProductInfoTablePrice">￥<fmt:formatNumber type="number" value="${p.originalprice}" minFractionDigits="2"/></span> 元 </td>
				</tr>
				<tr>
					<td>配送</td>
					<td>快递:  0.00</td>
				</tr>
				<tr>
					<td>月销量:</td>
					<td><span class="reviewProductInfoTableSellNumber">${p.saleCount}</span> 件</td>
				</tr>
			</table>
			
			<div class="reviewProductInfoRightBelowDiv">
				<span class="reviewProductInfoRightBelowImg"><img1 src="img/site/reviewLight.png"></span>
				<span class="reviewProductInfoRightBelowText" >现在查看的是 您所购买商品的信息
于<fmt:formatDate value="${o.createdate}" pattern="yyyy年MM月dd"/>下单购买了此商品 </span>
			
			</div>
		</div>
		<div style="clear:both"></div>
	</div>
	<div class="reviewStasticsDiv">
		<div class="reviewStasticsLeft">
				<div class="reviewStasticsLeftTop"></div>
				<div class="reviewStasticsLeftContent">累计评价 <span class="reviewStasticsNumber"> ${p.reviewCount}</span></div>
				<div class="reviewStasticsLeftFoot"></div>
		</div>
		<div class="reviewStasticsRight">
			<div class="reviewStasticsRightEmpty"></div>
			<div class="reviewStasticsFoot"></div>
		</div>
	</div>		
	
	<c:if test="${paramm.showonly==true}">
	<div class="reviewDivlistReviews">
		<c:forEach items="${reviews}" var="r">
			<div class="reviewDivlistReviewsEach">
				<div class="reviewDate"><fmt:formatDate value="${r.createdate}" pattern="yyyy-MM-dd"/></div>
				<div class="reviewContent">${r.content}</div>
				<div class="reviewUserInfo pull-right">${r.user.anonymousName}<span class="reviewUserInfoAnonymous">(匿名)</span></div>
			</div>
		</c:forEach>
	</div>
	</c:if>
	
	<c:if test="${paramm.showonly!=true}">
		<div class="makeReviewDiv">
		<form method="post" action="foredoreview">
			<div class="makeReviewText">其他买家，需要你的建议哦！</div>
			<table class="makeReviewTable">
				<tr>
					<td class="makeReviewTableFirstTD">评价商品</td>
					<td><textarea name="content"></textarea></td>
				</tr>
			</table>
			<div class="makeReviewButtonDiv">
				<input type="hidden" name="oid" value="${o.id}">
				<input type="hidden" name="pid" value="${p.id}">
				<button type="submit">提交评价</button>
			</div>
		</form>
		</div>	
	</c:if>

</div>