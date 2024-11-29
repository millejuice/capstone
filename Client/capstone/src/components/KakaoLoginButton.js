import React from 'react';

function KakaoLoginButton() {
  const client_id = "bf580ec08aba3a8a9be717d0bac903f7"; // 여기에 실제 카카오 앱의 Client ID를 입력하세요.
  const redirect_uri = "http://localhost:8080/callback"; // 여기에 실제 Redirect URI를 입력하세요.

  const handleLogin = () => {
    const kakaoAuthUrl = `http://localhost:8080/oauth2/authorization/kakao`;
    window.location.href = kakaoAuthUrl; // 해당 URL로 이동
  };

  return (
    <button onClick={handleLogin}>
      Kakao Login
    </button>
  );
}

export default KakaoLoginButton;
