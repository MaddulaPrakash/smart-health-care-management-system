package com.controller.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model.RegisterUser;
import com.constants.PersonalHealtConstantsIF;
import com.controller.inter.PersonalHealtControllerIF;
import com.delegate.inter.PersonalHealtDelegateIF;
import com.model.AJAXResponse;
import com.model.AnalysisData;
import com.model.AnswerPuzzle;
import com.model.AnswerUIVO;
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
import com.model.GlobalRowInfo;
import com.model.Information;
import com.model.Message;
import com.model.NaiveBayesDataSetModelInput;
import com.model.NaiveBayesInput;
import com.model.PatientInfo;
import com.model.PatientNames;
import com.model.ProbabilityInfo;
import com.model.PuzzleModel;
import com.model.PuzzleVO;
import com.model.QuestionVO;
import com.model.RangeModel;
import com.model.RegisterVerifyMsgs;
import com.model.ScreeningTestVO;
import com.model.StatusInfo;
import com.model.TestInfo;
import com.model.UserRoleInfo;
import com.model.DeleteAppointInfo;
import com.util.helper.StressHelperUtil;

@Controller
public class PersonalHealtControllerImpl implements PersonalHealtControllerIF {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PersonalHealtDelegateIF personalHealtDelegate;

	public PersonalHealtDelegateIF getPersonalHealtDelegate() {
		return personalHealtDelegate;
	}

	public void setPersonalHealtDelegate(PersonalHealtDelegateIF personalHealtDelegate) {
		this.personalHealtDelegate = personalHealtDelegate;
	}

