import { ErrorMessage, Field, Form, Formik, useField } from 'formik';
import * as Yup from 'yup';
import { Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack, Image } from "@chakra-ui/react";
import { getCustomer, registerProduct, uploadCustomerProfilePicture } from "../../services/client.js";
import { successNotification, errorNotification } from "../../services/notification.js";
import { useAuth } from "../context/AuthContext";
import React, { useCallback, useState, useEffect } from "react";
import { format } from 'date-fns';
import { useDropzone } from "react-dropzone";

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

const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon />
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MyDropzone = ({ setSelectedFile, setPreview }) => {
    const onDrop = useCallback(acceptedFiles => {
        if (acceptedFiles.length === 0) return;
        const file = acceptedFiles[0];
        setSelectedFile(file);
        setPreview(URL.createObjectURL(file));
    }, [setSelectedFile, setPreview]);

    const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

    return (
        <Box {...getRootProps()}
             w={'100%'}
             textAlign={'center'}
             border={'dashed'}
             borderColor={'gray.200'}
             borderRadius={'3xl'}
             p={6}
             rounded={'md'}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the picture here ...</p> :
                    <p>Drag 'n' drop picture here, or click to select picture</p>
            }
        </Box>
    );
};

const CreateProductForm = ({ onSuccess }) => {
    const currencies = ['EUR', 'USD', 'RON', 'JPY', 'GBP', 'AUD', 'CAD'];
    const { customer } = useAuth();
    const [userId, setUserID] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const [preview, setPreview] = useState(null);

    useEffect(() => {
        getCustomer(customer.username).then(res => {
            setUserID(res.data.id);
        }).catch(err => {
            console.error(err);
        });
    }, [customer.username]);

    const handleSubmit = async (product, setSubmitting) => {
        setSubmitting(true);

        const formattedProduct = {
            ...product,
            sellerEmail: customer.username,
            startDate: format(new Date(product.startDate), 'yyyy-MM-dd HH:mm'),
            endDate: format(new Date(product.endDate), 'yyyy-MM-dd HH:mm')
        };

        try {
            const res = await registerProduct(formattedProduct);
            const productId = res.data;
            console.log("prodid:->", productId)
            if (typeof productId === 'number' && selectedFile) {
                const formData = new FormData();
                formData.append("file", selectedFile);

                try {
                    await uploadCustomerProfilePicture(productId, formData);
                    successNotification(
                        "Product saved",
                        `${product.name} was successfully saved`
                    );
                } catch (err) {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    );
                }
            } else {
                successNotification(
                    "Product saved",
                    `${product.name} was successfully saved`
                );
            }

            onSuccess(res.headers["authorization"]);
        } catch (err) {
            errorNotification(
                err.code,
                err.response.data.message
            );
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <>
            {preview && <Image src={preview} alt="Preview" boxSize={'250px'} />}
            <MyDropzone setSelectedFile={setSelectedFile} setPreview={setPreview} />
            <Formik
                initialValues={{
                    name: '',
                    description: '',
                    startingPrice: '',
                    currency: 'RON',
                    startDate: '',
                    endDate: ''
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
                        .oneOf(currencies, 'Invalid currency')
                        .required('Required'),
                    startDate: Yup.date()
                        .required('Start date is required')
                        .min(new Date(), 'Start date must be later than current date'),
                    endDate: Yup.date()
                        .required('End date is required')
                        .min(Yup.ref('startDate'), 'End date must be later than start date'),
                })}
                onSubmit={(product, { setSubmitting }) => handleSubmit(product, setSubmitting)}
            >
                {({ isValid, isSubmitting }) => (
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
                                placeholder="Give a good description"
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
                                <Field name="startDate" type="datetime-local" />
                                <ErrorMessage name="startDate" component="div" />
                            </div>
                            <div>
                                <label htmlFor="endDate">End Date: </label>
                                <Field name="endDate" type="datetime-local" />
                                <ErrorMessage name="endDate" component="div" />
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
