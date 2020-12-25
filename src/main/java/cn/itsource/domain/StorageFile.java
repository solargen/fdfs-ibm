package cn.itsource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_storage_file")
public class StorageFile {

    @TableId(type= IdType.AUTO)
    private Long id;
    private String extname;
    private String fdfsFileId;
    private String ibmFileId;
    private Integer state;

}
