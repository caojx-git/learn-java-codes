function setTab(n){
	if(!document.getElementById("menu0")) return false;
	if(!document.getElementById("main0")) return false;
	var topno=document.getElementById("topno");
 var tli=document.getElementById("menu0").getElementsByTagName("li");
 var mli=document.getElementById("main0").getElementsByTagName("div");
 for(i=0;i<tli.length;i++){
	 if(n==i){
		 tli[i].className="lisovers";
		 mli[i].style.display="block";
	 }
	 if(n!=i){
		 tli[i].className="";
		 mli[i].style.display="none";
	 }
 }
}

function roll(){
if(!document.getElementById("demo")) return false;
if(!document.getElementById("demo1")) return false;
if(!document.getElementById("demo1")) return false;
	var speed=30; 
var tab=document.getElementById("demo");
var tab1=document.getElementById("demo1");
var tab2=document.getElementById("demo2");
tab2.innerHTML=tab1.innerHTML;
function Marquee(){
if(tab2.offsetWidth-tab.scrollLeft<=0)
tab.scrollLeft-=tab1.offsetWidth
else{
tab.scrollLeft++;
}
}
var MyMar=setInterval(Marquee,speed);
tab.onmouseover=function() {clearInterval(MyMar)};
tab.onmouseout=function() {MyMar=setInterval(Marquee,speed)};
}
function change(){
	if(!document.getElementById("news")) return false;
	var news=document.getElementById("news");
	var lis=news.getElementsByTagName("li");
	for(var i=0;i<lis.length;i++){
		lis[i].onmouseover=function(){
			this.className="liover";
		}
		lis[i].onmouseout=function(){
			this.className="";
		}
	}
}
function tabletd(){
	if(!document.getElementById("listofcoms")) return false;
	var trs=document.getElementById("listofcoms").getElementsByTagName("tr");
	for(var i=0;i<trs.length;i++){
		if(i%2==0){
			trs[i].style.background="#EEE9FD";
		}
	}
}

window.onload=function(){
	roll();
	change();
	tabletd();
}