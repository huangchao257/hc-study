package com.hc.api.compreface;

/**
 * 类描述: CompreFace常量
 *
 * @author HuangChao
 * @since 2025/11/10
 */
public abstract class CompreFaceConsts {
    /**
     * CompreFace API路径 (TODO 后续支持 nacos 配置)
     */
    public static final String COMPRE_FACE_API_PATH = "http://192.168.90.156:8000/api/v1";

    /**
     * CompreFace API密钥 (TODO 后续支持 nacos 配置)
     */
    public static final String COMPRE_FACE_API_KEY = "085d4fa5-972a-4ee6-a736-4d95e9ea4500";

    /**
     * CompreFace 主题
     */
    public static final String COMPRE_FACE_RECOGNITION_SUBJECTS_PATH = COMPRE_FACE_API_PATH + "/recognition/subjects";

    /**
     * CompreFace 人脸
     */
    public static final String COMPRE_FACE_RECOGNITION_SUBJECTS_FACES_PATH = COMPRE_FACE_API_PATH + "/recognition/faces";

    /**
     * CompreFace 识别
     */
    public static final String COMPRE_FACE_RECOGNITION_RECOGNIZE_PATH = COMPRE_FACE_API_PATH + "/recognition/recognize";
    }
