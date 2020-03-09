package en.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import en.model.User;
import en.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 后台管理功能首页
 *
 * @author lzy
 */
public class UserController extends Controller {

	private static final Logger log  = LoggerFactory.getLogger(UserController.class);

	public final static int COOKIE_LIVE_TIMES = 86400;   //cookie存活时间10天

	public void index() {
	}
	
	/**
	 * 登录验证码
	 */
	public static final String VALIDATE_CODE = "LOGIN_VALIDATE_CODE";
	
	/**
	 * 登录处理
	 * 进行用用户名和密码验证
	 */
	public void doLogin(){
		//变量获取与定义
		String username = getPara("username");
		String password = getPara("password");
		Ret ret = Ret.create();
    	//1.参数校验
		if(StrKit.isBlank(username)) {
			ret.put("msg", "用户名不能为空");
			ret.setFail();
		}
		if(StrKit.isBlank(password)) {
			ret.put("msg", "密码不能为空");
			ret.setFail();
		}
		if(ret.isFail()) {
			renderJson(ret);
			return;
		}
		//2.逻辑处理
		User user = UserService.me.findUser(username, password);
		if(user == null) {
			renderJson(ret.fail("msg", "用户名或密码错误"));
			return;
		}
		//2.1登录失败
		if(ret.isFail()) {
			renderJson(ret);
			return;
		}
		//2.2登录成功
		try {
			setCookie("user_id", user.getId() + "", COOKIE_LIVE_TIMES);
			setCookie("user_name", URLEncoder.encode(user.getUsername(), "UTF-8"), COOKIE_LIVE_TIMES);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setSessionAttr("User", user);
		log.info("login success, by user: {}", user);
		ret.put("user", user);
		renderJson(ret);
    }
	
	/**
	 * 用户注销
	 */
    public void logout() {
    	setSessionAttr("User", null);   //清除用户会话
    	redirect("/login");
    }

	/**
	 * 用户提交注册信息
	 */
	public void doRegister() {
		// 变量获取与定义
		String username = getPara("username");
		String password = getPara("password");
		Ret ret = Ret.create();
		// 添加用户
		boolean flag = UserService.me.insertUser(username, password);
		if (flag) {
			renderJson(Ret.ok());
		} else {
			renderJson(ret.fail("msg", "用户名已存在"));
		}
	}

	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		
		//不验证
		if(true){
			return false;
		}
		
		final String loginFailMapKey = "login:fail:map";
		Cache cache = Redis.use();
		int loginFailNum = 0;
		if(cache.hexists("loginFailMap", useruame)){
			loginFailNum = cache.hget(loginFailMapKey, useruame);
		}
		if (isFail){
			loginFailNum++;
			cache.hset(loginFailMapKey, useruame, loginFailNum);
		}
		if (clean){
			cache.hdel(loginFailMapKey, useruame);
		}
		return loginFailNum >= 3;
	}
	
}