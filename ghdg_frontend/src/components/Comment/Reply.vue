<template>
	<article class="reply">
		<figure class="reply-left image is-48x48">
			<img :src="response.bloggerAvatar">
		</figure>
		<div class="reply-content">
			<div class="content">
				<p>
					<strong>{{ response.bloggerName }}: @ {byReplyName} </strong>
					<small class="ml-2">{{ response.createdDate | date }}</small>
					<br />
					{{ response.content }}
				</p>
			</div>
		</div>
	</article>
</template>

<script>
	export default {
		name: 'reply',
		props: {
			response: {
				type: Object,
				default: null
			},
			byReplyId: {
				type: String,
				default: ''
			},
			byReplyName: {
				type: String,
				default: ''
			}
		},
	}
</script>

<style>
</style>
