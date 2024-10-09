import {createBrowserRouter} from "react-router-dom";
import {lazy, Suspense} from "react";

const Loading = <div>Loading...</div>
const MainPage = lazy(() => import("../pages/MainPage"))
const AboutPage = lazy(() => import("../pages/AboutPage"))

const root = createBrowserRouter([

	{
		path : "",
		element : <Suspense fallback={Loading}><MainPage/></Suspense>
	},

	{
		path : "/about",
		element : <Suspense fallback={Loading}><AboutPage/></Suspense>
	}

])

export default root