Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel','Ext.data.ArrayStore' ]);
Ext.Loader.setConfig( {
	enabled : true
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
	confMsgDiv.dom.innerHTML = msg;
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
	confMsgDiv.dom.innerHTML = msg;
	confMsgDiv.dom.style.display = 'inline-block';
}

var dashboardcolumns = [ {
	header : 'Dashboard',
	dataIndex : 'dashboard',
	width : 400,
	renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
		meta.tdAttr = 'data-qtip="' + v + '"';
		return v;
	}
}];

Ext.define('dashboardModel', {
	extend : 'Ext.data.Model',
	fields : [ 
	           {name:'dashboard', mapping:'dashboard',type:'string'}
			 ]
});





var questionStore;
Ext.onReady(function() {

	questionStore = Ext.create('Ext.data.Store', {
		storeId : 'questionStore',
		model:'dashboardModel',
		proxy : {
			type : 'ajax',
			url : contextPath + '/dia/dashboard.do',
			reader : {
				type : 'json',
				root : 'model'
			}
		},
		listeners : {
			'load' : function(store, records) {

				var count = store.getCount();

				if (count <= 0) {
					Ext.Msg.alert('Status', 'Dashboard is Empty');

				}
			}
		}

	});
	questionStore.load();
	var screeningGridPanel = Ext.create('Ext.grid.Panel', {
		id : 'screeningGrid',
		store : Ext.data.StoreManager.lookup('questionStore'),
		columns :dashboardcolumns,
		height : 200,
		width : 400,
		forceFit : true,
		renderTo:'screentestcontainer'
	});

});