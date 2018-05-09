package com.dao.inter;

import java.util.List;

import com.model.AnalysisData;
import com.model.AnswerVO;
import com.model.AppointVO;
import com.model.AttributeInformation;
import com.model.CategoryCountVO;
import com.model.ClassificationAgeGroup;
import com.model.ClassificationGroup;
import com.model.ClassifierInfo;
import com.model.ContigencyInfo;
import com.model.DiabeticsGraph;
import com.model.DoctorInfo;
import com.model.EligibilityModel;
import com.model.EnhanceContigency;
import com.model.PartialClassifierInfo;
import com.model.PatientInfo;
import com.model.PatientNames;
import com.model.ProbabilityInfo;
import com.model.PuzzleModel;
import com.model.PuzzleVO;
import com.model.QuestionVO;
import com.model.RangeModel;
import com.model.RegisterUser;
import com.model.StatusInfo;
import com.model.StressAnalysis;
import com.model.SuggestionObj;
import com.model.TestInfo;
import com.model.UserRoleInfo;

public interface PersonalHealtDAOIF {

	public String retrivePassword(String userId);

	public int retriveLoginType(String userId);

	public List<String> retriveUserIds();

	public StatusInfo insertLogin(RegisterUser registerUser);

	public List<UserRoleInfo> retriveUserRoleInfo();

	public StatusInfo insertTest(RangeModel rangeModel);

	public StatusInfo insertQuestion(QuestionVO questionVO, String testName);

	public List<String> retriveTestNames();

	public List<AppointVO> retriveAppointments();

	public StatusInfo deleteAppointment(Integer appointmentId);

	public List<PatientInfo> retrivePatientNames();

	public List<TestInfo> retriveTestNamesInTestInfoFormat();

	public List<String> retrivePatientNamesFromEligibility();

	public StatusInfo insertEligibility(EligibilityModel eligibilityModel);

	public StatusInfo updateEligibility(EligibilityModel eligibilityModel);

	public String retriveTestNameFromEligibility(String loginId);

	public List<QuestionVO> retriveQuestions(String testName);

	public RangeModel retriveRangeModel(String testName);

	public StatusInfo insertAnswer(AnswerVO answerVO);

	public StatusInfo insertAppointment(String loginId, int unapprovedKey);

	public StatusInfo retriveAppointStatus(String loginId);

	public List<String> retriveUniqueTestNames();

	public StatusInfo retriveTotalRatingFromPatName(String patName,
			String testName);

	public List<String> retriveUserIdsForPateint();

	public StatusInfo approveAppointment(Integer appointmentId, String date,
			String fromTime, String toTime);

	public StatusInfo approveAppointment(AppointVO appointmentVOTemp);

	public List<DoctorInfo> retriveDoctorList();

	public List<String> retrivePatNamesFromAppointment();

	public List<AppointVO> retriveAppointments(String doctorName);

	public List<AppointVO> retriveAppointmentsForPatName(String patName);

	public List<SuggestionObj> findSuggestionsByType(String type);

	public List<String> retriveUniqueTestNamesFromQuestionsForUserId(
			String userId);

	public List<String> retriveUniqueCurrentTimeFromAnswer(String userId);

	public StatusInfo savePuzzle(PuzzleModel puzzleVO);

	public List<PuzzleVO> retrivePuzzle();

	public String findAgeForUserNameFromLogin(String username);

	public List<PuzzleVO> retrivePuzzleForAgeGroup(String ageGroup);

	public RegisterUser retriveRegisterInfoForPatName(String patName,
			String registerOrNot);

	public StatusInfo insertStress(StressAnalysis stressAnalysis);

	public List<ClassificationAgeGroup> retriveClassifyByAge(String ageGroup);

	public List<ClassificationGroup> retriveClassifyByIncome(String group);

	public List<ClassificationGroup> retriveClassifyByExp(String group);

	public List<ClassificationGroup> retriveClassifyBySex(String group);

	public List<DiabeticsGraph> retriveTotalRatingFromPatNameFromAnalysis(
			String userId);

	public List<Integer> retriveUniqueRowIds();

	public List<AttributeInformation> reytiveAttributeInfoForRowId(
			Integer rowIdTemp);

