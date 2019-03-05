var form = {
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
        };
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
     * 列表
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
        };
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

    reset: function () {
        $("#name").val('');
        $("#css").val('');
        $("#isShow").val('');
        $("#defaultVal").val('');
        $("#type").html('<option value="0">请选择</option>');

    },

    /**
     * 新增
     */
    saveField: function () {
        var url = '/field/save';
        

        var data = {};


        $.ajax({
            url: url,
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
    }
}