	@Override
	@RequestMapping(value = "/login.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doLogin(HttpServletRequest request, @ModelAttribute RegisterUser registerUser) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String userId = registerUser.getUserId();

			if (null == userId || userId.isEmpty() || userId.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.USERID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.USERID_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_LOGIN_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String password = registerUser.getUserPassword();

			if (null == password || password.isEmpty() || password.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.PASSWORD_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.PASSWORD_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_LOGIN_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

			StatusInfo statusInfo = personalHealtDelegate.checkLogin(registerUser);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_LOGIN_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (statusInfo.isStatus()) {

				HttpSession session = request.getSession(true);

				session.setAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION, registerUser.getUserId());
				session.setAttribute(PersonalHealtConstantsIF.Keys.LOGINTYPE_SESSION, statusInfo.getType());

				if (statusInfo.getType() == PersonalHealtConstantsIF.Keys.ADMIN_TYPE) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(true);
					Message msg = new Message(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_WELCOMEPAGE,
							PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
				} else if (statusInfo.getType() == PersonalHealtConstantsIF.Keys.DOCTOR_TYPE) {
					return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_DOCTOR_WELCOMEPAGE,
							PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
				} else if (statusInfo.getType() == PersonalHealtConstantsIF.Keys.PC_TYPE) {
					return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_PC_WELCOMEPAGE,
							PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
				} else if (statusInfo.getType() == PersonalHealtConstantsIF.Keys.CUSTOMER_TYPE) {
					return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_P_WELCOMEPAGE,
							PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
				} else {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(true);
					Message msg = new Message(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_CUSTOMER_WELCOMEPAGE,
							PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_LOGIN_INPUT, PersonalHealtConstantsIF.Keys.OBJ,
					ajaxResponse);
		}
		return null;

	}

	@Override
	@RequestMapping(value = "/logout.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doLogout(HttpServletRequest request) {
		try {

			HttpSession session = request.getSession(false);

			cleanupQuestionsFromSession(session);
			cleanUpUser(session);

			session.invalidate();
			return new ModelAndView(PersonalHealtConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, null);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return new ModelAndView(PersonalHealtConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, null);
		}
	}

	@Override
	@RequestMapping(value = "/registerUser.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView registerUserInfo(@ModelAttribute RegisterUser registerUser, HttpServletRequest request) {
		RegisterVerifyMsgs registerVerifyMsgs = new RegisterVerifyMsgs();

		try {

			// Adding default login type as customer
			registerUser.setLoginType(PersonalHealtConstantsIF.Keys.CUSTOMER_TYPE);

			String firstName = registerUser.getFirstName();
			if (null == firstName || firstName.isEmpty() || firstName.trim().length() == 0) {

				registerVerifyMsgs.setFirstNameErrMsg(PersonalHealtConstantsIF.Message.FIRSTNAME_EMPTY);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}
			String lastName = registerUser.getLastName();
			if (null == lastName || lastName.isEmpty() || lastName.trim().length() == 0) {

				registerVerifyMsgs.setLastNameErrMsg(PersonalHealtConstantsIF.Message.LASTNAME_EMPTY);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String userId = registerUser.getUserId();

			if (null == userId || userId.isEmpty() || userId.trim().length() == 0) {

				registerVerifyMsgs.setUserNameErrMsg(PersonalHealtConstantsIF.Message.USERID_EMPTY);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String email = registerUser.getEmailId();

			if (null == email || email.isEmpty() || email.trim().length() == 0) {

				registerVerifyMsgs.setEmailErrMsg(PersonalHealtConstantsIF.Message.EMAIL_EMPTY);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String password = registerUser.getUserPassword();

			if (null == password || password.isEmpty() || password.trim().length() == 0) {

				registerVerifyMsgs.setPasswordErrMsg(PersonalHealtConstantsIF.Message.PASSWORD_EMPTY);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			String answer1 = registerUser.getAnswer1();

			if (null == answer1 || answer1.isEmpty() || answer1.trim().length() == 0) {
				registerVerifyMsgs.setHeartAttackErrMsg(PersonalHealtConstantsIF.Message.AGEGROUP_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer2 = registerUser.getAnswer2();

			if (null == answer2 || answer2.isEmpty() || answer2.trim().length() == 0) {
				registerVerifyMsgs.setDiabeticErrMsg(PersonalHealtConstantsIF.Message.INCOME_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer3 = registerUser.getAnswer3();

			if (null == answer3 || answer3.isEmpty() || answer3.trim().length() == 0) {
				registerVerifyMsgs.setDiabeticErrMsg(PersonalHealtConstantsIF.Message.PROFEXP_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer4 = registerUser.getAnswer4();

			if (null == answer4 || answer4.isEmpty() || answer4.trim().length() == 0) {
				registerVerifyMsgs.setObesityErrMsg(PersonalHealtConstantsIF.Message.GENDER_ERR_MSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			// This are just default unused values
			registerUser.setSex(1);

			String dob = "DEFAULT";
			registerUser.setDob(dob);

			int age = 1000;
			registerUser.setAge(age);

			StatusInfo statusInfo = personalHealtDelegate.doRegistration(registerUser);

			if (!statusInfo.isStatus()) {

				registerVerifyMsgs.setSeverMessage(statusInfo.getErrMsg());

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			registerVerifyMsgs.setSucessMsg(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
					PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			registerVerifyMsgs.setSeverMessage(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
					PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
		}

	}

	@Override
	@RequestMapping(value = "/viewscreeningtestuser.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView obtainTestForUserFromSession(HttpServletRequest request) {

		ModelAndView modelAndView = null;

		try {
			AJAXResponse ajaxResponse = generateAJAXResponseForAnswersVOUser(request);
			if (ajaxResponse.isStatus()) {
				// The next Step to Load questions
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTIONUSER_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}
		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("Error Message is");
			System.out.println(e.getMessage());

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, PersonalHealtConstantsIF.Message.ADMIN_CONTACT);

		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
				PersonalHealtConstantsIF.Message.ADMIN_CONTACT);
	}

	@Override
	@RequestMapping(value = "/registerUserInitial.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView initialUnregisterQuestions(@ModelAttribute RegisterUser registerUser,
			HttpServletRequest request) {
		RegisterVerifyMsgs registerVerifyMsgs = new RegisterVerifyMsgs();

		try {

			// Adding default login type as customer
			registerUser.setLoginType(PersonalHealtConstantsIF.Keys.CUSTOMER_TYPE);

			String firstName = "DEFAULT";
			registerUser.setFirstName(firstName);
			String lastName = "DEFAULT";
			registerUser.setLastName(lastName);
			String userId = request.getRemoteAddr() + System.currentTimeMillis();
			registerUser.setUserId(userId);
			String email = "DEFAULT";
			registerUser.setEmailId(email);
			String password = "DEFAULT";
			registerUser.setUserPassword(password);
			String answer1 = registerUser.getAnswer1();

			if (null == answer1 || answer1.isEmpty() || answer1.trim().length() == 0) {
				registerVerifyMsgs.setHeartAttackErrMsg(PersonalHealtConstantsIF.Message.AGEGROUP_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer2 = registerUser.getAnswer2();

			if (null == answer2 || answer2.isEmpty() || answer2.trim().length() == 0) {
				registerVerifyMsgs.setDiabeticErrMsg(PersonalHealtConstantsIF.Message.INCOME_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer3 = registerUser.getAnswer3();

			if (null == answer3 || answer3.isEmpty() || answer3.trim().length() == 0) {
				registerVerifyMsgs.setDiabeticErrMsg(PersonalHealtConstantsIF.Message.PROFEXP_ERRORMSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String answer4 = "DEFAULT";
			registerUser.setAnswer4(answer4);

			int age = 1000;
			registerUser.setAge(age);
			int sex = registerUser.getSex();

			if (sex <= 0) {

				registerVerifyMsgs.setAgeErrMsg(PersonalHealtConstantsIF.Message.SEX_ERR_MSG);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

			}

			String dob = "DEFAULT";
			registerUser.setDob(dob);

			StatusInfo statusInfo = personalHealtDelegate.doRegistration(registerUser);

			if (!statusInfo.isStatus()) {

				registerVerifyMsgs.setSeverMessage(statusInfo.getErrMsg());

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTER_INPUT,
						PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
			}

			registerVerifyMsgs.setSucessMsg(PersonalHealtConstantsIF.Message.CONTINUE_TEST);

			AJAXResponse ajaxResponse = generateAJAXResponseForAnswersVO(request);
			if (ajaxResponse.isStatus()) {

				// The next Step to Load questions

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTION_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			registerVerifyMsgs.setSeverMessage(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
					PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);
		}

		registerVerifyMsgs.setSeverMessage(PersonalHealtConstantsIF.Message.ADMIN_CONTACT);

		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_REGISTERINITIAL_INPUT,
				PersonalHealtConstantsIF.Keys.OBJ, registerVerifyMsgs);

	}

	@Override
	@RequestMapping(value = "/createUser.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createUser(@RequestBody RegisterUser registerUser, HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String firstName = registerUser.getFirstName();
			if (null == firstName || firstName.isEmpty() || firstName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.FIRSTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.FIRSTNAME_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			String lastName = registerUser.getLastName();
			if (null == lastName || lastName.isEmpty() || lastName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LASTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LASTNAME_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			String userId = registerUser.getUserId();

			if (null == userId || userId.isEmpty() || userId.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.USERID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.USERID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			String email = registerUser.getEmailId();

			if (null == email || email.isEmpty() || email.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMAIL_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMAIL_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			String password = registerUser.getUserPassword();

			if (null == password || password.isEmpty() || password.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.PASSWORD_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.PASSWORD_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			int loginType = registerUser.getLoginType();

			if (loginType <= 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGIN_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGIN_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			StatusInfo statusInfo = personalHealtDelegate.doRegistration(registerUser);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/userRoles.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<UserRoleInfo> retriveUserRoles(HttpServletRequest request) {
		List<UserRoleInfo> userRoleInfo = null;
		try {

			userRoleInfo = personalHealtDelegate.retriveUserRoles();
		} catch (Exception e) {
			return userRoleInfo;

		}
		return userRoleInfo;
	}

	@Override
	@RequestMapping(value = "/createScreenTest.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createScreeningTest(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ScreeningTestVO screeningTestVO) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String testName = screeningTestVO.getTestName();

			if (null == testName || testName.isEmpty() || testName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			ArrayList<QuestionVO> questionList = screeningTestVO.getQuestionList();

			if (questionList.isEmpty() || questionList.size() == 0 || null == questionList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			StatusInfo statusInfo = personalHealtDelegate.storeScreeningTest(screeningTestVO);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			// Status Information

			if (statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);

				RangeModel rangeModel = (RangeModel) statusInfo.getModel();

				ajaxResponse.setModel(rangeModel);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return null;
	}

	@Override
	@RequestMapping(value = "/retriveAppointments.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAppointments(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AppointVO> appointList = personalHealtDelegate.retriveAppointments();

			if (null == appointList || appointList.isEmpty() || appointList.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(appointList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/deleteAppoint.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse deleteAppointments(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DeleteAppointInfo appointmentInfo) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.deleteAppointments(appointmentInfo);

			if (!statusInfo.isStatus()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.DEL_APPOINTMENT_COMPLETE);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/pateintNames.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<PatientInfo> retrivePatientNames(HttpServletRequest request,
			HttpServletResponse response) {
		List<PatientInfo> userRoleInfo = null;
		try {

			userRoleInfo = personalHealtDelegate.retrivePatientNames();
		} catch (Exception e) {
			return userRoleInfo;

		}
		return userRoleInfo;
	}

	@Override
	@RequestMapping(value = "/testnames.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<TestInfo> retriveTestNames(HttpServletRequest request, HttpServletResponse response) {
		List<TestInfo> testNameList = null;
		try {

			testNameList = personalHealtDelegate.retriveTestNames();
		} catch (Exception e) {
			return testNameList;

		}
		return testNameList;
	}

	@Override
	@RequestMapping(value = "/eligibility.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createEligibbility(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EligibilityModel eligibilityModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String testName = eligibilityModel.getTestId();

			if (null == testName || testName.isEmpty() || testName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			String pateintName = eligibilityModel.getPatName();

			if (pateintName.isEmpty() || pateintName.trim().length() == 0 || null == pateintName) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.PNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.PNAME_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			StatusInfo statusInfo = personalHealtDelegate.createEligibility(eligibilityModel);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			// Status Information

			if (statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.ELIGIBILITY_CREATION_SUCESS);

				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/approveAppoint.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse approveAppointments(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentInfo appointmentInfo) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.approveAppointments(appointmentInfo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.APPROVE_APPOINTMENT_COMPLETE);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewscreen.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewScreeningTest(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<QuestionVO> listQuestions = personalHealtDelegate.retriveQuestions(loginId);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/viewscreenpagination.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewScreeningTestPagination(HttpServletRequest request,
			HttpServletResponse response, int start, int limit, int page) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<QuestionVO> listQuestions = personalHealtDelegate.retriveQuestionsPagination(loginId, start, start,
					limit);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/submitAns.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse processPateintAnswers(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AnswerUIVO quesList) {
		AJAXResponse ajaxResponse = null;
		try {
			HttpSession session = request.getSession(false);

			String patName = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == patName || patName.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INVALID_LOGIN);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INVALID_LOGIN);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			} else {

				ArrayList<QuestionVO> questionObj = quesList.getQuesList();

				if (null == questionObj || questionObj.isEmpty() || questionObj.size() == 0) {
					ajaxResponse = new AJAXResponse();
					ajaxResponse.setStatus(false);
					Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
					msg.setErrMessage(PersonalHealtConstantsIF.Message.ALIST_EMPTY);
					List<Message> ebErrors = new ArrayList<Message>();
					ebErrors.add(msg);
					ajaxResponse.setEbErrors(ebErrors);
					return ajaxResponse;

				} else {

					HealtInfo diaInfo = personalHealtDelegate.processHealt(questionObj, patName, "YES");

					if (null == diaInfo) {
						ajaxResponse = new AJAXResponse();
						ajaxResponse.setStatus(false);
						Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
						msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
						List<Message> ebErrors = new ArrayList<Message>();
						ebErrors.add(msg);
						ajaxResponse.setEbErrors(ebErrors);
						return ajaxResponse;
					} else {

						if (diaInfo.getErrMsg() != null) {
							ajaxResponse = new AJAXResponse();
							ajaxResponse.setStatus(false);
							Message msg = new Message(diaInfo.getErrMsg());
							msg.setErrMessage(diaInfo.getErrMsg());
							List<Message> ebErrors = new ArrayList<Message>();
							ebErrors.add(msg);
							ajaxResponse.setEbErrors(ebErrors);
							return ajaxResponse;

						} else {
							ajaxResponse = new AJAXResponse();
							ajaxResponse.setStatus(true);
							ajaxResponse.setModel(diaInfo);
							return ajaxResponse;

						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/appReq.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse requestAppoitment(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = new StatusInfo();

			//
			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty() || loginId.trim().length() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			statusInfo = personalHealtDelegate.doAppointmentReq(loginId);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.APPOINTMENTSUCESS);
				return ajaxResponse;

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/placeAppointment.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView placeAppointment(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelandview = null;

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = new StatusInfo();

			//
			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty() || loginId.trim().length() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

			statusInfo = personalHealtDelegate.doAppointmentReq(loginId);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.APPOINTMENTSUCESS);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_SUCESS_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}

	@Override
	@RequestMapping(value = "/dashboard.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse dashboard(HttpServletRequest request, HttpServletResponse response) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AppointVO> appointVOList = null;
			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty() || loginId.trim().length() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			appointVOList = personalHealtDelegate.retriveDashboardForPatName(loginId);

			if (null == appointVOList || appointVOList.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message("Appoint still un approved");
				msg.setErrMessage("Appointment Still Unaprroved");
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(appointVOList);
				return ajaxResponse;
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewspecifictest.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewSpecificScreeningTest(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String testId) {
		AJAXResponse ajaxResponse = null;
		try {

			if (null == testId || testId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<QuestionVO> listQuestions = personalHealtDelegate.retriveQuestionsForTest(testId);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;
	}

	@Override
	@RequestMapping(value = "/diabeticsgraph.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse generateDiabeticsInfo(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<DiabeticsGraph> diabeticsGraphList = personalHealtDelegate.retriveDiabeticsGraph();

			if (null == diabeticsGraphList || diabeticsGraphList.isEmpty() || diabeticsGraphList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(diabeticsGraphList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/trackhistory.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse trackHistory(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(false);

			String userId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			List<DiabeticsGraph> trackHistoryGraphList = personalHealtDelegate.retriveStressGraphForUser(userId);

			if (null == trackHistoryGraphList || trackHistoryGraphList.isEmpty() || trackHistoryGraphList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(trackHistoryGraphList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/eligibilityForAll.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createEligibbilityForAll(HttpServletRequest request, HttpServletResponse response,
			@RequestBody EligibilityModel eligibilityModel) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String testName = eligibilityModel.getTestId();

			if (null == testName || testName.isEmpty() || testName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			StatusInfo statusInfo = personalHealtDelegate.createEligibilityForAll(eligibilityModel);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			// Status Information

			if (statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.ELIGIBILITY_CREATION_SUCESS);

				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
		return ajaxResponse;
	}

	@Override
	@RequestMapping(value = "/doctorsList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public List<DoctorInfo> retriveDoctors(HttpServletRequest request, HttpServletResponse response) {
		List<DoctorInfo> doctorList = null;
		try {
			doctorList = personalHealtDelegate.retriveDoctorList();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return doctorList;

		}
		return doctorList;
	}

	@Override
	@RequestMapping(value = "/retriveAppointmentsForDoc.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAppointmentsForDoctor(HttpServletRequest request,
			HttpServletResponse response) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);
			if (null == loginId) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<AppointVO> appointList = personalHealtDelegate.retriveAppointments(loginId);

			if (null == appointList || appointList.isEmpty() || appointList.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(appointList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/onlineTest.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submitAnswer(@ModelAttribute AnswerVO answerVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			AnswerVO answerVO2 = updateAnswers(answerVO, session, answerVO.getPageNumberGlobal());

			Map<Integer, AnswerVO> mapAnswerVO = (Map<Integer, AnswerVO>) session
					.getAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS);

			boolean executeDiabeticsAnalysis = false;
			if (answerVO2 != null) {
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(answerVO2);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTION_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (null == answerVO2
					&& (mapAnswerVO != null && answerVO.getQuestionVO().getPageNumber() == mapAnswerVO.size())) {

				executeDiabeticsAnalysis = true;

			}

			if (executeDiabeticsAnalysis) {

				@SuppressWarnings("unchecked")
				Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
						.getAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION);

				StressHelperUtil stressHelperUtil = new StressHelperUtil();

				ArrayList<QuestionVO> questionObj = stressHelperUtil.convertAnswerVOMapToQuestionVOList(answerVOMap);

				HealtInfo diabeticsInfo = personalHealtDelegate.processHealt(questionObj, answerVO.getPatName(), "NO");
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(diabeticsInfo);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_OUTER_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ajaxResponse.setMessage(e.getMessage());
			ajaxResponse.setStatus(false);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_OUTER_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_OUTER_PAGE,
				PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

	}

	@Override
	@RequestMapping(value = "/onlineTestUser.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submitAnswerUser(@ModelAttribute AnswerVO answerVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			AnswerVO answerVO2 = updateAnswers(answerVO, session, answerVO.getPageNumberGlobal());

			Map<Integer, AnswerVO> mapAnswerVO = (Map<Integer, AnswerVO>) session
					.getAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS);

			boolean executeDiabeticsAnalysis = false;
			if (answerVO2 != null) {
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(answerVO2);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTIONUSER_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (null == answerVO2
					&& (mapAnswerVO != null && answerVO.getQuestionVO().getPageNumber() == mapAnswerVO.size())) {

				executeDiabeticsAnalysis = true;

			}

			if (executeDiabeticsAnalysis) {

				@SuppressWarnings("unchecked")
				Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
						.getAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION);

				StressHelperUtil stressHelperUtil = new StressHelperUtil();

				ArrayList<QuestionVO> questionObj = stressHelperUtil.convertAnswerVOMapToQuestionVOList(answerVOMap);

				HealtInfo diabeticsInfo = personalHealtDelegate.processHealt(questionObj, answerVO.getPatName(), "YES");
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(diabeticsInfo);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ajaxResponse.setMessage(e.getMessage());
			ajaxResponse.setStatus(false);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_PAGE,
				PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

	}

	private AnswerVO updateAnswers(AnswerVO answerVO, HttpSession session, int pageNumber) {

		AnswerVO answerVONew = null;

		@SuppressWarnings("unchecked")
		Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
				.getAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION);

		if (answerVOMap != null && !answerVOMap.isEmpty()) {

			answerVOMap.put(answerVO.getQuestionVO().getQuesId(), answerVO);
		} else {
			answerVOMap = new HashMap<Integer, AnswerVO>();
			answerVOMap.put(answerVO.getQuestionVO().getQuesId(), answerVO);
		}

		@SuppressWarnings("unchecked")
		Map<Integer, AnswerVO> displayAnswers = (Map<Integer, AnswerVO>) session
				.getAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS);

		session.setAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION, answerVOMap);

		if (answerVO.getActionCode().equals("NEXT")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber < displayAnswers.size()) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber + 1);
			}

		} else if (answerVO.getActionCode().equals("BACK")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber <= 2) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber - 1);
			}
		}

		return answerVONew;

	}

	@Override
	@RequestMapping(value = "/takeScreenTest.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewScreenTestSingleQPerPage(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			ajaxResponse = generateAJAXResponseForAnswersVO(request);

			if (ajaxResponse.isStatus()) {

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTION_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.ERROR_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
					ajaxResponse);

		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.WELCOME_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
				ajaxResponse);

	}

	private AJAXResponse generateAJAXResponseForAnswersVOUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		ajaxResponse.setStatus(false);

		HttpSession session = request.getSession(false);

		cleanupQuestionsFromSession(session);

		String userId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

		List<AnswerVO> listQuestions = personalHealtDelegate.retriveAllGeneralQuestionsForUser(userId);

		if (isNotEmptyAnswers(listQuestions)) {

			ajaxResponse.setStatus(true);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.QUESTONVO_SESSION, listQuestions);

			Map<Integer, AnswerVO> mapAnswerVO = createMapForAllTests(listQuestions);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS, mapAnswerVO);

			ajaxResponse.setModel(listQuestions != null ? listQuestions.get(0) : null);

			ajaxResponse.setStatus(true);

		}
		return ajaxResponse;
	}

	private AJAXResponse generateAJAXResponseForAnswersVO(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		ajaxResponse.setStatus(false);

		HttpSession session = request.getSession(true);

		cleanupQuestionsFromSession(session);

		session.setAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION, request.getRemoteAddr());

		List<AnswerVO> listQuestions = personalHealtDelegate.retriveAllGeneralQuestions(request.getRemoteAddr());

		if (isNotEmptyAnswers(listQuestions)) {

			ajaxResponse.setStatus(true);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.QUESTONVO_SESSION, listQuestions);

			Map<Integer, AnswerVO> mapAnswerVO = createMapForAllTests(listQuestions);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS, mapAnswerVO);

			ajaxResponse.setModel(listQuestions != null ? listQuestions.get(0) : null);

			ajaxResponse.setStatus(true);

		}
		return ajaxResponse;
	}

	private boolean isNotEmptyAnswers(List<AnswerVO> listQuestions) {
		return listQuestions != null && !listQuestions.isEmpty();
	}

	private Map<Integer, AnswerVO> createMapForAllTests(List<AnswerVO> answerVOList) {

		Map<Integer, AnswerVO> map = new HashMap<Integer, AnswerVO>();

		if (isNotEmptyAnswers(answerVOList)) {

			for (AnswerVO answerVO : answerVOList) {

				map.put(answerVO.getQuestionVO().getPageNumber(), answerVO);

			}

		}

		return map;

	}

	@Override
	@RequestMapping(value = "/savePuzzleQuestion.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView savePuzzleQues(@ModelAttribute PuzzleModel puzzleVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();

		List<Message> ebErrors = new ArrayList<Message>();

		try {

			String filePath = null;
			CommonsMultipartFile multipath = puzzleVO.getFile();

			if (null == multipath) {

				Message msg = new Message(PersonalHealtConstantsIF.Message.FILENAME_EMPTYERR);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.FILENAME_EMPTYERR);
				ebErrors.add(msg);
				msg = new Message(PersonalHealtConstantsIF.Message.SYSTEMPROPERTY_EMPTYERR);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.SYSTEMPROPERTY_EMPTYERR);
				ebErrors.add(msg);

			} else {
				String fileName = puzzleVO.getFile().getName();

				if (null == fileName || (fileName != null && fileName.isEmpty())) {

					Message msg = new Message(PersonalHealtConstantsIF.Message.FILENAME_EMPTYERR);
					msg.setErrMessage(PersonalHealtConstantsIF.Message.FILENAME_EMPTYERR);
					ebErrors.add(msg);
				}

				String fileSystemProperty = System.getProperty("STRESSFILEPATH");

				if (null == fileSystemProperty) {
					fileSystemProperty = "c://file";
				}

				if (null == fileSystemProperty || (fileSystemProperty != null && fileSystemProperty.isEmpty())) {

					Message msg = new Message(PersonalHealtConstantsIF.Message.SYSTEMPROPERTY_EMPTYERR);
					msg.setErrMessage(PersonalHealtConstantsIF.Message.SYSTEMPROPERTY_EMPTYERR);
					ebErrors.add(msg);
				}

				String contentType = puzzleVO.getContentType();

				if (null == contentType || (contentType != null && contentType.isEmpty())) {

					Message msg = new Message(PersonalHealtConstantsIF.Message.CONTENTTYPE_EMPTYERR);
					msg.setErrMessage(PersonalHealtConstantsIF.Message.CONTENTTYPE_EMPTYERR);
					ebErrors.add(msg);
				}

				byte[] fileData = puzzleVO.getData();

				if (null == fileData) {

					Message msg = new Message(PersonalHealtConstantsIF.Message.CONTENTDATA_EMPTYERR);
					msg.setErrMessage(PersonalHealtConstantsIF.Message.CONTENTDATA_EMPTYERR);
					ebErrors.add(msg);

				}

				filePath = fileSystemProperty + "/" + fileName;
			}

			// String appPath =
			// request.getSession().getServletContext().getRealPath("");
			// constructs path of the directory to save uploaded file
			String savePath = PersonalHealtConstantsIF.Keys.PATH_FOR_IMAGES + puzzleVO.getFile().getName();

			System.out.println("The Save Path is = " + savePath);

			// creates the save directory if it does not exists
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}

			String ageGroup = puzzleVO.getAgegroup();

			if (null == ageGroup || (ageGroup != null && ageGroup.isEmpty())) {
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_ERRORMSG);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_ERRORMSG);
				ebErrors.add(msg);
			}

			String ans1 = puzzleVO.getAnswer1();

			if (null == ans1 || (ans1 != null && ans1.isEmpty())) {
				Message msg = new Message(PersonalHealtConstantsIF.Message.ANSWER1_ERRORMSG);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.ANSWER1_ERRORMSG);
				ebErrors.add(msg);
			}

			String ans2 = puzzleVO.getAnswer2();

			if (null == ans2 || (ans2 != null && ans2.isEmpty())) {
				Message msg = new Message(PersonalHealtConstantsIF.Message.ANSWER2_ERRORMSG);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.ANSWER2_ERRORMSG);
				ebErrors.add(msg);
			}

			String ans3 = puzzleVO.getAnswer3();

			String ans = puzzleVO.getAnswer4();

			String correctAnswer = puzzleVO.getCorrectAnswer();

			if (null == correctAnswer || (correctAnswer != null && correctAnswer.isEmpty())) {
				Message msg = new Message(PersonalHealtConstantsIF.Message.CORRECTANS_ERRORMSG);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.CORRECTANS_ERRORMSG);
				ebErrors.add(msg);
			}

			if (ebErrors != null && !ebErrors.isEmpty()) {

				ajaxResponse.setStatus(false);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_PUZZLE_INPUT_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

			storeFileOnServer(puzzleVO, savePath);
			StatusInfo statusInfo = personalHealtDelegate.savePuzzle(puzzleVO);

			if (!statusInfo.isStatus()) {
				ajaxResponse.setStatus(false);
				Message message = new Message(statusInfo.getErrMsg());
				message.setErrMessage(statusInfo.getErrMsg());
				ebErrors.add(message);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_PUZZLE_INPUT_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.PUZZLE_SUCESS_MSG);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ebErrors = new ArrayList<Message>();
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			ebErrors.add(msg);

			ajaxResponse.setEbErrors(ebErrors);

			ajaxResponse.setMessage(e.getMessage());
			ajaxResponse.setStatus(false);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_PUZZLE_INPUT_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}

	private void storeFileOnServer(PuzzleModel puzzleVO, String filePath) throws FileNotFoundException, IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		File temp = new File(file, puzzleVO.getName());
		System.out.println("Path : " + temp);

		FileOutputStream fos = new FileOutputStream(temp);
		fos.write(puzzleVO.getData());
		fos.close();

	}

	@Override
	@RequestMapping(value = "/viewpuzzle.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPuzzleTest(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<PuzzleVO> listPuzzleVOs = personalHealtDelegate.retrivePuzzle();

			if (null == listPuzzleVOs || listPuzzleVOs.isEmpty() || listPuzzleVOs.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.PLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.PLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listPuzzleVOs != null && !listPuzzleVOs.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listPuzzleVOs);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/mypuzzle.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView findPuzzleForUser(HttpServletRequest request) {

		ModelAndView mv = null;

		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			HttpSession session = request.getSession(true);

			session.removeAttribute(PersonalHealtConstantsIF.SessionIF.PUZZLEVO_QUES);

			String username = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == username || (username != null && username.isEmpty())) {

				ajaxResponse.setStatus(false);
				cleanupQuestionsFromSession(session);
				cleanUpUser(session);

				return new ModelAndView(PersonalHealtConstantsIF.Views.WELCOME_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
						ajaxResponse);
			}

			List<PuzzleVO> puzzleVOs = personalHealtDelegate.retrivePuzzleForUser(username);

			if (null == puzzleVOs || (puzzleVOs != null && puzzleVOs.isEmpty())) {

				ajaxResponse.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message msg = new Message(PersonalHealtConstantsIF.Message.NO_PUZZLES_FOUND);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.NO_PUZZLES_FOUND);
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);

				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_ERROR_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.PUZZLEVO_QUES, puzzleVOs);

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(puzzleVOs);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_PUZZLES_USER, PersonalHealtConstantsIF.Keys.OBJ,
					ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ajaxResponse.setStatus(false);
			ajaxResponse.setMessage(e.getMessage());
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}

	@Override
	@RequestMapping(value = "/processPuzzle.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView processPuzzleForUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();

		try {

			HttpSession session = request.getSession(true);

			String username = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == username || (username != null && username.isEmpty())) {

				ajaxResponse.setStatus(false);
				cleanupQuestionsFromSession(session);
				cleanUpUser(session);

				return new ModelAndView(PersonalHealtConstantsIF.Views.WELCOME_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
						ajaxResponse);
			}

			@SuppressWarnings("unchecked")
			List<PuzzleVO> sessionPuzzles = (List<PuzzleVO>) session
					.getAttribute(PersonalHealtConstantsIF.SessionIF.PUZZLEVO_QUES);

			if (null == sessionPuzzles || (sessionPuzzles != null && sessionPuzzles.isEmpty())) {

				ajaxResponse.setStatus(false);
				cleanupQuestionsFromSession(session);
				cleanUpUser(session);

				return new ModelAndView(PersonalHealtConstantsIF.Views.WELCOME_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
						ajaxResponse);

			}

			Information informationList = processQuestionAndCreateMap(sessionPuzzles, request);

			if (null == informationList) {
				ajaxResponse.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message msg = new Message(PersonalHealtConstantsIF.Message.PLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INFORMATIONPROCESS_FAILED);
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_ERROR_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(informationList);

			return new ModelAndView(PersonalHealtConstantsIF.Views.PUZZLE_ANALYSIS, PersonalHealtConstantsIF.Keys.OBJ,
					ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ajaxResponse.setStatus(false);
			ajaxResponse.setMessage(e.getMessage());
			return new ModelAndView(PersonalHealtConstantsIF.Views.WELCOME_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
					ajaxResponse);
		}

	}

	private Information processQuestionAndCreateMap(List<PuzzleVO> sessionPuzzles, HttpServletRequest request) {

		Information information = new Information();

		List<AnswerPuzzle> puzzleList = new ArrayList<AnswerPuzzle>();
		try {

			for (PuzzleVO puzzleVO : sessionPuzzles) {

				AnswerPuzzle answerPuzzle = new AnswerPuzzle();

				int puzzleQId = puzzleVO.getPuzzleQId();
				String questionDesc = puzzleVO.getQuestionDesc();
				String correctAnswer = puzzleVO.getCorrectAnswer();

				String correctAnswerDesc = obtainCorrectAnswer(puzzleVO, correctAnswer);

				answerPuzzle.setCorrectAnswerDesc(correctAnswerDesc);
				answerPuzzle.setCorrectAnswer(correctAnswer);
				answerPuzzle.setQuestionDesc(questionDesc);
				answerPuzzle.setQuestionId(puzzleQId);

				String providedAnswer = request.getParameter("select_" + puzzleQId);

				answerPuzzle.setProvidedAnswer(providedAnswer);

				String providedAnswerDesc = obtainCorrectAnswer(puzzleVO, providedAnswer);
				answerPuzzle.setProvidedAnswerDesc(providedAnswerDesc);

				if (providedAnswer.equals(correctAnswer)) {
					answerPuzzle.setAnswerStatus(true);
				}

				puzzleList.add(answerPuzzle);

			}

			information.setPuzzleAnswers(puzzleList);

		} catch (Exception e) {

			System.out.println("Exception");
			System.out.println(e.getMessage());
			information = null;

		}

		return information;

	}

	private String obtainCorrectAnswer(PuzzleVO puzzleVO, String correctAnswer) {
		String correctAnswerDesc = null;

		if (correctAnswer.equals("1")) {

			correctAnswerDesc = puzzleVO.getAnswer1();

		} else if (correctAnswer.equals("2")) {

			correctAnswerDesc = puzzleVO.getAnswer2();

		} else if (correctAnswer.equals("3")) {

			correctAnswerDesc = puzzleVO.getAnswer3();

		} else {
			correctAnswerDesc = puzzleVO.getAnswer4();
		}
		return correctAnswerDesc;
	}

	private void cleanUpUser(HttpSession session) {
		session.removeAttribute(PersonalHealtConstantsIF.Keys.LOGINTYPE_SESSION);
		session.removeAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);
	}

	private void cleanupQuestionsFromSession(HttpSession session) {
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS);
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION);
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.QUESTONVO_SESSION);
	}

	private void cleanupQuestionsFromSessionDisease(HttpSession session) {
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS_DISEASE);
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION_DISEASE);
		session.removeAttribute(PersonalHealtConstantsIF.SessionIF.QUESTONVO_SESSION_DISEASE);
	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister1.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge1(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroup("1");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister2.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge2(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroup("2");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/ageclassifyUnRegister3.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge3(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroup("3");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/ageclassifyUnRegister4.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge4(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroup("4");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/incomeclassifyUnRegister1.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome1(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassifcationList = personalHealtDelegate.classifyIncomeGroup("1");

			if (null == incomeClassifcationList || incomeClassifcationList.isEmpty()
					|| incomeClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/incomeclassifyUnRegister2.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome2(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassificationList = personalHealtDelegate.classifyIncomeGroup("2");

			if (null == incomeClassificationList || incomeClassificationList.isEmpty()
					|| incomeClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/incomeclassifyUnRegister3.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome3(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassificationList = personalHealtDelegate.classifyIncomeGroup("3");

			if (null == incomeClassificationList || incomeClassificationList.isEmpty()
					|| incomeClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/incomeclassifyUnRegister4.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome4(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassificationlist = personalHealtDelegate.classifyIncomeGroup("4");

			if (null == incomeClassificationlist || incomeClassificationlist.isEmpty()
					|| incomeClassificationlist.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassificationlist);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister1.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyExp1(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroup("1");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister2.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifyExp2(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroup("2");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister3.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifyExp3(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroup("3");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister4.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifyExp4(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassifcationList = personalHealtDelegate.classifyExpGroup("4");

			if (null == expClassifcationList || expClassifcationList.isEmpty() || expClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/sexclassifyUnRegister1.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifySex1(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> sexClassificationList = personalHealtDelegate.classifySexGroup("1");

			if (null == sexClassificationList || sexClassificationList.isEmpty() || sexClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(sexClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/sexclassifyUnRegister2.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifySex2(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> sexClassificationList = personalHealtDelegate.classifySexGroup("2");

			if (null == sexClassificationList || sexClassificationList.isEmpty() || sexClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(sexClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/retriveDataSets.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse readDataSets() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.readDataSets();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.READ_DATASETS_FAILED);
				webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.READ_DATASETS_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			} else {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(PersonalHealtConstantsIF.Message.READ_DATASETS_SUCESS);
				ajaxRes.setModel((GlobalRowInfo) statusInfo.getModel());

				return ajaxRes;
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@Override
	@RequestMapping(value = "/performNavBayes.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse peformNavBayesProbability(@RequestBody NaiveBayesInput navBayesInput,
			HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);
			if (session != null) {

				if (session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION) != null) {

					String userId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

					navBayesInput.setUserId(userId);

				}
			}

			if (navBayesInput.getFti() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.FTI_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.FTI_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getT3() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T3_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T3_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getT4U() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T4U_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T4U_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getTsH() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TSH_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TSH_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getTt4() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TT4_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TT4_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getFtiLung() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.FTILUNG_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.FTILUNG_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getFtiThy() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.FTI_THY_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.FTI_THY_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getT3Lung() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T3_LUNG_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T3_LUNG_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getT3Thy() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T3_THY_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T3_THY_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getT4ULung() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T4U_LUNG_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T4U_LUNG_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getT4UThy() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.T4U_THY_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.T4U_THY_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getTsHLung() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TSH_LUNG_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TSH_LUNG_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getTsHThy() <= 0) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TSH_THYROID_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TSH_THYROID_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			if (navBayesInput.getTt4Lung() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TT4_LUNG_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TT4_LUNG_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			if (navBayesInput.getTt4Thy() <= 0) {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.TT4_THY_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.TT4_THY_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			NaiveBayesDataSetModelInput naiveBayesDataSetModelInput = populateNavBays(navBayesInput);

			naiveBayesDataSetModelInput.setUserId(navBayesInput.getUserId());

			GlobalNaiveBayesOutput navBayesOutput = personalHealtDelegate
					.performNaiveBayesCalculation(naiveBayesDataSetModelInput);

			if (null == navBayesOutput) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message message = new Message(PersonalHealtConstantsIF.Message.NAV_BAYES_ERROR);
				message.setErrMessage(PersonalHealtConstantsIF.Message.NAV_BAYES_ERROR);
				ebErrors.add(message);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			} else {

				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setModel(navBayesOutput);
				ajaxRes.setMessage(PersonalHealtConstantsIF.Message.NAV_BAYES_SUCESS);

			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
		return ajaxRes;
	}

	private NaiveBayesDataSetModelInput populateNavBays(NaiveBayesInput navBayesInput) {
		NaiveBayesDataSetModelInput naiveBayesDataSetModelInput = new NaiveBayesDataSetModelInput();

		Map<String, Double> mapCriteria = new HashMap<String, Double>();

		// For Hypertension
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.FTI_ATTRIBUTE,
				((double) navBayesInput.getFti() / (double) 1000));
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T3_ATTRIBUTE, (double) navBayesInput.getT3() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T4U_ATTRIBUTE,
				(double) navBayesInput.getT4U() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TSH_ATTRIBUTE,
				(double) navBayesInput.getTsH() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TT4_ATTRIBUTE,
				(double) navBayesInput.getTt4() / (double) 1000);

		// For Lung Cancer
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.FTI_LUNG_CANCER_ATTRIBUTE,
				((double) navBayesInput.getFtiLung()) / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T3_LUNG_CANCER_ATTRIBUTE,
				(double) navBayesInput.getT3Lung() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T4U_LUNG_CANCER_ATTRIBUTE,
				(double) navBayesInput.getT4ULung() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TSH_LUNG_CANCER_ATTRIBUTE,
				(double) navBayesInput.getTsHLung() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TT4_LUNG_CANCER_ATTRIBUTE,
				(double) navBayesInput.getTt4Lung() / (double) 1000);

		// For Thyroid Cancer
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.FTI_THYROID_ATTRIBUTE,
				((double) navBayesInput.getFtiThy()) / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T3_THYROID_ATTRIBUTE,
				(double) navBayesInput.getT3Thy() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.T4U_THYROID_ATTRIBUTE,
				(double) navBayesInput.getT4UThy() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TSH_THYROID_ATTRIBUTE,
				(double) navBayesInput.getTsHThy() / (double) 1000);
		mapCriteria.put(PersonalHealtConstantsIF.NavBayes.TT4_THYROID_ATTRIBUTE,
				(double) navBayesInput.getTt4Thy() / (double) 1000);

		naiveBayesDataSetModelInput.setMapCriteria(mapCriteria);
		return naiveBayesDataSetModelInput;
	}

	@Override
	@RequestMapping(value = "/viewProbability.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewProbability(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<ProbabilityInfo> probabilityInfoList = personalHealtDelegate.viewProbability();
			if (null == probabilityInfoList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_PROBINFO);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_PROBINFO);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(probabilityInfoList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.PROBINFO_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doContigency.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doContigency() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.doContigency();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);

				if (statusInfo.getErrMsg() != null) {
					Message m = new Message(statusInfo.getErrMsg());
					m.setErrMessage(statusInfo.getErrMsg());
					ebErrors.add(m);
				} else if (statusInfo.getExceptionMsg() != null) {
					Message m = new Message(statusInfo.getExceptionMsg());
					m.setErrMessage(statusInfo.getExceptionMsg());
					ebErrors.add(m);

				} else {
					Message m = new Message(PersonalHealtConstantsIF.Message.CONTIGENCY_ERROR);
					m.setErrMessage(PersonalHealtConstantsIF.Message.CONTIGENCY_ERROR);
					ebErrors.add(m);
				}
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(PersonalHealtConstantsIF.Message.CONTIGENCY_COMPUTATION_SUCESS);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewContigency.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewContigency(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<ContigencyInfo> contigencyInfoList = personalHealtDelegate.viewContigency();
			if (null == contigencyInfoList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_CONTIGENCY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_CONTIGENCY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(contigencyInfoList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.CONTIGENCY_RETRIVE_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/doEnhanceContigency.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doEnhanceContigency() {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.doEnhanceContigency();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);

				if (statusInfo.getErrMsg() != null) {
					Message m = new Message(statusInfo.getErrMsg());
					m.setErrMessage(statusInfo.getErrMsg());
					ebErrors.add(m);
				} else if (statusInfo.getExceptionMsg() != null) {
					Message m = new Message(statusInfo.getExceptionMsg());
					m.setErrMessage(statusInfo.getExceptionMsg());
					ebErrors.add(m);

				} else {
					Message m = new Message(PersonalHealtConstantsIF.Message.ENHANCECONTIGENCY_COULD_NOT_FOUND);
					m.setErrMessage(PersonalHealtConstantsIF.Message.ENHANCECONTIGENCY_COULD_NOT_FOUND);
					ebErrors.add(m);
				}
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(PersonalHealtConstantsIF.Message.ENHANCE_CONTIGENCY_COMPUTATION_SUCESS);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewEnhanceContigency.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewEnhanceContigency(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<EnhanceContigency> enhanceContigencyList = personalHealtDelegate.viewEnhanceContigency();
			if (null == enhanceContigencyList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_ENHANCECONTIGENCY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_ENHANCECONTIGENCY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(enhanceContigencyList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.ENHANCE_CONTIGENCY_RETRIVED_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doClassifier.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView classifyType(HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			StatusInfo statusInfo = personalHealtDelegate.classifyType();
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.CLASSIFY_FAILED);
				webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.CLASSIFY_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(true);
				ajaxRes.setMessage(PersonalHealtConstantsIF.Message.CLASSIFY_SUCESS);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			webSiteUrlMsg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewClassifier.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewClassifier(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassifierInfo> classifierInfoList = personalHealtDelegate.viewClassifier();
			if (null == classifierInfoList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(classifierInfoList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.CLASSIFY_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewClassifierDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewClassifierDisease(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassifierInfo> classifierInfoList = personalHealtDelegate.viewClassifierDisease();
			if (null == classifierInfoList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(classifierInfoList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.CLASSIFY_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewCountsForClassifier.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewNoOfClassifier(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<CategoryCountVO> categoryCountVOList = personalHealtDelegate.viewClassifierCount();
			if (null == categoryCountVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER_COUNT);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER_COUNT);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(categoryCountVOList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.CLASSIFY_COUNT_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/patnames.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPatNamesTest(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<PatientNames> listQuestions = personalHealtDelegate.retrivePatNames();

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/viewspecifictestForPatName.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<TestInfo> retriveTestNamesForPateintName(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String patName) {
		List<TestInfo> testNameList = null;
		try {

			testNameList = personalHealtDelegate.retriveTestNamesForPateintName(patName);
		} catch (Exception e) {
			return testNameList;

		}
		return testNameList;
	}

	@Override
	@RequestMapping(value = "/viewanalysisForPatNameAndTestId.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewanalysisForPatNameAndTestId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String testId, @RequestParam String patName) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<AnalysisData> listQuestions = personalHealtDelegate.retriveAnalysisForPatNameAndTestId(patName,
					testId);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.ANALYSISLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.ANALYSISLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/viewdiseasetestuser.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView obtainDiseaseTestForUserFromSession(HttpServletRequest request) {

		ModelAndView modelAndView = null;

		try {
			AJAXResponse ajaxResponse = generateAJAXResponseForAnswersVODiseaseUser(request);
			if (ajaxResponse.isStatus()) {
				// The next Step to Load questions
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTIONUSER_DISEASE_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}
		} catch (Exception e) {

			e.printStackTrace();

			System.out.println("Error Message is");
			System.out.println(e.getMessage());

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_ERROR_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, PersonalHealtConstantsIF.Message.ADMIN_CONTACT);

		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_USER_PAGE, PersonalHealtConstantsIF.Keys.OBJ,
				PersonalHealtConstantsIF.Message.ADMIN_CONTACT);
	}

	private AJAXResponse generateAJAXResponseForAnswersVODiseaseUser(HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		ajaxResponse.setStatus(false);

		HttpSession session = request.getSession(false);

		cleanupQuestionsFromSessionDisease(session);

		String userId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

		List<AnswerVO> listQuestions = personalHealtDelegate.retriveAllDiseaseGeneralQuestionsForUser(userId);

		if (isNotEmptyAnswers(listQuestions)) {

			ajaxResponse.setStatus(true);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.QUESTONVO_SESSION_DISEASE, listQuestions);

			Map<Integer, AnswerVO> mapAnswerVO = createMapForAllTests(listQuestions);

			session.setAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS_DISEASE, mapAnswerVO);

			ajaxResponse.setModel(listQuestions != null ? listQuestions.get(0) : null);

			ajaxResponse.setStatus(true);

		}
		return ajaxResponse;
	}

	@Override
	@RequestMapping(value = "/onlineTestUserDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submitAnswerUserDisease(@ModelAttribute AnswerVO answerVO, HttpServletRequest request) {

		AJAXResponse ajaxResponse = new AJAXResponse();
		try {

			HttpSession session = request.getSession(false);

			AnswerVO answerVO2 = updateAnswersDisease(answerVO, session, answerVO.getPageNumberGlobal());

			Map<Integer, AnswerVO> mapAnswerVO = (Map<Integer, AnswerVO>) session
					.getAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS_DISEASE);

			boolean executeDiabeticsAnalysis = false;
			if (answerVO2 != null) {
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(answerVO2);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_QUESTIONUSER_DISEASE_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (null == answerVO2
					&& (mapAnswerVO != null && answerVO.getQuestionVO().getPageNumber() == mapAnswerVO.size())) {

				executeDiabeticsAnalysis = true;

			}

			if (executeDiabeticsAnalysis) {

				@SuppressWarnings("unchecked")
				Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
						.getAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION_DISEASE);

				StressHelperUtil stressHelperUtil = new StressHelperUtil();

				ArrayList<QuestionVO> questionObj = stressHelperUtil.convertAnswerVOMapToQuestionVOList(answerVOMap);

				HealtInfo diabeticsInfo = personalHealtDelegate.processHealtDisease(questionObj, answerVO.getPatName(),
						"YES");
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(diabeticsInfo);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_DISEASE_PAGE,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());

			ajaxResponse.setMessage(e.getMessage());
			ajaxResponse.setStatus(false);

			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_PAGE,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}
		return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_ANALYIS_USER_PAGE,
				PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

	}

	@Override
	@RequestMapping(value = "/createScreenTestDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse createScreeningTestDisease(HttpServletRequest request,
			HttpServletResponse response, @RequestBody ScreeningTestVO screeningTestVO) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String testName = screeningTestVO.getTestName();

			if (null == testName || testName.isEmpty() || testName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTNAME_EMPTY);

				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			ArrayList<QuestionVO> questionList = screeningTestVO.getQuestionList();

			if (questionList.isEmpty() || questionList.size() == 0 || null == questionList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			StatusInfo statusInfo = personalHealtDelegate.storeScreeningTestDisease(screeningTestVO);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			// Status Information

			if (statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);

				RangeModel rangeModel = (RangeModel) statusInfo.getModel();

				ajaxResponse.setModel(rangeModel);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return null;
	}

	@Override
	@RequestMapping(value = "/retriveAppointmentsForDocDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAppointmentsForDoctorDisease(HttpServletRequest request,
			HttpServletResponse response) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);
			if (null == loginId) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<AppointVO> appointList = personalHealtDelegate.retriveAppointmentsDisease(loginId);

			if (null == appointList || appointList.isEmpty() || appointList.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(appointList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/deleteAppointDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse deleteAppointmentsDisease(HttpServletRequest request,
			HttpServletResponse response, @RequestBody DeleteAppointInfo appointmentInfo) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.deleteAppointmentsDisease(appointmentInfo);

			if (!statusInfo.isStatus()) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.DEL_APPOINTMENT_COMPLETE);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/testnamesdisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<TestInfo> retriveTestNamesDisease(HttpServletRequest request,
			HttpServletResponse response) {
		List<TestInfo> testNameList = null;
		try {

			testNameList = personalHealtDelegate.retriveTestNamesDisease();
		} catch (Exception e) {
			return testNameList;

		}
		return testNameList;
	}

	@Override
	@RequestMapping(value = "/viewspecifictestdisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewSpecificScreeningTestDisease(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String testId) {
		AJAXResponse ajaxResponse = null;
		try {

			if (null == testId || testId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.TESTID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.TESTID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<QuestionVO> listQuestions = personalHealtDelegate.retriveQuestionsForTestDisease(testId);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;
	}

	@Override
	@RequestMapping(value = "/patnamesdisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewPatNamesTestDisease(HttpServletRequest request,
			HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<PatientNames> listQuestions = personalHealtDelegate.retrivePatNamesDisease();

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.QLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/viewanalysisForPatNameAndTestIdDisease.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewanalysisForPatNameAndTestIdDisease(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String testId, @RequestParam String patName) {

		AJAXResponse ajaxResponse = null;
		try {

			HttpSession session = request.getSession(true);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			List<AnalysisData> listQuestions = personalHealtDelegate.retriveAnalysisForPatNameAndTestIdDisease(patName,
					testId);

			if (null == listQuestions || listQuestions.isEmpty() || listQuestions.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.ANALYSISLIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.ANALYSISLIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			if (listQuestions != null && !listQuestions.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(listQuestions);
				return ajaxResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION " + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;

		}
		return ajaxResponse;

	}

	@Override
	@RequestMapping(value = "/viewspecifictestForPatNameDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<TestInfo> viewspecifictestForPatNameDisease(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String patName) {
		List<TestInfo> testNameList = null;
		try {

			testNameList = personalHealtDelegate.retriveTestNamesForPateintNameDisease(patName);
		} catch (Exception e) {
			return testNameList;

		}
		return testNameList;
	}

	@Override
	@RequestMapping(value = "/viewCountsForClassifierDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewNoOfClassifierDisease(HttpServletRequest request) {
		AJAXResponse ajaxResponse;
		try {
			ajaxResponse = new AJAXResponse();

			List<CategoryCountVO> categoryCountVOList = personalHealtDelegate.viewClassifierCountDisease();
			if (null == categoryCountVOList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER_COUNT);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EMPTY_CLASSIFIER_COUNT);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(categoryCountVOList);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.CLASSIFY_COUNT_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister1Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge1Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroupDisease("1");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister2Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge2Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroupDisease("2");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister3Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge3Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroupDisease("3");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/ageclassifyUnRegister4Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyAge4Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationAgeGroup> ageClassificationList = personalHealtDelegate.classifyAgeGroupDisease("4");

			if (null == ageClassificationList || ageClassificationList.isEmpty() || ageClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.AGEGROUP_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(ageClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/sexclassifyUnRegister2Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifySex2Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> sexClassificationList = personalHealtDelegate.classifySexGroupDisease("2");

			if (null == sexClassificationList || sexClassificationList.isEmpty() || sexClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(sexClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/sexclassifyUnRegister1Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	@Override
	public @ResponseBody AJAXResponse classifySex1Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> sexClassificationList = personalHealtDelegate.classifySexGroupDisease("1");

			if (null == sexClassificationList || sexClassificationList.isEmpty() || sexClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.SEX_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(sexClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister1Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyExp1Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroupDisease("1");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister2Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyExp2Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroupDisease("2");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister3Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyExp3Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroupDisease("3");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@RequestMapping(value = "/profexpclassifyUnRegister4Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyExp4Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> expClassificationList = personalHealtDelegate.classifyExpGroupDisease("4");

			if (null == expClassificationList || expClassificationList.isEmpty() || expClassificationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.EXP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(expClassificationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/incomeclassifyUnRegister1Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome1Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassifcationList = personalHealtDelegate.classifyIncomeGroupDisease("1");

			if (null == incomeClassifcationList || incomeClassifcationList.isEmpty()
					|| incomeClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}
	
	@Override
	@RequestMapping(value = "/incomeclassifyUnRegister2Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome2Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassifcationList = personalHealtDelegate.classifyIncomeGroupDisease("2");

			if (null == incomeClassifcationList || incomeClassifcationList.isEmpty()
					|| incomeClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}
	
	@Override
	@RequestMapping(value = "/incomeclassifyUnRegister3Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome3Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassifcationList = personalHealtDelegate.classifyIncomeGroupDisease("3");

			if (null == incomeClassifcationList || incomeClassifcationList.isEmpty()
					|| incomeClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}

	@Override
	@RequestMapping(value = "/incomeclassifyUnRegister4Disease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse classifyIncome4Disease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<ClassificationGroup> incomeClassifcationList = personalHealtDelegate.classifyIncomeGroupDisease("4");

			if (null == incomeClassifcationList || incomeClassifcationList.isEmpty()
					|| incomeClassifcationList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.INCOMEGROUP_EMPTYLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(incomeClassifcationList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}
	
	
	@Override
	@RequestMapping(value = "/retriveAppointmentsDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveAppointmentsDisease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AppointVO> appointList = personalHealtDelegate.retriveAppointmentsDisease();

			if (null == appointList || appointList.isEmpty() || appointList.size() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.APPOINT_LIST_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(appointList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}
	
	
	@Override
	@RequestMapping(value = "/dashboardDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse dashboardDisease(HttpServletRequest request, HttpServletResponse response) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<AppointVO> appointVOList = null;
			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty() || loginId.trim().length() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;

			}

			appointVOList = personalHealtDelegate.retriveDashboardForPatNameDisease(loginId);

			if (null == appointVOList || appointVOList.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message("Appoint still un approved");
				msg.setErrMessage("Appointment Still Unaprroved");
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setModel(appointVOList);
				return ajaxResponse;
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}
	
	
	@Override
	@RequestMapping(value = "/trackhistoryDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse trackHistoryDisease(HttpServletRequest request, HttpServletResponse response) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(false);

			String userId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			List<DiabeticsGraph> trackHistoryGraphList = personalHealtDelegate.retriveStressGraphForUserDisease(userId);

			if (null == trackHistoryGraphList || trackHistoryGraphList.isEmpty() || trackHistoryGraphList.size() == 0) {

				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.DIAGRAPH_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(trackHistoryGraphList);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}

	}
	
	private AnswerVO updateAnswersDisease(AnswerVO answerVO, HttpSession session, int pageNumber) {

		AnswerVO answerVONew = null;

		@SuppressWarnings("unchecked")
		Map<Integer, AnswerVO> answerVOMap = (Map<Integer, AnswerVO>) session
				.getAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION_DISEASE);

		if (answerVOMap != null && !answerVOMap.isEmpty()) {

			answerVOMap.put(answerVO.getQuestionVO().getQuesId(), answerVO);
		} else {
			answerVOMap = new HashMap<Integer, AnswerVO>();
			answerVOMap.put(answerVO.getQuestionVO().getQuesId(), answerVO);
		}

		@SuppressWarnings("unchecked")
		Map<Integer, AnswerVO> displayAnswers = (Map<Integer, AnswerVO>) session
				.getAttribute(PersonalHealtConstantsIF.SessionIF.DISPLAY_QUESTIONS_DISEASE);

		session.setAttribute(PersonalHealtConstantsIF.SessionIF.ANSWER_SESSION_DISEASE, answerVOMap);

		if (answerVO.getActionCode().equals("NEXT")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber < displayAnswers.size()) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber + 1);
			}

		} else if (answerVO.getActionCode().equals("BACK")) {

			if (displayAnswers != null && !displayAnswers.isEmpty() && pageNumber <= 2) {
				answerVONew = new AnswerVO();
				answerVONew = displayAnswers.get(pageNumber - 1);
			}
		}

		return answerVONew;

	}
	
	
	@Override
	@RequestMapping(value = "/placeAppointmentDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView placeAppointmentDisease(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelandview = null;

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = new StatusInfo();

			//
			HttpSession session = request.getSession(false);
			String loginId = (String) session.getAttribute(PersonalHealtConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId || loginId.isEmpty() || loginId.trim().length() == 0) {

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				msg.setErrMessage(PersonalHealtConstantsIF.Message.LOGINID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

			statusInfo = personalHealtDelegate.doAppointmentReqDisease(loginId);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
			} else {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.APPOINTMENTSUCESS);
				return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_SUCESS_USER,
						PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);

			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			msg.setErrMessage(PersonalHealtConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(PersonalHealtConstantsIF.Views.VIEW_APPOINT_ERROR_USER,
					PersonalHealtConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}
	
	@Override
	@RequestMapping(value = "/approveAppointDisease.do", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse approveAppointmentsDisease(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AppointmentInfo appointmentInfo) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			StatusInfo statusInfo = personalHealtDelegate.approveAppointmentsDisease(appointmentInfo);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message(statusInfo.getErrMsg());
				msg.setErrMessage(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}

			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(PersonalHealtConstantsIF.Message.APPROVE_APPOINTMENT_COMPLETE);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(false);
			Message msg = new Message(e.getMessage());
			msg.setErrMessage(e.getMessage());
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}
}
