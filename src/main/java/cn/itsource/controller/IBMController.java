package cn.itsource.controller;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.ObjectStorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/ibm")
@Slf4j
public class IBMController {

    @Autowired
    private ObjectStorageUtils objectStorageUtils;

    @PostMapping("/upload")
    public AjaxResult fileUpload(MultipartFile file){

        UUID uuid = UUID.randomUUID();
        String itemName = uuid.toString()+ "." + FilenameUtils.getExtension(file.getOriginalFilename());

        try {
            objectStorageUtils.uploadFile(itemName,file.getInputStream());
            log.debug("文件上传成功!");
            return AjaxResult.me().setMessage(itemName);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败!");
        }

    }

}
