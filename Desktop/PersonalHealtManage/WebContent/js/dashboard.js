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
	header : 'User Name',
	dataIndex : 'patName',
	sortable : true,
	width : 80
}];

// Column Model shortcut array
var columns = [
            	{
            		header : 'User Name',
            		dataIndex : 'patName',
            		sortable : true,
            		width : 80
            	},
            	{
            		header : 'Status',
            		dataIndex : 'status',
            		sortable : true,
            		width : 400,
            		renderer : function(value) {
                		
            			if(value==1)
            			{
            				return "Your Appointment has been approved";
            			}
            			else if(value==2)
            			{
            				return "Your Appointment is awaiting Approval";
            			} 
            			else
            			{
            				return "Please contact System Administrator";
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
			url : contextPath + '/dia/dashboard.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				root:'model',
				type : 'json'
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
    	width:1000,
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
        title            : 'Customer Appointment', 
        margins          : '0 2 0 0',
        renderTo:Ext.getBody()
    });


});