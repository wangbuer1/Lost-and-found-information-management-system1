package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import interceptor.LoginValidator;
import model.*;

import java.util.List;


public class MainController extends Controller {


    public void index() {
        renderFreeMarker("login.ftl");

    }

    public void register() {

        renderFreeMarker("register.ftl");
    }

    public void Stu_Register() {
        String stuName = getPara("stuName");
        String password = getPara("password");
        String gender = getPara("gender");
        String email = getPara("email");
        String tel = getPara("tel");
        String QQ = getPara("QQ");
        String major = getPara("major");
        int stuId = getParaToInt("stuId");

        Student student = new Student();
        student.setStuId(stuId);
        student.setStuName(stuName);
        student.setGender(gender);
        student.setEmail(email);
        student.setQQ(QQ);
        student.setTel(tel);
        student.setMajor(major);
        student.setPassword(password);

        boolean success = false;
        try {
            student.save();
            success = true;
        } catch (Exception e) {
            LogKit.error("用户注册失败，原因是：" + e.getMessage());
        }
        String message = success ? "success" : "failed";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void login() {
        renderFreeMarker("login.ftl");
    }


    @Before(LoginValidator.class)
    public void loginCheck() {
        String username = getPara("username");
        String password = getPara("password");
        String usertype = getPara("usertype");
        boolean success = false;
        int flag = 0;
        if (usertype.equals("1")){
            String sql_stu = Db.getSql("check_login_stu");
            List<Student> students = Student.dao.find(sql_stu, username, password);
            System.out.println("1");
            if (students.size() != 0) {
                System.out.println("2");
                setSessionAttr("user", students.get(0));
                success = true;
                flag = 1;
            } else {

                    setAttr("errmsg", "用户名或密码错误");
                }
        }else if(usertype.equals("2")){
            String check_login_admin = Db.getSql("check_login_admin");
            List<Admin> admin = Admin.dao.find(check_login_admin, username, password);
            System.out.println("size"+admin.size());
            if (admin.size() != 0) {
                setSessionAttr("user", admin.get(0));
                success = true;
                flag = 2;
            } else {
                setAttr("errmsg", "用户名或密码错误");
            }

        }

        String message = success ? "登录成功" : "登录失败,密码或者用户名错误";
        Kv result = Kv.by("success", success).set("message", message).set("flag", flag);
        renderJson(result);
    }

    public void logout() {
        removeSessionAttr("users");
        redirect("/login");
    }


}
