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
  .upload-excel-file .el-upload--text {    
    border: none !important;
    width: 100% !important;
    height: 100% !important;
  }
</style>