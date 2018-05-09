package com.constants;

public interface PersonalHealtConstantsIF {

	public static final String MODEL_NAME = "obj";

	interface BusinessStressIF {
		String AGEGROUP1 = "20 to 25 years";
		String AGEGROUP2 = "26 to 30 years";
		String AGEGROUP3 = "31 to 40  years";
		String AGEGROUP4 = "Above 45 years";

		String INCOMEGROUP1 = "< 3 Lakhs";
		String INCOMEGROUP2 = "3 to 4 lakhs";
		String INCOMEGROUP3 = ">4 and < 6  lakhs";
		String INCOMEGROUP4 = ">6 lakhs";

		String PROFEXP1 = "<2 years";
		String PROFEXP2 = "2 to 5 years";
		String PROFEXP3 = ">5 and <8 years";
		String PROFEXP4 = ">8 years";
	}

	interface SessionIF {

		String ANSWER_SESSION = "ANSWERSESSION";
		String QUESTONVO_SESSION = "QUESTIONLIST";
		String DISPLAY_QUESTIONS = "DISPLAYQUESTIONS";
		String PUZZLEVO_QUES = "PUZZLEVO";
		String DISPLAY_QUESTIONS_DISEASE = "DISPLAYQUESTIONSDISEASE";
		String ANSWER_SESSION_DISEASE = "ANSWERSESSIONDISEASE";
		String QUESTONVO_SESSION_DISEASE = "QUESTIONLISTDISEASE";
		
	}

	interface Message {

