<template>
    <div>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
            <h1 class="h2">班级管理</h1>
            <div class="btn-toolbar mb-2 mb-md-0">
                <div class="btn-group mr-2">
                    <el-input v-model="table.searchKey" placeholder="学号/姓名">
                        <el-button slot="append" icon="el-icon-search" 
                        title="搜索学生" @click="search"></el-button>
                    </el-input>
                </div>
                <!-- <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                <span data-feather="calendar"></span>
                This week
                </button> -->
                <el-button type="primary" 
                    icon="el-icon-circle-plus" 
                    @click="openDialogueForAdd"
                    title="添加新学生信息">增加</el-button>
            </div>
        </div>
    </div>
</template>
