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
                    path: '/productList',
                    component: resolve => require(['../components/page/product/productList.vue'], resolve),
                    meta: { title: '商品列表' }
                },
                {
                    path: '/commodityAdd',
                    component: resolve => require(['../components/page/commodity/commodityAdd.vue'], resolve),
                    meta: { title: '新增商品' }
                },
                {
                    path: '/auditDetails',
                    name: 'auditDetails',
                    component: resolve => require(['../components/page/product/auditDetails.vue'], resolve),
                    meta: { title: '商品详情' }
                },
                {
                    path: '/commodityEdit',
                    name: 'commodityEdit',
                    component: resolve => require(['../components/page/commodity/commodityEdit.vue'], resolve),
                    meta: { title: '修改商品' }
                },
                {
                    path: '/commodityLook',
                    name: 'commodityLook',
                    component: resolve => require(['../components/page/commodity/commodityLook.vue'], resolve),
                    meta: { title: '商品详情' }
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
                    path: '/supplierList',
                    component: resolve => require(['../components/page/supplier/supplierList.vue'], resolve),
                    meta: { title: '商户管理' }
                },
                {
                    path: '/addSupplier',
                    component: resolve => require(['../components/page/supplier/addSupplier.vue'], resolve),
                    meta: { title: '新增商户' }
                },
                {
                    path: '/supplierAuditList',
                    component: resolve => require(['../components/page/supplier/supplierAuditList.vue'], resolve),
                    meta: { title: '商户审核' }
                },
                {
                    path: '/supplierSee',
                    name: 'supplierSee',
                    component: resolve => require(['../components/page/supplier/supplierSee.vue'], resolve),
                    meta: { title: '商户信息' }
                },
                
                {
                    path: '/productAuditList',
                    component: resolve => require(['../components/page/product/productAuditList.vue'], resolve),
                    meta: { title: '商品审核' }
                },
                {
                    path: '/catalogs',
                    component: resolve => require(['../components/page/catalog/catalogs.vue'], resolve),
                    meta: { title: '品目管理' }
                },
                {
                    path: '/camiloList',
                    component: resolve => require(['../components/page/inventory/camiloList.vue'], resolve),
                    meta: { title: '卡密库存' }
                },
                {
                    path: '/balanceList',
                    component: resolve => require(['../components/page/inventory/balanceList.vue'], resolve),
                    meta: { title: '直充库存' }
                },
                {
                    path: '/camiloRecordList',
                    component: resolve => require(['../components/page/inventory/camiloRecordList.vue'], resolve),
                    meta: { title: '卡密入库记录' }
                },
                {
                    path: '/balanceRecordList',
                    component: resolve => require(['../components/page/inventory/balanceRecordList.vue'], resolve),
                    meta: { title: '直充入库记录' }
                },
                {
                    path: '/companyList',
                    component: resolve => require(['../components/page/company/companyList.vue'], resolve),
                    meta: { title: '公司列表' }
                },
                {
                    path: '/ordersList',
                    component: resolve => require(['../components/page/orders/ordersList.vue'], resolve),
                    meta: { title: '订单列表' }
                },
                {
                    path: '/ordersDetails',
                    name: 'ordersDetails',
                    component: resolve => require(['../components/page/orders/ordersDetails.vue'], resolve),
                    meta: { title: '订单详情' }
                },
                {
                    path: '/supplierUpdateList',
                    component: resolve => require(['../components/page/supplier/supplierUpdateList.vue'], resolve),
                    meta: { title: '商户修改审核' }
                },
                {
                    path: '/supplierUpdateView',
                    name: 'supplierUpdateView',
                    component: resolve => require(['../components/page/supplier/supplierUpdateView.vue'], resolve),
                    meta: { title: '商户修改详情' }
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
                }
               
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/login/login.vue'], resolve)
        },
        {
            path: '/registed',
            name: 'registed',
            component: resolve => require(['../components/page/supplier/registed.vue'], resolve),
            meta: { title: '供应注册' }
        },
        {
            path: '/toexamine',
            component: resolve => require(['../components/page/supplier/toexamine.vue'], resolve),
            meta: { title: '供应商审核信息' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
