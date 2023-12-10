import React, { useState } from "react";
import SockJS from 'sockjs-client';
import {over} from 'stompjs';

var stompClient = null;
const App = () => {
  const [publicChats, setPublicChats] = useState([]);
  const [privateChats, setPrivateChats] = useState(new Map());
  const [tab, setTab] = useState("PUBLIC");
  const [user, setUser] = useState({
      username:"",
      recievername:"",
      connected:false,
      message:""
  });

  const handleValue = (event) => {
      const {value, name} =  event.target;
      setUser({...user,[name]:value});
  }

  const registerUser = () => {
      let Sock = new SockJS('http://localhost:8080/ws');
      stompClient = over(Sock);
      stompClient.connect({},onConnected,onError);
  }

  const onConnected = () => {
      setUser({...user,"connected":true});
      stompClient.subscribe('/topic/public', onPublicMessageRecieved);
      stompClient.subscribe('/user/' + user.username + '/private', onPrivateMessageRecieved);
      userJoin();
  }

  const userJoin = () => {
    console.log('start join');
      let message = {
          sender: user.username,
          receiver: "PUBLIC",
          content: (user.username + " join to the chat"),
          type: "JOIN"
      };
      stompClient.send('/app/message.add', {}, JSON.stringify(message));
      console.log('end join');
  }

  const onPublicMessageRecieved = (payload) => {
      let payloadData = JSON.parse(payload.body);
      switch(payloadData.type){
          case "JOIN":
              if(!privateChats.get(payloadData.sender)){
                  privateChats.set(payloadData.sender,[]);
                  setPrivateChats(new Map(privateChats));
                  publicChats.push(payloadData);
                  setPublicChats([...publicChats]);

              }
              break;
          case "LEAVE":
              break;    
          case "MESSAGE":
              publicChats.push(payloadData)
              setPublicChats([...publicChats]);
              break;
          case "DELETE":
              break;
      }
  }

  const onPrivateMessageRecieved = (payload) => {
      var payloadData = JSON.parse(payload);
      if(privateChats.get(payloadData.sender)){
          privateChats.get(payloadData.sender).push(payload);
          setPrivateChats(new Map(privateChats));
      } else {
          let list = [];
          list.push(payloadData);

          privateChats.set(payloadData.sender, list);
          setPrivateChats(new Map(privateChats));
      }
      // switch(payloadData.type){
      //     case "JOIN":
      //         break;
      //     case "LEAVE":
      //         break;    
      //     case "MESSAGE":
      //         publicChat.push(payloadData)
      //         setPublicChat([...publicChat]);
      //         break;
      //     case "DELETE":
      //         break;
      // }
  }

  const onError = (error) => {
      console.log(error);
  }

  const RenderUsers = () => {
    const chatsArray = Array.from(privateChats.keys()); // преобразуем ключи Map в массив

    return (
      chatsArray.map((name, index) => (
        <div 
          key={index} onClick={() => {setTab(name)}} 
          className={`user ${tab === name && "active"}`}
          style={{backgroundColor: tab === name ? '#c1dbc4' : '#c1cbdb'}} >
          <span className='user-name'>{name}</span>
        </div>
      ))
    );
  }

  const RenderMessages = () => {
    if(tab === "PUBLIC"){
        return publicChats.map(m => {
            const date = new Date(m.dateTime);
            const hours = date.getHours();
            const minutes = date.getMinutes();
            const time = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
            const renderName = m.sender !== user.username ? m.sender : "You";
            
            return (
              <div className='message'>
                <span className='user-name'>{renderName}</span>
                <span className='message-content'>{m.content}</span>
                <span className='message-time'>{time}</span>
              </div>
            );
        })
    } else {
        return [...privateChats.get(tab)].map(m => {
            const date = new Date(m.dateTime);
            const hours = date.getHours();
            const minutes = date.getMinutes();
            const time = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
            const renderName = m.sender !== user.username ? m.sender : "You";
            
            return (
              <div className='message'>
                <span className='user-name'>{renderName}</span>
                <span className='message-content'>{m.content}</span>
                <span className='message-time'>{time}</span>
              </div>
            );
        })
    }
  }

  const addMessage = () => {
      if(stompClient){
          let message = {
              sender: user.username,
              receiver: tab,
              content: user.message,
              type: 'MESSAGE'
          };
          if(tab === "PUBLIC"){
              stompClient.send('/app/message.add',{},JSON.stringify(message));
          } else {
              if(user.username !== tab){
                  privateChats.set(tab).push(message);
                  setPrivateChats(new Map(privateChats));
              }
              stompClient.send('/app/private-message',{},JSON.stringify(message));
          }             
          setUser({...user,"message":""});
      }
  }

  return(
  <>
      {user.connected ? 
      <div id='App'>
        <div className='chat'>
          <section className='messages-and-users'>
            <div className='messages-list'>
              <RenderMessages />
            </div>
            <div className='users-list'>
              <div 
                onClick={() => {setTab("PUBLIC")}} 
                className = {`user ${tab==="PUBLIC" && "active"}`}
                style={{backgroundColor: tab === "PUBLIC" ? '#c1dbc4' : '#c1cbdb'}}
              >Public chat</div>
              <RenderUsers />
            </div>
          </section>
          <section className='control'>
            <textarea className='new-message' name='message' value={user.message} onChange={handleValue}></textarea>
            <button className='send-message' onClick={addMessage}>Send</button>
          </section>
        </div>
        </div>
      :
      <div id='App'>
        <div className='registration'>
          <input 
              id='user-name'
              name='username' 
              placeholder='Enter the user name' 
              value={user.username} 
              onChange={handleValue}
          />
          <button type='button' onClick={registerUser}>
              connect
          </button> 
        </div>
        
    </div>
      }
  </>
  )
}

export default App;
