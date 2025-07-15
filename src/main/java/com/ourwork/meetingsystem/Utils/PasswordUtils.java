package com.ourwork.meetingsystem.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtils {


        // 用户名规则：以字母开头，包含字母、数字和下划线，长度为 6 到 20 个字符
//        private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9_]{5,19}$";
        // 密码规则：包含至少一个大写字母、一个小写字母、一个数字和一个特殊字符，长度为 8 到 20 个字符
        private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";

       /* public static boolean validateUsername(String username) {
            Pattern pattern = Pattern.compile(USERNAME_PATTERN);
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }*/

        public static boolean validatePassword(String password) {
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }
        public boolean PasswordCheck(/*String username,*/String password){
           /* if (validateUsername(username)) {
                System.out.println("用户名符合规范");
            } else {
                System.out.println("用户名不符合规范");
            }*/

            if (validatePassword(password)) {
                System.out.println("密码符合规范");
                return true;
            } else {
                System.out.println("密码不符合规范");
                return false;
            }
        }

}
