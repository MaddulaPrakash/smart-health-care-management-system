Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.form.Panel' ]);
Ext.Loader.setConfig( {
	enabled : true
});
Ext.tip.QuickTipManager.init();


var hideConfirmationMsg;
var showConfirmationMsg;
/* Hide the Confirmation Message */
hideConfirmationMsg = function() {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = "";
	confMsgDiv.dom.style.display = 'none';
}
/* Show Confirmation Message */
showConfirmationMsg = function(msg) {
	var confMsgDiv = Ext.get('confirmationMessage');
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}

Ext.onReady(function() {

	var contentPanel = Ext.create('Ext.form.Panel', {
		title: 'Change Password',
		width : 800,
		height : 300,
		defaults : {
		padding : '15 50 15 50',
			labelAlign : 'top'
		},
		layout : {
			type : 'table',
			columns : 2
		},
		items:[
				{
						xtype : 'textfield',
						fieldLabel : 'Enter the Current Password:',
						allowBlank : false,
						id : 'currentPassword',
						name : 'currentPassword',
						maxLength:20,
						minLength:10,
						msgTarget:'under',
						width:150,
						enforceMaxLength :true,
						minLengthText :'Min Length of the Current Password is 10',
						blankText :'Please enter Current Password'
				},
				{
						xtype : 'textfield',
						fieldLabel : 'Enter the New Password:',
						allowBlank : false,
						msgTarget:'under',
						id : 'newPassword',
						name : 'newPassword',
						maxLength:20,
						minLength:10,
						width:150,
						enforceMaxLength :true,
						minLengthText :'Min Length of the New Password is 10',
						blankText :'Please enter New Password'
				},
				{
					xtype:'button',
				    text: 'Change Password',
					id: 'Save',
					width : 150,
					disabled: false,
			        handler: function (store,btn, args) {
						
							var passwordFormat=generateJSONRequestForChangePassword();
							urlLink=contextPath+'/isro/changePassword.do';
							hideConfirmationMsg();
							doChangePasswordRequest(passwordFormat, urlLink);
			            }
				},
				{		
						xtype:'button',
			            text: 'Reset',
						id: 'Reset',
						width : 150,
						disabled: false,
			            handler: function () {
							contentPanel.getForm().reset();
						}
				}],
				renderTo:'changepasswordcontainer'
    });
	
	
	

	function generateJSONRequestForChangePassword()
	{
		var passwordFormat={};
		var currentPassword=Ext.getCmp('currentPassword').getValue();
		if(currentPassword!=null)
		{
			passwordFormat.currentPassword=currentPassword;
		}
		
		var newPassword=Ext.getCmp('newPassword').getValue();
		if(newPassword!=null)
		{
			passwordFormat.newPassword=newPassword;
		}
		return passwordFormat;
	}
	
	function doChangePasswordRequest(passwordFormat, urlLink)
	{
			var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
			loadMask.show();
			Ext.Ajax.request({	
			method: 'POST',
			processData: false,
			contentType:'application/json',
			jsonData: Ext.encode(passwordFormat),
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
								loadMask.hide();
							}
							else
							{
								var value=JsonData.message;
								showConfirmationMsg(value);
								contentPanel.hide();
								loadMask.hide();
							}
						}
			},
		failure : function(data) {
			contentPanel.hide();
			loadMask.hide();
		}
		});
	}
	
	
});
