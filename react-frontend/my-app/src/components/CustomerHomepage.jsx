import React, { useState } from 'react'
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CustomerService from '../services/CustomerService';
export default function CustomerHomepage(props) {

  const user_email = props.useCustomerEmail;
  
  // for navigation
  let navigate = useNavigate();

  // fetch user's details from his email address
  const [customerData, setCustomerData] = useState(null);

  useEffect(() => {
    CustomerService.getCustomerByEmail(user_email).then(res => {
      setCustomerData(res.data);
      // passing props to parent App.js
      props.pullCustomerIdFunction(res.data["id"]);
    });
  }, [user_email, props, customerData]);

  const navigateToManageReservations = function(){
    navigate("/manage-reservations");
  }

  const navigateToNewReservation = function(){
    navigate("/create-reservation");
  }

  const navigateToUpdateCustomerProfile = function(){
    navigate("/add-update-customer/update");
  }


  const contentStyle = {
    display: "flex",
    height: "300px",
    justifyContent: "space-between",
    flexDirection: "column",
    width: "92%",
    alignItems: "center"
  }

  const boxStyle = {
    height: "75px",
    width: "275px"
  }


  return (
    <div>
      
      {
        !customerData && <h2 className='text-center mt-5'>Please Wait While We Load Your Details!</h2>
      }

      {customerData && 
        <div>
          <h3 className='text-center'>Hi {customerData["firstName"]}!</h3>
          <div className='m-5 container' style={contentStyle}>
            
            <div className="row" style={boxStyle}>
              <button type="button" className="btn btn-lg btn-primary" onClick={navigateToManageReservations}>Manage Reservations</button>
            </div>

            <div className="row" style={boxStyle}>
            <button type="button" className="btn btn-lg btn-success" onClick={navigateToNewReservation}>Create Reservation</button>
            </div>

            <div className="row" style={boxStyle}>
            <button type="button" className="btn btn-lg btn-warning" onClick={navigateToUpdateCustomerProfile}>Update Profile Details</button>
            </div>
          
          </div>
      </div>
      }
      
    </div>
  )
}
