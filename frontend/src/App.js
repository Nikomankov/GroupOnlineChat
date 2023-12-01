function Message(message) {
  const time = message.dateTime;
  return (
    <div id='message'>
      <span id='user-name'>{message.user.name}</span>
      <span id='message-text'>{message.message}</span>
      <span id='message-time'>{time}</span>
    </div>
  )
}

function User(user) {
  return (
    <div id='user'>
      <span id='user-name'>{user.name}</span>
      <span id='user-ip'>{user.ip}</span>
    </div>
  )
}

function MessagesList() {
  const user = {
    id: 1,
    name: 'user',
    ip: 'puk',
    sessionId: "puk puk"
  }
  
  const message = {
    id: 1,
    message: "Hello there!",
    dateTime: "2023-11-28 15:13:42.786276",
    user: user
  }

  return(
    <div id='messages-list' className="col-5">
      {Message(message)}
    </div>
  )
}

function UsersList() {
  const user = {
    id: 1,
    name: 'user',
    ip: 'puk',
    sessionId: "puk puk"
  }

  return(
    <div id='user-list' className="col">
      {User(user)}
    </div>
  )
}

function App() {

  return (
    <div className="App"></div>
    // <div id="App">
    //   <div id='interface' className="container">
    //       <section id='messages-and-users' className="row">
    //         <MessagesList/>
    //         <UsersList/>
    //       </section>
    //       <section id='control' className="row">
    //         <textarea id='new-message' className="col-5"></textarea>
    //         <button id='send-message'className="col">Send</button>
    //       </section>
    //     </div>
    // </div>
  );
}

export default App;

