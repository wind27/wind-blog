/**
 * Created by qianchun on 17/11/10.
 */

require.config({
    baseUrl: '/js',
    paths: {
        jquery: 'lib/jquery1.10.2.min',
        jquery_validate: 'lib/jquery.validate.min',
        juicer: 'lib/juicer',
        header: 'header.js?v=' + Math.random(100),
        menu: 'menu.js?v=' + Math.random(100)
    }
});
var module = '';
var headerModule = '';

require(['jquery', 'jquery_validate', 'juicer'], function ($, jquery_validate, tpl) {
    currentJS = $("#current-js");
    var currentModule = currentJS.attr("current-module");
    var initMethod = currentJS.attr("init-method");
    console.log('初始化 :' + currentModule + "(" + initMethod + ')');
    //header
    if (currentModule != 'login') {
        require(['header'], function (header) {
            headerModule = header;
            header.nav.getNav();
        });
    }

    if (currentJS && currentModule && initMethod) {
        // 页面加载完毕后再执行相关业务代码比较稳妥
        $(function () {
                require([currentModule], function (target) {
                        module = target;
                        if ('login' == currentModule && 'valid' == initMethod) {
                            module.login.valid();
                        } else if ('user' == currentModule && 'list' == initMethod) {
                            module.user.list();
                        } else if ('menu' == currentModule && 'list' == initMethod) {
                            module.menu.list();
                        } else if ('role' == currentModule && 'list' == initMethod) {
                            module.role.list();
                        }
                    }
                );
            }
        )
    }
});