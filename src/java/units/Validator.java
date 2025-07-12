/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package units;

/**
 *
 * @author D E L L
 */
public class Validator {
    //Kiểm tra số điện thoại hơp lệ
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^0\\d{9}$");
    }
}
