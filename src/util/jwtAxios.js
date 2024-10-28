import axios from "axios";
import {getCookie, setCookie} from "./cookieUtil";
import {API_SERVER_HOST} from "../api/todoApi";

const jwtAxios = axios.create()

const refreshJWT =  async (accessToken, refreshToken) => {

	const host = API_SERVER_HOST

	const header = {headers: {"Authorization":`Bearer ${accessToken}`}}

	const res = await axios.get(`${host}/api/member/refresh?refreshToken=${refreshToken}`, header)

	console.log("----------------------")
	console.log(res.data)

	return res.data
}


//before request
const beforeReq = (config) => {
	console.log("before request")

	const memberInfo = getCookie('member')

	if(!memberInfo) {
		console.log("MEMBER NOT FOUND")

		return Promise.reject({
			response : {
				data : {
					error : "REQUIRE_LOGIN"
				}
			}
		})
	}

	const {accessToken} = memberInfo;

	console.log("accessToken")
	console.log(accessToken)

	console.log("config")
	console.log(config)

	// Authorization 헤더 처리
	config.headers.Authorization = `Bearer ${accessToken}`

	return config;
}

//fail request
const requestFail = (err) => {

	console.log("request error")
	return Promise.reject(err)
}

// before return response
const beforeRes = async (res) => {
	console.log("before return response")

	const data = res.data;

	console.log("data")
	console.log(data)

	console.log("콘피그")
	console.log(res.config)


	if(data && data.error === 'ERROR_ACCESS_TOKEN'){

		const memberCookieValue = getCookie("member")

		const result = await refreshJWT(memberCookieValue.accessToken, memberCookieValue.refreshToken)

		console.log("새로운 쿠키값")
		console.log(result)

		memberCookieValue.accessToken = result.accessToken
		memberCookieValue.refreshToken = result.refreshToken

		setCookie("member",JSON.stringify(memberCookieValue) , 1)


		// 기존 URL 호출
		const originalRequest = res.config // 응답값?

		console.log("originalRequest")
		console.log(originalRequest)

		// 갱신된 accessToken을 삽입
		originalRequest.headers.Authorization = `Bearer ${memberCookieValue.accessToken}`

		return await axios(originalRequest)
	}

	return res;
}

// fila response
const responseFail = (err) => {
	console.log("response error")
	return Promise.reject(err)
}

jwtAxios.interceptors.request.use(beforeReq, requestFail)
jwtAxios.interceptors.response.use(beforeRes, responseFail)

export default jwtAxios