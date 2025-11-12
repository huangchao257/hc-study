package com.hc.api.ucreater;

import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 类描述:
 *
 * @author HuangChao
 * @since 2025/09/16
 */
@Tag(name = "UCreaterApiController", description = "由创接口控制层")
@Slf4j
@RestController
@RequestMapping("/ucreater")
public class UCreaterApiController {
    @Resource
    private RestTemplate restTemplate;

    @Operation(summary = "由创回调")
    @RequestMapping(value = "/callback")
    public ResponseEntity<String> webhook(@RequestHeader HttpHeaders header, @RequestBody String body) {
        log.info("消息获取时间:{}, 请求头:{},请求体:{}", System.currentTimeMillis(), JSON.toJSONString(header), body);
        try {
            //todo:对收到的消息进行处理,最好发送到其他中间件,或者写到数据库中,不要影响回调地址的处理
            HttpHeaders h1 = new HttpHeaders();
            h1.add("abtag", "p0378");
            h1.add("Content-Type", "application/json");
            HttpEntity<Object> requestEntity = new HttpEntity<>(body, h1);
            ResponseEntity<Object> response = restTemplate.postForEntity("https://test3-bff-ocs.xiujiadian.com/api/zhendao/acceptMsg", requestEntity, Object.class);

        } catch (Exception e) {
            log.error("消息获取时间:{}, 请求头:{},请求体:{}", System.currentTimeMillis(), JSON.toJSONString(header), body, e);
        }
        return ResponseEntity.ok("成功");
    }

}
