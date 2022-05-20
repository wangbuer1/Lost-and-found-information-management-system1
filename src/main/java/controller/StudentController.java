package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.upload.UploadFile;
import interceptor.Login;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

@Before(Login.class)
public class StudentController extends Controller {

    public void SavePersonInfo(){
        String stuName = getPara("stuName");
        Integer stuId = getParaToInt("stuId");
        String password = getPara("password");
        String major = getPara("major");
        String QQ = getPara("QQ");
        String email = getPara("email");
        String tel = getPara("tel");
        String gender = getPara("gender");

        System.out.println("gender:"+gender);

        Db.update("update student set stuName=?,password=?,major=?,QQ=?,email=?,tel=?,gender=? where stuId=?",stuName,password,major,QQ,email,tel,gender,stuId);
        Boolean success = true;
        String message = success ? "修改成功" : "修改失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void modify_personainfo(){
        Student user = getSessionAttr("user");
        setAttr("student",user);
        renderFreeMarker("modify_personainfo.ftl");
    }

    public void personainfo(){

        Student user = getSessionAttr("user");
        Student byId = Student.dao.findById(user.getStuId());
        setAttr("student",byId);
        renderFreeMarker("personainfo.ftl");

    }

    public void BrowseAllExpressThanks(){

        String browseAllExpressThanks = Db.getSql("BrowseAllExpressThanks");

        List<Expressthanks> expressthanks = Expressthanks.dao.find(browseAllExpressThanks);

        setAttr("expressthanks",expressthanks);
        renderFreeMarker("BrowseAllExpressThanks.ftl");

    }

    public void SaveThanks(){
        String title = getPara("title");
        String content = getPara("content");
        Date date = new Date();
        Student user = getSessionAttr("user");

        Expressthanks expressthanks = new Expressthanks();
        expressthanks.setContent(content);
        expressthanks.setTitle(title);
        expressthanks.setStatus(0);
        expressthanks.setLeaveTime(date);
        expressthanks.setStuId(user.getStuId());
        expressthanks.save();
        Boolean success = true;
        String message = success ? "添加成功" : "添加失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void ReleaseThanks(){
        renderFreeMarker("ReleaseThanks.ftl");
    }

    public void query_Lostresult() {

        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("query_Lostresult", thingsType_temp_lost);
        Integer pageNumber = getParaToInt("page", 1);
        Page<Lostthings> page = Lostthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("BrowseAllLostInfo.ftl");
    }


    public static int thingsType_temp_lost;

    public void queryLost() {
        Boolean success = true;
        thingsType_temp_lost = getParaToInt("thingsType");

        String message = success ? "成功" : "失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public void BrowseAllLostInfo() {
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("BrowseAllLostInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Lostthings> page = Lostthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("BrowseAllLostInfo.ftl");
    }


    public void StuApply() {
        Integer paraToInt = getParaToInt(0, -1);

        Student user = getSessionAttr("user");

        Application application = new Application();
        application.setPickId(paraToInt);
        application.setStuId(user.getStuId());

        Date date = new Date();
        application.setApplyTime(date);
        application.save();


        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);


        setAttr("para",paraToInt);

        SqlPara sqlPara = Db.getSqlPara("BrowseAllPickInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Pickthings> page = Pickthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("BrowseAllPickInfo.ftl");

    }

    public void query_result() {

        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        setAttr("para",-1);

        SqlPara sqlPara = Db.getSqlPara("query_result", thingsType_temp);
        Integer pageNumber = getParaToInt("page", 1);
        Page<Pickthings> page = Pickthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);

        renderFreeMarker("BrowseAllPickInfo.ftl");
    }


    public static int thingsType_temp;

