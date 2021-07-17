<template>
	<!-- 手机区号 -->
	<view>
		<!-- 搜索框 
		<uni-search-bar cancel-button="none"  @input="onSearchBarInput" />-->
		<!-- 区号信息 -->
		<view>
			<list-index v-if="cheakListLength" :dataList="list" @click="chooseEvent"></list-index>
		</view>
	</view>
</template>

<script>
	// 引入组件
	import listIndex from './list-index.vue'
	// 区号数据
	import countrys from './country';
	
	// 声明组件
	export default{
		components:{
			listIndex
		},
		data(){
			return{
				list: []
			}
		},
		onLoad() {
			let letters = [];
			let letter = '';
			
			// 取出头
			for (let item of countrys) {
				if(letter != item.pinyin){
					letter = item.pinyin
					letters.push(item.pinyin);
				} 
			}
			
			// 根据头找
			for(let letter of letters){
				let data = [];
				for (let item of countrys) {
					if(letter === item.pinyin){
						let datas = {
							area:item.area,
							area_code:item.area_code
						}
						data.push(datas);
					}
				}
				this.list.push({letter: letter, data: data})
			}
		},
		computed: {
			cheakListLength() {
				return this.list.filter( item => {
					return item.data.length > 0
				})
			}
		},
		methods:{
			chooseEvent(data) {
				// redirectTo 关闭当前页面，跳转到应用内的某个页面。
				// navigateTo 保留当前页面，跳转到应用内的某个页面
				// reLaunch 关闭所有页面，打开到应用内的某个页面。 
				// navigateBack 关闭当前页面，返回上一页面
				// 获取所有页面栈实例列表
				let pages = getCurrentPages();
				// 当前页页面实例
				let nowPage = pages[ pages.length - 1];  
				// 上一页页面实例
				let prevPage = pages[ pages.length - 2 ];
				// 修改上一页data里面的countryAndCode参数值为data
				prevPage.$vm.countryAndCode = data;
				console.log(data);
				
				setTimeout(() => {
					// 返回上一页面
					uni.navigateBack({
					    delta: 1
					})}, 
				500)
			}
		}
	}
</script>

<style>
</style>