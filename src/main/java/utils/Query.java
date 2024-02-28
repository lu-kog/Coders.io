package utils;


public class Query {

	public static final String getHashedPasswd = ";";
	
	public static final String findRoleOfUser = ";";

	public static final String setPasswordToUser = ";";

	public static final String joinUserInClanByAdmin = "insert into ClanRelation (clanID, mailID) values(?, ?);";

	public static final String createNewClan = "insert into Clan (clanID, clanName, Admin) values(?, ?, ?);";

	public static final String leftFromClan = "delete from ClanRelation where mailID like ? and clanID like ?;";

	public static final String deleteClanByAdmin = "delete from Clan where clanID like ?;";

	public static final String getClanIDFromMailID = "select clanID from ClanRelation where mailID like ?;";

	public static final String checkAdminOfClanByAdminID = "select * from Clan where clanID like ? and Admin like ?;";

	public static final String checkMemberInAnyClan = "select * from ClanRelation where mailID like ?;";

	public static final String insertClanRequest = "insert into ClanRequest(clanID, mailID) values(?, ?);";

	public static final String AvailInThisClan = "select clanID from ClanRelation where mailID like ? and clanID like ?;";

	public static final String DepromoteAsMember = null;

	public static final String PromoteAsCoAdmin = null;

	public static final String GetAllAttemptedSolutions = "select distinct Q.Q_name, S.Sol, S.status, Q.levelID, L.level_name,Q.Q_ID from Solutions S join Questions Q on S.Q_ID = Q.Q_ID join Levels L on Q.levelID = L.levelID where status like 'ATTEMPTED' and S.mailID like ?;";

	public static final String GetClanDetails = "select U.userName, U.mailID, U.score, mail.role from Users U join(select mailID, role from ClanRelation where clanID like (select C.clanID from ClanRelation CR join Clan C on CR.ClanID = C.ClanID where mailID like 'charu07@gmail.com')) as mail on U.mailID = mail.mailID order by U.score desc;";


	public static final String GetAllRequestsOfClan = null;

	public static final String InsertNewSolution = null;

	public static String deleteClanRequestsByMailID = "delete from ClanRequest where clanID like ? and mailID like ?;";

	public static String checkCoAdminOfaClan = "select role from ClanRelation where mailID like ? and clanID like ? and role like 'CO_ADMIN';";

	public static String getPasswordByMailID = "SELECT passwd FROM Login WHERE mailID like ?;";

	public static String CreateNewSession = "INSERT INTO Session (sessionID, mailID, loggedTime) VALUES (?, ?, ?);";

	public static String DeleteSession = "DELETE FROM Session where sessionID like ?";

	public static String validateSession = "";

	public static String getClanRole = "select role from ClanRelation where mailID like ? and clanID like ?;";

	public static String CreateAccount = "INSERT INTO Users (mailID, userName, Datejoined, Streakdate) VALUES (?, ?, ?, ?);";

	public static String InsertCredentials = "INSERT INTO Login (mailID, passwd) VALUES (?, ?);";

	public static String getLangListFromQuestionID = "select L.lang_name from LanguageRelation R join Languages L on L.l_ID = R.l_ID where Q_ID = ?;";

	public static String getAuthoredQuestionsOfaUser = "select q.Q_ID, q.Q_name, l.level_name from Questions q join Levels l on q.levelID = l.levelID where Author like ?;";

	public static String getAllCompletedSolutionsOfaUser = "select distinct Q.Q_name, S.Sol, S.status, Q.levelID, L.level_name,Q.Q_ID from Solutions S join Questions Q on S.Q_ID = Q.Q_ID join Levels L on Q.levelID = L.levelID where status like 'COMPLETED' and S.mailID like ?;";

	public static String getAllMembersofaClan;

	public static String getCountOfCompletedSolutions;

	public static String getCountOfAuthoredQuestions;

	public static String getCountOfSolutionsForEachLang = "select count(Sol) as solCount from (select s.mailID, s.sol, l.lang_name from Solutions s join Languages l on s.lang_ID = l.l_ID) solutions where mailID like ? and solutions.lang_name like ?;";

	public static String getFullUserData;

	public static String getClanNameByClanID = "select clanName from Clan where clanID like ?;";

	public static String getClanRankOfaMember = "SELECT position FROM ( " +
            "SELECT ROW_NUMBER() OVER (ORDER BY mailID) AS position, mailID " +
            "FROM clanRelation WHERE clanID = ?) AS ranked " +
            "WHERE mailID = ?";

	public static String getClanReqByMemberID = "select mailID from ClanRequest where mailID like ? and clanID like ?;";

	public static String pickAdminFromCoAdmins = "select C.mailID from ClanRelation C join Users U on C.mailID = U.mailID where C.role like 'CO_ADMIN' and clanID like ? order by U.score desc limit 1;";

	public static String pickAdminFromMembers = "select C.mailID from ClanRelation C join Users U on C.mailID = U.mailID where C.role like 'MEMBER' and clanID like ? order by U.score desc limit 1;";

	public static String makeAdminOftheClan = "update Clan set Admin = ? where clanID = ?;";
	
	public static String insertAdminInClanRelation = "insert into ClanRelation (clanID, mailID, role) values(?, ?, 'ADMIN');";

	public static String changeRoleOfMemberInClan = "update ClanRelation set role = ? where clanID = ? and mailID like ?;";
}
