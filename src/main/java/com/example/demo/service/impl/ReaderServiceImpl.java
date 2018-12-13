package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.demo.pojo.Reader;
import com.example.demo.mapper.ReaderMapper;
import com.example.demo.service.IReaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.vo.BReaderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wxy
 * @since 2018-12-09
 */
@Service
public class ReaderServiceImpl extends ServiceImpl<ReaderMapper, Reader> implements IReaderService {

    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public BReaderVo getBReader(Wrapper wrapper) {
        return readerMapper.getBReader(wrapper);
    }
}
