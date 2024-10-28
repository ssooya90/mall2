import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {loginPost} from "../api/memberAPI";
import {getCookie, removeCookie, setCookie} from "../util/cookieUtil";

const initState = {
	email: '',
}

const loadMemberCookie = () => {

	const memberInfo = getCookie('member')

	if(memberInfo && memberInfo.nickname){
		memberInfo.nickname = decodeURIComponent(memberInfo.nickname)
	}

	return memberInfo

}

export const loginPostAsync = createAsyncThunk('loginPostAsync', (param) => {
	return loginPost(param)
})


const loginSlice = createSlice({

	name: 'loginSlice',
	initialState: loadMemberCookie() || initState,
	// reducers: {
	// 	login: (state, action) => {
	// 		console.log("login....")
	//
	// 		//{email, pw로 구성}
	// 		const data = action.payload
	//
	// 		console.log("DATA")
	// 		console.log(data)
	//
	// 		// 새로운 상태
	// 		return {email: data.email}
	//
	// 	},
		logout: () => {
			console.log("logout.....")
			console.log({...initState})

			removeCookie('member')

			return {...initState}

		},
	// },


	// 비동기 호출 상태에 따라 동작하는 기능
	extraReducers: (builder) => {

		builder
				.addCase(loginPostAsync.fulfilled, (state, action) => {
					console.log('fullfilled')
					const payload = action.payload

					console.log("payload")
					console.log(payload)

					if(!payload.error){
						setCookie("member",JSON.stringify(payload),1)
					}

					return payload
				})
				.addCase(loginPostAsync.pending, (state, action) => {
					console.log('pending')
				})
				.addCase(loginPostAsync.rejected, (state, action) => {
					console.log('rejected')
				})

	}
})

export const {login, logout} = loginSlice.actions

export default loginSlice.reducer