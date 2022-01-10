<template>
  <section class="box comments">
    <hr />
    <h3 class="title is-5">Comments</h3>

    <lv-comments-item
      v-for="comment in comments"
      :key="`comment-${comment.id}`"
      :comment="comment"
    />
	
	<!-- <lv-comments-form :slug="slug" v-if="token" @loadComments="fetchComments"/> -->
	<lv-comments-form :slug="slug" @loadComments="fetchComments"/>
	
  </section>
</template>

<script>
import { fetchCommentsByTopicId } from '@/api/comment'
import LvCommentsForm from './CommentsForm'
import LvCommentsItem from './CommentsItem'

export default {
  name: 'LvComments',
  components: {
    LvCommentsForm,
    LvCommentsItem
  },
  data() {
    return {
      comments: []
    }
  },
  props: {
    slug: {
      type: String,
      default: null
    }
  },
  async mounted() {
    await this.fetchComments(this.slug)
  },
  methods: {
    // 初始化
    async fetchComments(topic_id) {
      console.log(topic_id)
      fetchCommentsByTopicId(topic_id).then(response => {
		console.log("comments")
		console.log(response)
        this.comments = response.data
      })
    }
  }
}
</script>
