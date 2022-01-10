import Cookies from 'js-cookie'

const BloggerAccessToken = 'BloggerAccessToken'
const BloggerRefreshToken = 'BloggerRefreshToken'
const darkMode = 'dark_mode';



export function getAccessToken() {
    return Cookies.get(BloggerAccessToken);
}


export function setAccessToken(token) {
	console.log("set cookie -- AccessToken")
    return Cookies.set(BloggerAccessToken, token, {expires: 1})
}

export function getRefreshToken() {
    return Cookies.get(BloggerRefreshToken);
}


export function setRefreshToken(token) {
    return Cookies.set(BloggerRefreshToken, token, {expires: 2})
}

export function removeAccessToken() {
    return Cookies.remove(BloggerAccessToken)
}

export function removeRefreshToken() {
    return Cookies.remove(BloggerRefreshToken)
}

export function removeAll() {
    removeAccessToken() 
	removeRefreshToken()
}

export function setDarkMode(mode) {
    return Cookies.set(darkMode, mode, {expires: 365})
}

export function getDarkMode() {
    return !(undefined === Cookies.get(darkMode) || 'false' === Cookies.get(darkMode));
}