    public void query() {

        Boolean success = true;
        thingsType_temp = getParaToInt("thingsType");

        String message = success ? "成功" : "失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }

    public void BrowseAllPickInfo() {
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        SqlPara sqlPara = Db.getSqlPara("BrowseAllPickInfo");
        Integer pageNumber = getParaToInt("page", 1);
        Page<Pickthings> page = Pickthings.dao.paginate(pageNumber, 10, sqlPara);
        setAttr("page", page);
        setAttr("para",-1);
//        String browseAllPickInfo = Db.getSql("BrowseAllPickInfo");
//        List<Pickthings> pickthings = Pickthings.dao.find(browseAllPickInfo);
//        setAttr("pickthings",pickthings);
        renderFreeMarker("BrowseAllPickInfo.ftl");

    }

    public void FinishPickInfoImg() {
        Integer paraToInt = getParaToInt(0, -1);
        Db.update("update pickthings set Status=1 where id=?", paraToInt);
        redirect("/student/BrowseMyPickInfo");
    }

    public void PassPickInfo() {

        Integer paraToInt = getParaToInt(0, -1);
        String passPickInfo = Db.getSql("PassPickInfo");
        List<Application> applications = Application.dao.find(passPickInfo, paraToInt);
        setAttr("application", applications);
        renderFreeMarker("PassPickInfo.ftl");

    }

    public void uploadForPick() {

        Boolean success = false;
        UploadFile upload = this.getFile();
        String fileName = upload.getOriginalFileName();
//      UploadFile upFile = getFile("file", "", maxSize, "utf-8");// maxsize限制上传文件的大小，也可以在配置文件中设置

        File file = upload.getFile();
        String contentType = upload.getContentType();


        String webRootPath = PathKit.getWebRootPath();//得到web路径

        PropKit.use("myconfig.properties");//从配置文件中读取保存路径
//            String saveFilePathforimage = PropKit.get("saveFilePathforimage");
        String saveFilePathforimage = webRootPath + "\\template\\img\\";

        String filename = file.getName();

        String savaFileName = filename;

        System.out.println("保存路径=" + saveFilePathforimage);
        String saveNme = saveFilePathforimage + savaFileName;

        String mysql_save_Path = "/template/img/" + savaFileName;


        File Direction = new File(saveFilePathforimage);
        //判断文件夹是否存在 如果不存在 就创建文件夹
        if (!Direction.exists()) {
            Direction.mkdirs();
        }

        File t = new File(saveNme);
        try {
            t.createNewFile();
            //D:\2018.6.9\src\main\webapp\img\aef5b341-3cdb-4331-b2f5-8d6fa99ce4e7
            Db.update("update pickthings set thingsImg =? where id=? ", mysql_save_Path, tempvarforPick);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogKit.error("上传失败，原因是：" + e.getMessage());
        }
        fileChannelCopy(file, t);
        file.delete();


        String message = success ? "上传成功" : "上传失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }


    public static Integer tempvarforPick = -1;

    public void UploadPickInfoImg() {
        tempvarforPick = getParaToInt(0, -1);
        renderFreeMarker("upload_img_Pick.ftl");
    }


    public void DoModifyPickInfo() {
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

    public void ModifyPickInfo() {
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        Integer paraToInt = getParaToInt(0, -1);
        List<Pickthings> pickthing = Pickthings.dao.find("select * from pickthings where id=?", paraToInt);
        setAttr("pickthing", pickthing.get(0));

        renderFreeMarker("ModifyPickInfo.ftl");
    }

    public void DeleltePickInfo() {
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("DELETE FROM pickthings where id=?", paraToInt);
        redirect("/student/BrowseMyPickInfo");
    }

    public void BrowseMyPickInfo() {

        String ListMyPickInfo = Db.getSql("ListMyPickInfo");
        Student user = getSessionAttr("user");
        List<Pickthings> pickthings = Pickthings.dao.find(ListMyPickInfo, user.getStuId());

        setAttr("pickthings", pickthings);

        renderFreeMarker("BrowseMyPickInfo.ftl");

    }


    public void SavePickInfo() {
        String thingsName = getPara("thingsName");
        String thingsDetail = getPara("thingsDetail");
        String pickPlace = getPara("pickPlace");
        String storagePlace = getPara("storagePlace");
        Date pickTime = getParaToDate("pickTime");
        Integer thingsType = getParaToInt("thingsType");
        Student user = getSessionAttr("user");
        Integer stuId = user.getStuId();
        Date date = new Date();

        Pickthings pickthings = new Pickthings();
        pickthings.setPickPlace(pickPlace);
        pickthings.setStatus(0);
        pickthings.setStoragePlace(storagePlace);
        pickthings.setPickPlace(pickPlace);
        pickthings.setPickTime(pickTime);
        pickthings.setThingsDetail(thingsDetail);
        pickthings.setThingsName(thingsName);
        pickthings.setThingsType(thingsType);
        pickthings.setPublishTime(date);
        pickthings.setStuId(stuId);
        pickthings.setThingsImg("null");
        pickthings.save();

        Boolean success = true;
        String message = success ? "发布成功" : "发布失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);


    }

    public void upload() {


        Boolean success = false;
        UploadFile upload = this.getFile();
        String fileName = upload.getOriginalFileName();
//      UploadFile upFile = getFile("file", "", maxSize, "utf-8");// maxsize限制上传文件的大小，也可以在配置文件中设置

        File file = upload.getFile();
        String contentType = upload.getContentType();


        String webRootPath = PathKit.getWebRootPath();//得到web路径

        PropKit.use("myconfig.properties");//从配置文件中读取保存路径
//            String saveFilePathforimage = PropKit.get("saveFilePathforimage");
        String saveFilePathforimage = webRootPath + "\\template\\img\\";

        String filename = file.getName();

        String savaFileName = filename;

        System.out.println("保存路径=" + saveFilePathforimage);
        String saveNme = saveFilePathforimage + savaFileName;

        String mysql_save_Path = "/template/img/" + savaFileName;


        File Direction = new File(saveFilePathforimage);
        //判断文件夹是否存在 如果不存在 就创建文件夹
        if (!Direction.exists()) {
            Direction.mkdirs();
        }

        File t = new File(saveNme);
        try {
            t.createNewFile();
            //D:\2018.6.9\src\main\webapp\img\aef5b341-3cdb-4331-b2f5-8d6fa99ce4e7
            Db.update("update lostthings set thingsImg =? where id=? ", mysql_save_Path, tempvar);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            LogKit.error("上传失败，原因是：" + e.getMessage());
        }
        fileChannelCopy(file, t);
        file.delete();


        String message = success ? "上传成功" : "上传失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);
    }

    public static Integer tempvar = -1;

    public void UploadLostInfoImg() {
        tempvar = getParaToInt(0, -1);
        renderFreeMarker("upload_img.ftl");
    }

    public void DoModifyLostInfo() {
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


    public void ModifyLostInfo() {

        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);

        Integer paraToInt = getParaToInt(0, -1);
        List<Lostthings> lostthings = Lostthings.dao.find("select * from lostthings where id=?", paraToInt);
        setAttr("lostthing", lostthings.get(0));

        renderFreeMarker("ModifyLostInfo.ftl");

    }

    public void PassLostInfo() {
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("update  lostthings set status =1 where id=?", paraToInt);
        redirect("/student/BrowseMyLostInfo");
    }


    public void DelelteLostInfo() {
        Integer paraToInt = getParaToInt(0, -1);
        Db.delete("DELETE FROM lostthings where id=?", paraToInt);
        redirect("/student/BrowseMyLostInfo");
    }


    public void BrowseMyLostInfo() {
        String ListMyLostInfo = Db.getSql("ListMyLostInfo");
        Student user = getSessionAttr("user");
        List<Lostthings> lostthings = Lostthings.dao.find(ListMyLostInfo, user.getStuId());

        setAttr("lostthings", lostthings);
        renderFreeMarker("BrowseMyLostInfo.ftl");

    }


    public void SaveLostInfo() {
        String thingsName = getPara("thingsName");
        String lostPlace = getPara("lostPlace");
        Date lostTime = getParaToDate("lostTime");
        Integer thingsType = getParaToInt("thingsType", 1);
        String thingsDetail = getPara("thingsDetail");

        Student user = getSessionAttr("user");
        Integer stuId = user.getStuId();
        Date date = new Date();

        Lostthings lostthings = new Lostthings();
        lostthings.setLostPlace(lostPlace);
        lostthings.setLostTime(lostTime);
        lostthings.setStatus(0);
        lostthings.setThingsDetail(thingsDetail);
        lostthings.setThingsType(thingsType);
        lostthings.setThingsName(thingsName);
        lostthings.setStuId(stuId);
        lostthings.setPublishTime(date);
        lostthings.setThingsImg("null");
        lostthings.save();
        Boolean success = true;
        String message = success ? "发布成功" : "发布失败";
        Kv result = Kv.by("success", success).set("message", message);
        renderJson(result);

    }

    public void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void ReleaseLostInfo() {
        List<Type> types = Type.dao.find("SELECT * from type");
        setAttr("types", types);
        renderFreeMarker("ReleaseLostInfo.ftl");
    }

    public void ReleasePickInfo() {
        List<Type> types = Type.dao.find("SELECT * from type");

        setAttr("types", types);

        renderFreeMarker("ReleasePickInfo.ftl");
    }

    public void LostthingDetail() {

        Integer para = getParaToInt(0, -1);

        String LostthingDetail = Db.getSql("LostthingDetail");

        List<Lostthings> lostthings = Lostthings.dao.find(LostthingDetail, para);
        setAttr("lostthing", lostthings.get(0));

        renderFreeMarker("LostthingDetail.ftl");


    }

    public void Claim() {

        Integer para = getParaToInt(0, -1);

        Db.update("UPDATE pickthings set `Status`=1 where id= ? ", para);

        String pickthingDetail = Db.getSql("PickthingDetail");
        List<Pickthings> pickthings = Pickthings.dao.find(pickthingDetail, para);

        setAttr("pickthing", pickthings.get(0));

        renderFreeMarker("PickthingDetail.ftl");

    }


    public void PickthingDetail() {

        Integer para = getParaToInt(0, -1);

        String pickthingDetail = Db.getSql("PickthingDetail");

        List<Pickthings> pickthings = Pickthings.dao.find(pickthingDetail, para);
        setAttr("pickthing", pickthings.get(0));

        renderFreeMarker("PickthingDetail.ftl");


    }

    public void mainPageForLostInfo() {
        String mainPageForNotice = Db.getSql("mainPageForNotice");
        List<Notice> notices = Notice.dao.find(mainPageForNotice);
        setAttr("notice", notices.get(0));

        String mainPageForLostInfo = Db.getSql("mainPageForLostInfo");
        List<Lostthings> lostthings = Lostthings.dao.find(mainPageForLostInfo);
        setAttr("lostthings", lostthings);
        int flag = 2;
        setAttr("flag", flag);
        renderFreeMarker("mainPage.ftl");
    }

    public void mainPage() {

        String mainPageForNotice = Db.getSql("mainPageForNotice");
        List<Notice> notices = Notice.dao.find(mainPageForNotice);
        setAttr("notice", notices.get(0));

        String mainPageForPickInfo = Db.getSql("mainPageForPickInfo");
        List<Pickthings> pickthings = Pickthings.dao.find(mainPageForPickInfo);
        setAttr("pickthings", pickthings);
        int flag = 1;
        setAttr("flag", flag);
        renderFreeMarker("mainPage.ftl");
    }

    public void index() {

        String mainPageForNotice = Db.getSql("mainPageForNotice");
        List<Notice> notices = Notice.dao.find(mainPageForNotice);
        setAttr("notice", notices.get(0));

        String mainPageForPickInfo = Db.getSql("mainPageForPickInfo");
        List<Pickthings> pickthings = Pickthings.dao.find(mainPageForPickInfo);
        setAttr("pickthings", pickthings);
        int flag = 1;
        setAttr("flag", flag);
        renderFreeMarker("mainPage.ftl");
    }

    public void logout() {
        removeSessionAttr("user");
        redirect("/login");
    }

}
