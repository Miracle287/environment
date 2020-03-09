package en.services;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import en.model.Reply;
import en.model.Theme;

import java.util.List;

/**
 * Created by lizhenyu on 2020/3/6
 * description
 */
public class ForumService {
    // 日志对象
    private static final Log log = Log.getLog(ForumService.class);
    // 单例对象
    public final static ForumService me = new ForumService();

    public Theme getThemeById(int id) {
        return Theme.dao.findById(id);
    }

    public List<Theme> getThemeList() {
        return Theme.dao.find("select * from t_theme");
    }

    public List<Theme> getThemeListByKey(String key) {
        return Theme.dao.find("select * from t_theme where theme like '%" + key + "%'");
    }

    public List<Reply> getReplyListByTheme(int themeId) {
        return Reply.dao.find("select * from t_reply where theme=" + themeId);
    }

    public Reply getReplyById(int id) {
        return Reply.dao.findById(id);
    }

    public void insertReply(Reply reply) {
        if (reply != null) {
            reply.save();
        }
    }

    public void insertTheme(Theme theme) {
        if (theme != null) {
            theme.save();
        }
    }

    public void deleteReplyById(int id) {
        Reply reply = getReplyById(id);
        reply.delete();
    }

    public void deleteThemeById(int id) {
        // 先删除该主题的所有回复
        Db.update("delete from t_reply where theme=" + id);
        // 后删除该主题
        Db.update("delete from t_theme where id=" + id);
    }
}
