Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});
var dataSetGrid;
var dataSetsStore ;
Ext.tip.QuickTipManager.init();

var globalColumns=[

         			{
         				header : 'Total Measure Negative',
         				dataIndex : 'totalMeasureNegative',
         				sortable:true,
         				width:50
         			},
					{
         				header : 'Total Measure Positive',
         				dataIndex : 'totalMeasurePositive',
         				sortable:true,
         				width:50
         			},
					{
         				header : 'YES  Measure',
         				dataIndex : 'classYesProbability',
         				sortable:true,
         				width:50
         			},
					{
         				header : 'NO Measure',
         				dataIndex : 'classNoProbability',
         				sortable:true,
         				width:50
         			},
					{
         				header : 'Output Factor',
         				dataIndex : 'outputFactor',
         				sortable:true,
         				width:50
         			},
					{
         				header : 'Exception Message',
         				dataIndex : 'exceptionMessage',
         				sortable:true,
         				width:50
         			}

]	 
	var dataSetsColumn=[
         			{
         				header : 'Mean Positive',
         				dataIndex : 'meanPositive',
         				sortable:true,
         				width:50
         			},
         			{
         				header : 'Attribute Name',
         				dataIndex : 'attributeName',
         				sortable:true,
         				width    :150
         			},
         			{
         				header : 'Stanard Deviation Positive',
         				dataIndex : 'standardDevPositive',
         				sortable:true,
         				width    :150
         			},
					{
         				header : 'Total Sum Positive',
         				dataIndex : 'totalSumPositive',
         				sortable:true,
         				width    :150
         			},
					{
         				header : 'Positive Measure',
         				dataIndex : 'probabilityPositive',
         				sortable:true,
         				width    :150
         			},
					{
         				header : 'Mean Negative',
         				dataIndex : 'meanNegative',
         				sortable:true,
         				width:150
         			},
         			{
         				header : 'Stanard Deviation Negative',
         				dataIndex : 'standardDevNegative',
         				sortable:true,
         				width    :150
         			},
					{
         				header : 'Total Sum Negative',
         				dataIndex : 'totalSumNegative',
         				sortable:true,
         				width    :150
         			},
					{
         				header : 'Negative Measure',
         				dataIndex : 'probabilityNegative',
         				sortable:true,
         				width    :150
         			}
					
					
         			];





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
		confMsgDiv.dom.innerHTML =  msg;
		confMsgDiv.dom.style.display = 'inline-block';		
	}

	var globalStore;
	var dataSetsStore;
	
	var globalStoreLung;
	var dataSetsStoreLung;
	
	var globalStoreThy;
	var dataSetsStoreThy;
	
