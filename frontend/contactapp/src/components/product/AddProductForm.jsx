import {ErrorMessage, Field, Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {registerProduct} from "../../services/client.js";
import {successNotification, errorNotification} from "../../services/notification.js";
import {useAuth} from "../context/AuthContext";
import React from "react";
import { format } from 'date-fns';



const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};


// And now we can use these
const CreateProductForm = ({onSuccess}) => {
    const currencies = ['EUR', 'USD', 'RON', 'JPY', 'GBP', 'AUD', 'CAD'];
    const {customer} = useAuth();
    console.log("avem user " + customer.username)
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    description: '',
                    startingPrice: '',
                    currency: 'RON',
                    startDate: ''
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    description: Yup.string()
                        .min(20, 'Must be 20 characters or more')
                        .required('Required'),
                    startingPrice: Yup.number()
                        .min(1, 'Must be at least 1')
                        .max(10000, 'Must be less than 10000')
                        .required(),
                    currency: Yup.string()
                        .oneOf(currencies, 'Invalid currency' )
                        .required('Required'),
                    startDate: Yup.date()
                        .required('Start date is required')
                        .min(new Date(), 'Start date must be later than current date'),
                    endDate: Yup.date()
                    .required('End date is required')
                    .min(Yup.ref('startDate'), 'End date must be later than start date'),
                })}
                onSubmit={(product, {setSubmitting}) => {
                    setSubmitting(true);
                    const formatedProduct = {
                        ...product,
                        sellerEmail: customer.username,
                        startDate: format(new Date(product.startDate), 'yyyy-MM-dd HH:mm'),
                        endDate: format(new Date(product.endDate), 'yyyy-MM-dd HH:mm')};
                    registerProduct(formatedProduct)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Product saved",
                                `${product.name} was successfully saved`
                            )
                            onSuccess(res.headers["authorization"]);
                        }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Mouse"
                            />

                            <MyTextInput
                                label="Description"
                                name="description"
                                type="text"
                                placeholder="Give a good description1"
                            />

                            <MyTextInput
                                label="Starting Price"
                                name="startingPrice"
                                type="number"
                                placeholder="Greater than 1 and it can be decimal"
                            />

                            <MySelect label="Currency" name="currency">
                                <option value="">Select currency</option>
                                {currencies.map((curr) => (
                                    <option value={curr} key={curr}>{curr}</option>
                                ))}
                            </MySelect>

                            <div>
                                <label htmlFor="startDate">Start Date: </label>
                                <Field name="startDate" type="datetime-local"/>
                                <ErrorMessage name="startDate" component="div"/>
                            </div>
                            <div>
                                <label htmlFor="endDate">End Date: </label>
                                <Field name="endDate" type="datetime-local"/>
                                <ErrorMessage name="endDate" component="div"/>
                            </div>

                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default CreateProductForm;