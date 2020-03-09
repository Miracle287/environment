package en;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import en.controller.ForumController;
import en.controller.FrontController;
import en.controller.UserController;
import en.model._MappingKit;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lizhenyu on 2020/3/3
 * description
 */

public class DemoConfig extends JFinalConfig {
    public void configConstant(Constants me) {
        Db.use();
        PropKit.use("application.properties");

        me.setDevMode(PropKit.getBoolean("devMode"));
        me.setEncoding("UTF-8");

        //Beetl在Jfinal3.0的集成
        JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
        rf.config();  //需要antlr4支持
        me.setRenderFactory(rf);
        GroupTemplate gt = rf.groupTemplate;  //获得核心模板
        Map<String, Object> shared = new HashMap<String, Object>();
        shared.put("name", "lzy");
        gt.setSharedVars(shared);
    }

    public void configRoute(Routes me) {
        // 设置全局视图层根目录
        me.setBaseViewPath("/WEB-INF/view");
        // 添加路由
        me.add("/", FrontController.class);
        me.add("/forum", ForumController.class);
        me.add("/user", UserController.class);
    }

    public void configEngine(Engine me) {}
    public void configInterceptor(Interceptors me) {}
    public void configHandler(Handlers me) {}

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        // 添加连接池插件配置
        me.add(druidPlugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有对象-关系（ORM）映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        me.add(arp);
        // 配置数据库方言，默认是mysql
        arp.setDialect(new MysqlDialect()); // 支持mysql
        // 添加缓存插件
        me.add(new EhCachePlugin());
    }
}
