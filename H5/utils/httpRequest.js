
// 请求跟地址
const baseUrl = 'h5'
// const baseUrl = 'http://192.168.1.9:1686/study'

/*
	method: get post
	url ：  地址
	data：  请求参数
*/
const doRequest = (method, url, data) => {

    // uni.showLoading({title: '加载中', mask: true});
    return new Promise((resolve, reject) => {

        uni.request({
            url: baseUrl + url,
            method: method || 'get',
            data: data || '',
            header: {
				'content-type': 'application/json',
                Authorization: uni.getStorageSync('token')
            },
            success:(res)=> {
				// uni.hideLoading();
                if (res.data.code === 10000) {
					// resolve调用即可传递到调用方使用 then
					// 或者 async+await 同步方式进行处理逻辑
                    resolve(res.data.data);
                } else {
					uni.showToast({
						icon: 'none',
						duration: 1000,
						title: res.data.message
					});
				}
            },
            fail:(err)=> {
                // uni.hideLoading();
               // reject调用即可传递到调用方使用 catch
			   // 或者async+await同步方式进行处理逻辑
				reject(res);
            }
        })
    })
}

export default doRequest
