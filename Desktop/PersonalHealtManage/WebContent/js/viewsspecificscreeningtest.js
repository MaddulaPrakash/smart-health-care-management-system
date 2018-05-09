Ext.require( [ 'Ext.grid.*', 'Ext.data.*', 'Ext.form.*',
		'Ext.layout.container.Column', 'Ext.tab.Panel','Ext.data.ArrayStore' ]);
Ext.Loader.setConfig( {
	enabled : true
});


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
var testIdStore;
var questionStore;
Ext.onReady(function() {
	
	testIdStore=Ext.create('Ext.data.Store', {
	    storeId:'testIdStore',
	    fields:['testId'],
	    proxy: {
	        type: 'ajax',
			url:contextPath+'/dia/testnames.do',
	        reader: {
	            type: 'json'
	                }
	    },
		listeners : {
			'load' : function(store, records) {
			}
		}
	});
	testIdStore.load();
	

	questionStore = Ext.create('Ext.data.Store', {
		storeId : 'questionStore',
		fields : [ 'testId', 'quesId', 'questDesc', 'testName', 'ans1', 'ans2',
				'ans3', 'ans4', 'rating1', 'rating2', 'rating3', 'rating4',
				 'sug1', 'sug2', 'sug3', 'sug4' ],
		proxy : {
			type : 'ajax',
			params:{
				testId:''
			},
			url : contextPath + '/dia/viewspecifictest.do',
			reader : {
				type : 'json',
				root : 'model'
			}
		},
		listeners : {
			'load' : function(store, records) {
			}
		}

	});
	questionStore.load();
	var screeningGridPanel = Ext.create('Ext.grid.Panel', {
		id : 'screeningGrid',
		store : Ext.data.StoreManager.lookup('questionStore'),
		columns : [ {
			header : 'Question Description',
			dataIndex : 'questDesc',
			width : 400,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer1',
			dataIndex : 'ans1',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer2',
			dataIndex : 'ans2',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer3',
			dataIndex : 'ans3',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}

		}, {
			header : 'Answer4',
			dataIndex : 'ans4',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},
		{
			header : 'Suggestion1',
			dataIndex : 'sug1',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},
		{
			header : 'Suggestion2',
			dataIndex : 'sug2',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},
		{
			header : 'Suggestion3',
			dataIndex : 'sug3',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},
		{
			header : 'Suggestion4',
			dataIndex : 'sug4',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}],
		selType : 'cellmodel',
		plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		}) ],
		height : 200,
		width : 1200,
		forceFit : true,
		renderTo:'screentestcontainer'
	});

	var diaPanel = Ext.create('Ext.panel.Panel', {
		title:'Stress Panel',
		id : 'diapanel',
		collapsible : true,
		items : [{
			xtype : 'combo',
			fieldLabel : 'Test Names',
			id : 'testId',
			name : 'testId',
			queryMode:'local',
			editable : false,
			displayField : 'testId',
			valueField : 'testId',
			triggerAction : 'all',
			store : testIdStore,
			listeners:{
				'select':function(combo,records)
				{
						var testIdSelected=Ext.getCmp('testId').getValue();
						sendTestTable(testIdSelected);		
				}
			}
		},screeningGridPanel],
		renderTo : Ext.getBody()
	});

	function sendTestTable(testIdSelected)
	{
		var store=Ext.getCmp('screeningGrid').getStore();
		store.load(
			{
				url :contextPath+'/dia/viewspecifictest.do',
				params:{
						testId:testIdSelected
						}
			}
		);
			
	}

});