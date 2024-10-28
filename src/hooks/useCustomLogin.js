import React from 'react';
import {createSearchParams, Navigate, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {loginPostAsync, logout} from "../slices/loginSlice";

const UseCustomLogin = () => {

	const navigate = useNavigate();

	const dispatch = useDispatch();

	const loginState = useSelector(state => state.loginSlice)

	const isLogin = loginState.email ? true : false

	const exceptionHandle = (ex) => {

		console.log("Exception")

		console.log(ex)

		const errorMsg = ex.response.data.error
		const errorStr = createSearchParams({error:errorMsg}).toString()


		if(errorMsg === 'REQUIRE_LOGIN'){
			alert('로그인 해야만 합니다')
			navigate({pathname:"/member/login", search : errorStr})

			return
		}

		if(ex.response.data.error === 'ERROR_ACCESS_DENIED'){

			alert('해당 메뉴를 사용할 수 있는 권한이 없습니다');
			navigate({pathname:"/", search : errorStr})
			return

		}

	}

	const doLogin = async (loginParam) => {

		const action = await dispatch(loginPostAsync(loginParam))

		return action.payload;
	}

	const doLogout = () => {
		dispatch(logout())
	}

	const moveToPath = (path) =>{
		navigate({pathname : path}, {replace : true})
	}

	const moveToLogin = () => {
		navigate({pathname : '/member/login'}, {replace : true})
	}

	const moveToLoginReturn = () => {


		// react가 랜더링될 때 navigate 함수가 실행되면, 무한 루프에 빠질 수 있으므로 오류가 발생

		// useEffect등을 활용하여 변경하거나 return <Navigate>로 처리


		// navigate({pathname : '/member/login'}, {replace : false})


		// return <Navigate replace to={"/member/login"}/>
		return <Navigate replace to={"/member/login"}/>
	}

	return {loginState, isLogin, doLogin, doLogout, moveToPath, moveToLogin, moveToLoginReturn, exceptionHandle}

};

export default UseCustomLogin;
