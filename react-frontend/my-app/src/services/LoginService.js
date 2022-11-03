import axios from 'axios';


const LOGIN_API_BASE_URL = "http://localhost:9004/api/customers/login";

const LoginService = {
    login: function(username, password){
        
        const user = {
            "emailId": username,
            "password": password
        }

        return axios.post(LOGIN_API_BASE_URL, user);
    }
};

export default LoginService;