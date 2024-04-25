//package com.hc.util;
//
//import com.alibaba.csp.sentinel.annotation.SentinelResource;
//import com.alibaba.csp.sentinel.slots.block.BlockException;
//import com.alibaba.csp.sentinel.util.MethodUtil;
//import com.alibaba.csp.sentinel.util.StringUtil;
//import com.zmn.biz.serv.work.distribute.common.constant.DistributeErrorConsts;
//import com.zmn.biz.serv.work.distribute.model.BizServWorkDistributeException;
//import com.zmn.common.dto2.ResponseDTO;
//import com.zmn.common.exception.ErrorCodeTableFactory;
//import com.zmn.common.utils.number.NumberUtil;
//import com.zmn.common.utils.tag.TagUtil;
//import com.zmn.framework.sentinel.BlockExceptionWrapper;
//import com.zmn.framework.sentinel.BlockExceptionWrapperFactory;
//import com.zmn.framework.sentinel.constant.ZmnSentinelConstant;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.core.type.classreading.MetadataReader;
//import org.springframework.core.type.classreading.MetadataReaderFactory;
//import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
///**
// * 类描述: sentinel处理工具类
// *
// * @author: HuangChao
// * @since: 2024/02/27
// */
//@Slf4j
//public class SentinelHandlerUtil {
//
//
//    public final static String BLOCK_LOG_FORMAT = "[熔断]资源[{}]触发熔断规则[{}-{}]熔断处理!!!";
//    public final static String FALLBACK_LOG_FORMAT = "[{}]异常触发降级!!!";
//    public final static String BLOCK_LOG_NOTICE_FAIL_FORMAT = "[熔断]资源[{}]熔断规则[{}]触发异常通知失败!!!";
//
//    /**
//     * 描述: 抛出业务异常处理
//     *
//     * @param throwable 异常
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/3/12
//     */
//    public static <T> T throwBusinessException(Throwable throwable) {
//        return throwBusinessException(throwable, DistributeErrorConsts.ERROR_CODE_THIRD_SERVICE);
//    }
//
//    /**
//     * 描述: 抛出业务异常处理
//     *
//     * @param throwable 异常
//     * @param errorCode 错误码
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//    public static <T> T throwBusinessException(Throwable throwable, Integer errorCode) {
//
//        if (throwable instanceof BlockException) {
//            BlockException blockException = (BlockException) throwable;
//            ResponseDTO response = getSentinelExceptionResponse(blockException);
//            log.error(BLOCK_LOG_FORMAT, getResource(blockException), response.getStatus(), getBlockRuleDesc(response.getStatus()));
//            if (NumberUtil.isNullOrZero(errorCode)) {
//                throw new BizServWorkDistributeException(response.getStatus(), response.getMessage());
//            }
//        } else {
//            log.error(FALLBACK_LOG_FORMAT,TagUtil.getTag(throwable), throwable);
//        }
//
//        throw new BizServWorkDistributeException(ErrorCodeTableFactory.getErrorCode(errorCode));
//    }
//
//    /**
//     * 描述: 返回默认值空处理
//     *
//     * @param throwable 异常
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//    public static <T> T defaultNull(Throwable throwable) {
//        return defaultValueHandler(throwable, null);
//    }
//
//    /**
//     * 描述: 返回默认值空集合
//     *
//     * @param throwable 异常
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//    public static <T> List<T> defaultEmptyList(Throwable throwable) {
//        return defaultValueHandler(throwable, Collections.emptyList());
//    }
//
//    /**
//     * 描述: 返回默认值空Map
//     *
//     * @param throwable 异常
//     * @return {@link Map<K,V>}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//    public static <K, V> Map<K, V> defaultEmptyMap(Throwable throwable) {
//        return defaultValueHandler(throwable, Collections.emptyMap());
//    }
//
//    /**
//     * 描述: 默认值处理
//     *
//     * @param throwable 异常
//     * @param def       默认
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    public static <T> T defaultValueHandler(Throwable throwable, T def) {
//        if (throwable instanceof BlockException) {
//            BlockException blockException = (BlockException) throwable;
//            ResponseDTO response = BlockExceptionWrapperFactory.getBlockExceptionWrapper(blockException).wrap();
//            log.error(BLOCK_LOG_FORMAT, blockException.getRule().getResource(), response.getStatus(), getBlockRuleDesc(response.getStatus()));
//        } else {
//            log.error(FALLBACK_LOG_FORMAT,TagUtil.getTag(throwable), throwable);
//        }
//
//        return def;
//    }
//
//    /**
//     * 描述: 获取资源
//     *
//     * @param blockException 熔断异常
//     * @return {@link String}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//
//    public static String getResource(BlockException blockException) {
//        return blockException.getRule().getResource();
//    }
//
//    /**
//     * 描述: 获取sentinel异常响应
//     *
//     * @param blockException 熔断异常
//     * @return {@link ResponseDTO}
//     * @author: HuangChao
//     * @since: 2024/2/28
//     */
//    public static ResponseDTO getSentinelExceptionResponse(BlockException blockException) {
//        BlockExceptionWrapper blockExceptionWrapper = BlockExceptionWrapperFactory.getBlockExceptionWrapper(blockException);
//        return blockExceptionWrapper.wrap();
//    }
//
//    /**
//     * 描述: 获取熔断规则描述
//     *
//     * @param zmnCode zmn熔断code
//     * @return {@link String}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    public static String getBlockRuleDesc(int zmnCode) {
//
//        switch (zmnCode) {
//            case ZmnSentinelConstant.FLOW_QPS_STATUS:
//                return "限流：流量限流异常（资源访问量）";
//            case ZmnSentinelConstant.FLOW_TPS_STATUS:
//                return "限流：线程限流异常（资源执行的最大线程数）";
//            case ZmnSentinelConstant.DEGRADE_GRADE_RT_STATUS:
//                return "熔断：资源访问RT超过限制（x秒内x次超过RT值）";
//            case ZmnSentinelConstant.DEGRADE_GRADE_EXCEPTION_RATIO_STATUS:
//                return "熔断：资源访问异常比例超过限制";
//            case ZmnSentinelConstant.DEGRADE_GRADE_EXCEPTION_COUNT_STATUS:
//                return "熔断：资源访问异常数超过限制";
//            case ZmnSentinelConstant.PARAM_QPS_COUNT_STATUS:
//                return "热点规则：资源访问被限制";
//            case ZmnSentinelConstant.SYSTEM_EXCEPTION_STATUS:
//                return "系统规则：资源访问被限制";
//            case ZmnSentinelConstant.AUTHORITY_EXCEPTION_STATUS:
//                return "授权规则：资源访问被限制";
//            case ZmnSentinelConstant.UNAUTHORIZED:
//                return "未授权 http状态码";
//            case ZmnSentinelConstant.PAYLOAD_TOO_LARGE:
//                return "系统规则 http状态码";
//            case ZmnSentinelConstant.TOO_MANY_REQUESTS:
//                return "限流规则 http状态码";
//            case ZmnSentinelConstant.INTERNAL_SERVER_ERROR:
//                return "熔断规则 http状态码";
//            default:
//                return "未知熔断编码";
//        }
//    }
//
//    /**
//     * 描述: 获取指定包下及其子包的sentinel资源名称
//     *
//     * @param basePackage 包路径
//     * @return {@link List< String>}
//     * @author: HuangChao
//     * @since: 2024/3/12
//     */
//    @SneakyThrows
//    public static List<String> getAllSentinelResources(String basePackage) {
//        List<String> resourceNames = new ArrayList<>();
//
//        // 创建资源模式解析器
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        // 创建元数据读取工厂
//        MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
//
//        // 构建包扫描路径
//        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//                basePackage.replace('.', '/') + "/**/*.class";
//
//        // 扫描指定包及其子包下的所有类
//        org.springframework.core.io.Resource[] resources = resolver.getResources(packageSearchPath);
//        for (org.springframework.core.io.Resource resource : resources) {
//            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
//            String className = metadataReader.getClassMetadata().getClassName();
//
//            try {
//                Class<?> clazz = Class.forName(className);
//                // 检查类的所有方法
//                Method[] methods = clazz.getDeclaredMethods();
//                for (Method method : methods) {
//                    // 检查方法是否带有@SentinelResource注解
//                    SentinelResource sentinelResource = AnnotationUtils.findAnnotation(method, SentinelResource.class);
//                    if (sentinelResource != null) {
//                        // 提取资源名称
//                        String resourceName = sentinelResource.value();
//                        if (StringUtil.isNotBlank(resourceName)) {
//                            resourceNames.add(resourceName);
//                            continue;
//                        }
//                        // Parse name of target method.
//                        resourceNames.add(MethodUtil.resolveMethodName(method));
//                    }
//                }
//            } catch (ClassNotFoundException e) {
//                log.error("获取指定包下及其子包的sentinel资源名称异常", e);
//            }
//        }
//
//        return resourceNames;
//    }
//
//}
