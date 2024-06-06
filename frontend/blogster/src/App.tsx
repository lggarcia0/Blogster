import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import RegistrationPage from './components/pages/RegistrationPage';

function App() {
  return (
    <div className="content">
      <BrowserRouter>
        <Routes>
          <Route path="/register" element={<RegistrationPage/>}/>
        </Routes>
      </BrowserRouter>  
    </div>
  );
}

export default App;
