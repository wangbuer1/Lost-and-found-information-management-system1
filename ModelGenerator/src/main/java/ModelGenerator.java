import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;


public class ModelGenerator {

    public static DataSource getDataSource() {
        PropKit.use("myconfig.properties");
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl(PropKit.get("jdbcUrl"));
        ds.setUser(PropKit.get("user", "root"));
        ds.setPassword(PropKit.get("password", "root"));
        return ds;
    }

    public static void main(String[] args) {
        // base model 所使用的包名
        String baseModelPackageName = "model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getRootClassPath() + "../../../../src/main/java/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/../";

        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 添加不需要生成的表名

        generator.setMetaBuilder(new MetaBuilder(getDataSource()) {
            @Override
            protected boolean isSkipTable(String tableName) {
                return tableName.endsWith("_view");

            }
        });
        //SQL方言为MYSQL
        generator.setDialect(new MysqlDialect());
        //是否在model中生成dao字段
        generator.addExcludedTable("");
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(true);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes("t_");
        // 生成
        generator.generate();
    }
}




