import axios from 'axios';

let parameter='http://localhost:8000/hospital';
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
export const getAllScheduleList =params=>{return axios.post(`${parameter}/schedule/getAllScheduleList`,params);};

