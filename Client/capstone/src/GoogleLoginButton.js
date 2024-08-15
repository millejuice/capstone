import { GoogleLogin } from "@react-oauth/google";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { useNavigate } from "react-router-dom";

const GoogleLoginButton = () => {
    const clientId = '941001632953-ja7dpvnsusm7r287su9top3otp939dla.apps.googleusercontent.com';
    const navigate = useNavigate();

    const handleLoginSuccess = (response) => {
        const userEmail = response.profileObj.email; // 사용자 이메일 추출
        console.log(response);

        // 로그인 성공 후 이메일을 Chat 컴포넌트로 전달
        navigate('/chat', { state: { email: userEmail } });
    };

    const handleLoginFailure = (error) => {
        console.log(error);
    };

    return (
        <GoogleOAuthProvider clientId={clientId}>
            <GoogleLogin
                onSuccess={handleLoginSuccess}
                onFailure={handleLoginFailure}
            />
        </GoogleOAuthProvider>
    );
};

export default GoogleLoginButton;
