<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">商户合作系统</div>
            <el-form :model="ruleForm"  ref="ruleForm" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="用户名">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="密码" v-model="ruleForm.password" @keyup.enter.native="submitForm()">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <p class="login-tips right-supplier" style="text-align:right;" ><span style="cursor: pointer;" @click="registed()">供应商入驻</span></p>
            </el-form>
        </div>
        <remote-js src="http://pv.sohu.com/cityjson?ie=utf-8" ></remote-js>
    </div>
    
</template>

<script>
import Vue from "vue";
import baseURL_ from '@/utils/baseUrl.js';
import JsEncrypt from 'jsencrypt/bin/jsencrypt';
Vue.prototype.$jsEncrypt=JsEncrypt;
    export default {
        data: function(){
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                
            }
        },
        // mounted(){
            // alert(Base64.decode(null));
            // var baseEncode = Base64.encode('1');
            // alert(baseEncode);
            // var baseDecode = Base64.decode('MQ=='); 
            // alert(baseDecode);
        // },
        components: {
            'remote-js': {
                render(createElement) {
                    return createElement('script', { attrs: { type: 'text/javascript', src: this.src }});
                },
                props: {
                    src: { type: String, required: true },
                },
            },
        },
        methods: {
            async submitForm() {
                var ip = returnCitySN["cip"];
                var name = returnCitySN["cname"];
                localStorage.setItem('forestToken_address',ip+"_"+name);
                let jse=this.$jsEncrypt;
                jse.prototype.setPublicKey(baseURL_.public_key);
                let passwordDeg = jse.prototype.encrypt(this.ruleForm.password);
                const login = await this.$http.get(baseURL_.loginUrl+'/login/do', {
                    params: {'loginName':this.ruleForm.username,'password':passwordDeg}
                });
                if(login.data.statusCode==200){
                    localStorage.setItem('forestToken',login.data.data.token);
                    localStorage.setItem('ms_username',this.ruleForm.username);
                    localStorage.removeItem('newActive');
                    localStorage.removeItem('newUserId');
                    localStorage.removeItem('newSupplierId');
                    if(login.data.data.isSupplier == 1){
                        if(login.data.data.status == '-1'){
                            // 未提交  跳转继续填写
                            // activeId
                            var stepNum = '';
                            if(login.data.data.registerStep < 5){
                                stepNum = login.data.data.registerStep + 1;
                            }else{
                                stepNum = login.data.data.registerStep;
                            }
                            this.$router.push({
                                path: '/registed',
                                name: 'registed',
                                params: {
                                    activeId: stepNum
                                }
                            });
                        } else if(login.data.data.status == '0'){
                            // 待审核
                            sessionStorage.setItem("auditType",'1');
                            this.$router.push('/toexamine');
                        } else if(login.data.data.status == '1'){
                            // 审核通过   
                            this.$router.push('/');
                        } else if(login.data.data.status == '2'){
                            // 审核退回   
                            sessionStorage.setItem("auditType",'2');
                            this.$router.push('/toexamine');
                        }
                    }else{
                        this.$router.push('/');
                    }
                    
                }else{
                    localStorage.removeItem('forestToken');
                    alert(login.data.data);
                    
                }
            },
            registed(){
                localStorage.removeItem('newActive');
                this.$router.push('/registed');
            }
        }
    }
</script>

<style scoped>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
        background-image: url(../../../assets/img/login-bg.jpg);
        background-size: 100%;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:350px;
        margin:-190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255,255,255, 0.3);
        overflow: hidden;
    }
    .ms-content{
        padding: 30px 30px;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
    .login-tips{
        font-size:12px;
        line-height:30px;
        color:#fff;
    }

</style>