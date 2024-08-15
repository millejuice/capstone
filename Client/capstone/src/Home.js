import React from 'react';
import { Link } from 'react-router-dom';
import GoogleLoginButton from './GoogleLoginButton';

function Home() {
  return (
    <div>
        <h1>구글 로그인 예제</h1>
      <GoogleLoginButton />
      <h1>Home Page</h1>
      <Link to="/chat">Login Success</Link>
    </div>
  );
}

export default Home;