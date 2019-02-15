define(['jquery_validate'], function () {
    var menu = {
        /**
         * 表单校验
         * @returns
         */
        valid:function(formId) {
            var ruleJson = {};
            var ruleMsg = {};

            ruleJson['url'] = {required: true};
            ruleJson['name'] = {required: true};

            ruleMsg['name'] = {required: '菜单名称不能为空'};
            ruleMsg['url'] = {required: '菜单URL不能为空'};
            $('#'+formId).validate({
                rules: ruleJson,
                messages: ruleMsg
            });
        },

        /**
         * 新增
         */
        add: function () {
            var tpl = document.getElementById('tpl_menu_add').innerHTML;
            $('.container').html(tpl);
            menu.valid('form_menu_add');
        },

        /**
         * 编辑
         * @param id
         */
        edit: function (id) {
            var request_url = '/menu/edit/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_menu_edit').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.container').html(html);
                        menu.valid('form_menu_edit');
                    }
                }
            });
        },

        save: function () {
            if (!$("#form_menu_add").valid()) {
                return;
            }
            var request_url = '/menu/save/';
            var name = $(':input[name=name]').val();
            var url = $(':input[name=url]').val()//唯一性校验
            var appId = $("select[name='appId']").val();
            var status = $("select[name='status']").val();
            // var parentId = $("select[name='parentId']").val()

            var data = {
                'name': name,
                'url': url,
                'appId': appId,
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
                        menu.list();
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
            if (!$("#form_menu_edit").valid()) {
                return;
            }
            var request_url = '/menu/update/';
            var id = $(':input[name=id]').val();
            var name = $(':input[name=name]').val();
            var url = $(':input[name=url]').val()//唯一性校验
            var appId = $("select[name='appId']").val();
            var status = $("select[name='status']").val();
            // var parentId = $("select[name='parentId']").val()
            var data = {
                'id': id,
                'name': name,
                'url': url,
                'appId': appId,
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
                        menu.list();
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
            var request_url = '/menu/detail/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_menu_detail').innerHTML;
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
         * 启用
         * @param id
         */
        enable: function (id) {
            var request_url = '/menu/enable/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        menu.list();
                    }
                }
            });
        },

        /**
         * 停用
         * @param id
         */
        disable: function (id) {
            var request_url = '/menu/disable/' + id;
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        menu.list();
                    }
                }
            });
        },

        /**
         * 获取菜单
         */
        list: function () {
            var request_url = '/menu/list';
            $.ajax({
                url: request_url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('tpl_menu_list').innerHTML;
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
            $(':input[name=url]').val('');
            $("select[name='status']").val(1)
        }
    };
    return {
        menu: menu
    };
});