import React, {useEffect, useRef, useState} from 'react';
import {postAdd} from "../../api/productsAPI";
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";


const initState = {
	pname: '',
	pdesc: '',
	price: 0,
	files: []
}


const AddComponent = () => {

	const [product, setProduct] = useState(initState)

	const [fetching, setFetching] = useState(false)

	const [result, setResult] = useState(false)

	const {moveToList} = useCustomMove();

	const uploadRef = useRef();

	const handleChangeProduct = (event) => {

		product[event.target.name] = event.target.value

		setProduct({...product})

		console.log(product)


	}

	const handleClickAdd = () => {

		const files = uploadRef.current.files

		console.log(files)

		const formData = new FormData();

		for(let i = 0 ; i < files.length ; i ++) {
			formData.append("files",files[i]);
		}


		formData.append("pname",product.pname);
		formData.append("pdesc",product.pdesc);
		formData.append("price",product.price);

		console.log(formData)

		setFetching(true);

		postAdd(formData).then((result) => {

			console.log("결과")
			console.log(result)


			// 등록 완료 후 초기화
			setProduct({...initState})
			setFetching(false);
			setResult(true);



		})
	}

	const closeModal = () => {

		moveToList();

	}


	return (
			<div className="border-2 border-sky-200 mt-10 m-2 p-4">
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Product Name</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
						       name="pname" type={'text'} value={product.pname} onChange={handleChangeProduct}>
						</input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Desc</div>
						<textarea
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md resize-y"
								name="pdesc" rows="4" onChange={handleChangeProduct} value={product.pdesc}>
{product.pdesc}
</textarea>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Price</div>
						<input
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
								name="price" type={'number'} value={product.price} onChange={handleChangeProduct}>
						</input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">Files</div>
						<input
								ref={uploadRef}
								className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
								type={'file'} multiple={true}>
						</input>
					</div>
				</div>
				<div className="flex justify-end">
					<div className="relative mb-4 flex p-4 flex-wrap items-stretch">
						<button type="button"
						        className="rounded p-4 w-36 bg-blue-500 text-xl text-white "
						        onClick={handleClickAdd}>
							ADD
						</button>
					</div>
				</div>

				{fetching ? <FetchingModal/> : <></>}

				{result ? <ResultModal
						title={'완료'}
						content={'등록완료'}
						callbackFn={closeModal}
						/>
						: <></>
				}


			</div>
	);
};

export default AddComponent;
