import './App.css';
import Navbar from './Components/NavBar'
import LandingPage from "./Components/LandingPage";
import EditProfilePage from "./Components/EditProfilePage";
import LoggedInPage from "./Components/LoggedInPage";
import LoginPage from "./Components/LoginPage";
import Register from "./Components/Register";
import CreateProfilePage from "./Components/CreateProfilePage";
const App = () => {
    let PageComponent;
    switch(window.location.pathname) {
        case "/" :
            PageComponent = LandingPage
            break
        case "/home" :
            PageComponent = LandingPage
            break
        case "" :
            PageComponent = LoginPage
            break
        case "" :
            PageComponent = Register
            break
        case "" :
            PageComponent = CreateProfilePage
            break
        case "" :
            PageComponent = EditProfilePage
            break
        case "" :
            PageComponent = LoggedInPage
            break


    }
  return (
    <div className="App">
      <Navbar/>
    </div>
  );
}

export default App;
