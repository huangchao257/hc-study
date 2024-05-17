package com.hc;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SensitiveWordReplacement {
    public static void main(String[] args) {
        String directoryPath = "E:\\project\\hc-study\\src\\main\\java\\com\\hc\\util"; // 替换的目录路径
        List<String> sensitiveWordList = getSensitiveWords(); // 要替换的敏感字
        List<String> repalcementList = getRepalcement(); // 代替的内容
        System.out.println(repalcementList.size());

        File directory = new File(directoryPath);
        if (directory.isDirectory()) {

            for (int i = 0; i < sensitiveWordList.size(); i++) {
                processFiles(directory, sensitiveWordList.get(i), repalcementList.get(i));
            }
        } else {
            System.out.println("指定的路径不是一个目录");
        }
    }

    private static void processFiles(File directory, String sensitiveWord, String replacement) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().equals(".git")) {
                        continue;
                    }
                    if (file.getName().equals(".class")) {
                        continue;
                    }
                    processFiles(file, sensitiveWord, replacement);
                } else {
                    if (file.isFile() && file.getName().endsWith(".gitignore")) {
                        continue;
                    }
                    replaceSensitiveWord(file, sensitiveWord, replacement);
                }
            }
        }
    }

    private static void replaceSensitiveWord(File file, String sensitiveWord, String replacement) {
        try {
            Scanner scanner = new Scanner(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            boolean lastLineEmpty = false;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // 判断line是否有数据
                if (line != null) {
                    content.append(line.replaceAll(sensitiveWord, replacement));
                }

                // 检查是否是最后一行
                if (scanner.hasNextLine()) {
                    content.append(System.lineSeparator());
                } else {
                    lastLineEmpty = line.isEmpty();
                }
            }

            scanner.close();

            // 如果最后一行是空行，添加一个换行符
            if (lastLineEmpty) {
                content.append(System.lineSeparator());
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();

            System.out.println("替换完成：" + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("替换文件时发生错误：" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static List<String> getSensitiveWords() {
        String sensitiveWordsStr = "总司相关部门," +
                "子公司管理部," +
                "储备干部," +
                "储备人才," +
                "线上面试," +
                "线下面试," +
                "电话面试," +
                "视频面试," +
                "入职," +
                "应聘," +
                "聘用," +
                "录用," +
                "永不录用," +
                "转正," +
                "在职工程师," +
                "自营工程师," +
                "返岗," +
                "转岗," +
                "调岗," +
                "劳动合同," +
                "上岗," +
                "不在岗," +
                "坐班," +
                "上班," +
                "下班," +
                "值班," +
                "加班," +
                "考勤," +
                "工龄," +
                "离职," +
                "淘汰," +
                "开除," +
                "辞退," +
                "辞职," +
                "不能兼职," +
                "工资," +
                "薪资," +
                "提成," +
                "奖金," +
                "底薪," +
                "月薪," +
                "请假," +
                "放假," +
                "晋升," +
                "发展空间," +
                "发展机制," +
                "职业发展," +
                "成长之路," +
                "工牌," +
                "我的工牌," +
                "罚款," +
                "全体罚款," +
                "迟到罚款," +
                "子公司罚款," +
                "处罚," +
                "扣罚," +
                "扣款," +
                "依规扣除," +
                "招聘," +
                "急招," +
                "招人," +
                "高薪诚聘," +
                "同事," +
                "企业价值观," +
                "平台文化," +
                "竞聘," +
                "选拔," +
                "述职," +
                "领导," +
                "军令状," +
                "规章制度," +
                "红黄线原则," +
                "分配机制," +
                "分配规则," +
                "制度," +
                "管理规范," +
                "公司规定," +
                "工衣," +
                "工作服装," +
                "工服," +
                "工作服," +
                "工装," +
                "工作装备," +
                "人事," +
                "人事专员," +
                "人事办理签约," +
                "工作群," +
                "全职接单," +
                "专职接单," +
                "唯一接单平台," +
                "福利," +
                "按单提成," +
                "按单分成," +
                "计件分配," +
                "线下报道," +
                "司歌," +
                "天天来公司上下班打卡," +
                "您为啄木鸟培养的第X个人才," +
                "积极配合公司调度," +
                "严格按照公司各项服务流程给用户提供优质服务," +
                "配件报销," +
                "配件核销," +
                "配件初审," +
                "配件审核," +
                "虚假核销," +
                "培训销售技能," +
                "培训审批," +
                "作业审批," +
                "早会," +
                "晚会," +
                "开会," +
                "听从师父指派任务," +
                "迟到," +
                "早退," +
                "旷工," +
                "旷课," +
                "出勤," +
                "全勤," +
                "考勤," +
                "内采," +
                "外采," +
                "外报," +
                "职责," +
                "工作职责," +
                "安排计划," +
                "挑单," +
                "拒单," +
                "共事," +
                "内推," +
                "工作流程," +
                "业绩";

        String[] split = sensitiveWordsStr.split(",");
        List<String> sensitiveWordList = Arrays.asList(split);
        return sensitiveWordList;
    }

    private static List<String> getRepalcement() {
        String repalcementStr = "平台," +
                "区域运营方," +
                "优秀服务商," +
                "优秀服务商," +
                "线上沟通," +
                "线下沟通," +
                "电话沟通," +
                "视频沟通," +
                "合作," +
                "合作," +
                "合作," +
                "合作," +
                "永不合作," +
                "," +
                "合作工程师," +
                "合作工程师," +
                "恢复领单," +
                "迁走," +
                "迁走," +
                "合作协议," +
                "领单," +
                "不领单," +
                "领单," +
                "领单," +
                "不领单," +
                "领单," +
                "领单," +
                "," +
                "合作时长," +
                "长期停单," +
                "长期停单," +
                "长期停单," +
                "长期停单," +
                "长期停单," +
                "灵活领单," +
                "提留," +
                "提留," +
                "提留," +
                "提留," +
                "," +
                "月度提留," +
                "停单," +
                "," +
                "成长," +
                "合作优势," +
                "合作优势," +
                "合作优势," +
                "合作优势," +
                "信息," +
                "我的信息," +
                "承担违约金," +
                "承担违约金," +
                "," +
                "承担违约金," +
                "承担违约责任," +
                "承担违约责任," +
                "承担违约责任," +
                "按平台规则与合作约定承担违约责任," +
                "招募," +
                "招募," +
                "招募工程师," +
                "," +
                "," +
                "平台规则," +
                "平台规则," +
                "竞争," +
                "报名," +
                "服务商评审," +
                "," +
                "," +
                "平台规则," +
                "平台规则," +
                "平台规则," +
                "平台规则," +
                "平台规则," +
                "平台规则," +
                "平台规则," +
                "服装," +
                "服装," +
                "服装," +
                "服装," +
                "服装," +
                "装备," +
                "平台人员," +
                "平台人员," +
                "签约," +
                "合作沟通群," +
                "自由领单," +
                "自由领单," +
                "领单平台," +
                "合作优势," +
                "按单结算," +
                "按单结算," +
                "按单结算," +
                "线下签约," +
                "《啄木鸟之歌》," +
                "自由领单," +
                "您是啄木鸟平台注册的第XX工程师," +
                "支持平台," +
                "按照平台规则为用户提供优质服务," +
                "服务费优惠申请," +
                "服务费优惠核验," +
                "服务费优惠核验," +
                "服务费优惠核验," +
                "服务费优惠核验," +
                "学习," +
                "技术测试," +
                "技术测试," +
                "技术交流," +
                "技术交流," +
                "技术交流," +
                "领单后完成服务," +
                "," +
                "," +
                "," +
                "不参加、未完成安全学习," +
                "领单," +
                "," +
                "," +
                "笨鸟商城自采," +
                "非笨鸟商城自采," +
                "服务费优惠申请," +
                "义务," +
                "合作义务," +
                "学习交流," +
                "自由领单," +
                "领单后无故拒单," +
                "合作," +
                "推荐," +
                "服务流程," +
                "服务成果";


        String[] split = repalcementStr.split(",");
        return Arrays.asList(split);
    }
}