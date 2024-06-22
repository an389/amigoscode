import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton
} from "@chakra-ui/react";
import { useState } from "react";
import UpdateProductForm from "./UpdateProductForm.jsx";
import { useNavigate, useLocation } from "react-router-dom";
import ProductDetail from "./ProductDetail";
import AddRatingForm from "./AddRatingForm";

const CloseIcon = () => "x";

const DetailProductDrawer = ({ fetchCustomers, initialValues, productId }) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const {
        isOpen: isModalOpen,
        onOpen: onModalOpen,
        onClose: onModalClose
    } = useDisclosure();
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

    const handleAddRatingClick = () => {
        onModalOpen();
    };

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
                    <DrawerHeader>
                        <>
                            Details product
                            <Button
                                marginLeft={29}
                                leftIcon={<CloseIcon />}
                                colorScheme={"teal"}
                                onClick={handleAddRatingClick}
                            >
                                AddRating
                            </Button>
                        </>
                    </DrawerHeader>

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
            <Modal isOpen={isModalOpen} onClose={onModalClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Add Rating</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <AddRatingForm productId={productId} closeModal={onModalClose} idRatedCustomer = {initialValues.sellerId}/>
                    </ModalBody>

                    <ModalFooter>
                        <Button colorScheme="teal" mr={3} onClick={onModalClose}>
                            Close
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default DetailProductDrawer;