		public static final String INTERNAL_ERROR = "Please Contact System Adminitrator. An Internal Error has Ocuurred";
		public static final String FIRSTNAME_EMPTY = "First Name cannot be Empty";
		public static final String LASTNAME_EMPTY = "Last Name cannot be Empty";
		public static final String USERID_EMPTY = "User ID Cannot be Empty";
		public static final String EMAIL_EMPTY = "Email Cannot be Empty";
		public static final String PASSWORD_EMPTY = "Password Cannot be Empty";
		public static final String USERREGISTERED_SUCESS_MSG = "User Has Registered Sucessfully.Please Login";
		public static final String USERALREADY_EXIST = "User Already Exist";
		public static final String NO_USER_EXISTS = "No User Already Exist";
		public static final String PASSWORD_WRONG = "Password does not exist";
		public static final String USER_LOGIN_SUCESS = "User Login is Sucessful";
		public static final String ACCNO_EMPTY = "Account No Cannot be Empty";
		public static final String IPIN_EMPTY = "IPIN Cannot be Empty";
		public static final String ACCNOLSIT_EMPTY = "Account No Not Found/List is Empty";
		public static final String INSUFFICENT_FUNDS = "Insufficient Funds";
		public static final String BALANCE_UPDATE_FAILED = "Balance Could not be Updated";
		public static final String INSERT_TRANS_FAILED = "Transaction Insertion has Failed";
		public static final String LOGIN_EMPTY = "Login Type Cannot be Empty";
		public static final String TESTNAME_EMPTY = "Test Name Cannot be Empty";
		public static final String QLIST_EMPTY = "Question List is Empty";
		public static final String TESTNAME_ALREADY_EXISTS = "Test Name Already Exist";
		public static final String APPOINT_LIST_EMPTY = "Appointment List is Empty";
		public static final String DEL_LIST_EMPTY = "Deletion Could not be Performed";
		public static final String DEL_APPOINTMENT_COMPLETE = "Deletion of the Appointments is Sucessful";
		public static final String APPOINT_LIST_TO_DEL_EMPTY = "Appointment List to be Removed are Unsucessful";
		public static final String DEL_APPID_FAIL = "Delete of Appointment has Failed";
		public static final String PNAME_EMPTY = "Please Select a Patient Name";
		public static final String APPROVE_APPOINTMENT_COMPLETE = "Approval of Appointments is Sucessful";
		public static final String APPOINT_LIST_TO_APPROVE_EMPTY = "Appointment List to be Approved is Empty";
		public static final String APPROVE_APPID_FAIL = "Approval of Appointment has Failed";
		public static final String ELIGIBILITY_CREATION_SUCESS = "Eligibility Creation is Sucessful";
		public static final String LOGINID_EMPTY = "Login ID is Empty";
		public static final String ALIST_EMPTY = "Answer List is Empty";
		public static final String INVALID_LOGIN = "Invalid Login";
		public static final String RANGEMODEL_EMPTY = "Range Model is Empty";
		public static final String SUGLIST_EMPTY = "Suggestion Creation was Un-Sucessful";
		public static final String TOTAL_RATING_INVALID = "Total Rating Could not be Computed";
		public static final String DIA_COMPUTATION_FAILED = "Stress Computation has Failed During User Classification";
		public static final String APPOINTMENTSUCESS = "Appointment Has Been Completed Sucessfully";
		public static final String APPROVED_MSG = "Approval Requested has been Sucessfully Placed";
		public static final String UNAPPROVED_MSG = "No Approval on the Appointment Request has been Made";
		public static final String TESTID_EMPTY = "Test ID is Empty";
		public static final String DIAGRAPH_LIST = "Diabetics Graph List is Empty";
		public static final String RATING_EMPTY = "Rating is Empty";
		public static final String PATLIST_EMPTY = "Pateint List Cannot be Empty";
		public static final String COULD_NOT_CREATE_ELIGIBILITY = "Could not create Eligibility for the User";
		public static final String PATNAME_ALREADYEXIST = "Patient Name already Exists. You cannot have multiple appointments with doctor";
		public static final String HEART_ATTACK_ERR_MSG = "Heart Attack History Cannot be Empty";
		public static final String DIABETIC_ERR_MSG = "Please select Diabetic Information";
		public static final String BP_ERR_MSG = "Please select BP Information";
		public static final String GENDER_ERR_MSG = "Please select Gender Information";
		public static final String AGE_ERR_MSG = "Please select Age";
		public static final String SEX_ERR_MSG = "Please select Sex";
		public static final String DOB_ERR_MSG = "Please Select Date of Birth";
		public static final String COULD_NOT_MAINTAIN_HISTORY = "Could not Maintain History";
		public static final String AGEGROUP_ERRORMSG = "Age Group must has been selected";
		public static final String INCOME_ERRORMSG = "Income must has been selected";
		public static final String PROFEXP_ERRORMSG = "Profession Experience has been selected";
		public static final String CONTINUE_TEST = "Continue for the Test";
		public static final String ADMIN_CONTACT = "Please Contact System Administrator Call-9877777900";
		public static final String FILENAME_EMPTYERR = "Filename Cannot be Empty";
		public static final String ANSWER1_ERRORMSG = "Please Provide Answer1";
		public static final String ANSWER2_ERRORMSG = "Please Provide Answer2";
		public static final String CORRECTANS_ERRORMSG = "Please Provide Correct Answer";
		public static final String SYSTEMPROPERTY_EMPTYERR = "Please Contact System Administrator. If System Admin then Please set STRESSFILEPATH System Properties on Server Machine";
		public static final String PUZZLE_SUCESS_MSG = "Puzzle has been stored sucessfully";
		public static final String CONTENTTYPE_EMPTYERR = "Content Type is Empty";
		public static final String CONTENTDATA_EMPTYERR = "Content Data is Empty";
		public static final String PLIST_EMPTY = "Puzzle List is Empty";
		public static final String NO_PUZZLES_FOUND = "No Puzzles Found. Please try later";
		public static final String INFORMATIONPROCESS_FAILED = "Internal Error Occured.Could not Process Information";
		public static final String AGEGROUP_LIST = "Age Group Classification Could not be Done";
		public static final String COULD_NOT_STORE_STRESSANALYSIS = "Could not store Stress Analysis Data";
		public static final String INCOMEGROUP_EMPTYLIST = "Income Group Classification Could not be done";
		public static final String EXP_EMPTYLIST = "Professional Experience Classification Could not be done";
		public static final String SEX_EMPTYLIST = "Sex based Classification Could not be done";
		public static final String READ_DATASETS_FAILED = "Reading of Data Sets has Failed";
		public static final String READ_DATASETS_SUCESS = "Reading of Data Sets is Sucessful";
		public static final String COULD_NOT_FIND_DATASETS = "Could not find Data Sets";
		public static final String FTI_ERROR = "FTI Cannot be Empty";
		public static final String T3_ERROR = "T3 Cannot be Empty";
		public static final String T4U_ERROR = "T4U Cannot be Empty";
		public static final String TSH_ERROR = "TSH Cannot be Empty";
		public static final String TT4_ERROR = "TT4 Cannot be Empty";
		public static final String NAV_BAYES_ERROR = "Naive Bayes Computation Error has Occured";
		public static final String NAV_BAYES_SUCESS = "Naive Bayes Computatation is Sucessful";
		public static final String FTILUNG_ERROR = "FTI Lung Cannot be Empty";
		public static final String FTI_THY_ERROR = "FTI Thyroid Cannot be Empty";
		public static final String T3_LUNG_ERROR = "T3 Lung  Cannot be Empty";
		public static final String T3_THY_ERROR = "T3 Thy Cannot be Empty";
		public static final String T4U_LUNG_ERROR = "T4U Lung Cannot be Empty";
		public static final String T4U_THY_ERROR = "T4U Thyroid Cannot be Empty";
		public static final String TSH_LUNG_ERROR = "TSH Lung  Cannot be Empty";
		public static final String TSH_THYROID_ERROR = "TSH Thyroid Cannot be Empty";
		public static final String TT4_LUNG_ERROR = "TT4 Lung Cannot be Empty";
		public static final String TT4_THY_ERROR = "TT4 Thyroid Cannot be Empty";
		public static final String EMPTY_PROBINFO = "Probability Information is Empty";
		public static final String PROBINFO_RETRIVE_SUCESS = "Probability Information Retrival is Sucessful";
		public static final String CONTIGENCY_ERROR = "Contigency Could not be Computed";
		public static final String CONTIGENCY_COMPUTATION_SUCESS = "Contigency Computation is Sucessful";
		public static final String DELETE_CONTIGENCY = "Could not Delete the Contigency Computation";
		public static final String NOPROBABILITIES_FOUND = "Could not Find the Probabilities";
		public static final String CONTIGENCY_COULD_NOT_FOUND = "Could not Perform Contigency";
		public static final String CONTIGENCY_COULD_NOT_INSERT = "Contigency Could not be Inserted";
		public static final String EMPTY_CONTIGENCY = "Contigency Computation is Empty";
		public static final String CONTIGENCY_RETRIVE_SUCESS = "Contigency Retrival is Sucessful";
		public static final String EMPTY_ENHANCECONTIGENCY = "Empty Enhance Contigency";
		public static final String ENHANCE_CONTIGENCY_RETRIVED_SUCESS = "Enhance Contigency Retrival is Sucessful";
		public static final String ENHANCECONTIGENCY_COULD_NOT_FOUND = "Enhance Contigency Information Could not be Found";
		public static final String ENHANCE_CONTIGENCY_COMPUTATION_SUCESS = "Enhance Contigency Computation is Sucessful";
		public static final String CLASSIFY_FAILED = "Classification has Failed";
		public static final String CLASSIFY_SUCESS = "Classification is Sucessful";
		public static final String EMPTY_CLASSIFIER = "Empty Classifier Information";
		public static final String EMPTY_CLASSIFIER_COUNT = "Empty Classifier Count";
		public static final String CLASSIFY_COUNT_SUCESS = "Classification Count is Sucessful";
		public static final String DELETE_ENHANCE_CONTIGENCY_FAILED = "Deletion of Enhance Contigency has Failed";
		public static final String PLEASE_PERFORM_CONTIGENCY = "Please Perform Contigency";
		public static final String ENHANCE_CONTIGENCY_INSERT_FAILED = "Enhance Contigency Insertion has Failed";
		public static final String COULD_NOT_DELETE_OLD_CLASS_INFO = "Could not Delete Old Class Information";
		public static final String PLEASE_PERFORM_ENHNACE_CONTIGENCY = "Please Perform Enhance Contigency";
		public static final String NO_USERIDS_ENHANCECONTIGENCY = "No User Ids For Enhance Contigency";
		public static final String CLASSIFY_NOT_POSSIBLE_AT_THIS_TIME = "Classification Could not be Performed at this Point of Time";
		public static final String PATIAL_CLASSIFICATION_DONE = "Partial Classification Cannot be done";
		public static final String ANALYSISLIST_EMPTY = "Analysis List is Empty";
		public static final String COULD_NOT_STORE_DISEASEANALYSIS = "Could not Store Disease Analysis";

	}

