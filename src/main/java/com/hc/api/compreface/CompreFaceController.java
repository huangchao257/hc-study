package com.hc.api.compreface;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.hc.api.compreface.model.AddFaceDRO;
import com.hc.api.compreface.model.RecognizeRespDRO;
import com.hc.db.mapper.UserFaceTempMapper;
import com.hc.db.model.UserFaceTempEntity;
import com.hc.util.EasyExcelUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 类描述: CompreFace表现层
 *
 * @author HuangChao
 * @since 2025/11/10
 */
@Slf4j
@RestController
@RequestMapping("/compre-face")
public class CompreFaceController {

    @Resource
    private CompreFaceClient compreFaceClient;
    @Resource
    private UserFaceTempMapper userFaceTempMapper;

    @GetMapping("/add-subject")
    public String addSubject(String subjectName) {
        return compreFaceClient.addSubject(subjectName);
    }

    @GetMapping("/add-face")
    public AddFaceDRO addFace(String subjectName, String imagePath) {
        return compreFaceClient.addFace(subjectName, imagePath);
    }

    @DeleteMapping("/delete-all-subjects")
    public String deleteAllSubjects() {
        return compreFaceClient.deleteAllSubjects();
    }

    @GetMapping("/add-face-init")
    public String addFaceInit() {
        // 读取的excel文件路径
        String fileName = "file/重庆工程师人脸信息.xlsx";
        String sheetName = "Sheet1";
        Function<Map, UserFaceTempEntity> function = data -> {
            try {
                UserFaceTempEntity entity = new UserFaceTempEntity();
                entity.setUserId(Long.parseLong(data.get(0).toString()));
                entity.setUserName(data.get(1).toString());
                entity.setUserType(1);
                entity.setAvatarSrc(data.get(5).toString());
                // 创建主体
                compreFaceClient.addSubject(entity.getUserId().toString());
                // 为主体添加人脸图片
                AddFaceDRO test = compreFaceClient.addFace(entity.getUserId().toString(), entity.getAvatarSrc());
                entity.setSubject(test.getSubject());
                entity.setFaceId(test.getImage_id());
                entity.setSpId(Integer.parseInt(data.get(2).toString()));
                entity.setSpName(data.get(3).toString());
                return entity;
            } catch (Exception e) {
                log.error("数据转换异常:{}", JSON.toJSONString(data), e);
                return null;
            }
        };

        Consumer<UserFaceTempEntity> consumer = entity -> {
            if (entity == null) {
                return;
            }
            userFaceTempMapper.insert(entity);
        };

        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getSingleReadListener(function, consumer))
                .sheet(sheetName).headRowNumber(0).doRead();

        return "成功";
    }

    @GetMapping("/recognize")
    public RecognizeRespDRO recognize(String imagePath) throws Exception {
        return compreFaceClient.recognize(imagePath, "0.5");
    }
}
