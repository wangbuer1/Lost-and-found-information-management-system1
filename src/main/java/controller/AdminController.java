package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import interceptor.Login;
import model.*;

import java.util.Date;
import java.util.List;

@Before(Login.class)
public class AdminController extends Controller {

    public void BrowseNoticeInfo(){

        SqlPara sqlPara = Db.getSqlPara("BrowseNoticeInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Notice> page = Notice.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("BrowseNoticeInfo.ftl");

    }

    public void DoReleaseNotice(){
        String title = getPara("title");
        String content = getPara("content");
        Date date = new Date();
        Notice notice = new Notice();
        notice.setContent(content);
        notice.setPublishTime(date);
        notice.setTitle(title);
        notice.setStatus(0);
        notice.save();
        Boolean success=true;
        String message = success ? "发布成功" : "发布失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void ReleaseNotice(){
        renderFreeMarker("ReleaseNotice.ftl");
    }

    public void adminDoModifyPersonInfo(){
        Integer id = getParaToInt("id");
        String name = getPara("name");
        String password = getPara("password");
        String tel = getPara("tel");
        String qq = getPara("qq");
        String email = getPara("email");

        boolean success = false;
        try {
//            admin.save();
            Db.update("update admin set email=?,qq=?,tel=?,name=?,password=? where id=?",email,qq,tel,name,password,id);
            success = true;
        } catch (Exception e) {
            LogKit.error("用户修改失败，原因是：" + e.getMessage());
        }
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }

    public void ModifyPersonInfo(){
        Admin user = getSessionAttr("user");
        Admin admin = Admin.dao.findById(user.getId());
        setAttr("admin",admin);
        renderFreeMarker("ModifyPersonAdminInfo.ftl");
    }

    public void BrowsePersonInfo(){
        Admin user = getSessionAttr("user");
        Admin admin = Admin.dao.findById(user.getId());
        setAttr("admin",admin);
        renderFreeMarker("BrowsePersonAdminInfo.ftl");


    }

    public void adminDoAddAdminInfo(){
        Integer id = getParaToInt("id");
        String name = getPara("name");
        String password = getPara("password");
        String tel = getPara("tel");
        String qq = getPara("qq");
        String email = getPara("email");

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setId(id);
        admin.setName(name);
        admin.setPassword(password);
        admin.setQq(qq);
        admin.setTel(tel);
        boolean success = false;
        try {
            admin.save();
            success = true;
        } catch (Exception e) {
            LogKit.error("用户添加失败，原因是：" + e.getMessage());
        }
        String message = success ? "添加成功" : "添加失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void AddAdminInfo(){
        renderFreeMarker("AddAdminInfo.ftl");

    }

    public void adminDoModifyAdminInfo(){
        Integer id = getParaToInt("id");
        String name = getPara("name");
        String password = getPara("password");
        String tel = getPara("tel");
        String qq = getPara("qq");
        String email = getPara("email");

//        Admin admin = new Admin();
//        admin.setEmail(email);
//        admin.setId(id);
//        admin.setName(name);
//        admin.setPassword(password);
//        admin.setQq(qq);
//        admin.setTel(tel);
        boolean success = false;
        try {
//            admin.save();
            Db.update("update admin set email=?,qq=?,tel=?,name=?,password=? where id=?",email,qq,tel,name,password,id);
            success = true;
        } catch (Exception e) {
            LogKit.error("用户修改失败，原因是：" + e.getMessage());
        }
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);



    }

    public void AdminEditAdminInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Admin admin = Admin.dao.findById(paraToInt);
        setAttr("admin",admin);

