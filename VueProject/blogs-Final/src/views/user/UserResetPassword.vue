<script setup>
import { ref } from 'vue'

const pwd = ref({
    old_pwd:'',
    new_pwd:'',
    re_pwd:''
})

const rules = {
    old_pwd: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    new_pwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    re_pwd: [
        { required: true, message: '请确认新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        },
        {
            validator: (rule, value) => value === pwd.value.new_pwd,
            message: '两次输入的密码不一致',
            trigger: 'blur'
        }
    ]
}

import {useRouter} from 'vue-router'
const router = useRouter();
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();
import useUserInfoStore  from '@/stores/userInfo.js';
const userInfoStore = useUserInfoStore();
import {userPasswordUpdateService} from '@/api/user.js'
import {ElMessage} from 'element-plus'
const updateUserPassword = async() =>{
    let result = await userPasswordUpdateService(pwd.value)
    tokenStore.removeToken();
    userInfoStore.removeInfo();
    router.push('/login')
    ElMessage.success(result.msg? result.msg : '修改成功');
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="pwd" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="确认原密码" prop="old_pwd">
                        <el-input v-model="pwd.old_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="new_pwd">
                        <el-input v-model="pwd.new_pwd"></el-input>
                    </el-form-item>
                    <el-form-item label="确定新密码" prop="re_pwd">
                        <el-input v-model="pwd.re_pwd"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserPassword">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>