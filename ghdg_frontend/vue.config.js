/* 覆盖webpack的配置 */
module.exports = {
    devServer: { // 自定义服务配置
        open: true, // 自动打开浏览器
        port: 8081 // 端口号
    },
    lintOnSave: false //eslint-loader 是否在保存的时候检查
}
