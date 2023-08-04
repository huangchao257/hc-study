package com.hc.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.hc.constant.GlobalConsts;
import com.hc.db.mapper.ConfDistributePlanMapper;
import com.hc.db.mapper.ConfDistributeSingleVolumeMapper;
import com.hc.db.model.ConfDistributePlan;
import com.hc.db.model.ConfDistributeSingleVolume;
import com.hc.db.model.ConfDistributeSingleVolumeBO;
import com.hc.util.EasyExcelUtils;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hc.db.model.table.ConfDistributePlanTableDef.CONF_DISTRIBUTE_PLAN;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2023/07/31 14:52
 */
@Slf4j
@SpringBootTest
public class ExcelReadTest {

    @Resource
    private ConfDistributePlanMapper confDistributePlanMapper;
    @Resource
    private ConfDistributeSingleVolumeMapper confDistributeSingleVolumeMapper;

    /**
     * 采用解耦的自定义监听器读取Excel, 可以实现任何数据模型bean的读取
     */
    @Test
    public void testReadExcel() {
        // 读取的excel文件路径
        String fileName = "file/人单值.xlsx";

        Function<Map, List<ConfDistributeSingleVolume>> function = data -> coverList(data);
        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> confDistributeSingleVolumeMapper.insertBatch(list);
//        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> System.out.println("list = " + list);
        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getReadListener(function, consumer))
                .sheet().headRowNumber(2).doRead();


    }

    /**
     * 描述: 转换数据集合
     *
     * @param data
     * @return {@link List< ConfDistributeSingleVolume>}
     * @author: HuangChao
     * @since: 2023/8/4 19:17
     */
    private List<ConfDistributeSingleVolume> coverList(Map<Integer,String> data){

        List<ConfDistributePlan> confList = confDistributePlanMapper.selectListByQuery(QueryWrapper.create().select(CONF_DISTRIBUTE_PLAN.CONF_ID).where(CONF_DISTRIBUTE_PLAN.CITY_ID.eq(data.get(0))));
        if (CollectionUtils.isEmpty(confList)) {
            log.info("城市[{}]无派单配置", data.get(0));
            return Collections.emptyList();
        }

        List<Integer> confIdList = confList.stream().map(ConfDistributePlan::getConfId).collect(Collectors.toList());
        List<ConfDistributeSingleVolume> cachedDataList = ListUtils.newArrayListWithExpectedSize(GlobalConsts.NUM_200);
        for (Integer confId : confIdList) {
            for (int i = 0; i < 11; i++) {
                ConfDistributeSingleVolume volume = new ConfDistributeSingleVolume();
                volume.setConfId(confId);
                volume.setProductGroupId(Integer.valueOf(data.get(1)));
                volume.setEngineerLevel(i);
                volume.setType(10);
                volume.setCreater("系统");
                volume.setCreateTime(new Date());
                volume.setUpdater("系统");
                volume.setUpdateTime(new Date());
                if (i == 0 || i > 7) {
                    volume.setValleyValue(BigDecimal.ZERO);
                    volume.setPassValue(BigDecimal.ZERO);
                    volume.setPeakValue(BigDecimal.ZERO);
                } else {
                    volume.setValleyValue(new BigDecimal(data.get(i * 3 - 1)));
                    volume.setPassValue(new BigDecimal(data.get(i * 3)));
                    volume.setPeakValue(new BigDecimal(data.get(i * 3 + 1)));
                }
                volume.setCurrentWeekJson(ExcelReadTest.getWeekEveryDayCountStr(volume));
                // 数据转换
                cachedDataList.add(volume);
            }
        }

        log.info("解析完一条完整数据:{}", JSON.toJSONString(cachedDataList));

        return cachedDataList;
    }

    @Test
    public void confRead() {

        String fileName = "file/人单值.xlsx";


        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, new AnalysisEventListener<Map<Integer, String>>() {

            /**
             *临时存储
             */
            private List<ConfDistributeSingleVolume> cachedDataList = ListUtils.newArrayListWithExpectedSize(GlobalConsts.NUM_200);

            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                log.info("解析到一条数据:{}", JSON.toJSONString(data));
                for (int i = 0; i < 10; i++) {
                    ConfDistributeSingleVolume volume = new ConfDistributeSingleVolume();
                    volume.setConfId(null);
                    volume.setProductGroupId(Integer.valueOf(data.get(0)));
                    volume.setEngineerLevel(i);
                    volume.setCreater("系统");
                    volume.setCreateTime(new Date());
                    volume.setUpdater("系统");
                    volume.setUpdateTime(new Date());
                    if (i == 0 || i > 6) {
                        volume.setValleyValue(BigDecimal.ZERO);
                        volume.setPassValue(BigDecimal.ZERO);
                        volume.setPeakValue(BigDecimal.ZERO);
                    } else {
                        volume.setValleyValue(new BigDecimal(data.get((i - 1) * 3)).add(BigDecimal.ONE));
                        volume.setPassValue(new BigDecimal(data.get((i - 1) * 3)).add(BigDecimal.valueOf(2)));
                        volume.setPeakValue(new BigDecimal(data.get(i * 3)));
                    }
                    volume.setCurrentWeekJson(ExcelReadTest.getWeekEveryDayCountStr(volume));
                    log.info("解析完一条数据:{}", JSON.toJSONString(volume));
                    // 数据转换
                    cachedDataList.add(volume);
                }
                if (cachedDataList.size() >= GlobalConsts.NUM_200) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(GlobalConsts.NUM_200);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
            }


        }).sheet().headRowNumber(2).doRead();
    }

    /**
     * 描述: 获取自然周每天单量-字符串
     *
     * @param item
     * @return {@link String}
     * @author: HuangChao
     * @since: 2023/7/24 14:44
     */
    public static String getWeekEveryDayCountStr(ConfDistributeSingleVolume item) {
        // 获取对应值的自然周每天单量
        Map<Integer, Integer> valleyValueweekMap = getWeekEveryDayCountMap(item.getValleyValue());
        Map<Integer, Integer> passValueweekMap = getWeekEveryDayCountMap(item.getPassValue());
        Map<Integer, Integer> peakValueweekMap = getWeekEveryDayCountMap(item.getPeakValue());

        // 合并对象
        Map<Integer, ConfDistributeSingleVolumeBO> weepMap = new HashMap<>(7);
        for (int i = 1; i <= 7; i++) {
            ConfDistributeSingleVolumeBO singleVolumeBO = new ConfDistributeSingleVolumeBO();
            singleVolumeBO.setValleyValue(valleyValueweekMap.getOrDefault(i, 0));
            singleVolumeBO.setPassValue(passValueweekMap.getOrDefault(i, 0));
            singleVolumeBO.setPeakValue(peakValueweekMap.getOrDefault(i, 0));
            weepMap.put(i, singleVolumeBO);
        }
        return JSON.toJSONString(weepMap);
    }

    /**
     * 描述: 获取自然周每天单量map
     *
     * @param value
     * @return {@link Map< Integer, Integer>}
     * @author: HuangChao
     * @since: 2023/7/5 18:49
     */
    public static Map<Integer, Integer> getWeekEveryDayCountMap(BigDecimal value) {
        BigDecimal week = new BigDecimal(7);
        List<Integer> weekList = Arrays.asList(1, 3, 5, 7, 2, 4, 6);

        // 每周单量
        Map<Integer, Integer> weekMap = new HashMap<>();
        // 计算每个的周期划分的值
        // 计算公式 七天单量平均到七天 多余单量按 1、3、5、7、2、4、6 分布

        int remainder = value.remainder(week).setScale(0, RoundingMode.CEILING).intValue();
        int everyDayCount = value.divide(week, 0, RoundingMode.DOWN).intValue();

        for (int i = 1; i <= 7; i++) {
            weekMap.put(i, everyDayCount);
        }


        // 多余单量按 1、3、5、7、2、4、6 分布
        if (remainder > 0) {
            for (int i = 0; i < remainder; i++) {
                Integer index = weekList.get(i);
                weekMap.put(index, weekMap.get(index) + 1);
            }
        }
        return weekMap;
    }
}
