import React from 'react';
import BasicLayout from "../layouts/BasicLayout";
import useCustomLogin from "../hooks/useCustomLogin";

function AboutPage() {

	const {isLogin,moveToLoginReturn, moveToLogin} = useCustomLogin();


	if(!isLogin){
		return moveToLoginReturn();
	}

	return (
			<BasicLayout>
				<div className={"text-3xl"}>About Page</div>
			</BasicLayout>
	);
}

export default AboutPage;