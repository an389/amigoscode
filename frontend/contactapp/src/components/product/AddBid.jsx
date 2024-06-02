import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Image, Input, Stack, VStack} from "@chakra-ui/react";
import {
    customerProfilePictureUrl,
    registerBid,
    updateCustomer,
    uploadCustomerProfilePicture
} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import {useCallback} from "react";
import {useDropzone} from "react-dropzone";

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

const MyDropzone = ({customerId, fetchCustomers}) => {
    const onDrop = useCallback(acceptedFiles => {
        const formData = new FormData();
        formData.append("file", acceptedFiles[0])

        uploadCustomerProfilePicture(
            customerId,
            formData
        ).then(() => {
            successNotification("Success", "Profile picture uploaded")
            fetchCustomers()
        }).catch(() => {
            errorNotification("Error", "Profile picture failed upload")
        })
    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

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
    )
}

// And now we can use these
const AddBid = ({fetchCustomers, initialValues, productId}) => {
   console.log("customerid" + initialValues.sellerId)
    return (
        <>
            <Formik
                initialValues={{
                    amount: initialValues.amount,
                    productId: productId,
                    currency: initialValues.currency,
                    userId: initialValues.sellerId
                }}
                validationSchema={Yup.object({
                    amount: Yup.number()
                        .min(initialValues.startingPrice, 'Must be greater than: ' + initialValues.startingPrice)
                        .required(),
                })}
                onSubmit={(bid, {setSubmitting}) => {
                    setSubmitting(true);
                    console.log(bid);
                    registerBid(bid)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "You added this bid with success!",
                            )
                        }).catch(err => {
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Amount"
                                name="amount"
                                type="number"
                                placeholder={initialValues.amount}
                            />

                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default AddBid;