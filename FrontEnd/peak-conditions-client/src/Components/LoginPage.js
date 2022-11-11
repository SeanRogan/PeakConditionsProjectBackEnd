import React, {useState, useRef, useEffect, useContext} from 'react';
import { Link } from 'react-router-dom';
import AuthContext from "./AuthProvider";
import '../index.css';
import axios from "axios";

const LOGIN_URL = "/api/login";


const LoginPage = () => {

    const { setAuth } = useContext(AuthContext);

    const userRef = useRef(null);
    const errRef = useRef(null);

    const [user, setUser] = useState("");
    const [pswd, setPswd] = useState("");
    const [errMsg, setErrMsg] = useState("");
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    }, []);

//when an error msg is up, this will remove it upon a change to username or password field
    useEffect(() => {
        setErrMsg('');
    } , [user,pswd] );

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({ user, pswd }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
            console.log(JSON.stringify(response?.data));
            //console.log(JSON.stringify(response));
            const accessToken = response?.data?.accessToken;
            const roles = response?.data?.roles;
            setAuth({ user, pswd, roles, accessToken });
            setUser('');
            setPswd('');
            setSuccess(true);
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 400) {
                setErrMsg('Error with Username or Password');
            } else if (err.response?.status === 401) {
                setErrMsg('Unauthorized');
            } else {
                setErrMsg('Login Failed');
            }
            errRef.current.focus();
        }
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>You are logged in!</h1>
                    <br />
                    <p>
                        <a href="/home">Go to Home</a>
                    </p>
                </section>
            ) : (
                <section>
                    <p ref={errRef} className={errMsg ? "errMsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Sign In</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="username">Username:</label>
                        <input
                            type="text"
                            id="username"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(changeEvent) => setUser(changeEvent.target.value)}
                            value={user}
                            required
                        />

                        <label htmlFor="password">Password:</label>
                        <input
                            type="password"
                            id="password"
                            onChange={(changeEvent) => setPswd(changeEvent.target.value)}
                            value={pswd}
                            required
                        />
                        <button>Sign In</button>
                    </form>
                    <p>
                        Need an Account?<br />
                        <span className="line">
                            {<Link to="/register"/>}
                            <a href="/register">Sign Up</a>
                        </span>
                    </p>
                </section>
            )}
        </>
    )
 //    return(
 //     <div>
 //         <h2>Log in</h2>
 //         <form>
 //             <div>
 //                 <lable>Email address</lable>
 //                 <input type="text" name="email" required />
 //             </div>
 //             <div>
 //                 <lable>Password</lable>
 //                 <input type="text" name="pswd" required />
 //             </div>
 //             <button className="submit-button" type="submit">Submit</button>
 //         </form>
 //         <footer>
 //             <p>Need an account?<Link to="/register">Sign up here</Link></p>
 //         </footer>
 //     </div>
 // );
}

export default LoginPage;
