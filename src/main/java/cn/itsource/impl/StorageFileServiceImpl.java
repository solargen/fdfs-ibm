package cn.itsource.impl;

import cn.itsource.IStorageFileService;
import cn.itsource.domain.StorageFile;
import cn.itsource.mapper.StorageFileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StorageFileServiceImpl extends ServiceImpl<StorageFileMapper, StorageFile> implements IStorageFileService {
}
