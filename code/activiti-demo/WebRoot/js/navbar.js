//========================定义menu类==================;
function Menu(head,child,dir,speed,init_state,ext_on,ext_off,_id)
{
    this.head = document.getElementById(head);//菜单头
    this.body = document.getElementById(child);//菜单体
    this.direction = dir;//菜单收起的方向
    this.speed = speed;//速度
    this.ext_on = ext_on;//扩展菜单展开调用
    this.ext_off = ext_off;//扩展菜单收起调用
    this.init_state = init_state;//设置菜单的初始状态 true/false
    this.a = 30;//加速度
	this.id=_id;
//私用变量;
    this._interval = false;
    this._last_state = false;
    this._size = false;
    this._temp = false;
    this._js = false;
    this._div = false;
    this._parent = false;
    this._parent_control = false;
    var self = this;
    var temp = new Array(null,null);//temp[0]用来给_off()用，temp[1]用来给_on()用
    
//=============================方法=============================
//点击事件处理
    this.click = function(e)
    {
		//self._parent._closeall();
        if (self._parent_control)
        {
            self._parent._control(self);
            return false;
        }
        else
        {
            Interval.clear(self._interval);
            if (self._last_state == false)
            {
				
                self._on();
                return false;
            }
            else
            {
				
                self._off();
                return false;
            }
        }
    }
    
//初始化
    this.init = function()
    {
        this.head.onclick = this.click;
        this.head.onkeypress = function(e)
        {
            e||(e=window.event);
            if (!(e.keyCode ==32 || e.keyCode == 0))return;
            //alert(':)');
            self.click();
        }
        for(var i=0;i<this.body.childNodes.length;i++)
        {
            if (this.body.childNodes[i].nodeType==1)
            {
                this._div=this.body.childNodes[i];
                break;
            }
        }
        if (parseInt(this.body.style.height))//this.body.style.getPropertyCSSValue('height')this.body.currentStyle.height
        {
            this._size = parseInt(this.body.style.height);
        }
        else
        {
            this._size = this._div.offsetHeight;
        }
        switch (this.init_state)
        {
            case true:
                if (this.body.style.display == 'none')
                {
                    //this._last_state = false;
                    this._on();
                }
                else
                {
                    this._last_state = true;
                }
                break;
            default://case false:
                if (this.body.style.display !='none')
                {
                    this._last_state = true;
                    this._off();
                }
                break;
        }
    }
//展开菜单
    this._on = function()
    {
        if (self._last_state == false)
        {
            self._last_state = true;
            self.body.style.display="";
            temp[1] = self.a?2*parseInt(Math.sqrt(self.a*self._size))+1:self._size/5;
            if (isNaN(parseInt(self.body.style.height)))self.body.style.height="0px";
            if (self.ext_on)
            {
                self.ext_on(self.head,self.body,self.id)
            }
            self._interval = Interval.set(self._action_on,speed);
        }
        //setTimeout('slowon("'+self.body.id+'")',5)
    }
//收起菜单
    this._off = function()
    {
        if (self._last_state == true)
        {
            self._last_state = false;
            //if (temp[0] == null)
            //{
                temp[0]=self.a?2*parseInt(Math.sqrt(self.a*self._size))+1:self._size/5;;
            //}
            if(isNaN(parseInt(self.body.style.height)))self.body.style.height = self._size+'px';
            if (self.ext_off)
            {
                self.ext_off(self.head,self.body)
            }
            self._interval = Interval.set(self._action_off,this.speed);
        }
    }
//以下处理滑动
    this._action_on = function()
    {
        if (parseInt(self.body.style.height)+temp[1]>self._size)
        {
            self.body.style.height = self._size+'px';
            Interval.clear(self._interval);
        }
        else
        {
        self.body.style.height = parseInt(self.body.style.height)+temp[1]+'px';
        temp[1] +=self.a;
        }
    }
    this._action_off = function()
    {
        if(parseInt(self.body.style.height)-temp[0]<0)
        {
            Interval.clear(self._interval);
            self.body.style.display = "none";
        }
        else
        {
            self.body.style.height = parseInt(self.body.style.height)-temp[0]+'px';
            temp[0]-=self.a;
        }
    }
}
//meanu类结束

