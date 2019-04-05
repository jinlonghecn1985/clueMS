document.write("<script language='javascript' src='./jl/http.jl.js'></script>");
document.write("<script language='javascript' src='./jl/jquery.ajax.jl.js'></script>");
document.write("<script language='javascript' src='./jl/jquerysession.js'></script>");

//初始化代码
$(document).ready(function(){ 	
	console.log('load avril.js');	
	AVRIL.info();		
});

function showJLWarning(message){
	new $.zui.Messager(message, {
		type : 'warning',
		placement : 'top-right',
		close : false
	// 禁用关闭按钮
	}).show();
}

function showJLSuccess(message){
	new $.zui.Messager(message, {
		type : 'success',
		placement : 'top-right',
		close : false
	// 禁用关闭按钮
	}).show();
}

function showJLError(message){
	new $.zui.Messager(message, {
		type : 'danger',
		placement : 'top-right',
		close : false
	// 禁用关闭按钮
	}).show();
}

(function($) {
	//注册全局系统对象
	window['AVRIL'] = {};
	
	AVRIL.setUser=function(ucode, uname, utoken){
		AVRIL.clearUser();
		$.session.set("bmsusercode", ucode);
		$.session.set("bmsusername", uname);
		$.session.set("bmsusertoken", utoken);
	};
	
	AVRIL.clearUser=function(){
		$.session.remove("bmsusercode");
		$.session.remove("bmsusername");
		$.session.remove("bmsusertoken");
	};	
	
	AVRIL.userCode=function(){
		return $.session.get("bmsusercode");
	}
	
	AVRIL.userName=function(){
		return $.session.get("bmsusername");
	}	
	
	AVRIL.userToken=function(){
		return $.session.get("bmsusertoken");
	}	
	
	//定义方法-属性
	AVRIL.info = function(){
		console.log('AVRIL FUCK');
	};
	
	AVRIL.loginOut=function(){
		AVRIL.clearUser();
		top.location ="login.html";	
	}
	
	AVRIL.refreshToken = function(uc, fn){
		var iss = false;
		if(uc == undefined || uc.length==0 || uc==AVRIL.userCode()){
			iss = true;
			uc = AVRIL.userCode();
		}
		myAjax("/token/refresh/"+uc, "post", {}, function(data){			
			if(fn){
				if(iss){
					AVRIL.clearUser();
					top.location ="login.html";					
				}else{	
					fn(data);
				}				
			}
		}, false);
	}
	
	//
	AVRIL.getDictionaryByGroup = function(dic, fn){	
		if(dic!=undefined){
			myAjax("/dictionary?dicGroup="+dic, "get", {}, function(data){			
				if(fn){
					fn(data);
				}
			}, true);
		}
	}
	
	AVRIL.saveClueInfo = function(params, fn){
		myAjaxJson("/clueinfo", "post", params, function(data){
			if(fn){fn(data);}
		}, false);
	}
	
	AVRIL.saveClueInfo2 = function(params, fn){
		myAjaxJson("/bmsclueinfo/"+params.cId, "post", params, function(data){
			if(fn){fn(data);}
		}, false);
	}
	
	AVRIL.getEmployeeList = function(fn){
		myAjax("/employee?personStatus=0", "get", null, function(data){
			if(fn){fn(data);}
		}, false);
	}
	
	AVRIL.zeroEmployeeCount = function(fn){
		myAjax("/employees", "post", null, function(data){
			if(fn){fn(data);}
		}, false);
	}
	
	AVRIL.getClueInfo = function(cid, fn){
		myAjax("/clueinfo/"+cid, "get", null, function(data){
			if(fn){fn(data);}
		}, false);
	};
	
	AVRIL.queryUserByToken = function(token, fn){
		myAjax("/token/query/"+token, "post", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, true);		
	}
	
	AVRIL.doTokenCheckActionBack = function(data){
		if(data!=null && data.ucode.length>0){
			AVRIL.setUser(data.ucode, data.uname, data.token);
			if(data.ulevel=='1'){
				window.location.href="index1.html?t="+AVRIL.userToken();
			}else if(data.ulevel=='3'){
				window.location.href="index2.html?t="+AVRIL.userToken();
			}else if(data.ulevel=='9'){
				window.location.href="adm.html?t="+AVRIL.userToken();
			}else{
				window.location.href="index1.html?t="+AVRIL.userToken();
			}
		}else{
			AVRIL.clearUser();
			window.location.href="login.html";
		}
	}
	
	
	//查询交易数据
	AVRIL.queryClueList=function(fn, pn, ps, searchWord, searchStatus){
		var sou = "";
		if(searchWord!=undefined && searchWord.length>0){
			sou = "&cCustomer="+searchWord;
		}
		if(searchStatus!=undefined && searchStatus.length>0){
			sou += "&statuss="+searchStatus;
		}
		myAjax("/clueinfos?sort=%7B%22gmtModify%22%3A%22desc%22%7D&pageNo="+pn+"&pageSize="+ps+sou, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	AVRIL.loadDicList=function(fn, pn, ps, searchWord, searchStatus){
		var sou = "";
		if(searchWord!=undefined && searchWord.length>0){
			sou = "&dicValue="+searchWord;
		}
		if(searchStatus!=undefined && searchStatus.length>0){
			sou = "&dicStatus="+searchStatus;
		}
		myAjax("/dictionarys?sort=%7B%22dicGroup%22%3A%22asc%22%2C%22dicFlag%22%3A%22asc%22%7D&pageNo="+pn+"&pageSize="+ps+sou, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	AVRIL.saveDicInfo = function(fn, params){
		var url = '/dictionary';
		//params.dicGroup = 'config_ele_group';
		if(params.dicId!=undefined && params.dicId.length>0){
			url += ('/'+params.dicId);
			//params.dicGroup = undefined;
		}
		myAjaxJson(url, url=='/dictionary'?"post":"put", params, function(data){			
			//if(data!=undefined && data.batchCode!=undefined){
				if(fn){fn(data);}				
			//}
		}, false);
	};
	
	
	AVRIL.deleteDirInfo = function(fn, dicId){
		myAjax("/dictionary/"+dicId, "delete", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	//获取用户信息
	AVRIL.loadUserList=function(fn, pn, ps, searchWord, searchStatus){
		var sou = "";
		if(searchWord!=undefined && searchWord.length>0){
			sou = "&uname="+searchWord;
		}
		if(searchStatus!=undefined && searchStatus.length>0){
			sou += "&ustatus="+searchStatus;
		}
		myAjax("/userinfos?sort=%7B%22gmtModify%22%3A%22desc%22%7D&pageNo="+pn+"&pageSize="+ps+sou, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	
	AVRIL.saveUserInfo = function(fn, params){
		var url = '/userinfo';
		if(params.type!=undefined && params.type=='mod'){
			url += ('/'+params.ucode);
		}
		myAjaxJson(url, url=='/userinfo'?"post":"put", params, function(data){		
			if(fn){fn(data);}
		}, false);
	};
	
	AVRIL.loadEmployeeList=function(fn, pn, ps, searchWord, searchStatus){
		var sou = "";
		if(searchWord!=undefined && searchWord.length>0){
			sou = "&personName="+searchWord;
		}
		if(searchStatus!=undefined && searchStatus.length>0){
			sou += "&personStatus="+searchStatus;
		}
		myAjax("/employees?sort=%7B%22gmtModify%22%3A%22desc%22%7D&pageNo="+pn+"&pageSize="+ps+sou, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	
	AVRIL.saveEmployeeInfo = function(fn, params){
		var url = '/employee';
		if(params.pid!=undefined && params.pid.length>0){
			url += ('/'+params.pid);
		}
		myAjaxJson(url, url=='/employee'?"post":"put", params, function(data){		
			if(fn){fn(data);}	
		}, false);
	};
	
	AVRIL.deleteEmployeeInfo = function(fn, pid){
		myAjax("/employee/"+pid, "delete", {}, function(data){			
			if(fn){				
				fn(data);
			}
		}, false);
	};
	
	AVRIL.loadMailList = function(fn, pn, ps, sk){
		if(sk==undefined){
			sk = '';
		}
		myAjax("/mailhistorys?sort=%7B%22gmtCreated%22%3A%22desc%22%7D&pageNo="+pn+"&pageSize="+ps+"&titile="+sk, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	}
	
	AVRIL.saveBusinessInfo = function(fn, params){		
		myAjaxJson('/bmsBusiness/'+params.bId, "post", params, function(data){
			if(fn){fn(data);}	
		}, false);
	};
	
	AVRIL.saveReplyInfo= function(fn, params){		
		myAjaxJson('/bmsReply/'+params.bId, "post", params, function(data){
			if(fn){fn(data);}	
		}, false);
	};
	
	AVRIL.closeBusiness= function(params, fn){		
		myAjaxJson('/bmsBusiness/'+params.bId+"?bNote="+params.bNote, "delete", null, function(data){
			if(fn){fn(data);}	
		}, false);
	};
	
	AVRIL.dealBusiness= function(params, fn){
		myAjaxJson('/bmsDealBusiness/'+params.bId, "post", params, function(data){
			if(fn){fn(data);}	
		}, false);
	};
	
	
	
	
	AVRIL.getBusinessInfo= function(bid, fn){
		myAjax("/businessinfo/"+bid, "get", null, function(data){
			if(fn){fn(data);}
		}, false);
	};
	
	AVRIL.queryBusinessList = function(fn, pn, ps, searchWord, searchStatus){
		var sou = "";
		if(searchWord!=undefined && searchWord.length>0){
			sou = "&cCustomer="+searchWord;
		}
		if(searchStatus!=undefined && searchStatus.length>0){
			sou += "&sjStatus="+searchStatus;
		}
		myAjax("/businessinfos?sort=%7B%22gmtModify%22%3A%22desc%22%7D&pageNo="+pn+"&pageSize="+ps+sou, "get", {}, function(data){			
			if(fn){
				fn(data);
			}
		}, false);
	};
	
	/**
	 * 
	 * 初始化分页条
	 * @param pageBe
	 */
	AVRIL.initPageBar = function(pageBe){
		var maxPage;
		if(pageBe==undefined || pageBe.totalCount==0){
			maxPage=1;
			$(".pager").html("<li class=\"active\"><a href=\"javascript:void(0);\" onclick=\"goPage(1)\" class=\"on\">刷新</a></li>");
			//无数据
			return;
		}
		if(pageBe.totalPages==1){
			$(".pager").html("<li class=\"active\"><a href=\"javascript:void(0);\" onclick=\"goPage(1)\" class=\"on\">1</a></li>");
			return;
		}
		var khtml = "";
		if(pageBe.hasPrePage==true){
			khtml+="<li class=\"previous\"><a href=\"javascript:void(0);\" onclick=\"goPage("+(pageBe.page-1)+")\">« 上一页</a></li>";
		}
		if(pageBe.totalPages<10){
			var phtml=khtml;
			for(var j=0; j<pageBe.totalPages; j++){
				phtml += "<li "+(j+1==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage("+(j+1)+")\" >"+(j+1)+"</a></li>";
			}
			if(pageBe.page!=pageBe.totalPages){
				phtml+="<li class=\"next\"><a href=\"javascript:void(0);\" onclick=\"goPage("+(pageBe.page+1)+")\">下一页  »</a></li>";
			}
			$(".pager").html(phtml);
			return;
		}
		khtml += "<li "+(1==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage(1)\" >1</a></li><li "+(2==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage(2)\" >2</a></li>";
		var cpage = pageBe.page; //准备中间页
		if(pageBe.page<6){
			//在头5页时
			cpage=3;
		}else if(pageBe.page>pageBe.totalPages-4){
			cpage=pageBe.totalPages-6;
			khtml+=" <li><a href=\"javascript:void(0);\">⋯</a></li>";
		}else{
			cpage=pageBe.page-2;
			khtml+=" <li><a href=\"javascript:void(0);\">⋯</a></li>";
		}
		
		for(var k=0; k<5; k++){
			khtml+=("<li "+((cpage+k)==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage("+(cpage+k)+")\" >"+(cpage+k)+"</a></li>");
		}
		if(pageBe.page<pageBe.totalPages-4){
			khtml+=" <li><a href=\"javascript:void(0);\">⋯</a></li>";
		}
		khtml += ("<li "+((pageBe.totalPages-1)==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage("+(pageBe.totalPages-1)+")\" >"+(pageBe.totalPages-1)+"</a></li><li "+(pageBe.totalPages==pageBe.page?"class=\"active\"":"")+"><a href=\"javascript:void(0);\" onclick=\"goPage("+(pageBe.totalPages)+")\" >"+(pageBe.totalPages)+"</a></li>");
		if(pageBe.hasNextPage==true){
			khtml+="<li class=\"next\"><a href=\"javascript:void(0);\" onclick=\"goPage("+(pageBe.page+1)+")\">下一页  »</a></li>";
		}
		$(".pager").html(khtml);
		return;
	};
	
})(jQuery);