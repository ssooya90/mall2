import React, {useEffect, useState} from 'react';
import axios from "axios";
import {getOne, postAdd, putOne} from "../../api/todoApi";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
	tno : 0,
	title : '',
	writer : '',
	dueDate: '',
	complete : false,
}

const ModifyComponent = ({tno}) => {

	const [todo, setTodo] = useState({...initState})
	const [result, setResult] = useState(null)

	const {moveToRead, moveToList} = useCustomMove()

	useEffect(() => {

		getOne(tno).then( (data) => {
			console.log(data)
			setTodo(data)
		})

	}, [tno]);


	const handleChangeTodo = (e) =>{
		todo[e.target.name] = e.target.value;
		setTodo({...todo})
	}

	const handleChangeTodoComplete = (e) => {

		const value = e.target.value

		todo.complete = value === 'Y';
		setTodo({...todo})
	}

	const handleClickModify = () => {

		putOne(todo).then(result => {

			console.log(result) // {RESULT : SUCCESS)
			console.log("modify result: " + result) // {RESULT : SUCCESS)

			setResult('Modify')

			console.log("리쩌뜨")
			console.log(result)

		})


	}

	const closeModal = () => {

		if(result === 'Deleted'){
			moveToList()
		}else{
			moveToRead(tno)
		}

	}


	return (
			<div className="border-2 border-sky-200 mt-10 m-2 p-4">
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">TITLE</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="title" type={'text'} value={todo.title} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">WRITER</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="writer" type={'text'} value={todo.writer} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">DUEDATE</div>
						<input className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
						       name="dueDate" type={'date'} value={todo.dueDate} onChange={handleChangeTodo}></input>
					</div>
				</div>
				<div className="flex justify-center">
					<div className="relative mb-4 flex w-full flex-wrap items-stretch">
						<div className="w-1/5 p-6 text-right font-bold">COMPLETE</div>
						<select
								name="status"
								className="border-solid border-2 rounded m-1 p-2"
								onChange={handleChangeTodoComplete}
								value={todo.complete ? 'Y' : 'N'}>
							<option value='Y'>Completed</option>
							<option value='N'>Not Yet</option>
						</select>
					</div>
				</div>

				<div className="flex justify-end">
					<div className="relative mb-4 flex p-4 flex-wrap items-stretch">
						<button type="button"
						        onClick={handleClickModify}
						        className="rounded p-4 w-36 bg-blue-500 text-xl text-white"> MODIFY
						</button>
					</div>
				</div>


				{/*result 상태가 있을 경우 모달을 띄움*/}
				{result ?
						// alert('1')

						<ResultModal
								title={"완료"}
								content={"등록 완료"}
								callbackFn={closeModal}
						/>



						: <></>}


			</div>
	);
};

export default ModifyComponent;
