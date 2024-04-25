//package com.hc.util;
//
//
//import com.alibaba.fastjson2.JSON;
//import com.hc.enums.LogPrintEnum;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
///**
// * 描述: DUBBO远程调用接口工具类
// * ps: 主要负责对dubbo响应结果的处理，及对调用日志的统一封装处理，支持可选调用日志输出，支持将可选异常抛出
// *
// * @author huangchao
// * @since 2024/3/13
// **/
//@Slf4j
//public class DubboRespUtil {
//
//    public static final int ERROR_CODE_THIRD_SERVICE = 300001;
//
//    public static final String ERROR_CODE_THIRD_SERVICE_MSG = "Dubbo远程调用失败！";
//    public static final String ERROR_CODE_NULL_RESP_MSG = "返回结果为空！";
//
//    public static final ErrorCode DEFAULT_ERROR_CODE = new ErrorCode(ERROR_CODE_THIRD_SERVICE, ERROR_CODE_THIRD_SERVICE_MSG);
//    public static final ErrorCode NULL_RESP_ERROR_CODE = new ErrorCode(ERROR_CODE_THIRD_SERVICE, ERROR_CODE_NULL_RESP_MSG);
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-不支持日志输出
//     *
//     * @param supplier dubbo调用方法
//     * @author huangchao
//     * @since 2023/3/3 20:11
//     **/
//    public static <T> T resultToThrow(Supplier<ResponseDTO<T>> supplier) {
//        return resultToThrow(supplier, DEFAULT_ERROR_CODE, null);
//    }
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-不支持日志输出
//     *
//     * @param supplier            执行函数
//     * @param throwExceptionClass 抛出的异常类型
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    public static <T, E extends Throwable> T resultToThrow(Supplier<ResponseDTO<T>> supplier, Class<E> throwExceptionClass) {
//        return resultToThrow(supplier, DEFAULT_ERROR_CODE, throwExceptionClass);
//    }
//
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-出现异常使用传入的错误码-不支持日志输出
//     *
//     * @param supplier  dubbo调用方法
//     * @param errorCode 系统内部配置的错误码
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/1/25
//     */
//    public static <T> T resultToThrow(Supplier<ResponseDTO<T>> supplier, Integer errorCode) {
//        return resultToThrow(supplier, ErrorCodeTableFactory.getErrorCode(errorCode), null);
//    }
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-出现异常使用传入的错误码-不支持日志输出
//     *
//     * @param supplier            dubbo调用方法
//     * @param errorCode           系统内部配置的错误码
//     * @param throwExceptionClass 抛出的异常类型
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    public static <T, E extends Throwable> T resultToThrow(Supplier<ResponseDTO<T>> supplier, Integer errorCode, Class<E> throwExceptionClass) {
//        return resultToThrow(supplier, ErrorCodeTableFactory.getErrorCode(errorCode), throwExceptionClass);
//    }
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-出现异常使用传入的错误码-不支持日志输出
//     *
//     * @param supplier  dubbo调用方法
//     * @param errorCode 错误码
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/1/25
//     */
//    @SneakyThrows
//    public static <T, E extends Throwable> T resultToThrow(Supplier<ResponseDTO<T>> supplier, ErrorCode errorCode, Class<E> throwExceptionClass) {
//
//        ResponseDTO<T> responseDTO;
//        try {
//            responseDTO = supplier.get();
//        } catch (Exception e) {
//            log.error("Dubbo远程调用失败 exception: ", e);
//            if (Objects.nonNull(throwExceptionClass) && throwExceptionClass.isInstance(e)) {
//                throw (E) e;
//            }
//            throw new BizServWorkDistributeException(new ErrorCode(ERROR_CODE_THIRD_SERVICE, Optional.ofNullable(e.getMessage()).orElse(ERROR_CODE_THIRD_SERVICE_MSG)));
//        }
//
//        return respToThrow(responseDTO, errorCode);
//    }
//
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-出现异常使用传入的错误码-支持选择日志输出
//     *
//     * @param function dubbo调用方法
//     * @param t        入参
//     * @param tag      日志标识
//     * @param logPrint 日志打印选项
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2024/1/25
//     */
//    public static <T, R> R resultToThrow(Function<T, ResponseDTO<R>> function, T t, String tag, LogPrintEnum logPrint) {
//        return resultToThrow(function, t, DEFAULT_ERROR_CODE, null, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo接口通用响应处理-失败则异常-出现异常使用传入的错误码-支持选择日志输出
//     *
//     * @param function  dubbo调用方法
//     * @param t         入参
//     * @param errorCode 错误码
//     * @param tag       日志标识
//     * @param logPrint  日志打印选项
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/1/25
//     */
//    public static <T, R> R resultToThrow(Function<T, ResponseDTO<R>> function, T t, ErrorCode errorCode, String tag, LogPrintEnum logPrint) {
//        return resultToThrow(function, t, errorCode, null, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo接口响应处理-异常-支持向上抛出特定的异常类型
//     *
//     * @param function            执行函数
//     * @param t                   入参
//     * @param errorCode           错误码
//     * @param throwExceptionClass 抛出异常类型
//     * @param tag                 日志标识
//     * @param logPrint            日志打印选项
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    @SneakyThrows
//    public static <T, R, E extends Throwable> R resultToThrow(Function<T, ResponseDTO<R>> function,
//                                                              T t,
//                                                              ErrorCode errorCode,
//                                                              Class<E> throwExceptionClass,
//                                                              String tag,
//                                                              LogPrintEnum logPrint) {
//        if (Objects.nonNull(logPrint) && logPrint.isPrintIn()) {
//            log.info("{} request: {}", tag, JSON.toJSONString(t));
//        }
//
//        ResponseDTO<R> responseDTO = null;
//        try {
//            responseDTO = function.apply(t);
//
//            if (Objects.nonNull(logPrint) && logPrint.isPrintOut()) {
//                log.info("{} response: {}", tag, JSON.toJSONString(responseDTO));
//            }
//        } catch (Exception e) {
//            log.error("{} exception: ", tag, e);
//            if (Objects.nonNull(throwExceptionClass) && throwExceptionClass.isInstance(e)) {
//                throw (E) e;
//            }
//
//            throw new BizServWorkDistributeException(new ErrorCode(ERROR_CODE_THIRD_SERVICE, Optional.ofNullable(e.getMessage()).orElse(ERROR_CODE_THIRD_SERVICE_MSG)));
//        }
//        return respToThrow(responseDTO, errorCode);
//    }
//
//    /**
//     * 描述: 通用响应结果异常处理
//     *
//     * @param responseDTO 响应结果
//     * @param errorCode   错误码
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2024/1/25
//     */
//    public static <R> R respToThrow(ResponseDTO<R> responseDTO, ErrorCode errorCode) {
//        if (Objects.isNull(responseDTO)) {
//            throw new BizServWorkDistributeException(NULL_RESP_ERROR_CODE);
//        }
//
//        if (responseDTO.isSuccess()) {
//            return responseDTO.getData();
//        }
//
//        if (Objects.nonNull(errorCode)) {
//            throw new BizServWorkDistributeException(errorCode);
//        }
//
//        throw new BizServWorkDistributeException(new ErrorCode(ERROR_CODE_THIRD_SERVICE, Optional.ofNullable(responseDTO.getMessage()).orElse(ERROR_CODE_THIRD_SERVICE_MSG)));
//    }
//
//    /**
//     * 描述: dubbo查询接口通用响应处理-默认值
//     *
//     * @param supplier dubbo调用方法
//     * @author huangchao
//     * @since 2023/3/3 20:11
//     **/
//    public static <T> T toBeanOrNull(Supplier<ResponseDTO<T>> supplier) {
//        return toBeanOrDefault(supplier, null, null);
//    }
//
//    /**
//     * 描述: dubbo列表查询接口通用响应处理-默认值
//     *
//     * @param supplier dubbo调用方法
//     * @author huangchao
//     * @since 2023/3/3 20:11
//     **/
//    public static <T> List<T> toEmptyList(Supplier<ResponseDTO<List<T>>> supplier) {
//        return toBeanOrDefault(supplier, Collections.emptyList(), null);
//    }
//
//    /**
//     * 描述: dubbo查询接口通用响应处理-指定默认值
//     *
//     * @param supplier dubbo调用方法
//     * @param t 默认值
//     * @return {@link T}
//     * @author: HuangChao
//     * @since: 2024/3/12
//     */
//    public static <T> T toBeanOrDefault(Supplier<ResponseDTO<T>> supplier, T t) {
//        return toBeanOrDefault(supplier, t, null);
//    }
//
//    /**
//     * 描述:  dubbo查询接口通用响应处理-指定默认值
//     *
//     * @param supplier            dubbo调用方法
//     * @param t                   默认值
//     * @param throwExceptionClass 抛出异常类型
//     * @author huangchao
//     * @since 2023/3/3 20:11
//     **/
//    @SneakyThrows
//    public static <T, E extends Throwable> T toBeanOrDefault(Supplier<ResponseDTO<T>> supplier, T t, Class<E> throwExceptionClass) {
//        ResponseDTO<T> responseDTO;
//        try {
//            responseDTO = supplier.get();
//            if (Objects.isNull(responseDTO)) {
//                return t;
//            }
//        } catch (Exception e) {
//            log.error("调用调用三方服务异常：异常信息：{}", e.getMessage());
//            if (Objects.nonNull(throwExceptionClass) && throwExceptionClass.isInstance(e)) {
//                throw (E) e;
//            }
//            return t;
//        }
//        return responseDTO.getData();
//    }
//
//    /**
//     * 描述: dubbo查询接口通用响应处理-默认null-指定输出日志选项
//     *
//     * @param function 执行函数
//     * @param t        入参
//     * @param tag      日志标识
//     * @param logPrint 日志打印选项
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2023/9/7 14:52
//     */
//    public static <T, R> R toResultOrNull(Function<T, ResponseDTO<R>> function, T t, String tag, LogPrintEnum logPrint) {
//        return toResultOrDefault(function, t, null, null, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo查询接口通用响应处理-默认null-指定输出日志选项
//     *
//     * @param function            执行函数
//     * @param t                   入参
//     * @param throwExceptionClass 抛出异常类型
//     * @param tag                 日志标识
//     * @param logPrint            日志打印选项
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2024/3/11
//     */
//    public static <T, R, E extends Throwable> R toResultOrNull(Function<T, ResponseDTO<R>> function, T t, Class<E> throwExceptionClass, String tag, LogPrintEnum logPrint) {
//        return toResultOrDefault(function, t, null, throwExceptionClass, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo列表查询接口通用响应处理-默认空集合-无日志输出
//     *
//     * @param function 执行函数
//     * @param t        入参
//     * @param tag      日志标识
//     * @param logPrint 日志打印选项
//     * @return {@link List<R>}
//     * @author: HuangChao
//     * @since: 2023/9/18 18:15
//     */
//    public static <T, R> List<R> toResultOrEmptyList(Function<T, ResponseDTO<List<R>>> function, T t, String tag, LogPrintEnum logPrint) {
//        return toResultOrDefault(function, t, Collections.emptyList(), null, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo列表查询接口通用响应处理-默认空集合-无日志输出
//     *
//     * @param function            执行函数
//     * @param t                   入参
//     * @param throwExceptionClass 抛出异常类型
//     * @param tag                 日志标识
//     * @param logPrint            日志打印选项
//     * @return {@link List<R>}
//     * @author: HuangChao
//     * @since: 2023/9/18 18:15
//     */
//    public static <T, R, E extends Throwable> List<R> toResultOrEmptyList(Function<T, ResponseDTO<List<R>>> function, T t, Class<E> throwExceptionClass, String tag, LogPrintEnum logPrint) {
//        return toResultOrDefault(function, t, Collections.emptyList(), throwExceptionClass, tag, logPrint);
//    }
//
//    /**
//     * 描述: dubbo查询接口通用响应处理-指定默认值-指定输出日志选项
//     *
//     * @param function            执行函数
//     * @param t                   入参
//     * @param def                 默认值
//     * @param throwExceptionClass 抛出异常类型
//     * @param tag                 日志标识
//     * @param logPrint            日志打印选项
//     * @return {@link R}
//     * @author: HuangChao
//     * @since: 2023/9/7 14:52
//     */
//    @SneakyThrows
//    public static <T, R, E extends Throwable> R toResultOrDefault(Function<T, ResponseDTO<R>> function,
//                                                                  T t,
//                                                                  R def,
//                                                                  Class<E> throwExceptionClass,
//                                                                  String tag,
//                                                                  LogPrintEnum logPrint) {
//        if (Objects.nonNull(logPrint) && logPrint.isPrintIn()) {
//            log.info("{} request: {}", tag, JSON.toJSONString(t));
//        }
//
//        ResponseDTO<R> responseDTO = null;
//
//        try {
//            responseDTO = function.apply(t);
//
//            if (Objects.nonNull(logPrint) && logPrint.isPrintOut()) {
//                log.info("{} response: {}", tag, JSON.toJSONString(responseDTO));
//            }
//
//        } catch (Exception e) {
//            log.error("{} exception: ", tag, e);
//            if (Objects.nonNull(throwExceptionClass) && throwExceptionClass.isInstance(e)) {
//                throw (E) e;
//            }
//
//            return def;
//        }
//
//        if (Objects.isNull(responseDTO) || !responseDTO.isSuccess() || Objects.isNull(responseDTO.getData())) {
//            return def;
//        }
//
//        return responseDTO.getData();
//    }
//}
