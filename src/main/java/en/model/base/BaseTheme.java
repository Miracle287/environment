package en.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTheme<M extends BaseTheme<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setTheme(java.lang.String theme) {
		set("theme", theme);
		return (M)this;
	}

	public java.lang.String getTheme() {
		return get("theme");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return get("name");
	}

	public M setTime(java.util.Date time) {
		set("time", time);
		return (M)this;
	}

	public java.util.Date getTime() {
		return get("time");
	}

	public M setSex(java.lang.String sex) {
		set("sex", sex);
		return (M)this;
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public M setMood(java.lang.String mood) {
		set("mood", mood);
		return (M)this;
	}

	public java.lang.String getMood() {
		return get("mood");
	}

}
