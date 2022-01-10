const getters = {
  BloggerAccessToken: state => state.user.BloggerAccessToken,
  BloggerRefreshToken: state => state.user.BloggerRefreshToken,
  user: state => state.user.curBlogger,     // 用户对象
  curBlogger: state => state.user.curBlogger
}
export default getters