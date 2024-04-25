package com.hc.work;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.hc.db.mapper.ConfDistributeCommonRelationMapper;
import com.hc.db.mapper.ConfDistributePlanMapper;
import com.hc.db.model.ConfDistributeCommonRelationEntity;
import com.hc.db.model.ConfDistributePlan;
import com.hc.util.EasyExcelUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2024/04/24
 */
@Slf4j
@SpringBootTest
public class ConfDistributeCleanDataTest {

    @Resource
    private ConfDistributePlanMapper confDistributePlanMapper;
    @Resource
    private ConfDistributeCommonRelationMapper confDistributeCommonRelationMapper;
    @Test
    public void testConfDistributeMinSore() {
        // 读取的excel文件路径
        String fileName = "file/最小派单分差距配置.xlsx";

        // 插入sql模板
        String insertSqlTemplate = "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater,create_time, updater, update_time) VALUES (%s, %s, '%s', 110, '批量清洗数据20240425', now(), '批量清洗数据20240425', now());";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            Map<Integer, String> map = JSON.parseObject(data.get(1).toString(), new TypeReference<>() {
            });
            map.forEach((key,value)->{
                ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
                item.setBizId(Integer.valueOf(data.get(0).toString()));
                item.setMapKey(key.toString());
                item.setMapValue(value);
                item.setDataType(110);
                item.setCreater("批量清洗数据20240425");
                item.setCreateTime(new Date());
                item.setUpdater("批量清洗数据20240425");
                item.setUpdateTime(new Date());
                list.add(item);
            });
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer)).sheet().headRowNumber(1).doRead();
    }

    @Test
    public void testConfRookieDayDistributeCount() {
        // 读取的excel文件路径
        String fileName = "file/V1开发批量处理版本.xlsx";

        // 插入sql模板
//        String insertSqlTemplate = "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater,create_time, updater, update_time) VALUES (%s, %s, '%s', 80, '批量清洗数据20240425', now(), '批量清洗数据20240425', now());";
        String jxjDefault = "{\"firstDay\":1,\"firstOrderNum\":20,\"secondDay\":2,\"secondOrderNum\":20,\"thirdOrderNum\":20}";
        List<ConfDistributePlan> confList = confDistributePlanMapper.selectAll();
        Map<Integer, List<ConfDistributePlan>> distributeConfMap = confList.stream().collect(Collectors.groupingBy(ConfDistributePlan::getCityId));
        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            List<ConfDistributePlan> list1 = distributeConfMap.get(Integer.parseInt(data.get(1).toString()));
            for (ConfDistributePlan plan : list1) {
                ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
                item.setBizId(plan.getConfId());
                item.setMapKey(data.get(3).toString());
                item.setDataType(80);
                item.setCreater("批量清洗数据20240425");
                item.setCreateTime(new Date());
                item.setUpdater("批量清洗数据20240425");
                item.setUpdateTime(new Date());
                if (plan.getSubCompanyId() == 45111) {
                    item.setMapValue(jxjDefault);
                }else {
                    JSONObject param = new JSONObject();
                    param.put("firstDay", data.get(4));
                    param.put("firstOrderNum", data.get(5));
                    param.put("secondDay", data.get(6));
                    param.put("secondOrderNum", data.get(7));
                    param.put("thirdOrderNum", data.get(9));
                    item.setMapValue(param.toJSONString());
                }
                list.add(item);
            }
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel 并执行处理
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer)).sheet().headRowNumber(2).doRead();
    }
}
