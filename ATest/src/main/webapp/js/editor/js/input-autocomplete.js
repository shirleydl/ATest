/*! 
	input-autocomplete v1.0
	@author: starIl
 */
(function($){
	var bigAutocomplete = new function(){
		this.currentInputText = null;//目前获得光标的输入框（解决一个页面多个输入框绑定自动补全功能）
		this.functionalKeyArray = [9,20,13,16,17,18,91,92,93,45,36,33,34,35,37,39,112,113,114,115,116,117,118,119,120,121,122,123,144,19,145,40,38,27];//键盘上功能键键值数组
		this.holdText = null;//输入框中原始输入的内容
		
		//初始化插入自动补全div，并在document注册mousedown，点击非div区域隐藏div
		this.init = function(){
			$("body").append("<div id='bigAutocompleteContent' class='bigautocomplete-layout'></div>");
			$(document).bind('mousedown',function(event){
				var $target = $(event.target);
				if((!($target.parents().andSelf().is('#bigAutocompleteContent'))) && (!$target.is(bigAutocomplete.currentInputText))){
					bigAutocomplete.hideAutocomplete();
				}
			})			
			//鼠标悬停时选中当前行
			$("#bigAutocompleteContent").delegate("tr", "mouseover", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
				$(this).addClass("ct");
			}).delegate("tr", "mouseout", function() {
				$("#bigAutocompleteContent tr").removeClass("ct");
			});
			//单击选中行后，选中行内容设置到输入框中，并执行callback函数
			$("#bigAutocompleteContent").delegate("tr", "click", function() {
				var val = $(this).find("div:first").html();
				$(this).data("config",val);
				bigAutocomplete.currentInputText.data("customVal",val);
				bigAutocomplete.currentInputText.val(val);
				var callback_ = bigAutocomplete.currentInputText.data("config").callback;
				if($("#bigAutocompleteContent").css("display") != "none" && callback_ && $.isFunction(callback_)){
					callback_($(this).data("jsonData"),bigAutocomplete.currentInputText.data("config").param);					
				}				
				bigAutocomplete.hideAutocomplete();
			})
		}
		
		this.autocomplete = function(param){
			var $this = $(this);//为绑定自动补全功能的输入框jquery对象	
			if($("body").length > 0 && $("#bigAutocompleteContent").length <= 0){
				bigAutocomplete.init($this);//初始化信息
			}
					
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			
			this.config = {
				   /*
				    * type : Number [可选]
					* width:下拉框的宽度，默认使用输入框宽度
					*/
	               width:$this.outerWidth() - 2,
	               /*
	                * type: Array 
	                * url和data参数只有一个生效，data优先
	                */
	               data:null,
	               /*
	                * type : String
	                * url：格式url:""用来ajax后台获取数据，返回的数据格式为data参数一样
	                * (自动取输入框的值,keyword:value) 
	                */
	               url:null,
	               /*
	                * type : Boolean [可选]
	                * 用户是否可以自定义值(false后只能单击或Enter能选中值)
	                * =false时 清空全部值会调用callback方法 (null,param)
	                */
	               custom:true,
	               /*
	                * type : Function [可选]
	                * 下拉框行格式化 
	                * params[(data,index,row)]
	                */
	               formatItem:null,
	               /*
	                * type : Function [可选]
	                * 选中后输入框显示值 default : formatItem
	                * params[(data,index,row)]
	                */
	               formatSelected:null,
	               /*
	                * type : String [可选]
	                * default : title
	                * 格式化输出后,选中值
	                */
	               title:'title',
	               /*
	                * type : Function [可选]
	                * callback：选中行后按回车或单击时回调的函数
	                * params[(row,param)]
	                */
	               callback:null,
	               /*
	                * [可选]
	                * callback方法的第二个参数,用于自定义值
	                */
	               param:null,
			};
			$this.attr("title","输入匹配值");
			$.extend(this.config,param);
			$this.data("config",this.config);
			$this.data("customVal",$.trim($this.val()));
			//输入框keydown事件
			$this.keydown(function(event) {
				switch (event.keyCode) {
				case 40://向下键					
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $nextSiblingTr = $bigAutocompleteContent.find(".ct");
					if($nextSiblingTr.length <= 0){//没有选中行时，选中第一行
						$nextSiblingTr = $bigAutocompleteContent.find("tr:first");
					}else{
						$nextSiblingTr = $nextSiblingTr.next();
					}
					$bigAutocompleteContent.find("tr").removeClass("ct");
					
					if($nextSiblingTr.length > 0){//有下一行时（不是最后一行）
						$nextSiblingTr.addClass("ct");//选中的行加背景
						
						//div滚动到选中的行,jquery-1.6.1 $nextSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($nextSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $nextSiblingTr.height() );
					}
					break;
				case 38://向上键
					if($bigAutocompleteContent.css("display") == "none")return;
					
					var $previousSiblingTr = $bigAutocompleteContent.find(".ct");
					if($previousSiblingTr.length <= 0){//没有选中行时，选中最后一行行
						$previousSiblingTr = $bigAutocompleteContent.find("tr:last");
					}else{
						$previousSiblingTr = $previousSiblingTr.prev();
					}
					$bigAutocompleteContent.find("tr").removeClass("ct");
					
					if($previousSiblingTr.length > 0){//有上一行时（不是第一行）
						$previousSiblingTr.addClass("ct");//选中的行加背景
						
						//div滚动到选中的行,jquery-1.6.1 $$previousSiblingTr.offset().top 有bug，数值有问题
						$bigAutocompleteContent.scrollTop($previousSiblingTr[0].offsetTop - $bigAutocompleteContent.height() + $previousSiblingTr.height());
					}				
					break;
				case 27://ESC键隐藏下拉框					
					bigAutocomplete.hideAutocomplete();
					break;
				}
			});		
			//输入框focus事件(查询全部值)
			$this.focus(function(){
				autocompleteSelect();
			})
			//输入框keyup事件
//			$this.keyup(function(event) {
//				var k = event.keyCode;
//				var ctrl = event.ctrlKey;
//				var isFunctionalKey = false;//按下的键是否是功能键
//				for(var i=0;i<bigAutocomplete.functionalKeyArray.length;i++){
//					if(k == bigAutocomplete.functionalKeyArray[i]){
//						isFunctionalKey = true;
//						break;
//					}
//				}
//				//k键值不是功能键或是ctrl+c、ctrl+x时才触发自动补全功能
//				if(!isFunctionalKey && (!ctrl || (ctrl && k == 67) || (ctrl && k == 88)) ){
//					autocompleteSelect();
//				}
//				//回车键
//				if(k == 13){
//					var callback_ = $this.data("config").callback;
//					if($bigAutocompleteContent.css("display") != "none"){
//						var $previousSiblingTr = $bigAutocompleteContent.find(".ct");
//						//回车键 入值
//						if($previousSiblingTr.length <= 0){//没有选中行时，选中第一行
//							$previousSiblingTr = $bigAutocompleteContent.find("tr:first");
//						}
//						var val = $previousSiblingTr.find("div:first").html();
//						$this.data("customVal",val);
//						$this.val(val);//选中行内容设置到输入框中
//						//回调方法
//						if(callback_ && $.isFunction(callback_)){
//							callback_($previousSiblingTr.data("jsonData"),$this.data("config").param);
//						}
//						$bigAutocompleteContent.hide();						
//					}
//				}
//			});
			$this.bind('input propertychange',function() {
				autocompleteSelect();
	
			});
			
			//自动补全,查询
			function autocompleteSelect(){
				var config = $this.data("config");					
				var offset = $this.offset();
				$bigAutocompleteContent.width(config.width);
				var h = $this.outerHeight() - 1;
				$bigAutocompleteContent.css({"top":offset.top + h,"left":offset.left});
				
				var data = config.data;	
				var url = config.url;
				var title = config.title;
				var keyword_ = $.trim($this.val());
				if(data != null && $.isArray(data) ){
					var data_ = new Array();
					for(var i=0;i<data.length;i++){
						if(data[i][title] && data[i][title].toUpperCase().indexOf(keyword_.toUpperCase()) > -1){
							data_.push(data[i]);
						}
					}						
					makeContAndShow(data_);
				}else if(url != null && url != ""&&keyword_!=null&&keyword_!=""){//ajax请求数据
					//encodeURI 转换编码
					$.post(url,{keyword:keyword_},function(result){
						makeContAndShow(result.data)
					},"json")
				}					
				bigAutocomplete.holdText = $this.val();
			}
			//组装下拉框html内容并显示
			function makeContAndShow(data_){
				
				if(data_ == null || data_.length <=0 ) return;
				var config = $this.data("config");
				var title = config.title;		
				var cont = "<table><tbody>";
				if(config.formatItem == null){
					//默认格式
					for(var i=0;i<data_.length;i++){
						cont += "<tr><td><div style='display:none'>"+data_[i][title]+"</div><div>" + data_[i][title]+ "</div></td></tr>"
					}
				}else{
					//选中方法不存在时,优先
					config.formatSelected = config.formatSelected ? config.formatSelected : config.formatItem;
					//格式化
					for(var i=0;i<data_.length;i++){
						cont += "<tr><td><div style='display:none'>"+config.formatSelected(data_,i,data_[i])+"</div><div>" + config.formatItem(data_,i,data_[i]) + "</div></td></tr>"
					}
				}
				cont += "</tbody></table>";
				$bigAutocompleteContent.html(cont);
				$bigAutocompleteContent.show();
				
				//每行tr绑定数据，返回给回调函数
				$bigAutocompleteContent.find("tr").each(function(index){
					$(this).data("jsonData",data_[index]);
				})
			}
			//输入框blur事件
			$this.blur(function(){
				var config = $this.data("config");
				var callback_ = config.callback;
				var data = config.data;
				var url = config.url;
				var title = config.title;
				var keyword_ = $.trim($this.val());
				if(config.custom == false){//强制选取值
					//如果用户输入值与数据源不匹配,就恢复到原来的值
					if($this.data("customVal") != $this.val() && $this.val() != ''){
						var custom = true;//不匹配
						if(data != null && $.isArray(data) ){
							var data_ = new Array();
							for(var i=0;i<data.length;i++){
								if(data[i][title] == keyword_){
									if(callback_ && $.isFunction(callback_)){
										custom = false;//匹配到了 
										$this.data("customVal",keyword_);
										callback_(data[i],config.param);
									}
								}
							}						
							makeContAndShow(data_);
						}else if(url != null && url != ""&&keyword_!=null&&keyword_!=""){
							$.post(url,{keyword:keyword_},function(result){
								for(var i=0;i<result.data.length;i++){	
									if(result.data[i][title] == keyword_){
										if(callback_ && $.isFunction(callback_)){
											custom = false;//匹配到了 
											$this.data("customVal",keyword_);
											callback_(result.data[i],config.param);
										}
									}
								}
								
							},"json")
							
						}

						//恢复原来值
						if(custom) $this.val($this.data("customVal"));
					}
					if($this.val() == ''){
						//回调方法
						if(callback_ && $.isFunction(callback_)){
							callback_(null,config.param);
						}
					}
				}else{
					//用户输入值后与列表直接匹配,光标移出后调用callback_ 方法
					if($this.data("customVal") != keyword_){
						if(data != null && $.isArray(data) ){
							var data_ = new Array();
							for(var i=0;i<data.length;i++){
								if(data[i][title] == keyword_){
									if(callback_ && $.isFunction(callback_)){
										$this.data("customVal",keyword_);
										callback_(data[i],config.param);
									}
								}
							}						
							makeContAndShow(data_);
						}else if(url != null && url != ""&&keyword_!=null&&keyword_!=""){
							$.post(url,{keyword:keyword_},function(result){
								for(var i=0;i<result.data.length;i++){
									if(result.data[i][title] == keyword_){
										if(callback_ && $.isFunction(callback_)){
											$this.data("customVal",keyword_);
											callback_(result.data[i],config.param);
										}
									}
								}
							},"json")
						}
					}
				}
			});	
			//输入框focus事件
			$this.focus(function(){
				bigAutocomplete.currentInputText = $this;
			});	
		}
		//隐藏下拉框
		this.hideAutocomplete = function(){
			var $bigAutocompleteContent = $("#bigAutocompleteContent");
			if($bigAutocompleteContent.css("display") != "none"){
				$bigAutocompleteContent.find("tr").removeClass("ct");
				$bigAutocompleteContent.hide();
			}			
		}		
	};
	$.fn.bigAutocomplete = bigAutocomplete.autocomplete;	
})(jQuery)