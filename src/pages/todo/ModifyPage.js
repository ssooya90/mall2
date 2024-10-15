import React, {useCallback} from 'react';
import {createSearchParams, useNavigate, useParams, useSearchParams} from "react-router-dom";
import ModifyComponent from "../../components/todo/ModifyComponent";

const ModifyPage = () => {

	const {tno} = useParams()

	const navigate = useNavigate();
	const [queryParams] = useSearchParams();

	const page = queryParams.get("page") ? parseInt(queryParams.get("page")) : 1
	const size = queryParams.get("size") ? parseInt(queryParams.get("size")) : 10

	const queryStr = createSearchParams({page, size}).toString();


	const moveToRead = useCallback((tno) => {

		navigate({
			pathname : `/todo/read/${tno}`,
			search : queryStr,
		})

	},[page, size])


	const moveToList = () => {

		navigate({
			pathname : `/todo/list`,
		})

	}

	return (
			<div className="font-extrabold w-full bg-white mt-6">

				<div className={"text-2xl"}>
					Todo Modify Page Component {tno}
				</div>

				<ModifyComponent tno={tno}/>



				<div className={"flex justify-end p-4"}>

					<button type={"button"}
					        className={"rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"}
					        onClick={()=>moveToList()}
					        >
						List
					</button>

					{/*<button type={"button"}*/}
					{/*        className={"rounded p-4 m-2 text-xl w-32 text-white bg-red-500"}*/}
					{/*        onClick={() => moveToModify(13)}*/}
					{/*>*/}
					{/*	Modify*/}
					{/*</button>*/}

				</div>


			</div>
	);


};

export default ModifyPage;
