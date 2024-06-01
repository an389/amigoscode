import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    Wrap,
    Text,
    useDisclosure
} from "@chakra-ui/react";
import { BiPlus, BiSearch } from "react-icons/bi";
import AddProductForm from "./AddProductForm";
import SearchProductForm from "./SearchProductForm";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const CreateProductDrawer = ({ fetchProducts }) => {
    const {
        isOpen: isSearchOpen,
        onOpen: onSearchOpen,
        onClose: onSearchClose
    } = useDisclosure();

    const {
        isOpen: isRegisterOpen,
        onOpen: onRegisterOpen,
        onClose: onRegisterClose
    } = useDisclosure();

    return (
        <>
            <Wrap justify={"center"} spacing={"30px"}>
                <Button
                    leftIcon={<BiSearch />}
                    colorScheme={"teal"}
                    onClick={onSearchOpen}
                >
                    Search
                </Button>
                <Text marginLeft={20} marginRight={20}>Bid Home</Text>
                <Button
                    leftIcon={<BiPlus />}
                    colorScheme={"teal"}
                    onClick={onRegisterOpen}
                >
                    Register Product
                </Button>
            </Wrap>

            {/* Drawer for Search */}
            <Drawer isOpen={isSearchOpen} onClose={onSearchClose} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Search</DrawerHeader>
                    <DrawerBody>
                        <SearchProductForm/>
                    </DrawerBody>
                    <DrawerFooter>
                        <Button
                            leftIcon={<CloseIcon />}
                            colorScheme={"teal"}
                            onClick={onSearchClose}>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>

            {/* Drawer for Register Product */}
            <Drawer isOpen={isRegisterOpen} onClose={onRegisterClose} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Register Product</DrawerHeader>
                    <DrawerBody>
                        <AddProductForm
                            onSuccess={fetchProducts}
                        />
                    </DrawerBody>
                    <DrawerFooter>
                        <Button
                            leftIcon={<CloseIcon />}
                            colorScheme={"teal"}
                            onClick={onRegisterClose}>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
}

export default CreateProductDrawer;
