import React, {useEffect, useState, useRef} from 'react';
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import './App.css';
import Sidebar from './Sidebar.js';

function App() {
  const [username, setUsername] = useState('');
  const [messageContent, setMessageContent] = useState('');
  const [receivedMessages, setReceivedMessages] = useState([]);
  const stompClientRef = useRef(null);
  const [chatRooms] = useState(['General', 'Support', 'Random']);
  const [chatName, setChatName] = useState('General');

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        console.log('Connected');
        stompClientRef.current = client;

        client.subscribe('/topic/messages', (messageOutput) => {
          const message = JSON.parse(messageOutput.body);
          setReceivedMessages((prevMessages) => [...prevMessages, message]);
        });
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame);
      },
    });

    client.activate();

    return () => {
      if (client && client.active) {
        client.deactivate();
      }
    };
  }, []);

  const sendMessage = () => {
    if (stompClientRef.current && stompClientRef.current.connected) {
      const message = {
        content: messageContent,
        username: username || 'anonymous',
        chatName: chatName,
      };
      stompClientRef.current.publish({
        destination: '/app/messages/send',
        body: JSON.stringify(message),
      });
      setMessageContent('');
    } else {
      console.warn('STOMP client not connected.');
    }
  };

  const formatTimestamp = (timestamp) => {
    if (!timestamp) return '';
    const date = new Date(timestamp);
    return date.toLocaleString();
  };

  return (
    <div className="chat-container">
      <Sidebar
        chats={chatRooms}
        selectedChat={chatName}
        onSelectChat={(room) => setChatName(room)}
      />

      <div className="chat-main">
        <h1>{chatName}</h1>

        <label htmlFor="usernameInput">Username:</label>
        <input
          type="text"
          id="usernameInput"
          placeholder="Enter your name"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <br/>

        <label htmlFor="messageInput">Message:</label>
        <input
          type="text"
          id="messageInput"
          placeholder="Type your message..."
          value={messageContent}
          onChange={(e) => setMessageContent(e.target.value)}
        />
        <button onClick={sendMessage}>Send</button>

        <ul id="messages">
          {receivedMessages
            .filter((msg) => msg.chatName === chatName)
            .map((msg, index) => {
              const isOwnMessage = msg.username === (username || 'anonymous');
              return (
                <li
                  key={index}
                  className={isOwnMessage ? 'own-message' : 'other-message'}
                  title={formatTimestamp(msg.timestamp)}
                >
                  <strong>{msg.username}</strong>: {msg.content}
                </li>
              );
            })}
        </ul>
      </div>
    </div>
  );
}

export default App;