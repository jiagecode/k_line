<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  export default {
    name: 'App'
  }
</script>
<style lang="less">
  .app-main {
    background-color: #F6F7FB;
  }

  .app-box {
    width: 100%;
    height: 100%;
    border-radius: 20px;
    background: rgba(255, 255, 255, 1);
  }

  .app-box-tab {
    width: 95%;
    height: 100%;
    margin: 0 auto;
    overflow: hidden;
    overflow-x: auto;
  }

  .app-box-title {
    color: #3B3269;
    font-size: 16px;
    font-weight: bolder;
    margin-top: 26px;
  }

  .app-box-changeBox {
    width: 100%;
    height: 70px;
    display: flex;
    flex-direction: row;
    font-size: 13px;
    align-items: center;
  }

  .app-box-input {
    display: flex;
    flex-direction: row;
  }

  .app-box-select {
    display: flex;
    flex-direction: row;
  }

  .app-box-input-txt {
    color: #CDCCE5;
    font-size: 13px;
    line-height: 28px;
    width: 65px;
    /*text-align: right;*/
  }

  .app-box-input > .el-input {
    height: 28px;
    line-height: 28px;
    width: 200px;
  }

  .el-input > .el-input__inner {
    height: 28px;
    line-height: 28px;
    /*width: 200px;*/
  }

  .app-marginR {
    margin-right: 12px;
  }

  .el-select {
    width: 120px;
  }

  .el-input--suffix > .el-input__inner {
    width: 100% !important;
  }

  .el-input__suffix > .el-input__suffix-inner, .el-input__icon {
    line-height: 28px !important;
  }

  .app-btn-box {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }

  .app-btn-box > .el-button {
    height: 28px;
    line-height: 28px;
    padding: 0 6px;
  }

  .app-tab-box {
    width: 100%;
    height: 700px;
  }
  .app-tab-btn{
    padding: 0 10px;
    height: 28px;
    line-height: 28px;
  }

  .app-tab-btn1 {
    background:rgba(245,171,74,1);
    border-color: rgba(245,171,74,1);
    opacity: 1;
  }

  .app-tab-btn2 {
    background: #4392E8;
    border-color: #4392E8;
    opacity: 1;
  }
  .app-tab-btn3 {
    background: #FC4B50;
    border-color: #FC4B50;
    opacity: 1;
  }
  .add-btn{
    /*width:60px;*/
    height:28px;
    background:rgba(245,171,74,1);
    opacity:1;
    border-color: rgba(245,171,74,1);
    line-height: 28px;
    padding: 0 10px;
  }

  .pageBox {
    width: 98%;
    height: 58px;
    text-align: center;
    display: flex;
    flex-direction: row;
    justify-content: center;

    .block {
      margin-top: 12px;
    }

    .el-pagination.is-background .el-pager li:not(.disabled).active {
      background-color: #4392E8 !important;
      color: #FFF;
    }

    .btn-prev, .btn-next, .number, .el-icon-more {
      height: 24px;
      line-height: 24px;
    }

    .el-pagination__jump, .el-pagination__editor {
      height: 24px !important;
      line-height: 24px !important;

      .el-input__inner {
        height: 24px !important;
        line-height: 24px !important;
      }
    }

    .refresh {
      width: 60px;
      height: 24px;
      background: #4392E8;
      opacity: 1;
      border-radius: 3px;
      float: left;
      color: #FFFFFF;
      font-size: 17px;
      line-height: 24px;
      margin-left: 20px;
      margin-top: 14px;

      .el-button {
        padding: 0;
        background: #4392E8;
        border: none;
        color: #fff;
      }
    }
  }
  .dialog-footer {
    font-size: 16px !important;
    color: #3B3269 !important;
    font-weight: bolder !important;
  }
  .el-dialog{
    border-radius:20px;
    display: flex;
    flex-direction: column;
    margin:0 !important;
    position:absolute;
    top:50%;
    left:50%;
    transform:translate(-50%,-50%);
    /*height:600px;*/
    max-height:calc(100% - 30px);
    max-width:calc(100% - 30px);
  }
  .el-dialog .el-dialog__body{
    flex:1;
    overflow: auto;
  }
  input[type=number] {
    -moz-appearance:textfield;
  }
  input[type=number]::-webkit-inner-spin-button,
  input[type=number]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

  .el-button:hover{
    color: -moz-default-color!important;
    color: -moz-default-background-color!important;
  }

  @font-face {
    font-family: 'iconfont';  /* project id 1814723 */
    src: url('//at.alicdn.com/t/font_1814723_pk6qpiordp.eot');
    src: url('//at.alicdn.com/t/font_1814723_pk6qpiordp.eot?#iefix') format('embedded-opentype'),
    url('//at.alicdn.com/t/font_1814723_pk6qpiordp.woff2') format('woff2'),
    url('//at.alicdn.com/t/font_1814723_pk6qpiordp.woff') format('woff'),
    url('//at.alicdn.com/t/font_1814723_pk6qpiordp.ttf') format('truetype'),
    url('//at.alicdn.com/t/font_1814723_pk6qpiordp.svg#iconfont') format('svg');
  }
  .iconfont{
    font-family:"iconfont";
    /*font-size:16px;*/
    font-style:normal;
    -webkit-font-smoothing: antialiased;
    -webkit-text-stroke-width: 0.2px;
    -moz-osx-font-smoothing: grayscale;
  }
  .oneTxt {
    display: inline-block;
    white-space: nowrap;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

</style>
