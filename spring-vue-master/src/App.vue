<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>
<style>
   @import "./assets/css/main.css";
   /*@import "./assets/css/color-dark.css";     深色主题*/
   @import "./assets/css/theme-green/color-green.css";   浅绿色主题
   
    body .el-table th.gutter {
    display: table-cell !important;
  }

  body .el-table colgroup.gutter {
    display: table-cell !important;
  }

  /* 文件上传回显样式 */
  .upload_img_view .el-upload--picture-card {
    display: none;
  }
  /* 文件上传回显样式end */

</style>