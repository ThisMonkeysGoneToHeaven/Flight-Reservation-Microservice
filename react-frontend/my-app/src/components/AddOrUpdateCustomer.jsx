import React, {useState, useEffect} from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import CustomerService from '../services/CustomerService';

export default function AddOrUpdateCustomer(props) {

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [emailId, setEmailId] = useState("");
  const [password, setPassword] = useState("");

  let navigate = useNavigate();
  const {id} = useParams();
  const customerId = props.useCustomerId;

    useEffect(() => {

      if(id === 'add')
          return;

      CustomerService.getCustomerById(customerId).then(res => {
          let customer = res.data;

          setFirstName(customer.firstName);
          setLastName(customer.lastName);
          setPhoneNumber(customer.phoneNumber);
          setEmailId(customer.emailId);
          setPassword(customer.password);
      });

  }, [id, props, customerId]);


  function firstNameHandler(event){
    setFirstName(event.target.value);
  }
  function lastNameHandler(event){
      setLastName(event.target.value);
  }
  function phoneNumberHandler(event){
      setPhoneNumber(event.target.value);
  }
  function emailIdHandler(event){
      setEmailId(event.target.value);
  }
  function passwordHandler(event){
    setPassword(event.target.value);
}

  function addOrUpdateCustomer(event){
    event.preventDefault();
    const customer = {
        "firstName": firstName,
        "lastName": lastName,
        "emailId": emailId,
        "phoneNumber": phoneNumber,
        "password": password
    }

    if(id === 'add'){
        CustomerService.addCustomer(customer).then(res => {
            navigate("/");
        });    
    }
    else{
        CustomerService.updateCustomer(customer, customerId).then(res => {
            navigate("/customer-homepage");
        });    
    }
}

function cancel(){
    navigate("/customer-homepage");
}


  return (
    <div>
      {/* {localStorage.getItem("customerData") == null && <h3>Something Went Wrong. Please try again!</h3>} */}
                  <div className="container">
                  <div className="row">
                      <div className="card col-md-6 offset-md-3">
                          <h3 className="text-center m-2">{id === 'add' ? 'Register' : 'Update'} Customer</h3>
                          <div className="card-body">
                              <form>

                                  <div className="form-group m-2">
                                      <label>First Name</label>
                                      <input placeholder="First Name" name="firstName" 
                                      className="form-control my-1" value={firstName} 
                                      onChange={firstNameHandler} />
                                  </div>

                                  <div className="form-group m-2">
                                      <label>Last Name</label>
                                      <input placeholder="Last Name" name="lastName" 
                                      className="form-control my-1" value={lastName} 
                                      onChange={lastNameHandler} />
                                  </div>

                                  <div className="form-group m-2">
                                      <label>Phone Number</label>
                                      <input placeholder="Phone Number" name="phoneNumber" 
                                      className="form-control my-1" value={phoneNumber} 
                                      onChange={phoneNumberHandler} disabled={id==='update'}/>
                                  </div>

                                  <div className="form-group m-2">
                                      <label>Email Address</label>
                                      <input placeholder="Email Address" name="emailId" 
                                      className="form-control my-1" value={emailId} 
                                      onChange={emailIdHandler} disabled={id==='update'}/>
                                  </div>

                                  <div className="form-group m-2">
                                      <label>Password</label>
                                      <input type="password" placeholder="Password" name="password" 
                                      className="form-control my-1" value={password} 
                                      onChange={passwordHandler} />
                                  </div>

                                  <button className='btn btn-success m-2' onClick={addOrUpdateCustomer}>Save</button> 
                                  <button className='btn btn-danger m-2' onClick={cancel}>Cancel</button> 
                              </form>
                          </div>
                      </div>
                  </div>
              </div>
    </div>
  )
}
