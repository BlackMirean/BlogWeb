package com.rjgc.xzy.finalspringboot.Controller;

import com.rjgc.xzy.finalspringboot.Pojo.Result;
import com.rjgc.xzy.finalspringboot.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class FileUploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        log.info("文件上传，文件名 : {}", file.getOriginalFilename());
        String url = aliOSSUtils.upload(file);
        return Result.success(url);
    }
}
