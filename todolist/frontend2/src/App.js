import { useState, useRef } from "react";
import TodoList from "./Todolist";
import { v4 as uuidv4 } from "uuid";
import Axios from "axios";

function App() {
  const [todos, setTodos] = useState([]);
  const todoNameRef = useRef();

  const posting = (text) => {
    Axios.post('http://127.0.0.1:5000/post', {
      post_text: text
    }).then(res => {
        console.log(res.data.result);
        setTodos(res.data.result);
    });
  };

  const reload = () => {
    Axios.get('http://127.0.0.1:5000/reload')
    .then(res => {
      console.log(res.data.result);
      setTodos(res.data.result);
    });
  };

  const handleAddTodo = () => {
    const name = todoNameRef.current.value;
    if (name === "") return;
    const newTodos = [...todos, { id: uuidv4(), name: name, completed: false }];
    setTodos(newTodos);
    posting(newTodos);
    todoNameRef.current.value = null;
  };

  const toggleTodo = (id) => {
    const newTodos = [...todos];
    const todo = newTodos.find((todo) => todo.id === id);
    todo.completed = !todo.completed;
    setTodos(newTodos);
    posting(newTodos);
  };

  const handleClear = () => {
    const newTodos = todos.filter((todo) => !todo.completed);
    setTodos(newTodos);
    posting(newTodos);
  };

  return (
    <div>
      <TodoList todos={todos} toggleTodo={toggleTodo} />
      <input type="text" ref={todoNameRef} />
      <button onClick={handleAddTodo}>タスクを追加</button>
      <button onClick={handleClear}>完了したタスクの削除</button>
      <button onClick={reload}>更新</button>
      <div>残りのタスク:{todos.filter((todo) => !todo.completed).length}</div>
    </div>
  );
}

export default App;