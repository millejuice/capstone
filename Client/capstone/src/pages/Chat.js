import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Stomp } from '@stomp/stompjs';
import '../styles/Chat.css';
import axios from 'axios';

function Chat() {
  const location = useLocation();
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const [chatList, setChatList] = useState([]);
  
  const roomId = 1; 
  const sender = location.state?.name || "테스트유저"; 
  const senderEmail = location.state?.email || "user@example.com"; 

  useEffect(() => {
    const fetchChatList = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/chat/${roomId}`);
            console.log('Chat List:', response.data);
            setChatList(response.data);
            loadChat(response.data); // Load chat immediately after fetching
        } catch (error) {
            console.error('Error fetching the chat list:', error);
        }
    };

    fetchChatList();
}, [roomId]);

const connect = () => {
  const socket = new WebSocket("ws://localhost:8080/capstone");
  const stomp = Stomp.over(socket);

  stomp.connect({}, (frame) => {
      setConnected(true);
      console.log('Connected: ' + frame);

      stomp.subscribe(`/sub/${roomId}`, (chatMessage) => {
          showChat(JSON.parse(chatMessage.body)); 
      });

      loadChat(chatList);
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
        const url = `/pub/${roomId}`;
        const messageBody = JSON.stringify({
            sender: sender,
            message: inputValue,
            senderEmail: senderEmail,
        });

        // Send the message through the WebSocket
        stompClient.send(url, {}, messageBody);

        // Immediately update the UI with the new message
        showChat({
            sender: sender,
            senderEmail: senderEmail,
            message: inputValue,
        });

        console.log('Sent: ' + inputValue);
        setInputValue(''); // Clear the input field
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

    // Update the messages state to include the new message
    setMessages((prevMessages) => [...prevMessages, newMessage]);

    scrollToBottom(); // Ensure the chat scrolls to show the latest message
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

  return (
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
        <div>
            <button onClick={connected ? disconnect : connect}>
                {connected ? 'Disconnect' : 'Connect'}
            </button>
        </div>
    </div>
);

}

export default Chat;
