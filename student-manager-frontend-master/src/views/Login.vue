<template>
  <div class="text-center">
    <header class="mt-5 mb-3">
      <h1 class="h5">师生基本信息管理系统</h1>
    </header>
    <main>
      <h3 class="h3 mb-3 font-weight-normal">用户登录</h3>
      <form v-on:submit.prevent="onSubmit" class="form-signin mx-auto">
        <input
          type="text"
          placeholder="工号/学号/邮箱"
          v-model="username"
          class="form-control"
          @blur="inputBlur"
          :class="{'is-invalid': invalidUsername}"
        />
        <input
          type="password"
          placeholder="密码"
          v-model="password"
          class="form-control"
          @blur="inputBlur"
          :class="{'is-invalid': invalidPassword}"
        />
        <button class="btn btn-primary btn-lg btn-block">登录</button>
      </form>
    </main>
    <footer>
      <li class="nav-item">
        <router-link to="/ResetPwd" class="nav-link">
          <span data-feather="file"></span>教工管理
        </router-link>&copy; 山东建筑大学 计算机科学与技术学院
      </li>
    </footer>
  </div>
</template>


<script>
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
//import feather from 'feather-icons';

export default {
  name: "login",
  data() {
    return {
      username: "",
      password: "",
      invalidUsername: false,
      invalidPassword: false
    };
  },
  methods: {
    onSubmit: function(e) {
      if (!this.checkForm()) {
        e.preventDefault();
        return;
      }
      let url = this.$appConfig.apiBaseUrl + "/authenticate";
      this.$axios
        .post(url, {
          username: this.username,
          password: this.password
        })
        .then(this.loginSuccess)
        .catch(this.loginError);
    },
    loginSuccess: function(response) {
      this.authenticationNotPass = true;
      let token = response.data.token;
      this.$store.commit("setToken", token);
      console.log(this.$store.state.token);
      sessionStorage.setItem("token", token);
      this.$axios.defaults.headers.common["Authorization"] =
        "Bearer " + this.$store.state.token;
      this.$router.push(
        this.$route.query.redirectFrom || { name: "managementHome" }
      );
    },
    loginError: function(error) {
      console.log(error.response.status);
      let errorMessage = "";
      switch (error.response.status) {
        case 401:
          // this.invalidUsername = true;
          // this.invalidPassword = true;
          errorMessage = "用户名或密码错误";
          break;
        default:
          errorMessage = "登录错误";
      }
      this.$message({
        type: "error",
        message: errorMessage,
        duration: 5000,
        showClose: true
      });
    },
    inputBlur: function(e) {
      if (e.target.type == "text") {
        if (this.username.trim() == "") {
          this.invalidUsername = true;
          this.$message({
            type: "error",
            message: "请输入用户名",
            duration: 5000,
            showClose: true,
            offset: 30
          });
        } else {
          this.invalidUsername = false;
        }
      } else if (e.target.type == "password") {
        if (this.password == "") {
          this.invalidPassword = true;
          this.$message({
            type: "error",
            message: "请输入密码",
            duration: 5000,
            showClose: true,
            offset: 30
          });
        } else {
          this.invalidPassword = false;
        }
      }
    },
    checkForm: function() {
      let errors = [];
      if (this.username.trim() == "") {
        this.invalidUsername = true;
        errors.push("请输入用户名");
      } else {
        this.invalidUsername = false;
      }
      if (this.password == "") {
        this.invalidPassword = true;
        errors.push("请输入密码");
      } else {
        this.invalidPassword = false;
      }
      if (errors.length > 0) {
        if (document.getElementsByClassName("el-message").length > 0)
          this.$message.closeAll();
        this.$message({
          type: "error",
          message: errors.join("<br>"),
          dangerouslyUseHTMLString: true,
          duration: 5000,
          showClose: true,
          offset: 30
        });
        return false;
      }
      return true;
    }
  },
  mounted: function() {
    //feather.replace();
  }
};
</script>

<style scoped>
html,
body {
  height: 100%;
}

body {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: center;
  align-items: center;
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #f5f5f5;
}

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: auto;
}
.form-signin .checkbox {
  font-weight: 400;
}
.form-signin .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="text"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
