import axios from 'axios';

let parameter='http://localhost:8000/hospital';
// let parameter='http://192.168.1.106:8000/hospital';
/*需要拦截请求*/
//查找科室列表
export const getDepartClassList =params=>{return axios.get(`${parameter}/departClassification/getDepartClassList`,{params:params});};
//查找科室
export const getDepartment = params => { return axios.post(`${parameter}/department/findDepartment`, params) };
//修改科室
export const addDepartment = params => { return axios.post(`${parameter}/department/updateDepartment`, params) };
//添加科室
export const saveDepartment = params => { return axios.post(`${parameter}/department/addDepartment`, params) };

//查找性别
export const findGender =params=>{return axios.get(`${parameter}/generalitem/findGender`,{params:params});};
//查找用户
export const findUser = params => { return axios.post(`${parameter}/user/findUser`, params) };
//删除用固话
export const deleteUser = params => { return axios.post(`${parameter}/user/deleteByIds`,params) };
//修改用户
export const editUser = params => { return axios.post(`${parameter}/user/updateUser`,params) };
//添加用户
export const addUser = params => { return axios.post(`${parameter}/user/addUser`,  params ) };
//导出用户模板
export const templateDownloadUser = params => { return axios.post(`${parameter}/user/dataTemplateDownload`, "", {responseType: 'blob'})};
//导出全部数据
export const exportDataUser = params => { return axios.post(`${parameter}/user/exportData`, "", {responseType: 'blob'})};
//导入数据
export const analyseFileUser = params => { return axios.get(`${parameter}/user/analyseFile`)};

//显示全部挂号类型
export const findRegistered = params => { return axios.post(`${parameter}/registered/findByName`,  params ) };
//跟新价格
export const updateRegisteredPrice = params => { return axios.post(`${parameter}/registered/updatePrice`,  params ) };
//根据挂号级别查询医生
export const findDoctorByRegistration = params => { return axios.post(`${parameter}/doctor/findByRegistered`,  params ) };

//药品规格查询
export const findAllSpecification = params => { return axios.get(`${parameter}/nondrug/findAllSpecification`)};
//药品查询
export const getAllDrugByRequire = params => { return axios.post(`${parameter}/nondrug/getAllDrugByRequire`,  params ) };
//修改状态
export const DrugModifyEnable = params => { return axios.post(`${parameter}/nondrug/modifyEnable`,  params ) };
//删除业务
export const deleteByIdsDrug = params => { return axios.post(`${parameter}/nondrug/deleteByIds`,  params ) };
//跟新药品信息
export const updateDrug = params => { return axios.post(`${parameter}/nondrug/updateDrug`,  params ) };
//显示
export const addDrug = params => { return axios.post(`${parameter}/nondrug/addDrug`,  params ) };
//下载数据模板药品
export const templateDownloadDrug = params => { return axios.post(`${parameter}/nondrug/dataTemplateDownload`, "", {responseType: 'blob'})};
//下载全部数据药品
export const exportDataDrug = params => { return axios.post(`${parameter}/nondrug/exportData`, "", {responseType: 'blob'})};
//上传数据
export const analyseFileDrug = params => { return axios.get(`${parameter}/nondrug/analyseFile`)};

//显示挂号级别
export const findByRegistered =params=>{return axios.get(`${parameter}/generalitem/findByRegistered`,{params:params});};
//获取科名
export const findDepartment =params=>{return axios.get(`${parameter}/department/findDepartment`,{params:params});};
//获取医生数据
export const findAllDoctor =params=>{return axios.post(`${parameter}/doctor/findAllDoctor`,params);};
//批量删除
export const deleteByIdsDoctor =params=>{return axios.post(`${parameter}/doctor/deleteByIds`,params);};
//跟新医生信息
export const addDoctor =params=>{return axios.post(`${parameter}/doctor/addDoctor`,params);};
//查找班次
export const findScheduleList =params=>{return axios.get(`${parameter}/schedule/findScheduleList`,{params:params});};
//获取排班列表
export const getAllScheduleList =params=>{return axios.post(`${parameter}/schedule/getAllScheduleList`,params);};
//设置排班规则
export const setUpSchedule =params=>{return axios.post(`${parameter}/doctor/setUpSchedule`,params);};

