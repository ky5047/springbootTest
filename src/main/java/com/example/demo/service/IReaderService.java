package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.demo.pojo.Reader;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.vo.BReaderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wxy
 * @since 2018-12-09
 */
public interface IReaderService extends IService<Reader> {

    public BReaderVo getBReader(Wrapper wrapper);
}
