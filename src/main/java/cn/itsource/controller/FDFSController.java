package cn.itsource.controller;

import cn.itsource.service.IStorageFileService;
import cn.itsource.domain.StorageFile;
import cn.itsource.util.AjaxResult;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/fdfs")
@Slf4j
public class FDFSController {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private IStorageFileService storageFileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file){
        try {
            String extName = FilenameUtils.getExtension(file.getOriginalFilename());
            StorePath storePath = storageClient.uploadFile(file.getInputStream(),
                    file.getSize(),
                    extName,
                    null);
            String path = storePath.getFullPath();
            log.debug("文件上传成功，文件路径为{}",path);

            //保存数据库
            StorageFile storageFile = new StorageFile();
            storageFile.setExtname(extName);
            storageFile.setFdfsFileId(path);
            storageFile.setState(0);
            storageFileService.save(storageFile);

            return AjaxResult.me().setResultObj(path);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("文件上传失败!"+e.getMessage());
        }
    }



}
