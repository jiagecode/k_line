import Vue from 'vue'
import App from './App'

// 弹出层引入
import Vuex from 'vuex';
import vusLayerInit from '@/components/vusui-app-layer/vusui-layer.js';
import vusLayer from '@/components/vusui-app-layer/vusui-layer.vue';
Vue.use(Vuex);
Vue.use(vusLayerInit);
Vue.component('vus-layer', vusLayer); //设置组件名称
// 弹出层引入

Vue.config.productionTip = false


App.mpType = 'app'

const store = new Vuex.Store();
const app = new Vue({
	store,
	...App
})
app.$mount()
