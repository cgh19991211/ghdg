import {
	getUserInfo,
	login,
	logout
} from "@/api/auth/auth";
import {
	setAccessToken,
	setRefreshToken,
	getAccessToken,
	getRefreshToken,
	removeAll
} from "@/utils/auth";

const state = {
	BloggerAccessToken: getAccessToken(),
	BloggerRefreshToken: getRefreshToken(),
	curBlogger: ""
};

const mutations = {
	SET_REFRESH_TOKEN: (state, RefreshToken) => {
		state.BloggerRefreshToken = RefreshToken;
	},
	SET_ACCESS_TOKEN: (state, AccessToken) => {
		state.BloggerAccessToken = AccessToken;
	},
	SET_CUR_BLOGGER: (state, blogger) => {
		state.curBlogger = blogger
	}
};

const actions = {
	// 用户登录
	login({
		commit
	}, userInfo) {
		const {
			name,
			pass,
			rememberMe
		} = userInfo;
		return new Promise((resolve, reject) => {
			login(name.trim(), pass).then((response) => {
					const data = response.data
					console.log("登陆成功，response：")
					// console.log(data)
					setAccessToken(data.BloggerAccessToken)
					setRefreshToken(data.BloggerRefreshToken)
					commit("SET_ACCESS_TOKEN", data.BloggerAccessToken)
					commit("SET_REFRESH_TOKEN", data.BloggerRefreshToken)
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},
	// 获取用户信息
	getInfo({
		commit,
		state
	}) {
		return new Promise((resolve, reject) => {
			getUserInfo()
				.then((response) => {
					const data = response.data;
					// console.log("获取当前博主信息")
					// console.log(response)
					if (!data) {
						commit("SET_ACCESS_TOKEN", "");
						commit("SET_REFRESH_TOKEN", "");
						commit("SET_CUR_BLOGGER", "");
						removeAll();
						resolve();
						reject("Verification failed, please Login again.");
					}
					commit("SET_CUR_BLOGGER", data);
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},
	// 注销
	logout({
		commit,
		state
	}) {
		return new Promise((resolve, reject) => {
			logout(state.token)
				.then((response) => {
					console.log(response);
					commit("SET_TOKEN_STATE", "");
					commit("SET_ACCESS_TOKEN", "");
					commit("SET_REFRESH_TOKEN", "");
					commit("SET_CUR_BLOGGER", "");
					removeAll();
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},
};

export default {
	namespaced: true,
	state,
	mutations,
	actions,
};
