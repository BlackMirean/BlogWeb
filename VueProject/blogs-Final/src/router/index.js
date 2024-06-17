import {createRouter, createWebHistory} from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleManageVue from '@/views/article/ArticleManage.vue'
import ArticleBrowseVue from '@/views/article/ArticleBrowse.vue'
import ArticleStatisticsVue from '@/views/article/ArticleStatistics.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'

//定义路由关系
const routes = [
    {path:'/login', component:LoginVue },
    {path:'/', component:LayoutVue, redirect: '/article/browse',
        children:[
            {path:'/article/category', component: ArticleCategoryVue},
            {path:'/article/manage', component: ArticleManageVue},
            {path:'/article/browse', component: ArticleBrowseVue},
            {path:'/article/statistic', component: ArticleStatisticsVue},
            {path:'/user/info', component: UserInfoVue},
            {path:'/user/avatar', component: UserAvatarVue},
            {path:'/user/resetPassword', component: UserResetPassword}
        ]
    } 
]

//创建路由器
const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

export default router