	interface Views {
		public static final String VIEW_REGISTER_INPUT = "registerview";
		public static final String VIEW_CUSTOMER_WELCOMEPAGE = "custwelcome";
		public static final String VIEW_LOGIN_INPUT = "login";
		public static final String VIEW_ADMIN_WELCOMEPAGE = "admin";
		public static final String VIEW_BANK_INPUT = "bankinput";
		public static final String VIEW_ERROR_PAGE = "error";
		public static final String APPLICATION_WELCOME_PAGE = "welcome";
		public static final String VIEW_SUCESS_PAGE = "sucess";
		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";
		public static final String VIEW_DOCTOR_WELCOMEPAGE = "doctor";
		public static final String VIEW_PC_WELCOMEPAGE = "patientcor";
		public static final String VIEW_P_WELCOMEPAGE = "patient";
		public static final String DOCTOR_FAILURE_PAGE = "docfailure";
		public static final String DOCTOR_RANGE_PAGE = "docrange";
		public static final String PC_FAILURE_PAGE = "pcfailure";
		public static final String PC_SUCESS_PAGE = "pcsucess";
		public static final String VIEW_PATEINT_ERROR_PAGE = "pateintfailure";
		public static final String VIEW_ANSWERS_INPUT = "viewscreeningtest";
		public static final String VIEW_ANSWERS_OUTPUT = "viewansweroutput";
		public static final String VIEW_PATEINT_SUG_PAGE = "suggestion";
		public static final String VIEW_PATEINT_FAILURE_PAGE = "pfailure";
		public static final String VIEW_PATEINT_SUCESS_PAGE = "psucess";
		public static final String VIEW_QUESTION_PAGE = "questions";
		public static final String VIEW_ANALYIS_PAGE = "analysis";
		public static final String WELCOME_PAGE = "welcome";
		public static final String ERROR_PAGE = "error";
		public static final String VIEW_SCREEN_PAGE = "screen";
		public static final String VIEW_ANALYIS_OUTER_PAGE = "analysisouter";
		public static final String VIEW_REGISTERINITIAL_INPUT = "registerintialview";
		public static final String VIEW_USER_PAGE = "patient";
		public static final String VIEW_USER_ERROR_PAGE = "erroruser";
		public static final String VIEW_QUESTIONUSER_PAGE = "questionsuser";
		public static final String VIEW_ANALYIS_USER_PAGE = "analysisuser";
		public static final String VIEW_APPOINT_ERROR_USER = "appointerroruser";
		public static final String VIEW_APPOINT_SUCESS_USER = "appointsucessuser";
		public static final String VIEW_PUZZLE_INPUT_PAGE = "puzzlequestions";
		public static final String VIEW_SUCESS_ADMIN_PAGE = "adminsucess";
		public static final String VIEW_PUZZLES_USER = "puzzlesuser";
		public static final String PUZZLE_ANALYSIS = "puzzleanalysis";
		public static final String VIEW_QUESTIONUSER_DISEASE_PAGE = "questionsuserdisease";
		public static final String VIEW_ANALYIS_USER_DISEASE_PAGE = "analysisuserdisease";

	}

