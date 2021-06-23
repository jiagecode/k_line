
// 请求跟地址
const baseUrl = 'http://localhost:1688/study'

/*
	method: get post
	url ：  地址
	data：  请求参数
*/
const doRequest = (method, url, data) => {
	
    uni.showLoading({title: '加载中', mask: true});
    return new Promise((resolve, reject) => {
		
		// 验证token
        console.log(uni.getStorageSync('token'));
		
        uni.request({
            url: baseUrl + url,
            method: method || 'get',
            data: data || '',
            header: {
				'content-type': 'application/x-www-form-urlencoded',
                Authorization: uni.getStorageSync('token')
            },
            success:(res)=> {
                if (res.data.code === 10000) {
                    resolve(res.data.data)
               // resolve调用后，即可传递到调用方使用then或者async+await同步方式进行处理逻辑
               resolve(result)
                }
            },
            fail:(err)=> {
               // reject调用后，即可传递到调用方使用catch或者async+await同步方式进行处理逻辑
                reject(err)
				console.log('请求失败')
                uni.hideLoading({})
            }
        })
    })
}

export default doRequest