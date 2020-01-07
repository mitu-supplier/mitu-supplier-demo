<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover"  style="height:300px;">
                    <el-form ref="form" style="margin-left:50px;" >
                        <el-form-item label="科室名称" style="margin-top:30px;">
                            <el-select v-model="org_value" filterable placeholder="请选择科室" @change="selectOrg">
                                <el-option
                                v-for="item in org_option"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="项目名称">
                            <el-select v-model="project_value" filterable placeholder="项目名称" @change="selectProject">
                                <el-option
                                v-for="item in project_option"
                                :key="item.id"
                                :label="item.projectName"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <div style="margin-top:40px;">项目总个数：{{project_number}}</div>
                        <div style="margin-top:40px;">项目总预算（元）：{{project_total}}</div>
                    </el-form>
                </el-card>
                
            </el-col>
            <el-col :span="16">
                
                <el-card shadow="hover" style="height:300px;">
                      <div class="total-class" id="myChart" style="width:100%; height: 250px"></div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col :span="24">
                <el-card shadow="hover">
                <el-table  :data="tableData" border class="table" >
                    <el-table-column type="selection" width="55" align="center" ></el-table-column>
                    <el-table-column type="index" label="序号" width="55" align="center" ></el-table-column>
                    <el-table-column prop="projectName" label="项目名称"  align="center" ></el-table-column>
                    <el-table-column prop="orgName" label="科室"  align="center" ></el-table-column>
                    <el-table-column prop="name" label="状态"  align="center">
                       <template slot-scope="scope">
                         <span v-if="scope.row.xy=='true'">未达标</span>
                         <span v-else="scope.row.dy=='true'">超出计划</span>
                       </template>
                    
                    </el-table-column>
                    
            </el-table>
            <div class="pagination">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="page"
                    :page-sizes="[5,10, 15, 30, 50, 100]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total" >
                 </el-pagination>
            </div>
                </el-card>
            </el-col>
            
        </el-row>
    </div>
</template>

<script>
    import bus from '../common/bus';
    import baseURL_ from '@/utils/baseUrl.js';
    export default {
        name: 'dashboard',
        
        data() {
            return {
               org_option:[],
               project_option:[],
               org_value:'',
               project_value:'',
               project_number:'',
               project_total:'',
               tableData:[],
               page:1,
               total:1000,
               pageSize:5,
               org_id:'',
               project_id:'',
            }
        },
        components: {
        },
        computed: {
           
        },
        created(){
            this.projectCount(null,null);
            this.org();
            this.getData();
        },
        
        methods: {
             handleSizeChange(val){
                   this.pageSize=val;
                   this.getData();
            },
            handleCurrentChange(val){
                   this.page=val;
                   this.getData();
            },
            async getData(){
                 const projectcount = await this.$http.get(baseURL_.lyjUrl+'/home/getProjects',{
                   params: {'page':this.page,'pageSize':this.pageSize,'orgId':this.org_id,'id':this.project_id}
                 });
                this.tableData=projectcount.data.data.list;
                this.total=projectcount.data.data.total;
                this.page=projectcount.data.data.page;
            },
            async projectCount(orgId,id){
             const projectcount = await this.$http.get(baseURL_.lyjUrl+'/home/getProjectsCount',{params: {'orgId':orgId,'id':id}});
               this.project_number=projectcount.data.data.number;
               this.project_total=projectcount.data.data.total;
               var char=[];
               var obj={};
               obj.name="已执行金额:"+projectcount.data.data.expenditure_total;
               obj.value=projectcount.data.data.expenditure_total;
               char.push(obj);
               obj={};
               obj.name="剩余未执行金额:"+projectcount.data.data.subtract_total;
               obj.value=projectcount.data.data.subtract_total;
               char.push(obj);
               let myChart = this.$echarts.init(
                   document.getElementById('myChart')
                )
               myChart.setOption({
                   legend: {
                        orient: 'vertical',
                        left: 'left',
                        
                    },
                color: ['#f59b24', '#08a7f1'], 
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data:char,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)',
                               
                            }
                        }
                    },
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                                    itemStyle : {
                                        normal : {
                                            label:{            //饼图图形上的文本标签
                                                    show:true,
                                                    position:'inner', //标签的位置
                                                    textStyle : {
                                                        fontWeight : 300 ,
                                                        fontSize : 12    //文字的字体大小
                                                    },
                                                    formatter:'{d}%'
                                                },
            
                                            labelLine : {
                                                show : false   //隐藏标示线
                                            }
                                        }
                                    },
                                data: char,
                            }
                    ]
                })


            },
           async org(){
               const org = await this.$http.get(baseURL_.lyjUrl+'/home/getOrg');
               this.org_option=org.data.data;
           },
           async selectOrg(row){
             this.project_option=[];
             this.project_value='';
             const project = await this.$http.get(baseURL_.lyjUrl+'/home/getProjectByOrgId',{params: {'orgId':row}});
             this.project_option=project.data.data;
             this.projectCount(row,null);
             this.org_id=row;
             this.project_id='';
             this.getData();
           },
           async selectProject(row){
              this.projectCount(null,row);
              this.project_id=row;
              this.org_id='',
              this.getData();
           }
        }
    }

</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }
    .table{
        width: 100%;
        font-size: 12px;
    }
</style>