//显示全部常数类别
export const getAllGeneralItem =params=>{return axios.post(`${parameter}/generalitem/getAll`,params);};
//修改常数类别
export const editGeneralItem =params=>{return axios.post(`${parameter}/generalitem/editGeneralitem`,params);};

//永久删除
export const deletePermanently =params=>{return axios.post(`${parameter}/doctor/deletePermanently`,params);};
//恢复数据
export const dataRecoveryDoctor =params=>{return axios.post(`${parameter}/doctor/dataRecovery`,params);};
//显示·1数据
export const checkDeleteDoctor =params=>{return axios.get(`${parameter}/doctor/checkDeleteDoctor`);};
//获取删除的药品信息
export const getDeleteDrug =params=>{return axios.get(`${parameter}/nondrug/getDeleteDrug`);};
//药品恢复
export const dataRecoveryDrug =params=>{return axios.post(`${parameter}/nondrug/dataRecovery`,params);};
//永久删除药品
export const deletePermanentlyDrug =params=>{return axios.post(`${parameter}/nondrug/deletePermanently`,params);};
//显示患者信息
export const getDeleteUser =params=>{return axios.get(`${parameter}/user/getDeleteUser`);};
//药品恢复
export const dataRecoveryUser =params=>{return axios.post(`${parameter}/user/dataRecovery`,params);};
//永久删除用户信息
export const deletePermanentlyUser =params=>{return axios.post(`${parameter}/user/deletePermanently`,params);};

//修改用户信息
export const modifyLoginInformation =params=>{return axios.post(`${parameter}/login/modifyLoginInformation`,params);};

/*不需要拦截请求*/
//获取登录广告
export const GetAllAds =params=>{return axios.get(`${parameter}/login/getAllAds`);};
//获取用户登录头像
export const getAdminImgUrl =params=>{return axios.post(`${parameter}/login/getAdminImgUrl`,params);};
export const logout =params=>{return axios.get(`${parameter}/login/logout`);};
//管理员登录
export const adminRequestLogin =params=>{return axios.post(`${parameter}/login/adminRequestLogin`,params);};

//获取全部待取药品信息
export const findPatientMedic =params=>{return axios.post(`${parameter}/patientMedicine/findPatientMedic`,params);};
//获取当前药品发放信息
export const getMedicationStatus =params=>{return axios.get(`${parameter}/generalitem/getMedicationStatus`);};
//根绝id查询所属药品
export const ViewListMedicines =params=>{return axios.post(`${parameter}/patientMedicine/viewListMedicines`,params);};
//退药
export const withdrawal =params=>{return axios.post(`${parameter}/patientMedicine/withdrawal`,params);};
//根据id查找订单信息
export const orderDetails =params=>{return axios.post(`${parameter}/patientMedicine/orderDetails`,params);};
//创建相同订单
export const createSameOrder =params=>{return axios.post(`${parameter}/patientMedicine/createSameOrder`,params);};
//数据校验
export const dataValidation =params=>{return axios.post(`${parameter}/pay/dataValidation`,params);};
//获取订单数据
export const paymentStatement =params=>{return axios.post(`${parameter}/pay/paymentStatement`,params);};

export const paymentStatus =params=>{return axios.post(`${parameter}/pay/paymentStatus`,params);};
//首页获取数量
export const mainGetNumber =params=>{return axios.post(`${parameter}/user/mainGetNumber`,params);};
export const getDayNumber =params=>{return axios.get(`${parameter}/patientMedicine/getDayNumber`);};
export const getAllValues =params=>{return axios.get(`${parameter}/patientMedicine/getAllValues`);};

export const getRegisDoctor =params=>{return axios.get(`${parameter}/doctor/getRegisDoctor`);};
export const getStateStatistics =params=>{return axios.get(`${parameter}/patientMedicine/getStateStatistics`);};
//支付
export const pay =params=>{return axios.post(`http://192.168.1.106:8000/hospital/pay/pay`,params);};
export const scanStatus =params=>{return axios.post(`http://192.168.1.106:8000/hospital/pay/scanStatus`,params);};