        renderFreeMarker("AdminEditAdminInfo.ftl");
    }

    public void AdminDeleteAdminInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from admin where id =?",paraToInt);
        redirect("/admin/BrowseAllAdminInfo");
    }

    public void BrowseAllAdminInfo(){
        SqlPara sqlPara = Db.getSqlPara("BrowseAllAdminInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Admin> page = Admin.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("Alladmins.ftl");
    }


    public void Admin_Stu_Register(){
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
            LogKit.error("用户添加失败，原因是：" + e.getMessage());
        }
        String message = success ? "添加成功" : "添加失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void AddStudentInfo(){
        renderFreeMarker("AdminAddStudentInfo.ftl");
    }

    public void adminDoModifyStuInfo(){
        String stuName = getPara("stuName");
        Integer stuId = getParaToInt("stuId");
        String qq = getPara("QQ");
        String email = getPara("email");
        String tel = getPara("tel");
        String gender = getPara("gender");
        String major = getPara("major");
        String password = getPara("password");

        Db.update("update student set stuName=?,QQ=?,email=?,tel=?,gender=?,major=?,password=? where stuId=?",stuName,qq,email,tel,gender,major,password,stuId);
        Boolean success = true;
        String message = success ? "保存成功" : "保存失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }
    public void AdminEditStuInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Student stu = Student.dao.findById(paraToInt);
        setAttr("student",stu);

        renderFreeMarker("AdminEditStuInfo.ftl");

    }
    public void AdminDeleteStuInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from student where stuId =?",paraToInt);
        redirect("/admin/BrowseAllStudentInfo");
    }
    public void admin_query_stu_result(){

        Integer pageNumber = getParaToInt("page", 1);
        Page<Student> page = Student.dao.paginate(pageNumber, 10, select, stu_queryStu);
        setAttr("page", page);
        renderFreeMarker("Allstudents.ftl");
    }

    public static String select;
    public static String stu_queryStu;

    public void admin_query_stu(){
        Integer stuId = getParaToInt("stuId", -1);
        String stuName = getPara("stuName", "null");
        String major = getPara("major", "null");

        select = "select * ";
        stu_queryStu = " from student ";

        if (stuId != -1) {
            stu_queryStu = stu_queryStu + " where stuId = " + stuId;
        }
        if (!stuName.equals("null")) {
            if (stuId != -1) {
                stu_queryStu = stu_queryStu + " and stuName = \"" + stuName + "\"";
            } else {
                stu_queryStu = stu_queryStu + "where stuName = \"" + stuName + "\"";
            }
        }

        if (!major.equals("null")) {
            if ((stuId == -1) & (stuName.equals("null"))) {
                stu_queryStu = stu_queryStu + " where major = \"" + major + "\" ";
            } else {
                stu_queryStu = stu_queryStu + " and major = \"" + major + "\" ";
            }
        }
        System.out.println("sql查询语句为=" + stu_queryStu);

        boolean success = false;
        try {
            success = true;
        } catch (Exception e) {
            LogKit.error("查询失败，原因是：" + e.getMessage());
        }
        String message = success ? "查询成功" : "查询失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }

    public void BrowseAllStudentInfo(){
        SqlPara sqlPara = Db.getSqlPara("BrowseAllStudentInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Student> page = Student.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("Allstudents.ftl");
    }

    public void adminDoSaveTypeInfo(){
        String type = getPara("type");
        Type type1 = new Type();
        type1.setType(type);
        type1.save();
        Boolean success = true;
        String message = success ? "保存成功" : "保存失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void AddTypeInfo(){

        renderFreeMarker("AddTypeInfo.ftl");

    }


    public void adminDoEditTypeInfo(){
        String id = getPara("id");
        String type = getPara("type");
        Db.update("update type set type=? where id =?",type,id);
        Boolean success = true;
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void AdminEditTypeInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        List<Type> types = Type.dao.find("select * from type where id= ?", paraToInt);
        setAttr("type",types.get(0));
        renderFreeMarker("AdminEditTypeInfo.ftl");
    }

    public void BrowseAllTypeInfo(){
        List<Type> types = Type.dao.find("select * from type");
        setAttr("types",types);

        renderFreeMarker("AllType.ftl");
    }

    public void AdminDeleteApplicationInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from application where id=?",paraToInt);
        redirect("/admin/ManageApplyInfo");
    }

    public void ManageApplyInfo(){
        SqlPara sqlPara = Db.getSqlPara("ManageApplyInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Application> page = Application.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllApplication.ftl");
    }

    public void AdminDeleteExpressthanksInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from expressthanks where id=?",paraToInt);
        redirect("/admin/ManageThanksInfo");
    }

    public void ManageThanksInfo(){

        SqlPara sqlPara = Db.getSqlPara("ManageThanksInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Expressthanks> page = Expressthanks.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllExpressthanks.ftl");
    }

    public void adminDoModifyLostInfo(){
        Integer id = getParaToInt("id");
        String thingsName = getPara("thingsName");
        String thingsDetail = getPara("thingsDetail");
        String lostTime = getPara("lostTime");
        String lostPlace = getPara("lostPlace");
        Integer thingsType = getParaToInt("thingsType");

        Db.update("update lostthings set thingsName=? ,thingsDetail=?,lostTime=?,lostPlace=?,thingsType=? where id=?", thingsName, thingsDetail, lostTime, lostPlace, thingsType, id);

        Boolean success = true;
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void AdminEditLostInfo(){
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        Integer paraToInt = getParaToInt(0, -1);
        List<Lostthings> lostthings = Lostthings.dao.find("select * from lostthings where id=?", paraToInt);
        setAttr("lostthing", lostthings.get(0));

        renderFreeMarker("AdminModifyLostInfo.ftl");
    }

    public void AdminDeleteLostInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from lostthings where id=?",paraToInt);
        redirect("/admin/ManageLostInfo");
    }

    public void adminquery_Lostresult(){
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("query_Lostresult", Admin_thingsType_temp_lost);
        Integer pageNumber = getParaToInt("page", 1);
        Page<Lostthings> page = Lostthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllLostInfo.ftl");
    }


    public static int Admin_thingsType_temp_lost;

    public void adminqueryLost(){
        Boolean success = true;
        Admin_thingsType_temp_lost = getParaToInt("thingsType");

        String message = success ? "成功" : "失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void ManageLostInfo(){
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("BrowseAllLostInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Lostthings> page = Lostthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllLostInfo.ftl");
    }

    public void adminDoModifyPickInfo(){
        Integer id = getParaToInt("id");
        String thingsName = getPara("thingsName");
        String thingsDetail = getPara("thingsDetail");
        String pickTime = getPara("pickTime");
        String pickPlace = getPara("pickPlace");
        String storagePlace = getPara("storagePlace");
        Integer thingsType = getParaToInt("thingsType");

        Db.update("update pickthings set thingsName=? ,thingsDetail=?,pickTime=?,pickPlace=?,thingsType=? ,storagePlace=? where id=?", thingsName, thingsDetail, pickTime, pickPlace, thingsType, storagePlace, id);

        Boolean success = true;
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }

    public void AdminDeletePickInfo(){
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("delete from pickthings where id=?",paraToInt);
        redirect("/admin/ManagePickInfo");

    }


    public void AdminEditPickInfo(){
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        Integer paraToInt = getParaToInt(0, -1);
        List<Pickthings> pickthing = Pickthings.dao.find("select * from pickthings where id=?", paraToInt);
        setAttr("pickthing", pickthing.get(0));

        renderFreeMarker("AdminModifyPickInfo.ftl");



    }

    public void AdminQueryPickInfoResult() {

        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("query_result", admin_thingsType_temp);
        Integer pageNumber = getParaToInt("page", 1);
        Page<Pickthings> page = Pickthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllPickInfo.ftl");
    }


    public static int admin_thingsType_temp;
    public void AdminQueryPickInfo(){
        Boolean success = true;
        admin_thingsType_temp = getParaToInt("thingsType",1);

        String message = success ? "成功" : "失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void ManagePickInfo(){
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("BrowseAllPickInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Pickthings> page = Pickthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("AdminBrowseAllPickInfo.ftl");
    }

    public void mainPage() {
        renderFreeMarker("mainPage.ftl");
    }

    public void index() {


        renderFreeMarker("mainPage.ftl");
    }

    public void logout() {
        removeSessionAttr("user");
        redirect("/login");
    }
}
