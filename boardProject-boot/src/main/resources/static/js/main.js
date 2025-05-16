// 쿠키에 저장된 이메일 input 창에서 입력해놓기
// 로그인이 안된 경우에 수행

// 쿠키에서 매개변수로 전달받은 key와 일치하는 value 얻어와 반환하는 함수
const getCookie = (key) => {
  const cookies = document.cookie; // "K:V; K=V; ...." 형태로 쿠키 반환

  // cookies에 저장된 문자열을 배열 형태로 변환
  const cookieList = cookies.split("; ") // ;를 기준으로 문자열분리
                    .map(el => el.split("=")); 

  // 배열.map(함수) : 배열의 각 요소를 이용해 콜백함수 수행 후 결과값으로
                   // 새로운 배열을 만들어서 반환하는 JS 내장 함수
                   
  // 배열 -> JS 객체로 변환(쉽게 다루기 위함)
  
  /*
  [
    ['saveId', 'uesr01@kh.or.kr'],
    ['test', 'testValue']
  ]
  
  2차원 배열 형태임
  */

  const obj = {}; // 비어 있는 객체 선언

  for(let i=0; i<cookieList.length; i++){
    const k = cookieList[i][0];
    const v = cookieList[i][1];
    obj[k]=v; // obj 객체에 K:V 형태로 추가
  }

  return obj[key]; // 매개변수로 전달받은 key와 obj 객체에 저장된 key가 일치하는 요소의 value값 반환
  
}

// 이메일 작성 input 태그 요소
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']")

if(loginEmail != null) { // 로그인 폼의 이메일 input태그가 화면상에 존재할때

  // 쿠키 중 key 값이 "saveId"인 요소의 value얻어오기
  const saveId = getCookie("saveId"); // 이메일 또는 undefined

  // saveId값이 있을 경우
  if(saveId != undefined){
    loginEmail.value = saveId; // 쿠키에서 얻어온 이메일 값을 input 요소의 value값에 세팅
    // 아이디 저장 체크박스 체크
    document.querySelector("input[name='saveId']").checked = true;
  }
}

// const loginForm = document.querySelector("#loginForm")
// const loginBtn = document.querySelector("#loginBtn")
// const memberEmail = document.querySelector("[name='memberEmail']")
// const memberPw = document.querySelector("[name='memberPw']")

// loginForm.addEventListener("submit", (e)=>{

//   if(memberEmail.value.trim().length === 0 || memberPw.value.trim().length === 0){
//     alert("아이디 혹은 비밀번호를 입력하세요");
//     e.preventDefault();
//   }
// });

