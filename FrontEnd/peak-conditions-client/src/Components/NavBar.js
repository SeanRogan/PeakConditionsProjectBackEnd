import { ReactComponent as CaretIcon} from "../icons/caret.svg";
import { ReactComponent as MenuIcon} from "../icons/menu.svg";
import { ReactComponent as SearchIcon} from "../icons/search.svg";
import { ReactComponent as LogoutIcon} from "../icons/logout.svg";
import { ReactComponent as SettingsIcon} from "../icons/setting.svg";
import { ReactComponent as ProfileIcon} from "../icons/account.svg";
import React, {useState} from "react";
import '../index.css'
function Navbar(props) {
    return(
    <nav className='navbar'>
        <ul className='navbar-nav'>
            {props.children}
            <NavItem icon={<MenuIcon/>}>
            <DropdownMenu></DropdownMenu>
            </NavItem>
        </ul>
    </nav>
    );
}
function NavItem(props) {
    //useState
    const [open, setOpen] = useState(false);

    return(
      <li>
          <a href="#" className="icon-button" onClick={() => setOpen(!open)}>
              {props.icon}
          </a>
          {open && props.children}
      </li>
    );
}

function DropdownMenu() {
    return(
        <div className='dropdown'>
            <DropdownItem
            leftIcon={<ProfileIcon/>}
            rightIcon={<CaretIcon/>}>
                <div className='dropdown-menu-item'>
                Profile
        </div>
            </DropdownItem>
            <DropdownItem
                leftIcon={<SettingsIcon/>}
                rightIcon={<CaretIcon/>}>
                <div className='dropdown-menu-item'>
                Settings
                </div>
            </DropdownItem>
            <DropdownItem
                leftIcon={<LogoutIcon/>}>
                <div className='dropdown-menu-item'>
                Log Out
                </div>
            </DropdownItem>

        </div>
    );
}

function DropdownItem(props) {
    return(
        <a href='#' className="menu-item">
            <span className="icon-button">{props.leftIcon}</span>
            {props.children}
            <span className="icon-button" id="icon-right">{props.rightIcon}</span>
        </a>
    );
}

export default Navbar;