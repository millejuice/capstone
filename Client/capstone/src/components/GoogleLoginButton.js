import { GoogleLogin } from "@react-oauth/google";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from 'jwt-decode';

// const GoogleLoginButton = () => {
//     const clientId = '126282479173-9ua9tv82hou647tmdi67j1hgipdm821n.apps.googleusercontent.com';
//     const navigate = useNavigate();

//     const handleLoginSuccess = (credentialResponse) => {
//         const decodedToken = jwtDecode(credentialResponse.credential);
//         console.log("Decoded Token:", decodedToken); // Log the entire token
    
//         const userEmail = decodedToken.email;
//         const name = decodedToken.name;
//         console.log("Extracted Email:", userEmail);
//         // 로그인 성공 후 이메일을 Chat 컴포넌트로 전달
//         navigate('/chat', { state: { email: userEmail,name:name } });
//     };

//     const handleLoginFailure = (error) => {
//         console.log(error);
//     };

//     return (
//         <GoogleOAuthProvider clientId={clientId}>
//             <GoogleLogin
//                 onSuccess={handleLoginSuccess}
//                 onFailure={handleLoginFailure}
//             />
//         </GoogleOAuthProvider>
//     );
// };

// export default GoogleLoginButton;

const GoogleLoginButton = () => {
    const handleLogin = () => {
      // 구글 로그인 페이지로 리디렉션
      window.location.href = `http://localhost:8080/oauth2/authorization/google`;
    };
  
    return (
      <button onClick={handleLogin}>구글 로그인</button>
    );
  };
  
  export default GoogleLoginButton;