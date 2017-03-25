package net.samplelogin.util;

import org.slf4j.*;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;

public class ReflectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void setConstant(Class c, String fieldName, Object val) throws ReflectiveOperationException {
        try {
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, val);
            logger.debug("Updated field: {} of class: {}", c.getName(), fieldName);
        } catch (ReflectiveOperationException e) {
            logger.error("Can't update field: {} of class: {}", c.getName(), fieldName);
            throw e;
        }
    }
}
