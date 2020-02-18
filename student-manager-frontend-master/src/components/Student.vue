<template>
<div>
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">学生管理</h1>
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
    
    <el-table
            :data="table.data"
            style="min-width: 600px; width: 100%"
            @selection-change="handleSelectionChange"
            v-loading="table.loading"
            >
        <el-table-column
                type="selection"
                min-width="2%"
                align="center">
        </el-table-column>
        <el-table-column
                type="index"
                min-width="2%"
                align="center">
            <template slot-scope="scope">
                <span>{{(table.currentPage - 1) * $appConfig.pageSize + scope.$index + 1}}</span>
            </template>
        </el-table-column>
        <el-table-column
                prop="studentNo"
                label="学号"
                min-width="14%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="name"
                label="姓名"
                min-width="10%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="gender"
                label="性别"
                min-width="10%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="studentClass.name"
                label="班级"
                min-width="10%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="dob"
                label="出生日期"
                min-width="12%"
                align="center">
        </el-table-column>
        <el-table-column
                prop="telephone"
                label="电话"
                min-width="15%"
                align="center">
        </el-table-column>
        <el-table-column

                label="邮箱"
                min-width="20%"
                align="center">
            <template slot-scope="scope">
                <a class="text-truncate" :href="mailto(scope.row.email)">{{ scope.row.email }}</a>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="160px" align="center">
            <template slot-scope="scope">
                <el-button
                        size="mini"
                        @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                        size="mini"
                        type="danger"
                        @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-row type="flex" justify="start" style="margin: 5px 2px;">
        <el-col :span="12" style="text-align: left;">
            <el-pagination
                    background
                    layout="prev, pager, next"
                    :page-count="table.totalPages"
                    @current-change="goToPage">
            </el-pagination>
            <el-col :span="12"></el-col>
        </el-col>
    </el-row>
    
    <el-dialog :title="dialog.editMode ? '修改学生信息' : '增加新学生'" 
        :visible.sync="dialog.visible" :modal="true" width="650px" 
        @open="handleDialogOpen" @close="resetForm('studentInfoForm')">
        <el-form ref="studentInfoForm" :model="form.params" :rules="form.rules" label-position="right" label-width="85px">
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.params.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-radio v-model="form.params.gender" label="男">男</el-radio>
                <el-radio v-model="form.params.gender" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="出生日期" prop="dob">
                <el-date-picker
                        v-model="form.params.dob"
                        type="date"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="学号" prop="studentNo">
                <el-input v-model="form.params.studentNo" autocomplete="off" :readonly="dialog.editMode"></el-input>
            </el-form-item>
            <el-form-item label="班级" prop="studentClassId">
                <el-select v-model="form.params.studentClassId" placeholder="请选择班级">
                    <el-option
                            v-for="studentClass in form.studentClasses"
                            :key="studentClass.id"
                            :label="studentClass.name"
                            :value="studentClass.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="电话" prop="telephone">
                <el-input v-model="form.params.telephone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.params.email" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialog.visible = false;">取消</el-button>
            <el-button type="primary" @click="saveStudentInfo" :loading="dialog.processing">{{ dialog.processing ? '处理中' : '确定' }}</el-button>
        </div>
    </el-dialog>    
</div>
</template>

<script>    
import moment from 'moment';

