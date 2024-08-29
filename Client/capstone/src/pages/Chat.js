import React, { useState, useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';
import { Stomp } from '@stomp/stompjs';
import '../styles/Chat.css';
import axios from 'axios';
import Cookies from 'js-cookie'; 

function Chat() {
  const location = useLocation();
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [inputValue, setInputValue] = useState('');
  const isConnectedRef = useRef(false); // Track if already connected

  const roomId = 1; 
  const sender = location.state?.name || "테스트유저"; 
  const senderEmail = location.state?.email || "user@example.com"; 
  const token = Cookies.get('Capstone');

  const handleButtonClick = () => {
    // Replace with your backend API endpoint
    const apiUrl = 'http://localhost:8080/user'; 
  
    // Set the Authorization token
    const token = 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.fgGGLr6k5WoCk3NcA2ItPuBJRlSeJJEtZyvMoN5IFSY';
  
    axios.post(apiUrl, {
      name: 'test user',
      email: 'test@test.com'
    }, {
      headers: {
        'Authorization': token // Use Authorization header
      }
    })
    .then(response => {
      // Handle success
      console.log('User created:', response.data);
    })
    .catch(error => {
      // Handle error
      console.error('There was an error creating the user!', error);
    });
  };
  


  useEffect(() => {
    const fetchChatList = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/chat/${roomId}`);
          //   const response = await axios.get(`http://localhost:8080/chat/${roomId}`, {
          //     headers: {
          //         'Authorization': `Bearer ${token}`, 
          //     },
          // });
            console.log('Chat List:', response.data);
            const chatData = response.data.map((chat) => ({
                type: chat.senderEmail === senderEmail ? 'own' : 'other',
                message: chat.message,
                sender: chat.sender,
            }));
            setMessages(chatData);
        } catch (error) {
            console.error('Error fetching the chat list:', error);
        }
    };

    fetchChatList();

    // Only connect if not already connected
    if (!isConnectedRef.current) {
        connect(); // Connect to WebSocket if not connected
        isConnectedRef.current = true; // Mark as connected
    }

    return () => {
      disconnect(); // Disconnect when the component unmounts
    };
  }, [roomId]); // Only re-run the effect when roomId changes

  const connect = () => {
    const socket = new WebSocket("ws://localhost:8080/capstone");
    const stomp = Stomp.over(socket);

    stomp.connect({}, (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);

        stomp.subscribe(`/sub/${roomId}`, (chatMessage) => {
            showChat(JSON.parse(chatMessage.body)); // Update the UI when a message is received
        });
    });

    setStompClient(stomp);
  };

  const disconnect = () => {
    if (stompClient !== null) {
      stompClient.disconnect();
      console.log('Disconnected');
    }
    setConnected(false);
  };

  const sendChat = () => {
    if (inputValue !== '') {
        const url = `/pub/${roomId}`;
        const messageBody = JSON.stringify({
            sender: sender,
            message: inputValue,
            senderEmail: senderEmail,
        });

        stompClient.send(url, {}, messageBody);

        showChat({
            sender: sender,
            senderEmail: senderEmail,
            message: inputValue,
        });

        console.log('Sent: ' + inputValue);
        setInputValue(''); // Clear the input field
    }
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
            <button onClick={handleButtonClick}>
      Create User
    </button>
        </div>
    </div>
  );
}

export default Chat;