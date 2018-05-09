Ext.require('Ext.chart.*');
Ext.require('Ext.layout.container.Fit');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});



Ext.onReady(function () {
	
	Ext.define('ageGroupClassification', {
		extend : 'Ext.data.Model',
		fields : [ 
		           {name:'noOfPersons', mapping:'noOfPersons',type:'int'},
		           {name:'stressLabel', mapping:'stressLabel',type:'string'},
				   {name:'group', mapping:'group',type:'string'}
				   
		          ]
		});
	
	var ageGroupClassificationStore1 = Ext.create('Ext.data.Store', {
		id : 'ageGroupClassificationStoreId1',
		name : 'ageGroupClassificationStoreName1',
		model : 'ageGroupClassification',
		proxy : {
			type : 'ajax',
			url :contextPath+'/dia/sexclassifyUnRegister1Disease.do',
			actionMethods:{
				read:'POST'
			},
			reader : {
				type :'json',
				root:'model',
				totalProperty:'totalSize'
			}
		},
		listeners:
		{
			'load':function(store, records){
			
				if(store.getCount()==0)
				{
					Ext.Msg.alert('status','Group Analysis Graph Could not be Plotted at this point of Time');
			
				}			
		}
		},
		autoLoad : true
	});
	ageGroupClassificationStore1.load();


	var ageGroupClassificationStore2 = Ext.create('Ext.data.Store', {
		id : 'ageGroupClassificationStoreId2',
		name : 'ageGroupClassificationStoreName2',
		model : 'ageGroupClassification',
		proxy : {
			type : 'ajax',
			url :contextPath+'/dia/sexclassifyUnRegister2Disease.do',
			actionMethods:{
				read:'POST'
			},
			reader : {
				type :'json',
				root:'model',
				totalProperty:'totalSize'
			}
		},
		listeners:
		{
			'load':function(store, records){
			
				if(store.getCount()==0)
				{
					Ext.Msg.alert('status','Group Analysis Graph Could not be Plotted at this point of Time');
			
				}			
		}
		},
		autoLoad : true
	});
	ageGroupClassificationStore2.load();
	
	
	
	Ext.create('Ext.chart.Chart', {
	    renderTo: Ext.getBody(),
	    width: 500,
	    height: 300,
	    animate: true,
	    store: ageGroupClassificationStore1,
	    axes: [{
	        type: 'Numeric',
	        position: 'bottom',
	        fields: ['noOfPersons'],
	        label: {
	            renderer: Ext.util.Format.numberRenderer('0,0')
	        },
	        title: 'Number of People',
	        grid: true,
	        minimum: 0
	    }, {
	        type: 'Category',
	        position: 'left',
	        fields: ['stressLabel'],
	        title: 'Disease Name'
	    }],
	    series: [{
	        type: 'bar',
	        axis: 'bottom',
	        highlight: true,
	        tips: {
	          trackMouse: true,
	          width: 140,
	          height: 28,
	          renderer: function(storeItem, item) {
	            this.setTitle('MALE');
	          }
	        },
	        label: {
	          display: 'insideEnd',
	            field: 'noOfPersons',
	            renderer: Ext.util.Format.numberRenderer('0'),
	            orientation: 'horizontal',
	            color: '#333',
	            'text-anchor': 'middle'
	        },
	        xField: 'stressLabel',
	        yField: 'noOfPersons'
	    }]
	});	
	
	Ext.create('Ext.chart.Chart', {
	    renderTo: Ext.getBody(),
	    width: 500,
	    height: 300,
	    animate: true,
	    store: ageGroupClassificationStore2,
	    axes: [{
	        type: 'Numeric',
	        position: 'bottom',
	        fields: ['noOfPersons'],
	        label: {
	            renderer: Ext.util.Format.numberRenderer('0,0')
	        },
	        title: 'Number of People',
	        grid: true,
	        minimum: 0
	    }, {
	        type: 'Category',
	        position: 'left',
	        fields: ['stressLabel'],
	        title: 'Disease Name'
	    }],
	    series: [{
	        type: 'bar',
	        axis: 'bottom',
	        highlight: true,
	        tips: {
	          trackMouse: true,
	          width: 140,
	          height: 28,
	          renderer: function(storeItem, item) {
	            this.setTitle('FEMALE');
	          }
	        },
	        label: {
	          display: 'insideEnd',
	            field: 'noOfPersons',
	            renderer: Ext.util.Format.numberRenderer('0'),
	            orientation: 'horizontal',
	            color: '#333',
	            'text-anchor': 'middle'
	        },
	        xField: 'stressLabel',
	        yField: 'noOfPersons'
	    }]
	});	
	
});    
           