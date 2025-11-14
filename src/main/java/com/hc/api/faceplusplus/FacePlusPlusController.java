package com.hc.api.faceplusplus;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.hc.api.faceplusplus.model.DetectFaceDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 类描述: 旷视 face++ 接口调用
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
@Slf4j
@RestController
@RequestMapping("/facepp")
public class FacePlusPlusController {

    @Resource
    private FacePlusPlusComponent facePlusPlusComponent;


    @Operation(summary = "创建人脸库")
    @GetMapping("/createFaceSet")
    public String createFaceSet(String outerId, String displayName) {
        Map map = facePlusPlusComponent.createFaceSet(outerId, displayName);
        String jsonString = JSON.toJSONString(map);
        log.info("createFaceSet = {}", jsonString);
        return jsonString;
    }
    @Operation(summary = "删除人脸库")
    @GetMapping("/deleteFaceSet")
    public String deleteFaceSet(String faceSetToken) {
        Map map = facePlusPlusComponent.deleteFaceSet(faceSetToken);
        String jsonString = JSON.toJSONString(map);
        log.info("createFaceSet = {}", jsonString);
        return jsonString;
    }

    @Operation(summary = "人脸检测")
    @GetMapping("/detect")
    public String detect(String path) {
        Map map = facePlusPlusComponent.detect(new File(path));
        String jsonString = JSON.toJSONString(map);
        log.info("detect = {}", jsonString);
        return jsonString;
    }

    @Operation(summary = "人脸检测并搜索")
    @GetMapping("/detectSearch")
    public List<Map> detectSearch(String path, String outerId, String faceSetToken) throws InterruptedException {
        Map map = facePlusPlusComponent.detect(new File(path));
        String jsonString = JSON.toJSONString(map);
        log.info("detectSearch = {}", jsonString);
        Object faces = map.get("faces");
        List<DetectFaceDTO> detectFaceDTOS = JSON.parseArray(JSON.toJSONString(faces), DetectFaceDTO.class);
        List<Map> resultList = Lists.newArrayList();
        for (DetectFaceDTO detectFaceDTO : detectFaceDTOS) {
            String faceToken = detectFaceDTO.getFace_token();
            Thread.sleep(1100);
            Map search = facePlusPlusComponent.search(outerId, faceSetToken, faceToken);
            search.put("testFaceToken", faceToken);
            log.info("detectSearch faceToken is {} search is {}", faceToken, JSON.toJSONString(search));
            resultList.add(search);
        }
        log.info("resultList is {} ", JSON.toJSONString(resultList));
        return resultList;
    }

    @Operation(summary = "添加人脸")
    @GetMapping("/addFace")
    public String addFace(String outerId, String faceSetToken, String faceTokens) {
        Map map = facePlusPlusComponent.addFace(outerId, faceSetToken, faceTokens);
        String jsonString = JSON.toJSONString(map);
        log.info("addFace = {}", jsonString);
        return jsonString;

    }

    @Operation(summary = "检测并添加人脸")
    @GetMapping("/detectAddFace")
    public Map<String, String> detectAddFace(String directoryPath, String outerId, String faceSetToken) throws InterruptedException {
        long l = System.currentTimeMillis();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        List<String> faceTokenList = Lists.newArrayList();
        Map<String, String> faceTokenMap = Maps.newHashMap();
        for (File file : files) {
            Map map = facePlusPlusComponent.detect(file);
            Object faces = map.get("faces");
            List<DetectFaceDTO> detectFaceDTOS = JSON.parseArray(JSON.toJSONString(faces), DetectFaceDTO.class);
            for (DetectFaceDTO detectFaceDTO : detectFaceDTOS) {
                String faceToken = detectFaceDTO.getFace_token();
                faceTokenMap.put(file.getName(), faceToken);
                log.info("detectAddFace file {} faceToken {}", file.getName(), faceToken);
                long l1 = System.currentTimeMillis();
                if (l1 - l >= 150000) {
                    log.info("detectAddFace faceTokenMap {}", faceTokenMap);
                }
                Thread.sleep(1100);
                Map map1 = facePlusPlusComponent.addFace(outerId, faceSetToken, faceToken);
                faceTokenList.add(faceToken);
                Thread.sleep(1100);
            }
            log.info("detectAddFace faceTokenMap {}", faceTokenMap);
        }
        return faceTokenMap;
    }

    @Operation(summary = "人脸搜索")
    @GetMapping("/search")
    public String search(String outerId, String faceSetToken, String faceToken) {
        Map map = facePlusPlusComponent.search(outerId, faceSetToken, faceToken);
        String jsonString = JSON.toJSONString(map);
        log.info("search = {}", jsonString);
        return jsonString;
    }

    @Operation(summary = "人脸库详情")
    @GetMapping("/getFaceSetDetail")
    public String getFaceSetDetail(String outerId, String faceSetToken) {
        Map map = facePlusPlusComponent.getFaceSetDetail(outerId, faceSetToken);
        String jsonString = JSON.toJSONString(map);
        log.info("getFaceSetDetail = {}", jsonString);
        return jsonString;
    }

    @Operation(summary = "超时")
    @GetMapping("/timeOut")
    public void timeOut() throws InterruptedException {
        Thread.sleep(3000 * 60);
    }
}
