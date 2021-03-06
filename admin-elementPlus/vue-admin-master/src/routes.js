import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import departmentManagement from './views/hospitalInformation/DepartmentManagement.vue'
import UserManagement from './views/hospitalInformation/UserManagement.vue'
import Generalitem from './views/hospitalInformation/Generalitem.vue'
import registrationLevel from './views/medicalInformation/RegistrationLevel.vue'
import nonDrugManagement from './views/medicalInformation/nonDrugManagement.vue'
import schedule from './views/medicalInformation/schedule.vue'
import dataBaseRecover from './views/dataBse/DataBaseRecover.vue'
import workstation from './views/Workstation/DoctorWorkstation'
import pay from './views/pay/pay'
import paying from './views/pay/paying'
import HomeMain from './views/echars/home'
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
    {
        path: '/paying/:id',
        component: paying,
        name: '',
        hidden: true
    },
    {
        path: '/pay',
        component: pay,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-home',
        leaf: true,//只有一个节点
        children: [
            { path: '/HomeMain', component: HomeMain, name: '首页' }
        ]
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '医院信息',
        iconCls: 'fa fa-users',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/departmentManagement', component: departmentManagement, name: '科室管理' },
            { path: '/UserManagement', component: UserManagement, name: '用户管理' },
            { path: '/Generalitem', component: Generalitem, name: '常数类别管理' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '医护信息',
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
            { path: '/dataBaseRecover', component: dataBaseRecover, name: '数据恢复' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-american-sign-language-interpreting',
        leaf: true,//只有一个节点
        children: [
            { path: '/workstation', component: workstation, name: '患者发药' }
        ]
    },
    {
        path: '/paying/*',
        hidden: true,
        redirect: { path: '/paying/*' }
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;