package en.services;

import com.jfinal.log.Log;
import en.model.User;
import en.tools.MD5Tool;

public class UserService {

	// 日志对象
	private static final Log log = Log.getLog(UserService.class);
	// 单例对象
	public final static UserService me = new UserService();

	// 登录用户
	public User findUser(String username, String password) {
		String pwd = MD5Tool.md5(password);
 		User user;
		try {
			//对用户名做查询
			String sql = "select * from t_user where username='" + username + "'";
			user = User.dao.findFirst(sql);
			if (user != null && user.getPassword().equals(pwd)) {
				return user;
			}
		} catch (Exception e) {
		}
		return null;
	}

	// 注册用户
	public boolean insertUser(String username, String password) {
		User user;
		String sql = "select * from t_user where username='" + username + "'";
		user = User.dao.findFirst(sql);
		if (user == null) {
			user = new User();
			user.setUsername(username);
			user.setPassword(MD5Tool.md5(password));
			user.save();
			return true;
		}
		return false;
	}
}
