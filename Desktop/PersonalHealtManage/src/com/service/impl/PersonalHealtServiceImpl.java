package com.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.constants.PersonalHealtConstantsIF;
import com.dao.inter.PersonalHealtDAOIF;
import com.model.AnalysisData;
import com.model.AnswerVO;
import com.model.AppointVO;
import com.model.AppointmentInfo;
import com.model.AttributeComputationModel;
import com.model.AttributeInformation;
import com.model.CategoryCountVO;
import com.model.ClassificationAgeGroup;
import com.model.ClassificationGroup;
import com.model.ClassifierInfo;
import com.model.ContigencyInfo;
import com.model.DiabeticsGraph;
import com.model.EnhanceContigency;
import com.model.GlobalNaiveBayesOutput;
import com.model.HealtInfo;
import com.model.DoctorInfo;
import com.model.EligibilityModel;
import com.model.GlobalRowInfo;
import com.model.NaiveBayesDataSetModelInput;
import com.model.NaiveBayesOutput;
import com.model.PartialClassifierInfo;
import com.model.PatientInfo;
import com.model.PatientNames;
import com.model.ProbabilityInfo;
import com.model.PuzzleModel;
import com.model.PuzzleVO;
import com.model.QuestionVO;
import com.model.RangeModel;
import com.model.RegisterUser;
import com.model.RowInfo;
import com.model.ScreeningTestVO;
import com.model.StatusInfo;
import com.model.StressAnalysis;
import com.model.SuggestionObj;
import com.model.SuggestionVO;
import com.model.TestInfo;
import com.model.UserRoleInfo;
import com.service.inter.PersonalHealtServiceIF;
import com.util.helper.StressHelperUtil;
import com.model.DeleteAppointInfo;

public class PersonalHealtServiceImpl implements PersonalHealtServiceIF {

	@Autowired
	private PersonalHealtDAOIF personalHealtDao;

	public PersonalHealtDAOIF getPersonalHealtDao() {
		return personalHealtDao;
	}