export default {
    name: 'Student',
    data() {
        return {
            table: {
                data: [],
                loading: true,
                totalPages: 0,
                searchKey: '',
                currentPage: 1
            },
            dialog: {
                visible: false,
                editMode: false,
                processing: false
            },
            form: {                   
                studentClasses: [],
                params: {
                    name: '',
                    studentNo: '',
                    gender: '',
                    email: '',
                    telephone: '',
                    studentClassId: null,
                    dob: '',
                },
                rules: {
                    name: [
                        { required: true, message: '请输入学生姓名', trigger: 'blur' }
                    ],
                    studentNo: [
                        { required: true, message: '请输入学号', trigger: 'blur' },
                        { min: 10, max: 12, message: '学号长度在 10 到 12 个字符', trigger: 'blur' },
                        { validator: this.studentNoExists, trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    dob: [  // transform()用于在验证之前对要验证的值进行转换
                        { type: 'date', required: true, transform(value) { return new Date(value); }, message: '请选择出生日期', trigger: 'blur' }
                    ],
                    studentClassId: [
                        { required: true, message: '请选择班级', trigger: 'blur' }
                    ],
                    email: [
                        { type: 'string', required: true, 
                        pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/i, 
                        message: '请输入邮箱', trigger: 'blur' }
                    ]
                }
            } 
        }
    },
    methods: {
        goToPage: function(pageNum) {
            this.table.loading = true;
            this.table.currentPage = parseInt(pageNum);
            this.getTableData(this.table.currentPage - 1);
            
        },
        getTableData: function(pageNum) {
            let url = this.$appConfig.apiBaseUrl;
            if(this.table.searchKey == '') {
                url = url + '/students/list?page=' + pageNum + "&pageSize=" + this.$appConfig.pageSize;
            } else {
                url = url + '/students/search?key=' + encodeURI(this.table.searchKey) + "&page=" + pageNum + "&pageSize=" + this.$appConfig.pageSize;
            }
            this.$axios.get(url)
                .then( (response)  => {
                    console.log(response);
                    this.table.data = response.data.content.map(student => {
                        student.dob = moment(student.dob).format('YYYY-MM-DD');
                        student.studentClassId = student.studentClass.id;
                        //student.email = `<a class="text-truncate" href="mailto:${student.email}">${student.email}</a>`;
                        return student;
                    });
                    this.table.totalPages = response.data.totalPages;
                })
                .catch( (error) => {
                    console.log(error);
                    this.$message({
                        type: "error",
                        dangerouslyUseHTMLString: true,
                        message: "获取学生信息失败。<br><span style='font-size: xx-small;'>" + error + "</span>",
                        duration: 0,
                        showClose: true
                    });
                    
                })
                .finally( () => this.table.loading = false );
        },
        handleDialogOpen: function() {
            if(this.form.studentClasses.length > 0) {
                return;
            }
            this.$axios.get(this.$appConfig.apiBaseUrl + '/class/list')
                .then( (response)  => {
                    this.form.studentClasses = response.data; 
                } )
                .catch( (error) => {
                    console.log(error);
                    this.$message({
                        type: "error",
                        dangerouslyUseHTMLString: true,
                        message: "获取班级信息失败。<br><span style='font-size: xx-small;'>" + error + "</span>",
                        duration: 0,
                        showClose: true
                    });
                } );
        },
        openDialogueForAdd: function() {
            this.dialog.editMode = false;
            this.dialog.visible = true;
        },
        handleSelectionChange: function() {
            
        },
        handleDelete: function(index, row) {
            this.$confirm(`确定的删除学生${row.name}(${row.studentNo})吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
            .then(() => this.$axios.delete(this.$appConfig.apiBaseUrl + '/students/delete?studentNo=' + row.studentNo)
                .then(() => {
                    this.goToPage(this.table.currentPage);  // 刷新当前页
                    this.$message({
                        type: 'success',
                        message: '删除成功!',
                        duration: 5000,
                        showClose: true
                    });
                })
                .catch((error) => {
                    console.log(error);
                    this.$message({
                        type: "error",
                        dangerouslyUseHTMLString: true,
                        message: "删除学生信息失败。<br><span style='font-size: xx-small;'>" + error + "</span>",
                        duration: 0,
                        showClose: true
                    });
                }))
            .catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        saveStudentInfo: function() {
            this.dialog.processing = true;
            let apiUrl = this.$appConfig.apiBaseUrl + (this.dialog.editMode ? '/students/update' : '/students/new');
            this.$refs['studentInfoForm'].validate((valid) => {
                if (valid) {
                    this.$axios.post(apiUrl, this.form.params)
                        .then( () => {                                
                            this.$message({
                                type: "success",
                                dangerouslyUseHTMLString: true,
                                message: "学生信息已保存。",
                                duration: 5000,
                                showClose: true
                            });
                            this.goToPage(this.table.currentPage);  // 刷新当前页
                        })
                        .catch( (error) => {
                            console.log(error);
                            this.$message({
                                type: "error",
                                dangerouslyUseHTMLString: true,
                                message: "保存学生信息失败。<br><span style='font-size: xx-small;'>" + error + "</span>",
                                duration: 0,
                                showClose: true
                            });
                        })
                        .finally( () => {
                            this.dialog.processing = false;
                            this.dialog.visible = false;
                        } );
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm: function() {
            this.$refs['studentInfoForm'].resetFields();
            this.$refs['studentInfoForm'].clearValidate();
        },
        studentNoExists: function (rule, value, callback) {
            // 修改学生信息时不验证学号是否存在
            if(this.dialog.editMode) {
                return callback();
            }
            if (!value) {
                return callback(new Error('学号不能为空'));
            }                
            this.$axios.get(this.$appConfig.apiBaseUrl + "/users/username-exists?username=" + value)
                .then((response) => {
                    if(response.data) {
                        return callback(new Error(`学号"${value}"已经存在。`));                                
                    } else {
                        return callback();
                    }
                })
                .catch((error) => console.log("后端验证学号是否存在失败。" + error));                    
            
        },
        handleEdit: function(index) {
            this.dialog.editMode = true;            
            this.dialog.visible = true;
            /**
             * 使用this.$nextTick()将操作推迟到DOM渲染完成后，此时Dialogue中的Form已渲染完成，且使用
             * this.form.params中的值初始化表单中控件。如果直接执行this.form.params = ...，且
             * Dialogue第一次打开为“编辑”操作，会使表单中控件的初始值设为这次“编辑”学生的信息，从而使
             * this.$refs['newStudentInfoForm'].resetFields()后变为这次“编辑”学生的信息。
             * 
             * 此处Object.assign()创建对象的拷贝，直接this.form.params = this.tableData[index]
             * 会使修改this.form.params后this.tableData也相应修改，从而影响表格中的数据。
             */
            this.$nextTick( () => this.form.params = Object.assign({}, this.table.data[index]) );
            console.log(this.form.params);
            console.log(this.table.data[index] );

        },
        search: function() {
            this.goToPage(1);
        },
        mailto: function(email) {
            return `mailto:${email}`;
        }            
    },
    mounted: function() {
        this.goToPage(1);
    }
}
</script>