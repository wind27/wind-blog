define(['jquery_validate'], function () {
    var user = {
        /**
         * 表单校验
         * @returns {*|PlatformMismatchEvent|jQuery}
         */
        valid: function (formId) {
            // 手机号码验证
            jQuery.validator.addMethod("isMobile", function (value, element) {
                var length = value.length;
                var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
                return this.optional(element) || (length == 11 && mobile.test(value));
            }, "请输入正确的手机号码");

            var ruleJson = {};
            var ruleMsg = {};
            ruleJson['username'] = {required: true};
            ruleJson['realname'] = {required: true};
            ruleJson['mobile'] = {required: true, isMobile: true};
            ruleJson['email'] = {required: true, email: true};

            ruleMsg['username'] = {required: '用户名不能为空'};
            ruleMsg['realname'] = {required: '真实姓名不能为空'};
            ruleMsg['mobile'] = {required: '手机号码不能为空', isMobile: '请输入正确的手机号码'};
            ruleMsg['email'] = {required: '邮箱地址不能为空', email: '请输入有效的邮箱地址'};
            $('#' + formId).validate({
                rules: ruleJson,
                messages: ruleMsg
            });
        },

        /**
         * 新增
         * @param id
         */
        add: function () {
            var tpl = document.getElementById('tpl_user_add').innerHTML;
            $('.container').html(tpl);
            user.valid('form_user_add');
        },

        /**
         * 编辑
         * @param id
         */
        edit: function (id) {
            var request_url = '/user/edit/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_user_edit').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.container').html(html);
                        user.valid('form_user_edit');
                    }
                }
            });
        },

        save: function () {
            if (!$("#form_user_add").valid()) {
                return;
            }

            var request_url = '/user/save/';
            var username = $(':input[name=username]').val();
            var realname = $(':input[name=realname]').val();
            var mobile = $(':input[name=mobile]').val();
            var email = $(':input[name=email]').val();
            var status = $("select[name='status']").val();

            var data = {
                'username': username,
                'realname': realname,
                'mobile': mobile,
                'email': email,
                'status': status
            }
            $.ajax({
                url: request_url,
                type: "post",
                dataType: "json",
                data: data,
                success: function (result) {
                    if (result.code == 0) {
                        alert("操作成功!!!");
                        user.list();
                    } else {
                        alert("操作失败");
                    }
                }
            });
        },
        /**
         * 更新
         * @param id
         */
        update: function (id) {
            if (!$("#form_user_edit").valid()) {
                return;
            }
            var request_url = '/ser/update/';
            var id = $(':input[name=id]').val();
            var username = $(':input[name=username]').val();
            var realname = $(':input[name=realname]').val();
            var mobile = $(':input[name=mobile]').val();
            var email = $(':input[name=email]').val();
            var status = $("select[name='status']").val();
            var data = {
                'id': id,
                'username': username,
                'realname': realname,
                'mobile': mobile,
                'email': email,
                'status': status
            }
            $.ajax({
                url: request_url,
                type: "post",
                dataType: "json",
                data: data,
                success: function (result) {
                    if (result.code == 0) {
                        alert("操作成功!!!");
                        user.list();
                    } else {
                        alert("操作失败");
                    }
                }
            });
        },

        /**
         * 详情
         * @param id
         */
        detail: function (id) {
            var request_url = '/user/detail/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_user_detail').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.container').html(html);
                    }
                }
            });
        },

        /**
         * 删除
         * @param id
         */
        remove: function (id) {
            alert(id);
        },

        /**
         * 启用/停用
         * @param id
         * @param status
         */
        changeStatus: function (id, status) {
            var request_url = '/user/status/change/' + id + "?status=" + status;
            $.ajax({
                url: request_url,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        user.list();
                    }
                }
            });
        },

        /**
         * 获取菜单
         */
        list: function () {
            var request_url = '/user/list';
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_user_list').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.container').html(html);
                    }
                }
            });
        },

        /**
         * 新增表单重置
         */
        reset: function () {
            $(':input[name=username]').val('');
            $(':input[name=realname]').val('');
            $(':input[name=mobile]').val('');
            $(':input[name=email]').val('');
            $("select[name='status']").val(1);
        }
    }
    return {
        user: user
    };
});