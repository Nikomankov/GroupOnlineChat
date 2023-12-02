import { useEffect, useState } from 'react';
import API from '../src/api';

function App() {
  const [users, setUsers] = useState([]);
  // const [userProfile, setUserProfile] = useState();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');

  useEffect(() => {
    getMessages();
    getUsers();
  }, []);

  const getMessages = () => {
    API.get(`message`)
      .then(response => {
        setMessages(response.data);
      })
      .catch(error => {
        console.error('Error fetching messages:', error);
      });
  };

  const addMessage = () => {
    API.post(`message`, { text: newMessage })
      .then(response => {
        const newMessageData = response.data;
        setMessages([...messages, { id: newMessageData.id, text: newMessageData.text }]);
        setNewMessage('');
      })
      .catch(error => {
        console.error('Error sending message:', error);
      });
  };

  const getUsers = () =>{
    API.get(`user`)
      .then(response => {
        setUsers(response.data);
      })
      .catch(error => {
        console.error('Error fetching messages:', error);
      });
  }

  const renderMessages = () => {
    return messages.map(m => {
      const date = new Date(m.dateTime);
      const hours = date.getHours();
      const minutes = date.getMinutes();
      const time = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
      
      return (
        <div id='message'>
          <span id='user-name'>{m.user.name}</span>
          <span id='message-content'>{m.content}</span>
          <span id='message-time'>{time}</span>
        </div>
      );
    })
  };

  const renderUsers = () => {
    return users.map(u => (
      <div id='user'>
        <span id='user-name'>{u.name}</span>
        <span id='user-ip'>{u.ip}</span>
      </div>
    ))
  };

  return (
    <>
    <div className="App"></div><div id="App">
      <div id='interface' className="container">
        <section id='messages-and-users' className="row">
          <div id='messages-list'>
            {renderMessages()}
          </div>
          <div id='user-list'>
            {renderUsers()}
          </div>
        </section>
        <section id='control' className="row">
          <textarea id='new-message' value={newMessage} onChange={(e) => setNewMessage(e.target.value)}></textarea>
          <button id='send-message' onClick={addMessage}>Send</button>
        </section>
      </div>
    </div>
    </>
  );


  //__________________________________________________________________________________________________

  // useEffect(() => {
  //   getAllMessages;
  //   getAllUsers;
  // })

  // const addMessage = (message, userProfile) => {
  //   API.post(`message`, {
  //     content: message.text,
  //     user: userProfile
  //   }).then(response => {
  //     setMessages((prevMessges) => [response, ...prevMessges])
  //   })
  // }

  // const getAllMessages = () => {
  //   API.get(`message`)
  //   .then(messages => setMessages(messages.data));
  // }

  // const getAllUsers = () => {
  //   API.get(`user`)
  //   .then(users => setUsers(users.data));
  // }
  
  // const deleteMessage = (messageId) => {
  //   API.delete(`message/${messageId}`)
  //   .then(res => console.log('delete request is ', res.data));  
  // }

  // const editMessage = (newContent, messageId) => {
  //   API.patch(`message/${messageId}`)
  //   .then(req => console.log('patch request is ', req.data));
  // }

  // return (
  //   <>
  //   <div className="App"></div><div id="App">
  //     <div id='interface' className="container">
  //       <section id='messages-and-users' className="row">
  //         <div id='messages-list'>
  //           <MessagesList />
  //         </div>
  //         <div id='user-list'>
  //           <UsersList />
  //         </div>
  //       </section>
  //       <section id='control' className="row">
  //         <textarea id='new-message' value={message} onChange={(e) => setMessage(e.target.value)}></textarea>
  //         <button id='send-message' onClick={addMessage}>Send</button>
  //       </section>
  //     </div>
  //   </div>
  //   </>
  // );
}

export default App;

