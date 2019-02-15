define(['jquery_validate'], function () {
    var role = {
        /**
         * 表单校验
         * @returns {*|PlatformMismatchEvent|jQuery}
         */
        valid: function (formId) {
            var ruleJson = {};
            var ruleMsg = {};
            ruleJson['name'] = {required: true};
            ruleMsg['name'] = {required: '名称不能为空'};
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
            var tpl = document.getElementById('tpl_role_add').innerHTML;
            $('.container').html(tpl);
            role.valid('form_role_add');
        },

        /**
         * 编辑
         * @param id
         */
        edit: function (id) {
            var request_url = '/role/edit/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_role_edit').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.container').html(html);
                        role.valid('form_role_edit');
                    }
                }
            });
        },

        save: function () {
            if (!$("#form_role_add").valid()) {
                return;
            }

            var request_url = '/role/save/';
            var name = $(':input[name=name]').val();
            var status = $("select[name='status']").val();

            var data = {
                'name': name,
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
                        role.list();
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
            if (!$("#form_role_edit").valid()) {
                return;
            }
            var request_url = '/role/update/';
            var id = $(':input[name=id]').val();
            var name = $(':input[name=name]').val();
            var status = $("select[name='status']").val();
            var data = {
                'id': id,
                'name': name,
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
                        role.list();
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
            var request_url = '/role/detail/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_role_detail').innerHTML;
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
            var request_url = '/role/status/change/' + id + "?status=" + status;
            $.ajax({
                url: request_url,
                type: "post",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        role.list();
                    }
                }
            });
        },

        /**
         * 获取菜单
         */
        list: function () {
            var request_url = '/role/list';
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_role_list').innerHTML;
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
            $(':input[name=name]').val('');
            $("select[name='status']").val(1);
        }
    }
    return {
        role: role
    };
});