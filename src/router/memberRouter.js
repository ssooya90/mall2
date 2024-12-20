import React, {lazy, Suspense} from 'react';

const Loading = <div className={"bg-red-700"}>Loading...</div>
const Login = lazy(()=> import("../pages/member/LoginPage"))
const Logout = lazy(()=> import("../pages/member/LogoutPage"))

const MemberRouter = () => {
	return [
		{
			path : "login",
			element: <Suspense fallback={Loading}><Login/></Suspense>
		},
		{
			path : "logout",
			element: <Suspense fallback={Loading}><Logout/></Suspense>
		}
	]
};

export default MemberRouter;
