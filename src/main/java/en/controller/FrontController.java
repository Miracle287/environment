package en.controller;

import com.jfinal.core.Controller;
import en.model.News;
import en.model.Slider;
import en.services.FrontService;

import java.util.List;

/**
 * Created by lizhenyu on 2020/3/6
 * description
 */
public class FrontController extends Controller {
    private final static String FRONT_PAGE_DIR = "front/";

    public void index() {
        render(FRONT_PAGE_DIR + "index.html");
    }

    public void zhuye() {
        List<News> newsList = FrontService.me.getNewsList();
        List<Slider> sliderList = FrontService.me.getSliderList();

        setAttr("news", newsList);
        setAttr("sliders", sliderList);

        render(FRONT_PAGE_DIR + "zhuye.html");
    }

    public void hngq() {
        render(FRONT_PAGE_DIR + "hngq.html");
    }

    public void gwxz() {
        render(FRONT_PAGE_DIR + "gwxz.html");
    }

    public void zwwl() {
        render(FRONT_PAGE_DIR + "zwwl.html");
    }

    public void timeline() {
        render(FRONT_PAGE_DIR + "timeline.html");
    }

    public void login() {
        render(FRONT_PAGE_DIR + "login.html");
    }

    public void register() {
        render(FRONT_PAGE_DIR + "register.html");
    }

    public void protect() {
        render(FRONT_PAGE_DIR + "protect.html");
    }

    public void news() {
        int id = getParaToInt("news_id");

        News news = FrontService.me.getNewsById(id);
        setAttr("news", news);
        render(FRONT_PAGE_DIR + "news.html");
    }
}
