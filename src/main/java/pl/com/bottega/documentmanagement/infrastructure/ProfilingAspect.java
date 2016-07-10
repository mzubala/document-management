package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by bartosz.paszkowski on 09.07.2016.
 */

@Component
public class ProfilingAspect {

    public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Date startTime = new Date();
        //call targeted method
        Object retValue = proceedingJoinPoint.proceed();

        Date endTime = new Date();
        long time = endTime.getTime() - startTime.getTime();
        String msg = "Class: " + proceedingJoinPoint.getTarget().getClass().getName() +
                "Method: " + proceedingJoinPoint.getSignature().getName() + "Processing time: "+ time+ "ms";
        Logger.getLogger(ProfilingAspect.class).info(msg);

        //return targeted method return value
        return retValue;
    }


}
