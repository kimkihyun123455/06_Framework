const goToList = document.querySelector("#goToList");

goToList.addEventListener("click",()=>{
  location.href="/";
});

// 완료여부 변경 버튼
const completeBtn = document.querySelector(".complete-btn");

completeBtn.addEventListener("click", (e)=>{

  // 요소.dataset : data-* 속성에 저장된 값 반환
  // data-toto-no 세팅한 속성값 얻어오기
  // (html) data-todo-no -> js(카멜케이스) dataset.todoNo
  const todoNo = e.target.dataset.todoNo;

  let complete = e.target.tnnerText;

  // Y <-> N
  complete = (complete === 'Y') ? 'N' : 'Y';

  // 완료 여부 수정 요청하기
  location.href=`/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`
});

  // 수정 버튼 클릭 시 동작
  const updateBtn = document.querySelector("#updateBtn");

  updateBtn.addEventListener("click", e=>{
    const todoNo = e.target.dataset.todoNo;

    location.href=`/todo/update?todoNo=${todoNo}`;
  })

  const deleteBtn = document.querySelector("#deleteBtn");

  deleteBtn.addEventListener("click", e =>{
    const todoNo = e.target.dataset.todoNo;

    location.href=`/todo/delete?todoNo=${todoNo}`;
  });