package com.hc.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
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
    public void testConfDistributeSingleVolumeExcel() {
        // 读取的excel文件路径
        String fileName = "file/人单值-2024-04-25_修改版.xlsx";
        // 读取指定sheet页名称
        String sheetName = "Result 1";

        Function<Map, List<ConfDistributeSingleVolume>> function = data -> coverList(data);
        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> confDistributeSingleVolumeMapper.insertBatch(list);
//        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> System.out.println("list = " + list);
        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getReadListener(function, consumer))
                .sheet(sheetName).headRowNumber(1).doRead();

    }

    /**
     * 采用解耦的自定义监听器读取Excel, 可以实现任何数据模型bean的读取
     */
    @Test
    public void testConfDistributeSingleVolumeExcel1() {
        // 读取的excel文件路径
        String fileName = "file/标准人单值调整.xlsx";
        // 读取指定sheet页名称
        String sheetName = "Sheet1";

        Function<Map, List<ConfDistributeSingleVolume>> function = data -> coverList1(data);
        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> confDistributeSingleVolumeMapper.insertBatch(list);
//        Consumer<List<ConfDistributeSingleVolume>> consumer = list -> System.out.println("list = " + list);
        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getReadListener(function, consumer))
                .sheet(sheetName).headRowNumber(1).doRead();


    }

    @Test
    public void testEngineerTakeConfExcel() {
        // 读取的excel文件路径
        String fileName = "file/北京当日值&次日值调整明细_12月(1).xlsx";
        // 读取指定sheet页名称
        String updateSql = "update biz_engineer.engineer_take_config set day_distribute_num = %s, next_day_distribute_num = %s where engineer_id in (%s);";
        String whereSql = "SELECT\n" +
                "    B.engineer_id\n" +
                "FROM biz_grade.`engineer_hierarchy_extend` H, base_engineer.`engineer_basic_info` B\n" +
                "WHERE B.`engineer_id` = H.`engineer_id`\n" +
                "  AND H.hierarchy_product_group_id = %s AND H.hierarchy_level = %s\n" +
                "  AND B.`oa_status` IN (1,2) AND B.`city_id` = 110100 AND sub_company_id = 10017";
        String sheetName = "Sheet1";
        Function<Map, List<String>> function = data -> {
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= 7; i++) {
                String sql = String.format(updateSql, data.get(i), data.get(i), String.format(whereSql, data.get(0), i));
                list.add(sql);
            }
            return list;
        };

        Consumer<List<String>> consumer = list -> list.forEach(System.out::println);

        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getReadListener(function, consumer))
                .sheet(sheetName).headRowNumber(2).doRead();


    }

    @Test
    public void testEngineerTakeConfExcel1() {
        // 读取的excel文件路径
        String fileName = "file/北京当日值&次日值调整明细_12月(1).xlsx";
        // 读取指定sheet页名称
        String updateSql = "update biz_engineer.engineer_take_config set day_distribute_num = %s, next_day_distribute_num = %s where engineer_id in (%s);";
        String whereSql = "SELECT\n" +
                "    B.engineer_id\n" +
                "FROM biz_grade.`engineer_hierarchy_extend` H, base_engineer.`engineer_basic_info` B\n" +
                "WHERE B.`engineer_id` = H.`engineer_id`\n" +
                "  AND H.hierarchy_product_group_id = %s AND H.hierarchy_level = %s\n" +
                "  AND B.`oa_status` IN (1,2) AND B.`city_id` = 110100 AND sub_company_id = 10017";
        String sheetName = "Sheet1";
        Function<Map, List<String>> function = data -> {
            List<String> list = new ArrayList<>();
            for (int i = 1; i <= 7; i++) {
//                String sql = String.format(updateSql, data.get(i), data.get(i), String.format(whereSql, data.get(0), i));
//                list.add(sql);
                String str = String.format("%s,%s,%s", data.get(0), i, data.get(i));
                list.add(str);
            }
            return list;
        };

        Consumer<List<String>> consumer = list -> list.forEach(System.out::println);

        // 读取excel
        EasyExcel.read(fileName,
                        EasyExcelUtils.getReadListener(function, consumer))
                .sheet(sheetName).headRowNumber(2).doRead();


    }

    @Test
    void test() {
        int[] confIdList = {1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 62, 63, 64, 65, 66, 67, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490, 491, 492, 493, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 529, 530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569, 570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 600, 601, 602, 603, 604, 605, 606, 607, 608, 609, 610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 641, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 656, 657, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671, 672, 673, 674, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686, 687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729, 730, 731, 732, 733, 734, 735, 736, 737, 738, 739, 740, 741, 742, 743, 744};
        String orderConf = "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater,  create_time, updater, update_time) VALUES (%s, %s, '{\"firstDay\":3,\"firstOrderNum\":1,\"secondDay\":8,\"secondOrderNum\":2,\"thirdOrderNum\":3}', 80, '批量配置数据', NOW(), '批量配置数据', NOW());";
        String[] categList = {
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1004, 2816, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1003, 3131, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1003, 2743, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1003, 3132, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1003, 2709, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4207, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2756, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2755, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2754, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 3131, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2746, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4173, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2737, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2727, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4186, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2717, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2706, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2705, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2720, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2744, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2743, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2728, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2758, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2729, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2745, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2757, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 3132, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2721, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2740, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2726, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2748, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2742, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4185, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2877, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2735, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4205, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2730, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2733, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2709, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2764, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2759, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4184, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2710, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 4040, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2749, 90, '批量配置数据', NOW(), '批量配置数据', NOW());",
                "INSERT INTO biz_serv_work_distribute.conf_distribute_common_relation (biz_id, map_key, map_value, data_type, creater, create_time, updater, update_time) VALUES (%s,1002, 2751, 90, '批量配置数据', NOW(), '批量配置数据', NOW());"};

        List<List<String>> sqlList = new ArrayList<>(10000);
        for (int i : confIdList) {
            sqlList.add(Collections.singletonList(String.format(orderConf, i, i)));
            for (String j : categList) {
                sqlList.add(Collections.singletonList(String.format(j, i)));
            }
        }

        // 创建ExcelWriter对象
        String fileName = "sql.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();

        // 创建Sheet并设置表头
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        List<List<String>> head = new ArrayList<>();
        head.add(Arrays.asList("sql"));
        excelWriter.write(head, writeSheet);

        // 写入数据
        excelWriter.write(sqlList, writeSheet);

        // 关闭ExcelWriter
        excelWriter.finish();
    }

    /**
     * 描述: 转换数据集合
     *
     * @param data
     * @return {@link List< ConfDistributeSingleVolume>}
     * @author: HuangChao
     * @since: 2023/8/4 19:17
     */
    private List<ConfDistributeSingleVolume> coverList(Map<Integer, String> data) {

        List<ConfDistributePlan> confList = confDistributePlanMapper.selectListByQuery(QueryWrapper.create().select(CONF_DISTRIBUTE_PLAN.CONF_ID).where(CONF_DISTRIBUTE_PLAN.CITY_ID.eq(data.get(0))));
        if (CollectionUtils.isEmpty(confList)) {
            log.info("城市[{}]无派单配置", data.get(0));
            return Collections.emptyList();
        }

        List<Integer> confIdList = confList.stream().map(ConfDistributePlan::getConfId).collect(Collectors.toList());
        List<ConfDistributeSingleVolume> cachedDataList = ListUtils.newArrayListWithExpectedSize(GlobalConsts.NUM_200);
        for (Integer confId : confIdList) {
            try {
                ConfDistributeSingleVolume volume = new ConfDistributeSingleVolume();
                volume.setConfId(confId);
                volume.setProductGroupId(Integer.valueOf(data.get(3)));
                volume.setEngineerLevel(Integer.valueOf(data.get(5)));
                volume.setType(10);
                volume.setCreater("批量配置20240426");
                volume.setCreateTime(new Date());
                volume.setUpdater("批量配置20240426");
                volume.setUpdateTime(new Date());
//                volume.setValleyValue(new BigDecimal(data.get(i * 3 - 1)));
//                volume.setPassValue(new BigDecimal(data.get(i * 3)));
//                volume.setPeakValue(new BigDecimal(data.get(i * 3 + 1)));
                volume.setValleyValue(new BigDecimal(data.get(6)));
                volume.setPassValue(new BigDecimal(data.get(7)));
                volume.setPeakValue(new BigDecimal(data.get(8)));
                volume.setCurrentWeekJson(ExcelReadTest.getWeekEveryDayCountStr(volume));
                // 数据转换
                cachedDataList.add(volume);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        log.info("解析完一条完整数据:{}", JSON.toJSONString(cachedDataList));

        return cachedDataList;
    }

    /**
     * 描述: 转换数据集合
     *
     * @param data
     * @return {@link List< ConfDistributeSingleVolume>}
     * @author: HuangChao
     * @since: 2023/8/4 19:17
     */
    private List<ConfDistributeSingleVolume> coverList1(Map<Integer, String> data) {

        List<ConfDistributePlan> confList = confDistributePlanMapper.selectListByQuery(QueryWrapper.create().select(CONF_DISTRIBUTE_PLAN.CONF_ID).where(CONF_DISTRIBUTE_PLAN.CITY_NAME.eq(data.get(0))));
        if (CollectionUtils.isEmpty(confList)) {
            log.info("城市[{}]无派单配置", data.get(0));
            return Collections.emptyList();
        }

        List<Integer> confIdList = confList.stream().map(ConfDistributePlan::getConfId).collect(Collectors.toList());
        List<ConfDistributeSingleVolume> cachedDataList = ListUtils.newArrayListWithExpectedSize(GlobalConsts.NUM_200);
        for (Integer confId : confIdList) {
            try {
                ConfDistributeSingleVolume volume = new ConfDistributeSingleVolume();
                volume.setConfId(confId);
                volume.setProductGroupId(Integer.valueOf(data.get(2)));
                volume.setEngineerLevel(Integer.valueOf(data.get(3)));
                volume.setType(10);
                volume.setCreater("系统");
                volume.setCreateTime(new Date());
                volume.setUpdater("系统");
                volume.setUpdateTime(new Date());
                volume.setValleyValue(new BigDecimal(data.get(4)));
                volume.setPassValue(new BigDecimal(data.get(5)));
                volume.setPeakValue(new BigDecimal(data.get(6)));
                volume.setCurrentWeekJson(ExcelReadTest.getWeekEveryDayCountStr(volume));
                // 数据转换
                cachedDataList.add(volume);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        log.info("解析完一条完整数据:{}", JSON.toJSONString(cachedDataList));

        return cachedDataList;
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
