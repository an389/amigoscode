// AddRatingForm.jsx
import React, { useState } from 'react';
import { Button, Input, Textarea, Stack, useToast } from "@chakra-ui/react";
import {registerRating} from "../../services/client";
import {useAuth} from "../context/AuthContext";

const AddRatingForm = ({ productId, closeModal, idRatedCustomer }) => {
    const [grade, setGrade] = useState('');
    const [comment, setComment] = useState('');
    const toast = useToast();
    const {customer} = useAuth();

    const handleSubmit = async () => {
        const ratingData = {
            email: customer.username,
            productId: productId,
            idRatedCustomer: idRatedCustomer,
            grade: grade,
            comment: comment
        };
        if (ratingData.grade === '' || ratingData.comment === '') {
            toast({
                title: "Error",
                description: "Please fill in all fields.",
                status: "error",
                duration: 9000,
                isClosable: true,
            });
        } else try {
            registerRating(ratingData);
            toast({
                title: "Rating added.",
                description: "Your rating has been added successfully.",
                status: "success",
                duration: 9000,
                isClosable: true,
            });
            closeModal();
        }catch (err) {
            toast({
                title: "Error",
                description: err.response.data.message,
                status: "error",
                duration: 9000,
                isClosable: true,
            });
        }
    };

    return (
        <Stack spacing={4}>
            <Input
                placeholder="Grade"
                value={grade}
                onChange={(e) => setGrade(e.target.value)}
            />
            <Textarea
                placeholder="Comment"
                value={comment}
                onChange={(e) => setComment(e.target.value)}
            />
            <Button colorScheme="teal" onClick={handleSubmit}>
                Submit
            </Button>
        </Stack>
    );
};

export default AddRatingForm;
