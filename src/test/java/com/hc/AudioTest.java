package com.hc;

import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.HashMap;

/**
 * 类描述:
 *
 * @author: HuangChao
 * @since: 2025/04/02
 */
@Slf4j
@SpringBootTest
public class AudioTest {

    @Test
    void test() throws Exception{
        // 获取音频文件
        MP3File mp3File = new MP3File("file/20250401152245_144993.aac");
        MP3AudioHeader header = mp3File.getMP3AudioHeader();

        System.out.println("header = " + header);

        HashMap<String, String> map = new HashMap<String,String>();
        map.put("duration", String.valueOf(header.getTrackLength())); //获得时长
        map.put("bitRate", String.valueOf(header.getBitRate())); //获得比特率
        map.put("trackSize", String.valueOf(header.getTrackLength())); //音轨长度
        map.put("format", String.valueOf(header.getFormat())); //格式，例 mp3\wav
        map.put("channel", String.valueOf(header.getChannels())); //声道
        map.put("sampleRate", String.valueOf(header.getSampleRate())); //采样率
        map.put("mpegLayer", String.valueOf(header.getMpegLayer())); //MPEG
        map.put("mp3StartByte", String.valueOf(header.getMp3StartByte())); //MP3起始字节
        map.put("preciseTrackSize", String.valueOf(header.getPreciseTrackLength())); //精确的音轨长度
        System.out.println("map = " + map);

    }

    @Test
    void test1() throws Exception {
        File audioFile = new File("file/20250401152245_144993.aac");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
    }

    @Test
    void test2() throws EncoderException {

        File aacFile = new File("file/20250401152245_144993.aac");
        MultimediaObject media = new MultimediaObject(aacFile);
        MultimediaInfo info = media.getInfo();
        Encoder encoder = new Encoder();
        long duration = info.getDuration() / 1000; // 转换为秒
        System.out.println("时长：" + duration + "秒");
    }

    @Test
    void test3() throws Exception {
        try {
            File file = new File("file/20250401152245_144993.aac");
            MultimediaObject media = new MultimediaObject(file);
            MultimediaInfo info = media.getInfo();
            long durationMs = info.getDuration(); // 单位：毫秒
            System.out.println("时长（秒）: " + durationMs / 1000.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
