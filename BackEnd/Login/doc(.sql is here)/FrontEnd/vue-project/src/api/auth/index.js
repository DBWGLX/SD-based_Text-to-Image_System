import request from "@/utils/request";
/**
 * 登录API
 *
 * @param data {LoginData}
 * @returns
 */
export function loginApi(data) {
  return request({
    url: "/auth/login",
    method: "POST",
    params: data
  });
}

export function registerApi(data) {
  return request({
    url: "/auth/register",
    method: "POST",
    data
  });
}