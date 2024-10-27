import React, {useState} from 'react';
import {useDispatch} from "react-redux";
import {login, loginPostAsync} from "../../slices/loginSlice";
import {useNavigate} from "react-router-dom";

const initState = {
	email: '',
	pw: ''
}

function LoginComponent() {

	const [loginParam, setLoginParam] = useState({...initState})

	const navigate = useNavigate();

	const dispatch = useDispatch();

	// const {doLogin, moveToPath} = useCustomLogin()


	const handleChange = (e) => {

		loginParam[e.target.name] = e.target.value

		setLoginParam({...loginParam})



	}

	const handleClickLogin = (e) => {

		// dispatch(login(loginParam))

		// 비동기 처리를 위한 login 메서드가 아닌 loginPstAsync 기능 사용

		dispatch((loginPostAsync(loginParam)))
				.unwrap()
				.then(data => {

					console.log("after unwrap...")
					console.log(data)


					if (data.error) {
						alert("이메일과 패스워드 확인해 주세요")
					} else {
						alert("로그인 성공");

						navigate({pathname : '/'},{replace:true}) //뒤로 가기 했을 떄 로그인 화면 볼수 없게
					}


				})







		// doLogin(loginParam).then(
		// 		data => {
		//
		// 			if (data.error) {
		// 				alert("이메일과 패스워드 확인해 주세요")
		// 			} else {
		// 				alert("로그인 성공");
		//
		// 				moveToPath("/");
		// 			}
		//
		//
		// 		}
		// )

		// dispatch(loginPostAsync(loginParam))
		// 		.unwrap()
		// 		.then(data => {
		//
		// 			if(data.error){
		// 				alert("이메일과 패스워드 확인해 주세요")
		// 			}else{
		// 				alert("로그인 성공");
		//
		// 				moveToPath();
		//
		// 			}
		//
		//
		// 		})
	}

	return (
			<div className="border-2 border-sky-200 mt-10 m-2 p-4">
				<div className="flex justify-center">
					<div className="text-4xl m-4 p-4 font-extrabold text-blue-500">Login Component</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-full p-3 font-bold">Email</div>
						<input className="w-full p-3 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="email" type={'text'} value={loginParam.email} onChange={handleChange}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-full p-3 font-bold">Password</div>
						<input className="w-full p-3 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="pw" type={'password'} value={loginParam.pw} onChange={handleChange}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full justify-center">
						<div className="w-2/5 p-6 flex justify-center font-bold">
							<button
									className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
									onClick={handleClickLogin}
							>
								LOGIN
							</button>
						</div>
					</div>
				</div>

				{/*<KakaoLoginComponent/>*/}

			</div>
	);
}

export default LoginComponent;