function myAjax(action, type, data, success, async){
        var url = action;
        var asy = async;
        var type = type || 'post';
        $.ajax({
            cache: false,
            async: asy,
            url: url,
            data: data,
            timeout: 600000,
            dataType: 'json',
            type: type,
            success: function(rs){
                if (!success) {
                	return;
                }                    
                success(rs);
            },
            error: function(xhr, textStatus, errorThrown){             	
            	new $.zui.Messager(xhr.status+'操作数据失败：('+xhr.responseText+')', {
					type: 'danger',
					placement: 'top-right',
				    close: false // 禁用关闭按钮
				}).show();
                console.log('发现系统错误 <BR>错误码：'+xhr.status + xhr.responseText);
            }
        });
    };
    
function myAjaxJson(action, type, data, success, async){
        var url = action;
        var asy = async;
        var type = type || 'post';
        $.ajax({
            cache: false,
            async: asy,
            contentType : "application/json;charset=utf-8", //内容类型
            url: url,
            data: JSON.stringify(data),
            timeout: 600000,
            dataType: 'json',
            type: type,
            success: function(rs){
                if (!success) {
                	return;
                }                    
                success(rs);
            },
            error: function(xhr, textStatus, errorThrown){             	
            	new $.zui.Messager(xhr.status+'操作数据失败json：('+xhr.responseText+')', {
					type: 'danger',
					placement: 'top-right',
				    close: false // 禁用关闭按钮
				}).show();
                console.log('发现系统错误 <BR>错误码：'+xhr.status + xhr.responseText);
            }
        });
    };
    
    
function myAjaxp(action, type, data, success, async){
        var url = action;
        var asy = async;
        $.ajax({
            cache: false,
            async: asy,
            url: "http://127.0.0.1:8086"+url,
            data: data,
            timeout: 600000,
            dataType: 'jsonp',
            jsonp:'callback',  
            jsonpCallback:"callback",
            type: 'get',
            success: function(rs){
                if (!success) {
                	return;
                }                    
                success(rs);
            },
            error: function(result, b){
                console.log('发现系统错误 <BR>错误码：' + result.status);
            }
        });
    };
