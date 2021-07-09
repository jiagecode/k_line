import request from '@/utils/request'

// 查询操作用户信息
export function querySpecialUserInfo (params) {
    return request({
        url: '/sysDiy/diyUserInfo',
        method: 'get',
        params
    })
}

// 修改操作用户信息
export function updateSpecialUserInfo (data) {
    return request({
        url: '/sysDiy/changeInfo',
        method: 'post',
        data:data
    })
}

// 查询SKU下拉列表
export function querySkuDataList (params) {
    return request({
        url: '/sysDiy/skuList',
        method: 'get',
        params
    })
}

// 生成模拟数据并返回
export function createImitateData (data) {
    return request({
        url: '/sysDiy/createData',
        method: 'post',
        data:data
    })
}

// 编辑操作记录
export function editDiyRecordByType (data) {
    return request({
        url: '/sysDiy/editRecord',
        method: 'post',
        data:data
    })
}
