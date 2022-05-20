package model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePickthings<M extends BasePickthings<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setThingsName(java.lang.String thingsName) {
		set("thingsName", thingsName);
		return (M)this;
	}
	
	public java.lang.String getThingsName() {
		return getStr("thingsName");
	}

	public M setPickPlace(java.lang.String pickPlace) {
		set("pickPlace", pickPlace);
		return (M)this;
	}
	
	public java.lang.String getPickPlace() {
		return getStr("pickPlace");
	}

	public M setPickTime(java.util.Date pickTime) {
		set("pickTime", pickTime);
		return (M)this;
	}
	
	public java.util.Date getPickTime() {
		return get("pickTime");
	}

	public M setThingsType(java.lang.Integer thingsType) {
		set("thingsType", thingsType);
		return (M)this;
	}
	
	public java.lang.Integer getThingsType() {
		return getInt("thingsType");
	}

	public M setThingsImg(java.lang.String thingsImg) {
		set("thingsImg", thingsImg);
		return (M)this;
	}
	
	public java.lang.String getThingsImg() {
		return getStr("thingsImg");
	}

	public M setThingsDetail(java.lang.String thingsDetail) {
		set("thingsDetail", thingsDetail);
		return (M)this;
	}
	
	public java.lang.String getThingsDetail() {
		return getStr("thingsDetail");
	}

	public M setStoragePlace(java.lang.String storagePlace) {
		set("storagePlace", storagePlace);
		return (M)this;
	}
	
	public java.lang.String getStoragePlace() {
		return getStr("storagePlace");
	}

	public M setPublishTime(java.util.Date publishTime) {
		set("publishTime", publishTime);
		return (M)this;
	}
	
	public java.util.Date getPublishTime() {
		return get("publishTime");
	}

	public M setStatus(java.lang.Integer Status) {
		set("Status", Status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("Status");
	}

	public M setStuId(java.lang.Integer stuId) {
		set("stuId", stuId);
		return (M)this;
	}
	
	public java.lang.Integer getStuId() {
		return getInt("stuId");
	}

}
