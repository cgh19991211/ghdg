<template>
	<div class="columns">
		<!--文章详情-->
		<div class="column is-three-quarters">
			<!--主题-->
			<el-card class="box-card" shadow="never">
				<div slot="header" class="has-text-centered">
					<p class="is-size-5 has-text-weight-bold">{{ topic.title }}</p>
					<div class="has-text-grey is-size-7 mt-3">
						<span>{{ dayjs(topic.createdDate).format('YYYY/MM/DD HH:mm:ss') }}</span>
						<el-divider direction="vertical" />
						<span>发布者：{{ blogger.name }}</span>
						<el-divider direction="vertical" />
						<span>阅读：{{ topic.viewNums }}</span>
					</div>
				</div>

				<!--Markdown-->
				<div id="preview" />

				<!--标签-->
				<nav class="level has-text-grey is-size-7 mt-6">
					<div class="level-left">
						<p class="level-item">
							<b-taglist>
								<router-link v-for="(tag, index) in tags" :key="index"
									:to="{ name: 'tag', params: { name: tag.name } }">
									<b-tag type="is-info is-light mr-1">
										{{ "#" + tag.name }}
									</b-tag>
								</router-link>
							</b-taglist>
						</p>
					</div>
					<div v-if="(BloggerAccessToken && user.id === blogger.id)" class="level-right">
						<router-link class="level-item" :to="{name:'topic-edit',params: {id:topic.id}}">
							<i class="fa fa-edit"></i>
							<p>&nbsp;编辑</p>
						</router-link>
						&nbsp;&nbsp;
						<a class="level-item" @click="handleDelete(topic.id)">
							<i class="fa fa-trash"></i>
							<p>&nbsp;删除</p>
						</a>
					</div>
				</nav>
				<nav class="level has-text-grey is-size-7 mt-6">
					<!-- 点赞、收藏、分享等按钮 -->
					<div class="level-item">
						<vue-star animate="animated bounceIn" color="#F05654" :num=topic.likeNums>
							<i slot="icon" class="fa fa-thumbs-up fa-2x"></i>
						</vue-star>
					</div>
					<div class="level-item">
						<vue-star animate="animated bounceIn" color="#F05654">
							<i slot="icon" class="fa fa-bookmark fa-2x"></i>
						</vue-star>
					</div>
					<div class="level-item">
						<vue-star animate="animated bounceIn" color="#F05654">
							<i slot="icon" class="fa fa-share-square fa-2x"></i>
						</vue-star>
					</div>
				</nav>

			</el-card>

			<lv-comments :slug="topic.id" />
		</div>

		<div class="column">
			<!--作者-->
			<Author v-if="flag" :user="blogger" />
			<!--推荐-->
			<recommend v-if="flag" :bloggerId="blogger.id" :curBlogId="topic.id" />
		</div>
	</div>
</template>

<script>
	import {
		deleteTopic,
		getTopic
	} from '@/api/post'
	import {
		mapGetters
	} from 'vuex'
	import Author from '@/views/post/Author'
	import Recommend from '@/views/post/Recommend'
	import LvComments from '@/components/Comment/Comments'
	import Vditor from 'vditor'
	import 'vditor/dist/index.css'
	import VueStar from '@/components/Star/component/VueStar'

	export default {
		name: 'TopicDetail',
		components: {
			Author,
			Recommend,
			LvComments,
			VueStar
		},
		computed: {
			...mapGetters([
				'BloggerAccessToken', 'user'
			])
		},
		data() {
			return {
				flag: false,
				topic: {
					content: '',
					id: this.$route.params.id
				},
				tags: [],
				blogger: {
					name: '',
					id: '',
				}
			}
		},
		mounted() {
			this.fetchTopic()
			// console.log(this.blogger)
		},
		methods: {
			renderMarkdown(md) {
				Vditor.preview(document.getElementById('preview'), md, {
					hljs: {
						style: 'github'
					}
				})
			},
			// 初始化
			async fetchTopic() {
				getTopic(this.$route.params.id).then(response => {
					let data = response.data
					// document.title = data.title
					console.log(data);

					this.topic = data
					this.tags = data.labels
					this.blogger.name = data.bloggerName
					this.blogger.id = data.bloggerId
					// this.comments = data.comments
					this.renderMarkdown(this.topic.content)
					this.flag = true
				})
			},
			handleDelete(id) {
				deleteTopic(id).then(value => {
					const {
						code,
						message
					} = value
					alert(message)

					if (code === 200) {
						setTimeout(() => {
							this.$router.push({
								path: '/'
							})
						}, 500)
					}
				})
			}
		}
	}
</script>

<style>
	#preview {
		min-height: 300px;
	}
</style>
