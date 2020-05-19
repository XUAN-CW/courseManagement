package server;

import Database.ConnectWithDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 问题：使用tomcat产生的java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver问题
 * 解决方案：
 *     你可以在web-info文件夹下，创建目录lib，
 *     然后将你需要在服务器上使用的jar包，复制到里面。
 *     在配置项目的依赖，ctrl+alt+shift+s 打开项目设置，
 *     选择Moudles–>右侧Dependencies–>右侧小加号选择1.Jar or Directories
 *     选择你当前项目下的web-info下的lib目录。apply即可
 * 来源：https://blog.csdn.net/MRcheng12138/article/details/104564333
 */

public class Controller {
    HttpServletRequest request;
    HttpServletResponse response;
    ConnectWithDatabase database;
    public Controller(HttpServletRequest request, HttpServletResponse HSResponse){
        setRequest(request);
        setResponse(HSResponse);
        setDatabase("47.102.200.197","root","XUAN","assignment");
        response.addHeader("status","OK");//默认状态码为 OK
    }

    public void parse(){
        String operate=request.getHeader("operate");
        System.out.println(operate);
        if (operate.equals("login")){
            login(request.getHeader("account"),request.getHeader("password"));
        }
        if (operate.equals("signUp")){
            signUp(request.getHeader("account"),request.getHeader("password"),request.getHeader("identity"));
        }
        if(operate.equals("find student assignment")){
            findStudentAssignment(request.getHeader("studentNumber"));
        }
        if(operate.equals("getTeacherAssignment")){
            getTeacherAssignment(request.getHeader("jobNumber"));
        }
        if(operate.equals("getTeacherCourse")){
            getTeacherCourse(request.getHeader("jobNumber"));
        }
        if (operate.equals("assignHomework")){
            assignHomework(request.getHeader("courseNumbers"),request.getHeader("title"),request.getHeader("content"),request.getHeader("deadline"));
        }
        if (operate.equals("updateAssignment")){
            updateAssignment(request.getHeader("assignmentNumber"),request.getHeader("title"),request.getHeader("content"),request.getHeader("deadline"));
        }
        if (operate.equals("joinClass")){
            joinClass(request.getHeader("studentNumber"),request.getHeader("courseNumber"));
        }
        if(operate.equals("createClass")){
            createClass(request.getHeader("courseNumber"),request.getHeader("name"),request.getHeader("jobNumber"));
        }
        if(operate.equals("deleteAssignment")){
            deleteAssignment(request.getHeader("assignmentNumber"));
        }
        if(operate.equals("countTheNumberOfHomework")){
            countTheNumberOfHomework(request.getHeader("studentNumber"));
        }
    }

