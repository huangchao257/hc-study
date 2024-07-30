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
        city_conf_map.put(110100, 32);
        city_conf_map.put(120100, 37);
        city_conf_map.put(130100, 46);
        city_conf_map.put(130200, 65);
        city_conf_map.put(130300, 60);
        city_conf_map.put(130400, 74);
        city_conf_map.put(130500, 69);
        city_conf_map.put(130600, 51);
        city_conf_map.put(130700, 289);
        city_conf_map.put(130800, 292);
        city_conf_map.put(130900, 334);
        city_conf_map.put(131000, 54);
        city_conf_map.put(131100, 346);
        city_conf_map.put(140100, 16);
        city_conf_map.put(140200, 506);
        city_conf_map.put(140300, 509);
        city_conf_map.put(140400, 512);
        city_conf_map.put(140500, 514);
        city_conf_map.put(140600, 517);
        city_conf_map.put(140700, 520);
        city_conf_map.put(140800, 524);
        city_conf_map.put(140900, 526);
        city_conf_map.put(141000, 530);
        city_conf_map.put(141100, 535);
        city_conf_map.put(150100, 172);
        city_conf_map.put(150200, 177);
        city_conf_map.put(150300, 541);
        city_conf_map.put(150400, 546);
        city_conf_map.put(150500, 551);
        city_conf_map.put(150600, 554);
        city_conf_map.put(150700, 559);
        city_conf_map.put(150800, 564);
        city_conf_map.put(150900, 567);
        city_conf_map.put(152200, 571);
        city_conf_map.put(152500, 575);
        city_conf_map.put(152900, 594);
        city_conf_map.put(210100, 13);
        city_conf_map.put(210200, 244);
        city_conf_map.put(210300, 246);
        city_conf_map.put(210400, 439);
        city_conf_map.put(210500, 442);
        city_conf_map.put(210600, 445);
        city_conf_map.put(210700, 449);
        city_conf_map.put(210800, 455);
        city_conf_map.put(210900, 459);
        city_conf_map.put(211000, 462);
        city_conf_map.put(211100, 465);
        city_conf_map.put(211200, 469);
        city_conf_map.put(211300, 472);
        city_conf_map.put(211400, 474);
        city_conf_map.put(220100, 4);
        city_conf_map.put(220200, 598);
        city_conf_map.put(220300, 602);
        city_conf_map.put(220400, 606);
        city_conf_map.put(220500, 610);
        city_conf_map.put(220600, 614);
        city_conf_map.put(220700, 620);
        city_conf_map.put(220800, 622);
        city_conf_map.put(222400, 626);
        city_conf_map.put(230100, 42);
        city_conf_map.put(230200, 418);
        city_conf_map.put(230300, 423);
        city_conf_map.put(230400, 427);
        city_conf_map.put(230500, 430);
        city_conf_map.put(230600, 435);
        city_conf_map.put(230700, 476);
        city_conf_map.put(230800, 481);
        city_conf_map.put(230900, 484);
        city_conf_map.put(231000, 487);
        city_conf_map.put(231100, 491);
        city_conf_map.put(231200, 497);
        city_conf_map.put(232700, 500);
        city_conf_map.put(310100, 9);
        city_conf_map.put(320100, 18);
        city_conf_map.put(320200, 99);
        city_conf_map.put(320300, 26);
        city_conf_map.put(320400, 8);
        city_conf_map.put(320500, 118);
        city_conf_map.put(320600, 52);
        city_conf_map.put(320700, 44);
        city_conf_map.put(320800, 252);
        city_conf_map.put(320900, 43);
        city_conf_map.put(321000, 72);
        city_conf_map.put(321100, 50);
        city_conf_map.put(321200, 62);
        city_conf_map.put(321300, 137);
        city_conf_map.put(330100, 257);
        city_conf_map.put(330200, 157);
        city_conf_map.put(330300, 163);
        city_conf_map.put(330400, 261);
        city_conf_map.put(330500, 259);
        city_conf_map.put(330600, 265);
        city_conf_map.put(330700, 263);
        city_conf_map.put(330800, 295);
        city_conf_map.put(330900, 301);
        city_conf_map.put(331000, 255);
        city_conf_map.put(331100, 306);
        city_conf_map.put(340100, 7);
        city_conf_map.put(340200, 151);
        city_conf_map.put(340300, 176);
        city_conf_map.put(340400, 307);
        city_conf_map.put(340500, 248);
        city_conf_map.put(340600, 310);
        city_conf_map.put(340700, 316);
        city_conf_map.put(340800, 321);
        city_conf_map.put(341000, 326);
        city_conf_map.put(341100, 333);
        city_conf_map.put(341200, 250);
        city_conf_map.put(341300, 342);
        city_conf_map.put(341500, 159);
        city_conf_map.put(341600, 352);
        city_conf_map.put(341700, 359);
        city_conf_map.put(341800, 369);
        city_conf_map.put(350100, 142);
        city_conf_map.put(350200, 147);
        city_conf_map.put(350300, 105);
        city_conf_map.put(350400, 293);
        city_conf_map.put(350500, 103);
        city_conf_map.put(350600, 113);
        city_conf_map.put(350700, 296);
        city_conf_map.put(350800, 111);
        city_conf_map.put(350900, 298);
        city_conf_map.put(360100, 25);
        city_conf_map.put(360200, 273);
        city_conf_map.put(360300, 275);
        city_conf_map.put(360400, 181);
        city_conf_map.put(360500, 277);
        city_conf_map.put(360600, 380);
        city_conf_map.put(360700, 269);
        city_conf_map.put(360800, 271);
        city_conf_map.put(360900, 279);
        city_conf_map.put(361000, 267);
        city_conf_map.put(361100, 281);
        city_conf_map.put(370100, 10);
        city_conf_map.put(370200, 15);
        city_conf_map.put(370300, 119);
        city_conf_map.put(370400, 357);
        city_conf_map.put(370500, 374);
        city_conf_map.put(370600, 19);
        city_conf_map.put(370700, 24);
        city_conf_map.put(370800, 107);
        city_conf_map.put(370900, 383);
        city_conf_map.put(371000, 30);
        city_conf_map.put(371100, 387);
        city_conf_map.put(371300, 21);
        city_conf_map.put(371400, 390);
        city_conf_map.put(371500, 392);
        city_conf_map.put(371600, 395);
        city_conf_map.put(371700, 397);
        city_conf_map.put(410100, 41);
        city_conf_map.put(410200, 64);
        city_conf_map.put(410300, 73);
        city_conf_map.put(410400, 410);
        city_conf_map.put(410500, 416);
        city_conf_map.put(410600, 419);
        city_conf_map.put(410700, 422);
        city_conf_map.put(410800, 425);
        city_conf_map.put(410900, 431);
        city_conf_map.put(411000, 434);
        city_conf_map.put(411100, 438);
        city_conf_map.put(411200, 444);
        city_conf_map.put(411300, 79);
        city_conf_map.put(411400, 448);
        city_conf_map.put(411500, 451);
        city_conf_map.put(411600, 454);
        city_conf_map.put(411700, 458);
        city_conf_map.put(411900, 463);
        city_conf_map.put(420100, 186);
        city_conf_map.put(420200, 468);
        city_conf_map.put(420300, 477);
        city_conf_map.put(420500, 86);
        city_conf_map.put(420600, 89);
        city_conf_map.put(420700, 480);
        city_conf_map.put(420800, 485);
        city_conf_map.put(420900, 92);
        city_conf_map.put(421000, 96);
        city_conf_map.put(421100, 490);
        city_conf_map.put(421200, 493);
        city_conf_map.put(421300, 496);
        city_conf_map.put(422800, 501);
        city_conf_map.put(429100, 504);
        city_conf_map.put(430100, 38);
        city_conf_map.put(430200, 178);
        city_conf_map.put(430300, 189);
        city_conf_map.put(430400, 194);
        city_conf_map.put(430500, 537);
        city_conf_map.put(430600, 542);
        city_conf_map.put(430700, 200);
        city_conf_map.put(430800, 547);
        city_conf_map.put(430900, 552);
        city_conf_map.put(431000, 203);
        city_conf_map.put(431100, 557);
        city_conf_map.put(431200, 578);
        city_conf_map.put(431300, 581);
        city_conf_map.put(433100, 584);
        city_conf_map.put(440100, 93);
        city_conf_map.put(440200, 100);
        city_conf_map.put(440300, 124);
        city_conf_map.put(440400, 205);
        city_conf_map.put(440500, 120);
        city_conf_map.put(440600, 117);
        city_conf_map.put(440700, 123);
        city_conf_map.put(440800, 242);
        city_conf_map.put(440900, 237);
        city_conf_map.put(441200, 239);
        city_conf_map.put(441300, 145);
        city_conf_map.put(441400, 302);
        city_conf_map.put(441500, 308);
        city_conf_map.put(441600, 136);
        city_conf_map.put(441700, 126);
        city_conf_map.put(441800, 133);
        city_conf_map.put(441900, 59);
        city_conf_map.put(442000, 108);
        city_conf_map.put(445100, 312);
        city_conf_map.put(445200, 317);
        city_conf_map.put(445300, 328);
        city_conf_map.put(450100, 83);
        city_conf_map.put(450200, 283);
        city_conf_map.put(450300, 285);
        city_conf_map.put(450400, 332);
        city_conf_map.put(450500, 337);
        city_conf_map.put(450600, 343);
        city_conf_map.put(450700, 348);
        city_conf_map.put(450800, 353);
        city_conf_map.put(450900, 287);
        city_conf_map.put(451000, 360);
        city_conf_map.put(451100, 364);
        city_conf_map.put(451200, 368);
        city_conf_map.put(451300, 372);
        city_conf_map.put(451400, 377);
        city_conf_map.put(460100, 76);
        city_conf_map.put(460200, 94);
        city_conf_map.put(460300, 508);
        city_conf_map.put(460400, 516);
        city_conf_map.put(460500, 523);
        city_conf_map.put(460600, 536);
        city_conf_map.put(460700, 543);
        city_conf_map.put(460800, 555);
        city_conf_map.put(460900, 561);
        city_conf_map.put(461000, 566);
        city_conf_map.put(461100, 572);
        city_conf_map.put(461200, 576);
        city_conf_map.put(461300, 582);
        city_conf_map.put(461400, 587);
        city_conf_map.put(461500, 589);
        city_conf_map.put(461600, 591);
        city_conf_map.put(461700, 593);
        city_conf_map.put(462000, 597);
        city_conf_map.put(500100, 1);
        city_conf_map.put(510100, 22);
        city_conf_map.put(510300, 231);
        city_conf_map.put(510400, 319);
        city_conf_map.put(510500, 224);
        city_conf_map.put(510600, 222);
        city_conf_map.put(510700, 217);
        city_conf_map.put(510800, 234);
        city_conf_map.put(510900, 6);
        city_conf_map.put(511000, 226);
        city_conf_map.put(511100, 167);
        city_conf_map.put(511300, 220);
        city_conf_map.put(511400, 215);
        city_conf_map.put(511500, 229);
        city_conf_map.put(511600, 218);
        city_conf_map.put(511700, 170);
        city_conf_map.put(511800, 225);
        city_conf_map.put(511900, 331);
        city_conf_map.put(512000, 692);
        city_conf_map.put(513200, 695);
        city_conf_map.put(513300, 697);
        city_conf_map.put(513400, 699);
        city_conf_map.put(520100, 127);
        city_conf_map.put(520200, 141);
        city_conf_map.put(520300, 149);
        city_conf_map.put(520400, 601);
        city_conf_map.put(522200, 144);
        city_conf_map.put(522300, 605);
        city_conf_map.put(522400, 608);
        city_conf_map.put(522600, 612);
        city_conf_map.put(522700, 617);
        city_conf_map.put(530100, 152);
        city_conf_map.put(530300, 154);
        city_conf_map.put(530400, 619);
        city_conf_map.put(530500, 625);
        city_conf_map.put(530600, 164);
        city_conf_map.put(530700, 629);
        city_conf_map.put(530800, 632);
        city_conf_map.put(530900, 641);
        city_conf_map.put(532300, 644);
        city_conf_map.put(532500, 656);
        city_conf_map.put(532600, 658);
        city_conf_map.put(532800, 660);
        city_conf_map.put(532900, 662);
        city_conf_map.put(533100, 664);
        city_conf_map.put(533300, 666);
        city_conf_map.put(533400, 668);
        city_conf_map.put(540100, 670);
        city_conf_map.put(542100, 674);
        city_conf_map.put(542200, 678);
        city_conf_map.put(542300, 680);
        city_conf_map.put(542400, 683);
        city_conf_map.put(542500, 687);
        city_conf_map.put(542600, 689);
        city_conf_map.put(610100, 160);
        city_conf_map.put(610200, 399);
        city_conf_map.put(610300, 196);
        city_conf_map.put(610400, 209);
        city_conf_map.put(610500, 207);
        city_conf_map.put(610600, 211);
        city_conf_map.put(610700, 198);
        city_conf_map.put(610800, 213);
        city_conf_map.put(610900, 191);
        city_conf_map.put(611000, 401);
        city_conf_map.put(620100, 82);
        city_conf_map.put(620200, 320);
        city_conf_map.put(620300, 338);
        city_conf_map.put(620400, 349);
        city_conf_map.put(620500, 358);
        city_conf_map.put(620600, 366);
        city_conf_map.put(620700, 373);
        city_conf_map.put(620800, 382);
        city_conf_map.put(620900, 389);
        city_conf_map.put(621000, 403);
        city_conf_map.put(621100, 405);
        city_conf_map.put(621200, 407);
        city_conf_map.put(622900, 409);
        city_conf_map.put(623000, 412);
        city_conf_map.put(630100, 165);
        city_conf_map.put(632100, 643);
        city_conf_map.put(632200, 646);
        city_conf_map.put(632300, 648);
        city_conf_map.put(632500, 650);
        city_conf_map.put(632600, 652);
        city_conf_map.put(632700, 654);
        city_conf_map.put(632800, 673);
        city_conf_map.put(640100, 173);
        city_conf_map.put(640200, 631);
        city_conf_map.put(640300, 637);
        city_conf_map.put(640400, 634);
        city_conf_map.put(640500, 639);
        city_conf_map.put(650100, 184);
        city_conf_map.put(650200, 717);
        city_conf_map.put(652100, 713);
        city_conf_map.put(652200, 686);
        city_conf_map.put(652300, 719);
        city_conf_map.put(652700, 721);
        city_conf_map.put(652800, 723);
        city_conf_map.put(652900, 725);
        city_conf_map.put(653000, 727);
        city_conf_map.put(653100, 729);
        city_conf_map.put(653200, 694);
        city_conf_map.put(654000, 731);
        city_conf_map.put(654200, 733);
        city_conf_map.put(654300, 735);
        city_conf_map.put(820042, 519);
        city_conf_map.put(820043, 531);
        city_conf_map.put(820054, 681);
        city_conf_map.put(820055, 737);
        city_conf_map.put(820056, 739);
        city_conf_map.put(820058, 702);
        city_conf_map.put(820060, 705);
        city_conf_map.put(820061, 707);
        city_conf_map.put(820063, 709);
        city_conf_map.put(820065, 711);
        city_conf_map.put(820066, 741);
        city_conf_map.put(442000540, 715);
        city_conf_map.put(442000543, 743);
    }

    //字母转数字  A-Z ：1-26
    public int letterToNumber(String letter) {
        int length = letter.length();
        int num = 0;
        int number = 0;
        for (int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        return number;
    }

    @Test
    void confDeliveryMetricsWeight() {
        // 读取的excel文件路径
        String fileName = "file/数据准备-18套方案测算.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(3).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            if (confId == null) {
                log.error("error cityId is {}", cityId);
            }
            item.setMapKey(data.get(5).toString());
            Map<String, Integer> map = new HashMap<>();
            map.put("successOrderCountWeight", Integer.parseInt(data.get(7).toString()));
            map.put("visitRateWeight", Integer.parseInt(data.get(8).toString()));
            map.put("successRateWeight", Integer.parseInt(data.get(9).toString()));
            map.put("completeAtvWeight", Integer.parseInt(data.get(10).toString()));

            item.setMapValue(JSON.toJSONString(map));
            // 获取方案类型并转换成数字
            int i = letterToNumber(data.get(0).toString());
            // 配置类型 * 10 + 方案数字
            item.setDataType(600 + i);
            item.setCreater("临时数据20240730");
            item.setCreateTime(new Date());
            item.setUpdater("临时数据20240730");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                // 读取指定sheet
                .sheet("指标权重")
                // 第2行为第一条数据
                .headRowNumber(1).doRead();
    }

    @Test
    void confDeliveryMetricsDay() {
        // 读取的excel文件路径
        String fileName = "file/数据准备-18套方案测算.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(3).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            if (confId == null) {
                log.error("error cityId is {}", cityId);
            }
            item.setMapKey(data.get(5).toString());
            Map<String, Integer> map = new HashMap<>();
            map.put("bizLine", Integer.parseInt(data.get(5).toString()));
            map.put("day", Integer.parseInt(data.get(7).toString()));
            map.put("baseCount", Integer.parseInt(data.get(8).toString()));
            map.put("successOrderCountDay", Integer.parseInt(data.get(9).toString()));
            map.put("visitRateDay", Integer.parseInt(data.get(10).toString()));
            map.put("successRateDay", Integer.parseInt(data.get(11).toString()));
            map.put("completeAtvDay", Integer.parseInt(data.get(12).toString()));
            item.setMapValue(JSON.toJSONString(map));
            // 获取方案类型并转换成数字
            int i = letterToNumber(data.get(0).toString());
            // 配置类型 * 10 + 方案数字
            item.setDataType(1000 + i);
            item.setCreater("临时数据20240730");
            item.setCreateTime(new Date());
            item.setUpdater("临时数据20240730");
            item.setUpdateTime(new Date());
            list.add(item);
            return list;
        };

        Consumer<List<ConfDistributeCommonRelationEntity>> consumer = list -> confDistributeCommonRelationMapper.insertBatch(list);

        // 读取excel
        EasyExcel.read(fileName, EasyExcelUtils.getReadListener(function, consumer))
                // 读取指定sheet
                .sheet("指标获取周期")
                // 第2行为第一条数据
                .headRowNumber(1).doRead();
    }

    @Test
    void testDistributeScoreMinGap() {
        // 读取的excel文件路径
        String fileName = "file/调整保底标准_2024年6月.xlsx";

        Function<Map, List<ConfDistributeCommonRelationEntity>> function = data -> {
            List<ConfDistributeCommonRelationEntity> list = new ArrayList<>();
            ConfDistributeCommonRelationEntity item = new ConfDistributeCommonRelationEntity();
            int cityId = Integer.parseInt(data.get(0).toString());
            Integer confId = city_conf_map.get(cityId);
            item.setBizId(confId);
            if (confId == null) {
                log.error("error cityId is {}", cityId);
            }
            item.setMapKey(data.get(2).toString());
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = 1; j < 11; j++) {
                map.put(j, Integer.parseInt(data.get(3 + j).toString()));
                item.setMapValue(JSON.toJSONString(map));
            }
            item.setDataType(20);
            item.setCreater("批量清洗数据20240622");
            item.setCreateTime(new Date());
            item.setUpdater("批量清洗数据20240622");
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
