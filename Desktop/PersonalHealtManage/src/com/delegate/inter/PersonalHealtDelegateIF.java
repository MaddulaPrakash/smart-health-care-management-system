package com.delegate.inter;

import java.util.ArrayList;
import java.util.List;

import com.model.AnalysisData;
import com.model.AnswerVO;
import com.model.AppointVO;
import com.model.AppointmentInfo;
import com.model.CategoryCountVO;
import com.model.ClassificationAgeGroup;
import com.model.ClassificationGroup;
import com.model.ClassifierInfo;
import com.model.ContigencyInfo;
import com.model.DeleteAppointInfo;
import com.model.DiabeticsGraph;
import com.model.EnhanceContigency;
import com.model.GlobalNaiveBayesOutput;
import com.model.HealtInfo;
import com.model.DoctorInfo;
import com.model.EligibilityModel;
import com.model.NaiveBayesDataSetModelInput;
import com.model.NaiveBayesOutput;
import com.model.PatientInfo;
import com.model.PatientNames;
import com.model.ProbabilityInfo;
import com.model.PuzzleModel;
import com.model.PuzzleVO;
import com.model.QuestionVO;
import com.model.RegisterUser;
import com.model.ScreeningTestVO;
import com.model.StatusInfo;
import com.model.TestInfo;
import com.model.UserRoleInfo;

public interface PersonalHealtDelegateIF {

	public StatusInfo checkLogin(RegisterUser registerUser);

	public StatusInfo doRegistration(RegisterUser registerUser);

	public List<UserRoleInfo> retriveUserRoles();

	public StatusInfo storeScreeningTest(ScreeningTestVO screeningTestVO);

	public List<AppointVO> retriveAppointments();

	public List<AppointVO> retriveAppointments(String doctorName);

	public StatusInfo deleteAppointments(DeleteAppointInfo appointmentInfo);

	public List<PatientInfo> retrivePatientNames();

	public List<TestInfo> retriveTestNames();

	public StatusInfo createEligibility(EligibilityModel eligibilityModel);

	public StatusInfo approveAppointments(AppointmentInfo appointmentInfo);

	public List<QuestionVO> retriveQuestions(String loginId);

	public HealtInfo processHealt( 
			ArrayList<QuestionVO> questionObj, String patName, String registerOrNot);

	public StatusInfo doAppointmentReq(String loginId);

	public StatusInfo retriveDashboard(String loginId);

	public List<QuestionVO> retriveQuestionsForTest(String testId);

	public List<DiabeticsGraph> retriveDiabeticsGraph();

	public StatusInfo createEligibilityForAll(EligibilityModel eligibilityModel);

	public List<DoctorInfo> retriveDoctorList();

	public List<AppointVO> retriveDashboardForPatName(String patName);

	List<AnswerVO> retriveAllGeneralQuestions(String patName);

	public List<QuestionVO> retriveQuestionsPagination(String loginId,
			int start, int start2, int limit);

	public List<AnswerVO> retriveAllGeneralQuestionsForUser(String userId);

	public List<DiabeticsGraph> retriveStressGraphForUser(String userId);

	public StatusInfo savePuzzle(PuzzleModel puzzleVO);

	public List<PuzzleVO> retrivePuzzle();

	public List<PuzzleVO> retrivePuzzleForUser(String username);

	public List<ClassificationAgeGroup> classifyAgeGroup(String ageGroup);

	public List<ClassificationGroup> classifyIncomeGroup(String string);

	public List<ClassificationGroup> classifyExpGroup(String string);

	public List<ClassificationGroup> classifySexGroup(String string);

	public StatusInfo readDataSets();

	public GlobalNaiveBayesOutput performNaiveBayesCalculation(
			NaiveBayesDataSetModelInput naiveBayesDataSetModelInput);

	public List<ProbabilityInfo> viewProbability();

	public StatusInfo doContigency();

	public List<ContigencyInfo> viewContigency();

	public List<EnhanceContigency> viewEnhanceContigency();

	public StatusInfo doEnhanceContigency();

	public StatusInfo classifyType();

	public List<ClassifierInfo> viewClassifier();

	public List<CategoryCountVO> viewClassifierCount();

	public List<PatientNames> retrivePatNames();  

	public List<TestInfo> retriveTestNamesForPateintName(String patName);

	public List<AnalysisData> retriveAnalysisForPatNameAndTestId(String patName, String testId);

	public List<AnswerVO> retriveAllDiseaseGeneralQuestionsForUser(String userId);

	public HealtInfo processHealtDisease(ArrayList<QuestionVO> questionObj, String patName, String string);

	public StatusInfo storeScreeningTestDisease(ScreeningTestVO screeningTestVO);

	public List<AppointVO> retriveAppointmentsDisease(String loginId); 

	public StatusInfo deleteAppointmentsDisease(DeleteAppointInfo appointmentInfo);

	public List<TestInfo> retriveTestNamesDisease(); 

	public List<QuestionVO> retriveQuestionsForTestDisease(String testId);
 
	public List<PatientNames> retrivePatNamesDisease(); 

	public List<AnalysisData> retriveAnalysisForPatNameAndTestIdDisease(String patName, String testId);

	public List<TestInfo> retriveTestNamesForPateintNameDisease(String patName);

	public List<ClassifierInfo> viewClassifierDisease(); 
 
	public List<CategoryCountVO> viewClassifierCountDisease();

	public List<ClassificationAgeGroup> classifyAgeGroupDisease(String string);

	public List<ClassificationGroup> classifySexGroupDisease(String string);

	public List<ClassificationGroup> classifyExpGroupDisease(String string);

	public List<ClassificationGroup> classifyIncomeGroupDisease(String string);

	public List<AppointVO> retriveAppointmentsDisease();

	public List<AppointVO> retriveDashboardForPatNameDisease(String loginId);

	public List<DiabeticsGraph> retriveStressGraphForUserDisease(String userId);

	public StatusInfo doAppointmentReqDisease(String loginId);

	public StatusInfo approveAppointmentsDisease(AppointmentInfo appointmentInfo);         
    
}  
 