Ext.onReady(function () {

	var loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
	
	
	globalStore = Ext.create('Ext.data.ArrayStore', {
		id : 'dataSetsStoreId1',
		name : 'dataSetsStoreName1',
		fields : [ 
					{name:'totalMeasureNegative', mapping:'totalMeasureNegative',type:'double'},
		          	{name:'totalMeasurePositive', mapping:'totalMeasurePositive',type:'double'},
		          	{name:'classYesProbability', mapping:'classYesProbability',type:'double'},
					{name:'classNoProbability',mapping:'classNoProbability',type:'double'},
					{name:'outputFactor', mapping:'outputFactor',type:'boolean'},
		          	{name:'exceptionMessage', mapping:'exceptionMessage',type:'string'}
			      ]
	});
	
	

	globalStoreLung = Ext.create('Ext.data.ArrayStore', {
		id : 'globalStoreLungId',
		name : 'globalStoreLungName',
		fields : [ 
					{name:'totalMeasureNegative', mapping:'totalMeasureNegative',type:'double'},
		          	{name:'totalMeasurePositive', mapping:'totalMeasurePositive',type:'double'},
		          	{name:'classYesProbability', mapping:'classYesProbability',type:'double'},
					{name:'classNoProbability',mapping:'classNoProbability',type:'double'},
					{name:'outputFactor', mapping:'outputFactor',type:'boolean'},
		          	{name:'exceptionMessage', mapping:'exceptionMessage',type:'string'}
			      ]
	});
	

	globalStoreThy = Ext.create('Ext.data.ArrayStore', {
		id : 'globalStoreThyId',
		name : 'globalStoreThyName',
		fields : [ 
					{name:'totalMeasureNegative', mapping:'totalMeasureNegative',type:'double'},
		          	{name:'totalMeasurePositive', mapping:'totalMeasurePositive',type:'double'},
		          	{name:'classYesProbability', mapping:'classYesProbability',type:'double'},
					{name:'classNoProbability',mapping:'classNoProbability',type:'double'},
					{name:'outputFactor', mapping:'outputFactor',type:'boolean'},
		          	{name:'exceptionMessage', mapping:'exceptionMessage',type:'string'}
			      ]
	});
	
	
	dataSetsStore = Ext.create('Ext.data.ArrayStore', {
		id : 'dataSetsStoreId',
		name : 'dataSetsStoreName',
		fields : [ 
					{name:'meanPositive', mapping:'meanPositive',type:'double'},
		          	{name:'standardDevPositive', mapping:'standardDevPositive',type:'double'},
		          	{name:'totalSumPositive', mapping:'totalSumPositive',type:'double'},
					{name:'probabilityPositive',mapping:'probabilityPositive',type:'double'},
					{name:'meanNegative', mapping:'meanNegative',type:'double'},
		          	{name:'standardDevNegative', mapping:'standardDevNegative',type:'double'},
		          	{name:'totalSumNegative', mapping:'totalSumNegative',type:'double'},
					{name:'probabilityNegative',mapping:'probabilityNegative',type:'double'},
					{name:'attributeName',mapping:'attributeName',type:'string'}
			      ]
	});
	
	
	dataSetsStoreLung = Ext.create('Ext.data.ArrayStore', {
		id : 'dataSetsStoreLungId',
		name : 'dataSetsStoreLungName',
		fields : [ 
					{name:'meanPositive', mapping:'meanPositive',type:'double'},
		          	{name:'standardDevPositive', mapping:'standardDevPositive',type:'double'},
		          	{name:'totalSumPositive', mapping:'totalSumPositive',type:'double'},
					{name:'probabilityPositive',mapping:'probabilityPositive',type:'double'},
					{name:'meanNegative', mapping:'meanNegative',type:'double'},
		          	{name:'standardDevNegative', mapping:'standardDevNegative',type:'double'},
		          	{name:'totalSumNegative', mapping:'totalSumNegative',type:'double'},
					{name:'probabilityNegative',mapping:'probabilityNegative',type:'double'},
					{name:'attributeName',mapping:'attributeName',type:'string'}
			      ]
	});
	
	dataSetsStoreThy = Ext.create('Ext.data.ArrayStore', {
		id : 'dataSetsStoreThyId',
		name : 'dataSetsStoreThyName',
		fields : [ 
					{name:'meanPositive', mapping:'meanPositive',type:'double'},
		          	{name:'standardDevPositive', mapping:'standardDevPositive',type:'double'},
		          	{name:'totalSumPositive', mapping:'totalSumPositive',type:'double'},
					{name:'probabilityPositive',mapping:'probabilityPositive',type:'double'},
					{name:'meanNegative', mapping:'meanNegative',type:'double'},
		          	{name:'standardDevNegative', mapping:'standardDevNegative',type:'double'},
		          	{name:'totalSumNegative', mapping:'totalSumNegative',type:'double'},
					{name:'probabilityNegative',mapping:'probabilityNegative',type:'double'},
					{name:'attributeName',mapping:'attributeName',type:'string'}
			      ]
	});
	
	
	contentPanel = Ext
	.create(
			'Ext.form.Panel',
			{
				title : 'Predicting Hyper Tension',
				collapsible:true,
				width : 1200,
				height : 500,
				autoScroll : true,
				renderTo:'container',
				defaults : {
					padding : '15 15 15 15',
					labelAlign : 'top'
				},
				layout : {
					type : 'table',
					columns : 4
				},
				items : [
				        {
							xtype : 'textfield',
							fieldLabel : 'Enter FTI:',
							id : 'fti',
							name : 'fti',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'T3:',
							id : 't3',
							name : 't3',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'textfield',
							fieldLabel : 'T4U:',
							id : 't4U',
							name : 't4U',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TSH:',
							id : 'tsH',
							name : 'tsH',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TT4:',
							id : 'tt4',
							name : 'tt4',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'textfield',
							fieldLabel : 'Enter FTI Lung:',
							id : 'ftiLung',
							name : 'ftiLung',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'T3 Lung:',
							id : 't3Lung',
							name : 't3Lung',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'textfield',
							fieldLabel : 'T4U Lung:',
							id : 't4ULung',
							name : 't4ULung',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TSH Lung:',
							id : 'tsHLung',
							name : 'tsHLung',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TT4 Lung:',
							id : 'tt4Lung',
							name : 'tt4Lung',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'textfield',
							fieldLabel : 'Enter FTI Thyroid:',
							id : 'ftiThy',
							name : 'ftiThy',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'T3 Thyroid:',
							id : 't3Thy',
							name : 't3Thy',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'textfield',
							fieldLabel : 'T4U Thyroid:',
							id : 't4UThy',
							name : 't4UThy',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TSH Thyroid:',
							id : 'tsHThy',
							name : 'tsHThy',
							labelAlign : 'top',
							width : 150,
							maxLength:4,
							enforceMaxLength :true
	         				
						},{
							xtype : 'textfield',
							fieldLabel : 'TT4 Thyroid:',
							id : 'tt4Thy',
							name : 'tt4Thy',
							labelAlign : 'top',
							width : 150,
							maxLength:3,
							enforceMaxLength :true
	         				
						},
						{
							xtype : 'button',
							text : 'Determine Nav Bayes',
							id : 'Save',
							disabled : false,
							handler : function(store, btn, args) {

								var obj=generateJSONRequest();
								urlLink = contextPath + '/dia/performNavBayes.do';
								hideConfirmationMsg();
								doGenerationRequest(urlLink,obj);
							}
						} ]
			});	
	
	/* This is Panel for */
	
	notificationPanel = Ext
	.create(
			'Ext.form.Panel',
			{
				title : 'Notification Panel',
				collapsible:true,
				width : 500,
				height : 200,
				autoScroll : true,
				hidden:true,
				id:'notificationPanel',
				renderTo:'container',
				defaults : {
					padding : '15 100 15 100',
					labelAlign : 'top'
				},
				layout : {
					type : 'table',
					columns : 2
				},
				items : [
						{
							xtype : 'button',
							text : 'Appointment',
							id : 'notify',
							disabled : false,
							handler : function(store, btn, args) {
								var obj={};
								urlLink = contextPath + '/dia/appReq.do';
								hideConfirmationMsg();
								doGenerationRequest(urlLink,obj);
							}
						} ]
			});	

		
function generateJSONRequest()
{
	var obj={};
	//HyperTension
	var fti=Ext.getCmp('fti').getValue();
	if(fti!=null)
	{
		obj.fti=fti;
	}
	var t3=Ext.getCmp('t3').getValue();
	if(t3!=null)
	{
		obj.t3=t3;
	}
	var t4U=Ext.getCmp('t4U').getValue();
	if(t4U!=null)
	{
		obj.t4U=t4U;
	}
	var tsH=Ext.getCmp('tsH').getValue();
	if(tsH!=null)
	{
		obj.tsH=tsH;
	}
	var tt4=Ext.getCmp('tt4').getValue();
	if(tt4!=null)
	{
		obj.tt4=tt4;
	}
	
	// Lung Cancer
	var ftiLung=Ext.getCmp('ftiLung').getValue();
	if(ftiLung!=null)
	{
		obj.ftiLung=ftiLung;
	}
	var t3Lung=Ext.getCmp('t3Lung').getValue();
	if(t3Lung!=null)
	{
		obj.t3Lung=t3Lung;
	}
	var t4ULung=Ext.getCmp('t4ULung').getValue();
	if(t4ULung!=null)
	{
		obj.t4ULung=t4ULung;
	}
	var tsHLung=Ext.getCmp('tsHLung').getValue();
	if(tsHLung!=null)
	{
		obj.tsHLung=tsHLung;
	}
	var tt4Lung=Ext.getCmp('tt4Lung').getValue();
	if(tt4Lung!=null)
	{
		obj.tt4Lung=tt4Lung;
	}
	
	// Thyroid
	var ftiThy=Ext.getCmp('ftiThy').getValue();
	if(ftiThy!=null)
	{
		obj.ftiThy=ftiThy;
	}
	var t3Thy=Ext.getCmp('t3Thy').getValue();
	if(t3Thy!=null)
	{
		obj.t3Thy=t3Thy;
	}
	var t4UThy=Ext.getCmp('t4UThy').getValue();
	if(t4UThy!=null)
	{
		obj.t4UThy=t4UThy;
	}
	var tsHThy=Ext.getCmp('tsHThy').getValue();
	if(tsHThy!=null)
	{
		obj.tsHThy=tsHThy;
	}
	var tt4Thy=Ext.getCmp('tt4Thy').getValue();
	if(tt4Thy!=null)
	{
		obj.tt4Thy=tt4Thy;
	}
	
	
	
	return obj;
}
	
	dataSetGrid = Ext.create('Ext.grid.Panel', {
		title:'Naive Bayes Output Hypertension',
		forceFit : true,
		id : 'dataSetsGrid',
		store : dataSetsStore,
		columns : dataSetsColumn,
		width:1200,
		height:300,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainer',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});
	
	
	globalGrid = Ext.create('Ext.grid.Panel', {
		title:'Global Output Hyper Tension',
		forceFit : true,
		id : 'globalGrid',
		store : globalStore,
		columns : globalColumns,
		width:1200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainerGlobal',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});
	
	
	dataSetLungGrid = Ext.create('Ext.grid.Panel', {
		title:'Naive Bayes Output Lung Cancer',
		forceFit : true,
		id : 'dataSetLungGrid',
		store : dataSetsStoreLung,
		columns : dataSetsColumn,
		width:1200,
		height:300,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainerLung',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});
	
	
	globalGridLung = Ext.create('Ext.grid.Panel', {
		title:'Global Output Lung',
		forceFit : true,
		id : 'globalGridLung',
		store : globalStoreLung,
		columns : globalColumns,
		width:1200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainerGlobalLung',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});

	
	dataSetThyGrid = Ext.create('Ext.grid.Panel', {
		title:'Naive Bayes Output Thyroid',
		forceFit : true,
		id : 'dataSetThyGrid',
		store : dataSetsStoreThy,
		columns : dataSetsColumn,
		width:1200,
		height:300,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainerThy',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});
	
	
	globalGridThy = Ext.create('Ext.grid.Panel', {
		title:'Global Output Thyroid',
		forceFit : true,
		id : 'globalGridThy',
		store : globalStoreThy,
		columns : globalColumns,
		width:1200,
		height:200,
		autoFit : true,
		autoscroll:true,
		stripRows:true,
		renderTo: 'dataSetsContainerGlobalThy',
		collapsible:true,
		overflowY:'auto',
		hidden:true
	});

});
	

function doGenerationRequest(urlLink,obj)
{
loadMask = new Ext.LoadMask(Ext.getBody(), {msg:"Loading"});
loadMask.show();
Ext.Ajax.request({	
method: 'POST',
processData: false,
contentType:'application/json',
jsonData: Ext.encode(obj),
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
					
					//Compute the Processing of Data sets Computation
					processJSONDataAndViewDataSets(JsonData.model);
					
					contentPanel.hide();
					loadMask.hide();
				}
			}
},
failure : function(data) {
loadMask.hide();
}
});
}



	
	
function processJSONDataAndViewDataSets(obj){
	
	var notificationA =false;
	var notificationB =false;
	var notificationC =false;
	
	var rowArray = new Array();
	var tempObj=obj.hyperTensionOutput;
	
	var parentArray = new Array();
	if(tempObj){
			
			var totalMeasureNegative=tempObj.totalMeasureNegative;
			var totalMeasurePositive=tempObj.totalMeasurePositive;
			var classYesProbability=tempObj.classYesProbability;
			var classNoProbability=tempObj.classNoProbability;
			var outputFactor=tempObj.outputFactor;
			if(outputFactor==true){
				notificationA=true;
			}
			var exceptionMessage=tempObj.exceptionMessage;
			
			var attributeComputationModelListArray=tempObj.attributeComputationModelList;
			
			parentArray.push({'totalMeasureNegative':totalMeasureNegative,
								'totalMeasurePositive':totalMeasurePositive,
								'classYesProbability':classYesProbability,
								'classNoProbability':classNoProbability,
								'outputFactor':outputFactor,
								'exceptionMessage':exceptionMessage
								});
			
			var len =attributeComputationModelListArray.length;
			
			for(i=0;i<len;i++){
				
				var attributeInfoList = attributeComputationModelListArray[i];
				
					
					var meanPositive=attributeInfoList.meanPositive;
					var standardDevPositive=attributeInfoList.standardDevPositive;
					var totalSumPositive=attributeInfoList.totalSumPositive;
					var probabilityPositive=attributeInfoList.probabilityPositive;
					
					var meanNegative=attributeInfoList.meanNegative;
					var standardDevNegative=attributeInfoList.standardDevNegative;
					var totalSumNegative=attributeInfoList.totalSumNegative;
					var probabilityNegative=attributeInfoList.probabilityNegative;
					var attributeName  =attributeInfoList.attributeName;
					
					rowArray.push({
									'meanPositive':meanPositive,'standardDevPositive':standardDevPositive,
								   'totalSumPositive':totalSumPositive,'probabilityPositive':probabilityPositive,
								   'meanNegative':meanNegative,'standardDevNegative':standardDevNegative,
								   'totalSumNegative':totalSumNegative,'probabilityNegative':probabilityNegative,
								   'attributeName':attributeName
								   });
					
				}
			}
	
	
	// Load the Store
	var dataSetsStoreTemp=Ext.getCmp('dataSetsGrid').getStore();
	dataSetsStoreTemp.loadData(rowArray);
	Ext.getCmp('dataSetsGrid').show();
	
	//Now Process the Global Stuff
	var globalGridStoreTemp=Ext.getCmp('globalGrid').getStore();
	globalGridStoreTemp.loadData(parentArray);
	Ext.getCmp('globalGrid').show();
	
	//--------------------------------------------------------------------------------------------------------------------------------
	
	var tempObj=obj.lungcancerOutput;
	
	var parentArrayL = new Array();
	var rowArrayL = new Array();
	if(tempObj){
			
			var totalMeasureNegative=tempObj.totalMeasureNegative;
			var totalMeasurePositive=tempObj.totalMeasurePositive;
			var classYesProbability=tempObj.classYesProbability;
			var classNoProbability=tempObj.classNoProbability;
			var outputFactor=tempObj.outputFactor;
			if(outputFactor==true){
				notificationB=true;
			}
			var exceptionMessage=tempObj.exceptionMessage;
			
			var attributeComputationModelListArray=tempObj.attributeComputationModelList;
			
			parentArrayL.push({'totalMeasureNegative':totalMeasureNegative,
								'totalMeasurePositive':totalMeasurePositive,
								'classYesProbability':classYesProbability,
								'classNoProbability':classNoProbability,
								'outputFactor':outputFactor,
								'exceptionMessage':exceptionMessage
								});
			
			var len =attributeComputationModelListArray.length;
			
			for(i=0;i<len;i++){
				
				var attributeInfoList = attributeComputationModelListArray[i];
				
					
					var meanPositive=attributeInfoList.meanPositive;
					var standardDevPositive=attributeInfoList.standardDevPositive;
					var totalSumPositive=attributeInfoList.totalSumPositive;
					var probabilityPositive=attributeInfoList.probabilityPositive;
					
					var meanNegative=attributeInfoList.meanNegative;
					var standardDevNegative=attributeInfoList.standardDevNegative;
					var totalSumNegative=attributeInfoList.totalSumNegative;
					var probabilityNegative=attributeInfoList.probabilityNegative;
					var attributeName  =attributeInfoList.attributeName;
					
					rowArrayL.push({
									'meanPositive':meanPositive,'standardDevPositive':standardDevPositive,
								   'totalSumPositive':totalSumPositive,'probabilityPositive':probabilityPositive,
								   'meanNegative':meanNegative,'standardDevNegative':standardDevNegative,
								   'totalSumNegative':totalSumNegative,'probabilityNegative':probabilityNegative,
								   'attributeName':attributeName
								   });
					
				}
			}
	
	
	// Load the Store
	var dataSetsStoreTemp=Ext.getCmp('dataSetLungGrid').getStore();
	dataSetsStoreTemp.loadData(rowArrayL);
	Ext.getCmp('dataSetLungGrid').show();
	
	//Now Process the Global Stuff
	var globalGridStoreTemp=Ext.getCmp('globalGridLung').getStore();
	globalGridStoreTemp.loadData(parentArrayL);
	Ext.getCmp('globalGridLung').show();
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	
	var tempObj=obj.thyroidOutput;
	
	var parentArrayT = new Array();
	var rowArrayT = new Array();
	if(tempObj){
			
			var totalMeasureNegative=tempObj.totalMeasureNegative;
			var totalMeasurePositive=tempObj.totalMeasurePositive;
			var classYesProbability=tempObj.classYesProbability;
			var classNoProbability=tempObj.classNoProbability;
			var outputFactor=tempObj.outputFactor;
			if(outputFactor==true){
				notificationC=true;
			}
			var exceptionMessage=tempObj.exceptionMessage;
			
			var attributeComputationModelListArray=tempObj.attributeComputationModelList;
			
			parentArrayT.push({'totalMeasureNegative':totalMeasureNegative,
								'totalMeasurePositive':totalMeasurePositive,
								'classYesProbability':classYesProbability,
								'classNoProbability':classNoProbability,
								'outputFactor':outputFactor,
								'exceptionMessage':exceptionMessage
								});
			
			var len =attributeComputationModelListArray.length;
			
			for(i=0;i<len;i++){
				
				var attributeInfoList = attributeComputationModelListArray[i];
				
					
					var meanPositive=attributeInfoList.meanPositive;
					var standardDevPositive=attributeInfoList.standardDevPositive;
					var totalSumPositive=attributeInfoList.totalSumPositive;
					var probabilityPositive=attributeInfoList.probabilityPositive;
					
					var meanNegative=attributeInfoList.meanNegative;
					var standardDevNegative=attributeInfoList.standardDevNegative;
					var totalSumNegative=attributeInfoList.totalSumNegative;
					var probabilityNegative=attributeInfoList.probabilityNegative;
					var attributeName  =attributeInfoList.attributeName;
					
					rowArrayT.push({
									'meanPositive':meanPositive,'standardDevPositive':standardDevPositive,
								   'totalSumPositive':totalSumPositive,'probabilityPositive':probabilityPositive,
								   'meanNegative':meanNegative,'standardDevNegative':standardDevNegative,
								   'totalSumNegative':totalSumNegative,'probabilityNegative':probabilityNegative,
								   'attributeName':attributeName
								   });
					
				}
			}
	
	
	// Load the Store
	var dataSetsStoreTemp=Ext.getCmp('dataSetThyGrid').getStore();
	dataSetsStoreTemp.loadData(rowArrayT);
	Ext.getCmp('dataSetThyGrid').show();
	
	//Now Process the Global Stuff
	var globalGridStoreTemp=Ext.getCmp('globalGridThy').getStore();
	globalGridStoreTemp.loadData(parentArrayT);
	Ext.getCmp('globalGridThy').show();
	
	
	/*if(notificationA==true  || notificationB==true || notificationC==true ){
		Ext.getCmp('notificationPanel').show();
	}*/
	
	
}