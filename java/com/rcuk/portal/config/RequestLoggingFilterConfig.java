package com.rcuk.portal.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestLoggingFilterConfig extends AbstractRequestLoggingFilter {

    public RequestLoggingFilterConfig() {
        setIncludeClientInfo(false);
        setIncludeHeaders(false);
        setIncludePayload(false);
        setIncludeQueryString(false);
        setBeforeMessagePrefix("Request started => ");
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        if(request.getRequestURI().toLowerCase().contains("assets")){
            return false;
        }
        return true;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}