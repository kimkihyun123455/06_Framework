//console.log("ajax-main.js loaded")
const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");
// 할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");
// 할 일 목록 조회 관련 요소
const tbody = document.querySelector("#tbody");
// 할 일 상세 조회 관련 요소
const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.querySelector("#popupClose");
// 상세 조회 팝업레이어 관련 버튼 요소
const changeComplete = document.querySelector("#changeComplete");
const updateView = document.querySelector("#updateView");
const deleteBtn = document.querySelector("#deleteBtn");
// 수정 레이어 관련 요소
const updateLayer = document.querySelector("#updateLayer");
const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");
const updateBtn = document.querySelector("#updateBtn");
const updateCancel = document.querySelector("#updateCancel");







/*
fetch() API
비동기 요청을 수행하는 최신 Javascript API 중 하나

- Promies 는 비동기 작업의 결과를 처리하는 방법중 하나
-> 어떤 결과가 올지는 모르지만 반드시 결과를 송신
-> 비동기 작업이 맞이할 완료 또는 실패와 그 결과값을 나타낸다
-> 비동기 작업이 완료되었을 때 실행할 콜백함수를 지정하고,
   해당 작업의 성공 또는 실패 여부를 처리할 수 있도록 한다

Promise 객체는 세 가지 상태를 지닌다
- Pending (대기 중) : 비동기 작업이 완료되지 않아 대기중인 상태
- Fulfilled (이행됨) : 비동기 작업이 성공적으로 완료된 상태
- Rejected (거부됨) : 비동기 작업이 실패한 상태

*/

// 전체 Todo 개수 조회 및 출력하는 함수
function getTotalCount(){

  // 비동기로 서버에 전체 Todo 개수를 조회 요청
  // fetch() API 코드 작성
  fetch("/ajax/totalCount") // 서버로 "/ajax/totalCount"로 GET 요청
  // 첫번째 then : 응답을 처리하는 역할
  .then(response => {
    console.log(response);
    // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 결과를 가지는 Promise 객체 반환
    // 매개변수 response : 비동기 요청에 대한 응답이 담긴 객체
    return response.text(); // 응답(response)을 텍스트 형식으로 변환하는 콜백함수
  })
  // 두번째 then : 첫번째 then에서 return(변환)된 데이터를 활용하는 역할
  .then(result =>{ // 첫번째 콜백함수가 완료된 후 호출되는 콜백함수
                   // 변환된 텍스트 데이터(result)를 받아서 콘솔에 단순 출력
                   // 매개변수 result : 첫번째 콜백함수에서 반환된 Promise 객체의 PromiseResult 값
                   // == result 매개변수로 받아서 처리
    console.log(result); // 최종 결과값


  
  totalCount.innerText = result;

  })
}

// 완료된 할 일 개수 조회 및 출력하는 함수
function getCompleteCount(){

  fetch("/ajax/completeCount")
  .then(response => response.text())
  .then(result => {

    // #completeCount 요소에 내용으로 result 값 출력
    completeCount.innerText = result;
  })
}

// 새로고침 버튼이 클릭되었을 경우
reloadBtn.addEventListener("click",()=>{

  getTotalCount();
  getCompleteCount();
  selectTodoList();
});

