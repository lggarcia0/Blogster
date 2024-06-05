import axios from 'axios'
import React, { ChangeEvent, useState } from 'react'
import { useNavigate } from 'react-router-dom'

function RegistrationPage() {
    const [formData, setFormData] = useState({
        email:'',
        username:'',
        password:''
    })
    const [message, setMessage] = useState('')
    const navigate = useNavigate()
    const handleRegister = async() =>{
        if (!formData.email.trim() || !formData.username.trim() || !formData.password.trim) {
            setMessage("Fill out all forms!")
            return
        }
        try {
            let response = await axios.post(`${process.env.REACT_APP_API_URL}/users`, formData, {
                withCredentials:true, headers:{'Content-Type':'application/json'}
            });
            if (response.status === 201) {
                setMessage("Successful registration!");
                navigate('/login');
            }
        }
        catch(error:any) {
            if(error.response && error.response.status === 409) {
                setMessage('Username already taken');
            } else {
                setMessage('Registration failed: ' + (error.response.data.message ||'Unknown error'));
            }
            
        }
    }
    const handleUserInfo = (e:ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setFormData(prevState => ({
            ...prevState, [name]:value
        }))
    }

    return (
        <div>
            <p>{message}</p>
            <form>
                <label>
                    Email:
                    <input type="email" name="email" onChange={handleUserInfo}/>
                </label>
                <label>
                    Username:
                    <input type="text" name="username" onChange={handleUserInfo}/>
                </label>
                <label>
                    Password:
                    <input type ="password" name="password" onChange={handleUserInfo}/>
                </label>
                <input type="button" onClick={handleRegister}value="Submit"/>
                
            </form>
            
            
        </div>
    )
}

export default RegistrationPage
