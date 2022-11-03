import {useNavigate} from 'react-router-dom';

function Header(){

    const navigate = useNavigate();
    const handleLogout = function(){
        sessionStorage.setItem("loginStatus", false);
        navigate("/");
    }

    return(
        <div>
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                <div><a href="/" className="navbar-brand px-3">Flight Reservation System</a></div>
                {
                    sessionStorage.getItem("loginStatus") === "false" ? <span/> :
                    <ul className="nav navbar-nav ms-auto">
                        <li>
                            <button className="btn btn-light btn-sm mx-3" onClick={handleLogout}>Logout</button>
                        </li>
                    </ul>
                }
                </nav>
            </header>
        </div>
    );
}

export default Header;