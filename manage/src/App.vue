
<!--App.vue 只是作为一个存放组件的容器-->

<template>
  <div id="app">
<!--    其中 <router-view> 是用来渲染通过路由映射过来的组件，当路径更改时，<router-view> 中的内容也会发生更改-->
<!--    既，路由路径变更，router-view会自动渲染所映射的对应的组件-->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
