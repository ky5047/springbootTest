package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.pojo.Reader;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.vo.BReaderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wxy
 * @since 2018-12-09
 */
@Mapper
public interface ReaderMapper extends BaseMapper<Reader> {


//    @Select("select * from mysql_data ${ew.customSqlSegment}")
    BReaderVo getBReader(@Param(Constants.WRAPPER) Wrapper wrapper);
}
