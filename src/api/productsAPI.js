import {API_SERVER_HOST} from "./todoApi";
import axios from "axios";
import jwtAxios from "../util/jwtAxios";
const host = `${API_SERVER_HOST}/api/products`

export const postAdd = async (product) => {


	// 파일이 포함되어 form-data 형식으로 헤더를 변경해야 함
	const header = {headers : {"Content-Type" : "multipart/form-data"}}

	const res = await axios.post(`${host}/`,product, header);

	return res.data;

}

export const getList = async (pageParam) => {

	const {page, size} = pageParam;

	const res = await jwtAxios.get(`${host}/list`, {params : {page : page, size : size}});

	return res.data;

}

export const getOne = async (pno) => {

	const res = await axios.get(`${host}/${pno}`)

	return res.data;

}

export const putOne = async (pno, product) => {

	const header = {headers : {'Content-Type' : 'multipart/form-data'}}

	const res = await axios.put(`${host}/${pno}`, product, header)

	return res.data;

}


export const deleteOne = async (pno) => {

	const res = await axios.delete(`${host}/${pno}`)

	return res.data

}