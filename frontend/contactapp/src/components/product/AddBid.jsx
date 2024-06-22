import { Form, Formik, useField } from 'formik';
import * as Yup from 'yup';
import {
    Alert, AlertIcon, Box, Button, FormLabel, Input, Stack, Text
} from "@chakra-ui/react";
import {
    getBids, registerBid
} from "../../services/client.js";
import { errorNotification, successNotification } from "../../services/notification.js";
import React, { useEffect, useState } from "react";

const MyTextInput = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const AddBid = ({ initialValues, productId }) => {
    const [bids, setBids] = useState([]);

    useEffect(() => {
        fetchBids();
    }, [productId]);

    const fetchBids = () => {
        getBids(productId)
            .then(response => {
                setBids(response.data);
            })
            .catch(err => {
                console.error(err);
            });
    };

    return (
        <>
            <Formik
                initialValues={{
                    amount: '',
                    productId: productId,
                    currency: initialValues.currency,
                    userId: initialValues.sellerId
                }}
                validationSchema={Yup.object({
                    amount: Yup.number()
                        .min(bids.length > 0 ? bids[bids.length - 1].amount + 1 : initialValues.startingPrice, 'Must be greater than the latest bid amount + 1')
                        .required('Amount is required'),
                })}
                onSubmit={(bid, { setSubmitting, resetForm }) => {
                    setSubmitting(true);
                    registerBid(bid)
                        .then(res => {
                            successNotification("You added this bid with success!");
                            setBids([...bids, res.data]); // Update the bids list
                            resetForm(); // Reset form fields after successful submission
                        })
                        .catch(err => {
                            errorNotification(err.code, err.response.data.message);
                        })
                        .finally(() => {
                            setSubmitting(false);
                        });
                }}
            >
                {({ isValid, isSubmitting, dirty }) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Amount"
                                name="amount"
                                type="number"
                                placeholder={`Minimum bid amount: ${bids.length > 0 ? bids[bids.length - 1].amount + 1 : initialValues.startingPrice}`}
                            />
                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">
                                Submit
                            </Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
            <Text mt={4}><span style={{ fontWeight: 'bold' }}>Number of bids: {bids.length}</span></Text>
            {bids.length > 0 && bids.map((r, index) => (
                <Box key={r.id} padding="6" boxShadow="lg" bg="gray.100" mt={4}>
                    <Text><span style={{ fontWeight: 'bold' }}>Bid number:</span> {bids.length - index}</Text>
                    <Text><span style={{ fontWeight: 'bold' }}>Date:</span> {new Date(r.dateAndTime).toLocaleString()}</Text>
                    <Text><span style={{ fontWeight: 'bold' }}>Buyer Name:</span> {r.buyerName}</Text>
                    <Text><span style={{ fontWeight: 'bold' }}>Amount:</span> {r.amount}</Text>
                    <Text><span style={{ fontWeight: 'bold' }}>Currency:</span> {r.currency}</Text>
                </Box>
            ))}
        </>
    );
};

export default AddBid;
