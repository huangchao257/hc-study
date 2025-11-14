package com.hc.api.faceplusplus;

/**
 * 类描述: 旷视 Face++ Api常量类
 *
 * @author: HuangChao
 * @since: 2025/02/25
 */
public class FacePlusPlusApiConsts {

    /**
     * 域名
     */
    public static final String DOMAIN_NAME = "https://api-cn.faceplusplus.com";

    /**
     * 基础请求前缀
     */
    public static final String BASIC_REQUEST_PREFIX = "/facepp/v3";

    /**
     * 创建人脸库接口地址
     */
    public static final String CREATE_FACE_SET_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/faceset/create";
    /**
     * 删除人脸库接口地址
     */
    public static final String DELETE_FACE_SET_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/faceset/delete";

    /**
     * 人脸检测
     */
    public static final String DETECT_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/detect";

    /**
     * 添加人脸
     */
    public static final String ADD_FACE_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/faceset/addface";

    /**
     * 搜索人脸
     */
    public static final String SEARCH_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/search";
    /**
     * 人脸库详情
     */
    public static final String FACE_GET_DETAIL_URL = DOMAIN_NAME + BASIC_REQUEST_PREFIX + "/faceset/getdetail";

}
