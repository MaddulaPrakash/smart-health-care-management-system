	Ext.Loader.setConfig({
    enabled: true
});

var hideConfirmationMsg;
var showConfirmationMsg;
var hideErrorMsg;
var showErrorMsg;
/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
}
	/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
		var confMsgDiv = Ext.get('confirmationMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
}

/* Hide the Error Message */
hideErrorMsg = function() {
		var confMsgDiv = Ext.get('errorMessage');
		confMsgDiv.dom.innerHTML = "";
		confMsgDiv.dom.style.display = 'none';
}
	/* Show the Error Message */
showErrorMsg = function(msg) {
		var confMsgDiv = Ext.get('errorMessage');
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
}




var genRequest;
var doRequest;
Ext.onReady(function () {






genRequest =function(param1,param2)
{
var eligibleObj={};
var userId=param1;
var password=param2;
eligibleObj.loginuserId=userId;
eligibleObj.password=password;
eligibleObj.loginType=1;
return eligibleObj;

}

doRequest =function(reqFormat, urlLink)
	{
			var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
			loadMask.show();
			Ext.Ajax.request({	
			method: 'POST',
			processData: false,
			contentType:'application/json',
			jsonData: Ext.encode(reqFormat),
			url:urlLink, 
			success: function(response) {
			var data;
			if (response){
						 
						var JsonData = Ext.decode(response.responseText);
							if(JsonData.ebErrors != null){
								var errorObj=JsonData.ebErrors;
								for(i=0;i<errorObj.length;i++)
								{
										var value=errorObj[i].errMessage;
										showConfirmationMsg(value);
								}
								
							}
							else
							{
								var value=JsonData.message;
								showConfirmationMsg(value);
								diaPanel.hide();
							}
							loadMask.hide();
						}
			},
		failure : function(data) {
			Ext.Msg.alert('Status', 'No Response From Server.');
			loadMask.hide();
		}
		});
	}

 
});