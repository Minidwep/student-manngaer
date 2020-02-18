<template>
    <div>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">专业管理</h1>
            <div class="btn-toolbar mb-2 mb-md-0">
                <!-- <div class="btn-group mr-2">
                    <el-input v-model="table.searchKey" placeholder="学号/姓名">
                        <el-button slot="append" icon="el-icon-search" 
                        title="搜索学生" @click="search"></el-button>
                    </el-input>
                </div> -->
                <!-- <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                <span data-feather="calendar"></span>
                This week
                </button> -->
                <!-- <el-button type="primary" 
                    icon="el-icon-circle-plus" 
                    @click="openDialogueForAdd"
                    title="添加新专业">增加</el-button> -->
            </div>
        </div>

        <table class="table table-bordered">
        <thead>
            <tr>
            <th scope="col">#</th>
            <th scope="col">名称</th>
            <th scope="col">状态</th>
            <th scope="col">专业负责人</th>
            <th scope="col">操作</th>
            </tr>
        </thead>
        <tbody v-show="loading">
            <tr>
                <td colspan="5" style="text-align: center; padding: 20px;" v-bind:class="{ 'text-danger': loadingMessage === '' }">{{ loadingMessage }}</td>
            </tr>
        </tbody>
        <tbody>
            <tr v-for="(subject, index) in subjects"  :key='subject'>                
                <th scope="row">{{ index + 1 }}</th>
                <td>{{ subject.name }}</td>
                <td>{{ subject.status }}</td>
                <td>{{ subject.director.name }}</td>
                <td></td>
            </tr>            
        </tbody>
        </table>
         <!-- <el-table
            :data="table.data"
            style="min-width: 600px; width: 100%"
            @selection-change="handleSelectionChange"
            v-loading="table.loading"
            >
        <el-table-column
                type="selection"
                min-width="3%"
                align="center">
        </el-table-column>
        <el-table-column
                type="index"
                min-width="5%"
                align="center">
            <template slot-scope="scope">
                <span>{{(table.currentPage - 1) * $appConfig.pageSize + scope.$index + 1}}</span>
            </template>
        </el-table-column>
        <el-table-column
                prop="name"
                label="名称"
                min-width="12%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="status"
                label="状态"
                min-width="10%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="director.name"
                label="专业负责人"
                min-width="10%"
                align="center">
        </el-table-column>
        
        <el-table-column label="操作" width="160px" align="center">
            <template slot-scope="scope">
                <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table> -->
    </div>
</template>

<script>
export default {
    name: 'Subject',
    data() {
        return {
            subjects: [],
            loading: true,
            loadingMessage: ''
        }
    },
    methods: {
        getSubjects: function() {
            this.loadingMessage = true;
            this.loadingMessage = '加载中';
            let url = this.$appConfig.apiBaseUrl + "/subjects/";
            this.$axios.get(url)
                .then( (response) => {
                    this.subjects = response.data;
                    this.loading = false;
                })
                .catch( (error) => {
                    console.log(error);
                    this.loading = true;
                    this.loadingMessage = '获取专业数据错误'  + '<br><span style="font-size: xx-small;">' + error + '</span>';
                })
                .finally();
        }
    },
    mounted: function() {
        this.getSubjects();
    }
}
</script>
