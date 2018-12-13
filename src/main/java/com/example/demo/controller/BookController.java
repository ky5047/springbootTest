package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.Book;
import com.example.demo.service.IBookService;
import com.example.demo.util.Utils;
import com.example.demo.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wxy
 * @since 2018-12-07
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @RequestMapping("addBook")
    private String addBook(){
        Book book = new Book();
        String uuid = Utils.getUUID();
        book.setIsbn("isbn-"+ uuid);
        book.setAuthor("wxy");
        book.setDescription("测试"+uuid);
        book.setReader("zx");
        book.setTitle(uuid);
        bookService.save(book);

        Map map = new HashMap<String,String>();
        map.put("title",uuid);
        Book book1 = bookService.getOne(new QueryWrapper<Book>().allEq(map)) ;
        return book1.toString();
    }

    @RequestMapping("queryBook")
    public Book queryBook(){
        Book book1 = bookService.getOne(new QueryWrapper<Book>().eq("title","fafa809ad3ab4870b734537b99c21a2b"));
        /*
        * 拼接 EXISTS ( sql语句 )
        例: exists("select id from table where age = 1")--->exists (select id from table where age = 1)
        * **/
        bookService.list(new QueryWrapper<Book>().exists("select id from table where age = 1"));


        /*
        * BETWEEN 值1 AND 值2
        例: between("age", 18, 30)--->age between 18 and 30
        * */
        bookService.list(new QueryWrapper<Book>().between("id",10,15));

        /*LIKE '%值'
        例: likeRight("name", "王")--->name like '王%'*/

        bookService.list(new QueryWrapper<Book>().likeRight("title","316550"));

        /*字段 IS NULL
        例: isNotNull("name")--->name is not null*/

       /* 字段 IN (value.get(0), value.get(1), ...)
        例: in("age",{1,2,3})--->age in (1,2,3)*/
        bookService.list(new QueryWrapper<Book>().in("id",1,2,3,4,5,6,7,8,9));

        bookService.list(new QueryWrapper<Book>().in("id",Arrays.asList(1,2,3,4,5,6,7,8)));

      /*   字段 IN ( sql语句 )
                例: inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
        例: inSql("id", "select id from table where id < 3")--->id in (select id from table where id < 3)*/
        bookService.list(new QueryWrapper<Book>().inSql("id","select id from book where id < 3"));

        /*groupBy(R... columns)
        groupBy(boolean condition, R... columns)*/
        bookService.list(new QueryWrapper<Book>().inSql("id","select id from book where id < 3").groupBy("id","title"));


       /* 排序：ORDER BY 字段, ...
        例: orderBy(true, true, "id", "name")--->order by id ASC,name ASC*/

        bookService.list(new QueryWrapper<Book>().orderBy(true,false,"id"));

        /**
         *
         * HAVING ( sql语句 )
         例: having("sum(age) > 10")--->having sum(age) > 10
         例: having("sum(age) > {0}", 11)--->having sum(age) > 11
         */
        bookService.list(new QueryWrapper<Book>().groupBy("title").having("count(1) > 10"));


        /**
         * 正常嵌套 不带 AND 或者 OR
         例: nested(i -> i.eq("name", "李白").ne("status", "活着"))--->(name = '李白' and status <> '活着')
         * */
        bookService.list(new QueryWrapper<Book>().nested(i->i.eq("title","fafa809ad3ab4870b734537b99c21a2b").or().eq("title","58f519c0a526454abf535ed8921518cb")));

        /*OR 嵌套
        例: or(i -> i.eq("name", "李白").ne("status", "活着"))--->or (name = '李白' and status <> '活着')
        */
        bookService.list(new QueryWrapper<Book>().or(i->i.eq("title","fafa809ad3ab4870b734537b99c21a2b").ne("title","58f519c0a526454abf535ed8921518cb")));

        /*
        * AND 嵌套
        例: and(i -> i.eq("name", "李白").ne("status", "活着"))--->and (name = '李白' and status <> '活着')
        * */
        bookService.list(new QueryWrapper<Book>().and(i->i.eq("title","fafa809ad3ab4870b734537b99c21a2b").ne("title","58f519c0a526454abf535ed8921518cb")));

        /**
         *
         拼接 sql
         注意事项:
         该方法可用于数据库函数 动态入参的params对应前面sqlHaving内部的{index}部分.这样是不会有sql注入风险的,反之会有!
         例: apply("id = 1")--->having sum(age) > 10
         例: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
         例: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
         * */
//        bookService.list(new QueryWrapper<Book>().apply("date_format(reg_date,'%Y-%m-%d') = '2018-08-08'"));


        /*无视优化规则直接拼接到 sql 的最后
        注意事项:
        只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
        例: last("limit 1")*/


        /*例: select("id", "name", "age")
        例: select(i -> i.getProperty().startsWith("test"))*/

        System.out.println((new QueryWrapper<Book>().select("id", "name", "age").and(i->i.eq("title","fafa809ad3ab4870b734537b99c21a2b").ne("title","58f519c0a526454abf535ed8921518cb"))));

        /*UpdateWrapper set
        set(String column, Object val)
        set(boolean condition, String column, Object val)*/

        /*使用 Wrapper 自定义SQL*/
        /*mysqlMapper.getAll(Wrappers.lambdaQuery(new MysqlData()).eq(MysqlData::getGroup, 1));
        # 方案一 注解方式 Mapper.java
        @Select("select * from mysql_data ${ew.customSqlSegment}")
        List<MysqlData> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);
        # 方案二 XML形式 Mapper.xml
                <select id="getAll" resultType="MysqlData">
                SELECT * FROM mysql_data ${ew.customSqlSegment}
        </select>*/

       /*
        * 实体带查询使用方法  输出看结果
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.setEntity(new User(1));
        ew.where("user_name={0}", "'zhangsan'").and("id=1")
                .orNew("user_status={0}", "0").or("status=1")
                .notLike("user_nickname", "notvalue")
                .andNew("new=xx").like("hhh", "ddd")
                .andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
                .groupBy("x1").groupBy("x2,x3")
                .having("x1=11").having("x3=433")
                .orderBy("dd").orderBy("d1,d2");
        System.out.println(ew.getSqlSegment());

        int buyCount = selectCount(Condition.create()
                .setSqlSelect("sum(quantity)")
                .isNull("order_id")
                .eq("user_id", 1)
                .eq("type", 1)
                .in("status", new Integer[]{0, 1})
                .eq("product_id", 1)
                .between("created_time", startDate, currentDate)
                .eq("weal", 1));

        */




//        bookService.getAll(Wrappers.lambdaQuery(new Book()).eq(Book::getGroup, 1));
        return book1;
    }

    @RequestMapping("/queryBookVoByPage")
    public IPage<Book> queryBookVoByPage(){
        Page<Book> page  = new Page<Book>(0,10);
        IPage<Book> pageVo = bookService.selectPageVo(page,new QueryWrapper<>());
        return pageVo;
    }
}
