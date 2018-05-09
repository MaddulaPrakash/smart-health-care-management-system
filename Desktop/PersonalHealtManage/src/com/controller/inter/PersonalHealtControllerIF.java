package com.controller.inter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.util.Login;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.AJAXResponse;
import com.model.AnswerUIVO;
import com.model.AnswerVO;
import com.model.AppointmentInfo;
import com.model.DeleteAppointInfo;
import com.model.DoctorInfo;
import com.model.EligibilityModel;
import com.model.LoginModel;
import com.model.NaiveBayesInput;
import com.model.PatientInfo;
import com.model.PuzzleModel;
import com.model.QuestionVO;
import com.model.RegisterUser;
import com.model.ScreeningTestVO;
import com.model.TestInfo;
import com.model.UserRoleInfo;

public interface PersonalHealtControllerIF {

	public ModelAndView doLogin(HttpServletRequest request, RegisterUser registerUser);

	public ModelAndView doLogout(HttpServletRequest request);

	public ModelAndView registerUserInfo(RegisterUser registerUser, HttpServletRequest request);

	public @ResponseBody AJAXResponse createUser(@RequestBody RegisterUser registerUser, HttpServletRequest request);

	public @ResponseBody List<UserRoleInfo> retriveUserRoles(HttpServletRequest request);

	public @ResponseBody AJAXResponse createScreeningTest(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ScreeningTestVO screeningTestVO);

	public @ResponseBody AJAXResponse retriveAppointments(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse deleteAppointments(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DeleteAppointInfo appointmentInfo);

	public @ResponseBody List<TestInfo> retriveTestNames(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody List<PatientInfo> retrivePatientNames(HttpServletRequest request,
			HttpServletResponse response);

	public @ResponseBody AJAXResponse createEligibbility(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EligibilityModel eligibilityModel);

	public @ResponseBody AJAXResponse approveAppointments(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentInfo appointmentInfo);

	public @ResponseBody AJAXResponse viewScreeningTest(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse processPateintAnswers(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AnswerUIVO questionObj);

	public @ResponseBody AJAXResponse requestAppoitment(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse dashboard(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse viewSpecificScreeningTest(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String testId);

	public @ResponseBody AJAXResponse generateDiabeticsInfo(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse createEligibbilityForAll(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EligibilityModel eligibilityModel);

	public @ResponseBody List<DoctorInfo> retriveDoctors(HttpServletRequest request, HttpServletResponse response);

	public @ResponseBody AJAXResponse retriveAppointmentsForDoctor(HttpServletRequest request,
			HttpServletResponse response);

	ModelAndView submitAnswer(AnswerVO questionVO, HttpServletRequest request);

	AJAXResponse viewScreeningTestPagination(HttpServletRequest request, HttpServletResponse response, int start,
			int limit, int page);

	ModelAndView viewScreenTestSingleQPerPage(HttpServletRequest request, HttpServletResponse response);

	ModelAndView initialUnregisterQuestions(RegisterUser registerUser, HttpServletRequest request);

	ModelAndView obtainTestForUserFromSession(HttpServletRequest request);

	ModelAndView submitAnswerUser(AnswerVO answerVO, HttpServletRequest request);

	ModelAndView placeAppointment(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse trackHistory(HttpServletRequest request, HttpServletResponse response);

	ModelAndView savePuzzleQues(PuzzleModel puzzleVO, HttpServletRequest request);

	AJAXResponse viewPuzzleTest(HttpServletRequest request, HttpServletResponse response);

	ModelAndView findPuzzleForUser(HttpServletRequest request);

	ModelAndView processPuzzleForUser(HttpServletRequest request);

	AJAXResponse classifyAge1(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge2(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge3(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge4(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome1(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome2(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome3(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome4(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyExp1(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyExp2(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyExp3(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyExp4(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifySex1(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifySex2(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse readDataSets();

	AJAXResponse peformNavBayesProbability(NaiveBayesInput navBayesInput, HttpServletRequest request);

	AJAXResponse viewProbability(HttpServletRequest request);

	ModelAndView doContigency();

	AJAXResponse viewContigency(HttpServletRequest request);

	AJAXResponse viewEnhanceContigency(HttpServletRequest request);

	ModelAndView doEnhanceContigency();

	ModelAndView classifyType(HttpServletRequest request);

	AJAXResponse viewClassifier(HttpServletRequest request);

	AJAXResponse viewNoOfClassifier(HttpServletRequest request);

	AJAXResponse viewPatNamesTest(HttpServletRequest request, HttpServletResponse response);

	List<TestInfo> retriveTestNamesForPateintName(HttpServletRequest request, HttpServletResponse response,
			String patName);

	AJAXResponse viewanalysisForPatNameAndTestId(HttpServletRequest request, HttpServletResponse response,
			String testId, String patName);

	ModelAndView obtainDiseaseTestForUserFromSession(HttpServletRequest request);

	ModelAndView submitAnswerUserDisease(AnswerVO answerVO, HttpServletRequest request);

	AJAXResponse createScreeningTestDisease(HttpServletRequest request, HttpServletResponse response,
			ScreeningTestVO screeningTestVO);

	AJAXResponse retriveAppointmentsForDoctorDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse deleteAppointmentsDisease(HttpServletRequest request, HttpServletResponse response,
			DeleteAppointInfo appointmentInfo);

	List<TestInfo> retriveTestNamesDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse viewSpecificScreeningTestDisease(HttpServletRequest request, HttpServletResponse response,
			String testId);

	AJAXResponse viewPatNamesTestDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse viewanalysisForPatNameAndTestIdDisease(HttpServletRequest request, HttpServletResponse response,
			String testId, String patName);

	List<TestInfo> viewspecifictestForPatNameDisease(HttpServletRequest request, HttpServletResponse response,
			String patName);

	AJAXResponse viewClassifierDisease(HttpServletRequest request);

	AJAXResponse viewNoOfClassifierDisease(HttpServletRequest request);

	AJAXResponse classifyAge1Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge2Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge3Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyAge4Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifySex2Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifySex1Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome1Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome2Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome3Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse classifyIncome4Disease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse retriveAppointmentsDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse dashboardDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse trackHistoryDisease(HttpServletRequest request, HttpServletResponse response);

	ModelAndView placeAppointmentDisease(HttpServletRequest request, HttpServletResponse response);

	AJAXResponse approveAppointmentsDisease(HttpServletRequest request, HttpServletResponse response,
			AppointmentInfo appointmentInfo);

}
