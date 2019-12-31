import Vue from 'vue';
import Router from 'vue-router';

const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {

  return originalPush.call(this, location).catch(err => err)

}

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children:[
                {
                    path: '/user',
                    component: resolve => require(['../components/page/system/user/user.vue'], resolve),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/home',
                    component: resolve => require(['../components/page/home.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/role',
                    component: resolve => require(['../components/page/system/role/role.vue'], resolve),
                    meta: { title: '角色管理' }
                },
                {
                    path: '/login_logs',
                    component: resolve => require(['../components/page/system/loginLogs/loginlogs.vue'], resolve),
                    meta: { title: '登录日志' }
                },
                {
                    path: '/permission',
                    component: resolve => require(['../components/page/system/permission/permission.vue'], resolve),
                    meta: { title: '菜单管理' }
                },
                {
                    path: '/admin',
                    component: resolve => require(['../components/page/system/admin/admin.vue'], resolve),
                    meta: { title: '系统监控' }
                },
                {
                    path: '/sys_logs',
                    component: resolve => require(['../components/page/system/sysLogs/sysLogs.vue'], resolve),
                    meta: { title: '操作日志' }
                },
                {
                    path: '/sys_exception_logs',
                    component: resolve => require(['../components/page/system/sysExceptionLogs/sysExceptionLogs.vue'], resolve),
                    meta: { title: '异常日志' }
                },
                {
                    path: '/dict_type',
                    component: resolve => require(['../components/page/system/dict_type/dict_type.vue'], resolve),
                    meta: { title: '字典类型' }
                },
                {
                    path: '/dict_data',
                    component: resolve => require(['../components/page/system/dict_data/dict_data.vue'], resolve),
                    meta: { title: '字典数据' }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '403' }
                },
                {
                    path: '/donate',
                    component: resolve => require(['../components/page/Donate.vue'], resolve),
                    meta: { title: '支持作者' }
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/login/login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
