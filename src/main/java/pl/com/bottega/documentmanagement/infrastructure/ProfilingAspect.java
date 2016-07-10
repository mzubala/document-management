package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by paulina.pislewicz on 2016-07-09.
 */
@Component
public class ProfilingAspect {
    public Object profile(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Date startTime = new Date();

        //call target method
        Object returnValue = proceedingJoinPoint.proceed();

        Date endTime = new Date();

        long time = endTime.getTime() - startTime.getTime();
        String msg = "Class: " +
                proceedingJoinPoint.getTarget().getClass().getName() +
                ", Method: " + proceedingJoinPoint.getSignature().getName()+
                ", Proceesing time: " + time + "ms";
        Logger.getLogger(ProfilingAspect.class).info(msg);
        return returnValue;
    }
}
