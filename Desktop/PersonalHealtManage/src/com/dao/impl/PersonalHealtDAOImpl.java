package com.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.constants.PersonalHealtConstantsIF;
import com.dao.inter.PersonalHealtDAOIF;
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
import com.model.ManyValueRating;
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

public class PersonalHealtDAOImpl implements PersonalHealtDAOIF {

	protected SimpleJdbcTemplate template;

	public SimpleJdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(SimpleJdbcTemplate template) {
		this.template = template;
	}

	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Autowired
	protected MessageSource sqlProperties;

	public MessageSource getSqlProperties() {
		return sqlProperties;
	}

	public void setSqlProperties(MessageSource sqlProperties) {
		this.sqlProperties = sqlProperties;
	}

	protected JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void init() {
		template = new SimpleJdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int retriveLoginType(String userId) {
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_LOGINTYPE_WHERE_USERID_SQL, null, null);
			return jdbcTemplate.queryForList(sql, Integer.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public String retrivePassword(String userId) {
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PASSWORD_WHERE_USERID_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveUserIds() {
		System.out.println("METHOD[retriveUserIds()]");
		try {
			System.out.println("SQL Proprty" + sqlProperties);
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTER_USERIDS_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertLogin(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_LOGIN_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { registerUser.getFirstName(), registerUser.getLastName(),
							registerUser.getUserPassword(), registerUser.getEmailId(), registerUser.getUserId(),
							registerUser.getLoginType(), registerUser.getAge(), registerUser.getDob(),
							registerUser.getSex(), registerUser.getAnswer1(), registerUser.getAnswer2(),
							registerUser.getAnswer3(), registerUser.getAnswer4() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<UserRoleInfo> retriveUserRoleInfo() {
		List<UserRoleInfo> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_USERROLE_SQL, null,
					null);
			userRoleInfo = jdbcTemplate.query(sql, new UserRoleInfoMapper());

		} catch (Exception e) {
			return userRoleInfo;
		}
		return userRoleInfo;
	}

	final class UserRoleInfoMapper implements RowMapper<UserRoleInfo> {
		public UserRoleInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			UserRoleInfo userRoleInfo = new UserRoleInfo();
			userRoleInfo.setRoleId(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.ROLE_ID_COL));
			userRoleInfo.setRoleKey(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ROLE_NAME_COL));
			userRoleInfo.setRoleDesc(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ROLE_DESC_COL));
			return userRoleInfo;
		}
	}

	// INSERT INTO
	// QUESTION(QUESDESC,ANSWER1,ANSWER2,ANSWER3,ANSWER4,SUG1,SUG2,SUG3,SUG4,TESTNAME,RATING1,RATING2,RATING3,RATING4)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)

	@Override
	public StatusInfo insertQuestion(QuestionVO questionVO, String testName) {
		StatusInfo insertQuestionStatus = null;
		try {
			insertQuestionStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_QUESTION_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { questionVO.getQuestDesc(), questionVO.getAns1(), questionVO.getAns2(),
							questionVO.getAns3(), questionVO.getAns4(), questionVO.getSug1(), questionVO.getSug2(),
							questionVO.getSug3(), questionVO.getSug4(), testName, questionVO.getRating1(),
							questionVO.getRating2(), questionVO.getRating3(), questionVO.getRating4() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
							Types.INTEGER, Types.INTEGER, Types.INTEGER });
			insertQuestionStatus.setStatus(true);
			return insertQuestionStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertQuestionStatus = new StatusInfo();
			insertQuestionStatus.setErrMsg(e.getMessage());
			insertQuestionStatus.setStatus(false);
			return insertQuestionStatus;

		}
	}

	// INSERT INTO
	// TEST(TESTNAME,R1LL,R1HL,R2LL,R2HL,R3LL,R3HL)VALUES(?,?,?,?,?,?,?)
	// INSERT INTO
	// TEST(TESTNAME,R1LL,R1HL,R2LL,R2HL,R3LL,R3HL)VALUES(?,?,?,?,?,?,?)
	@Override
	public StatusInfo insertTest(RangeModel rangeModel) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_TEST_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { rangeModel.getTestName(), rangeModel.getR1LL(), rangeModel.getR1HL(),
							rangeModel.getR2LL(), rangeModel.getR2HL(), rangeModel.getR3LL(), rangeModel.getR3HL(),
							rangeModel.getR4LL(), rangeModel.getR4HL() },
					new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
							Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<String> retriveTestNames() {
		System.out.println("METHOD[retriveTestNames()]");
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMES_SQL, null,
					null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AppointVO> retriveAppointments() {
		List<AppointVO> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_SQL, null, null);
			userRoleInfo = jdbcTemplate.query(sql, new AppointInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return userRoleInfo;
		}
		return userRoleInfo;
	}

	final class AppointInfoMapper implements RowMapper<AppointVO> {
		public AppointVO mapRow(ResultSet rs, int arg1) throws SQLException {
			AppointVO appointInfo = new AppointVO();

			appointInfo.setAppointId(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.APPOINTID_COL));

			appointInfo.setPateintName(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.PATIENTNAME_COL));

