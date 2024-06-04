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

import java.util.*;
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

    /**
     * 20240530-非家修匠全量配置ID
     */
    private static final List<Integer> conf_list = Arrays.asList(1, 4, 6, 7, 8, 9, 10, 13, 15, 16, 18, 19, 21, 22, 24, 25, 26, 30, 32, 37, 38, 41, 42, 43, 44, 46, 50, 51, 52, 54, 59, 60, 62, 64, 65, 69, 72, 73, 74, 76, 79, 82, 83, 86, 89, 92, 93, 94, 96, 99, 100, 103, 105, 107, 108, 111, 113, 117, 118, 119, 120, 123, 124, 126, 127, 133, 136, 137, 141, 142, 144, 145, 147, 149, 151, 152, 154, 157, 159, 160, 163, 164, 165, 167, 170, 172, 173, 176, 177, 178, 181, 184, 186, 189, 191, 194, 196, 198, 200, 203, 205, 207, 209, 211, 213, 215, 217, 218, 220, 222, 224, 225, 226, 229, 231, 234, 237, 239, 242, 244, 246, 248, 250, 252, 255, 257, 259, 261, 263, 265, 267, 269, 271, 273, 275, 277, 279, 281, 283, 285, 287, 289, 292, 293, 295, 296, 298, 301, 302, 306, 307, 308, 310, 312, 316, 317, 319, 320, 321, 326, 328, 331, 332, 333, 334, 337, 338, 342, 343, 346, 348, 349, 352, 353, 357, 358, 359, 360, 364, 366, 368, 369, 372, 373, 374, 377, 380, 382, 383, 387, 389, 390, 392, 395, 397, 399, 401, 403, 405, 407, 409, 410, 412, 416, 418, 419, 422, 423, 425, 427, 430, 431, 434, 435, 438, 439, 442, 444, 445, 448, 449, 451, 454, 455, 458, 459, 462, 463, 465, 468, 469, 472, 474, 476, 477, 480, 481, 484, 485, 487, 490, 491, 493, 496, 497, 500, 501, 504, 506, 508, 509, 512, 514, 516, 517, 519, 520, 523, 524, 526, 530, 531, 535, 536, 537, 541, 542, 543, 546, 547, 551, 552, 554, 555, 557, 559, 561, 564, 566, 567, 571, 572, 575, 576, 578, 581, 582, 584, 587, 589, 591, 593, 594, 597, 598, 601, 602, 605, 606, 608, 610, 612, 614, 617, 619, 620, 622, 625, 626, 629, 631, 632, 634, 637, 639, 641, 643, 644, 646, 648, 650, 652, 654, 656, 658, 660, 662, 664, 666, 668, 670, 673, 674, 678, 680, 681, 683, 686, 687, 689, 692, 694, 695, 697, 699, 702, 705, 707, 709, 711, 713, 715, 717, 719, 721, 723, 725, 727, 729, 731, 733, 735, 737, 739, 741, 743);
    /**
     * 20240530-试点城市非家修匠配置ID
     */
    private static final List<Integer> city_conf_list = Arrays.asList(4, 7, 8, 10, 13, 15, 16, 19, 21, 24, 25, 26, 38, 41, 42, 46, 51, 52, 59, 65, 72, 73, 74, 76, 82, 83, 89, 99, 103, 108, 117, 118, 119, 127, 142, 145, 147, 149, 152, 157, 163, 165, 172, 173, 184, 205, 209, 217, 244, 252, 261, 285);
    /**
     * 20240530-试点城市与非家修匠配置ID映射map
     */
    private static final Map<Integer, Integer> city_conf_map;

    static {
        city_conf_map = new HashMap<>();
        city_conf_map.put(130100, 46);
        city_conf_map.put(130200, 65);
        city_conf_map.put(130400, 74);
        city_conf_map.put(130600, 51);
        city_conf_map.put(140100, 16);
        city_conf_map.put(150100, 172);
        city_conf_map.put(210100, 13);
        city_conf_map.put(210200, 244);
        city_conf_map.put(220100, 4);
        city_conf_map.put(230100, 42);
        city_conf_map.put(320200, 99);
        city_conf_map.put(320300, 26);
        city_conf_map.put(320400, 8);
        city_conf_map.put(320500, 118);
        city_conf_map.put(320600, 52);
        city_conf_map.put(320800, 252);
        city_conf_map.put(321000, 72);
        city_conf_map.put(330200, 157);
        city_conf_map.put(330300, 163);
        city_conf_map.put(330400, 261);
        city_conf_map.put(340100, 7);
        city_conf_map.put(350100, 142);
        city_conf_map.put(350200, 147);
        city_conf_map.put(350500, 103);
        city_conf_map.put(360100, 25);
        city_conf_map.put(370100, 10);
        city_conf_map.put(370200, 15);
        city_conf_map.put(370300, 119);
        city_conf_map.put(370600, 19);
        city_conf_map.put(370700, 24);
        city_conf_map.put(371300, 21);
        city_conf_map.put(410100, 41);
        city_conf_map.put(410300, 73);
        city_conf_map.put(420600, 89);
        city_conf_map.put(430100, 38);
        city_conf_map.put(440400, 205);
        city_conf_map.put(440600, 117);
        city_conf_map.put(441300, 145);
        city_conf_map.put(441900, 59);
        city_conf_map.put(442000, 108);
        city_conf_map.put(450100, 83);
        city_conf_map.put(450300, 285);
        city_conf_map.put(460100, 76);
        city_conf_map.put(510700, 217);
        city_conf_map.put(520100, 127);
        city_conf_map.put(520300, 149);
        city_conf_map.put(530100, 152);
        city_conf_map.put(610400, 209);
        city_conf_map.put(620100, 82);
        city_conf_map.put(630100, 165);
        city_conf_map.put(640100, 173);
        city_conf_map.put(650100, 184);
    }

    @Test
    void testDistributeScoreMinGap() {
        // 读取的excel文件路径
        String fileName = "file/基础保底推单差距_202405.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(0).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            item.setMapKey(data.get(2).toString());
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = 1; j < 11; j++) {
                map.put(j, Integer.parseInt(data.get(3 + j).toString()));
                item.setMapValue(JSON.toJSONString(map));
            }
            item.setDataType(20);
            item.setCreater("批量清洗数据20240531");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240531");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                // 读取指定sheet
                .sheet()
                // 第2行为第一条数据
                .headRowNumber(1).doRead();
    }

    @Test
    void testGuaranteedOrder() {
        // 读取的excel文件路径
        String fileName = "file/基础保底推单差距_202405.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(0).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            item.setMapKey(data.get(2).toString());
            Map param = new HashMap<>();
            param.put("score", Integer.parseInt(data.get(14).toString()));
            param.put("gap", Integer.parseInt(data.get(15).toString()));
            param.put("maxLevel", Integer.parseInt(data.get(16).toString()));
            item.setMapValue(JSON.toJSONString(param));
            item.setDataType(110);
            item.setCreater("批量清洗数据20240531");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240531");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                // 读取指定sheet
                .sheet()
                // 第2行为第一条数据
                .headRowNumber(1).doRead();
    }

    @Test
    void testInvalidIdleTime() {
        // 读取的excel文件路径
        String fileName = "file/空闲时长配置_202405.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(0).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            item.setMapKey(data.get(3).toString());
            item.setMapValue(data.get(5).toString());
            item.setDataType(30);
            item.setCreater("批量清洗数据20240531");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240531");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                .sheet()
                // 第2行 为第一条数据
                .headRowNumber(1).doRead();
    }

    @Test
    void testDistributeScoreWeight() {
        // 读取的excel文件路径
        String fileName = "file/最终推单分权重参数_202405(1).xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(0).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            item.setMapKey(confId.toString());
            Map param = new HashMap<>();
            param.put("levelWeight", Integer.parseInt(data.get(2).toString()));
            param.put("distributeScoreWeight", Integer.parseInt(data.get(3).toString()));
            item.setMapValue(JSON.toJSONString(param));
            item.setDataType(70);
            item.setCreater("批量清洗数据20240531");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240531");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                .sheet()
                // 第2行 为第一条数据
                .headRowNumber(1).doRead();

    }

    @Test
    void testVLevelHierarchy() {
        String mapValue = "[{\"engineerLevelList\":[8,10,9,7],\"priority\":1,\"value\":100},{\"engineerLevelList\":[6],\"priority\":2,\"value\":80},{\"engineerLevelList\":[5],\"priority\":3,\"value\":70},{\"engineerLevelList\":[4,1],\"priority\":4,\"value\":60},{\"engineerLevelList\":[2,3],\"priority\":5,\"value\":40}]";
        List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
        for (Integer i : city_conf_list) {
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            item.setBizId(i);
            item.setDataType(40);
            item.setMapKey(i.toString());
            item.setMapValue(mapValue);
            item.setCreater("批量清洗数据20240531");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240531");
            item.setUpdateTime(new Date());
            list.add(item);

        }

        confDistributeCommonRelationMapper.insertBatch(list);
    }

    @Test
    void testConfDeliveryMetricsCycle() {
        Map<Integer, Integer> cycleMap = new HashMap<>();
        cycleMap.put(1000, 3);
        cycleMap.put(1002, 3);
        cycleMap.put(10001, 7);
        cycleMap.put(1014, 7);
        cycleMap.put(1004, 3);

        List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
        for (Integer i : city_conf_list) {
            cycleMap.forEach((k, v) -> {
                ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
                item.setBizId(i);
                item.setDataType(100);
                item.setMapKey(k.toString());
                item.setMapValue(v.toString());
                item.setCreater("批量清洗数据20240531");
                item.setCreateTime(new Date());
                item.setUpdater("批量清洗数据20240531");
                item.setUpdateTime(new Date());
                list.add(item);
            });

        }

        confDistributeCommonRelationMapper.insertBatch(list);
    }

    @Test
    void testTakeParam() {
        // 读取的excel文件路径
        String fileName = "file/领单参数_202405.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            for (Integer i : conf_list) {
                ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
                item.setBizId(i);
                item.setMapKey(data.get(0).toString());
                Map<Integer, Map> map = new HashMap<>();
                for (int j = 1; j < 11; j++) {
                    Map param = new HashMap<>();
                    param.put("dayDistributeNum", Integer.parseInt(data.get(2 * j).toString()));
                    param.put("maxReceiveNum", Integer.parseInt(data.get(2 * j + 1).toString()));
                    map.put(j, param);
                    item.setMapValue(JSON.toJSONString(map));
                }
                item.setDataType(120);
                item.setCreater("批量清洗数据20240531");
                item.setCreateTime(new Date());
                item.setUpdater("批量清洗数据20240531");
                item.setUpdateTime(new Date());
                list.add(item);
            }
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                // 读取指定sheet
                .sheet("Sheet2")
                // 第三3行为第一条数据
                .headRowNumber(2).doRead();
    }


    @Test
    public void testConfDistributeMinSore() {
        // 读取的excel文件路径
        String fileName = "file/最小派单分差距配置.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            Map<Integer, String> map = JSON.parseObject(data.get(1).toString(), new TypeReference<>() {
            });
            map.forEach((key, value) -> {
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
                } else {
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
