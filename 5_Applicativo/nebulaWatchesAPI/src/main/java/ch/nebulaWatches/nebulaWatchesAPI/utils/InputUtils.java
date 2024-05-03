package ch.nebulaWatches.nebulaWatchesAPI.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtils {
    public static String testInput(String data) {
        String sanitizedData = data.trim();
        sanitizedData = sanitizedData.replaceAll("\\\\", "");
        sanitizedData = StringEscapeUtils.escapeHtml4(sanitizedData);
        return sanitizedData;
    }

    public static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidImageType(MultipartFile file) {
        List<String> allowedTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/jpg");
        return allowedTypes.contains(file.getContentType());
    }

}
