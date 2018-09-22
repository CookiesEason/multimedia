$(function(){
	var oDiv=document.getElementById('pageNav');	
	var pageAble=new Pageable(10,3,1,oDiv);
	pageAble.changePageNum(1);
})