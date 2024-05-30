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
import UpdateProductForm from "./UpdateProductForm.jsx";
import { useNavigate, useLocation } from "react-router-dom";
import ProductDetail from "./ProductDetail";

const CloseIcon = () => "x";

const DetailProductDrawer = ({ fetchCustomers, initialValues, productId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const navigate = useNavigate();
    const location = useLocation();

    const openDrawer = () => {
        const newUrl = `${location.pathname}/${productId}`;
        navigate(newUrl);
        onOpen();
    };

    const closeDrawer = () => {
        const parts = location.pathname.split("/");
        const newPathname = parts.slice(0, parts.length - 1).join("/");
        navigate(newPathname);
        onClose();
    };

    console.log("PRODUCT ID " + productId);
    return (
        <>
            <Button
                bg={'gray.200'}
                color={'black'}
                rounded={'full'}
                _hover={{
                    transform: 'translateY(-2px)',
                    boxShadow: 'lg'
                }}
                onClick={openDrawer}
            >
                Details
            </Button>
            <Drawer isOpen={isOpen} onClose={closeDrawer} size={"xl"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Details product</DrawerHeader>

                    <DrawerBody>
                        <ProductDetail/>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            leftIcon={<CloseIcon />}
                            colorScheme={"teal"}
                            onClick={closeDrawer}
                        >
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
};

export default DetailProductDrawer;
