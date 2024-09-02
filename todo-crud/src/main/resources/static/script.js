const todoList = document.getElementById('todo-list');
const todoForm = document.getElementById('todo-form');
const titleInput = document.getElementById('title');
const descriptionInput = document.getElementById('description');
const submitBtn = document.getElementById('submit-btn');

// Get all ToDos from API
fetch('/api/todos')
 .then(response => response.json())
 .then(data => {
    data.forEach(todo => {
      const todoItem = document.createElement('li');
      todoItem.textContent = `${todo.title} - ${todo.description}`;
      todoList.appendChild(todoItem);
    });
  });

// Add new ToDo
submitBtn.addEventListener('click', (e) => {
  e.preventDefault();
  const title = titleInput.value;
  const description = descriptionInput.value;
  fetch('/api/todos', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ title, description })
  })
   .then(response => response.json())
   .then(data => {
      const todoItem = document.createElement('li');
      todoItem.textContent = `${data.title} - ${data.description}`;
      todoList.appendChild(todoItem);
      titleInput.value = '';
      descriptionInput.value = '';
    });
});

// Update ToDo
todoList.addEventListener('click', (e) => {
  if (e.target.tagName === 'LI') {
    const todoId = e.target.dataset.id;
    fetch(`/api/todos/${todoId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ completed: true })
    })
     .then(response => response.json())
     .then(data => {
        e.target.style.textDecoration = 'line-through';
      });
  }
});

// Delete ToDo
todoList.addEventListener('click', (e) => {
  if (e.target.tagName === 'LI') {
    const todoId = e.target.dataset.id;
    fetch(`/api/todos/${todoId}`, {
      method: 'DELETE'
    })
     .then(response => response.json())
     .then(data => {
        e.target.remove();
      });
  }
});