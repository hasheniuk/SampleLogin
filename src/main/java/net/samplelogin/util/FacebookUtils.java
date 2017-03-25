package net.samplelogin.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.*;
import org.springframework.social.facebook.api.UserOperations;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

public class FacebookUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private FacebookUtils() {}

    public static void excludeProfileFields(String ... fields) throws ReflectiveOperationException {
        String[] newProfileFields = ArrayUtils.removeElements(UserOperations.PROFILE_FIELDS, fields);
        ReflectionUtils.setConstant(UserOperations.class, "PROFILE_FIELDS", newProfileFields);
        String profileFieldsDebugMessage = Arrays.toString(UserOperations.PROFILE_FIELDS);
        logger.debug("Profile fields after exclusion: {}", profileFieldsDebugMessage);
    }
}
