package com.nathan.fileparser.controller.hook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nathan.fileparser.request.ServiceContext;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    public RequestInterceptor() {
        super();
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startMs = System.currentTimeMillis();
        request.setAttribute("startMs", startMs);

        HandlerMethod hm = (HandlerMethod) handler;
        String methodName = hm.getMethod().getName();

        Logger controllerLogger = LoggerFactory.getLogger(hm.getMethod().getDeclaringClass());
        initServiceContext(request);

        if (controllerLogger != null && controllerLogger.isDebugEnabled()) {
            controllerLogger.debug("Continuing with preHandler " + methodName + " (init took: " + String.format("%.3f", (System.currentTimeMillis() - startMs) / 1000.0) + "s)");
        }
        return super.preHandle(request, response, handler);
    }

    private ServiceContext initServiceContext(HttpServletRequest request) {
        ServiceContext sc = ServiceContext.getServiceContext();
        if (sc != null) {
            if (logger.isInfoEnabled()) {
                logger.info("Initializing service context more than once for same http request. {} ", sc);
            }
            return sc;
        }

        return createServiceContext(request);
    }

    /**
     * Initializes a ServiceContext object based on the specified HTTP request and associates it with this thread.
     */
    private ServiceContext createServiceContext(HttpServletRequest req) {

        ServiceContext serviceContext = new ServiceContext();
        ServiceContext.setServiceContext(serviceContext);
        return serviceContext;
    }

}
