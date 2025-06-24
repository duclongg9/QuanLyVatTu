/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package units;

import java.util.regex.Pattern;

/**
 *
 * @author D E L L
 */
public class Validator {
    //kiểm tra chuỗi điền vào null hay không
     public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
     
     //kiểm tra tên đầu vào
    public static boolean isValidNameSup(String name) {
    String regex = "^[\\p{L} ]{5,50}$";
    return name != null && name.matches(regex);
}
    
    
    //Kiểm tra số điện thoại hơp lệ
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^0\\d{9}$");
    }
    
    //kiểm tra email format
     public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }
     
     //kiểm tra địa chỉ
    public static boolean isValidAddress(String address) {
        // Cho phép chữ, số, khoảng trắng và các ký tự phổ biến trong địa chỉ như dấu phẩy, chấm, gạch ngang
        String regex = "^[\\p{L}0-9\\s,.-]+$";
        return Pattern.matches(regex, address);
    }
}
