import {ErrorMessage, Field, Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack, Text, Wrap, WrapItem} from "@chakra-ui/react";
import {getCustomerSearch, getProducts, getProductsSearch, registerProduct} from "../../services/client.js";
import {successNotification, errorNotification} from "../../services/notification.js";
import {useAuth} from "../context/AuthContext";
import React, {useState} from "react";
import { format } from 'date-fns';
import Home from "../../Home";
import CardWithImage from "./CustomerCard";



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
const CreateProductForm = () => {
    const [results, setResult] = useState([]);
    return (
        <>
            <Formik
                initialValues={{
                    name: ''
                }}
                validationSchema={Yup.object({
                    name: Yup.string().required()
                })}
                onSubmit={(product, {setSubmitting}) => {
                    setSubmitting(true);
                    console.log("PRODUCTU "+product.name)
                    getCustomerSearch(product.name)
                        .then(res => {
                            console.log(res);
                            if (res) {
                                const count = res.data.length;
                                successNotification(`We found: ${count}`);
                                setResult(res.data);
                            }else {
                                successNotification(
                                    "No products found."
                                )
                            }
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
                            <Button disabled={!isValid || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
            {results.length > 0 && (
                <Wrap justify={"center"} spacing={"30px"}>
                    {results.map((product, index) => (
                        <WrapItem key={index}>
                            <CardWithImage
                                {...product}
                            />
                        </WrapItem>
                    ))}
                </Wrap>
            )}
        </>
    );
};

export default CreateProductForm;