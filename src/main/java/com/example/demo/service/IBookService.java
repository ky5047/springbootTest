package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Book;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wxy
 * @since 2018-12-07
 */
public interface IBookService extends IService<Book> {

    public IPage<Book> selectPageVo(Page<Book> page, Wrapper<Book> wrapper);
}
