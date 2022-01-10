<template>
	<article class="media">
		<figure class="media-left image is-48x48">
			<img :src="comment.bloggerAvatar">
		</figure>
		<div class="media-content">
			<div class="content">
				<p>
					<strong>{{ comment.bloggerName }}</strong>
					<small class="ml-2">{{ comment.createdDate | date }}</small>
					<!-- 应该做成按钮，点击变色并且执行点赞逻辑 -->
					<span>
						&nbsp;&nbsp;<i class="fa fa-thumbs-o-up">&nbsp;{{comment.likeNum}}</i>
					</span>
					<br />
					{{ comment.content }}
				</p>
			</div>
			<div class="replies" v-if="comment.responses!=null">
				<reply v-for="(item,index) in comment.responses" :response="item" :byReplyId="comment.bloggerId"
					:byReplyName="comment.bloggerName" :key='item'></reply>
			</div>
		</div>
	</article>
</template>
<script>
	import {
		reply
	} from "@/components/Comment/Reply"
	export default {
		name: 'LvCommentsItem',
		props: {
			comment: {
				type: Object,
				required: true
			}
		}
	}
</script>
