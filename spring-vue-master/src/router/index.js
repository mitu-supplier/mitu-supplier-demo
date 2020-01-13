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
                    path: '/user_details',
                    component: resolve => require(['../components/page/system/user/user_details.vue'], resolve),
                    meta: { title: '用户详情' }
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
                    path: '/position',
                    component: resolve => require(['../components/page/position/position.vue'], resolve),
                    meta: { title: '岗位管理' }
                },
                {
                    path: '/org',
                    component: resolve => require(['../components/page/organization/organization.vue'], resolve),
                    meta: { title: '科室管理' }
                },
                {
                    path: '/org_details',
                    component: resolve => require(['../components/page/organization/org_details.vue'], resolve),
                    meta: { title: '科室详情' }
                },
                {
                    path: '/org_user',
                    component: resolve => require(['../components/page/organization/org_user.vue'], resolve),
                    meta: { title: '科室人员' }
                },
                {
                    path: '/project',
                    component: resolve => require(['../components/page/project/project.vue'], resolve),
                    meta: { title: '项目管理' }
                },
                {
                    path: '/project_details',
                    component: resolve => require(['../components/page/project/project_details.vue'], resolve),
                    meta: { title: '项目详情' }
                },
                {
                    path: '/contract',
                    component: resolve => require(['../components/page/contract/contract.vue'], resolve),
                    meta: { title: '项目资料管理' }
                },
                {
                    path: '/contract_details',
                    component: resolve => require(['../components/page/contract/contract_details.vue'], resolve),
                    meta: { title: '项目资料详情' }
                },
                {
                    path: '/plan',
                    component: resolve => require(['../components/page/plan/plan.vue'], resolve),
                    meta: { title: '计划管理' }
                },
                {
                    path: '/plan_details',
                    component: resolve => require(['../components/page/plan/plan_details.vue'], resolve),
                    meta: { title: '计划详情' }
                },
               
                {
                    path: '/expenditure',
                    component: resolve => require(['../components/page/expenditure/expenditure.vue'], resolve),
                    meta: { title: '执行情况管理' }
                },
                {
                    path: '/expenditure_details',
                    component: resolve => require(['../components/page/expenditure/expenditure_details.vue'], resolve),
                    meta: { title: '执行情况详情' }
                },

                {
                    path: '/project_supervise',
                    component: resolve => require(['../components/page/supervise/project.vue'], resolve),
                    meta: { title: '项目监管' }
                },
                {
                    path: '/contract_supervise',
                    component: resolve => require(['../components/page/supervise/contract.vue'], resolve),
                    meta: { title: '合同监管' }
                },
                {
                    path: '/plan_supervise',
                    component: resolve => require(['../components/page/supervise/plan.vue'], resolve),
                    meta: { title: '计划监管' }
                },
                {
                    path: '/expenditure_supervise',
                    component: resolve => require(['../components/page/supervise/expenditure.vue'], resolve),
                    meta: { title: '执行情况监管' }
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
