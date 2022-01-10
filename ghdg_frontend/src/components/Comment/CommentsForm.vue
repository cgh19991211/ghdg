<template>
  <article class="media">
    <div class="media-content">
      <form @submit.prevent="onSubmit">
        <b-field>
          <b-input
            v-model.lazy="commentText"
            type="textarea"
            maxlength="400"
            placeholder="Add a comment..."
            :disabled="isLoading"
          ></b-input>
        </b-field>
        <nav class="level">
          <div class="level-left">
            <b-button
              type="is-primary"
              native-type="submit"
              class="level-item"
              :disabled="isLoading"
            >
              Comment
            </b-button>
          </div>
        </nav>
      </form>
    </div>
  </article>
</template>

<script>
import { mapGetters } from 'vuex'
import { pushComment } from '@/api/comment'

export default {
  name: 'LvCommentsForm',
  data() {
    return {
      commentText: '',
      isLoading: false
    }
  },
  props: {
    slug: {
      type: String,
      default: null
    }
  },
  computed: {
    ...mapGetters([
      'AccessToken'
    ])
  },
  methods: {
    async onSubmit() {
		if(this.AccessToken==null){
			this.$buefy.toast.open({
			  message: `login first when you want to comment`,
			  type: 'is-warning'
			})
			return; 
		}
      this.isLoading = true
      try {
        let postData = {}
        console.log(this.commentText)
        postData['content'] = this.commentText
        postData['topic_id'] = this.slug
        await pushComment(postData)
        this.$emit('loadComments', this.slug)
        this.$message.success('留言成功')
      } catch (e) {
        this.$buefy.toast.open({
          message: `Cannot comment this story. ${e}`,
          type: 'is-danger'
        })
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>
