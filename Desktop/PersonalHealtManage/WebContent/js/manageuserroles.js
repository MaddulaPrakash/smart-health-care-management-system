Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel' ]);
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
var webSiteStore;
Ext.onReady(function() {

	Ext.define('userRole', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'roleId',
			mapping : 'roleId',
			type : 'int'
		}, {
			name : 'roleKey',
			mapping : 'roleKey',
			type : 'string'
		}, {
			name : 'roleDesc',
			mapping : 'roleDesc',
			type : 'string'
		} ],
		idProperty : 'roleId'
	});

	var userRoleStore = Ext.create('Ext.data.Store', {
		id : 'userStoreId',
		name : 'userStoreName',
		model : 'userRole',
		proxy : {
			type : 'ajax',
			url : contextPath + '/dia/userRoles.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				totalProperty : 'totalSize'
			}
		},
		listeners : {
			'load' : function(store, records) {
			}
		},
		autoLoad : true
	});
	userRoleStore.load();

	var contentPanel = Ext.create('Ext.form.Panel', {
		title : 'Create Role',
		collapsible:true,
		defaults : {
			labelAlign : 'top',
			padding:'10px 10px 10px 10px',
			width:'auto'
		},
		layout : {
			type : 'table',
			columns : 3
		},
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'Enter the First Name:',
			msgTarget : 'under',
			id : 'firstName',
			name : 'firstName',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			enforceMaxLength : true,
			minLengthText : 'First Name must have minimum of 5 characters',
			blankText : 'Please enter First Name'
		},
		{
			xtype : 'textfield',
			fieldLabel : 'Enter the Last Name:',
			msgTarget : 'under',
			id : 'lastName',
			name : 'lastName',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			enforceMaxLength : true,
			minLengthText : 'Last Name must have minimum of 5 characters',
			blankText : 'Please enter Last Name'
		},
		{
			xtype : 'textfield',
			fieldLabel : 'Enter the User Id:',
			msgTarget : 'under',
			id : 'userId',
			name : 'userId',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			enforceMaxLength : true,
			minLengthText : 'User must have minimum of 5 characters',
			blankText : 'Please enter User Id'
		},
		{
			xtype : 'textfield',
			fieldLabel : 'Enter the User Id:',
			msgTarget : 'under',
			id : 'userId',
			name : 'userId',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			enforceMaxLength : true,
			minLengthText : 'User must have minimum of 5 characters',
			blankText : 'Please enter User Id'
		},
		{
			xtype : 'textfield',
			fieldLabel : 'Enter the Password:',
			msgTarget : 'under',
			id : 'userPassword',
			name : 'userPassword',
			allowBlank : false,
			maxLength : 50,
			minLength : 6,
			enforceMaxLength : true,
			minLengthText : 'User must have minimum of 6 characters',
			blankText : 'Please enter Password'
		},
		{
			xtype : 'textfield',
			fieldLabel : 'Enter the Email Id:',
			msgTarget : 'under',
			id : 'email',
			name : 'email',
			allowBlank : false,
			maxLength : 50,
			minLength : 8,
			enforceMaxLength : true,
			minLengthText : 'Email must have minimum of 8 characters',
			blankText : 'Please enter Email'
		},
		{
			xtype : 'combo',
			fieldLabel : 'Assign Role:',
			allowBlank : false,
			id : 'loginType',
			name : 'loginType',
			displayField : 'roleDesc',
			valueField : 'roleId',
			store : userRoleStore,
			triggerAction : 'all'
		},{
			xtype : 'button',
			text : 'Submit',
			id : 'Save',
			disabled : false,
			handler : function(store, btn, args) {

				var roleFormat = generateJSONRequestForRole();
				urlLink = contextPath + '/dia/createUser.do';
				hideConfirmationMsg();
				doJSONRequestForRole(roleFormat, urlLink);
			}
		}, {
			xtype : 'button',
			text : 'Reset',
			id : 'Reset',
			disabled : false,
			handler : function() {
				contentPanel.getForm().reset();
			}
		} ],
		renderTo : 'rolediv'
	});

	function generateJSONRequestForRole() {
		
		var roleFormat = {};
		var firstName = Ext.getCmp('firstName').getValue();
		if (firstName != null) {
			roleFormat.firstName = firstName;
		}

		var lastName = Ext.getCmp('lastName').getValue();
		if (lastName != null) {
			roleFormat.lastName = lastName;
		}
		
		var userId = Ext.getCmp('userId').getValue();
		if (userId != null) {
			roleFormat.userId = userId;
		}
		
		var userPassword = Ext.getCmp('userPassword').getValue();
		if (userPassword != null) {
			roleFormat.userPassword = userPassword;
		}
		
		var emailId = Ext.getCmp('email').getValue();
		if (emailId != null) {
			roleFormat.emailId = emailId;
		}
		
		var loginType = Ext.getCmp('loginType').getValue();
		if (loginType != null) {
			roleFormat.loginType = loginType;
		}
		
		
		return roleFormat;
	}

	function doJSONRequestForRole(roleFormat, urlLink) {
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "Loading"
		});
		loadMask.show();
		Ext.Ajax.request( {
			method : 'POST',
			processData : false,
			contentType : 'application/json',
			jsonData : Ext.encode(roleFormat),
			url : urlLink,
			success : function(response) {
				var data;
				if (response) {

					var JsonData = Ext.decode(response.responseText);
					if (JsonData.ebErrors != null) {
						var errorObj = JsonData.ebErrors;
						for (i = 0; i < errorObj.length; i++) {
							var value = errorObj[i].errMessage;
							showConfirmationMsg(value);
						}
						loadMask.hide();
					} else {
						var value = JsonData.message;
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
