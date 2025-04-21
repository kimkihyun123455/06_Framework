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
          a.href="/ajax/detail?todoNo="+todo.todoNo;
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
  fetch(url)
  .then(resp => resp.json())
  .then(todo => {
    console.log(todo);

    // popup layer에 조회된 값을 출력
    popupTodoNo.innerText = todo.todoNo;
    popupTodoTitle.innerText = todo.todoTitle;
    popupComplete.innerText = todo.complete;
    popupRegDate.innerText = todo.regDate;
    popupTodoContent.innerText = todo.todoContent;

    // popup layer 보이게 하기
    popupLayer.classList.remove("popup-hidden");

    // update layer가 열려있따면 숨기기
    updateLayer.classList.add("popup-hidden");

  });
}

// popupLayer X 버튼 클릭 시 닫기
popupClose.addEventListener("click", ()=>{
  // 숨겨주는 class 추가
  popupLayer.classList.add("popup-hidden");
});

// 삭제 버튼 클릭 시
deleteBtn.addEventListener("click", ()=>{
  
  // 취소 클릭 시 해당 함수 종료
  if(!confirm("정말 삭제하시겠습니까?")){
    return;
  }
  // 삭제할 할 일 번호 얻어오기
  const todoNo = popupTodoNo.innerText;

  // method 목록 :: get : 조회 / post : 삽입 / delete : 삭제 / put : 수정
  // 삭제 비동기 요청(DELETE)
  fetch("/ajax/delete",{
    method : "DELETE", // delete 방식 요청
    headers : {"Content-Type" : "application/json"},
    body : todoNo // todoNo 단입 값 하나는 JSON 형태로 자동 변환되어 전달됨
    // body : JSON.stringify(todoNo) 형태로 원래 변환 받아야함
  })
  .then(resp => resp.text())
  .then(result =>{
    console.log(result);

    if(result > 0){// 삭제 성공
      alert("삭제되었습니다");
      // 상세조회 팝업레이어 닫기
      popupLayer.classList.add("popup-hidden");

      // 전체, 완료된 할 일 개수, 할 일 목록 갱신
      selectTodoList();
      getTotalCount();
      getCompleteCount();
    }else{// 삭제 실패
      alert("삭제가 실패하셨습니다");
    }

  });


});

// 완료 여부 변경 버튼 클릭 시
changeComplete.addEventListener("click", ()=>{

  // 변경할 할 일 번호, 완료 여부 (Y <-> N)
  const todoNo = popupTodoNo.innerText;
  const complete = popupComplete.innerText === 'Y'?'N':'Y';

  // SQL 수행에 필요한 두 값을 JS 객체로 묶음
  const obj = {"todoNo" : todoNo, "complete":complete};
  //          {"todoNo" : 2, "complete" : "Y"}

  // 비동기로 완료여부 변경 요청(PUT 요청 방식)
  fetch("/ajax/changeComplete", {
    method : "PUT", // PutMapping
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(resp => resp.text())
  .then(result => {

    if(result > 0){
      alert("완료여부 변경 성공");

      // update 된 DB 데이터를 다시 조회해서 화면에 출력하면 서버에 부하가 크다
      // selectTodo();

      // 상세조회에서 Y/N 바꾸기
      popupComplete.innerText = complete;

      // 완료된 할 일 개수 갱신
      // getCompleteCount(); 다시 요청하면 서버에 부하
      // 완료된 Todo 개수 +- 
      const count = Number(completeCount.innerText)
      if(complete==='Y') completeCount.innerText = count + 1;
      else completeCount.innerText = count - 1;

      // 위처럼 서버에 재요청하지 않고 바꾸려면 코드가 길어져 그냥 요청함
      selectTodoList();

    }else{
      alert("완료여부 변경 실패");
    }

  });

});

updateView.addEventListener("click",()=>{

  popupLayer.classList.add("popup-hidden");
  updateLayer.classList.remove("popup-hidden");

  updateTitle.value = popupTodoTitle.innerText;
  // HTML 에서는 줄바꿈을 <br>로 인식하여 텍스트창에 다 노출되어 있기때문에
  // <br>을 textarea에서의 줄바꿈 \n 으로 바꾸고 innerHTML로 실행도 시켜야 제대로 나온다
  updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n"); 

  // 수정 레이어 -> 수정 버튼에 data-todo-no 추가
  updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText);

  
});



updateBtn.addEventListener("click", (e)=>{
  
  const obj = {"todoNo" : e.target.dataset.todoNo, todoTitle : updateTitle.value, "todoContent" : updateContent.value}
  
  fetch("/ajax/update",{
    method : "PUT",
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(obj)
  })
  .then(resp => resp.text())
  .then(result => {
  
    if(result > 0){
      selectTodoList();
      alert("수정이 완료되었습니다");
      
      updateLayer.classList.add("popup-hidden");
      popupLayer.classList.remove("popup-hidden");

      popupTodoTitle.innerText = updateTitle.value;
      popupTodoContent.innerHTML = updateContent.value.replaceAll("<\n>", "<br>");

      popupLayer.classList.add.remove("popup-hidden");

      
      updateTitle.value="";
      updateContent.value="";
      updateBtn.removeAttribute("date-todo-no"); // 속성 제거
      
  
    }else alert("수정에 실패하였습니다");

  });
});

updateCancel.addEventListener("click", ()=>{

  updateLayer.classList.add("popup-hidden");
  popupLayer.classList.remove("popup-hidden");

});









selectTodoList();
getTotalCount();
getCompleteCount();
