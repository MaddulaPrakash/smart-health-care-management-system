Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.dd.*'
]);

Ext.define('doctorModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'docId',
		mapping : 'docId',
		type : 'string'
	}, {
		name : 'docName',
		mapping : 'docName',
		type : 'string'
	}]
});
var doctorStore;

//Remote Store of doctors
doctorStore = Ext.create('Ext.data.Store', {
	id : 'doctorStoreId',
	name : 'doctorStoreName',
	model : 'doctorModel',
	proxy : {
		type : 'ajax',
		url : contextPath + '/dia/doctorsList.do',
		extraParams : {},
		actionMethods : {
			read : 'POST'
		},
		reader : {
			type : 'json'
				}
	},
	listeners : {
		'load' : function(store, records) {

			}
	},
	autoLoad : true
});

doctorStore.load();


var diaPanel=Ext.create('Ext.form.Panel', {
	id:'diapanel',
	collapsible:true,
	title:'Doctor Assigment',
	width:300,
	items:[
			{
				xtype : 'combo',
				fieldLabel : 'Doctor Names',
				id : 'docId',
				name : 'docName',
				queryMode:'local',
				editable : false,
				displayField : 'docName',
				valueField : 'docId',
				triggerAction : 'all',
				store : doctorStore
			},
			{
				xtype : 'numberfield',
				fieldLabel : 'Row',
				id : 'rowNo',
				name : 'rowNo'
			},
			{
				xtype:'button',
				text:'Update Doctor Information',
				id:'updateDoc',
				handler: function (btn,args) {
							var gridObj=Ext.getCmp('secondgrid');
							var secondGridStoreObj=gridObj.getStore();
							var count=secondGridStoreObj.getCount();
								var rowNoObj=Ext.getCmp('rowNo');
								var rowNo=rowNoObj.getValue();
								var docName=Ext.getCmp('docId').getValue();
					
							if(count==0)
							{
								alert("Grid has No Rows");
							}
							if(rowNo<count)
							{
								alert("Row Number is Invalid");
							}
							else
							{
							if(secondGridStoreObj.getCount()>0)
								secondGridStoreObj.getAt(rowNo-1).set('docId',docName);
								secondGridStoreObj.commitChanges();
							}
						}
			}
		  ]
	});


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
            	}];

var appcolumns = [
               {
            		header : 'Appoint Id',
            		dataIndex : 'appointId',
            		sortable : true,
            		width : 100
            	}, 
            	{
            		header : 'Pateint Name',
            		dataIndex : 'patName',
            		sortable : true,
            		width : 100
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
            		header : 'Date',
            		dataIndex : 'date',
            		width:100,
            	    editor:
            		{
            	            xtype: 'textfield'
                     }
            	},
            	{
            		header : 'In Time',
            		dataIndex : 'fromTime',
            		width:100,
            		  editor: new Ext.form.field.ComboBox({
                          typeAhead: true,
                          triggerAction: 'all',
                          store: [
                              ['10:00AM','10:00AM'],
                              ['10:30AM','10:30AM'],
                              ['11:00AM','11:00AM'],
                              ['11:30AM','11:30AM'],
                              ['12:00PM','12:00PM'],
                              ['12:30PM','12:30PM'],
                              ['1:00PM','1:00PM'],
                              ['1:30PM','1:30PM'],
                              ['2:00PM','2:00PM'],
                              ['2:30PM','2:30PM'],
                              ['3:00PM','3:00PM'],
                              ['3:30PM','3:30PM'],
                              ['4:00PM','4:00PM'],
                              ['4:30PM','4:30PM'],
                              ['5:00PM','5:00PM'],
                              ['5:30PM','5:30PM'],
                              ['6:00PM','6:00PM']
                          ]
                      })
            		},
            	{
            		header : 'Out Time',
            		dataIndex : 'toTime',
            		width:100,
            		editor: new Ext.form.field.ComboBox({
                        typeAhead: true,
                        triggerAction: 'all',
                        store: [
                            ['10:00AM','10:00AM'],
                            ['10:30AM','10:30AM'],
                            ['11:00AM','11:00AM'],
                            ['11:30AM','11:30AM'],
                            ['12:00PM','12:00PM'],
                            ['12:30PM','12:30PM'],
                            ['1:00PM','1:00PM'],
                            ['1:30PM','1:30PM'],
                            ['2:00PM','2:00PM'],
                            ['2:30PM','2:30PM'],
                            ['3:00PM','3:00PM'],
                            ['3:30PM','3:30PM'],
                            ['4:00PM','4:00PM'],
                            ['4:30PM','4:30PM'],
                            ['5:00PM','5:00PM'],
                            ['5:30PM','5:30PM'],
                            ['6:00PM','6:00PM']
                        ]
                    })
            	 	},
            	 	{
                		header : 'Doctors',
                		dataIndex : 'docId',
                		width:100
                	}];




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
			url : contextPath + '/dia/retriveAppointmentsDisease.do',
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
	
	appointStore.load();

    // declare the source Grid
    var firstGrid = Ext.create('Ext.grid.Panel', {
        multiSelect: true,
        width:300,
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
    	width:700,
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
        columns          : appcolumns,
        stripeRows       : true,
        selType: 'cellmodel',
        plugins: [
            Ext.create('Ext.grid.plugin.CellEditing', {
                clicksToEdit: 1
            })
        ],
        title            : 'Appointments to Be Approved',
        margins          : '0 0 0 3'
    });

    //Simple 'border layout' panel to house both grids
    var displayPanel = Ext.create('Ext.Panel', {
        width        : 1400,
        height       : 300,
        layout       : {
            type: 'hbox',
            align: 'stretch',
            padding: 5
        },
        renderTo     : 'appointContainer',
        items        : [
            firstGrid,
            secondGrid,
            diaPanel
        ],
        dockedItems: {
            xtype: 'toolbar',
            dock: 'bottom',
			id:'toolbar',
            items: ['->', // Fill
			{
            	
            	text:'Approve Pateint',
            	handler:function()
            	{
            		var dataToSubmit=generateJSONRequestForDeleteData();
        			var urlLink = contextPath + '/dia/approveAppointDisease.do';
            		doDeleteTableSubmissionRequest(dataToSubmit, urlLink);
            	}
            },
            {
                text: 'Reset',
                handler: function(){
                    //refresh source grid
                    appointStore.load();

                    //purge destination grid
                    secondGridStore.removeAll();
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
			alert("Please Select the Appointments to be Approved");
		}
		var webSubArray =new Array();

		for (i = 0; i < count; i++) {
			webSubArray.push(
			{appointId:storeObj.getAt(i).get('appointId'),
	  	     date:storeObj.getAt(i).get('date'),
			 fromTime:storeObj.getAt(i).get('fromTime'),
			 toTime:storeObj.getAt(i).get('toTime'),
			 docId:storeObj.getAt(i).get('docId')
			}
			);
		}
		deleteData.appointmentList=webSubArray;
		return deleteData;
	}
    
});