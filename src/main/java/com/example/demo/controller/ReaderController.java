package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.ReaderMapper;
import com.example.demo.pojo.Book;
import com.example.demo.pojo.Reader;
import com.example.demo.service.IReaderService;
import com.example.demo.util.DesUtils;
import com.example.demo.util.Utils;
import com.example.demo.vo.BReaderVo;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wxy
 * @since 2018-12-09
 */
@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private IReaderService readerService;


    @RequestMapping("addReaders")
    private List<Reader> addReaders(){
        Reader reader = new Reader();
        List<Reader> readers = new ArrayList<Reader>();
        for (int i = 0; i < 50 ; i++) {
            String uuid = Utils.getUUID();
            try {
                reader.setFullname("full_"+uuid);
                reader.setUsername(uuid);
                reader.setPassword(DesUtils.base64Encode(uuid));
                reader.setRegTime(new Date());
                readerService.save(reader);

            }  catch (EncoderException e) {
                e.printStackTrace();
            }
            Map map = new HashMap<String,String>();
            map.put("userName",uuid);
            Reader r = readerService.getOne(new QueryWrapper<Reader>().allEq(map)) ;
            readers.add(r);
        }
        return readers;
    }

    /**
     * 测试自定义sql的测试
     * */
    @RequestMapping("test")
    public BReaderVo test(){
        BReaderVo bReaderVo =  readerService.getBReader(new QueryWrapper<BReaderVo>().eq("reader","wxy"));
        return bReaderVo;
    }


    /**
     * 分页
     * */





}
