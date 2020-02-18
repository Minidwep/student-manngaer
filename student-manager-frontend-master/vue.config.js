module.exports = {
    devServer: {
        port: 3000, // 端口
        proxy: {  // 配置代理，否则无法跨域调用接口
            "/api": {
              target: 'http://localhost:8080',
              changeOrigin: true,
              pathRewrite: {
                '^/api': 'http://localhost:8080'//调用接口直接写‘/api/user/add’即可
              }
            }
        }
    },
    // lintOnSave: false // 取消 eslint 验证
}