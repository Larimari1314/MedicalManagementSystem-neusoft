import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import departmentManagement from './views/nav1/DepartmentManagement.vue'
import UserManagement from './views/nav1/UserManagement.vue'
import user from './views/nav1/user.vue'
import registrationLevel from './views/nav2/RegistrationLevel.vue'
import nonDrugManagement from './views/nav2/nonDrugManagement.vue'
import schedule from './views/nav2/schedule.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '导航一',
        iconCls: 'fa fa-users',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/departmentManagement', component: departmentManagement, name: '科室管理' },
            { path: '/UserManagement', component: UserManagement, name: '用户管理' },
            { path: '/user', component: user, name: '常数类别管理' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '导航二',
        iconCls: 'fa fa-ambulance',
        children: [
            { path: '/registrationLevel', component: registrationLevel, name: '挂号级别管理' },
            { path: '/nonDrugManagement', component: nonDrugManagement, name: '非药品收费项目管理' },
            { path: '/schedule', component: schedule, name: '医生排班管理' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-database',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: schedule, name: '数据恢复' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;