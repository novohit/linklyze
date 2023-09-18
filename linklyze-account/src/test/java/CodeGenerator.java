import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setAuthor("novo")
                .setOpen(false)
                .setFileOverride(false)
                .setIdType(IdType.AUTO)
                .setBaseResultMap(true)
                .setBaseColumnList(false)
                .setEntityName("%sDO")
                .setServiceName("%sService");
        mpg.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(datasourceUrl)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("root");
        mpg.setDataSource(dataSourceConfig);

        // 包名配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig
                .setParent(parent)
                .setPathInfo(getPathInfo())
                .setEntity("model")
                .setController("api.v1")
                .setXml("xml");
        mpg.setPackageInfo(packageConfig);

        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig
                .setService("/mpg/templates/service.java")
                .setServiceImpl("/mpg/templates/serviceImpl.java")
                .setEntity("/mpg/templates/entity.java")
                .setXml("/mpg/templates/mapper.xml")
                .setMapper("/mpg/templates/mapper.java")
                .setController("/mpg/templates/controller.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setNaming(NamingStrategy.underline_to_camel)
                .setSuperEntityClass("BaseModel")
                //.setTablePrefix("z_")
                .setEntitySerialVersionUID(false)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setSuperEntityColumns("id", "create_time", "update_time", "delete_time")
                //.setSuperEntityColumns("create_time", "update_time", "delete_time")
                .setInclude(scanner("表名，多个英文逗号分割").split(","))
                .setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 读取控制台内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    // 在哪个包下生成路径
    private static final String module = "/linklyze-account";

    private static final String path = "com/linklyze/account";

    // com.xxx.xxx
    private static final String parent = path.replace("/", ".");

    private static final String datasourceUrl = "jdbc:mysql://localhost:3308/plato_account?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    private static Map<String, String> getPathInfo() {
        Map<String, String> pathInfo = new HashMap<>();

        pathInfo.put(ConstVal.ENTITY_PATH, System.getProperty("user.dir") + module + String.format("/src/main/java/%s/model", path));
        pathInfo.put(ConstVal.MAPPER_PATH, System.getProperty("user.dir") + module + String.format("/src/main/java/%s/mapper", path));
        pathInfo.put(ConstVal.SERVICE_PATH, System.getProperty("user.dir") + module + String.format("/src/main/java/%s/service", path));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, System.getProperty("user.dir") + module + String.format("/src/main/java/%s/service/impl", path));
        pathInfo.put(ConstVal.CONTROLLER_PATH, System.getProperty("user.dir") + module + String.format("/src/main/java/%s/api/v1", path));
        pathInfo.put(ConstVal.XML_PATH, System.getProperty("user.dir") + module + "/src/main/resources/mapper");
        return pathInfo;
    }
}
