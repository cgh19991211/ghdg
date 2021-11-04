package com.gh.ghdg.common.utils.constant;

public class CommonRex {
    private CommonRex(){}

    public static final String
            usernameText = "用户名由6到32位字母、数字或下划线组成",
            username = "|[a-zA-Z0-9_]{1,32}", // 表达式统一前面加|，允许空
            passwordText = "密码由8~16位字符组成，至少1个大写字母、1个小写字母、1个数字和1个特殊字符",
            password = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[`~!@#$%\\^&*\\(\\)\\_=\\+\\\\\\|;:'\\\",<.>/?-])[A-Za-z\\d`~!@#$%\\^&*\\(\\)\\_=\\+\\\\\\|;:'\\\",<.>/?-]{8,16}",
            zipCodeText = "请输入正确的邮政编码",
            zipCode = "|[1-9]\\d{5}",
            idNoText = "请输入正确的身份证号",
            idNo = "|[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]",
            cellphoneNoText = "请输入正确的手机号",
            cellphoneNo = "|((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0,1,3,5-8])|(18[0-9]))\\d{8}",
            shortCellphoneNoText = "请输入正确的手机短号",
            shortCellphoneNo = "|\\d{6}",
            linephoneNoText = "请输入正确的固定电话",
            linephoneNo = "|(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}",
            phoneNoText = "请输入正确的电话",
            phoneNo = "|(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}",
            qqText = "请输入正确的QQ",
            qq = "|[1-9][0-9]{4,10}";
}
