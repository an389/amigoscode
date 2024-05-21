import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {Text} from "@chakra-ui/react";

const Home = () => {

    return (
        <SidebarWithHeader>
            <Text fontSize={"6xl"}>Bid on these</Text>
        </SidebarWithHeader>
    )
}

export default Home;