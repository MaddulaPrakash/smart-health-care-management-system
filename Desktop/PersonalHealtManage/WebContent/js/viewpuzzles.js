Ext.QuickTips.init(); 
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

var puzzleStore;
Ext.onReady(function() {

	puzzleStore = Ext.create('Ext.data.Store', {
		storeId : 'puzzleStore',
		fields : [ 'puzzleQId', 'questionDesc', 'answer1', 'answer2', 'answer3', 'answer4',
				'correctAnswer', 'name', 'agegroup', 'contentType'],
		proxy : {
			type : 'ajax',
			url : contextPath + '/dia/viewpuzzle.do',
			reader : {
				type : 'json',
				root : 'model'
			}
		},
		listeners : {
			'load' : function(store, records) {

				var count = store.getCount();

				if (count <= 0) {
					Ext.Msg.alert('Status', 'No Puzzle Questions have been Found');

				}
			}
		}

	});
	puzzleStore.load();
	var screeningGridPanel = Ext.create('Ext.grid.Panel', {
		id : 'screeningGrid',
		store : Ext.data.StoreManager.lookup('puzzleStore'),
		columns : [ {
			header : 'Puzzle Id',
			dataIndex : 'puzzleQId',
			width : 50,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},{
			header : 'Question Description',
			dataIndex : 'questionDesc',
			width : 400,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer1',
			dataIndex : 'answer1',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer2',
			dataIndex : 'answer2',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Answer3',
			dataIndex : 'answer3',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}

		}, {
			header : 'Answer4',
			dataIndex : 'answer4',
			width : 100,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		}, {
			header : 'Correct Answer',
			dataIndex : 'correctAnswer',
			flex : 1,
			width : 50,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
		},{
			header : 'Age Group',
			dataIndex : 'agegroup',
			flex : 1,
			width : 70,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				
				if(v==1){
					meta.tdAttr = 'data-qtip="' + '20 to 25 years' + '"';
					return v;
				}else if(v==2){
					meta.tdAttr = 'data-qtip="' + '26 to 30 years' + '"';
					return v;
				}else if(v==3){
					meta.tdAttr = 'data-qtip="' + '31 to 40  years' + '"';
					return v;
				}else{
					meta.tdAttr = 'data-qtip="' + 'Above 45 years' + '"';
					return v;
				}
				
				
				
			}
		},{
			header : 'Name',
			dataIndex : 'name',
			flex : 1,
			width : 70,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
			
		},{
			header : 'Content Type',
			dataIndex : 'contentType',
			flex : 1,
			width : 70,
			renderer : function(v, meta, record, rowIdx, colIdx, store, view) {
				meta.tdAttr = 'data-qtip="' + v + '"';
				return v;
			}
			
		}
		],
		height : 200,
		width : 1200,
		forceFit : true,
		renderTo:Ext.getBody()
	});

});