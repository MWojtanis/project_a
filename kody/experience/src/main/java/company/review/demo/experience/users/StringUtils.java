package company.review.demo.experience.users;

public final class StringUtils {
    public static boolean isNotEmpty(String value) {
        if (value != null && !value.trim().equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

}
