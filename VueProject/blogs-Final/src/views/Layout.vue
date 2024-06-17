<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    Opportunity,
    User,
    Crop,
    EditPen,
    SwitchButton,
    Histogram,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import {userInfoService } from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const getUserInfo = async()=> {
    //调用接口
    let result = await userInfoService();
    userInfoStore.setInfo(result.data)

}

getUserInfo();

import {useRouter} from 'vue-router'
const router = useRouter();
import {ElMessage, ElMessageBox} from 'element-plus'
const handleCommand =(command)=> {
    if (command === 'logout') {
        ElMessageBox.confirm('确认退出？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(async() => {
            //1.清空
            tokenStore.removeToken();
            userInfoStore.removeInfo();
            
            router.push('/login')

            ElMessage({
            type: 'success',
            message: '退出登录成功!'
            });
        }).catch(() => {
            ElMessage({
            type: 'info',
            message: '已取消退出登录'
            });          
        });
    } else {
        router.push('/user/' + command)
    }
}


</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
                router>
                <el-menu-item index="/article/category">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>文章分类</span>
                </el-menu-item>
                <el-menu-item index="/article/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>文章管理</span>   
                </el-menu-item>
                <el-menu-item index="/article/browse">
                    <el-icon>
                        <Opportunity/>
                    </el-icon>
                    <span>文章浏览</span>
                </el-menu-item>
                <el-menu-item index="/article/statistic">
                    <el-icon>
                        <Histogram/>
                    </el-icon>
                    <span>文章统计</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>
                    <el-menu-item index="/user/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>用户名：<strong> {{ userInfoStore.info.nickname}} </strong></div>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.userPic?userInfoStore.info.userPic:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>                
            </el-main>
            <!-- 底部区域 -->
            <el-footer>220110900735 徐峥扬 22软件工程3班</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            position: relative; /* 确保伪元素可以正确定位 */
            height: 120px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        &__logo::before {
            content: "Blog"; /* 添加的文本 */
            font-size: 40px; /* 字体大小 */
            color: white; /* 字体颜色 */
            font-family: 'Arial', sans-serif; /* 设置字体 */
            color: white; /* 字体颜色为白色 */
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6); /* 添加黑色阴影 */
            font-weight: bold; /* 字体加粗 */
            font-style: italic; /* 设置字体为斜体 */
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>