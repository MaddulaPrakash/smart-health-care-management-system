Ext.Loader.setConfig({enabled: true});

Ext.Loader.setPath('Ext.ux', '../extjs41/ux');
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.toolbar.Paging',
    'Ext.ux.PreviewPlugin',
    'Ext.ModelManager',
    'Ext.tip.QuickTipManager'
]);

 var storeQuestion;
var pluginExpanded = true;
  
Ext.onReady(function(){
    Ext.tip.QuickTipManager.init();

    Ext.define('screenTestModel', {
        extend: 'Ext.data.Model',
           fields : [ 'testId', 'quesId', 'questDesc', 'testName', 'ans1', 'ans2',
				'ans3', 'ans4', 'rating1', 'rating2', 'rating3', 'rating4',
				'selectedAnswer', 'sug1', 'sug2', 'sug3', 'sug4' ],
        idProperty: 'quesId'
    });

    // create the Data Store
     storeQuestion = Ext.create('Ext.data.Store', {
        pageSize: 50,
        model: 'screenTestModel',
        remoteSort: false,
        proxy: {
            type: 'json',
            url: contextPath + '/dia/viewscreenpagination.do',
            reader: {
                root: 'model'
            },
            simpleSortMode: false
        }
    });

      var grid = Ext.create('Ext.grid.Panel', {
        width: 700,
        height: 500,
        title: 'Stress Manage',
        store: storeQuestion,
        disableSelection: true,
        loadMask: true,
        viewConfig: {
            id: 'gv',
            trackOver: false,
            stripeRows: false,
            plugins: [{
                ptype: 'preview',
                bodyField: 'excerpt',
                expanded: true,
                pluginId: 'preview'
            }]
        },
        // grid columns
        columns:[
		 {
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
		}, {
			header : 'Selected Answer',
			dataIndex : 'selectedAnswer',
			flex : 1,
			editor : {
				xtype : 'numberfield',
				anchor : '100%',
				name : 'selectedAnswer',
				value : 1,
				maxValue : 4,
				minValue : 1
			},
			width : 70
		} 
		],
        // paging bar on the bottom
		bbar: {
              xtype: 'pagingtoolbar',
              pageSize: 50,
              store: storeQuestion,
              displayInfo: true
            },
        renderTo: 'topic-grid'
    });

    // trigger the data store load
    storeQuestion.loadPage(1);
});