import {Text} from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SideBar";
import UpdateCustomerForm from "./components/customer/UpdateCustomerForm";
import {useState} from "react";
import {useAuth} from "./components/context/AuthContext";
import {getCustomer} from "./services/client";

const Settings = () => {
    const [customerId, setCustomerId] = useState();
    const {customer} = useAuth()
    const username = customer?.username;

    console.log(username)
    return (
        <SidebarWithHeader>
            <UpdateCustomerForm/>
        </SidebarWithHeader>)
}

export default Settings;