import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import {BiSearch} from "react-icons/bi";
import SearchCustomerForm from "./SearchCustomerForm";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const CreateCustomerDrawer = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button
            leftIcon={<BiSearch/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            Serach customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Search customer</DrawerHeader>

                <DrawerBody>
                    <SearchCustomerForm/>
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"teal"}
                        onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>

}

export default CreateCustomerDrawer;