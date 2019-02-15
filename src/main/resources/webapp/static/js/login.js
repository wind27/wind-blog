define(function () {


    var loginModel = {


        /**
         * 密码校验
         * @param password
         */
        checkPassword: function (password) {
            var passwordReg = /^[a-zA-Z0-9]\w{5,17}$/;
            console.log(passwordReg.test(password));
            return passwordReg.test(password);
        },

        /**
         * 校验
         */
        valid: function () {
            jQuery.validator.addMethod("checkPassword", function (value, element) {
                return this.optional(element) || loginModel.checkPassword(value);
            }, "密码必须字母开头,且为6-18位!");

            var ruleJson = {};
            var ruleMsg = {};

            ruleJson['username'] = {required: true};
            ruleJson['password'] = {required: true, checkPassword: true};

            ruleMsg['username'] = {required: '用户名不能为空'};
            ruleMsg['password'] = {required: '密码不能为空'};
            $('#form_login').validate({
                rules: ruleJson,
                messages: ruleMsg
            });
        }
        ,
        /**
         * 登录
         */
        login: function () {
            if (!$("#form_login").valid()) {
                return;
            }
            var username = $("input[name=username]").val();
            var password = $("input[name=password]").val();
            var data = {
                'username': username,
                'password': password
            };
            var request_url = '/login';
            $.ajax({
                url: request_url,
                type: "post",
                data: data,
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("登录成功!!!");
                        window.location.href = "/";
                    } else if (result.code == 1) {
                        alert("操作失败!!!");
                    } else if (result.code == 2) {
                        alert("系统异常!!!");
                    } else if (result.code == 3) {
                        alert("用户名或密码错误!!!");
                    } else if (result.code == 6) {
                        alert("账号已被禁用!!!");
                    }
                }
            });
        },
        /**
         * 重置
         */
        reset: function () {
            $("input[name=username]").val('');
            $("input[name=password]").val('');
        },
        /**
         * 退出登录
         */
        logout: function () {
            var request_url = '/logout';
            $.ajax({
                url: request_url,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        alert("退出登录成功!!!");
                        window.location.href = "/login";
                    }
                }
            });
        }
    }
    return {
        login: loginModel
    };
});