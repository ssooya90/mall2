import React from 'react';
import ReadComponent from "../../components/products/ReadComponent";
import {useParams} from "react-router-dom";
import ModifyComponent from "../../components/products/ModifyComponent";


const ModifyPage = () => {

	const {pno} = useParams();

	return (
			<div className="p-4 w-full bg-white">
				<div className="text-3xl font-extrabold">
					Products Modify Page

					<ModifyComponent pno={pno}/>
				</div>
			</div>
	);
};

export default ModifyPage;
