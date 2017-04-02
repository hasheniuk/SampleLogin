package net.samplelogin.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecaptchaUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String RECAPTCHA_VERIFICATION_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static boolean isRobot(String gRecaptchaResponse, String recaptchaDataSecret) {
        if (StringUtils.isEmpty(gRecaptchaResponse)) {
            logger.debug("Captcha wasn't checked");
            return true;
        }
        logger.debug("gRecaptchaResponse: {}", gRecaptchaResponse);
        try {
            HttpsURLConnection connection = getHttpsURLConnection(new URL(RECAPTCHA_VERIFICATION_URL));
            postRequest(connection, gRecaptchaResponse, recaptchaDataSecret);
            String jsonResponse = readResponse(connection);
            return isSuccess(jsonResponse);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    private static HttpsURLConnection getHttpsURLConnection(URL url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setDoOutput(true);
        return connection;
    }

    private static void postRequest(HttpURLConnection connection, String gRecaptchaResponse,
                                    String recaptchaDataSecret) throws IOException {
        String postParams = String.format("secret=%s&response=%s", recaptchaDataSecret, gRecaptchaResponse);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
        logSendingRequest(postParams, connection.getResponseCode());
    }

    private static void logSendingRequest(String postParams, int responseCode) {
        logger.debug("Sending 'POST' request to URL {}: ", RECAPTCHA_VERIFICATION_URL);
        logger.debug("Post parameters {}: ", postParams);
        logger.debug("Response Code {}: ", responseCode);
    }

    private static String readResponse(HttpsURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
        }
        in.close();
        String response = responseBuilder.toString();
        logger.debug("Response: {}", response);
        return response;
    }

    private static boolean isSuccess(String response) throws IOException {
        JsonNode rootNode = OBJECT_MAPPER.readTree(response.toString());
        return !rootNode.findValue("success").asBoolean();
    }
}