	public List<String> retriveUniqueAttributeList();

	public List<Double> retriveListAttributeInfoWithStatus(String string,
			String attributeName);

	StatusInfo insertProbability(ProbabilityInfo probabilityInfo);

	List<String> retriveUniqueAttributeListForType(String diseaseType);

	List<Double> retriveListAttributeInfoWithStatusForType(String outputFactor,
			String attributeName, String diseaseType);

	StatusInfo deleteAllProbabilitiesForUserId(String userId);

	public List<ProbabilityInfo> retriveAllProbability();

	public StatusInfo deleteAllContigency();

	public ContigencyInfo findTotalPosOthersAnsTotalNegativeOthers(
			String username, String catName);

	public StatusInfo insertContigency(ContigencyInfo contigencyInfo);

	public List<ContigencyInfo> retriveAllContigency();

	public List<EnhanceContigency> retriveAllEnhanceContigency();

	public StatusInfo deleteAllEnhanceContigency();

	public StatusInfo insertEhanceContigency(EnhanceContigency enhanceContigency);

	public StatusInfo deleteAllClassifier();

	public List<String> retriveDistinctUsersIdsFromEnhanceContigency();

	public List<PartialClassifierInfo> retriveClassifierByRank(String userId);

	public StatusInfo insertClassfierInfo(ClassifierInfo ci);

	public List<ClassifierInfo> retriveAllClassifierInfo();

	public List<CategoryCountVO> viewClassifierCount();

	public List<PatientNames> retrivePatientNamesFromAnalysis();

	List<String> retriveOnlyPatientNamesFromAnalysis();

	List<String> retriveOnlyUserNamesFromLogin(); 

	public List<TestInfo> retriveTestNamesForPateintName(String patName);

	public List<AnalysisData> retriveAnalysisForPatNameAndTestId(String patName, String testId);

	public List<String> retriveUniqueTestNamesDisease();

	public List<String> retriveUniqueTestNamesFromQuestionsForUserIdDisease(String userId);

	public List<QuestionVO> retriveQuestionsDisease(String testNameObj);

	public RangeModel retriveRangeModelDisease(String testName);
 
	public RegisterUser retriveRegisterInfoForPatNameDiseaseName(String patName, String registerOrNot);

	public List<String> retriveTestNamesDisease(); 

	public StatusInfo insertQuestionDisease(QuestionVO questionVO, String testName);

	public StatusInfo insertTestDisease(RangeModel rangeModel);

	public List<AppointVO> retriveAppointmentsDisease(String loginId);

	public StatusInfo deleteAppointmentDisease(Integer appointmentId);

	public List<TestInfo> retriveTestNamesInTestInfoFormatDisease();
 
	public List<PatientNames> retrivePatientNamesFromAnalysisDisease();

	public List<AnalysisData> retriveAnalysisForPatNameAndTestIdDisease(String patName, String testId);

	public List<TestInfo> retriveTestNamesForPateintNameDisease(String patName);

	public List<ClassifierInfo> retriveAllClassifierInfoDisease();

	public List<CategoryCountVO> viewClassifierCountDisease();  

	public List<ClassificationAgeGroup> retriveClassifyByAgeDisease(String ageGroup);

	public List<ClassificationGroup> retriveClassifyBySexDisease(String genderGroup);

	public List<ClassificationGroup> retriveClassifyByExpDisease(String expGroup);

	public List<ClassificationGroup> retriveClassifyByIncomeGroupDisease(String incomeGroup);

	public List<AppointVO> retriveAppointmentsDisease();  

	public List<AppointVO> retriveAppointmentsForPatNameDisease(String loginId);

	public List<DiabeticsGraph> retriveTotalRatingFromPatNameFromAnalysisDisease(String userId);

	public List<String> retrivePatNamesFromAppointmentDisease();

	public StatusInfo insertAppointmentDisease(String loginId, int unapprovedKey);

	public StatusInfo insertAnswerDisease(AnswerVO answerVO); 

	public StatusInfo insertStressDisease(StressAnalysis stressAnalysis);

	public StatusInfo approveAppointmentDisease(AppointVO appointmentVOTemp);

	List<String> retriveUniqueTestNamesDisease1();       

}     
