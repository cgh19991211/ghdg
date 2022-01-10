<template>
  <el-card class="" shadow="never">
    <div slot="header">
      <span class="has-text-weight-bold">🧐 随便看看</span>
    </div>
    <div>
      <p v-for="(item,index) in recommend" :key="index" :title="item.title" class="block ellipsis is-ellipsis-1">
        <router-link :to="{name:'post-detail',params: { id: item.id }}">
          <span v-if="index<9" class="tag">
            0{{ parseInt(index) + 1 }}
          </span>
          <span v-else class="tag">
            {{ parseInt(index) + 1 }}
          </span>
          {{ item.title }}
        </router-link>
      </p>
    </div>
  </el-card>
</template>

<script>
import { getRecommendTopics } from '@/api/post'

export default {
  name: 'Recommend',
  props: {
    bloggerId: {
      type: String,
      default: null
    },
	curBlogId: {
		type: String,
		default: null
	}
  },
  data() {
    return {
      recommend: []
    }
  },
  created() {
    this.fetchRecommendTopics()
  },
  methods: {
    fetchRecommendTopics() {
      getRecommendTopics(this.bloggerId, this.curBlogId).then(value => {
        const { data } = value
		console.log("recommend")
		console.log(data)
        this.recommend = data.records
      })
    }
  }
}
</script>

<style scoped>

</style>
