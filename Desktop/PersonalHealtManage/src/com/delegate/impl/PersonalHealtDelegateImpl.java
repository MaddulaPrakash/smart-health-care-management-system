package com.delegate.impl;

import com.model.DeleteAppointInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.delegate.inter.PersonalHealtDelegateIF;
import com.model.AnalysisData;
import com.model.AnswerVO;
import com.model.AppointVO;
import com.model.AppointmentInfo;
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
import com.model.NaiveBayesDataSetModelInput;
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
import com.service.inter.PersonalHealtServiceIF;

public class PersonalHealtDelegateImpl implements PersonalHealtDelegateIF {

	@Autowired
	private PersonalHealtServiceIF personalHealtService;

	public PersonalHealtServiceIF getPersonalHealtService() {
		return personalHealtService;
	}

	public void setPersonalHealtService(PersonalHealtServiceIF personalHealtService) {
		this.personalHealtService = personalHealtService;
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		return personalHealtService.checkLogin(registerUser);
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {
		return personalHealtService.doRegistration(registerUser);
	}

	@Override
	public List<UserRoleInfo> retriveUserRoles() {
		return personalHealtService.retriveUserRoles();
	}

	@Override
	public StatusInfo storeScreeningTest(ScreeningTestVO screeningTestVO) {
		return personalHealtService.storeScreeningTest(screeningTestVO);
	}

	@Override
	public List<AppointVO> retriveAppointments() {
		return personalHealtService.retriveAppointments();
	}

	@Override
	public StatusInfo deleteAppointments(DeleteAppointInfo appointmentInfo) {
		return personalHealtService.deleteAppointments(appointmentInfo);
	}

	@Override
	public List<PatientInfo> retrivePatientNames() {
		return personalHealtService.retrivePatientNames();
	}

	@Override
	public List<TestInfo> retriveTestNames() {
		return personalHealtService.retriveTestNames();
	}

	@Override
	public StatusInfo createEligibility(EligibilityModel eligibilityModel) {
		return personalHealtService.createEligibility(eligibilityModel);
	}

	@Override
	public StatusInfo approveAppointments(AppointmentInfo appointmentInfo) {
		return personalHealtService.approveAppointments(appointmentInfo);
	}

	@Override
	public List<QuestionVO> retriveQuestions(String loginId) {
		return personalHealtService.retriveQuestions(loginId);
	}

	@Override
	public HealtInfo processHealt(ArrayList<QuestionVO> questionObj, String patName, String registerOrNot) {
		return personalHealtService.processHealt(questionObj, patName, registerOrNot);
	}

	@Override
	public StatusInfo doAppointmentReq(String loginId) {
		return personalHealtService.doAppointmentReq(loginId);
	}

	@Override
	public StatusInfo retriveDashboard(String loginId) {
		return personalHealtService.retriveDashboard(loginId);
	}

	@Override
	public List<QuestionVO> retriveQuestionsForTest(String testId) {
		return personalHealtService.retriveQuestionsForTest(testId);
	}

	@Override
	public List<DiabeticsGraph> retriveDiabeticsGraph() {
		return personalHealtService.retriveDiabeticsGraph();
	}

	@Override
	public StatusInfo createEligibilityForAll(EligibilityModel eligibilityModel) {
		return personalHealtService.createEligibilityForAll(eligibilityModel);
	}

	@Override
	public List<DoctorInfo> retriveDoctorList() {
		return personalHealtService.retriveDoctorList();
	}

	@Override
	public List<AppointVO> retriveAppointments(String doctorName) {
		return personalHealtService.retriveAppointments(doctorName);
	}

	@Override
	public List<AppointVO> retriveDashboardForPatName(String patName) {
		return personalHealtService.retriveDashboardForPatName(patName);
	}

	@Override
	public List<AnswerVO> retriveAllGeneralQuestions(String patName) {
		return personalHealtService.retriveAllGeneralQuestions(patName);
	}

	@Override
	public List<QuestionVO> retriveQuestionsPagination(String loginId, int start, int start2, int limit) {
		return personalHealtService.retriveQuestionsPagination(loginId, start, start2, limit);
	}

	@Override
	public List<AnswerVO> retriveAllGeneralQuestionsForUser(String userId) {
		return personalHealtService.retriveAllGeneralQuestionsForUser(userId);
	}

	@Override
	public List<DiabeticsGraph> retriveStressGraphForUser(String userId) {
		return personalHealtService.retriveStressGraphForUser(userId);
	}

	@Override
	public StatusInfo savePuzzle(PuzzleModel puzzleVO) {
		return personalHealtService.savePuzzle(puzzleVO);
	}

	@Override
	public List<PuzzleVO> retrivePuzzle() {
		return personalHealtService.retrivePuzzle();
	}

	@Override
	public List<PuzzleVO> retrivePuzzleForUser(String username) {
		return personalHealtService.retrivePuzzleForUser(username);
	}

	@Override
	public List<ClassificationAgeGroup> classifyAgeGroup(String ageGroup) {
		return personalHealtService.classifyAgeGroup(ageGroup);
	}

	@Override
	public List<ClassificationGroup> classifyIncomeGroup(String group) {
		return personalHealtService.classifyIncomeGroup(group);
	}

	@Override
	public List<ClassificationGroup> classifyExpGroup(String group) {
		return personalHealtService.classifyExpGroup(group);
	}

	@Override
	public List<ClassificationGroup> classifySexGroup(String group) {
		return personalHealtService.classifySexGroup(group);
	}

	@Override
	public StatusInfo readDataSets() {
		return personalHealtService.readDataSets();
	}

	@Override
	public GlobalNaiveBayesOutput performNaiveBayesCalculation(
			NaiveBayesDataSetModelInput naiveBayesDataSetModelInput) {
		return personalHealtService.performNaiveBayesCalculation(naiveBayesDataSetModelInput);
	}

	@Override
	public List<ProbabilityInfo> viewProbability() {
		return personalHealtService.viewProbability();
	}

	@Override
	public StatusInfo doContigency() {
		return personalHealtService.doContigency();
	}

	@Override
	public List<ContigencyInfo> viewContigency() {
		return personalHealtService.viewContigency();
	}

	@Override
	public List<EnhanceContigency> viewEnhanceContigency() {
		return personalHealtService.viewEnhanceContigency();
	}

	@Override
	public StatusInfo doEnhanceContigency() {
		return personalHealtService.doEnhanceContigency();
	}

	@Override
	public StatusInfo classifyType() {
		return personalHealtService.classifyType();
	}

	@Override
	public List<ClassifierInfo> viewClassifier() {
		return personalHealtService.viewClassifier();
	}

	@Override
	public List<CategoryCountVO> viewClassifierCount() {
		return personalHealtService.viewClassifierCount();
	}

	@Override
	public List<PatientNames> retrivePatNames() {
		return personalHealtService.retrivePatNames();
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintName(String patName) {
		return personalHealtService.retriveTestNamesForPateintName(patName);
	}

	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestId(String patName, String testId) {
		return personalHealtService.retriveAnalysisForPatNameAndTestId(patName, testId);
	}

	@Override
	public List<AnswerVO> retriveAllDiseaseGeneralQuestionsForUser(String userId) {
		return personalHealtService.retriveAllDiseaseGeneralQuestionsForUser(userId);
	}

	@Override
	public HealtInfo processHealtDisease(ArrayList<QuestionVO> questionObj, String patName, String status) {
		return personalHealtService.processHealtDisease(questionObj, patName, status);
	}

	@Override
	public StatusInfo storeScreeningTestDisease(ScreeningTestVO screeningTestVO) {
		return personalHealtService.storeScreeningTestDisease(screeningTestVO);
	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease(String loginId) {
		return personalHealtService.retriveAppointmentsDisease(loginId);
	}

	@Override
	public StatusInfo deleteAppointmentsDisease(DeleteAppointInfo appointmentInfo) {
		return personalHealtService.deleteAppointmentsDisease(appointmentInfo);
	}

	@Override
	public List<TestInfo> retriveTestNamesDisease() {
		return personalHealtService.retriveTestNamesDisease();
	}

	@Override
	public List<QuestionVO> retriveQuestionsForTestDisease(String testId) {
		return personalHealtService.retriveQuestionsForTestDisease(testId);
	}

	@Override
	public List<PatientNames> retrivePatNamesDisease() {
		return personalHealtService.retrivePatNamesDisease();
	}

	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestIdDisease(String patName, String testId) {
		return personalHealtService.retriveAnalysisForPatNameAndTestIdDisease(patName,testId);
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintNameDisease(String patName) {
		return personalHealtService.retriveTestNamesForPateintNameDisease(patName);
	}

	@Override
	public List<ClassifierInfo> viewClassifierDisease() {
		return personalHealtService.viewClassifierDisease();
	}

	@Override
	public List<CategoryCountVO> viewClassifierCountDisease() {
		return personalHealtService.viewClassifierCountDisease();
	}

	@Override
	public List<ClassificationAgeGroup> classifyAgeGroupDisease(String ageGroup) {
		return personalHealtService.classifyAgeGroupDisease(ageGroup);
	}

	@Override
	public List<ClassificationGroup> classifySexGroupDisease(String genderGroup) {
		return personalHealtService.classifySexGroupDisease(genderGroup);
	}

	@Override
	public List<ClassificationGroup> classifyExpGroupDisease(String expGroup) {
		return personalHealtService.classifyExpGroupDisease(expGroup);
	}

	@Override
	public List<ClassificationGroup> classifyIncomeGroupDisease(String incomeGroup) {
		return personalHealtService.classifyIncomeGroupDisease(incomeGroup);
	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease() {
		return personalHealtService.retriveAppointmentsDisease();
	}

	@Override
	public List<AppointVO> retriveDashboardForPatNameDisease(String loginId) {
		return personalHealtService.retriveDashboardForPatNameDisease(loginId);
	}

	@Override
	public List<DiabeticsGraph> retriveStressGraphForUserDisease(String userId) {
		return personalHealtService.retriveStressGraphForUserDisease(userId);
	}

	@Override
	public StatusInfo doAppointmentReqDisease(String loginId) {
		return personalHealtService.doAppointmentReqDisease(loginId);
	}

	@Override
	public StatusInfo approveAppointmentsDisease(AppointmentInfo appointmentInfo) {
		return personalHealtService.approveAppointmentsDisease(appointmentInfo);
	}

}
