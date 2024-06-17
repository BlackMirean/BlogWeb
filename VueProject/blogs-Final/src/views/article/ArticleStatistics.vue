<script setup>
import {
    Edit,
    Delete,
    More
} from '@element-plus/icons-vue'
import cover from '@/assets/cover.jpg'
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

//文章分类数据模型
const categorys = ref([
    {
        "id": 3,
        "categoryName": "美食",
        "categoryAlias": "my",
        "createTime": "2023-09-02 12:06:59",
        "updateTime": "2023-09-02 12:06:59"
    },
    {
        "id": 4,
        "categoryName": "娱乐",
        "categoryAlias": "yl",
        "createTime": "2023-09-02 12:08:16",
        "updateTime": "2023-09-02 12:08:16"
    },
    {
        "id": 5,
        "categoryName": "军事",
        "categoryAlias": "js",
        "createTime": "2023-09-02 12:08:33",
        "updateTime": "2023-09-02 12:08:33"
    }
])

//用户搜索时选中的分类id
const categoryId=ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
    {
        "id": 5,
        "title": "陕西旅游攻略",
        "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
        "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
        "state": "草稿",
        "categoryId": 2,
        "createTime": "2023-09-03 11:55:30",
        "updateTime": "2023-09-03 11:55:30"
    },
])


import { articleCategoryListService, articleListService } from '@/api/article.js'
const getAllCategory = async () => {
    let result = await articleCategoryListService();
    categorys.value = result.data;
}

const articleListFormal = async()=> {
    let params = {
        pageNum:1,
        pageSize:99999999,
        categoryId:null,
        state:"已发布"
    }
    let result = await articleListService(params)
    articles.value = result.data.items;
    
    //处理数据
    for(let i=0;i<articles.value.length;i++) {
        let article = articles.value[i];
        for(let j=0;j<categorys.value.length;j++) {
            if(article.categoryId===categorys.value[j].id) {
                article.categoryName=categorys.value[j].categoryName;
            }
        }
    }

    updateCharts();
}

const totalArticles = ref(20)//总条数
const publishedArticles = ref(20)
const draftArticles = ref(20)
const Favorites = ref(9999)
const Likes = ref(9999)
const Revenue = ref(998243021)

const articleListCnt = async()=> {
    let params = {
        pageNum:1,
        pageSize:99999999,
        categoryId:null,
        state:null
    }
    let result = await articleListService(params)
    totalArticles.value = result.data.total;
    params = {
        pageNum:1,
        pageSize:99999999,
        categoryId:null,
        state:'草稿'
    }
    result = await articleListService(params)
    draftArticles.value = result.data.total;
    publishedArticles.value = totalArticles.value - draftArticles.value;
}

onMounted(() => {
    getAllCategory()
    articleListCnt();
    articleListFormal();
})

getAllCategory()
articleListCnt();
articleListFormal();


import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {Plus} from '@element-plus/icons-vue'

// 设置图表容器引用
const pieChartRef = ref(null);
const lineChartRef = ref(null)

const updateCharts = () => {
    // 更新柱状图数据
    const articleCountsByCategory = categorys.value.map(category => ({
        name: category.categoryName,
        value: articles.value.filter(article => article.categoryId === category.id).length
    }));

    // 更新饼图数据
    const pieChart = echarts.init(pieChartRef.value);
    pieChart.setOption({
        title: {
            text: '发布文章分类占比',
            left: 'center',
            top: '10%'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: '5', // 图例位置设置为左上角
            top: '20'
        },
        series: [{
            name: '文章分类',
            type: 'pie',
            radius: '50%',
            data: articleCountsByCategory,
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }]
    });

    // 更新折线图数据
    const articlesByDate = articles.value
        .filter(article => article.state === '已发布')
        .reduce((acc, article) => {
            const date = article.updateTime.split('T')[0]
            if (!acc[date]) acc[date] = 0
            acc[date]++
            return acc
        }, {})
    
    const sortedDates = Object.keys(articlesByDate).sort((a, b) => new Date(a) - new Date(b))

    const lineChart = echarts.init(lineChartRef.value)
    lineChart.setOption({
        title: {
            text: '正式文章每日发布数量',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            data: sortedDates, // 日期作为X轴数据
            name: '日期' // X轴名称
        },
        yAxis: {
            type: 'value',
            name: '发表数量', // Y轴名称
            minInterval: 1 
        },
        series: [{
            data: sortedDates.map(date => articlesByDate[date]),
            type: 'line',
            smooth: true,
            areaStyle: {}
        }]
    });
}


</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章统计</span>
            </div>
        </template>
        <el-row :gutter="20" class="stats">
            <el-col :span="8" class="stat-item">
                <div>全部文章个数</div>
                <div class="stat-value">{{ totalArticles }}</div>
            </el-col>
            <el-col :span="8" class="stat-item">
                <div>正式文章数</div>
                <div class="stat-value">{{ publishedArticles }}</div>
            </el-col>
            <el-col :span="8" class="stat-item">
                <div>草稿</div>
                <div class="stat-value">{{ draftArticles }}</div>
            </el-col>
            <el-col :span="8" class="stat-item">
                <div>点赞数</div>
                <div class="stat-value">{{ Likes }}+</div>
            </el-col>
            <el-col :span="8" class="stat-item">
                <div>收藏数</div>
                <div class="stat-value">{{ Favorites }}+</div>
            </el-col>
            <el-col :span="8" class="stat-item">
                <div>文章总收益</div>
                <div class="stat-value gold-value">{{ Revenue }}</div>
            </el-col>
        </el-row>
        <el-row :gutter="20" class="chart-row">
            <el-col :span="12">
                <div ref="pieChartRef" class="chart"></div>
            </el-col>
            <el-col :span="12">
                <div ref="lineChartRef" class="chart"></div>
            </el-col>
        </el-row>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .stats {
        display: flex;
        justify-content: space-between;
        text-align: center;
        margin-top: 5px;
        padding: 10px; /* 增加内边距以便更好地显示阴影效果 */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4); /* 添加阴影 */
        border-radius: 8px; /* 添加圆角 */
        background-color: #fff; /* 背景色设置为白色 */
    }

    .stat-item {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .stat-value {
        font-size: 24px;
        font-weight: bold;
        margin-top: 10px;
        margin-bottom: 20px;
    }

    .gold-value {
        color: rgb(224, 193, 14); /* 将文本颜色设置为金色 */
    }

    .chart-row {
        margin-top: 20px; /* 增加上下间距 */
    }

    .chart {
        width: 100%;
        height: 400px; /* 调整图表高度 */
    }
}
</style>
