import React, {lazy, Suspense} from 'react';
import {Navigate} from "react-router-dom";


const Loading = <div>Loading...</div>
const TodoList = lazy(() => import("../pages/todo/ListPage"))
const TodoRead = lazy(() => import("../pages/todo/ReadPage"))
const TodoModify = lazy(() => import("../pages/todo/ModifyPage"))
const TodoAdd = lazy(() => import("../pages/todo/AddPage"))


const todoRouter = () => {
	return [
		{
			path : "",
			element: <Navigate replace to="list"/>
		},
		{
			path : "list",
			element : <Suspense><TodoList/></Suspense>
		},
		{
			path : "read/:tno",
			element : <Suspense fallback={Loading}><TodoRead/></Suspense>
		},
		{
			path : "modify/:tno",
			element : <Suspense fallback={Loading}><TodoModify/></Suspense>
		},
		{
			path : "add",
			element : <Suspense><TodoAdd/></Suspense>
		},


	]
};

export default todoRouter;
