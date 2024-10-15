import React, {useState} from 'react';
import axios from "axios";
import {postAdd} from "../../api/todoApi";
import ResultModal from "../common/ResultModal";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
	title : '',
	writer : '',
	dueDate: '',
}

const AddComponent = () => {

	const [todo, setTodo] = useState({...initState})
	const [result, setResult] = useState(null)

	const {moveToList} = useCustomMove()


	const handleChangeTodo = (e) =>{

		todo[e.target.name] = e.target.value;
		setTodo({...todo})

		console.log("변경 TODO")
		console.log(todo)
	}

	const handleClickAdd = () => {


		postAdd(todo).then(result => {

			console.log(result)
			setResult(result.tno)

			setTodo({...initState})


		})


	}

	const closeModal = () => {
		setResult(null)
		moveToList()
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
				<div className="flex justify-end">
					<div className="relative mb-4 flex p-4 flex-wrap items-stretch">
						<button type="button"
						        onClick={handleClickAdd}
						        className="rounded p-4 w-36 bg-blue-500 text-xl text-white"> ADD
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


						// (
						// 		<>
						// 			{(alert('1'), console.log('tt'))}
						// 			<div>Condition is true!</div>
						// 		</>
						//
						// )

						: <></>}


			</div>
	);
};

export default AddComponent;
