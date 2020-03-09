package en.model;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import en.DemoConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *  demo 仅表达最为粗浅的 jfinal 用法，更为有价的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * 在数据库表有任何变动时，运行 main 方法，极速响应变化进行代码重
 */
public class _JFinalDemoGenerator {
	
	public static DataSource getDataSource() {
		PropKit.use("application.properties");
		DruidPlugin druidPlugin = DemoConfig.createDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// base model 使用的包
		String baseModelPackageName = "en.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/en/model/base";
		
		// model 使用的包 (MappingKit 默认使用的包)
		String modelPackageName = "en.model";
		// model 文件保存路径 (MappingKit 和 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成
		Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 添加不需要生成的表名
		generator.addExcludedTable(getExcTab("exclude_"));
		// 设置是否 Model 中生 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(true);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(true);
		// 设置要被移除的表名前用于生成modelName。例如 "osc_user"，移除前 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes("t_");
		// 生成
		generator.generate();
	}
	
	private static String[] getExcTab(String preName) {
		String sql = "SELECT table_name from information_schema.tables WHERE table_name LIKE '" + preName + "%'";
		List<String> list = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String[] s = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			s[i] = list.get(i);
		}
		return s;
	}
	
}