	public void setPersonalHealtDao(PersonalHealtDAOIF personalHealtDao) {
		this.personalHealtDao = personalHealtDao;
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			System.out.println("Inside Verify Login Service");
			boolean status = checkUserInformation(registerUser.getUserId());
			if (!status) {
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.NO_USER_EXISTS);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				String password = personalHealtDao.retrivePassword(registerUser.getUserId());

				if (null == password || password.isEmpty()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (!password.equals(registerUser.getUserPassword())) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (password.equals(registerUser.getUserPassword())) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.USER_LOGIN_SUCESS);
					statusInfo.setStatus(true);
					// Now retrieve the login type
					int loginTypeDB = personalHealtDao.retriveLoginType(registerUser.getUserId());
					statusInfo.setType(loginTypeDB);
					return statusInfo;
				}
			}
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	private boolean checkUserInformation(String userId) {
		try {
			List<String> userNameList = personalHealtDao.retriveUserIds();

			System.out.println("LIST" + userNameList);
			if (null == userNameList || userNameList.isEmpty() || userNameList.size() == 0) {
				return false;
			}
			if (userNameList.contains(userId)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = personalHealtDao.retriveUserIds();
			if (null == userIdList || userIdList.isEmpty() || userIdList.size() == 0) {
				statusInfo = personalHealtDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}

			}

			if (userIdList.contains(registerUser.getUserId())) {
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.USERALREADY_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = personalHealtDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}

	}

	@Override
	public List<UserRoleInfo> retriveUserRoles() {
		List<UserRoleInfo> userRoleInfoList = null;

		try {
			userRoleInfoList = personalHealtDao.retriveUserRoleInfo();
		} catch (Exception e) {
			return userRoleInfoList;
		}
		return userRoleInfoList;
	}

	@Override
	public StatusInfo storeScreeningTest(ScreeningTestVO screeningTestVO) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String testName = screeningTestVO.getTestName();

			if (null == testName) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				return statusInfo;
			}

			List<String> testNameList = personalHealtDao.retriveTestNames();

			if (null == testNameList || testNameList.isEmpty() || testNameList.size() == 0) {

				statusInfo = doInsertionOfScreeningTest(screeningTestVO);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);
				return statusInfo;

			}

			if (testNameList.contains(testName)) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.TESTNAME_ALREADY_EXISTS);
				return statusInfo;
			}

			statusInfo = doInsertionOfScreeningTest(screeningTestVO);

			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

	}

	private StatusInfo doInsertionOfScreeningTest(ScreeningTestVO screeningTestVO) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String testName = screeningTestVO.getTestName();

			ArrayList<QuestionVO> questionList = screeningTestVO.getQuestionList();

			List<Integer> maxListData = new ArrayList<Integer>();

			if (null == questionList || questionList.isEmpty() || questionList.size() == 0) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				return statusInfo;
			}

			for (QuestionVO questionVO : questionList) {
				statusInfo = personalHealtDao.insertQuestion(questionVO, testName);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				List<Integer> temp = new ArrayList<Integer>();

				temp.add(questionVO.getRating1());
				temp.add(questionVO.getRating2());
				temp.add(questionVO.getRating3());
				temp.add(questionVO.getRating4());

				int max = Collections.max(temp);

				maxListData.add(max);

			}

			// Now Find the Max Value and the ranges

			int maximumOfAll = 0;

			for (Integer data : maxListData) {

				maximumOfAll = maximumOfAll + data;
			}

			int size = maximumOfAll;

			int sizeDataPayload = size;

			int size2 = sizeDataPayload / 4;
			int size3 = sizeDataPayload / 3;
			int size4 = sizeDataPayload / 2;
			int size5 = sizeDataPayload;

			// Range 1

			int R1LL = 0;

			int R1HL = size2 - 1;

			// Range 2
			int R2LL = size2;

			int R2HL = size3 - 1;

			// Range 3
			int R3LL = size3;

			int R3HL = size4 - 1;

			// Range 4
			int R4LL = size4;

			int R4HL = size5 - 1;

			RangeModel rangeModel = new RangeModel();

			rangeModel.setR1LL(R1LL);
			rangeModel.setR1HL(R1HL);
			rangeModel.setR2LL(R2LL);
			rangeModel.setR2HL(R2HL);
			rangeModel.setR3LL(R3LL);
			rangeModel.setR3HL(R3HL);
			rangeModel.setR4HL(R4HL);
			rangeModel.setR4LL(R4LL);
			rangeModel.setTestName(testName);

			statusInfo = personalHealtDao.insertTest(rangeModel);
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}
			statusInfo.setModel(rangeModel);
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}

	}

	@Override
	public List<AppointVO> retriveAppointments() {
		List<AppointVO> appointInfoList = null;

		try {
			appointInfoList = personalHealtDao.retriveAppointments();
		} catch (Exception e) {
			return appointInfoList;
		}
		return appointInfoList;
	}

	@Override
	public StatusInfo deleteAppointments(DeleteAppointInfo appointmentInfo) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			ArrayList<Integer> appointmentList = appointmentInfo.getAppointmentList();

			if (null == appointmentList || appointmentList.isEmpty() || appointmentList.size() == 0) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPOINT_LIST_TO_DEL_EMPTY);
				return statusInfo;
			}

			for (Integer appointmentId : appointmentList) {
				statusInfo = personalHealtDao.deleteAppointment(appointmentId);
				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.DEL_APPID_FAIL);
					return statusInfo;
				}

			}

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<PatientInfo> retrivePatientNames() {
		List<PatientInfo> pateintList = null;

		try {
			pateintList = personalHealtDao.retrivePatientNames();
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<TestInfo> retriveTestNames() {
		List<TestInfo> pateintList = null;

		try {
			pateintList = personalHealtDao.retriveTestNamesInTestInfoFormat();
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public StatusInfo createEligibility(EligibilityModel eligibilityModel) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String patientName = eligibilityModel.getPatName();
			if (null == patientName || patientName.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PNAME_EMPTY);
				return statusInfo;
			}

			String testName = eligibilityModel.getTestId();
			if (null == testName || testName.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				return statusInfo;
			}

			List<String> pateintNames = personalHealtDao.retrivePatientNamesFromEligibility();

			if (null == pateintNames || pateintNames.isEmpty() || pateintNames.size() == 0) {

				statusInfo = personalHealtDao.insertEligibility(eligibilityModel);

				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);

				return statusInfo;
			}

			if (pateintNames.contains(patientName)) {
				statusInfo = personalHealtDao.updateEligibility(eligibilityModel);

				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);
				return statusInfo;
			} else {

				statusInfo = personalHealtDao.insertEligibility(eligibilityModel);

				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);

				return statusInfo;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());

			statusInfo = new StatusInfo();

			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	@Override
	public StatusInfo approveAppointments(AppointmentInfo appointmentInfo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			ArrayList<AppointVO> appointmentList = appointmentInfo.getAppointmentList();

			if (null == appointmentList || appointmentList.isEmpty() || appointmentList.size() == 0) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPOINT_LIST_TO_APPROVE_EMPTY);
				return statusInfo;
			}

			for (AppointVO appointmentVOTemp : appointmentList) {
				appointmentVOTemp.setStatus(PersonalHealtConstantsIF.Keys.APPROVED);
				statusInfo = personalHealtDao.approveAppointment(appointmentVOTemp);
				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPROVE_APPID_FAIL);
					return statusInfo;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<QuestionVO> retriveQuestions(String loginId) {
		List<QuestionVO> questionVOList = null;

		try {
			String testName = personalHealtDao.retriveTestNameFromEligibility(loginId);

			if (null == testName) {
				return null;
			}

			questionVOList = personalHealtDao.retriveQuestions(testName);
		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	@Override
	public HealtInfo processHealt(ArrayList<QuestionVO> questionObj, String patName, String registerOrNot) {

		HealtInfo healtInfo = null;
		try {

			String testName = questionObj.get(0).getTestName();
			healtInfo = new HealtInfo();

			RangeModel rangeModel = personalHealtDao.retriveRangeModel(testName);

			if (null == rangeModel) {
				healtInfo = new HealtInfo();
				healtInfo.setStatus(false);
				healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.RANGEMODEL_EMPTY);
				return healtInfo;
			}

			// Now Find the Total Rating

			int totalRating = computeTotalMarks(questionObj, testName);

			if (totalRating <= 0) {
				healtInfo = new HealtInfo();
				healtInfo.setStatus(false);
				healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.TOTAL_RATING_INVALID);
				return healtInfo;
			} else {

				// now put answers for Healt Analyis

				// Add Total Rating
				healtInfo.setTotalRating(totalRating);

				// Now find whether person has low high or medium Healt

				String type = computeType(totalRating, rangeModel);

				/*
				 * List<SuggestionObj> sugVOList = personalHealtDao
				 * .findSuggestionsByType(type);
				 */

				List<SuggestionVO> sugVOList = computeSuggestions(questionObj);

				List<SuggestionObj> sugVOListNew = convertSuggestionFromOneFormToOther(sugVOList, type);

				healtInfo.setSuggestionObjList(sugVOListNew);

				healtInfo.setType(type);

				StatusInfo statusInfo = putAnswersForHealAnalyis(questionObj, patName, totalRating, type);

				if (statusInfo != null && !statusInfo.isStatus()) {

					healtInfo.setStatus(false);
					healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_MAINTAIN_HISTORY);
				}

				// Now
				StatusInfo statusInfo1 = storeDataForStressAnalysis(patName, totalRating, type, registerOrNot,
						testName);

				if (statusInfo1 != null && !statusInfo1.isStatus()) {

					healtInfo.setStatus(false);
					healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_STORE_STRESSANALYSIS);
				}

				if ((type != null && !type.isEmpty()) && type.equals(PersonalHealtConstantsIF.Keys.HIGHSTRESS)) {
					healtInfo.setStatus(true);
					healtInfo.setHigh(true);
					return healtInfo;
				}

				healtInfo.setStatus(true);
				healtInfo.setHigh(false);
				return healtInfo;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			healtInfo = new HealtInfo();
			healtInfo.setStatus(false);
			healtInfo.setErrMsg(e.getMessage());
			return healtInfo;
		}

	}

	private List<SuggestionObj> convertSuggestionFromOneFormToOther(List<SuggestionVO> sugVOList, String type) {

		List<SuggestionObj> suggestionObjList = new ArrayList<SuggestionObj>();

		if (sugVOList != null && !sugVOList.isEmpty()) {

			for (SuggestionVO suggestionVO : sugVOList) {

				SuggestionObj suggestionObj = new SuggestionObj();
				suggestionObj.setSuggestion(suggestionVO.getSug());
				suggestionObj.setType(type);

				suggestionObjList.add(suggestionObj);

			}
		}

		return suggestionObjList;

	}

	private StatusInfo storeDataForStressAnalysis(String patName, int totalRating, String type, String registerOrNot,
			String testName) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			RegisterUser registerUser = personalHealtDao.retriveRegisterInfoForPatName(patName, registerOrNot);

			StressAnalysis stressAnalysis = new StressAnalysis();
			stressAnalysis.setAgeGroup(registerUser.getAnswer1());
			populateStressForAgeGroup(registerUser, stressAnalysis);
			stressAnalysis.setIncomeGroup(registerUser.getAnswer2());
			populateStressForIncomeGroup(registerUser, stressAnalysis);
			stressAnalysis.setProfExpGroup(registerUser.getAnswer3());
			populateStressForProfExp(registerUser, stressAnalysis);
			stressAnalysis.setRegistered(registerOrNot);
			stressAnalysis.setSex(registerUser.getSex() == 1 ? "1" : "2");
			stressAnalysis.setSexDesc(registerUser.getSex() == 1 ? "MALE" : "FEMALE");
			stressAnalysis.setStressLabel(type);
			stressAnalysis.setUsername(patName);
			stressAnalysis.setTotalRating(totalRating);
			stressAnalysis.setTestName(testName);
			statusInfo = personalHealtDao.insertStress(stressAnalysis);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
		}

		return statusInfo;

	}

	private void populateStressForAgeGroup(RegisterUser registerUser, StressAnalysis stressAnalysis) {
		if (registerUser.getAnswer1().equals("1")) {
			stressAnalysis.setAgeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.AGEGROUP1);

		} else if (registerUser.getAnswer1().equals("2")) {
			stressAnalysis.setAgeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.AGEGROUP2);

		} else if (registerUser.getAnswer1().equals("3")) {
			stressAnalysis.setAgeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.AGEGROUP3);

		} else {
			stressAnalysis.setAgeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.AGEGROUP4);
		}
	}

	private void populateStressForIncomeGroup(RegisterUser registerUser, StressAnalysis stressAnalysis) {
		if (registerUser.getAnswer2().equals("1")) {
			stressAnalysis.setIncomeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.INCOMEGROUP1);

		} else if (registerUser.getAnswer2().equals("2")) {
			stressAnalysis.setIncomeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.INCOMEGROUP2);

		} else if (registerUser.getAnswer2().equals("3")) {
			stressAnalysis.setIncomeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.INCOMEGROUP3);

		} else {
			stressAnalysis.setIncomeGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.INCOMEGROUP4);
		}
	}

	private void populateStressForProfExp(RegisterUser registerUser, StressAnalysis stressAnalysis) {
		if (registerUser.getAnswer3().equals("1")) {
			stressAnalysis.setProfExpGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.PROFEXP1);

		} else if (registerUser.getAnswer3().equals("2")) {
			stressAnalysis.setProfExpGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.PROFEXP2);

		} else if (registerUser.getAnswer3().equals("3")) {
			stressAnalysis.setProfExpGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.PROFEXP3);

		} else {
			stressAnalysis.setProfExpGroupDesc(PersonalHealtConstantsIF.BusinessStressIF.PROFEXP4);
		}
	}

	private StatusInfo putAnswersForHealAnalyis(ArrayList<QuestionVO> questionObj, String patName, int totalRating,
			String type) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String currentTime = String.valueOf(System.currentTimeMillis());

			for (QuestionVO questionVO : questionObj) {
				AnswerVO answerVO = new AnswerVO();

				answerVO.setPatName(patName);
				answerVO.setTotalRating(totalRating);
				answerVO.setType(type);
				answerVO.setQuestionVO(questionVO);
				answerVO.setCurrentTime(currentTime);
				statusInfo = personalHealtDao.insertAnswer(answerVO);

				if (!statusInfo.isStatus()) {
					return statusInfo;
				}
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

	}

	private String computeType(int totalRating, RangeModel rangeModel) {

		String type = PersonalHealtConstantsIF.Keys.NOSTRESS;

		if (totalRating <= rangeModel.getR1HL()) {
			type = PersonalHealtConstantsIF.Keys.NOSTRESS;
		} else if (totalRating <= rangeModel.getR2HL() && totalRating > rangeModel.getR1HL()) {
			type = PersonalHealtConstantsIF.Keys.LOWSTRESS;
		} else if (totalRating > rangeModel.getR2HL() && totalRating <= rangeModel.getR3HL()) {
			type = PersonalHealtConstantsIF.Keys.MEDIUMSTRESS;
		} else if (totalRating > rangeModel.getR3HL()) {
			type = PersonalHealtConstantsIF.Keys.HIGHSTRESS;
		}
		return type;

	}

	private String computeTypeDISEASE(int totalRating, RangeModel rangeModel) {

		String type = PersonalHealtConstantsIF.Keys.NODISEASE;

		if (totalRating <= rangeModel.getR1HL()) {
			type = PersonalHealtConstantsIF.Keys.NODISEASE;
		} else if (totalRating <= rangeModel.getR2HL() && totalRating > rangeModel.getR1HL()) {
			type = PersonalHealtConstantsIF.Keys.LOWDISEASE;
		} else if (totalRating > rangeModel.getR2HL() && totalRating <= rangeModel.getR3HL()) {
			type = PersonalHealtConstantsIF.Keys.MEDIUMDISEASE;
		} else if (totalRating > rangeModel.getR3HL()) {
			type = PersonalHealtConstantsIF.Keys.HIGHDISEASE;
		}
		return type;

	}

	private int computeTotalMarks(ArrayList<QuestionVO> questionObj, String testName) {

		if (null == questionObj || questionObj.isEmpty() || questionObj.size() == 0) {

			return -1;

		}

		int totalRating = 0;

		for (QuestionVO questionVO : questionObj) {

			String testNameTemp = questionVO.getTestName();
			if (testNameTemp != null && !testNameTemp.isEmpty() && testNameTemp.equals(testName)) {
				int tempRating = computeMarkForQuestion(questionVO);

				if (tempRating <= 0) {
					tempRating = 0;
				} else {
					totalRating = totalRating + tempRating;
				}
			}
		}

		return totalRating;
	}

	private int computeMarkForQuestion(QuestionVO questionVO) {

		int answer = questionVO.getSelectedAnswer();
		if (answer <= 0) {
			return -1;
		} else {
			if (answer == 1) {
				return questionVO.getRating1();
			}
			if (answer == 2) {
				return questionVO.getRating2();
			}
			if (answer == 3) {
				return questionVO.getRating3();
			}
			if (answer == 4) {
				return questionVO.getRating4();
			}

		}

		return 0;

	}

	private List<SuggestionVO> computeSuggestions(ArrayList<QuestionVO> questionObjList) {

		List<SuggestionVO> sugList = null;
		try {
			sugList = new ArrayList<SuggestionVO>();

			for (QuestionVO questionVO : questionObjList) {
				SuggestionVO suggestionVO = new SuggestionVO();

				int answer = questionVO.getSelectedAnswer();

				String sug = getSugForAns(answer, questionVO);

				if (null == sug) {
					return null;
				} else {
					suggestionVO.setSug(sug);
					sugList.add(suggestionVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}

		return sugList;
	}

	private String getSugForAns(int answer, QuestionVO questionVO) {

		String sug = null;
		if (answer == 1) {
			sug = questionVO.getSug1();
			return sug;
		}
		if (answer == 2) {
			sug = questionVO.getSug2();
			return sug;
		}
		if (answer == 3) {
			sug = questionVO.getSug3();
			return sug;
		}
		if (answer == 4) {
			sug = questionVO.getSug4();
			return sug;
		}
		return sug;

	}

	@Override
	public StatusInfo doAppointmentReq(String loginId) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();

			if (null == loginId || loginId.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				return statusInfo;
			}

			List<String> patNames = personalHealtDao.retrivePatNamesFromAppointment();
			if (null == patNames || patNames.isEmpty()) {
				statusInfo = personalHealtDao.insertAppointment(loginId, PersonalHealtConstantsIF.Keys.UNAPPROVED_KEY);
			} else if (patNames.contains(loginId)) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PATNAME_ALREADYEXIST);
				return statusInfo;
			} else {
				statusInfo = personalHealtDao.insertAppointment(loginId, PersonalHealtConstantsIF.Keys.UNAPPROVED_KEY);
			}
			if (!statusInfo.isStatus()) {
				return statusInfo;
			} else {

				statusInfo.setStatus(true);

				return statusInfo;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public StatusInfo retriveDashboard(String loginId) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();

			statusInfo = personalHealtDao.retriveAppointStatus(loginId);

			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<QuestionVO> retriveQuestionsForTest(String testId) {

		List<QuestionVO> questionVOList = null;
		try {

			questionVOList = personalHealtDao.retriveQuestions(testId);
			if (null == questionVOList) {
				return null;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	@Override
	public List<DiabeticsGraph> retriveDiabeticsGraph() {
		List<DiabeticsGraph> diabeticsGraphVOList = null;
		try {
			diabeticsGraphVOList = new ArrayList<DiabeticsGraph>();

			List<String> diabeticsList = personalHealtDao.retriveUniqueTestNames();

			if (null == diabeticsList || diabeticsList.isEmpty() || diabeticsList.size() == 0) {
				return null;
			}

			List<String> pateintNames = personalHealtDao.retrivePatientNamesFromEligibility();

			if (null == pateintNames || pateintNames.isEmpty() || pateintNames.size() == 0) {
				return null;
			}

			for (String testName : diabeticsList) {

				for (String patName : pateintNames) {

					StatusInfo statusInfo = personalHealtDao.retriveTotalRatingFromPatName(patName, testName);
					if (statusInfo.isStatus()) {

						DiabeticsGraph diabeticsGraph = new DiabeticsGraph();

						diabeticsGraph.setPatName(patName);
						diabeticsGraph.setRating(statusInfo.getType());

						diabeticsGraphVOList.add(diabeticsGraph);

					}

				}
			}
			if (null == diabeticsGraphVOList || diabeticsGraphVOList.isEmpty() || diabeticsGraphVOList.size() == 0) {
				return null;
			}

		} catch (Exception e) {
			return diabeticsGraphVOList;
		}
		return diabeticsGraphVOList;
	}

	@Override
	public StatusInfo createEligibilityForAll(EligibilityModel eligibilityModel) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> pateintNames = personalHealtDao.retriveUserIdsForPateint();

			if (null == pateintNames || pateintNames.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PATLIST_EMPTY);
				return statusInfo;
			} else {
				EligibilityModel eligibilityModel2 = new EligibilityModel();

				for (String patName : pateintNames) {
					eligibilityModel2.setPatName(patName);
					eligibilityModel2.setTestId(eligibilityModel.getTestId());

					statusInfo = createEligibility(eligibilityModel2);
					if (!statusInfo.isStatus()) {
						statusInfo = new StatusInfo();
						statusInfo.setStatus(false);
						statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_CREATE_ELIGIBILITY);
						return statusInfo;
					}
				}

			}
		} catch (Exception e) {

			e.printStackTrace();

			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
		statusInfo = new StatusInfo();
		statusInfo.setStatus(true);
		return statusInfo;

	}

	@Override
	public List<DoctorInfo> retriveDoctorList() {
		List<DoctorInfo> appointInfoList = null;

		try {
			appointInfoList = personalHealtDao.retriveDoctorList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return appointInfoList;
		}
		return appointInfoList;

	}

	@Override
	public List<AppointVO> retriveAppointments(String doctorName) {
		List<AppointVO> appointInfoList = null;

		try {
			appointInfoList = personalHealtDao.retriveAppointments(doctorName);
		} catch (Exception e) {
			return appointInfoList;
		}
		return appointInfoList;
	}

	@Override
	public List<AppointVO> retriveDashboardForPatName(String patName) {
		List<AppointVO> appVOList = null;
		try {
			appVOList = personalHealtDao.retriveAppointmentsForPatName(patName);
			return appVOList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AnswerVO> retriveAllGeneralQuestions(String patName) {
		List<AnswerVO> questionVOList = new ArrayList<AnswerVO>();
		try {

			List<String> testNames = personalHealtDao.retriveUniqueTestNames();

			String testNameObj = null;

			if (testNames != null && !testNames.isEmpty()) {

				int size = testNames.size();

				int index = StressHelperUtil.getRandomNumberInRange(0, size);

				testNameObj = testNames.get(index);

			}

			List<QuestionVO> questionVOs = personalHealtDao.retriveQuestions(testNameObj);

			int pageNumberGlobal = 1;

			for (QuestionVO questionVO : questionVOs) {

				AnswerVO answerVO = populateAnswerVO(patName, pageNumberGlobal, questionVO);
				questionVOList.add(answerVO);

				pageNumberGlobal = pageNumberGlobal + 1;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	private AnswerVO populateAnswerVO(String patName, int pageNumberGlobal, QuestionVO questionVO) {
		AnswerVO answerVO = new AnswerVO();
		answerVO.setPageNumberGlobal(pageNumberGlobal);
		answerVO.setPatName(patName);
		questionVO.setPageNumber(pageNumberGlobal);
		answerVO.setQuestionVO(questionVO);
		return answerVO;
	}

	@Override
	public List<QuestionVO> retriveQuestionsPagination(String loginId, int start, int end, int limit) {

		List<QuestionVO> questionVOList = null;

		try {
			String testName = personalHealtDao.retriveTestNameFromEligibility(loginId);

			if (null == testName) {
				return null;
			}

			questionVOList = personalHealtDao.retriveQuestions(testName);
			if (questionVOList != null && !questionVOList.isEmpty()) {

				if (end < questionVOList.size()) {
					questionVOList = questionVOList.subList(start, end);
				} else {
					questionVOList = questionVOList.subList(start, questionVOList.size());
				}
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	@Override
	public List<AnswerVO> retriveAllGeneralQuestionsForUser(String userId) {
		List<AnswerVO> questionVOList = new ArrayList<AnswerVO>();
		try {

			List<String> testNames = personalHealtDao.retriveUniqueTestNames();

			List<String> testNamesForUser = personalHealtDao.retriveUniqueTestNamesFromQuestionsForUserId(userId);

			List<String> unAttendedTests = findUnAttendedTests(testNames, testNamesForUser);

			String testNameObj = findBestTestName(unAttendedTests, testNames);

			List<QuestionVO> questionVOs = personalHealtDao.retriveQuestions(testNameObj);

			int pageNumberGlobal = 1;

			for (QuestionVO questionVO : questionVOs) {

				AnswerVO answerVO = populateAnswerVO(userId, pageNumberGlobal, questionVO);
				questionVOList.add(answerVO);

				pageNumberGlobal = pageNumberGlobal + 1;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	private String findBestTestName(List<String> unAttendedTests, List<String> testNames) {

		String bestTestName = null;
		if (null == unAttendedTests || (unAttendedTests != null && unAttendedTests.isEmpty())) {

			int index = StressHelperUtil.getRandomNumberInRange(0, testNames.size());
			bestTestName = testNames.get(index);

		} else {
			bestTestName = unAttendedTests.get(0);
		}

		return bestTestName;

	}

	private List<String> findUnAttendedTests(List<String> testNames, List<String> testNamesForUser) {
		List<String> unAttendedTests = new ArrayList<String>();

		if (testNamesForUser != null && !testNamesForUser.isEmpty()) {

			for (String testName : testNames) {

				if (!testNamesForUser.contains(testName)) {
					unAttendedTests.add(testName);
				}
			}
		}

		return unAttendedTests;

	}

	@Override
	public List<DiabeticsGraph> retriveStressGraphForUser(String userId) {
		List<DiabeticsGraph> diabeticsGraphVOList = null;
		try {
			diabeticsGraphVOList = new ArrayList<DiabeticsGraph>();

			/*
			 * StatusInfo statusInfo = stressManageDao
			 * .retriveTotalRatingFromPatName(userId, curTime);
			 */
			diabeticsGraphVOList = personalHealtDao.retriveTotalRatingFromPatNameFromAnalysis(userId);

			if (null == diabeticsGraphVOList || diabeticsGraphVOList.isEmpty() || diabeticsGraphVOList.size() == 0) {
				return null;
			}

		} catch (Exception e) {
			return diabeticsGraphVOList;
		}
		return diabeticsGraphVOList;
	}

	@Override
	public StatusInfo savePuzzle(PuzzleModel puzzleVO) {

		return personalHealtDao.savePuzzle(puzzleVO);

	}

	@Override
	public List<PuzzleVO> retrivePuzzle() {

		List<PuzzleVO> puzzleVOList = null;
		try {
			puzzleVOList = personalHealtDao.retrivePuzzle();

		} catch (Exception e) {

			System.out.println("Exception");
			System.out.println(e.getMessage());
		}

		return puzzleVOList;

	}

	@Override
	public List<PuzzleVO> retrivePuzzleForUser(String username) {

		List<PuzzleVO> puzzleVOListNew = null;
		try {

			String ageGroup = personalHealtDao.findAgeForUserNameFromLogin(username);

			List<PuzzleVO> puzzleVOList = personalHealtDao.retrivePuzzleForAgeGroup(ageGroup);

			if (checkIsEmptyPuzzle(puzzleVOList)) {
				return null;
			}

			if (puzzleVOList.size() < 10) {
				return puzzleVOList;
			} else {

				puzzleVOListNew = obtainRandomPuzzleQuestions(puzzleVOList);

			}

		} catch (Exception e) {

			System.out.println("Exception");
			System.out.println(e.getMessage());
			puzzleVOListNew = null;
		}

		return puzzleVOListNew;

	}

	private List<PuzzleVO> obtainRandomPuzzleQuestions(List<PuzzleVO> puzzleVOList) {

		List<PuzzleVO> finalPuzzles = new ArrayList<PuzzleVO>();
		List<Integer> finalIndexes = new ArrayList<Integer>();

		List<Integer> indexList = new ArrayList<Integer>();

		for (int i = 0; i < puzzleVOList.size(); i++) {
			indexList.add(i);
		}

		Collections.shuffle(indexList);

		for (int j = 0; j < 10; j++) {
			finalIndexes.add(indexList.get(j));
		}

		for (int i = 0; i < finalIndexes.size(); i++) {

			finalPuzzles.add(puzzleVOList.get(finalIndexes.get(i)));
		}
		return finalPuzzles;
	}

	private boolean checkIsEmptyPuzzle(List<PuzzleVO> puzzleVOList) {
		return null == puzzleVOList || (puzzleVOList != null && puzzleVOList.isEmpty());
	}

	@Override
	public List<ClassificationAgeGroup> classifyAgeGroup(String ageGroup) {

		List<ClassificationAgeGroup> classificationAgeGroupList = null;
		try {

			classificationAgeGroupList = personalHealtDao.retriveClassifyByAge(ageGroup);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationAgeGroupList;
	}

	@Override
	public List<ClassificationGroup> classifyIncomeGroup(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyByIncome(group);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> classifyExpGroup(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyByExp(group);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> classifySexGroup(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyBySex(group);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public StatusInfo readDataSets() {
		StatusInfo statusInfo = new StatusInfo();
		List<RowInfo> rowInfoList = new ArrayList<RowInfo>();
		try {

			List<Integer> rowIds = personalHealtDao.retriveUniqueRowIds();
			if (null == rowIds || rowIds.isEmpty()) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_FIND_DATASETS);
				return statusInfo;
			}

			for (Integer rowIdTemp : rowIds) {

				List<AttributeInformation> attributeInformations = personalHealtDao
						.reytiveAttributeInfoForRowId(rowIdTemp);

				if (null == attributeInformations || attributeInformations.isEmpty()) {
					statusInfo.setStatus(false);
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_FIND_DATASETS);
					return statusInfo;

				}

				RowInfo rowInfo = new RowInfo();
				rowInfo.setAttributeInfo(attributeInformations);
				rowInfoList.add(rowInfo);

			}

			// Status
			statusInfo = new StatusInfo();
			statusInfo.setStatus(true);

			GlobalRowInfo globalRowInfo = new GlobalRowInfo();
			globalRowInfo.setRowInfoList(rowInfoList);
			statusInfo.setModel(globalRowInfo);

			return statusInfo;

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getLocalizedMessage());

		}
		return statusInfo;
	}

	@Override
	public GlobalNaiveBayesOutput performNaiveBayesCalculation(
			NaiveBayesDataSetModelInput naiveBayesDataSetModelInput) {

		GlobalNaiveBayesOutput globalNaiveBayesOutput = new GlobalNaiveBayesOutput();
		NaiveBayesOutput hyperTensionOutput = performNavBayesForDiseaseType(naiveBayesDataSetModelInput,
				PersonalHealtConstantsIF.Classify.HYPERTENSION);

		globalNaiveBayesOutput.setHyperTensionOutput(hyperTensionOutput);

		NaiveBayesOutput lungcancerOutput = performNavBayesForDiseaseType(naiveBayesDataSetModelInput,
				PersonalHealtConstantsIF.Classify.LUNGCANCER);

		globalNaiveBayesOutput.setLungcancerOutput(lungcancerOutput);

		NaiveBayesOutput thyroidOutput = performNavBayesForDiseaseType(naiveBayesDataSetModelInput,
				PersonalHealtConstantsIF.Classify.THYROID);

		globalNaiveBayesOutput.setThyroidOutput(thyroidOutput);

		// Now do an Insertion into the Probability

		StatusInfo statusInfo = personalHealtDao
				.deleteAllProbabilitiesForUserId(naiveBayesDataSetModelInput.getUserId());

		if (!statusInfo.isStatus()) {

			statusInfo.setStatus(false);

			globalNaiveBayesOutput.setGlobalException("Could not Remove the Probability");

			return globalNaiveBayesOutput;
		}

		// Computing for Hyper Tension
		computeProbabilityForCatName(naiveBayesDataSetModelInput, globalNaiveBayesOutput, hyperTensionOutput,
				PersonalHealtConstantsIF.Classify.HYPERTENSION);

		if (globalNaiveBayesOutput.getGlobalException() != null
				&& !globalNaiveBayesOutput.getGlobalException().isEmpty()) {

			return globalNaiveBayesOutput;
		}

		// Computing for Lung Cancer
		computeProbabilityForCatName(naiveBayesDataSetModelInput, globalNaiveBayesOutput, lungcancerOutput,
				PersonalHealtConstantsIF.Classify.LUNGCANCER);

		if (globalNaiveBayesOutput.getGlobalException() != null
				&& !globalNaiveBayesOutput.getGlobalException().isEmpty()) {

			return globalNaiveBayesOutput;
		}

		// Computing for Thyroid
		computeProbabilityForCatName(naiveBayesDataSetModelInput, globalNaiveBayesOutput, thyroidOutput,
				PersonalHealtConstantsIF.Classify.THYROID);

		return globalNaiveBayesOutput;
	}

	private void computeProbabilityForCatName(NaiveBayesDataSetModelInput naiveBayesDataSetModelInput,
			GlobalNaiveBayesOutput globalNaiveBayesOutput, NaiveBayesOutput hyperTensionOutput, String catName) {
		// Now create ProbabilityInfo and insert

		ProbabilityInfo probabilityInfo = new ProbabilityInfo();
		probabilityInfo.setCatName(catName);
		BigDecimal probability = hyperTensionOutput.getClassYesProbability();
		probabilityInfo.setProbability(probability);

		BigDecimal oneValue = new BigDecimal(1);

		BigDecimal negativeProbability = oneValue.subtract(probability);

		probabilityInfo.setNegativeProbability(negativeProbability);
		probabilityInfo.setUsername(naiveBayesDataSetModelInput.getUserId());

		StatusInfo statusInfo2 = personalHealtDao.insertProbability(probabilityInfo);

		if (!statusInfo2.isStatus()) {
			globalNaiveBayesOutput.setGlobalException("Could not Store the Hyper Tension Probability");
		}
	}

	private NaiveBayesOutput performNavBayesForDiseaseType(NaiveBayesDataSetModelInput naiveBayesDataSetModelInput,
			String diseaseType) {
		NaiveBayesOutput navBayesOutput = new NaiveBayesOutput();
		try {

			List<AttributeComputationModel> attributeComputationModels = new ArrayList<AttributeComputationModel>();

			// Obtain the List of Attribute Names

			List<String> attributeNames = personalHealtDao.retriveUniqueAttributeListForType(diseaseType);

			Map<String, Double> mapCriteria = naiveBayesDataSetModelInput.getMapCriteria();

			BigDecimal probabilityTotalNegative = new BigDecimal(0);
			BigDecimal probabilityTotalPositive = new BigDecimal(0);

			for (String attributeName : attributeNames) {

				Double attributeValue = mapCriteria.get(attributeName);

				AttributeComputationModel attributeComputationModel = new AttributeComputationModel();

				// Obtain list of Yes values
				List<Double> list = personalHealtDao.retriveListAttributeInfoWithStatus("YES", attributeName);

				attributeComputationModel.setAttributeName(attributeName);

				if (list != null && !list.isEmpty()) {

					double totalSumPositive = computeSum(list);

					attributeComputationModel.setTotalSumPositive(new BigDecimal(totalSumPositive));

					double meanPositive = computeMean(totalSumPositive, list.size());
					attributeComputationModel.setMeanPositive(new BigDecimal(meanPositive));

					double standardDevPositive = computeStandardDeviation(list, meanPositive);
					attributeComputationModel.setStandardDevPositive(new BigDecimal(standardDevPositive));

					BigDecimal probabilityPositive = computeProbability(standardDevPositive, meanPositive,
							attributeValue);

					attributeComputationModel.setProbabilityPositive(probabilityPositive);

					BigDecimal x = probabilityPositive;

					probabilityTotalPositive = x.add(probabilityTotalPositive);

					System.out.println("Prob Positive");
					System.out.println(probabilityPositive);

					System.out.println("Total positive");
					System.out.println(probabilityTotalPositive);

				}

				// Obtain list of No values
				List<Double> list2 = personalHealtDao.retriveListAttributeInfoWithStatus("NO", attributeName);

				if (list2 != null && !list2.isEmpty()) {

					double totalSumNegative = computeSum(list2);

					double meanNegative = computeMean(totalSumNegative, list2.size());

					double standardDevNegative = computeStandardDeviation(list2, meanNegative);

					BigDecimal probabilityNegative = computeProbability(standardDevNegative, meanNegative,
							attributeValue);

					BigDecimal x = probabilityNegative;

					probabilityTotalNegative = x.add(probabilityTotalNegative);

					System.out.println("Prob Negative");
					System.out.println(probabilityNegative);

					System.out.println("Total Negative");
					System.out.println(probabilityTotalNegative);

					attributeComputationModel.setTotalSumNegative(new BigDecimal(totalSumNegative));
					attributeComputationModel.setMeanNegative(new BigDecimal(meanNegative));
					attributeComputationModel.setStandardDevNegative(new BigDecimal(standardDevNegative));
					attributeComputationModel.setProbabilityNegative(probabilityNegative);

				}

				attributeComputationModels.add(attributeComputationModel);
			}

			navBayesOutput.setAttributeComputationModelList(attributeComputationModels);
			navBayesOutput.setTotalMeasureNegative(probabilityTotalNegative);
			navBayesOutput.setTotalMeasurePositive(probabilityTotalPositive);

			BigDecimal denom = new BigDecimal(1);

			denom = denom.add(probabilityTotalNegative);
			denom = denom.add(probabilityTotalPositive);

			// Now Compute Positive Probability

			BigDecimal c = probabilityTotalPositive.divide(denom, 2, BigDecimal.ROUND_HALF_UP);

			BigDecimal positiveProbability = new BigDecimal(0.5);
			positiveProbability = positiveProbability.multiply(c);

			// Now Compute Negative Probability
			BigDecimal c1 = probabilityTotalNegative.divide(denom, 2, BigDecimal.ROUND_HALF_UP);

			BigDecimal negativeProbability = new BigDecimal(0.5);
			negativeProbability = negativeProbability.multiply(c1);

			// Compute the Negative Probability
			navBayesOutput.setClassYesProbability(positiveProbability);
			navBayesOutput.setClassNoProbability(negativeProbability);

			int res = positiveProbability.compareTo(negativeProbability);

			if (res == 1) {
				navBayesOutput.setOutputFactor(true);
			} else {
				navBayesOutput.setOutputFactor(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			navBayesOutput.setExceptionMessage(e.getMessage());
		}
		return navBayesOutput;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private Double computeSum(List<Double> list) {

		double sum = 0;
		try {

			if (list != null) {

				for (Double temp : list) {
					sum = sum + temp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
		}

		return sum;
	}

	private BigDecimal computeProbability(double standardDeviation, double mean, double data) {

		BigDecimal probability = new BigDecimal(0);
		try {

			MathContext mc = new MathContext(4);

			BigDecimal bg1 = new BigDecimal(2 * 3.14);
			BigDecimal bg2 = new BigDecimal(standardDeviation);

			BigDecimal bg3 = bg1.multiply(bg2, mc);

			BigDecimal SqrtValue = sqrt(bg3);

			BigDecimal num = new BigDecimal(1);
			BigDecimal left_expression = num.divide(SqrtValue, 20, RoundingMode.HALF_UP);

			double value = mean - data;

			double powerValue = Math.pow(value, 2);

			double value2 = 2 * standardDeviation;

			double bracketValue = (double) ((double) powerValue / (double) value2);

			double right_expression = Math.exp(-bracketValue);

			BigDecimal rightExp = new BigDecimal(right_expression);

			probability = left_expression.multiply(rightExp, mc);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
		}

		return probability;

	}

	public static BigDecimal sqrt(BigDecimal value) {
		BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
		return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
	}

	// Addding Square Root Custom Method

	private static final BigDecimal SQRT_DIG = new BigDecimal(150);
	private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

	private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
		BigDecimal fx = xn.pow(2).add(c.negate());
		BigDecimal fpx = xn.multiply(new BigDecimal(2));
		BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
		xn1 = xn.add(xn1.negate());
		BigDecimal currentSquare = xn1.pow(2);
		BigDecimal currentPrecision = currentSquare.subtract(c);
		currentPrecision = currentPrecision.abs();
		if (currentPrecision.compareTo(precision) <= -1) {
			return xn1;
		}
		return sqrtNewtonRaphson(c, xn1, precision);
	}

	private double computeStandardDeviation(List<Double> list, double mean) {
		// Computing the Standing Deviation

		double standardDev = 0;

		double totalSum = 0;

		for (Double tempNew : list) {

			double temp = tempNew - mean;

			double tempSquare = Math.pow(temp, 2);

			totalSum = totalSum + tempSquare;

		}

		double standardDevTemp = 0;
		if (list != null && list.size() > 1) {

			standardDevTemp = (double) ((double) totalSum / (double) list.size());
		} else {

			standardDevTemp = (double) ((double) totalSum / (double) 1);
		}

		standardDev = Math.sqrt(standardDevTemp);

		return standardDev;
	}

	private Double computeMean(Double total, int size) {
		double mean = 1;
		mean = (double) (double) total / (double) size;

		return mean;
	}

	public static void main(String s[]) {

		double v = computeProbabilityNew(0.0100, 0.01002, 0.0100);

		System.out.println(v);
	}

	public static double computeProbabilityNew(double standardDeviation, double mean, double data) {

		double probability = 0;
		try {

			double left_expression = (double) (1 / Math.sqrt(2 * 3.14 * standardDeviation));

			double value1 = Math.pow(mean - data, 2);

			double value2 = 2 * standardDeviation;

			double value = (double) ((double) value1 / (double) value2);

			double right_expression = Math.exp(-value);

			probability = left_expression * right_expression;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
		}

		return probability;

	}

	@Override
	public List<ProbabilityInfo> viewProbability() {
		List<ProbabilityInfo> probabilityInfoList = null;
		try {
			probabilityInfoList = personalHealtDao.retriveAllProbability();
			if (null == probabilityInfoList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return probabilityInfoList;
	}

	@Override
	public StatusInfo doContigency() {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			statusInfo = new StatusInfo();
			// Delete the Probabilities
			statusInfo = personalHealtDao.deleteAllContigency();
			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.DELETE_CONTIGENCY);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			// Now Obtain the List of Probabilities
			List<ProbabilityInfo> probabilityInfos = personalHealtDao.retriveAllProbability();
			if (null == probabilityInfos || probabilityInfos.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.NOPROBABILITIES_FOUND);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			List<ContigencyInfo> contigencyInfos = new ArrayList<ContigencyInfo>();
			// Now Loop through each and determine the Contigency Information
			for (ProbabilityInfo probabilityInfo : probabilityInfos) {
				// Create a Contigency Information and get back
				ContigencyInfo contigencyInfo = new ContigencyInfo();

				contigencyInfo.setUserName(probabilityInfo.getUsername());

				contigencyInfo.setNegativeProbability(probabilityInfo.getNegativeProbability());
				contigencyInfo.setProbability(probabilityInfo.getProbability());
				contigencyInfo.setCatName(probabilityInfo.getCatName());

				ContigencyInfo contigencyInfoTemp = personalHealtDao.findTotalPosOthersAnsTotalNegativeOthers(
						probabilityInfo.getUsername(), probabilityInfo.getCatName());

				if (null == contigencyInfoTemp) {
					statusInfo = new StatusInfo();
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.CONTIGENCY_COULD_NOT_FOUND);
					statusInfo.setStatus(false);
					return statusInfo;
				}

				contigencyInfo.setTotalNegativeOthers(contigencyInfoTemp.getTotalNegativeOthers());
				contigencyInfo.setTotalPositiveOthers(contigencyInfoTemp.getTotalPositiveOthers());

				contigencyInfos.add(contigencyInfo);

			}

			// Now Insert the Contigency into the Database
			for (ContigencyInfo contigencyInfo : contigencyInfos) {
				StatusInfo statusInfo5 = personalHealtDao.insertContigency(contigencyInfo);
				if (!statusInfo5.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.CONTIGENCY_COULD_NOT_INSERT);
					statusInfo.setStatus(false);
					return statusInfo;
				}

			}

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
		return statusInfo;
	}

	@Override
	public List<ContigencyInfo> viewContigency() {
		List<ContigencyInfo> contigencyInfoList = null;
		try {
			contigencyInfoList = personalHealtDao.retriveAllContigency();
			if (null == contigencyInfoList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return contigencyInfoList;
	}

	@Override
	public List<EnhanceContigency> viewEnhanceContigency() {
		List<EnhanceContigency> enhanceContigencyList = null;
		try {
			enhanceContigencyList = personalHealtDao.retriveAllEnhanceContigency();
			if (null == enhanceContigencyList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return enhanceContigencyList;
	}

	@Override
	public StatusInfo doEnhanceContigency() {
		StatusInfo statusInfo = null;
		try {

			StatusInfo statusInfo2 = personalHealtDao.deleteAllEnhanceContigency();

			if (!statusInfo2.isStatus()) {
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.DELETE_ENHANCE_CONTIGENCY_FAILED);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			List<ContigencyInfo> contigencyInfos = personalHealtDao.retriveAllContigency();

			if (null == contigencyInfos || contigencyInfos.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PLEASE_PERFORM_CONTIGENCY);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			for (ContigencyInfo contigencyInfo : contigencyInfos) {
				EnhanceContigency enhanceContigency = new EnhanceContigency();

				enhanceContigency.setUserName(contigencyInfo.getUserName());
				enhanceContigency.setCatName(contigencyInfo.getCatName());

				BigDecimal probability = contigencyInfo.getProbability();

				BigDecimal positiveCatRatio = probability.add(contigencyInfo.getTotalNegativeOthers());

				enhanceContigency.setPositiveCatRatio(positiveCatRatio);

				BigDecimal negativeProbability = contigencyInfo.getNegativeProbability();

				BigDecimal otherCatRatio = negativeProbability.add(contigencyInfo.getTotalPositiveOthers());

				enhanceContigency.setOtherCatRatio(otherCatRatio);

				statusInfo = personalHealtDao.insertEhanceContigency(enhanceContigency);

				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.ENHANCE_CONTIGENCY_INSERT_FAILED);
					statusInfo.setStatus(false);
					return statusInfo;
				}

			}

			statusInfo.setStatus(true);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
		return statusInfo;
	}

	@Override
	public StatusInfo classifyType() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			statusInfo = personalHealtDao.deleteAllClassifier();

			if (!statusInfo.isStatus()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_DELETE_OLD_CLASS_INFO);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			List<EnhanceContigency> contigencyInfos = personalHealtDao.retriveAllEnhanceContigency();

			if (null == contigencyInfos || contigencyInfos.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PLEASE_PERFORM_ENHNACE_CONTIGENCY);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			// Obtain Distinct User Ids from Comtigency Table
			List<String> distinctUserIds = personalHealtDao.retriveDistinctUsersIdsFromEnhanceContigency();

			if (null == distinctUserIds || distinctUserIds.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.NO_USERIDS_ENHANCECONTIGENCY);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			List<ClassifierInfo> classifierInfo = new ArrayList<ClassifierInfo>();

			for (String userId : distinctUserIds) {

				List<PartialClassifierInfo> classInfo = personalHealtDao.retriveClassifierByRank(userId);

				if (classInfo != null && !classInfo.isEmpty()) {

					double probabilityPositive = 0;
					double probabilityNegative = 0;

					for (int i = 0; i < classInfo.size(); i++) {

						PartialClassifierInfo pcI = classInfo.get(i);

						if (i == 0) {
							ClassifierInfo classifierInfoTemp = new ClassifierInfo();
							classifierInfoTemp.setCatName(pcI.getCatName());
							classifierInfoTemp.setUserName(pcI.getUserId());
							classifierInfo.add(classifierInfoTemp);
							probabilityPositive = pcI.getPositiveRatio();
							probabilityNegative = pcI.getNegativeRatio();
						} else {
							double PP = pcI.getPositiveRatio();
							double np = pcI.getNegativeRatio();

							if (PP == probabilityPositive && np == probabilityNegative) {

								ClassifierInfo classifierInfoTemp = new ClassifierInfo();
								classifierInfoTemp.setCatName(pcI.getCatName());
								classifierInfoTemp.setUserName(pcI.getUserId());
								classifierInfo.add(classifierInfoTemp);

							}
						}

					}
				}

			}

			// Now the List of Classification is Fed Into Database
			if (null == classifierInfo || classifierInfo.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.CLASSIFY_NOT_POSSIBLE_AT_THIS_TIME);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			// Now Fed into the Classifier Information
			for (ClassifierInfo ci : classifierInfo) {

				StatusInfo statusInfo2 = personalHealtDao.insertClassfierInfo(ci);

				if (!statusInfo2.isStatus()) {

					statusInfo = new StatusInfo();
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PATIAL_CLASSIFICATION_DONE);
					statusInfo.setStatus(false);
					return statusInfo;
				}

			}

			statusInfo = new StatusInfo();
			statusInfo.setStatus(true);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
		return statusInfo;
	}

	@Override
	public List<ClassifierInfo> viewClassifier() {
		List<ClassifierInfo> classifierInfoList = null;
		try {
			classifierInfoList = personalHealtDao.retriveAllClassifierInfo();
			if (null == classifierInfoList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return classifierInfoList;
	}

	@Override
	public List<CategoryCountVO> viewClassifierCount() {
		List<CategoryCountVO> categoryCountVOList = null;
		try {
			categoryCountVOList = personalHealtDao.viewClassifierCount();
			if (null == categoryCountVOList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return categoryCountVOList;
	}

	@Override
	public List<PatientNames> retrivePatNames() {
		List<PatientNames> pateintList = null;

		try {
			pateintList = personalHealtDao.retrivePatientNamesFromAnalysis();
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintName(String patName) {
		List<TestInfo> pateintList = null;

		try {
			pateintList = personalHealtDao.retriveTestNamesForPateintName(patName);
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestId(String patName, String testId) {
		List<AnalysisData> pateintList = null;

		try {
			pateintList = personalHealtDao.retriveAnalysisForPatNameAndTestId(patName, testId);
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;

	}

	@Override
	public List<AnswerVO> retriveAllDiseaseGeneralQuestionsForUser(String userId) {
		List<AnswerVO> questionVOList = new ArrayList<AnswerVO>();
		try {

			List<String> testNames = personalHealtDao.retriveUniqueTestNamesDisease();

			List<String> testNamesForUser = personalHealtDao
					.retriveUniqueTestNamesFromQuestionsForUserIdDisease(userId);

			List<String> unAttendedTests = findUnAttendedTests(testNames, testNamesForUser);

			String testNameObj = findBestTestName(unAttendedTests, testNames);

			List<QuestionVO> questionVOs = personalHealtDao.retriveQuestionsDisease(testNameObj);

			int pageNumberGlobal = 1;

			for (QuestionVO questionVO : questionVOs) {

				AnswerVO answerVO = populateAnswerVO(userId, pageNumberGlobal, questionVO);
				questionVOList.add(answerVO);

				pageNumberGlobal = pageNumberGlobal + 1;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	@Override
	public HealtInfo processHealtDisease(ArrayList<QuestionVO> questionObj, String patName, String registerOrNot) {
		HealtInfo healtInfo = null;
		try {

			String testName = questionObj.get(0).getTestName();
			healtInfo = new HealtInfo();

			RangeModel rangeModel = personalHealtDao.retriveRangeModelDisease(testName);

			if (null == rangeModel) {
				healtInfo = new HealtInfo();
				healtInfo.setStatus(false);
				healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.RANGEMODEL_EMPTY);
				return healtInfo;
			}

			// Now Find the Total Rating

			int totalRating = computeTotalMarksDisease(questionObj, testName);

			if (totalRating <= 0) {
				healtInfo = new HealtInfo();
				healtInfo.setStatus(false);
				healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.TOTAL_RATING_INVALID);
				return healtInfo;
			} else {

				// now put answers for Healt Analyis

				// Add Total Rating
				healtInfo.setTotalRating(totalRating);

				// Now find whether person has low high or medium Healt

				String type = computeTypeDISEASE(totalRating, rangeModel);

				/*
				 * List<SuggestionObj> sugVOList = personalHealtDao
				 * .findSuggestionsByType(type);
				 */

				List<SuggestionVO> sugVOList = computeSuggestionsDisease(questionObj);

				List<SuggestionObj> sugVOListNew = convertSuggestionFromOneFormToOtherDisease(sugVOList, type);

				healtInfo.setSuggestionObjList(sugVOListNew);

				healtInfo.setType(type);

				StatusInfo statusInfo = putAnswersForHealAnalyisDisease(questionObj, patName, totalRating, type);

				if (statusInfo != null && !statusInfo.isStatus()) {

					healtInfo.setStatus(false);
					healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_MAINTAIN_HISTORY);
				}

				// Now
				StatusInfo statusInfo1 = storeDataForDiseaseAnalysis(patName, totalRating, type, registerOrNot,
						testName);

				if (statusInfo1 != null && !statusInfo1.isStatus()) {

					healtInfo.setStatus(false);
					healtInfo.setErrMsg(PersonalHealtConstantsIF.Message.COULD_NOT_STORE_DISEASEANALYSIS);
				}

				if ((type != null && !type.isEmpty()) && type.equals(PersonalHealtConstantsIF.Keys.HIGHDISEASE)) {
					healtInfo.setStatus(true);
					healtInfo.setHigh(true);
					return healtInfo;
				}

				healtInfo.setStatus(true);
				healtInfo.setHigh(false);
				return healtInfo;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			healtInfo = new HealtInfo();
			healtInfo.setStatus(false);
			healtInfo.setErrMsg(e.getMessage());
			return healtInfo;
		}
	}

	private List<SuggestionVO> computeSuggestionsDisease(ArrayList<QuestionVO> questionObjList) {

		List<SuggestionVO> sugList = null;
		try {
			sugList = new ArrayList<SuggestionVO>();

			for (QuestionVO questionVO : questionObjList) {
				SuggestionVO suggestionVO = new SuggestionVO();

				int answer = questionVO.getSelectedAnswer();

				String sug = getSugForAns(answer, questionVO);

				if (null == sug) {
					return null;
				} else {
					suggestionVO.setSug(sug);
					sugList.add(suggestionVO);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}

		return sugList;
	}

	private int computeTotalMarksDisease(ArrayList<QuestionVO> questionObj, String testName) {

		if (null == questionObj || questionObj.isEmpty() || questionObj.size() == 0) {

			return -1;

		}

		int totalRating = 0;

		for (QuestionVO questionVO : questionObj) {

			String testNameTemp = questionVO.getTestName();
			if (testNameTemp != null && !testNameTemp.isEmpty() && testNameTemp.equals(testName)) {
				int tempRating = computeMarkForQuestionDisease(questionVO);

				if (tempRating <= 0) {
					tempRating = 0;
				} else {
					totalRating = totalRating + tempRating;
				}
			}
		}

		return totalRating;
	}

	private int computeMarkForQuestionDisease(QuestionVO questionVO) {

		int answer = questionVO.getSelectedAnswer();
		if (answer <= 0) {
			return -1;
		} else {
			if (answer == 1) {
				return questionVO.getRating1();
			}
			if (answer == 2) {
				return questionVO.getRating2();
			}
			if (answer == 3) {
				return questionVO.getRating3();
			}
			if (answer == 4) {
				return questionVO.getRating4();
			}

		}

		return 0;

	}

	private StatusInfo putAnswersForHealAnalyisDisease(ArrayList<QuestionVO> questionObj, String patName,
			int totalRating, String type) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String currentTime = String.valueOf(System.currentTimeMillis());

			for (QuestionVO questionVO : questionObj) {
				AnswerVO answerVO = new AnswerVO();

				answerVO.setPatName(patName);
				answerVO.setTotalRating(totalRating);
				answerVO.setType(type);
				answerVO.setQuestionVO(questionVO);
				answerVO.setCurrentTime(currentTime);
				statusInfo = personalHealtDao.insertAnswerDisease(answerVO);

				if (!statusInfo.isStatus()) {
					return statusInfo;
				}
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}

	}

	private List<SuggestionObj> convertSuggestionFromOneFormToOtherDisease(List<SuggestionVO> sugVOList, String type) {

		List<SuggestionObj> suggestionObjList = new ArrayList<SuggestionObj>();

		if (sugVOList != null && !sugVOList.isEmpty()) {

			for (SuggestionVO suggestionVO : sugVOList) {

				SuggestionObj suggestionObj = new SuggestionObj();
				suggestionObj.setSuggestion(suggestionVO.getSug());
				suggestionObj.setType(type);

				suggestionObjList.add(suggestionObj);

			}
		}

		return suggestionObjList;

	}

	private StatusInfo storeDataForDiseaseAnalysis(String patName, int totalRating, String type, String registerOrNot,
			String testName) {

		StatusInfo statusInfo = new StatusInfo();
		try {

			RegisterUser registerUser = personalHealtDao.retriveRegisterInfoForPatNameDiseaseName(patName,
					registerOrNot);

			StressAnalysis stressAnalysis = new StressAnalysis();
			stressAnalysis.setAgeGroup(registerUser.getAnswer1());
			populateStressForAgeGroup(registerUser, stressAnalysis);
			stressAnalysis.setIncomeGroup(registerUser.getAnswer2());
			populateStressForIncomeGroup(registerUser, stressAnalysis);
			stressAnalysis.setProfExpGroup(registerUser.getAnswer3());
			populateStressForProfExp(registerUser, stressAnalysis);
			stressAnalysis.setRegistered(registerOrNot);
			stressAnalysis.setSex(registerUser.getSex() == 1 ? "1" : "2");
			stressAnalysis.setSexDesc(registerUser.getSex() == 1 ? "MALE" : "FEMALE");
			stressAnalysis.setStressLabel(type);
			stressAnalysis.setUsername(patName);
			stressAnalysis.setTotalRating(totalRating);
			stressAnalysis.setTestName(testName);
			statusInfo = personalHealtDao.insertStressDisease(stressAnalysis);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
		}

		return statusInfo;

	}

	@Override
	public StatusInfo storeScreeningTestDisease(ScreeningTestVO screeningTestVO) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String testName = screeningTestVO.getTestName();

			if (null == testName) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				return statusInfo;
			}

			List<String> testNameList = personalHealtDao.retriveTestNamesDisease();

			if (null == testNameList || testNameList.isEmpty() || testNameList.size() == 0) {

				statusInfo = doInsertionOfScreeningTestDisease(screeningTestVO);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				statusInfo.setStatus(true);
				return statusInfo;

			}

			if (testNameList.contains(testName)) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.TESTNAME_ALREADY_EXISTS);
				return statusInfo;
			}

			statusInfo = doInsertionOfScreeningTestDisease(screeningTestVO);

			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	private StatusInfo doInsertionOfScreeningTestDisease(ScreeningTestVO screeningTestVO) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			String testName = screeningTestVO.getTestName();

			ArrayList<QuestionVO> questionList = screeningTestVO.getQuestionList();

			List<Integer> maxListData = new ArrayList<Integer>();

			if (null == questionList || questionList.isEmpty() || questionList.size() == 0) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				return statusInfo;
			}

			for (QuestionVO questionVO : questionList) {
				statusInfo = personalHealtDao.insertQuestionDisease(questionVO, testName);
				if (!statusInfo.isStatus()) {
					return statusInfo;
				}

				List<Integer> temp = new ArrayList<Integer>();

				temp.add(questionVO.getRating1());
				temp.add(questionVO.getRating2());
				temp.add(questionVO.getRating3());
				temp.add(questionVO.getRating4());

				int max = Collections.max(temp);

				maxListData.add(max);

			}

			// Now Find the Max Value and the ranges

			int maximumOfAll = 0;

			for (Integer data : maxListData) {

				maximumOfAll = maximumOfAll + data;
			}

			int size = maximumOfAll;

			int sizeDataPayload = size;

			int size2 = sizeDataPayload / 4;
			int size3 = sizeDataPayload / 3;
			int size4 = sizeDataPayload / 2;
			int size5 = sizeDataPayload;

			// Range 1

			int R1LL = 0;

			int R1HL = size2 - 1;

			// Range 2
			int R2LL = size2;

			int R2HL = size3 - 1;

			// Range 3
			int R3LL = size3;

			int R3HL = size4 - 1;

			// Range 4
			int R4LL = size4;

			int R4HL = size5 - 1;

			RangeModel rangeModel = new RangeModel();

			rangeModel.setR1LL(R1LL);
			rangeModel.setR1HL(R1HL);
			rangeModel.setR2LL(R2LL);
			rangeModel.setR2HL(R2HL);
			rangeModel.setR3LL(R3LL);
			rangeModel.setR3HL(R3HL);
			rangeModel.setR4HL(R4HL);
			rangeModel.setR4LL(R4LL);
			rangeModel.setTestName(testName);

			statusInfo = personalHealtDao.insertTestDisease(rangeModel);
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}
			statusInfo.setModel(rangeModel);
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}

	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease(String loginId) {
		List<AppointVO> appointInfoList = null;

		try {
			appointInfoList = personalHealtDao.retriveAppointmentsDisease(loginId);
		} catch (Exception e) {
			return appointInfoList;
		}
		return appointInfoList;
	}

	@Override
	public StatusInfo deleteAppointmentsDisease(DeleteAppointInfo appointmentInfo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			ArrayList<Integer> appointmentList = appointmentInfo.getAppointmentList();

			if (null == appointmentList || appointmentList.isEmpty() || appointmentList.size() == 0) {

				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPOINT_LIST_TO_DEL_EMPTY);
				return statusInfo;
			}

			for (Integer appointmentId : appointmentList) {
				statusInfo = personalHealtDao.deleteAppointmentDisease(appointmentId);
				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.DEL_APPID_FAIL);
					return statusInfo;
				}

			}

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

	@Override
	public List<TestInfo> retriveTestNamesDisease() {
		List<TestInfo> pateintList = null;

		try {
			pateintList = personalHealtDao.retriveTestNamesInTestInfoFormatDisease();
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<QuestionVO> retriveQuestionsForTestDisease(String testId) {
		List<QuestionVO> questionVOList = null;
		try {

			questionVOList = personalHealtDao.retriveQuestionsDisease(testId);
			if (null == questionVOList) {
				return null;
			}

		} catch (Exception e) {
			return questionVOList;
		}
		return questionVOList;
	}

	@Override
	public List<PatientNames> retrivePatNamesDisease() {
		List<PatientNames> pateintList = null;

		try {
			pateintList = personalHealtDao.retrivePatientNamesFromAnalysisDisease();
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestIdDisease(String patName, String testId) {
		List<AnalysisData> analysisDataList = null;

		try {
			analysisDataList = personalHealtDao.retriveAnalysisForPatNameAndTestIdDisease(patName, testId);
		} catch (Exception e) {
			return analysisDataList;
		}
		return analysisDataList;
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintNameDisease(String patName) {
		List<TestInfo> pateintList = null;

		try {
			pateintList = personalHealtDao.retriveTestNamesForPateintNameDisease(patName);
		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<ClassifierInfo> viewClassifierDisease() {
		List<ClassifierInfo> classifierInfoList = null;
		try {
			classifierInfoList = personalHealtDao.retriveAllClassifierInfoDisease();
			if (null == classifierInfoList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return classifierInfoList;
	}

	@Override
	public List<CategoryCountVO> viewClassifierCountDisease() {
		List<CategoryCountVO> categoryCountVOList = null;
		try {
			categoryCountVOList = personalHealtDao.viewClassifierCountDisease();
			if (null == categoryCountVOList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return categoryCountVOList;
	}

	@Override
	public List<ClassificationAgeGroup> classifyAgeGroupDisease(String ageGroup) {
		List<ClassificationAgeGroup> classificationAgeGroupList = null;
		try {

			classificationAgeGroupList = personalHealtDao.retriveClassifyByAgeDisease(ageGroup);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationAgeGroupList;
	}

	@Override
	public List<ClassificationGroup> classifySexGroupDisease(String genderGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyBySexDisease(genderGroup);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> classifyExpGroupDisease(String expGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyByExpDisease(expGroup);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> classifyIncomeGroupDisease(String incomeGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {

			classificationGroup = personalHealtDao.retriveClassifyByIncomeGroupDisease(incomeGroup);

		} catch (Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		return classificationGroup;
	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease() {
		List<AppointVO> appointInfoList = null;

		try {
			appointInfoList = personalHealtDao.retriveAppointmentsDisease();
		} catch (Exception e) {
			return appointInfoList;
		}
		return appointInfoList;
	}

	@Override
	public List<AppointVO> retriveDashboardForPatNameDisease(String loginId) {
		List<AppointVO> appVOList = null;
		try {
			appVOList = personalHealtDao.retriveAppointmentsForPatNameDisease(loginId);
			return appVOList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<DiabeticsGraph> retriveStressGraphForUserDisease(String userId) {
		List<DiabeticsGraph> diabeticsGraphVOList = null;
		try {
			diabeticsGraphVOList = new ArrayList<DiabeticsGraph>();

			diabeticsGraphVOList = personalHealtDao.retriveTotalRatingFromPatNameFromAnalysisDisease(userId);

			if (null == diabeticsGraphVOList || diabeticsGraphVOList.isEmpty() || diabeticsGraphVOList.size() == 0) {
				return null;
			}

		} catch (Exception e) {
			return diabeticsGraphVOList;
		}
		return diabeticsGraphVOList;
	}

	@Override
	public StatusInfo doAppointmentReqDisease(String loginId) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();

			if (null == loginId || loginId.isEmpty()) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				return statusInfo;
			}

			List<String> patNames = personalHealtDao.retrivePatNamesFromAppointmentDisease();
			if (null == patNames || patNames.isEmpty()) {
				statusInfo = personalHealtDao.insertAppointmentDisease(loginId, PersonalHealtConstantsIF.Keys.UNAPPROVED_KEY);
			} else if (patNames.contains(loginId)) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.PATNAME_ALREADYEXIST);
				return statusInfo;
			} else {
				statusInfo = personalHealtDao.insertAppointmentDisease(loginId, PersonalHealtConstantsIF.Keys.UNAPPROVED_KEY);
			}
			if (!statusInfo.isStatus()) {
				return statusInfo;
			} else {

				statusInfo.setStatus(true);

				return statusInfo;

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public StatusInfo approveAppointmentsDisease(AppointmentInfo appointmentInfo) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			ArrayList<AppointVO> appointmentList = appointmentInfo.getAppointmentList();

			if (null == appointmentList || appointmentList.isEmpty() || appointmentList.size() == 0) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPOINT_LIST_TO_APPROVE_EMPTY);
				return statusInfo;
			}

			for (AppointVO appointmentVOTemp : appointmentList) {
				appointmentVOTemp.setStatus(PersonalHealtConstantsIF.Keys.APPROVED);
				statusInfo = personalHealtDao.approveAppointmentDisease(appointmentVOTemp);
				if (!statusInfo.isStatus()) {
					statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.APPROVE_APPID_FAIL);
					return statusInfo;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		statusInfo.setStatus(true);
		return statusInfo;
	}

}