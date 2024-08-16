import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './Home';
import LoginSuccess from './LoginSuccess';
import Chat from './Chat';

function App() {
  return (
    <div>
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/loginSuccess" element={<LoginSuccess />} />
        <Route path="/chat" element={<Chat />} />
      </Routes>
    </Router>
    </div>
  );
}

export default App;