// 할 일 추가 버튼 클릭 시 동작
addBtn.addEventListener("click",()=>{

  if(todoTitle.value.trim().length === 0  || todoContent.value.trim().length === 0 ){
    alert("제목이나 내용은 비어있을 수 없습니다");
  }

  // POST 방식 fetch() 비동기 요청 보내기
  // - 요청 주소 : "/ajax/add"
  // - 데이터 전달 방식 (method) : "post"
  // - 전달 데이터 : todoTitle 값, todoContent값
  // JS <-> Java
  // JSON (JavaScript Object Notation) : 데이터를 표현하는 문법
  // {"name" : "홍길동", "age" : "20", "skill" : "fire"}

  // todoTitle과 todoContent를 저장한 JS 객체
  const param = {
    // key : value
    "todoTitle" : todoTitle.value,
    "todoContent" : todoContent.value
  }

  // http 요청
  // 요청 Header -> 요청에 대한 정보(설정)
  // 요청 Body -> 데이터

  fetch("/ajax/add", {
    // key : value
    method : "POST", // POST 방식 요청
    headers : {"Content-Type" : "application/json"}, // 요청 데이터의 형식을 JSON으로 지정
    body : JSON.stringify(param) // param이라는 JS객체 JSON(string)으로 변환
  })
  .then(resp => resp.text()) // 반환된 값을 text로 변환
  .then(result => {
    // 첫번 째 then에서 반환된 값을 result에 저장

    if(result > 0){
      alert("추가 성공");

      // 추가 성공 했다면 작성했던 제목, 내용 인풋 지우기
      todoTitle.value ="";
      todoContent.value ="";

      // 할 일이 새롭게 추가되었으므로 Todo개수 조회 함수 재호출
      getTotalCount();

      // -> 전체 Todo 목록 조회하는 함수 재호출 예정
      selectTodoList();
    }else{
      alert("추가 실패");
    }
    
  });

});

//----------------------------------

// 비동기 할 일 전체 목록을 조회하는 함수
const selectTodoList = () => {

  fetch("/ajax/selectList")
  .then(resp => resp.json())
  .then(todoList => {
    // 매개변수 todoList : 
    // 첫번째 then에서 text() / json() 인지에 따라서
    // 단순 text이거나, JS Object 일 수 있음

    // 만약 resp.text() 사용했다면 문자열(JSON이 그래도 노출)
    // -> JSON.parse() 이용하여 JS Object 타입으로 변환 가능

    // JSON.parse(JSON 데이터) : string -> JS object
    // - String 형태의 JSON 데이터를 JS Object 타입으로 변환

    // JSON.stringify(JS Object) : JS Object -> string
    // - JS Object 타입을 string 형태의 JSON 데이터로 변환

    //-----------------------------------------------------

    // 기준에 출력되어 있던 할 일 목록 비우기
    tbody.innerHTML = "";

    // tbody에 tr/td 요소를 생성해서 내용 추가
    for(let todo of todoList) {
      // tr 태그 생성
      const tr = document.createElement("tr");

      // JS 객체에 존재하는 key 모음 배열 생성
      const arr =['todoNo', 'todoTitle', 'complete', 'regDate'];

      for(let key of arr) {
        const td = document.createElement("td");

        // 제목인 경우
        if(key === 'todoTitle'){
          const a = document.createElement("a");
          a.innerText = todo[key];
          a.href="/ajax/detial?todoNo"+todo.todoNo;
          td.append(a);
          tr.append(td);

          // a태그 클릭 시 페이지 이동 막고 비동기 요청으로 우회
          a.addEventListener("click", e=>{
            e.preventDefault(); // 기본 이벤트 방지

            // 할 일 상세 조회 비동기 요청 함수 호출
            selectTodo(e.target.href);
          });

          continue;
        }
        // 제목이 아닌 경우
        td.innerText = todo[key];
        tr.append(td); // tr의 마지막 요소로 현재 td 추가하기
      }

      // tbody의 자식으로 tr 추가
      tbody.append(tr);
    }


  });

}

const selectTodo = (url) => {
  // 매개변수 url == "/ajax/detail?todoNo=..." 형태의 문자열

  // fetch 요청 시 url 이용
  fetch("ajax/selectTodo",{
    method : "GET", 
    headers : {"Content-Type" : "application/json"}, // 요청 데이터의 형식을 JSON으로 지정
    body : JSON.stringify(url)}) // param이라는 JS객체 JSON(string)으로 변환)
    .then(resp => resp.text())
    .then(todoList => {
      popupLayer.style.display = "block";

      popupTodoNo.innerText = todoList.todoNo;
      popupTodoTitle.innerText = todoList.todoTitle;
      popupComplete.innerText = todoList.complete;
      popupRegDate.innerText = todoList.regDate;
      popupTodoContent.innerText = todoList.todoContent;

      
    })
};




selectTodoList();
getTotalCount();
getCompleteCount();
