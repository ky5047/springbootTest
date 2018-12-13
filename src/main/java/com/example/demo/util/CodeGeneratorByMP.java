package com.example.demo.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 92992 on 2018/12/3.
 *  MyBatis-Plus 生成代码
 *  代码生成器
 * AutoGenerator 是 MyBatis-Plus 的代码生成器，
 * 通过 AutoGenerator 可以快速生成 Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码，
 * 极大的提升了开发效率。
 */

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class CodeGeneratorByMP {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 另外一点值得注意的是@Value注解，基本格式如下：
     @value("${key:default")
     private String var;
     以上声明指导spring根据key去属性配置文件查找value，如果没找到，则使用default作为默认值。
     注意以上的${…}占位符只有当注册了PropertySourcesPlaceholderConfigurer bean后才能被解析，否则@Value注解会一直将默认值赋值给var
     * */
    @Value("${spring.datasource.url}")
    public static String url;
    @Value("${spring.datasource.username}")
    public static String username;
    @Value("${spring.datasource.password}")
    public static String password;
    @Value("${spring.datasource.driver-class-name}")
    public static String driverName;
    /**
     *读取控制台内容
     * */
    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入"+tip+":");
        System.out.println(help.toString());
        if (scanner.hasNext()){
            String ipt = scanner.next();
            if(StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw  new MybatisPlusException("请输入正确的"+tip+"!");
    }


    public static void main(String[] args){
        //代码生成器
        AutoGenerator ag = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        /**
         *
         * System.getProperty("user.dir")
         * 中的user.dir 是 由以下方法定义的
         * 第一种方法是在启动tomcat的时候配置：
             比如在myeclipse中，选中这个项目，然后在工具栏中选择"Run-->Run Confgurations“，然后在对话框的右边选择"Arguments，然后在VM arguments中输入-DconfigurePath=hello
         * 第二种方法是在执行java命令的时候配置：
            将之前的测试类导出为一个jar包，再控制台使用命令执行：java -DconfigurePath=hello -jar Test.jar
         * */
//        String projectPath = System.getProperty("user.dir");//
//        gc.setOutputDir(projectPath+"/src/main/java");
        String idType = scanner("id生产策略，【0：AUTO，1：NONE，2：INPUT，3：ID_WORKER，" +
                "4：UUID，5：ID_WORKER_STR】如：自增请输入1，以此类推");
        gc.setOutputDir("E:\\studySpace\\SpringBoot\\bootDemo"+"/src/main/java")// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(false)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(true);// XML columList
        gc.setAuthor("wxy");
        gc.setOpen(false);
        /*
        自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("I%sServiceImpl");
        gc.setControllerName("%sController");
        */
        //todo   加上校验 确保数据无误
        gc.setIdType(IdType.values()[Integer.parseInt(idType)]);//id生产策略 自增id
        ag.setGlobalConfig(gc);


        //数据源配置
        DataSourceConfig ds = new DataSourceConfig();
        ds.setDriverName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("root");
        ag.setDataSource(ds);

        //包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));// 总是加上该模块名
        pc.setParent("com.example.demo"); //父级模块名 --> 如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setEntity("pojo");//entity包名
        pc.setService("service");//Service包名
        pc.setServiceImpl("service.impl");//Service Impl包名
        pc.setController("controller");//Controller包名

        ag.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do someting
            }
        };
        String moduleName = scanner("模块名");
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "E:\\studySpace\\SpringBoot\\bootDemo" + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        focList.add(new FileOutConfig("/generator/templates/ftl/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "E:\\studySpace\\SpringBoot\\bootDemo" + "/src/com/example/demo/"
                        + "/" + tableInfo.getEntityName()  + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        ag.setCfg(cfg);
        ag.setTemplate(new TemplateConfig().setXml(null));

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//命名 下划线转驼峰
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);

//        strategyConfig.setSuperEntityClass("com.example.demo.pojo.BaseEntity");//定义个父类
//        strategyConfig.setEntityLombokModel(true);//使用lombok模块
        strategyConfig.setEntityBuilderModel(false);
        strategyConfig.setRestControllerStyle(true);//生成rest风格的controller
//        strategyConfig.setSuperControllerClass("");//设置 controller 统一父类
        strategyConfig.setInclude(scanner("表名"));

//        strategyConfig.setExclude(new String[]{"test"}) // 排除生成的表
        // 自定义实体父类
        // .setSuperEntityClass("cn.com.bosssoft.gep.basicdata.common.base.BsBaseEntity")
        // // 自定义 mapper 父类
        // .setSuperMapperClass("cn.com.bosssoft.gep.basicdata.common.base.BsBaseMapper")
        // // 自定义 service 父类
        // .setSuperServiceClass("cn.com.bosssoft.gep.basicdata.common.base.BsBaseService")
        // // 自定义 service 实现类父类
        // .setSuperServiceImplClass("cn.com.bosssoft.gep.basicdata.common.base.BsBaseServiceImpl")
//        strategyConfig.setSuperEntityColumns("id");
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix(pc.getModuleName()+"_");// 此处可以修改为您的表前缀
        // Boolean类型字段是否移除is前缀处理
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        strategyConfig.setEntityLombokModel(true);
        ag.setStrategy(strategyConfig);
        ag.setTemplateEngine(new FreemarkerTemplateEngine());
        ag.execute();
    }

}
