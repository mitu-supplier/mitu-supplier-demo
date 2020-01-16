<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 品目管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <ul>
            <li class="li-left">
                <div class="container">
                    <ul id="ztree" class="ztree"></ul>
                </div>
            </li>
            <li class="li-right">
                <div class="container">
                    <el-form ref="form" :model="form" label-width="100px">
                        <el-form-item label="名称">
                        <el-input v-model="form.name" class="input" style="width:200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="编码">
                            <el-input v-model="form.code" class="input" style="width:200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="父节点名称">
                            <el-input v-model="form.parentName" class="input" style="width:200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="排序">
                            <el-input v-model="form.priority" class="input" style="width:200px;"></el-input>
                        </el-form-item>
                        <el-form-item label="是否启用">
                            <el-radio v-model="form.usable" label="1">启用</el-radio>
                            <el-radio v-model="form.usable" label="0">禁用</el-radio>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer" style="margin-left:120px;">
                        <el-button type="primary" @click="saveEdit" >保存</el-button>
                    </span>
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
    import { fetchData } from '../../../api/index';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'basetable',
        data() {
            return {
                page:1,
                total:1000,
                pageSize:10,
                tableData: [],
                multipleSelection: [],
                clickNode:{},
                form:{
                    id:'',
                   name:'',
                   code:'',
                   priority:'',
                   usable:'',
                   treeIds:'',
                   pic:''
                   
                },
                 setting:{
                     view: {
                            addHoverDom: this.addHoverDom,
                            removeHoverDom: this.removeHoverDom,
                            selectedMulti: false
                        },
                        edit: {
                            enable: true,
                            editNameSelectAll: true,
                            showRemoveBtn: this.showRemoveBtn,
                            showRenameBtn: false
                           
                        },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "parentId"
                        }
                    },
                    callback: {
                        onClick: this.zTreeOnClick,
                        beforeRemove:this.beforeRemove
                    }
                },
                
            
            }
        },
        created() {
            this.getTreeData();
        },
        computed: {
           
        },
        methods: {
            
		  addHoverDom(treeId, treeNode) {
              let then=this;
                var sObj = $("#" + treeNode.tId + "_span");
                if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
                var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                    + "' title='add node' onfocus='this.blur();'></span>";
                sObj.after(addStr);
                var btn = $("#addBtn_"+treeNode.tId);
                if (btn) btn.bind("click", function(){
                    then.form={};
                    then.form.parentId=treeNode.id;
                    then.form.parentName=treeNode.name;
                    then.clickNode=treeNode;
                    return false;
                });
		   },
            showRemoveBtn(treeId, treeNode) {
                return !treeNode.isParent;
		   },
            removeHoverDom(treeId, treeNode) {
			   $("#addBtn_"+treeNode.tId).unbind().remove();
		    },
            beforeRemove(treeId, treeNode){
             this.$confirm('确认删除？')
                .then( e=> {
                  
                   this.delete(treeNode);

                }).catch(_ => {
                  
                });
              return false;
            },
             
            async delete(treeNode){
                 const del = await this.$http.get(baseURL_.mallUrl+'/catalogs/delete',{ 
                        params: {'id':treeNode.id}
                    });
                this.clickNode=treeNode.getParentNode();
                this.$message(del.data.data);
                this.getTreeData();
            },
            async saveEdit(){
                 var addOrEdit={};
                 if(this.form.id!=null){
                     addOrEdit= await this.$http.post(baseURL_.mallUrl+'/catalogs/update',this.$qs.stringify(this.form));
                 }else{
                     addOrEdit= await this.$http.post(baseURL_.mallUrl+'/catalogs/save',this.$qs.stringify(this.form));
                     
                 }
                 this.$message(addOrEdit.data.data);
                 this.getTreeData();
                 
            },
            async zTreeOnClick(event, treeId, treeNode){
                const catalog = await this.$http.get(baseURL_.mallUrl+'/catalogs/getById',{
                    params: {'id':treeNode.id}
                });
                this.clickNode=treeNode;
                
                this.form=catalog.data.data;
                this.form.usable=catalog.data.data.usable+'';
                this.form.parentName=treeNode.getParentNode().name;
            },
            async getTreeData(){
                const catalogTree = await this.$http.get(baseURL_.mallUrl+'/catalogs/getAll');
                $.fn.zTree.init($("#ztree"), this.setting, catalogTree.data.data);
                var zTree = $.fn.zTree.getZTreeObj("ztree");
                zTree.selectNode(this.clickNode);
                //zTree.expandNode(this.clickNode, true, true, true);//指定选中ID节点展开
            }
            
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 12px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 12px;
    }
  
    .red{
        color: #ff0000;
    }
    .mr10{
        margin-right: 10px;
    }
    .container{
        min-height:500px;
    }
    .input{
        width:90%;
    }
   ul.ztree{
        margin-top: 10px;
        width: 200px;
        height: 450px;
        overflow-y: auto;
        margin:auto;
        
    }
    .li-left{
        width: 15%;
        float: left;
        list-style:none;
    }
    .li-right{
        width: 75%;
        float: left;
        list-style:none;
    }
    
</style>
<style>
  .container .ztree li span.button.add{
        margin-left: 2px;
        margin-right: -1px;
        background-position: -144px 0;
        vertical-align: top;
    }

</style>