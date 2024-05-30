import React from 'react'
import ReactDOM from 'react-dom/client'
import Customer from './Customer.jsx'
import {ChakraProvider} from '@chakra-ui/react'
import {createStandaloneToast} from '@chakra-ui/toast'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Signup from "./components/signup/Signup";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import './index.css'
import Home from "./Home.jsx";
import Settings from "./Settings";
import CustomerProfile from "./components/customer/CustomerProfile";

const {ToastContainer} = createStandaloneToast();

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/signup",
        element: <Signup/>
    },
    {
        path: "dashboard",
        element: <ProtectedRoute><Home/></ProtectedRoute>
    },
    {
        path: "dashboard/customers",
        element: <ProtectedRoute><Customer/></ProtectedRoute>
    },
    {
        path: "dashboard/:id",
        element: <ProtectedRoute><Home/></ProtectedRoute>
    },
    {
        path: "dashboard/settings",
        element: <ProtectedRoute><Settings/></ProtectedRoute>
    },
    {
        path: "dashboard/customers/:userName",
        element: <ProtectedRoute><CustomerProfile/></ProtectedRoute>
    }
])
console.log('API URL:', process.env.REACT_APP_API_URL);
ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router}/>
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )
