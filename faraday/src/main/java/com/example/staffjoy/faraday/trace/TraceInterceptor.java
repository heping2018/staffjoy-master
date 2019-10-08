package com.example.staffjoy.faraday.trace;

public interface TraceInterceptor {
    /**
     * 接收到recive的request
     * @param traceId
     * @param request
     */
    void onRequestRecive(String traceId,IncomRequest request);

    /**
     * 没有找到到对应的路由
     * @param traceId
     * @param request
     */
    void onNoMappingFound(String traceId,IncomRequest request);

    /**
     * 找到路由之后进行的发送到哪个service
     * @param traceId
     * @param forwordRequest
     */
    void onForWardStart(String traceId,ForwordRequest forwordRequest);

    /**
     * 路由错误之后
     * @param traceId
     * @param throwable
     */
    void onForwardError(String traceId,Throwable throwable);

    /**
     * 完成请求 返回响应完成
     * @param trace
     * @param receivedResponse
     */
    void onForwardComplete(String trace,ReceivedResponse receivedResponse);
}