	interface Keys {
		public static final String OBJ = "obj";
		public static final int ADMIN_TYPE = 1;
		public static final String LOGINID_SESSION = "LOGINID_SESSION";
		public static final String LOGINTYPE_SESSION = "LOGINTYPE_SESSION";
		public static final int CUSTOMER_TYPE = 4;
		public static final int DOCTOR_TYPE = 2;
		public static final int PC_TYPE = 3;
		public static final String LOWSTRESS = "LOWSTRESS";
		public static final String LOWDISEASE = "VIRAL";
		public static final String MEDIUMSTRESS = "MEDIUMSTRESS";
		public static final String MEDIUMDISEASE = "THYPHOID";
		public static final String HIGHSTRESS = "HIGHSTRESS";
		public static final String  HIGHDISEASE="DENGUE";
		public static final String NOSTRESS = "NOSTRESS";
		public static final int UNAPPROVED_KEY = 2;
		public static final int APPROVED = 1;
		public static final String GENERIC_TEST = "GENERIC";

		public static final String PATH_FOR_IMAGES = "C:\\PersonalHealtManage\\WebContent\\images\\";
		public static final String NODISEASE = "MILDFEVER";
	}

	interface DATABASESQL {
		public static final String RETRIVE_REGISTER_USERIDS_SQL = "RETRIVE_REGISTER_USERIDS_SQL";
		public static final String INSERT_LOGIN_SQL = "INSERT_LOGIN_SQL";
		public static final String RETRIVE_PASSWORD_WHERE_USERID_SQL = "RETRIVE_PASSWORD_WHERE_USERID_SQL";
		public static final String RETRIVE_LOGINTYPE_WHERE_USERID_SQL = "RETRIVE_LOGINTYPE_WHERE_USERID_SQL";
		public static final String RETRIVE_ACCCOUNTNOS_SQL = "RETRIVE_ACCCOUNTNOS_SQL";
		public static final String RETRIVE_IPIN_WHERE_ACCNO_SQL = "RETRIVE_IPIN_WHERE_ACCNO_SQL";
		public static final String RETRIVE_BALANCE_WHERE_ACCNO_SQL = "RETRIVE_BALANCE_WHERE_ACCNO_SQL";
		public static final String UPDATE_BALANCE_SQL = "UPDATE_BALANCE_SQL";
		public static final String INSERT_TRANS_SQL = "INSERT_TRANS_SQL";
		public static final String RETRIVE_USERROLE_SQL = "RETRIVE_USERROLE_SQL";
		public static final String RETRIVE_TESTNAMES_SQL = "RETRIVE_TESTNAMES_SQL";
		public static final String INSERT_QUESTION_SQL = "INSERT_QUESTION_SQL";
		public static final String INSERT_TEST_SQL = "INSERT_TEST_SQL";
		public static final String RETRIVE_APPOINT_SQL = "RETRIVE_APPOINT_SQL";
		public static final String DELETE_APPOINTMENT_SQL = "DELETE_APPOINTMENT_SQL";
		public static final String RETRIVE_PATIENTLIST_SQL = "RETRIVE_PATIENTLIST_SQL";
		public static final String RETRIVE_TESTNAMESLIST_FROM_TEST_SQL = "RETRIVE_TESTNAMESLIST_FROM_TEST_SQL";
		public static final String RETRIVE_PNAMES_FROM_ELIGIBILITY_SQL = "RETRIVE_PNAMES_FROM_ELIGIBILITY_SQL";
		public static final String INSERT_ELIGIBILITY_SQL = "INSERT_ELIGIBILITY_SQL";
		public static final String UPDATE_ELIGIBILITY_SQL = "UPDATE_ELIGIBILITY_SQL";
		public static final String APPROVE_APPOINTMENT_SQL = "APPROVE_APPOINTMENT_SQL";
		public static final String RETRIVE_TESTNAME_FROM_ELIGIBILITY_WHERE_PATNAME_SQL = "RETRIVE_TESTNAME_FROM_ELIGIBILITY_WHERE_PATNAME_SQL";
		public static final String RETRIVE_QUESTIONS_SQL = "RETRIVE_QUESTIONS_SQL";
		public static final String RETRIVE_RANGE_WHERE_TESTNAME_SQL = "RETRIVE_RANGE_WHERE_TESTNAME_SQL";
		public static final String INSERT_ANSWER_SQL = "INSERT_ANSWER_SQL";
		public static final String INSERT_APPOINT_SQL = "INSERT_APPOINT_SQL";
		public static final String RETRIVE_APPOINTSTATUS_WHERE_PATID_SQL = "RETRIVE_APPOINTSTATUS_WHERE_PATID_SQL";
		public static final String RETRIVE_UNIQUETESTS_FROM_TEST_SQL = "RETRIVE_UNIQUETESTS_FROM_TEST_SQL";
		public static final String RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_TESTNAME_SQL = "RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_TESTNAME_SQL";
		public static final String RETRIVE_REGISTER_USERIDS_PATEINT_SQL = "RETRIVE_REGISTER_USERIDS_PATEINT_SQL";
		public static final String UPDATE_APPOINTMENT_SQL = "UPDATE_APPOINTMENT_SQL";
		public static final String RETRIVE_DOCTORNAME_SQL = "RETRIVE_DOCTORNAME_SQL";
		public static final String RETRIVE_PATNAME_APPOINT_SQL = "RETRIVE_PATNAME_APPOINT_SQL";
		public static final String RETRIVE_APPOINT_WHERE_DOCNAMESQL = "RETRIVE_APPOINT_WHERE_DOCNAMESQL";
		public static final String RETRIVE_APPOINT_WHERE_PATNAMESQL = "RETRIVE_APPOINT_WHERE_PATNAMESQL";
		public static final String RETRIVE_SUGGESTIONS_BYTYPE_SQL = "RETRIVE_SUGGESTIONS_BYTYPE_SQL";
		public static final String RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_SQL = "RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_SQL";
		public static final String RETRIVE_CURRENTTIME_FROM_ANSWER_FORUSERID_SQL = "RETRIVE_CURRENTTIME_FROM_ANSWER_FORUSERID_SQL";
		public static final String RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_CURTIME_SQL = "RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_CURTIME_SQL";
		public static final String INSERT_PUZZLE_SQL = "INSERT_PUZZLE_SQL";
		public static final String RETRIVE_PUZZLE_SQL = "RETRIVE_PUZZLE_SQL";
		public static final String RETRIVE_AGE_FOR_USERNAME_SQL = "RETRIVE_AGE_FOR_USERNAME_SQL";
		public static final String RETRIVE_PUZZLE_WHERE_AGEGROUP_SQL = "RETRIVE_PUZZLE_WHERE_AGEGROUP_SQL";
		public static final String RETRIVE_REGISTERINFO_FOR_USERID_SQL = "RETRIVE_REGISTERINFO_FOR_USERID_SQL";
		public static final String INSERT_STRESS_SQL = "INSERT_STRESS_SQL";
		public static final String RETRIVE_REGISTERINFO_FOR_USERID_PLAIN_SQL = "RETRIVE_REGISTERINFO_FOR_USERID_PLAIN_SQL";
		public static final String RETRIVE_AGEGRPOUP_CLASSIFY_SQL = "RETRIVE_AGEGRPOUP_CLASSIFY_SQL";
		public static final String RETRIVE_INCOMEGROUP_CLASSIFY_SQL = "RETRIVE_INCOMEGROUP_CLASSIFY_SQL";
		public static final String RETRIVE_PROFEXP_CLASSIFY_SQL = "RETRIVE_PROFEXP_CLASSIFY_SQL";
		public static final String RETRIVE_SEX_CLASSIFY_SQL = "RETRIVE_SEX_CLASSIFY_SQL";
		public static final String RETRIVE_TRACKHISTORY_SQL = "RETRIVE_TRACKHISTORY_SQL";
		public static final String RETRIVE_UNIQUE_ROWIDS_SQL = "RETRIVE_UNIQUE_ROWIDS_SQL";
		public static final String RETRIVE_ATTRIBUTE_FOR_ROWID_SQL = "RETRIVE_ATTRIBUTE_FOR_ROWID_SQL";
		public static final String RETRIVE_UNIQUE_ATTRIBUTES_SQL = "RETRIVE_UNIQUE_ATTRIBUTES_SQL";
		public static final String RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_SQL = "RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_SQL";
		public static final String INSERT_PROBABILITY_INFO_SQL = "INSERT_PROBABILITY_INFO_SQL";
		public static final String RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_TYPE_SQL = "RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_TYPE_SQL";
		public static final String REMOVE_ALL_PROBABILITIES_WHERE_USERID_SQL = "REMOVE_ALL_PROBABILITIES_WHERE_USERID_SQL";
		public static final String RETRIVE_UNIQUE_ATTRIBUTES_FOR_TYPE_SQL = "RETRIVE_UNIQUE_ATTRIBUTES_FOR_TYPE_SQL";
		public static final String RETRIVE_PROBABILITY_FULL_SQL = "RETRIVE_PROBABILITY_FULL_SQL";
		public static final String REMOVE_ALL_CONTIGENCY_SQL = "REMOVE_ALL_CONTIGENCY_SQL";
		public static final String INSERT_CONTIGENCY_INFO_SQL = "INSERT_CONTIGENCY_INFO_SQL";
		public static final String RETRIVE_CONTIGENCY_FULL_SQL = "RETRIVE_CONTIGENCY_FULL_SQL";
		public static final String RETRIVE_ENHANCECONTIGENCY_FULL_SQL = "RETRIVE_ENHANCECONTIGENCY_FULL_SQL";
		public static final String REMOVE_ALL_ENHANCECONTIGENCY_SQL = "REMOVE_ALL_ENHANCECONTIGENCY_SQL";
		public static final String INSERT_ENHANCE_CONTIGENCY_INFO_SQL = "INSERT_ENHANCE_CONTIGENCY_INFO_SQL";
		public static final String REMOVE_ALL_CLASSIFIER_SQL = "REMOVE_ALL_CLASSIFIER_SQL";
		public static final String RETRIVE_DISTINCT_USERIDS_FROM_ENHANCECONTGENCY_SQL = "RETRIVE_DISTINCT_USERIDS_FROM_ENHANCECONTGENCY_SQL";
		public static final String RETRIVE_RANKED_CLASSIFIER_FROM_CONTGENCY_WHERE_USERID_SQL = "RETRIVE_RANKED_CLASSIFIER_FROM_CONTGENCY_WHERE_USERID_SQL";
		public static final String INSERT_CLASSIFIER_INFO_SQL = "INSERT_CLASSIFIER_INFO_SQL";
		public static final String RETRIVE_CLASSINFO_FULL_SQL = "RETRIVE_CLASSINFO_FULL_SQL";
		public static final String RETRIVE_CLASSIFYCOUNT_FULL_SQL = "RETRIVE_CLASSIFYCOUNT_FULL_SQL";
		public static final String RETRIVE_PATIENTLIST_ANALYSIS_SQL = "RETRIVE_PATIENTLIST_ANALYSIS_SQL";
		public static final String RETRIVE_DISTINCT_USERNAME_ANALYSIS_SQL = "RETRIVE_DISTINCT_USERNAME_ANALYSIS_SQL";
		public static final String RETRIVE_DISTINCT_USERIDS_LOGINTYPE_EMAIL_DEFAULT_SQL = "RETRIVE_DISTINCT_USERIDS_LOGINTYPE_EMAIL_DEFAULT_SQL";
		public static final String RETRIVE_PATIENTLIST_APPOINT_SQL = "RETRIVE_PATIENTLIST_APPOINT_SQL";
		public static final String RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_SQL = "RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_SQL";
		public static final String RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_SQL = "RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_SQL";
		public static final String RETRIVE_UNIQUETESTS_FROM_TEST_DISEASE_SQL = "RETRIVE_UNIQUETESTS_FROM_TEST_DISEASE_SQL";
		public static final String RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_DISEASE_SQL = "RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_DISEASE_SQL";
		public static final String RETRIVE_QUESTIONS_DISEASE_SQL = "RETRIVE_QUESTIONS_DISEASE_SQL";
		public static final String RETRIVE_RANGE_WHERE_TESTNAME_DISEASE_SQL = "RETRIVE_RANGE_WHERE_TESTNAME_DISEASE_SQL";
		public static final String RETRIVE_TESTNAMES_DISEASE_SQL = "RETRIVE_TESTNAMES_DISEASE_SQL";
		public static final String INSERT_QUESTION_DISEASE_SQL = "INSERT_QUESTION_DISEASE_SQL";
		public static final String INSERT_TEST_DISEASE_SQL = "INSERT_TEST_DISEASE_SQL";
		public static final String RETRIVE_APPOINT_WHERE_DOCNAME_DISEASE_SQL = "RETRIVE_APPOINT_WHERE_DOCNAME_DISEASE_SQL";
		public static final String DELETE_APPOINTMENT_DISEASE_SQL = "DELETE_APPOINTMENT_DISEASE_SQL";
		public static final String RETRIVE_TESTNAMESLIST_FROM_TEST_DISEASE_SQL = "RETRIVE_TESTNAMESLIST_FROM_TEST_DISEASE_SQL";
		public static final String RETRIVE_PATIENTLIST_APPOINT_DISEASE_SQL = "RETRIVE_PATIENTLIST_APPOINT_DISEASE_SQL";
		public static final String RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_DISEASE_SQL = "RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_DISEASE_SQL";
		public static final String RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_DISEASE_SQL = "RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_DISEASE_SQL";
		public static final String RETRIVE_CLASSINFO_FULL_DISEASE_SQL = "RETRIVE_CLASSINFO_FULL_DISEASE_SQL";
		public static final String RETRIVE_CLASSIFYCOUNT_FULL_DISEASE_SQL = "RETRIVE_CLASSIFYCOUNT_FULL_DISEASE_SQL";
		public static final String RETRIVE_AGEGRPOUP_CLASSIFY_DISEASE_SQL = "RETRIVE_AGEGRPOUP_CLASSIFY_DISEASE_SQL";
		public static final String RETRIVE_SEX_CLASSIFY_DISEASE_SQL = "RETRIVE_SEX_CLASSIFY_DISEASE_SQL";
		public static final String RETRIVE_PROFEXP_CLASSIFY_DISEASE_SQL = "RETRIVE_PROFEXP_CLASSIFY_DISEASE_SQL";
		public static final String RETRIVE_INCOMEGROUP_CLASSIFY_DISEASE_SQL = "RETRIVE_INCOMEGROUP_CLASSIFY_DISEASE_SQL";
		public static final String RETRIVE_APPOINT_DISEASE_SQL = "RETRIVE_APPOINT_DISEASE_SQL";
		public static final String RETRIVE_APPOINT_WHERE_PATNAME_DISEASE_SQL = "RETRIVE_APPOINT_WHERE_PATNAME_DISEASE_SQL";
		public static final String RETRIVE_TRACKHISTORY_DISEASE_SQL = "RETRIVE_TRACKHISTORY_DISEASE_SQL";
		public static final String RETRIVE_PATNAME_APPOINT_DISEASE_SQL = "RETRIVE_PATNAME_APPOINT_DISEASE_SQL";
		public static final String INSERT_APPOINT_DISEASE_SQL = "INSERT_APPOINT_DISEASE_SQL";
		public static final String INSERT_ANSWER_DISEASE_SQL = "INSERT_ANSWER_DISEASE_SQL";
		public static final String INSERT_STRESS_DISEASE_SQL = "INSERT_STRESS_DISEASE_SQL";
		public static final String UPDATE_APPOINTMENT_DISEASE_SQL = "UPDATE_APPOINTMENT_DISEASE_SQL";
		public static final String RETRIVE_UNIQUETESTS_FROM_TEST_PREDICT_SQL = "RETRIVE_UNIQUETESTS_FROM_TEST_PREDICT_SQL";   
	} 
  
