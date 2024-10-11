import {createBrowserRouter} from "react-router-dom";
import {lazy, Suspense} from "react";
import Error404 from "../components/error/error404";
import todoRouter from "./todoRouter";

const Loading = <div>Loading...</div>
const MainPage = lazy(() => import("../pages/MainPage"))
const AboutPage = lazy(() => import("../pages/AboutPage"))
const TodoIndex = lazy(() => import("../pages/todo/IndexPage"))

const root = createBrowserRouter([

	{
		path : "/",
		element : <Suspense fallback={Loading}><MainPage/></Suspense>,
		errorElement : <Error404/>
	},

	{
		path : "/about",
		element : <Suspense fallback={Loading}><AboutPage/></Suspense>,
	},

	{
		path : "/todo",
		element : <Suspense fallback={Loading}><TodoIndex/></Suspense>,
		children : todoRouter()
	},




])

export default root