    private void login(String account,String password){
        //在学生表和教师表中查询账号 account
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(	 "SELECT * FROM (SELECT studentNumber as account," +
                    "`password` FROM students UNION ALL SELECT jobNumber as account," +
                    "`password`  FROM teachers) as a WHERE a.account='"+account+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(account);
            if(null!=rs && isEmptyResultSet(rs)){
                response.addHeader("status","account does not exist");
            } else {
                try {
                    rs.next();
                    if (!password.equals(rs.getString(2))){
                        response.addHeader("status","incorrect password");
                    }else {
                        rs=database.myExecuteQuery("SELECT * FROM students WHERE " +
                                "studentNumber='"+account+"';");
                        database.printResultSet(rs);
                        System.out.println(isEmptyResultSet(rs));
                        if (isEmptyResultSet(rs)){
                            response.addHeader("identity","teacher");
                        }else {
                            response.addHeader("identity","student");
                            System.out.println("student");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    private void signUp(String account,String password,String identity){
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(	 "SELECT * FROM (SELECT studentNumber as account," +
                    "`password` FROM students UNION ALL SELECT jobNumber as account," +
                    "`password`  FROM teachers) as a WHERE a.account='"+account+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(null!=rs&&isEmptyResultSet(rs)){
            if (identity.equals("student")){
                try {
                    database.myExecute("INSERT INTO students(studentNumber,`password`) VALUE('"+account+"','"+password+"');");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (identity.equals("teacher")){
                try {
                    database.myExecute("INSERT INTO teachers(jobNumber,`password`) VALUE('"+account+"','"+password+"');");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            response.addHeader("status","account already exists");
        }
    }

    public void findStudentAssignment(String studentNumber){
        String sql = "SELECT DISTINCT assignmentNumber,`name` AS course,title AS homeworkTitle," +
                "content AS homeworkContent,startTime,deadline FROM assignment JOIN " +
                "studentLearning ON (studentLearning.studentNumber='"+studentNumber+"' AND " +
                "assignment.courseNumber=studentLearning.courseNumber) JOIN " +
                "course ON (course.courseNumber=assignment.courseNumber)ORDER BY " +
                "assignmentNumber ASC;";
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String temp=database.resultSetToString(rs);
        System.out.println(temp);
        response.addHeader("data",temp);
    }

    public void getTeacherAssignment(String jobNumber){
        System.out.println(jobNumber);
        String sql = "SELECT title,content,course.courseNumber,`name`,assignmentNumber,startTime,deadline " +
                "FROM course  JOIN assignment ON (course.courseNumber=assignment.courseNumber and jobNumber='"+jobNumber+"');";
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String temp=database.resultSetToString(rs);
        System.out.println(temp);
        response.addHeader("data",temp);
    }



    public void getTeacherCourse(String jobNumber){
        System.out.println(jobNumber);
        String sql = "SELECT courseNumber FROM course WHERE jobNumber='"+jobNumber+"';";
        String temp="";
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(sql);
            System.out.println(temp);
            while (rs.next()) {
                temp+=rs.getString(1)+database.itemDivider;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(temp);
        response.addHeader("course",temp);
    }


    public void assignHomework( String courseNumbers,  String title,  String content,  String deadline){
        String[] assign=courseNumbers.split(database.itemDivider);
        String sql ="";
        System.out.println("title:"+title);
        System.out.println("content:"+content);

        try{
            database.myExecute("start transaction;");
            for (int i = 0; i < assign.length; i++) {
                sql="INSERT INTO assignment(courseNumber,title,content,startTime,deadline) VALUE " +
                        "('"+assign[i]+"','"+title+"','"+content+"',NOW(),'"+deadline+"');";
                database.myExecute(sql);
            }
            database.myExecute("commit;");

        }catch (Exception e){
            response.setHeader("status","failure");
        }

    }

    public void updateAssignment( String assignmentNumber,  String title,  String content,  String deadline){
        String sql="update assignment set title='"+title+"',content='"+content+"',deadline='"+deadline+"' WHERE assignmentNumber='"+assignmentNumber+"';";
        try{
            System.out.println(sql);
            database.myExecute(sql);
        }catch (Exception e){
            response.setHeader("status","failure");
        }
    }



    public  void joinClass( String studentNumber,  String courseNumber) {
        String sql="INSERT INTO studentLearning(studentNumber,courseNumber) VALUE('"+studentNumber+"', '"+courseNumber+"');";
        try{
            System.out.println(sql);
            database.myExecute(sql);
        }catch (Exception e){
            response.setHeader("status","failure");
        }
    }


    public void createClass(String courseNumber,String name,String jobNumber){
        String sql="INSERT INTO course(jobNumber,`name`,courseNumber) VALUE('"+courseNumber+"', '"+name+"','"+jobNumber+"');";
        try{
            database.myExecute(sql);
        }catch (Exception e){
            response.setHeader("status","failure");
        }
    }

    public void deleteAssignment(String assignmentNumber ){
        String sql="DELETE FROM assignment WHERE assignmentNumber="+assignmentNumber+";";
        try{
            database.myExecute(sql);
        }catch (Exception e){
            response.setHeader("status","failure");
        }
    }

    public void countTheNumberOfHomework(final String studentNumber){
        System.out.println(studentNumber);
        String sql = "SELECT courseNumber,course,COUNT(*) FROM ( SELECT DISTINCT assignmentNumber," +
                "course.courseNumber,`name` AS course,title AS homeworkTitle,content AS homeworkContent," +
                "startTime,deadline FROM assignment JOIN studentLearning ON (studentLearning.studentNumber" +
                "='"+studentNumber+"' AND assignment.courseNumber=studentLearning.courseNumber) JOIN course ON " +
                "(course.courseNumber=assignment.courseNumber)) as aa GROUP BY courseNumber,aa.course;";
        String temp="";
        ResultSet rs = null;
        try {
            rs = database.myExecuteQuery(sql);
            while (rs.next()) {
                temp+=rs.getString(2)+":"+rs.getInt(3)+database.itemDivider;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(temp);
        response.addHeader("statisticalResult",temp);
    }



        /**
         * 结果集的类型要是 ResultSet.TYPE_SCROLL_INSENSITIVE
         * 结果集可滚动，这样才能在调用 rs.next() 后恢复原样
         * @param rs
         * @return 默认为空
         */
    private boolean isEmptyResultSet(ResultSet rs){
        boolean result = true;
        try {
            rs.beforeFirst();
            result=!rs.next();
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setDatabase(String url, String account, String password, String databaseName) {
        this.database = new ConnectWithDatabase(url, account, password, databaseName);
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
