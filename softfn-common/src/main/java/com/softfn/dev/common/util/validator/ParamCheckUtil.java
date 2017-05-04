package com.softfn.dev.common.util.validator;

import com.softfn.dev.common.exception.InvalidParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Set;

/**
 * <p/>
 * ParamCheckUtil
 * <p/>
 *
 * @author softfn
 */
public class ParamCheckUtil {
    private static Validator validator = null;

    public static <T> String checkParam(T obj) {
        Set<ConstraintViolation<T>> valideSert = getConstraintViolations(obj);
        if (valideSert != null && valideSert.size() > 0) {
            StringBuilder strBuff = new StringBuilder();
            for (ConstraintViolation<T> cv : valideSert) {
                strBuff.append(cv.getPropertyPath()).append(":").append(cv.getMessage()).append(";");
            }
            return strBuff.toString();
        } else {
            return null;
        }
    }

    public static <T> void validate(T obj) {
        Set<ConstraintViolation<T>> valideSert = getConstraintViolations(obj);
        if (valideSert != null && valideSert.size() > 0) {
            ConstraintViolation<T> cv = valideSert.iterator().next();
            throw new InvalidParamException(cv.getMessage());
        }
    }

    public static <T> void validate(Collection<T> collection) {
        for (T obj : collection) {
            Set<ConstraintViolation<T>> valideSert = getConstraintViolations(obj);
            if (valideSert != null && valideSert.size() > 0) {
                ConstraintViolation<T> cv = valideSert.iterator().next();
                throw new InvalidParamException(cv.getMessage());
            }
        }
    }

    private static <T> Set<ConstraintViolation<T>> getConstraintViolations(T obj) {
        if (validator == null) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
        return validator.validate(obj);
    }
}
