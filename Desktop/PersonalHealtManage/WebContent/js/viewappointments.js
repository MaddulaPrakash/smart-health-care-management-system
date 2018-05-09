Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.dd.*'
]);

var delcolumns=[{
	header : 'Appoint Id',
	dataIndex : 'appointId',
	sortable : true,
	width : 80
}, 
{
	header : 'Pateint Name',
	dataIndex : 'patName',
	sortable : true,
	width : 80
}];

// Column Model shortcut array
var columns = [
               {
            		header : 'Appoint Id',
            		dataIndex : 'appointId',
            		sortable : true,
            		width : 80
            	}, 
            	{
            		header : 'Pateint Name',
            		dataIndex : 'patName',
            		sortable : true,
            		width : 80
            	},
            	{
            		header : 'Status',
            		dataIndex : 'status',
            		sortable : true,
            		width : 100,
            		renderer : function(value) {
                		
            			if(value==1)
            			{
            				return "APPROVED";
            			}
            			else if(value==2)
            			{
            				return "UNAPPROVED";
            			}
            			else
            			{
            				return "UNKNOWN";
            			}
            	}
            	},
            	{
            		header:'Doctor Name',
            		dataIndex:'docId',
            		sortable:true,
            		width:100
            	},
            	{
            		header : 'Date',
            		dataIndex : 'date',
            		width:100
            	 },
            	{
            		header : 'In Time',
            		dataIndex : 'fromTime',
            		width:100
            	 },
            	{
            		header : 'Out Time',
            		dataIndex : 'toTime',
            		width:100
            	}
            	
            	
];


Ext.define('appointModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'appointId',
		mapping : 'appointId',
		type : 'int'
	}, {
		name : 'patName',
		mapping : 'pateintName',
		type : 'string'
	}, {
		name : 'status',
		mapping : 'status',
		type : 'int'
	},
	{
		name : 'date',
		mapping : 'date',
		type : 'string'
	},
	{
		name : 'fromTime',
		mapping : 'fromTime',
		type : 'string'
	},
	{
		name : 'toTime',
		mapping : 'toTime',
		type : 'string'
	},
	{
		name : 'toTime',
		mapping : 'toTime',
		type : 'string'
	},
	{
		name : 'docId',
		mapping : 'docId',
		type : 'string'
	}
	]
});

var appointStore;

Ext.onReady(function(){
	
	
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
	
	//Remote Store of Appointments
	appointStore = Ext.create('Ext.data.Store', {
		id : 'appointStoreId',
		name : 'appointStoreName',
		model : 'appointModel',
		proxy : {
			type : 'ajax',
			url : contextPath + '/dia/retriveAppointmentsForDoc.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				root:'model',
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
	
	

    // declare the source Grid
    var firstGrid = Ext.create('Ext.grid.Panel', {
    	width:700,
        multiSelect: true,
        viewConfig: {
            plugins: {
                ptype: 'gridviewdragdrop',
                dragGroup: 'firstGridDDGroup',
                dropGroup: 'secondGridDDGroup'
            },
            listeners: {
                drop: function(node, data, dropRec, dropPosition) {
                    var dropOn = dropRec ? ' ' + dropPosition + ' ' + dropRec.get('appointId') : ' on empty view';
                }
            }
        },
        store            : appointStore,
        columns          : columns,
        stripeRows       : true,
        title            : 'List of Appointments',
        margins          : '0 2 0 0'
    });

    var secondGridStore = Ext.create('Ext.data.Store', {
        model: 'appointModel'
    });

    // create the destination Grid
    var secondGrid = Ext.create('Ext.grid.Panel', {
    	id:'secondgrid',
    	width:200,
        viewConfig: {
            plugins: {
                ptype: 'gridviewdragdrop',
                dragGroup: 'secondGridDDGroup',
                dropGroup: 'firstGridDDGroup'
            },
            listeners: {
                drop: function(node, data, dropRec, dropPosition) {
                    var dropOn = dropRec ? ' ' + dropPosition + ' ' + dropRec.get('appointId') : ' on empty view';
                }
            }
        },
        store            : secondGridStore,
        columns          : delcolumns,
        stripeRows       : true,
        title            : 'Appointments to Be Deleted',
        margins          : '0 0 0 3'
    });

    //Simple 'border layout' panel to house both grids
    var displayPanel = Ext.create('Ext.Panel', {
        width        : 900,
        height       : 300,
        layout       : {
            type: 'hbox',
            align: 'stretch',
            padding: 5
        },
        renderTo     : 'appContainer',
        items        : [
            firstGrid,
            secondGrid
        ],
        dockedItems: {
            xtype: 'toolbar',
            dock: 'bottom',
            items: ['->', // Fill
            {
                text: 'Reset both grids',
                handler: function(){
                    //refresh source grid
                    appointStore.load();

                    //purge destination grid
                    secondGridStore.removeAll();
                }
            },
            {
            	
            	text:'Remove Pateint',
            	handler:function()
            	{
            		var dataToSubmit=generateJSONRequestForDeleteData();
        			var urlLink = contextPath + '/dia/deleteAppoint.do';
            		doDeleteTableSubmissionRequest(dataToSubmit, urlLink);
            	}
            	
            	
            }
            
            ]
        }
    });
    
    function doDeleteTableSubmissionRequest(nameOfTables, urlLink) {
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "Loading"
		});
		loadMask.show();
		Ext.Ajax.request( {
			method : 'POST',
			processData : false,
			contentType : 'application/json',
			jsonData : Ext.encode(nameOfTables),
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
							displayPanel.hide();
						}
						
					} else {
						var value = JsonData.message;
						showConfirmationMsg(value);
						secondGridStore.removeAll();
					}
				}
				loadMask.hide();
			},
			failure : function(data) {
				loadMask.hide();
			}
		});
	}
    
    
    function generateJSONRequestForDeleteData() {
		var deleteData = {};
		var storeObj = Ext.getCmp('secondgrid').getStore();
		var count = storeObj.getCount();

		if (count == 0) {
			alert("Please Select the Appointments to be Deleted");
		}
		var webSubArray =new Array();

		for (i = 0; i < count; i++) {
			webSubArray.push(storeObj.getAt(i).get('appointId'));
		}
		deleteData.appointmentList=webSubArray;
		return deleteData;
	}
    
});