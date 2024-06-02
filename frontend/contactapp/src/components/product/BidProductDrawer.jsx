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
import {BiPlus} from "react-icons/bi";
import AddBid from "./AddBid";

const CloseIcon = () => "x";

const BidProductDrawer = ({ fetchProducts, initialValues, productId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            colorScheme={"teal"}
            rounded={'full'}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
            onClick={onOpen}
        >
            BID
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Bid as much as you can!</DrawerHeader>

                <DrawerBody>
                    <AddBid
                        fetchCustomers={fetchProducts}
                        initialValues={initialValues}
                        productId={productId}
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

export default BidProductDrawer;