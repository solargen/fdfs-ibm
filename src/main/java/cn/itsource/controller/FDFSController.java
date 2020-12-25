package cn.itsource.controller;

import cn.itsource.util.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fdfs")
public class FDFSController {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file){

        return null;
    }

}
