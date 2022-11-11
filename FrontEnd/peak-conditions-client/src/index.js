import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import './index.css';
import App from './App';
import {render} from "react-dom";
import {BrowserRouter} from "react-router-dom";
import {AuthProvider} from "./Components/AuthProvider";

render(
    <BrowserRouter>
        <AuthProvider>
            <App />
        </AuthProvider>
    </BrowserRouter>,
    document.getElementById('root'));

