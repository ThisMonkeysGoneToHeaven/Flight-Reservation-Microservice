import React from 'react'
import Login from './Login';

export default function HomePage(props) {

    const titleStyle = {
        "display" :"flex",
        "height": "20vh",
        "justifyContent": "center",
        "alignItems": "center",
        "fontSize": "4em",
        "textShadow": "2px 2px #ff0000",
        "fontWeight": "bolder"
    };

    const divStyle = {
        "display" :"flex",
        "marginBottom": "30px",
    }


    return (
    <div>
        <h1 style={titleStyle}>Flight Reservation System</h1>
        <div style={divStyle}>
            <Login pullCustomerEmailFunction={props.pullCustomerEmailFunction} />
        </div>
    </div>
    )
}
