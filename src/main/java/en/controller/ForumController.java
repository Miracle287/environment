package en.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.template.expr.ast.Id;
import en.model.Reply;
import en.model.Theme;
import en.model.User;
import en.services.ForumService;
import en.utils.DateTimeUtil;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by lizhenyu on 2020/3/6
 * description
 */
public class ForumController extends Controller {
    public void index() {
        List<Theme> themeList = ForumService.me.getThemeList();

        setAttr("themes", themeList);
        render("index.html");
    }

    public void search() {
        Ret ret;
        String keyName = getPara("keyName");
        try {
            keyName = new String(keyName.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Theme> themeList = ForumService.me.getThemeListByKey(keyName);
        if (themeList != null) {
            ret = Ret.ok();
            ret.set("result", themeList);
        } else {
            ret = Ret.fail();
        }
        renderJson(ret);
    }

    public void theme() {
        int themeId = getParaToInt("id");

        List<Reply> replyList = ForumService.me.getReplyListByTheme(themeId);
        Theme theme = ForumService.me.getThemeById(themeId);
        setAttr("theme", theme);
        setAttr("replys", replyList);
        render("theme.html");
    }

    public void add_theme() {
        render("add_theme.html");
    }

    public void addTheme() {
        try {
            HttpSession session = getSession();
            User user = (User) session.getAttribute("User");
            Theme theme = new Theme();
            theme.setName(user.getUsername());
            theme.setTheme(getPara("theme"));
            theme.setSex(getPara("sex"));
            theme.setContent(getPara("content"));
            theme.setMood(getPara("mood"));
            theme.setTime(DateTimeUtil.getCurrentDate());
            ForumService.me.insertTheme(theme);
            renderJson(Ret.ok());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Ret.fail());
        }
    }

    public void reply() {
        int themeId = getParaToInt("id");

        Theme theme = ForumService.me.getThemeById(themeId);
        setAttr("theme", theme);
        render("reply.html");
    }

    public void addReply() {
        try {
            HttpSession session = getSession();
            User user = (User) session.getAttribute("User");
            Reply reply = new Reply();
            reply.setName(user.getUsername());
            reply.setReply(getPara("reply"));
            reply.setSex(getPara("sex"));
            reply.setTheme(getParaToInt("themeId"));
            ForumService.me.insertReply(reply);
            renderJson(Ret.ok());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Ret.fail());
        }
    }

    public void manage() {
        int themeId = getParaToInt("id");

        List<Reply> replyList = ForumService.me.getReplyListByTheme(themeId);
        Theme theme = ForumService.me.getThemeById(themeId);
        setAttr("theme", theme);
        setAttr("replys", replyList);
        render("manage.html");
    }

    public void deleteReply() {
        try {
            int replyId = getParaToInt("id");
            ForumService.me.deleteReplyById(replyId);
            renderJson(Ret.ok());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Ret.fail());
        }
    }

    public void deleteTheme() {
        try {
            int themeId = getParaToInt("id");
            ForumService.me.deleteThemeById(themeId);
            renderJson(Ret.ok());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(Ret.fail());
        }
    }
}
