package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Date;

/**
 * Created by Dell on 2016-07-09.
 */
@Component
public class ProfilingAspect {

    public Object profile(ProceedingJoinPoint proccedingJoinPoint) throws Throwable {
        Date startTime = new Date();
        Object ret = proccedingJoinPoint.proceed();
        Date endTime = new Date();
        long time = endTime.getTime() - startTime.getTime();
        String msg = "Class: " + proccedingJoinPoint.getTarget().getClass().getName() + ", method: " +
                proccedingJoinPoint.getSignature().getName() + ", processing time: " + time + " ms";
        Logger.getLogger(ProfilingAspect.class).info(msg);
        proccedingJoinPoint.getSignature();
        return ret;
    }
}