			appointInfo.setStatus(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.STATUS_COL));

			appointInfo.setDate(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.DATE_COL));

			appointInfo.setFromTime(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.TIMEIN_COL));

			appointInfo.setToTime(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.TIMEOUT_COL));

			appointInfo.setDocId(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.DOCNAME_COL));

			return appointInfo;
		}
	}

	@Override
	public StatusInfo deleteAppointment(Integer appointmentId) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.DELETE_APPOINTMENT_SQL, null,
					null);
			jdbcTemplate.update(sql, new Object[] { appointmentId }, new int[] { Types.INTEGER });
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<PatientInfo> retrivePatientNames() {
		List<PatientInfo> pateintList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PATIENTLIST_SQL, null,
					null);
			pateintList = jdbcTemplate.query(sql, new PatientNameInfoMapper());

		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	final class PatientNameInfoMapper implements RowMapper<PatientInfo> {
		public PatientInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			PatientInfo patientInfo = new PatientInfo();

			patientInfo.setPatName(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.USERID_COL));

			return patientInfo;
		}
	}

	@Override
	public List<TestInfo> retriveTestNamesInTestInfoFormat() {
		List<TestInfo> testNameList = null;
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMESLIST_FROM_TEST_SQL, null, null);
			testNameList = jdbcTemplate.query(sql, new TestNameInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	final class TestNameInfoMapper implements RowMapper<TestInfo> {
		public TestInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			TestInfo testInfo = new TestInfo();
			testInfo.setTestId(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.TESTNAME_COL));
			return testInfo;
		}
	}

	@Override
	public List<String> retrivePatientNamesFromEligibility() {
		System.out.println("METHOD[retrivePatientNamesFromEligibility()]");
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PNAMES_FROM_ELIGIBILITY_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertEligibility(EligibilityModel eligibilityModel) {
		StatusInfo insertElibilityStatus = null;
		try {
			insertElibilityStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_ELIGIBILITY_SQL, null,
					null);
			jdbcTemplate.update(sql, new Object[] { eligibilityModel.getPatName(), eligibilityModel.getTestId() },
					new int[] { Types.VARCHAR, Types.VARCHAR });
			insertElibilityStatus.setStatus(true);
			return insertElibilityStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertElibilityStatus = new StatusInfo();
			insertElibilityStatus.setErrMsg(e.getMessage());
			insertElibilityStatus.setStatus(false);
			return insertElibilityStatus;
		}
	}

	@Override
	public StatusInfo updateEligibility(EligibilityModel eligibilityModel) {
		StatusInfo updateElibilityStatus = null;
		try {
			updateElibilityStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.UPDATE_ELIGIBILITY_SQL, null,
					null);
			jdbcTemplate.update(sql, new Object[] { eligibilityModel.getTestId(), eligibilityModel.getPatName() },
					new int[] { Types.VARCHAR, Types.VARCHAR });
			updateElibilityStatus.setStatus(true);
			return updateElibilityStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			updateElibilityStatus = new StatusInfo();
			updateElibilityStatus.setErrMsg(e.getMessage());
			updateElibilityStatus.setStatus(false);
			return updateElibilityStatus;
		}
	}

	@Override
	public StatusInfo approveAppointment(Integer appointmentId, String date, String fromTime, String toTime) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.APPROVE_APPOINTMENT_SQL, null,
					null);
			jdbcTemplate.update(sql, new Object[] { appointmentId, date, fromTime, toTime },
					new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public String retriveTestNameFromEligibility(String loginId) {
		String testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAME_FROM_ELIGIBILITY_WHERE_PATNAME_SQL, null,
					null);
			testNameList = jdbcTemplate.queryForObject(sql, String.class, loginId);

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	@Override
	public List<QuestionVO> retriveQuestions(String testName) {
		List<QuestionVO> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_QUESTIONS_SQL, null,
					null);
			userRoleInfo = jdbcTemplate.query(sql, new QuestionListMapper(), testName);

		} catch (Exception e) {
			return userRoleInfo;
		}
		return userRoleInfo;
	}

	final class QuestionListMapper implements RowMapper<QuestionVO> {
		public QuestionVO mapRow(ResultSet rs, int arg1) throws SQLException {
			QuestionVO questionVO = new QuestionVO();

			questionVO.setAns1(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ANSWER1_COL));
			questionVO.setAns2(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ANSWER2_COL));
			questionVO.setAns3(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ANSWER3_COL));
			questionVO.setAns4(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.ANSWER4_COL));
			questionVO.setQuesId(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.QUESID_COL));
			questionVO.setQuestDesc(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.QUESDESC_COL));
			questionVO.setRating1(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.RATING1_COL));
			questionVO.setRating2(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.RATING2_COL));
			questionVO.setRating3(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.RATING3_COL));
			questionVO.setRating4(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.RATING4_COL));
			questionVO.setSug1(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.SUG1_COL));
			questionVO.setSug2(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.SUG2_COL));
			questionVO.setSug3(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.SUG3_COL));
			questionVO.setSug4(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.SUG4_COL));
			questionVO.setTestName(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.TESTNAME_COL));
			return questionVO;
		}
	}

	@Override
	public RangeModel retriveRangeModel(String testName) {
		RangeModel rangeModel = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_RANGE_WHERE_TESTNAME_SQL,
					null, null);
			rangeModel = jdbcTemplate.queryForObject(sql, new RangeMapper(), testName);

		} catch (Exception e) {
			return rangeModel;
		}
		return rangeModel;
	}

	final class RangeMapper implements RowMapper<RangeModel> {
		public RangeModel mapRow(ResultSet rs, int arg1) throws SQLException {
			RangeModel rangeModel = new RangeModel();
			rangeModel.setTestName(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.TESTNAME_COL));
			rangeModel.setR1LL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R1LL_COL));
			rangeModel.setR1HL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R1HL_COL));
			rangeModel.setR2LL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R2LL_COL));
			rangeModel.setR2HL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R2HL_COL));
			rangeModel.setR3LL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R3LL_COL));
			rangeModel.setR3HL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R3HL_COL));
			rangeModel.setR4LL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R4LL_COL));
			rangeModel.setR4HL(rs.getInt(PersonalHealtConstantsIF.DATABASECOLUMNS.R4HL_COL));

			return rangeModel;
		}
	}

	// INSERT INTO
	// ANSWER(QUESID,QUESDESC,ANSWER1,ANSWER2,ANSWER3,ANSWER4,SUG1,SUG2,SUG3,SUG4,TESTNAME,RATING1,RATING2,RATING3,RATING4,PATEINTNAME,TYPE,SELECTEDANSWER,TOTALRATING,CURRENTTIME)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	@Override
	public StatusInfo insertAnswer(AnswerVO answerVO) {
		StatusInfo insertAnswerStatus = null;
		try {
			insertAnswerStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_ANSWER_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { answerVO.getQuestionVO().getQuesId(), answerVO.getQuestionVO().getQuestDesc(),
							answerVO.getQuestionVO().getAns1(), answerVO.getQuestionVO().getAns2(),
							answerVO.getQuestionVO().getAns3(), answerVO.getQuestionVO().getAns4(),
							answerVO.getQuestionVO().getSug1(), answerVO.getQuestionVO().getSug2(),
							answerVO.getQuestionVO().getSug3(), answerVO.getQuestionVO().getSug4(),
							answerVO.getQuestionVO().getTestName(), answerVO.getQuestionVO().getRating1(),
							answerVO.getQuestionVO().getRating2(), answerVO.getQuestionVO().getRating3(),
							answerVO.getQuestionVO().getRating4(), answerVO.getPatName(), answerVO.getType(),
							answerVO.getQuestionVO().getSelectedAnswer(), answerVO.getTotalRating(),
							answerVO.getCurrentTime() },
					new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.INTEGER, Types.VARCHAR

					});
			insertAnswerStatus.setStatus(true);
			return insertAnswerStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertAnswerStatus = new StatusInfo();
			insertAnswerStatus.setErrMsg(e.getMessage());
			insertAnswerStatus.setStatus(false);
			return insertAnswerStatus;

		}
	}

	@Override
	public StatusInfo insertAppointment(String loginId, int unapprovedKey) {
		StatusInfo insertAppointmentStatus = null;
		try {
			insertAppointmentStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_APPOINT_SQL, null, null);
			jdbcTemplate.update(sql, new Object[] { loginId, unapprovedKey },
					new int[] { Types.VARCHAR, Types.INTEGER });
			insertAppointmentStatus.setStatus(true);
			return insertAppointmentStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertAppointmentStatus = new StatusInfo();
			insertAppointmentStatus.setErrMsg(e.getMessage());
			insertAppointmentStatus.setStatus(false);
			return insertAppointmentStatus;

		}
	}

	@Override
	public StatusInfo retriveAppointStatus(String loginId) {
		StatusInfo retriveAppointmentStatus = null;
		try {
			retriveAppointmentStatus = new StatusInfo();
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINTSTATUS_WHERE_PATID_SQL, null, null);
			// int status = jdbcTemplate.queryForInt(sql, loginId);
			int status = jdbcTemplate.queryForList(sql, Integer.class, loginId).get(0);
			retriveAppointmentStatus.setType(status);
			retriveAppointmentStatus.setStatus(true);
			return retriveAppointmentStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			retriveAppointmentStatus = new StatusInfo();
			retriveAppointmentStatus.setErrMsg(e.getMessage());
			retriveAppointmentStatus.setStatus(false);
			return retriveAppointmentStatus;

		}
	}

	@Override
	public List<String> retriveUniqueTestNames() {

		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUETESTS_FROM_TEST_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}
	
	
	@Override
	public List<String> retriveUniqueTestNamesDisease1() {
 
		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUETESTS_FROM_TEST_PREDICT_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}

	@Override
	public StatusInfo retriveTotalRatingFromPatName(String patName, String curTime) {

		StatusInfo statusInfo = null;
		List<ManyValueRating> uniqueList = null;
		try {
			uniqueList = new ArrayList<ManyValueRating>();
			/*
			 * String sql = sqlProperties .getMessage(
			 * StressConstantsIF.DATABASESQL
			 * .RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_TESTNAME_SQL,
			 * null, null);
			 */

			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TOTALRATING_FROM_ANSWERS_WHERE_PATNAME_CURTIME_SQL,
					null, null);

			uniqueList = jdbcTemplate.query(sql, new RatingMapper(), patName, curTime);

			if (null == uniqueList || uniqueList.isEmpty() || uniqueList.size() == 0) {
				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(PersonalHealtConstantsIF.Message.RATING_EMPTY);
				return statusInfo;
			}

			int totalRating = uniqueList.get(0).getTotalRating();
			String testName = uniqueList.get(0).getTestName();
			statusInfo = new StatusInfo();
			statusInfo.setStatus(true);
			statusInfo.setType(totalRating);
			statusInfo.setTestName(testName);
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

	final class RatingMapper implements RowMapper<ManyValueRating> {
		public ManyValueRating mapRow(ResultSet rs, int arg1) throws SQLException {
			ManyValueRating ratingTestName = new ManyValueRating();

			ratingTestName.setTestName(rs.getString("TESTNAME"));
			ratingTestName.setTotalRating(rs.getInt("TOTALRATING"));

			return ratingTestName;
		}
	}

	@Override
	public List<String> retriveUserIdsForPateint() {
		try {
			System.out.println("SQL Proprty" + sqlProperties);
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTER_USERIDS_PATEINT_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo approveAppointment(AppointVO appointmentVOTemp) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			// UPDATE APPOINTMENT SET DATE=?,TIMEIN=?,TIMEOUT=?,DOCNAME=? WHERE
			// APPOINTID=?
			// UPDATE APPOINTMENT SET
			// DATE=?,TIMEIN=?,TIMEOUT=?,DOCNAME=?,STATUS=? WHERE APPOINTID=?
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.UPDATE_APPOINTMENT_SQL, null,
					null);
			jdbcTemplate.update(sql,
					new Object[] { appointmentVOTemp.getDate(), appointmentVOTemp.getFromTime(),
							appointmentVOTemp.getToTime(), appointmentVOTemp.getDocId(), appointmentVOTemp.getStatus(),
							appointmentVOTemp.getAppointId(), },

					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
							Types.INTEGER });
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<DoctorInfo> retriveDoctorList() {
		List<DoctorInfo> doctorInfoList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_DOCTORNAME_SQL, null,
					null);
			List<String> doctorInfoL = jdbcTemplate.queryForList(sql, String.class);

			doctorInfoList = new ArrayList<DoctorInfo>();
			for (String doctorInfoTemp : doctorInfoL) {
				DoctorInfo di = new DoctorInfo();
				di.setDocId(doctorInfoTemp);
				di.setDocName(doctorInfoTemp);
				doctorInfoList.add(di);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return null;
		}
		return doctorInfoList;
	}

	@Override
	public List<String> retrivePatNamesFromAppointment() {

		List<String> patNames = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PATNAME_APPOINT_SQL,
					null, null);
			patNames = jdbcTemplate.queryForList(sql, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return null;
		}
		return patNames;
	}

	@Override
	public List<AppointVO> retriveAppointments(String doctorName) {
		List<AppointVO> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_WHERE_DOCNAMESQL,
					null, null);
			userRoleInfo = jdbcTemplate.query(sql, new AppointInfoMapper(), doctorName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return userRoleInfo;
		}
		return userRoleInfo;

	}

	@Override
	public List<AppointVO> retriveAppointmentsForPatName(String patName) {
		List<AppointVO> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_WHERE_PATNAMESQL,
					null, null);
			userRoleInfo = jdbcTemplate.query(sql, new AppointInfoMapper(), patName);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return userRoleInfo;
		}
		return userRoleInfo;

	}

	public static void main(String s[]) {
		ApplicationContext context = new ClassPathXmlApplicationContext("dia.xml");

		PersonalHealtDAOIF diabeticsDAOIF = (PersonalHealtDAOIF) context.getBean("diabeticsDAOBean",
				PersonalHealtDAOImpl.class);

		List<AppointVO> appVOList = diabeticsDAOIF.retriveAppointments("bush123");

		System.out.println("Appointment VO List =");
		System.out.println(appVOList);

	}

	@Override
	public List<SuggestionObj> findSuggestionsByType(String type) {
		List<SuggestionObj> suggestionList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_SUGGESTIONS_BYTYPE_SQL,
					null, null);
			suggestionList = jdbcTemplate.query(sql, new SuggestionMapper(), type);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return suggestionList;
		}
		return suggestionList;
	}

	final class SuggestionMapper implements RowMapper<SuggestionObj> {
		public SuggestionObj mapRow(ResultSet rs, int arg1) throws SQLException {

			SuggestionObj suggestionInfo = new SuggestionObj();
			suggestionInfo.setSuggestionId(rs.getInt("SUGGESTIONID"));
			suggestionInfo.setSuggestion(rs.getString("SUGGESTIONDESC"));
			suggestionInfo.setType(rs.getString("SUGGESTIONTYPE"));

			return suggestionInfo;
		}
	}

	@Override
	public List<String> retriveUniqueTestNamesFromQuestionsForUserId(String userId) {
		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class, userId);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}

	@Override
	public List<String> retriveUniqueCurrentTimeFromAnswer(String userId) {
		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CURRENTTIME_FROM_ANSWER_FORUSERID_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class, userId);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}

	@Override
	public StatusInfo savePuzzle(PuzzleModel puzzleVO) {
		StatusInfo insertPuzzle = null;
		try {
			insertPuzzle = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_PUZZLE_SQL, null, null);
			jdbcTemplate.update(sql, new Object[] { puzzleVO.getQuestionDesc(), puzzleVO.getAgegroup(),
					puzzleVO.getAnswer1(), puzzleVO.getAnswer2(), puzzleVO.getAnswer3(), puzzleVO.getAnswer4(),
					puzzleVO.getCorrectAnswer(), puzzleVO.getName(), puzzleVO.getContentType(), puzzleVO.getData() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB });
			insertPuzzle.setStatus(true);
			return insertPuzzle;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertPuzzle = new StatusInfo();
			insertPuzzle.setErrMsg(e.getMessage());
			insertPuzzle.setStatus(false);
			return insertPuzzle;

		}
	}

	@Override
	public List<PuzzleVO> retrivePuzzle() {
		List<PuzzleVO> puzzleVO = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PUZZLE_SQL, null, null);
			puzzleVO = jdbcTemplate.query(sql, new PuzzleListMapper());

		} catch (Exception e) {
			return puzzleVO;
		}
		return puzzleVO;
	}

	final class PuzzleListMapper implements RowMapper<PuzzleVO> {
		public PuzzleVO mapRow(ResultSet rs, int arg1) throws SQLException {
			PuzzleVO puzzleVO = new PuzzleVO();

			puzzleVO.setAgegroup(rs.getString("AGEGROUP"));
			puzzleVO.setAnswer1(rs.getString("ANSWER1"));
			puzzleVO.setAnswer2(rs.getString("ANSWER2"));
			puzzleVO.setAnswer3(rs.getString("ANSWER3"));
			puzzleVO.setAnswer4(rs.getString("ANSWER4"));
			puzzleVO.setContentType(rs.getString("CONTENTTYPE"));
			puzzleVO.setCorrectAnswer(rs.getString("CORRECTANSWER"));
			puzzleVO.setName(rs.getString("FILENAME"));
			puzzleVO.setPuzzleQId(rs.getInt("PUZZLEQID"));
			puzzleVO.setQuestionDesc(rs.getString("QUESDESC"));

			return puzzleVO;
		}
	}

	@Override
	public String findAgeForUserNameFromLogin(String username) {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_AGE_FOR_USERNAME_SQL,
					null, null);
			return jdbcTemplate.queryForObject(sql, String.class, username);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<PuzzleVO> retrivePuzzleForAgeGroup(String ageGroup) {
		List<PuzzleVO> puzzleVO = null;
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PUZZLE_WHERE_AGEGROUP_SQL, null, null);
			puzzleVO = jdbcTemplate.query(sql, new PuzzleListMapper(), ageGroup);

		} catch (Exception e) {
			return puzzleVO;
		}
		return puzzleVO;
	}

	@Override
	public RegisterUser retriveRegisterInfoForPatName(String userId, String registerOrNot) {
		RegisterUser registerUser = null;
		try {
			String sql = null;
			if ("YES".equals(registerOrNot)) {
				sql = sqlProperties.getMessage(
						PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTERINFO_FOR_USERID_PLAIN_SQL, null, null);
				registerUser = jdbcTemplate.queryForObject(sql, new RegisterInfoMapper(), userId);
			} else {
				sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTERINFO_FOR_USERID_SQL,
						null, null);
				registerUser = jdbcTemplate.query(sql, new RegisterInfoMapper()).get(0);
			}

		} catch (Exception e) {
			return registerUser;
		}
		return registerUser;
	}

	final class RegisterInfoMapper implements RowMapper<RegisterUser> {
		public RegisterUser mapRow(ResultSet rs, int arg1) throws SQLException {

			RegisterUser registerUser = new RegisterUser();

			registerUser.setAge(rs.getInt("AGE"));
			registerUser.setSex(rs.getInt("SEX"));
			registerUser.setAnswer1(rs.getString("ANSWER1"));
			registerUser.setAnswer2(rs.getString("ANSWER2"));
			registerUser.setAnswer3(rs.getString("ANSWER3"));
			registerUser.setAnswer4(rs.getString("ANSWER4"));
			registerUser.setDob(rs.getString("DOB"));
			registerUser.setEmailId(rs.getString("EMAIL"));
			registerUser.setFirstName(rs.getString("FIRSTNAME"));
			registerUser.setLastName(rs.getString("LASTNAME"));
			registerUser.setLoginType(rs.getInt("LOGINTYPE"));
			registerUser.setUserId(rs.getString("USERID"));
			registerUser.setUserPassword(rs.getString("PASSWORD"));

			return registerUser;
		}
	}

	@Override
	public StatusInfo insertStress(StressAnalysis stressAnalysis) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_STRESS_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { stressAnalysis.getAgeGroup(), stressAnalysis.getAgeGroupDesc(),
							stressAnalysis.getIncomeGroup(), stressAnalysis.getIncomeGroupDesc(),
							stressAnalysis.getProfExpGroup(), stressAnalysis.getProfExpGroupDesc(),
							stressAnalysis.getRegistered(), stressAnalysis.getSex(), stressAnalysis.getSexDesc(),
							stressAnalysis.getStressLabel(), stressAnalysis.getUsername(),
							stressAnalysis.getTotalRating(), stressAnalysis.getTestName() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.VARCHAR });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<ClassificationAgeGroup> retriveClassifyByAge(String ageGroup) {

		List<ClassificationAgeGroup> classficationAgeGroupList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_AGEGRPOUP_CLASSIFY_SQL,
					null, null);
			classficationAgeGroupList = jdbcTemplate.query(sql, new ClassificationQueryMapper(), ageGroup);

		} catch (Exception e) {
			return classficationAgeGroupList;
		}
		return classficationAgeGroupList;
	}

	final class ClassificationQueryMapper implements RowMapper<ClassificationAgeGroup> {
		public ClassificationAgeGroup mapRow(ResultSet rs, int arg1) throws SQLException {
			ClassificationAgeGroup classificationAgeGroup = new ClassificationAgeGroup();

			classificationAgeGroup.setAgeGroup(rs.getString("AGEGROUP"));
			classificationAgeGroup.setNoOfPersons(rs.getInt("NOOFPERSONS"));
			classificationAgeGroup.setStressLabel(rs.getString("STRESSLABEL"));

			return classificationAgeGroup;
		}
	}

	@Override
	public List<ClassificationGroup> retriveClassifyByIncome(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_INCOMEGROUP_CLASSIFY_SQL,
					null, null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperIncome(), group);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	final class ClassificationGroupQueryMapperIncome implements RowMapper<ClassificationGroup> {
		public ClassificationGroup mapRow(ResultSet rs, int arg1) throws SQLException {
			ClassificationGroup classificationAgeGroup = new ClassificationGroup();

			classificationAgeGroup.setGroup(rs.getString("INCOMEGROUP"));
			classificationAgeGroup.setNoOfPersons(rs.getInt("NOOFPERSONS"));
			classificationAgeGroup.setStressLabel(rs.getString("STRESSLABEL"));

			return classificationAgeGroup;
		}
	}

	@Override
	public List<ClassificationGroup> retriveClassifyByExp(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PROFEXP_CLASSIFY_SQL,
					null, null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperProfExp(), group);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	final class ClassificationGroupQueryMapperProfExp implements RowMapper<ClassificationGroup> {
		public ClassificationGroup mapRow(ResultSet rs, int arg1) throws SQLException {
			ClassificationGroup classificationAgeGroup = new ClassificationGroup();

			classificationAgeGroup.setGroup(rs.getString("PROFEXPGROUP"));
			classificationAgeGroup.setNoOfPersons(rs.getInt("NOOFPERSONS"));
			classificationAgeGroup.setStressLabel(rs.getString("STRESSLABEL"));

			return classificationAgeGroup;
		}
	}

	@Override
	public List<ClassificationGroup> retriveClassifyBySex(String group) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_SEX_CLASSIFY_SQL, null,
					null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperSex(), group);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	final class ClassificationGroupQueryMapperSex implements RowMapper<ClassificationGroup> {
		public ClassificationGroup mapRow(ResultSet rs, int arg1) throws SQLException {
			ClassificationGroup classificationAgeGroup = new ClassificationGroup();

			classificationAgeGroup.setGroup(rs.getString("SEX"));
			classificationAgeGroup.setNoOfPersons(rs.getInt("NOOFPERSONS"));
			classificationAgeGroup.setStressLabel(rs.getString("STRESSLABEL"));

			return classificationAgeGroup;
		}
	}

	@Override
	public List<DiabeticsGraph> retriveTotalRatingFromPatNameFromAnalysis(String userId) {
		List<DiabeticsGraph> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TRACKHISTORY_SQL, null,
					null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationDiabeticsGraphMapper(), userId);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	final class ClassificationDiabeticsGraphMapper implements RowMapper<DiabeticsGraph> {
		public DiabeticsGraph mapRow(ResultSet rs, int arg1) throws SQLException {
			DiabeticsGraph stressGraphAnalysis = new DiabeticsGraph();

			stressGraphAnalysis.setPatName(rs.getString("USERNAME"));
			stressGraphAnalysis.setRating(rs.getInt("RATING"));
			stressGraphAnalysis.setTestName(rs.getString("TESTNAME"));

			Timestamp date = rs.getTimestamp("TIMESTAMP");
			if (date != null) {

				stressGraphAnalysis.setTimeStamp(String.valueOf(date));
			} else {
				stressGraphAnalysis.setTimeStamp(String.valueOf(""));
			}
			return stressGraphAnalysis;
		}
	}

	@Override
	public List<Integer> retriveUniqueRowIds() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUE_ROWIDS_SQL, null,
					null);
			Map<String, Object> map = null;
			return namedJdbcTemplate.query(sql, map, new RowIdMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	final class RowIdMapper implements RowMapper<Integer> {

		@Override
		public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getInt("ROWID");
		}
	}

	@Override
	public List<AttributeInformation> reytiveAttributeInfoForRowId(Integer rowIdTemp) {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ATTRIBUTE_FOR_ROWID_SQL,
					null, null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ROWID", rowIdTemp);
			return namedJdbcTemplate.query(sql, map, new DataSetMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	final class DataSetMapper implements RowMapper<AttributeInformation> {

		@Override
		public AttributeInformation mapRow(ResultSet rs, int arg1) throws SQLException {

			AttributeInformation attributeInformation = new AttributeInformation();
			attributeInformation.setAttributeName(rs.getString("ATTRIBUTENAME"));
			attributeInformation.setAttributeValue(rs.getString("ATTRIBUTEVALUE"));
			attributeInformation.setOutputFactor(rs.getString("OUTPUTFACTOR"));
			attributeInformation.setRowId(rs.getInt("ROWID"));

			return attributeInformation;

		}
	}

	@Override
	public List<String> retriveUniqueAttributeList() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUE_ATTRIBUTES_SQL,
					null, null);

			Map<String, Object> map = null;
			return namedJdbcTemplate.query(sql, map, new AttributeMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	final class AttributeMapper implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("ATTRIBUTENAME");
		}

	}

	@Override
	public List<Double> retriveListAttributeInfoWithStatus(String outputFactor, String attributeName) {
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_SQL, null,
					null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("OUTPUTFACTOR", outputFactor);
			map.put("ATTRIBUTENAME", attributeName);
			return namedJdbcTemplate.query(sql, map, new ValueMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	final class ValueMapper implements RowMapper<Double> {

		@Override
		public Double mapRow(ResultSet rs, int arg1) throws SQLException {
			String value = rs.getString("ATTRIBUTEVALUE");
			return new Double(value);
		}

	}

	@Override
	public StatusInfo insertProbability(ProbabilityInfo probabilityInfo) {
		StatusInfo insertUserInfo = null;
		try {
			insertUserInfo = new StatusInfo();

			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_PROBABILITY_INFO_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", probabilityInfo.getUsername());
			paramMap.put("probability", probabilityInfo.getProbability());
			paramMap.put("catName", probabilityInfo.getCatName());
			paramMap.put("negativeProb", probabilityInfo.getNegativeProbability());

			namedJdbcTemplate.update(sql, paramMap);
			insertUserInfo.setStatus(true);
			return insertUserInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertUserInfo = new StatusInfo();
			insertUserInfo.setErrMsg(e.getMessage());
			insertUserInfo.setStatus(false);
			return insertUserInfo;

		}
	}

	@Override
	public List<Double> retriveListAttributeInfoWithStatusForType(String outputFactor, String attributeName,
			String diseaseType) {
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ATTRIBUTE_FOR_OUTPUTFACT_ATTRIBUTENAME_TYPE_SQL, null,
					null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("OUTPUTFACTOR", outputFactor);
			map.put("ATTRIBUTENAME", attributeName);
			map.put("TYPE", diseaseType);
			return namedJdbcTemplate.query(sql, map, new ValueMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveUniqueAttributeListForType(String diseaseType) {
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUE_ATTRIBUTES_FOR_TYPE_SQL, null, null);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TYPE", diseaseType);
			return namedJdbcTemplate.query(sql, map, new AttributeMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllProbabilitiesForUserId(String userId) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.REMOVE_ALL_PROBABILITIES_WHERE_USERID_SQL, null, null);
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("USERNAME", userId);
			namedJdbcTemplate.update(sql, parammap);

			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<ProbabilityInfo> retriveAllProbability() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PROBABILITY_FULL_SQL,
					null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new ProbabilityInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class ProbabilityInfoMapper implements RowMapper<ProbabilityInfo> {

		public ProbabilityInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ProbabilityInfo probabilityInfo = new ProbabilityInfo();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setNegativeProbability(rs.getBigDecimal("NEGATIVEPROBABILITY"));
			probabilityInfo.setProbability(rs.getBigDecimal("PROBABILITY"));
			probabilityInfo.setProbId(rs.getInt("PROBID"));
			probabilityInfo.setUsername(rs.getString("USERID"));

			return probabilityInfo;

		}
	}

	@Override
	public StatusInfo deleteAllContigency() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.REMOVE_ALL_CONTIGENCY_SQL, null,
					null);
			SqlParameterSource parammap = null;
			namedJdbcTemplate.update(sql, parammap);

			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public ContigencyInfo findTotalPosOthersAnsTotalNegativeOthers(String username, String catName) {
		ContigencyInfo contigencyInfo = null;
		try {

			StringBuilder str = new StringBuilder();
			str.append(
					"SELECT SUM(PROBABILITY) AS POSITIVEOTHER ,SUM(NEGATIVEPROBABILITY) AS NEGATIVEOTHER FROM PROBABILITY WHERE USERID=");
			str.append("'");
			str.append(username);
			str.append("'");
			str.append("  AND CATNAME NOT IN('");
			str.append(catName);
			str.append("')");

			Map<String, Object> paramMap = null;

			contigencyInfo = namedJdbcTemplate.query(str.toString(), paramMap, new PartialContigencyMapper()).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return contigencyInfo;
	}

	private final class PartialContigencyMapper implements RowMapper<ContigencyInfo> {

		public ContigencyInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ContigencyInfo webSiteDataVO = new ContigencyInfo();
			webSiteDataVO.setTotalPositiveOthers(rs.getBigDecimal("POSITIVEOTHER"));
			webSiteDataVO.setTotalNegativeOthers(rs.getBigDecimal("NEGATIVEOTHER"));
			return webSiteDataVO;
		}
	}

	@Override
	public StatusInfo insertContigency(ContigencyInfo contigencyInfo) {
		StatusInfo insertUserInfo = null;
		try {
			insertUserInfo = new StatusInfo();

			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_CONTIGENCY_INFO_SQL, null,
					null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", contigencyInfo.getUserName());
			paramMap.put("positiveProbability", contigencyInfo.getProbability());
			paramMap.put("catName", contigencyInfo.getCatName());
			paramMap.put("negativeProbability", contigencyInfo.getNegativeProbability());
			paramMap.put("positiveOther", contigencyInfo.getTotalPositiveOthers());
			paramMap.put("negativeOther", contigencyInfo.getTotalNegativeOthers());

			namedJdbcTemplate.update(sql, paramMap);
			insertUserInfo.setStatus(true);
			return insertUserInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertUserInfo = new StatusInfo();
			insertUserInfo.setErrMsg(e.getMessage());
			insertUserInfo.setStatus(false);
			return insertUserInfo;

		}
	}

	@Override
	public List<ContigencyInfo> retriveAllContigency() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CONTIGENCY_FULL_SQL,
					null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new ContigencyInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class ContigencyInfoMapper implements RowMapper<ContigencyInfo> {

		public ContigencyInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ContigencyInfo probabilityInfo = new ContigencyInfo();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setTotalNegativeOthers(rs.getBigDecimal("TOTALNEGATIVEOTHER"));
			probabilityInfo.setNegativeProbability(rs.getBigDecimal("NEGATIVEPROBABILITY"));
			probabilityInfo.setProbability(rs.getBigDecimal("POSITIVEPROBABILITY"));
			probabilityInfo.setTotalPositiveOthers((rs.getBigDecimal("TOTALPOSITIVEOTHER")));
			probabilityInfo.setUserName(rs.getString("USERID"));

			return probabilityInfo;

		}
	}

	@Override
	public List<EnhanceContigency> retriveAllEnhanceContigency() {
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ENHANCECONTIGENCY_FULL_SQL, null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new EnhanceContigencyMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	// SELECT USERID,POSITIVECATRATIO,OTHERCATRATIO,CATNAME FROM
	// ENHANCEDCONTIGENCY
	private final class EnhanceContigencyMapper implements RowMapper<EnhanceContigency> {

		public EnhanceContigency mapRow(ResultSet rs, int arg1) throws SQLException {
			EnhanceContigency probabilityInfo = new EnhanceContigency();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setPositiveCatRatio((rs.getBigDecimal("POSITIVECATRATIO")));
			probabilityInfo.setOtherCatRatio(rs.getBigDecimal("OTHERCATRATIO"));
			probabilityInfo.setUserName(rs.getString("USERID"));

			return probabilityInfo;

		}
	}

	@Override
	public StatusInfo deleteAllEnhanceContigency() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.REMOVE_ALL_ENHANCECONTIGENCY_SQL,
					null, null);
			SqlParameterSource parammap = null;
			namedJdbcTemplate.update(sql, parammap);

			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo insertEhanceContigency(EnhanceContigency enhanceContigency) {
		StatusInfo insertUserInfo = null;
		try {
			insertUserInfo = new StatusInfo();

			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_ENHANCE_CONTIGENCY_INFO_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", enhanceContigency.getUserName());
			paramMap.put("positive", enhanceContigency.getPositiveCatRatio());
			paramMap.put("other", enhanceContigency.getOtherCatRatio());
			paramMap.put("catName", enhanceContigency.getCatName());
			namedJdbcTemplate.update(sql, paramMap);
			insertUserInfo.setStatus(true);
			return insertUserInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertUserInfo = new StatusInfo();
			insertUserInfo.setErrMsg(e.getMessage());
			insertUserInfo.setStatus(false);
			return insertUserInfo;

		}
	}

	@Override
	public StatusInfo deleteAllClassifier() {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.REMOVE_ALL_CLASSIFIER_SQL, null,
					null);
			SqlParameterSource parammap = null;
			namedJdbcTemplate.update(sql, parammap);

			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveDistinctUsersIdsFromEnhanceContigency() {
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_DISTINCT_USERIDS_FROM_ENHANCECONTGENCY_SQL, null,
					null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<PartialClassifierInfo> retriveClassifierByRank(String userId) {
		List<PartialClassifierInfo> partialClassList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_RANKED_CLASSIFIER_FROM_CONTGENCY_WHERE_USERID_SQL,
					null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);

			partialClassList = namedJdbcTemplate.query(sql, paramMap, new PartialClassifierMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;

		}

		return partialClassList;
	}

	private final class PartialClassifierMapper implements RowMapper<PartialClassifierInfo> {

		public PartialClassifierInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			PartialClassifierInfo probabilityInfo = new PartialClassifierInfo();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setPositiveRatio((rs.getDouble("POSITIVECATRATIO")));
			probabilityInfo.setNegativeRatio(rs.getDouble("OTHERCATRATIO"));
			probabilityInfo.setUserId(rs.getString("USERID"));

			return probabilityInfo;

		}
	}

	@Override
	public StatusInfo insertClassfierInfo(ClassifierInfo ci) {
		StatusInfo insertUserInfo = null;
		try {
			insertUserInfo = new StatusInfo();

			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_CLASSIFIER_INFO_SQL, null,
					null);
			// INSERT INTO classifier(TWEETID,CATNAME) VALUES(:tweetId,:catName)

			Map<String, Object> paramMap = new HashMap<String, Object>();

			paramMap.put("userId", ci.getUserName());
			paramMap.put("catName", ci.getCatName());

			namedJdbcTemplate.update(sql, paramMap);
			insertUserInfo.setStatus(true);
			return insertUserInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertUserInfo = new StatusInfo();
			insertUserInfo.setErrMsg(e.getMessage());
			insertUserInfo.setStatus(false);
			return insertUserInfo;

		}
	}

	@Override
	public List<ClassifierInfo> retriveAllClassifierInfo() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CLASSINFO_FULL_SQL, null,
					null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new ClassifierInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class ClassifierInfoMapper implements RowMapper<ClassifierInfo> {

		public ClassifierInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			ClassifierInfo probabilityInfo = new ClassifierInfo();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setUserName(rs.getString("USERID"));

			return probabilityInfo;

		}
	}

	@Override
	public List<CategoryCountVO> viewClassifierCount() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CLASSIFYCOUNT_FULL_SQL,
					null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new CategoryCountVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class CategoryCountVOMapper implements RowMapper<CategoryCountVO> {

		public CategoryCountVO mapRow(ResultSet rs, int arg1) throws SQLException {
			CategoryCountVO probabilityInfo = new CategoryCountVO();

			probabilityInfo.setCatName(rs.getString("CATNAME"));
			probabilityInfo.setNoOfValues((rs.getInt("COUNTER")));

			return probabilityInfo;

		}
	}

	@Override
	public List<PatientNames> retrivePatientNamesFromAnalysis() {
		List<PatientNames> pateintList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PATIENTLIST_APPOINT_SQL,
					null, null);
			pateintList = jdbcTemplate.query(sql, new PatientNameNewInfoMapper());

		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	final class PatientNameNewInfoMapper implements RowMapper<PatientNames> {
		public PatientNames mapRow(ResultSet rs, int arg1) throws SQLException {
			PatientNames patientInfo = new PatientNames();

			patientInfo.setPatName(rs.getString(PersonalHealtConstantsIF.DATABASECOLUMNS.PATIENTNAME_COL));

			return patientInfo;
		}
	}

	@Override
	public List<String> retriveOnlyPatientNamesFromAnalysis() {
		List<String> pateintList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_DISTINCT_USERNAME_ANALYSIS_SQL, null, null);
			pateintList = jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<String> retriveOnlyUserNamesFromLogin() {
		List<String> pateintList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_DISTINCT_USERIDS_LOGINTYPE_EMAIL_DEFAULT_SQL, null,
					null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("LOGINTYPE", 4);
			paramMap.put("EMAIL", "DEFAULT");

			pateintList = namedJdbcTemplate.query(sql, paramMap, new UserIdMapperLogin());

		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	final class UserIdMapperLogin implements RowMapper<String> {
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("USERID");
		}
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintName(String patName) {
		List<TestInfo> testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USERNAME", patName);

			testNameList = namedJdbcTemplate.query(sql, paramMap, new TestNameInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	// SELECT TESTNAME,TIMESTAMP,RATING,STRESSLABEL FROM ANALYSISDATA WHERE
	// USERNAME=:USERNAME AND TESTNAME=:TESTNAME
	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestId(String patName, String testId) {
		List<AnalysisData> testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_SQL,
					null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USERNAME", patName);
			paramMap.put("TESTNAME", testId);

			testNameList = namedJdbcTemplate.query(sql, paramMap, new AnalysisDataInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	final class AnalysisDataInfoMapper implements RowMapper<AnalysisData> {
		public AnalysisData mapRow(ResultSet rs, int arg1) throws SQLException {
			AnalysisData analysisData = new AnalysisData();

			analysisData.setRating(rs.getInt("RATING"));
			analysisData.setStresslabel(rs.getString("STRESSLABEL"));
			analysisData.setTestname(rs.getString("TESTNAME"));

			Timestamp timestamp = rs.getTimestamp("TIMESTAMP");

			if (timestamp != null) {
				analysisData.setTimeStamp(timestamp.toString());
			}

			return analysisData;
		}
	}

	@Override
	public List<String> retriveUniqueTestNamesDisease() {
		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUETESTS_FROM_TEST_PREDICT_SQL, null, null);

			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}

	@Override
	public List<String> retriveUniqueTestNamesFromQuestionsForUserIdDisease(String userId) {
		List<String> uniqueList = null;
		try {
			uniqueList = new ArrayList<String>();
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_UNIQUETESTS_FROM_ANSWER_FORUSERID_DISEASE_SQL, null,
					null);

			return jdbcTemplate.queryForList(sql, String.class, userId);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return uniqueList;
		}
	}

	@Override
	public List<QuestionVO> retriveQuestionsDisease(String testNameObj) {
		List<QuestionVO> userRoleInfo = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_QUESTIONS_DISEASE_SQL,
					null, null);
			userRoleInfo = jdbcTemplate.query(sql, new QuestionListMapper(), testNameObj);

		} catch (Exception e) {
			return userRoleInfo;
		}
		return userRoleInfo;
	}

	@Override
	public RangeModel retriveRangeModelDisease(String testName) {
		RangeModel rangeModel = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_RANGE_WHERE_TESTNAME_DISEASE_SQL, null, null);
			rangeModel = jdbcTemplate.queryForObject(sql, new RangeMapper(), testName);

		} catch (Exception e) {
			return rangeModel;
		}
		return rangeModel;
	}

	@Override
	public RegisterUser retriveRegisterInfoForPatNameDiseaseName(String patName, String registerOrNot) {
		RegisterUser registerUser = null;
		try {
			String sql = null;
			if ("YES".equals(registerOrNot)) {
				sql = sqlProperties.getMessage(
						PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTERINFO_FOR_USERID_PLAIN_SQL, null, null);
				registerUser = jdbcTemplate.queryForObject(sql, new RegisterInfoMapper(), patName);
			} else {
				sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_REGISTERINFO_FOR_USERID_SQL,
						null, null);
				registerUser = jdbcTemplate.query(sql, new RegisterInfoMapper()).get(0);
			}

		} catch (Exception e) {
			return registerUser;
		}
		return registerUser;
	}

	@Override
	public List<String> retriveTestNamesDisease() {
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMES_DISEASE_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertQuestionDisease(QuestionVO questionVO, String testName) {
		StatusInfo insertQuestionStatus = null;
		try {
			insertQuestionStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_QUESTION_DISEASE_SQL,
					null, null);
			jdbcTemplate.update(sql,
					new Object[] { questionVO.getQuestDesc(), questionVO.getAns1(), questionVO.getAns2(),
							questionVO.getAns3(), questionVO.getAns4(), questionVO.getSug1(), questionVO.getSug2(),
							questionVO.getSug3(), questionVO.getSug4(), testName, questionVO.getRating1(),
							questionVO.getRating2(), questionVO.getRating3(), questionVO.getRating4() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
							Types.INTEGER, Types.INTEGER, Types.INTEGER });
			insertQuestionStatus.setStatus(true);
			return insertQuestionStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertQuestionStatus = new StatusInfo();
			insertQuestionStatus.setErrMsg(e.getMessage());
			insertQuestionStatus.setStatus(false);
			return insertQuestionStatus;

		}
	}

	@Override
	public StatusInfo insertTestDisease(RangeModel rangeModel) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_TEST_DISEASE_SQL, null,
					null);
			jdbcTemplate.update(sql,
					new Object[] { rangeModel.getTestName(), rangeModel.getR1LL(), rangeModel.getR1HL(),
							rangeModel.getR2LL(), rangeModel.getR2HL(), rangeModel.getR3LL(), rangeModel.getR3HL(),
							rangeModel.getR4LL(), rangeModel.getR4HL() },
					new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
							Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease(String loginId) {
		List<AppointVO> appointVOList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_WHERE_DOCNAME_DISEASE_SQL, null, null);
			appointVOList = jdbcTemplate.query(sql, new AppointInfoMapper(), loginId);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return appointVOList;
		}
		return appointVOList;
	}

	@Override
	public StatusInfo deleteAppointmentDisease(Integer appointmentId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.DELETE_APPOINTMENT_DISEASE_SQL,
					null, null);
			jdbcTemplate.update(sql, new Object[] { appointmentId }, new int[] { Types.INTEGER });
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}

	@Override
	public List<TestInfo> retriveTestNamesInTestInfoFormatDisease() {
		List<TestInfo> testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMESLIST_FROM_TEST_DISEASE_SQL, null, null);
			testNameList = jdbcTemplate.query(sql, new TestNameInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	@Override
	public List<PatientNames> retrivePatientNamesFromAnalysisDisease() {
		List<PatientNames> pateintList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PATIENTLIST_APPOINT_DISEASE_SQL, null, null);
			pateintList = jdbcTemplate.query(sql, new PatientNameNewInfoMapper());

		} catch (Exception e) {
			return pateintList;
		}
		return pateintList;
	}

	@Override
	public List<AnalysisData> retriveAnalysisForPatNameAndTestIdDisease(String patName, String testId) {
		List<AnalysisData> testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_ANALYSISLIST_FROM_TEST_WHERE_PATNAME_AND_TESTID_DISEASE_SQL,
					null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USERNAME", patName);
			paramMap.put("TESTNAME", testId);

			testNameList = namedJdbcTemplate.query(sql, paramMap, new AnalysisDataInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	@Override
	public List<TestInfo> retriveTestNamesForPateintNameDisease(String patName) {
		List<TestInfo> testNameList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TESTNAMESLIST_FROM_TEST_WHERE_PATNAME_DISEASE_SQL,
					null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("USERNAME", patName);

			testNameList = namedJdbcTemplate.query(sql, paramMap, new TestNameInfoMapper());

		} catch (Exception e) {
			return testNameList;
		}
		return testNameList;
	}

	@Override
	public List<ClassifierInfo> retriveAllClassifierInfoDisease() {
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CLASSINFO_FULL_DISEASE_SQL, null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new ClassifierInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<CategoryCountVO> viewClassifierCountDisease() {
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_CLASSIFYCOUNT_FULL_DISEASE_SQL, null, null);
			Map<String, Object> paramMap = null;
			return namedJdbcTemplate.query(sql, paramMap, new CategoryCountVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ClassificationAgeGroup> retriveClassifyByAgeDisease(String ageGroup) {
		List<ClassificationAgeGroup> classficationAgeGroupList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_AGEGRPOUP_CLASSIFY_DISEASE_SQL, null, null);
			classficationAgeGroupList = jdbcTemplate.query(sql, new ClassificationQueryMapper(), ageGroup);

		} catch (Exception e) {
			return classficationAgeGroupList;
		}
		return classficationAgeGroupList;
	}

	@Override
	public List<ClassificationGroup> retriveClassifyBySexDisease(String genderGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_SEX_CLASSIFY_DISEASE_SQL,
					null, null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperSex(), genderGroup);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> retriveClassifyByExpDisease(String expGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties
					.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PROFEXP_CLASSIFY_DISEASE_SQL, null, null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperProfExp(), expGroup);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	@Override
	public List<ClassificationGroup> retriveClassifyByIncomeGroupDisease(String incomeGroup) {
		List<ClassificationGroup> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_INCOMEGROUP_CLASSIFY_DISEASE_SQL, null, null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationGroupQueryMapperIncome(), incomeGroup);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	@Override
	public List<AppointVO> retriveAppointmentsDisease() {
		List<AppointVO> appointVOList = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_DISEASE_SQL,
					null, null);
			appointVOList = jdbcTemplate.query(sql, new AppointInfoMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return appointVOList;
		}
		return appointVOList;
	}

	@Override
	public List<AppointVO> retriveAppointmentsForPatNameDisease(String loginId) {
		List<AppointVO> appointVOList = null;
		try {
			String sql = sqlProperties.getMessage(
					PersonalHealtConstantsIF.DATABASESQL.RETRIVE_APPOINT_WHERE_PATNAME_DISEASE_SQL, null, null);
			appointVOList = jdbcTemplate.query(sql, new AppointInfoMapper(), loginId);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return appointVOList;
		}
		return appointVOList;
	}

	@Override
	public List<DiabeticsGraph> retriveTotalRatingFromPatNameFromAnalysisDisease(String userId) {
		List<DiabeticsGraph> classificationGroup = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_TRACKHISTORY_DISEASE_SQL, null,
					null);
			classificationGroup = jdbcTemplate.query(sql, new ClassificationDiabeticsGraphMapper(), userId);

		} catch (Exception e) {
			return classificationGroup;
		}
		return classificationGroup;
	}

	@Override
	public List<String> retrivePatNamesFromAppointmentDisease() {

		List<String> patNames = null;
		try {
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.RETRIVE_PATNAME_APPOINT_DISEASE_SQL,
					null, null);
			patNames = jdbcTemplate.queryForList(sql, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			return null;
		}
		return patNames;
	}

	@Override
	public StatusInfo insertAppointmentDisease(String loginId, int unapprovedKey) {
		StatusInfo insertAppointmentStatus = null;
		try {
			insertAppointmentStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_APPOINT_DISEASE_SQL, null, null);
			jdbcTemplate.update(sql, new Object[] { loginId, unapprovedKey },
					new int[] { Types.VARCHAR, Types.INTEGER });
			insertAppointmentStatus.setStatus(true);
			return insertAppointmentStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertAppointmentStatus = new StatusInfo();
			insertAppointmentStatus.setErrMsg(e.getMessage());
			insertAppointmentStatus.setStatus(false);
			return insertAppointmentStatus;

		}
	}

	@Override
	public StatusInfo insertAnswerDisease(AnswerVO answerVO) {
		StatusInfo insertAnswerStatus = null;
		try {
			insertAnswerStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_ANSWER_DISEASE_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { answerVO.getQuestionVO().getQuesId(), answerVO.getQuestionVO().getQuestDesc(),
							answerVO.getQuestionVO().getAns1(), answerVO.getQuestionVO().getAns2(),
							answerVO.getQuestionVO().getAns3(), answerVO.getQuestionVO().getAns4(),
							answerVO.getQuestionVO().getSug1(), answerVO.getQuestionVO().getSug2(),
							answerVO.getQuestionVO().getSug3(), answerVO.getQuestionVO().getSug4(),
							answerVO.getQuestionVO().getTestName(), answerVO.getQuestionVO().getRating1(),
							answerVO.getQuestionVO().getRating2(), answerVO.getQuestionVO().getRating3(),
							answerVO.getQuestionVO().getRating4(), answerVO.getPatName(), answerVO.getType(),
							answerVO.getQuestionVO().getSelectedAnswer(), answerVO.getTotalRating(),
							answerVO.getCurrentTime() },
					new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.INTEGER, Types.VARCHAR

					});
			insertAnswerStatus.setStatus(true);
			return insertAnswerStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertAnswerStatus = new StatusInfo();
			insertAnswerStatus.setErrMsg(e.getMessage());
			insertAnswerStatus.setStatus(false);
			return insertAnswerStatus;

		}
	}

	@Override
	public StatusInfo insertStressDisease(StressAnalysis stressAnalysis) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.INSERT_STRESS_DISEASE_SQL, null, null);
			jdbcTemplate.update(sql,
					new Object[] { stressAnalysis.getAgeGroup(), stressAnalysis.getAgeGroupDesc(),
							stressAnalysis.getIncomeGroup(), stressAnalysis.getIncomeGroupDesc(),
							stressAnalysis.getProfExpGroup(), stressAnalysis.getProfExpGroupDesc(),
							stressAnalysis.getRegistered(), stressAnalysis.getSex(), stressAnalysis.getSexDesc(),
							stressAnalysis.getStressLabel(), stressAnalysis.getUsername(),
							stressAnalysis.getTotalRating(), stressAnalysis.getTestName() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.INTEGER, Types.VARCHAR });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	@Override
	public StatusInfo approveAppointmentDisease(AppointVO appointmentVOTemp) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			// UPDATE APPOINTMENT SET DATE=?,TIMEIN=?,TIMEOUT=?,DOCNAME=? WHERE
			// APPOINTID=?
			// UPDATE APPOINTMENT SET
			// DATE=?,TIMEIN=?,TIMEOUT=?,DOCNAME=?,STATUS=? WHERE APPOINTID=?
			String sql = sqlProperties.getMessage(PersonalHealtConstantsIF.DATABASESQL.UPDATE_APPOINTMENT_DISEASE_SQL, null,
					null);
			jdbcTemplate.update(sql,
					new Object[] { appointmentVOTemp.getDate(), appointmentVOTemp.getFromTime(),
							appointmentVOTemp.getToTime(), appointmentVOTemp.getDocId(), appointmentVOTemp.getStatus(),
							appointmentVOTemp.getAppointId(), },

					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
							Types.INTEGER });
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
	}
}
