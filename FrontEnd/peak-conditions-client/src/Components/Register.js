import React, {useState, useRef, useEffect} from 'react';

import { faCheck, faTimes, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default Register

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PSWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/register';

const Register = (props) => {
    const userRef = userRef(null);
    const errRef = errRef(null);

    const [user, setUser] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [pswd, setPswd] = useState('');
    const [validPswd, setValidPswd] = useState(false);
    const [pswdFocus, setPswdFocus] = useState(false);

    const [matchPswd, setMatchPswd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    } , [])
    useEffect(() => {
        setValidName(USER_REGEX.test(user))
    }, [user])
    useEffect(() => {
        setValidPswd(PSWD_REGEX.test(pswd))
        setValidMatch(pswd === matchPswd)
    } , [pswd, matchPswd])
    useEffect(() => {
        setErrMsg('')
    } , [user, pswd, matchPswd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const userTest = USER_REGEX.test(user);
        const pswdTest = PSWD_REGEX.test(pswd);
        if(!userTest || !pswdTest) {
            setErrMsg('invalid user name or password.');
            return;
        }
        try {

            const response = await axios.post(REGISTER_URL,
                JSON.stringify({user,pswd}),
                {
                    headers: {'Content-type': 'application/json'},
                    withCredentials:true
                });

            setSuccess(true);
            setUser('');
            setPswd('');
            setMatchPswd('');


        } catch(err) {
            if(!err.response) {
                setErrMsg('No response from server!')
            } else if(err.response?.status === 409){
                setErrmsg('An account with that user name already exists.')
            } else {
                setErrMsg('Registration failed, please try again or contact support if the problem persists.')
            }
            errRef.current.focus();
        }
    }




    return (
        <>
            {success ? (
                <section>
                    <h1>Success!</h1>
                    <p>
                        <a href="/login">Sign In</a>
                    </p>
                </section>
            ) : (
                <section>
                    <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                    <h1>Register</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="username">
                            Username:
                            <FontAwesomeIcon icon={faCheck} className={validName ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validName || !user ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="text"
                            id="username"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(e) => setUser(e.target.value)}
                            value={user}
                            required
                            aria-invalid={validName ? "false" : "true"}
                            aria-describedby="uidnote"
                            onFocus={() => setUserFocus(true)}
                            onBlur={() => setUserFocus(false)}
                        />
                        <p id="uidnote" className={userFocus && user && !validName ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            4 to 24 characters.<br />
                            Must begin with a letter.<br />
                            Letters, numbers, underscores, hyphens allowed.
                        </p>


                        <label htmlFor="password">
                            Password:
                            <FontAwesomeIcon icon={faCheck} className={validPswd ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validPswd || !pswd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="password"
                            onChange={(e) => setPswd(e.target.value)}
                            value={pswd}
                            required
                            aria-invalid={validPswd ? "false" : "true"}
                            aria-describedby="pswd_note"
                            onFocus={() => setPswdFocus(true)}
                            onBlur={() => setPswdFocus(false)}
                        />
                        <p id="pswd_note" className={pswdFocus && !validPswd ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            8 to 24 characters.<br />
                            Must include uppercase and lowercase letters, a number and a special character.<br />
                            Allowed special characters: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                        </p>


                        <label htmlFor="confirm_pswd">
                            Confirm Password:
                            <FontAwesomeIcon icon={faCheck} className={validMatch && matchPswd ? "valid" : "hide"} />
                            <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPswd ? "hide" : "invalid"} />
                        </label>
                        <input
                            type="password"
                            id="confirm_pswd"
                            onChange={(e) => setMatchPswd(e.target.value)}
                            value={matchPswd}
                            required
                            aria-invalid={validMatch ? "false" : "true"}
                            aria-describedby="confirm_note"
                            onFocus={() => setMatchFocus(true)}
                            onBlur={() => setMatchFocus(false)}
                        />
                        <p id="confirm_note" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Must match the first password input field.
                        </p>

                        <button disabled={!validName || !validPswd || !validMatch ? true : false}>Sign Up</button>
                    </form>
                    <p>
                        Already registered?<br />
                        <span className="line">
                            {/*put router link here*/}
                            <a href="#">Sign In</a>
                        </span>
                    </p>
                </section>
            )}
        </>
    );
}