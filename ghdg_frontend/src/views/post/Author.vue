<template>
	<section id="author">
		<el-card class="" shadow="never">
			<div slot="header">
				<span class="has-text-weight-bold">👨‍💻 关于作者</span>
			</div>
			<div class="has-text-centered">
				<img :src="blogger.avatar" crossorigin="anonymous" style="border-radius: 5px;">
				<p class="is-size-5 mb-5">
					<router-link :to="{ path: `/member/${user.username}/home` }">
						{{ blogger.nickname }} <span class="is-size-7 has-text-grey">{{ '@' + blogger.bloggerName }}</span>
					</router-link>
				</p>
				<div class="columns is-mobile">
					<div class="column is-half">
						<code>{{ blogger.blogNums }}</code>
						<p>文章</p>
					</div>
					<div class="column is-half">
						<code>{{ blogger.fanNums }}</code>
						<p>粉丝</p>
					</div>
				</div>
				<div>
					<button v-if="hasFollow" class="button is-success button-center is-fullwidth"
						@click="handleUnFollow(blogger.bloggerId)">
						已关注
					</button>

					<button v-else class="button is-link button-center is-fullwidth" @click="handleFollow(blogger.bloggerId)">
						关注
					</button>
				</div>
			</div>
		</el-card>
	</section>
</template>

<script>
	import {
		follow,
		hasFollow,
		unFollow
	} from '@/api/follow'
	import {
		mapGetters
	} from 'vuex'
	import { getBloggerInfo } from '@/api/blogger'
	export default {
		name: 'Author',
		props: {
			user: {
				type: Object,
				default: null
			}
		},
		data() {
			return {
				hasFollow: false,
				blogger: {
					fanNums: 0
				}
			}
		},
		mounted() {
			this.fetchInfo()
		},
		computed: {
			...mapGetters([
				'BloggerAccessToken'
			])
		},
		methods: {
			fetchInfo() {
				/**
				 * 获取作者信息:从list页面传进来的user.id是bloggerId
				 */
				getBloggerInfo(this.user.id).then(response => {
					this.blogger = response.data
					this.blogger.fanNums = this.blogger.fans==null?0:this.blogger.fans.length
					console.log("author")
					console.log(this.user)
				})
				/**
				 * 是否已关注
				 */
				if (this.BloggerAccessToken != null && this.BloggerAccessToken !== '') {
					hasFollow(this.user.id).then(value => {
						const {
							data
						} = value
						this.hasFollow = data.hasFollow
					})
				}
			},
			/**
			 * 关注
			 * @param {Object} id 被关注的博主的id
			 */
			handleFollow: function(id) {
				if (this.BloggerAccessToken != null && this.BloggerAccessToken !== '') {
					follow(id).then(response => {
						const {
							message
						} = response
						this.$message.success(message)
						this.hasFollow = !this.hasFollow
						this.blogger.fanNums = parseInt(this.blogger.fanNums) + 1
					})
				} else {
					this.$message.success('请先登录')
				}
			},
			/**
			 * 取关
			 * @param {Object} id 被取关的博主的id
			 */
			handleUnFollow: function(id) {
				unFollow(id).then(response => {
					const {
						message
					} = response
					this.$message.success(message)
					this.hasFollow = !this.hasFollow
					this.blogger.fanNums = parseInt(this.blogger.fanNums) - 1
				})
			}
		}
	}
</script>

<style scoped>

</style>
