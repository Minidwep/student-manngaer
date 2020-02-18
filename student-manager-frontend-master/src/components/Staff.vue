<template>
  <div>
    <div
      class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom"
    >
      <h1 class="h2">教工管理</h1>
      <div class="btn-toolbar mb-2 mb-md-0">
        <div class="btn-group mr-2">
          <el-input v-model="table.searchKey" placeholder="学号/姓名">
            <el-button slot="append" icon="el-icon-search" title="搜索教工" @click="search"></el-button>
          </el-input>
        </div>
        <!-- <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
            <span data-feather="calendar"></span>
            This week
        </button>-->
        <el-button
          type="primary"
          icon="el-icon-circle-plus"
          @click="openDialogueForAdd"
          title="添加新教工信息"
        >增加</el-button>
      </div>
    </div>

    <el-table
      :data="table.data"
      style="min-width: 600px; width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="table.loading"
    >
      <el-table-column type="selection" min-width="2%" align="center"></el-table-column>
      <el-table-column type="index" min-width="2%" align="center">
        <template slot-scope="scope">
          <span>{{(table.currentPage - 1) * $appConfig.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="staffNo" label="职工号" min-width="14%" align="center"></el-table-column>
      <el-table-column prop="name" label="姓名" min-width="10%" align="center"></el-table-column>
      <el-table-column prop="telephone" label="电话" min-width="10%" align="center"></el-table-column>
      <el-table-column prop="gender" label="性别" min-width="10%" align="center"></el-table-column>
      <el-table-column label="邮箱" min-width="20%" align="center">
        <template slot-scope="scope">
          <a class="text-truncate" :href="mailto(scope.row.email)">{{ scope.row.email }}</a>
        </template>
      </el-table-column>
      <el-table-column prop="dob" label="出生日期" min-width="12%" align="center"></el-table-column>
      <el-table-column prop="academicRank" label="职位" min-width="15%" align="center"></el-table-column>

      <el-table-column label="操作" width="240px" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="warning" @click="resetPwdBtn(scope.row.email)">忘记</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-row type="flex" justify="start" style="margin: 5px 2px;">
      <el-col :span="12" style="text-align: left;">
        <el-pagination
          background
          layout="prev, pager, next"
          :page-count="table.totalPages"
          @current-change="goToPage"
        ></el-pagination>
        <el-col :span="12"></el-col>
      </el-col>
    </el-row>

    <el-dialog
      :title="dialog.editMode ? '修改教工信息' : '增加新教工'"
      :visible.sync="dialog.visible"
      :modal="true"
      width="650px"
      @open="handleDialogOpen"
      @close="resetForm('staffInfoForm')"
    >
      <el-form
        ref="staffInfoForm"
        :model="form.params"
        :rules="form.rules"
        label-position="right"
        label-width="85px"
      >
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
            placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="职工号" prop="staffNo">
          <el-input v-model="form.params.staffNo" autocomplete="off" :readonly="dialog.editMode"></el-input>
        </el-form-item>

        <el-form-item label="电话" prop="telephone">
          <el-input v-model="form.params.telephone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="职位" prop="academicRank">
          <el-input v-model="form.params.academicRank" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.params.email" autocomplete="off"></el-input>
        </el-form-item>

        <!-- <el-form-item label="电话" prop="telephone">
                <el-input v-model="form.params.telephone" autocomplete="off"></el-input>
            </el-form-item>
        -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog.visible = false;">取消</el-button>
        <el-button
          type="primary"
          @click="saveStaffInfo"
          :loading="dialog.processing"
        >{{ dialog.processing ? '处理中' : '确定' }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "Staff",
  data() {
    return {
      table: {
        data: [],
        loading: true,
        totalPages: 0,
        searchKey: "",
        currentPage: 1
      },
      dialog: {
        visible: false,
        editMode: false,
        processing: false
      },
      form: {
        staffClasses: [],
        params: {
          name: "",
          staffNo: "",
          gender: "",
          email: "",
          telephone: "",
          academicRank: "",
          dob: ""
        },
        rules: {
          name: [
            { required: true, message: "请输入教工姓名", trigger: "blur" }
          ],
          staffNo: [
            { required: true, message: "请输入学号", trigger: "blur" },
            {
              min: 1,
              max: 12,
              message: "学号长度在 10 到 12 个字符",
              trigger: "blur"
            },
            { validator: this.staffNoExists, trigger: "blur" }
          ],
          gender: [
            { required: true, message: "请选择性别", trigger: "change" }
          ],
          dob: [
            // transform()用于在验证之前对要验证的值进行转换
            {
              type: "date",
              required: true,
              transform(value) {
                return new Date(value);
              },
              message: "请选择出生日期",
              trigger: "blur"
            }
          ],
          staffClassId: [
            { required: true, message: "请选择班级", trigger: "blur" }
          ],
          email: [
            {
              type: "string",
              required: true,
              pattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/i,
              message: "请输入邮箱",
              trigger: "blur"
            }
          ]
        }
      }
    };
  },
  methods: {
    goToPage: function(pageNum) {
      this.table.loading = true;
      this.table.currentPage = parseInt(pageNum);
      this.getTableData(this.table.currentPage - 1);
    },
    getTableData: function(pageNum) {
      let url = this.$appConfig.apiBaseUrl;
      if (this.table.searchKey == "") {
        url =
          url +
          "/staff/list?page=" +
          pageNum +
          "&pageSize=" +
          this.$appConfig.pageSize;
      } else {
        url =
          url +
          "/staff/search?key=" +
          encodeURI(this.table.searchKey) +
          "&page=" +
          pageNum +
          "&pageSize=" +
          this.$appConfig.pageSize;
      }
      this.$axios
        .get(url)
        .then(response => {
          console.log(response);
          this.table.data = response.data.content.map(staff => {
            staff.dob = moment(staff.dob).format("YYYY-MM-DD");

            //staff.email = `<a class="text-truncate" href="mailto:${staff.email}">${staff.email}</a>`;
            return staff;
          });
          this.table.totalPages = response.data.totalPages;
        })
        .catch(error => {
          console.log(error);
          this.$message({
            type: "error",
            dangerouslyUseHTMLString: true,
            message:
              "获取教工信息失败。<br><span style='font-size: xx-small;'>" +
              error +
              "</span>",
            duration: 0,
            showClose: true
          });
        })
        .finally(() => (this.table.loading = false));
    },
    handleDialogOpen: function() {
      if (this.form.staffClasses.length > 0) {
        return;
      }
      this.$axios
        .get(this.$appConfig.apiBaseUrl + "/class/list")
        .then(response => {
          this.form.staffClasses = response.data;
        })
        .catch(error => {
          console.log(error);
          this.$message({
            type: "error",
            dangerouslyUseHTMLString: true,
            message:
              "获取班级信息失败。<br><span style='font-size: xx-small;'>" +
              error +
              "</span>",
            duration: 0,
            showClose: true
          });
        });
    },
    openDialogueForAdd: function() {
      this.dialog.editMode = false;
      this.dialog.visible = true;
    },
    handleSelectionChange: function() {},

    resetPwdBtn(email) {
      console.log(email);
       this.$axios
        .get(
             this.$appConfig.apiBaseUrl +
                "/resetPwd?email="+email
        )
        .then(function(response) {
          console.log(response);
        })
        .catch(function(error) {
          console.log(error);
        });

    },
    handleDelete: function(index, row) {
      this.$confirm(`确定的删除教工${row.name}(${row.staffNo})吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() =>
          this.$axios
            .delete(
              this.$appConfig.apiBaseUrl +
                "/staff/delete?staffNo=" +
                row.staffNo
            )
            .then(() => {
              this.goToPage(this.table.currentPage); // 刷新当前页
              this.$message({
                type: "success",
                message: "删除成功!",
                duration: 5000,
                showClose: true
              });
            })
            .catch(error => {
              console.log(error);
              this.$message({
                type: "error",
                dangerouslyUseHTMLString: true,
                message:
                  "删除教工信息失败。<br><span style='font-size: xx-small;'>" +
                  error +
                  "</span>",
                duration: 0,
                showClose: true
              });
            })
        )
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    saveStaffInfo: function() {
      this.dialog.processing = true;
      let apiUrl =
        this.$appConfig.apiBaseUrl +
        (this.dialog.editMode ? "/staff/update" : "/staff/new");
      this.$refs["staffInfoForm"].validate(valid => {
        console.log(this.$refs);
        if (valid) {
          this.$axios
            .post(apiUrl, this.form.params)
            .then(() => {
              this.$message({
                type: "success",
                dangerouslyUseHTMLString: true,
                message: "教工信息已保存。",
                duration: 5000,
                showClose: true
              });
              this.goToPage(this.table.currentPage); // 刷新当前页
            })
            .catch(error => {
              console.log(error);
              this.$message({
                type: "error",
                dangerouslyUseHTMLString: true,
                message:
                  "保存教工信息失败。<br><span style='font-size: xx-small;'>" +
                  error +
                  "</span>",
                duration: 0,
                showClose: true
              });
            })
            .finally(() => {
              this.dialog.processing = false;
              this.dialog.visible = false;
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm: function() {
      this.$refs["staffInfoForm"].resetFields();
      this.$refs["staffInfoForm"].clearValidate();
    },
    staffNoExists: function(rule, value, callback) {
      // 修改教工信息时不验证学号是否存在
      if (this.dialog.editMode) {
        return callback();
      }
      if (!value) {
        return callback(new Error("学号不能为空"));
      }
      this.$axios
        .get(
          this.$appConfig.apiBaseUrl +
            "/users/username-exists?username=" +
            value
        )
        .then(response => {
          if (response.data) {
            return callback(new Error(`学号"${value}"已经存在。`));
          } else {
            return callback();
          }
        })
        .catch(error => console.log("后端验证学号是否存在失败。" + error));
    },
    handleEdit: function(index) {
      this.dialog.editMode = true;
      this.dialog.visible = true;
      /**
       * 使用this.$nextTick()将操作推迟到DOM渲染完成后，此时Dialogue中的Form已渲染完成，且使用
       * this.form.params中的值初始化表单中控件。如果直接执行this.form.params = ...，且
       * Dialogue第一次打开为“编辑”操作，会使表单中控件的初始值设为这次“编辑”教工的信息，从而使
       * this.$refs['newStaffInfoForm'].resetFields()后变为这次“编辑”教工的信息。
       *
       * 此处Object.assign()创建对象的拷贝，直接this.form.params = this.tableData[index]
       * 会使修改this.form.params后this.tableData也相应修改，从而影响表格中的数据。
       */
      this.$nextTick(
        () => (this.form.params = Object.assign({}, this.table.data[index]))
      );
      console.log(this.form.params);
      console.log(this.table.data[index]);
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
};
</script>