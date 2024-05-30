import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Flex, Text,
    useDisclosure, Wrap
} from "@chakra-ui/react";
import CreateCustomerForm from "../shared/CreateCustomerForm.jsx";
import {BiPlus, BiSearch} from "react-icons/bi";
import AddProductForm from "./AddProductForm";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const CreateProductDrawer = ({fetchProducts}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Wrap justify={"center"} spacing={"30px"}>
            <Button
                leftIcon={<BiSearch/>}
                colorScheme={"teal"}
                onClick={onOpen}
            >
                Serach
            </Button>
            <Text marginLeft={20} marginRight={20}>Bid Home</Text>
            <Button
                leftIcon={<BiPlus/>}
                colorScheme={"teal"}
                onClick={onOpen}
            >
                Register Product
            </Button>
        </Wrap>

        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Register Product</DrawerHeader>

                <DrawerBody>
                    <AddProductForm
                        onSuccess={fetchProducts}
                    />
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

export default CreateProductDrawer;