package com.my.common.verification;

import com.my.common.exception.CPBusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by 96230 on 2017/6/14.
 * 参数验证拦截
 */
@Aspect
@Component
public class ControllerInterceptor {

    /**
     * 定义拦截规则：拦截com.my.biz.*.app.service包下面的所有类中
     */
    @Pointcut("execution(* com.my.biz.*.app.service..*(..))")
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return
     */
    @Around("controllerMethodPointcut()")
    public  Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Object[] objects=pjp.getArgs();//获取参数列表
        for(Object o:objects){
            Set<ConstraintViolation<Object>> violations = validator.validate(o);
            if(violations.size() != 0)
                for(ConstraintViolation<Object> violation: violations) {
                    CPBusinessException.throwIt(violation.getMessage(),123);
                }
        }
        return pjp.proceed();
    }

    /**
     * 把首字母转换为大写
     *
     * @param src
     * @return
     */
    public static String change(String src) {
        if (src != null) {
            StringBuffer sb = new StringBuffer(src);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return null;
        }
    }
}