	interface DATABASECOLUMNS {
		public static final String ROLE_ID_COL = "ROLEID";
		public static final String ROLE_NAME_COL = "ROLENAME";
		public static final String ROLE_DESC_COL = "ROLEDESC";
		public static final String APPOINTID_COL = "APPOINTID";
		public static final String PATIENTNAME_COL = "PATIENTNAME";
		public static final String STATUS_COL = "STATUS";
		public static final String TESTNAME_COL = "TESTNAME";
		public static final String ANSWER1_COL = "ANSWER1";
		public static final String ANSWER2_COL = "ANSWER2";
		public static final String ANSWER3_COL = "ANSWER3";
		public static final String ANSWER4_COL = "ANSWER4";
		public static final String QUESID_COL = "QUESID";
		public static final String QUESDESC_COL = "QUESDESC";
		public static final String RATING1_COL = "RATING1";
		public static final String RATING2_COL = "RATING2";
		public static final String RATING3_COL = "RATING3";
		public static final String RATING4_COL = "RATING4";
		public static final String SUG1_COL = "SUG1";
		public static final String SUG2_COL = "SUG2";
		public static final String SUG3_COL = "SUG3";
		public static final String SUG4_COL = "SUG4";
		public static final String USERID_COL = "USERID";
		public static final String R1LL_COL = "R1LL";
		public static final String R1HL_COL = "R1HL";
		public static final String R2LL_COL = "R2LL";
		public static final String R2HL_COL = "R2HL";
		public static final String R3HL_COL = "R3HL";
		public static final String R3LL_COL = "R3LL";
		public static final String DATE_COL = "DATE";
		public static final String TIMEIN_COL = "TIMEIN";
		public static final String TIMEOUT_COL = "TIMEOUT";
		public static final String DOCNAME_COL = "DOCNAME";
		public static final String R4LL_COL = "R4LL";
		public static final String R4HL_COL = "R4HL";
		public static final String USERNAME_COL = "USERNAME";
	}

