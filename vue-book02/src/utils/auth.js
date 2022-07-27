import Cookie from 'js-cookie'

const TokenKey = 'token'

export function getToken () {
  return Cookie.get(TokenKey)
}

// 设置token
export function setToken (token, expireAt = 1) {
  return Cookie.set(TokenKey, 'Bearer ' + token, { expires: expireAt })
}

// 移除token
export function removeToken () {
  return Cookie.remove(TokenKey)
}
