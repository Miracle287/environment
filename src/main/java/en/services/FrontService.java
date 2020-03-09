package en.services;

import com.jfinal.log.Log;
import en.model.News;
import en.model.Slider;

import java.util.List;

/**
 * Created by lizhenyu on 2020/3/6
 * description
 */
public class FrontService {
    // 日志对象
    private static final Log log = Log.getLog(FrontService.class);
    // 单例对象
    public final static FrontService me = new FrontService();

    public News getNewsById(int id) {
        return News.dao.findById(id);
    }

    public List<News> getNewsList() {
        return News.dao.find("select * from t_news");
    }

    public List<Slider> getSliderList() {
        return Slider.dao.find("select * from t_slider");
    }
}
