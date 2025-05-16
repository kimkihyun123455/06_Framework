// 웹소켓 테스트

// 1. SockJS 라이브러리 추가 (common.html에 작성)

// 2. SockJS 객체를 생성
// ws://localhost(또는 ip)
const testSock = new SockJS("/testSock");
// ws://localhost(또는 ip)/testSock

// 3. 생성된 SockJS 객체를 이용해서 메세지 전달
const sendMessageFn = (name, str) => {

  // JSON 이용해서 데이터를 text 형태로 전달
  const obj = {
    "name" : name,
    "str"  : str
  };

  testSock.send(JSON.stringify(obj));
}

// 4. 서버로부터 현재 클라이언트에게 웹소켓을 이용한 메세지가 전달된 경우
testSock.addEventListener("message", e=>{

  // e.data : 서버로부터 전달받은 message 존재
  // JSON -> JS Object 형태로 변환
  const msg = JSON.parse(e.data);
  console.log(`${msg.name} : ${msg.str}`);
});