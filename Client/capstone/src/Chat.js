import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Stomp } from '@stomp/stompjs';
import './Chat.css';

function Chat() {
  const location = useLocation();
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  
  const roomId = 1; // 예시로 roomId를 1로 설정했습니다. 필요에 따라 변경하세요.
  const chatList = []; // 서버에서 가져온 채팅 리스트를 여기에 설정하세요.
  const sender = "테스트유저"; // 사용자 이름 예시입니다.
  const senderEmail = location.state?.email || "user@example.com"; // 전달된 이메일 사용

  const connect = () => {
    const socket = new WebSocket("ws://localhost:8080/capstone");
    const stomp = Stomp.over(socket);

    stomp.connect({}, (frame) => {
      setConnected(true);
      console.log('Connected: ' + frame);
      loadChat(chatList);  // 저장된 채팅 불러오기

      stomp.subscribe(`/sub/${roomId}`, (chatMessage) => {
        showChat(JSON.parse(chatMessage.body));
      });
    });

    setStompClient(stomp);
  };

  const disconnect = () => {
    if (stompClient !== null) {
      stompClient.disconnect();
    }
    setConnected(false);
    console.log('Disconnected');
  };

  const sendChat = () => {
    if (inputValue !== '') {
      stompClient.send(`/pub/${roomId}`, {}, JSON.stringify({
        sender: sender,
        message: inputValue,
        senderEmail: senderEmail,
      }));
      console.log('Sent: ' + inputValue);
      setInputValue('');
    }
  };

  const loadChat = (chatList) => {
    if (chatList !== null) {
      const newMessages = chatList.map((chat) => {
        if (chat.senderEmail === senderEmail) {
          return { type: 'own', message: chat.message, sender: chat.sender };
        } else {
          return { type: 'other', sender: chat.sender, message: chat.message };
        }
      });
      setMessages(newMessages);
    }
    scrollToBottom();
  };

  const showChat = (chatMessage) => {
    const newMessage = {
      type: chatMessage.senderEmail === senderEmail ? 'own' : 'other',
      sender: chatMessage.sender,
      message: chatMessage.message,
    };
    setMessages((prevMessages) => [...prevMessages, newMessage]);
    scrollToBottom();
  };

  const scrollToBottom = () => {
    const chatContainer = document.querySelector('.chat-container');
    if (chatContainer) {
      chatContainer.scrollTop = chatContainer.scrollHeight;
    }
  };

  useEffect(() => {
    connect();
    return () => disconnect();
  }, []);

  const toggleModal = () => {
    const modal = document.querySelector('.modal');
    if (modal) {
      modal.classList.toggle('show-modal');
    }
  };

  return (
    <div>
      <div>
        <button onClick={connected ? disconnect : connect}>
          {connected ? 'Disconnect' : 'Connect'}
        </button>
      </div>
      <div id="conversation" style={{ display: connected ? 'block' : 'none' }}>
        <div className="chat-container">
          {messages.map((msg, index) => (
            <div
              key={index}
              className={msg.type === 'own' ? 'chatting_own' : 'chatting_other'}
            >
              {msg.type === 'other' && <strong>[{msg.sender}] </strong>}
              {msg.message}
            </div>
          ))}
        </div>
        <input
          type="text"
          id="message"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
        />
        <button onClick={sendChat}>Send</button>
      </div>
      <div className="modal">
        <div className="close-button" onClick={toggleModal}>Close</div>
        {/* 모달 내용 */}
      </div>
      <button className="trigger" onClick={toggleModal}>Open Modal</button>
    </div>
  );
}

export default Chat;
