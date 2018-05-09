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
var patNameStore;
var testIdStore;
var questionStore;
Ext.onReady(function() {
	
	
	patNameStore=Ext.create('Ext.data.Store', {
	    storeId:'patNameStore',
	    fields:['patName'],
	    proxy: {
	        type: 'ajax',
			url:contextPath+'/dia/patnames.do',
	        reader: {
	            type: 'json',
	            root:'model'
	                }
	    },
		listeners : {
			'load' : function(store, records) {
			}
		}
	});
	patNameStore.load();
	
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
		fields : [ 'rating', 'stresslabel', 'testname','timeStamp'],
		proxy : {
			type : 'ajax',
			params:{
				testId:''
			},
			url : contextPath + '/dia/viewanalysisForPatNameAndTestId.do',
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
		columns : [{
			header : 'Rating',
			dataIndex : 'rating',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Stress Label',
			dataIndex : 'stresslabel',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Test Name',
			dataIndex : 'testname',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}

		}, {
			header : 'Time Stamp',
			dataIndex : 'timeStamp',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}

		}],
		height : 200,
		width : 1200,
		forceFit : true,
		renderTo:'screentestcontainer'
	});

	var diaPanel = Ext.create('Ext.panel.Panel', {
		title:'Patient History Panel',
		id : 'diapanel',
		collapsible : true,
		items : [
			{
				xtype : 'combo',
				fieldLabel : 'Pateint Names',
				id : 'patNameId',
				name : 'patNameId',
				queryMode:'local',
				editable : false,
				displayField : 'patName',
				valueField : 'patName',
				triggerAction : 'all',
				store : patNameStore,
				listeners:{
					'select':function(combo,records)
					{
							var patNameSelected=Ext.getCmp('patNameId').getValue();
							obtainTestNamesForPatName(patNameSelected);		
					}
				}
			},
			
			{
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
						var patName = Ext.getCmp('patNameId').getValue();
						obtainDataForTestIdAndPatName(testIdSelected,patName);		
				}
			}
		},screeningGridPanel],
		renderTo : Ext.getBody()
	});

	function obtainTestNamesForPatName(patName)
	{
		var store=Ext.getCmp('testId').getStore();
		store.load(
			{
				url :contextPath+'/dia/viewspecifictestForPatName.do',
				params:{
						patName:patName
						}
			}
		);
			
	}
	
	function obtainDataForTestIdAndPatName(testIdSelected,patName){
		
		var store=Ext.getCmp('screeningGrid').getStore();
		store.load(
			{
				url :contextPath+'/dia/viewanalysisForPatNameAndTestId.do',
				params:{
						patName:patName,
						testId:testIdSelected
						}
			}
		);
		
	}
	
	
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