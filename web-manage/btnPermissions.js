export default (Vue) => {
  //自定义按钮权限功能
  Vue.directive('has',{
    inserted:function (el,binding) {
      let className = binding.value.class; //这个地方要注意了，看下面页面上的代码，对应取得相应的值
      if(!Vue.prototype.$_has(className)){
        el.parentNode.removeChild(el);
      }
    }
  });
  Vue.prototype.$_has = function (val) {
    let isShow = false;
    let btnPowerArr = sessionStorage.getItem('btnPowerString');
    if(btnPowerArr==undefined || btnPowerArr==null){
      return false;
    }
    if(btnPowerArr.indexOf(val) > -1){
      isShow = true;
    }
    return isShow;
  }
}
