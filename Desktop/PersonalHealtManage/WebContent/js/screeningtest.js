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

var questionStore;
Ext.onReady(function() {

	questionStore = Ext.create('Ext.data.Store', {
		storeId : 'questionStore',
		fields : [ 'quesId', 'questDesc', 'ans1', 'ans2', 'ans3', 'ans4',
				'rating1', 'rating2', 'rating3', 'rating4', 'selectedAnswer',
				'sug1', 'sug2', 'sug3', 'sug4' ],
		proxy : {
			type : 'ajax',
			url : 'answers.json',
			reader : {
				type : 'json'
			}
		}
	});
	questionStore.load();
	var screeningGridPanel = Ext.create('Ext.grid.Panel', {
		id : 'screeningGrid',
		title : 'Screening Grid',
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
		} ],
		selType : 'cellmodel',
		plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		}) ],
		height : 200,
		width : 850,
		forceFit : true
	});

	var diaPanel = Ext.create('Ext.panel.Panel', {
		id : 'diapanel',
		collapsible : true,
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'Enter the Test Name:',
			msgTarget : 'under',
			id : 'testName',
			name : 'testName',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			width : 150,
			enforceMaxLength : true,
			minLengthText : 'Test Name must have minimum of 5 characters',
			blankText : 'Please enter Test Name'
		}

		, screeningGridPanel, {
			xtype : 'button',
			text : 'Submit Answers',
			id : 'submitQ',
			handler : function(btn, args) {
				var questDetails = generateQuestionList();
				if (questDetails) {
					var questionFormat = generateQuestionList(questDetails);
					hideConfirmationMsg();
					var urlLink = contextPath + '/dia/screeningTest.do';
					doRequest(questionFormat, urlLink);
				}

			}
		} ],
		renderTo : Ext.getBody()
	});

	function generateQuestionList() {

		// Add a Logic for Screening Test Name

		// Logic for Screening Test Questions

		var quesStoreObj = Ext.getCmp('screeningGrid').getStore();
		var countOfRecords = quesStoreObj.getCount();

		var questListObj = {};

		var questionObjList = new Array();

		for (i = 0; i < countOfRecords; i++) {
			var questionObj = {};
			var obj = quesStoreObj.getAt(i);
			var quesId = obj.get('quesId');
			questionObj.quesId = quesId;
			var quesDesc = obj.get('quesDesc');
			questionObj.quesDesc = quesDesc;
			var ans1 = obj.get('ans1');
			questionObj.ans1 = ans1;
			var ans2 = obj.get('ans2');
			questionObj.ans2 = ans2;
			var ans3 = obj.get('ans3');
			questionObj.ans3 = ans3;
			var ans4 = obj.get('ans4');
			questionObj.ans4 = ans4;
			var subAns = obj.get('selectedAnswer');
			questionObj.subAns = subAns;
			var rating1 = obj.get('rating1');
			questionObj.rating1 = rating1;
			var rating2 = obj.get('rating2');
			questionObj.rating2 = rating2;
			var rating3 = obj.get('rating3');
			questionObj.rating3 = rating3;
			var rating4 = obj.get('rating4');
			questionObj.rating4 = rating4;
			var sug1 = obj.get('sug1');
			questionObj.sug1 = sug1;
			var sug2 = obj.get('sug2');
			questionObj.sug2 = sug2;
			var sug3 = obj.get('sug3');
			questionObj.sug3 = sug3;
			var sug4 = obj.get('sug4');
			questionObj.sug4 = sug4;
			questionObjList.push(questionObj);
		}

		questListObj.quesList = questionObjList;
		return questListObj;

	}

	function doRequest(reqFormat, urlLink) {
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "Loading"
		});
		loadMask.show();
		Ext.Ajax.request( {
			method : 'POST',
			processData : false,
			contentType : 'application/json',
			jsonData : Ext.encode(reqFormat),
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
						}

					} else {
						var value = JsonData.message;
						showConfirmationMsg(value);
						diaPanel.hide();
					}
					loadMask.hide();
				}
			},
			failure : function(data) {
				Ext.Msg.alert('Status', 'No Response From Server.');
				loadMask.hide();
			}
		});
	}

});