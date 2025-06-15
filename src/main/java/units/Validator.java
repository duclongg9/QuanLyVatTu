package units;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        String regex = "^[\\p{L} ]+$"; // Chữ cái có dấu + khoảng trắng
        return name != null && name.matches(regex);
    }

    public static boolean isValidPhoneNumber(String phone) {
        String regex = "^0\\d{9}$"; // Bắt đầu bằng 0, sau đó 9 số
        return phone != null && phone.matches(regex);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidAddress(String address) {
        // Cho phép chữ, số, khoảng trắng và các ký tự phổ biến trong địa chỉ như dấu phẩy, chấm, gạch ngang
        String regex = "^[\\p{L}0-9\\s,.-]+$";
        return Pattern.matches(regex, address);
    }
}
