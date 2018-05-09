Ext.require('Ext.chart.*');
Ext.require('Ext.layout.container.Fit');
Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.form.*', 'Ext.layout.container.Column', 'Ext.tab.Panel']);
Ext.Loader.setConfig({
    enabled: true
});



Ext.onReady(function () {
	
	Ext.define('pateintModel', {
		extend : 'Ext.data.Model',
		fields : [ 
		           {name:'patName', mapping:'patName',type:'string'},
		           {name:'rating', mapping:'rating',type:'int'},
				   {name:'testName', mapping:'testName',type:'string'},
				   {name:'timeStamp', mapping:'timeStamp',type:'string'}
				   
		          ]
		});
	
	var pateintStore = Ext.create('Ext.data.Store', {
		id : 'pateintStoreId',
		name : 'pateintStoreName',
		model : 'pateintModel',
		proxy : {
			type : 'ajax',
			url :contextPath+'/dia/trackhistoryDisease.do',
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
					Ext.Msg.alert('status','Stress Graph Could not be Plotted at this point of Time');
			
				}			
		}
		},
		autoLoad : true
	});
	pateintStore.load();
	
	Ext.create('Ext.chart.Chart', {
	    renderTo: Ext.getBody(),
	    width: 500,
	    height: 300,
	    animate: true,
	    store: pateintStore,
	    axes: [{
	        type: 'Numeric',
	        position: 'bottom',
	        fields: ['rating'],
	        label: {
	            renderer: Ext.util.Format.numberRenderer('0,0')
	        },
	        title: 'Rating',
	        grid: true,
	        minimum: 0
	    }, {
	        type: 'Category',
	        position: 'left',
	        fields: ['testName'],
	        title: 'Screening Tests'
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
	            this.setTitle(storeItem.get('timeStamp'));
	          }
	        },
	        label: {
	          display: 'insideEnd',
	            field: 'rating',
	            renderer: Ext.util.Format.numberRenderer('0'),
	            orientation: 'horizontal',
	            color: '#333',
	            'text-anchor': 'middle'
	        },
	        xField: 'testName',
	        yField: 'rating'
	    }]
	});	
	

});    
           