
import './App.css';
import {useState} from 'react';
import Header from './components/Header'
import Footer from './components/Footer'
import HomePage from './components/HomePage'
import AddOrUpdateCustomer from './components/AddOrUpdateCustomer'
import CustomerHomepage from './components/CustomerHomepage'
import ManageReservations from './components/ManageReservations';
import CreateReservation from './components/CreateReservation';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'


function App() {

  const [customerEmail, setCustomerEmail] = useState(null);
  const pullCustomerEmail = (data) => {
    setCustomerEmail(data);
  }

  const [customerId, setCustomerId] = useState(null);
  const pullCustomerId = (data) => {
    setCustomerId(data);
  }

  return (
    <div>
        <Router>
            <Header />
              <div className='container mt-5'>
                <Routes>
                  <Route path="/" exact element={<HomePage pullCustomerEmailFunction={pullCustomerEmail}/>} />
                  <Route path="/customer-homepage" element={ <CustomerHomepage useCustomerEmail={customerEmail} pullCustomerIdFunction={pullCustomerId}/> } />
                  <Route path="/add-update-customer/:id" element={ <AddOrUpdateCustomer useCustomerId={customerId} /> } />
                  <Route path="/manage-reservations" element={ <ManageReservations useCustomerId={customerId} /> } />
                  <Route path="/create-reservation" element={ <CreateReservation useCustomerId={customerId} /> } />
                  {/* <Route path="/add-update-customer/:id" element={ <AddOrUpdateCustomer/> } />
                  <Route path="/search-customers/" element={ <SearchComponent/> } /> */}
                </Routes>
              </div>
            <Footer/>
        </Router>
    </div>
  );
}

export default App;