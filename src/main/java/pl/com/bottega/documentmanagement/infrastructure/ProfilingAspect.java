package pl.com.bottega.documentmanagement.infrastructure;

import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.annotations.CollectionId;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by Wojciech Winiarski on 09.07.2016.
 */
@Component
public class ProfilingAspect {

    public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date startTime = new Date();

        //wywolanie metody
        Object ret =  proceedingJoinPoint.proceed();
        Date endTime = new Date();
        long time = endTime.getTime() - startTime.getTime();

        String msg = "Class: " + proceedingJoinPoint.getTarget().getClass().getName()+
                "Method: " + proceedingJoinPoint.getSignature().getName() +
                 "Processing time: " + time + "/ms";
        org.apache.log4j.Logger.getLogger(ProfilingAspect.class).info(msg);
        return ret;
    }
}
