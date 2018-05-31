package com.nathan.fileparser.request;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceContext {
    
    private String clientRequestId;
    private String serviceTransactionId;

    private static final ThreadLocal<ServiceContext> serviceContext = new ThreadLocal<ServiceContext>() {
        @Override
        protected ServiceContext initialValue() {
            return null;
        }
    };

    /**
     * Gets the service context associated with this thread. May be null.
     */
    public static ServiceContext getServiceContext() {
        return serviceContext.get();
    }

    /**
     * Sets the service context associated with this thread. The previous service context, if any, is cleared.
     */
    public static void setServiceContext(ServiceContext context) {
        serviceContext.set(context);
    }

    public ServiceContext() {
        serviceTransactionId = UUID.randomUUID().toString().replaceAll("-", "");
        clientRequestId = serviceTransactionId;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServiceContext [clientRequestId=").append(clientRequestId).append(", serviceTransactionId=")
                .append(serviceTransactionId).append("]");
        
        return builder.toString();
    }



}
