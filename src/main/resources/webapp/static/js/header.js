define(function () {
    var nav = {
        /**
         * 获取导航
         */
        getNav: function () {
            var url = '/navigation';
            $.ajax({
                url: url,
                type: "get",
                dataType: "json",
                success: function (result) {
                    if (result.code == 0) {
                        var tpl = document.getElementById('header_tpl').innerHTML;
                        var html = juicer(tpl, result.data);
                        $('.div_header_menus ul').html(html);
                        $('.div_header_user').html('<label style="line-height: 70px;">欢迎:' + result.data.name + ', <a onclick="headerModule.nav.logout()">退出</a> </label>');
                        $('.footer').html('<p>Copyright 2017 ' + result.data.name + '</p>');

                    }
                }
            });
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
                        window.location.href = "login";
                    }
                }
            });
        }
    };
    return {
        nav: nav
    };
});