	interface NavBayes {

		String FTI_ATTRIBUTE = "FTI";
		String T3_ATTRIBUTE = "T3";
		String T4U_ATTRIBUTE = "T4U";
		String TSH_ATTRIBUTE = "TSH";
		String TT4_ATTRIBUTE = "TT4";
		String FTI_LUNG_CANCER_ATTRIBUTE = "FTI_LUNG";
		String T3_LUNG_CANCER_ATTRIBUTE = "T3_LUNG";
		String T4U_LUNG_CANCER_ATTRIBUTE = "T4U_LUNG";
		String TSH_LUNG_CANCER_ATTRIBUTE = "TSH_LUNG";
		String TT4_LUNG_CANCER_ATTRIBUTE = "TT4_LUNG";
		String FTI_THYROID_ATTRIBUTE = "FTI_THY";
		String T3_THYROID_ATTRIBUTE = "T3_THY";
		String T4U_THYROID_ATTRIBUTE = "T4U_THY";
		String TSH_THYROID_ATTRIBUTE = "TSH_THY";
		String TT4_THYROID_ATTRIBUTE = "TT4_THY";

	}

	interface Classify {

		String HYPERTENSION = "HYPERTENSION";

		String LUNGCANCER = "LUNGCANCER";

		String THYROID = "THYROID";

	}

}
