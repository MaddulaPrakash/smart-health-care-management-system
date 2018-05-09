Ext.QuickTips.init(); 
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

Ext.define('ratModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'testName',
		mapping : 'testName',
		type : 'string'
	}, {
		name : 'r1LL',
		mapping : 'r1LL',
		type : 'int'
	}, {
		name : 'r1HL',
		mapping : 'r1HL',
		type : 'int'
	}, {
		name : 'r2LL',
		mapping : 'r2LL',
		type : 'int'
	}, {
		name : 'r2HL',
		mapping : 'r2HL',
		type : 'int'
	}, {
		name : 'r3LL',
		mapping : 'r3LL',
		type : 'int'
	}, {
		name : 'r3HL',
		mapping : 'r3HL',
		type : 'int'
	} ]
});

var ratStore=Ext.create('Ext.data.Store',
		{
	storeId : 'ratStore',
	fields : [ 'r1LL','r1HL','r2LL','r2HL','r3LL','r3HL','testName' ],
});

Ext.onReady(function() {

	Ext.create('Ext.data.Store',
			{
				storeId : 'questionStore',
				fields : [ 'questDesc', 'ans1', 'ans2', 'ans3', 'ans4',
						'rating1', 'rating2', 'rating3', 'rating4', 'sug1',
						'sug2', 'sug3', 'sug4' ],
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			});

	var screeningGridPanel = Ext.create('Ext.grid.Panel', {
		id : 'screeningGrid',
		store : Ext.data.StoreManager.lookup('questionStore'),
		columns : [ {
			header : 'Question Description',
			dataIndex : 'questDesc',
			editor : 'textarea'
		}, {
			header : 'Answer1',
			dataIndex : 'ans1',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Answer2',
			dataIndex : 'ans2',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Answer3',
			dataIndex : 'ans3',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Answer4',
			dataIndex : 'ans4',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Rating1',
			dataIndex : 'rating1',
			flex : 1,
			editor : {
				xtype : 'numberfield',
				anchor : '100%',
				name : 'rating1',
				value : 1,
				minValue : 1
			}
		}, {
			header : 'Rating2',
			dataIndex : 'rating2',
			flex : 1,
			editor : {
				xtype : 'numberfield',
				anchor : '100%',
				name : 'rating2',
				value : 1,
				minValue : 1
			}
		}, {
			header : 'Rating3',
			dataIndex : 'rating3',
			flex : 1,
			editor : {
				xtype : 'numberfield',
				anchor : '100%',
				name : 'rating1',
				value : 1,
				minValue : 1
			}
		}, {
			header : 'Rating4',
			dataIndex : 'rating4',
			flex : 1,
			editor : {
				xtype : 'numberfield',
				anchor : '100%',
				name : 'rating4',
				value : 1,
				minValue : 1
			}
		}, {
			header : 'Suggestion 1',
			dataIndex : 'sug1',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Suggestion 2',
			dataIndex : 'sug2',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Suggestion 3',
			dataIndex : 'sug3',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		}, {
			header : 'Suggestion 4',
			dataIndex : 'sug4',
			flex : 1,
			editor : {
				xtype : 'textarea',
				allowBlank : false
			}
		} ],
		selType : 'cellmodel',
		plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1
		}) ],
		height : 200
	});

	var diaPanel = Ext.create('Ext.panel.Panel', {
		id : 'diapanel',
		collapsible : true,
		title : 'Stress Screening Test',
		items : [ {
			xtype : 'textfield',
			fieldLabel : 'Enter the Test Name:',
			msgTarget : 'under',
			id : 'testName',
			name : 'testName',
			allowBlank : false,
			maxLength : 50,
			minLength : 5,
			width : 300,
			enforceMaxLength : true,
			minLengthText : 'Test Name must have minimum of 5 characters',
			blankText : 'Please enter Test Name'
		}, screeningGridPanel, {
			xtype : 'button',
			text : 'Add New Question',
			id : 'addNewQ',
			handler : function(btn, args) {
				var storeObj = screeningGridPanel.getStore();
				var r = {
					"questDesc" : "",
					"ans1" : "",
					"ans2" : "",
					"rating1" : 1,
					"ans3" : "",
					"ans4" : "",
					"rating2" : 2,
					"rating3" : 3,
					"rating4" : 4,
					"sug1" : "",
					"sug2" : "",
					"sug3" : "",
					"sug4" : ""
				};
				storeObj.insert(storeObj.getCount(), r);
				storeObj.commitChanges();
			}
		}, {
			xtype : 'button',
			text : 'Submit Test Questions',
			id : 'submitQ',
			handler : function(btn, args) {

				var status = checkForValidations();
				if (status == true) {
					var questDetails = generateQuestionList();
					if (questDetails) {
						urlLink = contextPath + '/dia/createScreenTest.do';
						hideConfirmationMsg();
						generateJSONReqForQuesAndSubmit(questDetails, urlLink);
					}

				}
			}

		} ],
		renderTo : Ext.getBody()
	});

	function checkForValidations() {

		// First get the no of records
		var quesStoreObj = Ext.getCmp('screeningGrid').getStore();
		var countOfRecords = quesStoreObj.getCount();
		if (countOfRecords < 4) {
			showErrorMsg("Please Enter Questions. Questions Must be Minimum of Four");
			return false;
		}

		if (countOfRecords >= 4) {
			for (i = 0; i < countOfRecords; i++) {
				var obj = quesStoreObj.getAt(i);

				var rating1 = obj.get('rating1');
				var rating2 = obj.get('rating2');
				var rating3 = obj.get('rating3');
				var rating4 = obj.get('rating4');
				hideErrorMsg();
				if (rating1 == rating2 || rating1 == rating3
						|| rating1 == rating4) {
					var j = i + 1;
					showErrorMsg("Question " + j + "has Two Rating Same");
					return false;
				}
				// Validating Question Desc
				var quesTionDesc = obj.get('questDesc');
				if (quesTionDesc) {
					var len = quesTionDesc.length;
					if (len <= 10) {
						var j = i + 1;
						showErrorMsg("Question "
								+ j
								+ " Description must have a minimum of 10 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Question " + j
							+ " Description cannot be Empty");
					return false;
				}
				// Validating Answer1 Desc
				var ans1Desc = obj.get('ans1');
				if (ans1Desc) {
					var len = ans1Desc.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Answer1 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Answer1 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating Answer1 Desc
				var ans2Desc = obj.get('ans2');
				if (ans2Desc) {
					var len = ans2Desc.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Answer2 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Answer2 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating Answer3 Desc
				var ans3Desc = obj.get('ans3');
				if (ans3Desc) {
					var len = ans3Desc.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Answer3 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Answer3 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating Answer4 Desc
				var ans4Desc = obj.get('ans4');
				if (ans4Desc) {
					var len = ans4Desc.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Answer4 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Answer4 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating for Suggestion1
				var sug1 = obj.get('sug1');
				if (sug1) {
					var len = sug1.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Suggestion1 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Suggestion1 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating for Suggestion2
				var sug2 = obj.get('sug2');
				if (sug2) {
					var len = sug2.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Suggestion2 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Suggestion2 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating for Suggestion3
				var sug3 = obj.get('sug3');
				if (sug3) {
					var len = sug3.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Suggestion3 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Suggestion3 for Question " + j
							+ " cannot be Empty");
					return false;
				}

				// Validating for Suggestion4
				var sug4 = obj.get('sug4');
				if (sug4) {
					var len = sug4.length;
					if (len <= 5) {
						var j = i + 1;
						showErrorMsg("Suggestion4 for Question" + j
								+ "  must have a minimum of 5 characters");
						return false;
					}
				} else {
					var j = i + 1;
					showErrorMsg("Suggestion4 for Question " + j
							+ " cannot be Empty");
					return false;
				}

			}
		}
		return true;

	}

	function generateQuestionList() {

		// Add a Logic for Screening Test Name
		var testNameTxtObj = Ext.getCmp('testName');

		var testName = testNameTxtObj.getValue();

		// Logic for Screening Test Questions

		var quesStoreObj = Ext.getCmp('screeningGrid').getStore();
		var countOfRecords = quesStoreObj.getCount();

		var questListObj = {};

		var questionObjList = new Array();

		for (i = 0; i < countOfRecords; i++) {
			var questionObj = {};
			var obj = quesStoreObj.getAt(i);
			var quesDesc = obj.get('questDesc');
			questionObj.questDesc = quesDesc;
			var ans1 = obj.get('ans1');
			questionObj.ans1 = ans1;
			var ans2 = obj.get('ans2');
			questionObj.ans2 = ans2;
			var ans3 = obj.get('ans3');
			questionObj.ans3 = ans3;
			var ans4 = obj.get('ans4');
			questionObj.ans4 = ans4;
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

		questListObj.questionList = questionObjList;
		questListObj.testName = testName;
		return questListObj;

	}

	function generateJSONReqForQuesAndSubmit(topologyGenFormat, urlLink) {
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "Loading"
		});
		loadMask.show();
		Ext.Ajax.request( {
			method : 'POST',
			processData : false,
			contentType : 'application/json',
			jsonData : Ext.encode(topologyGenFormat),
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
//						ratStore.removeAll();
						var modelObj = JsonData.model;
						if (modelObj) {

							var testName = modelObj.testName;

							var r1LL = modelObj.r1LL;

							var r1HL = modelObj.r1HL;

							var r2LL = modelObj.r2LL;

							var r2HL = modelObj.r2HL;

							var r3LL = modelObj.r3LL;

							var r3HL = modelObj.r3HL;
							
							var r4LL=modelObj.r4LL;
							
							var r4HL=modelObj.r4HL;
			
							var textMsg = "The Test name is : {<h2>"+testName+"</h2>}"+"<br/>No Stress Levels are :{"+r1LL +"to"+r1HL+"}"+"<br/>"
							+"Low Stress Levels are :{"+r2LL +"to"+r2HL+"}"+"<br/>"
							+"Medium Stress Levels are :{"+r3LL +"to"+r3HL+"}"+"<br/>"
							+"High Stress Levels are :{"+r4LL +"to"+r4HL+"}"+"<br/>";
		

							showConfirmationMsg(textMsg);
						}

					}
					loadMask.hide();
				}
			},
			failure : function(data) {
				loadMask.hide();
			}
		});
	}

	
});