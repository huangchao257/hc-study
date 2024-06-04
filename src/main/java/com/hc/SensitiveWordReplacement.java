package com.hc;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class SensitiveWordReplacement {
    public static void main(String[] args) {
        String directoryPath = "E:\\project\\zmn-delivery-monitoring"; // 替换的目录路径
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
                    if (file.getName().equals(".idea")) {
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
            // 读取文件的所有行
            List<String> lines = Files.readAllLines(file.toPath());

            // 替换敏感词
            for (int i = 0; i < lines.size(); i++) {
                lines.set(i, lines.get(i).replaceAll(sensitiveWord, replacement));
            }

            boolean lastLineEmpty = false;
            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                // 检查文件是否以换行符结尾
                long fileLength = raf.length();
                if (fileLength > 0) {

                    raf.seek(fileLength - 1);
                    int lastByte = raf.read();
                    if (lastByte == '\n' || lastByte == '\r') {
                        lastLineEmpty = true;
                    }
                }
            } catch (IOException e) {
                System.out.println("读取文件时发生错误：" + file.getAbsolutePath());
                e.printStackTrace();
            }


            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // 写入处理后的行
            for (int i = 0; i < lines.size(); i++) {
                writer.write(lines.get(i));
                if (i < lines.size() - 1 || lastLineEmpty) {
                    writer.newLine();
                }
            }

            writer.close();
            System.out.println("替换完成：" + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("替换文件时发生错误：" + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static List<String> getSensitiveWords() {
        String sensitiveWordsStr = "公司,总司,网点负责人,区域经理,线上面试,线下面试,电话面试,视频面试,入职,应聘,聘用,录用,永不录用,转正,在职工程师,自营工程师,罚款,全体罚款,子公司罚款,处罚,扣罚,扣款,罚,规章制度,红黄线原则,制度,管理规范,公司规定,工衣,工作服装,工服,工作服,工装,工作装备,接单,全职接单,专职接单,唯一接单平台,派单,改派,培训,培训销售技能,培训审批,定责,出勤,奖励,挑单,拒单";

        String[] split = sensitiveWordsStr.split(",");
        List<String> sensitiveWordList = Arrays.asList(split);
        return sensitiveWordList;
    }

    private static List<String> getRepalcement() {
        String repalcementStr = "平台,平台,服务商,服务商,线上沟通,线下沟通,电话沟通,视频沟通,合作,合作,合作,合作,永不合作,工程师无试用期,合作工程师,合作工程师,承担违约金,承担违约金,承担违约金,承担违约责任,承担违约责任,承担违约责任,承担违约责任,合作约定,合作约定,合作约定,平台规则、合作约定,合作约定,服装,服装,服装,服装,服装,装备,领单,自由领单,自由领单,领单平台,推单,转单,交流技术,交流技术,技术测试,按过错追究违约责任,在线,酬谢,自由领单,领单后无故拒单";


        String[] split = repalcementStr.split(",");
        return Arrays.asList(split);
    }
}