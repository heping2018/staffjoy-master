package com.example.staffjoy.faraday.trace;


import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;

/**
 * 对追踪点进行日志输出
 */
public class LoggingTraceInterceptor implements TraceInterceptor {
    private static final ILogger log = SLoggerFactory.getLogger(LoggingTraceInterceptor.class);

    @Override
    public void onRequestRecive(String traceId, IncomRequest request) {
        log.info("Incoming HTTP");
    }

    @Override
    public void onNoMappingFound(String traceId, IncomRequest request) {

    }

    @Override
    public void onForWardStart(String traceId, ForwordRequest forwordRequest) {

    }

    @Override
    public void onForwardError(String traceId, Throwable throwable) {

    }

    @Override
    public void onForwardComplete(String trace, ReceivedResponse receivedResponse) {

    }
}