//====================定义Navbar类，用来管理一组menu集合===============================
function navbar(dir,a,speed,ext_on,ext_off)
{
    this.open_only_one = true;//这组menu在任何时刻是否只有一个在开启，true/false
    this.dir = dir;//menu组的公共方向，既然是一组menu它们应该有相同的方向吧？
    this.a =a;//menu公共加速度
    this.speed =speed;//公共速度
    this.ext_on = ext_on;//公共扩展打开函数调用
    this.ext_off = ext_off;//公共的扩展收起函数调用
    this.menu_item = new Array();//menu组
    this._openning;//如果只允许打开一个菜单，这个就会记录当前打开的菜单
    this.open_all = function()//
    {
    };
    this.add = function (head,body)//添加menu的函数
    {
        var temp = new Menu(head,body,this.dir,this.speed,this.ext_on,this.ext_off);
        this.menu_item.push(temp);
    };
    this.init = function ()//Navbar的初始化函数，必须在add完成后调用
    {
        if(this.open_only_one == true)
        {//如果只允许一个打开，那么仅仅设置菜单组的第一个菜单为打开状态
            if (this.menu_item.length>0)
            {
                with(this.menu_item[0])
                {
                    init_state = true;
                    _parent = this;//设置menu的父亲为这个Navbar
                    _parent_control = true;//设置父亲来控制菜单
                    init();
                }
                this._openning = this.menu_item[0];
            }
            for(var i = 1; i<this.menu_item.length;i++)
            {//设置出第一个外的其他菜单为关闭，同时设置好其他参数
                with(this.menu_item[i])
                {
                    init_state = false;
                    _parent = this;
                    init();
                    _parent_control = true;
                }
            }
        }
        else
        {//如果open_only_one == false 那么仅仅初始化菜单
            for(var i = 0;i<this.menu_item.length;i++)
            {
                this.menu_item[i].init();
            }
        }
    };
	
	
	this._closeall= function ()//全部关闭所有菜单
	{
		for(var i = 0;i<this.menu_item.length;i++)
            {
                this.menu_item[i]._off();
            }
	};
	
//额外添加的父亲控制函数
    this._control = function(child)
    {
		for(var i = 0;i<this.menu_item.length;i++)
            {
                this.menu_item[i]._off();
            }
        var self =child;
        Interval.clear(self._interval);
        if (self._last_state == false)
        {
            if (typeof(self._parent._openning) == 'object')
                {
                    self._parent._openning._off();
                    self._parent._openning = self;
                }
            self._on();
            return false;
        }
        else
        {
            //self._off();
            return false;
        }
    }
    
}//Navbar类结束


//===============================interval 处理=============================
//注意：_stack 只有20个
//扩充时必须赋初值1-n
Interval=
{
    length:20,
    _action : new Array(length),
    _stack : new Array(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19),
    _interval : Array(length),
    _timeout: new Array(length),
    //for(var i=0;i<_action.length;i++)stack.push(i);,
    set:function(action_function,speed,time_out)
    {
        time_out = time_out?time_out:15000;//默认的interval超时为15000秒，如果不需要设置超时，那么将下面的setTimeout 那移行注释掉; 
        var p = Interval._stack.pop();
        if(p)
        {
            Interval._action[p] = action_function;
            Interval._interval[p]=setInterval('if(Interval._action['+p+'])Interval._action['+p+']();',speed);//这里的重复执行函数不能写成'Interval._action['+p+']'因为很可能Interval.clear以后，还有一次没有执行完毕，于是就产生了一次错误
            Interval._timeout[p] = setTimeout('Interval.clear('+p+')',time_out);//这行设置interval超时,如果不需要可注释掉;
            return p;
        }
    },
    clear:function(p)
    {
        if (Interval._action[p])
        {
            clearInterval(Interval._interval[p]);
            clearTimeout(Interval._timeout[p]);//这行清除interval超时,如果没有设置超时可注释掉;
            Interval._action[p] = "";
            Interval._stack.push(p);
        }
    }
}
//Interval 处理结束

var  highlightcolor='#d5f4fe';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=highlightcolor;
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

function  clickto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}