import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

export const articleCategoryListService = () => {
    //获取token状态
    // const tokenStore = useTokenStore()
    //通过请求头Authorization携带token
    // alert(tokenStore.token)
    // return request.get('/category', { headers: { 'Authorization': tokenStore.token } })
    return request.get('/category')
}

export const articleCategoryFormalListService = () => {
    return request.get('/category/formal')
}

export const articleCategoryAddService  = (categoryData) => {
    return request.post('/category', categoryData)
}

export const articleCategoryUpdateService  = (categoryData) => {
    return request.put('/category', categoryData)
}

export const articleCategoryDeleteService  = (id) => {
    return request.delete('/category?id='+id)
}

export const articleListService = (params) => {
    return request.get('/article', {params:params})
}

export const articleAddService = (articleData) => {
    return request.post('/article', articleData)
}

export const articleUpdateService  = (articleModel) => {
    return request.put('/article', articleModel)
}

export const articleDeleteService  = (id) => {
    return request.delete('/article?id='+id)
}

export const articleFormalListService = (params) => {
    return request.get('/article/formal', {params:params})
}
