import {Cookies} from "react-cookie";


const cookies = new Cookies();



export const setCookie = (name, value, days) => {

	const expires = new Date()
	expires.setUTCDate(expires.getUTCDate() + days) // 보관기간

	return cookies.set(name, value, {path:'/', expires})

}

export const getCookie = (name) => {

	return cookies.get(name)

}

export const removeCookie = (name , path = "/") => {

	return cookies.remove(name, {path});

}