package com.example.staffjoy.faraday.controller;

import com.example.commonlib.commonlib.config.StaffjoyProps;
import com.example.commonlib.commonlib.envconfig.EnvConfig;
import com.example.staffjoy.faraday.view.ErrorPage;
import com.example.staffjoy.faraday.view.ErrorPageFactory;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLogger;
import com.github.structlog4j.SLoggerFactory;
import io.sentry.SentryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.util.UUID;

@Controller
public class GlobalErrorController implements ErrorController {

    static final ILogger logger = SLoggerFactory.getLogger(GlobalErrorController.class);

    @Autowired
    private ErrorPageFactory errorPageFactory;
    @Autowired
    private SentryClient sentryClient;

    @Autowired
    private StaffjoyProps staffjoyProps;

    @Autowired
    private EnvConfig envConfig;


    public String handError(HttpServletRequest httpRequest, Model model){
        Object statusCode = httpRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object excption = httpRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        ErrorPage errorPage = null;
        if(excption instanceof HttpClientErrorException.Forbidden){
            errorPage = errorPageFactory.buildForbiddenErrorPage();
        }else if(excption instanceof ResourceAccessException){
            ResourceAccessException resourceAccessException = (ResourceAccessException) excption;
            if(resourceAccessException.contains(SocketTimeoutException.class)){
                errorPage = errorPageFactory.buildTimeOutErrorPage();
            }
        }
        if(errorPage == null){
            errorPage = errorPageFactory.buildInternalServerErrorPage();
        }
        if(errorPage != null){
            if(envConfig.isDebug()){
                logger.error("Global error handing",excption);
            }else {
                /**
                 * 转发到sentry平台
                 */
                sentryClient.sendException((Throwable) excption);
                UUID uuid = sentryClient.getContext().getLastEventId();
                errorPage.setSentryErrorId(uuid.toString());
                errorPage.setSentryPubicSn(staffjoyProps.getSentryDsn());
                logger.warn("Reported error to sentry", "id", uuid.toString(), "error", excption);
            }
        }
        model.addAttribute("page",errorPage);
        return "error";
    }
    /**
     * 相当于web.xml <error></error>
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
