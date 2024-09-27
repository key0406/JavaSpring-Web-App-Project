package com.rcuk.portal.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class Slf4jMDCFilter extends OncePerRequestFilter {

    protected final Log logger = LogFactory.getLog(getClass());

    private final String responseHeader;
    private final String mdcTokenKey;
    private final String mdcClientIpKey;
    private final String mdcUserName;
    private final String requestHeader;
    private final String mdcRequestURL;

    public Slf4jMDCFilter() {
        responseHeader = Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER;
        mdcTokenKey = Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY;
        mdcClientIpKey = Slf4jMDCFilterConfiguration.DEFAULT_MDC_CLIENT_IP_KEY;
        mdcRequestURL = Slf4jMDCFilterConfiguration.DEFAULT_MDC_REQUEST_URL;
        mdcUserName = Slf4jMDCFilterConfiguration.DEFAULT_MDC_USER_NAME;
        requestHeader = null;
    }

    public Slf4jMDCFilter(final String responseHeader, final String mdcTokenKey, final String mdcClientIPKey, final String mdcUserName, final String mdcRequestURL, final String requestHeader) {
        this.responseHeader = responseHeader;
        this.mdcTokenKey = mdcTokenKey;
        this.mdcClientIpKey = mdcClientIPKey;
        this.mdcRequestURL = mdcRequestURL;
        this.mdcUserName = mdcUserName;
        this.requestHeader = requestHeader;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws java.io.IOException, ServletException {

        try {

            final String token = extractToken(request);
            final String clientIP = extractClientIP(request);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String userValue = "ANONYMOUS";

            if (nonNull(authentication) && nonNull(authentication.getPrincipal())) {
                Object principal = authentication.getPrincipal();
                if(principal instanceof UserDetails) {
                    userValue = ((UserDetails) principal).getUsername().substring(0,5);
                }
            }

            MDC.put(mdcClientIpKey, clientIP);
            MDC.put(mdcTokenKey, token);
            MDC.put(mdcUserName, userValue);
            MDC.put(mdcRequestURL, request.getRequestURL().toString());

            if (StringUtils.hasText(responseHeader)) {
                response.addHeader(responseHeader, token);
            }

            chain.doFilter(request, response);

        } finally {
            MDC.remove(mdcTokenKey);
            MDC.remove(mdcClientIpKey);
            MDC.remove(mdcRequestURL);
            MDC.remove(mdcUserName);
        }
    }

    private String extractToken(final HttpServletRequest request) {
        final String token;
        if (StringUtils.hasText(requestHeader) && StringUtils.hasText(request.getHeader(requestHeader))) {
            token = request.getHeader(requestHeader);
        } else {
            token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        }
        return token;
    }

    private String extractClientIP(final HttpServletRequest request) {
        final String clientIP;
        if (request.getHeader("X-Forwarded-For") != null) {
            clientIP = request.getHeader("X-Forwarded-For").split(",")[0];
        } else {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}