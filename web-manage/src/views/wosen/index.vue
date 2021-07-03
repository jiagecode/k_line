<template>
  <div id="wosen" class="app-container">
    <div class="app-box">
      <div class="app-box-tab">
        <div class="app-box-title">沃森管理</div>
        <div class="app-box-changeBox">
          <el-upload
            style="margin-right: 10px;border-radius: 4px"
            class="formBtn2"
            action=""
            :on-change="handleChange"
            :show-file-list="false"
            :on-remove="handleRemove"
            :file-list="fileListUpload"
            :limit="limitUpload"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"
            :auto-upload="false">
            <el-button size="small" class="formBtn2" type="primary" ><i class="iconfont marginR">&#xe609;</i>批量上传</el-button>
          </el-upload>
          <a :href='"http://192.168.110.76/mb/mb.xlsx?time=153465464"' class="file"><i class="iconfont marginR">&#xe628;</i>下载模板</a>
          <el-button type="primary" class="app-marginR add-btn" icon="el-icon-s-operation" @click="openDia">确定上传</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name:'index',
    data() {
      return {
        tableData: [],
        tableData1: [],
        fileListUpload:[],
        fileListUpload1:[],
        limitUpload:1000,
        tableData2:"",
      }
    },
    created() {

    },
    methods: {
      // excel表上传
      handleChange(file, fileList){
        this.fileTemp = file.raw
        // 判断上传文件格式
        if(this.fileTemp){
          if((this.fileTemp.type == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') || (this.fileTemp.type == 'application/vnd.ms-excel')){
            this.importfxx(this.fileTemp)
            // console.log(this.fileTemp)
          } else {
            this.$message({
              type:'warning',
              message:'附件格式错误，请删除后重新上传！'
            })
          }
        } else {
          this.$message({
            type:'warning',
            message:'请上传附件！'
          })
        }
      },
      // 移除excel表
      handleRemove(file,fileList){
        console.log('移除');
        this.fileTemp = null
      },
      importfxx(obj) {
        let _this = this;
        // 通过DOM取文件数据
        this.file = obj
        var rABS = false; //是否将文件读取为二进制字符串
        var f = this.file;
        var reader = new FileReader();
        FileReader.prototype.readAsBinaryString = function(f) {
          var binary = "";
          var rABS = false; //是否将文件读取为二进制字符串
          var pt = this;
          var wb; //读取完成的数据
          var outdata;
          var reader = new FileReader();
          reader.onload = function(e) {
            var bytes = new Uint8Array(reader.result);
            console.log(reader.result);
            var length = bytes.byteLength;
            for(var i = 0; i < length; i++) {
              binary += String.fromCharCode(bytes[i]);
            }
            var XLSX = require('xlsx');
            if(rABS) {
              wb = XLSX.read(btoa(fixdata(binary)), { //手动转化
                type: 'base64'
              });
            } else {
              wb = XLSX.read(binary, {
                type: 'binary'
              });
            }
            outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);//outdata就是读取excel内容之后输出的东西
            this.da = [...outdata]
            console.log(this.da);
            let shuju = this.da;

            let arr = []
            this.da.map(v => {
              let obj = {}
              obj.payAccount = v['payAccount'].replace(/\s*/g,"")
              obj.payName = v['payName']
              obj.payAmount = v['payAmount']
              arr.push(obj)
            })
            _this.tableData = arr


            return arr
            // 把读取的excel表格中的内容放进tableData2中(这里要改成自己的表的名字)
            _this.tableData2 = _this.tableData2.concat(arr)
          }
          reader.readAsArrayBuffer(f);

        }
        if(rABS) {
          reader.readAsArrayBuffer(f);
        } else {
          reader.readAsBinaryString(f);
        }
      },
      openDia(){
        console.log(this.tableData)
      },
    },
  }
</script>

<style lang="less">
  #wosen {
    height: 800px;
    opacity: 1;
    margin: 0 auto;
    margin-top: 30px;
    .formBtn2 {
      height: 28px;
      line-height: 28px;
      background: rgba(92, 56, 130, 1);
      opacity: 1;
      color: #fff;
      padding: 0 6px;
      font-size: 13px;
      border-color: rgba(92, 56, 130, 1);
    }
    .marginR {
      margin-right: 4px;
    }
    .file {
      width: 90px;
      position: relative;
      display: block;
      background: rgba(92, 56, 130, 1);
      border: 1px solid rgba(92, 56, 130, 1);
      border-radius: 4px;
      height: 28px;
      line-height: 28px;
      /*overflow: hidden;*/
      color: #fff;
      text-decoration: none;
      text-indent: 0;
      margin-right: 10px;
      text-align: center;
    }
    .file input {
      position: absolute;
      font-size: 100px;
      right: 0;
      top: 0;
      opacity: 0;
    }
    .file:hover {
      /*background: #AADFFD;*/
      /*border-color: #78C3F3;*/
      /*color: #004974;*/
      text-decoration: none;
    }
    .el-select{
      width: 150px!important;
    }

    .el-table {
      border-radius: 10px;
      text-align: center;

      td, tr, th {
        text-align: center;
      }

      .removeBnt {
        font-size: 20px;
        color: #F75F78;
        cursor: pointer;
      }
    }
    .el-dialog{
      width: 460px;
    }
    .payNameDiaBox {
      width: 80%;
      margin: 0 auto;
      .el-select{
        width: 100% !important;
      }
      .payNameBnt1{
        width:60px;
        height:28px;
        background:rgba(246,232,255,1);
        opacity:1;
        line-height: 28px;
        padding: 0;
        color: #3B3269;
        font-size: 13px;
        border-color: rgba(246,232,255,1);
        float: left;
      }
      .payNameBnt2{
        width:60px;
        height:28px;
        background:rgba(92,56,130,1);
        opacity:1;
        line-height: 28px;
        padding: 0;
        color: #FFFFFF;
        font-size: 13px;
        border-color: rgba(92,56,130,1);
        float: right;
      }
      .payNameBnt2:focus, .payNameBnt2:hover{
        color: #FFFFFF;
        border-color: rgba(92,56,130,1);
        background:rgba(92,56,130,1);
      }
      .payNameBnt1:focus, .payNameBnt1:hover{
        color: #3B3269;
        border-color: rgba(246,232,255,1);
        background:rgba(246,232,255,1);
      }
    }
  }
</style>
