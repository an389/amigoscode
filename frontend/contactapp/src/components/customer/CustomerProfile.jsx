import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Avatar, Box, Heading, Image, Text, VStack} from '@chakra-ui/react';
import {customerProfilePictureUrl, getCustomer} from "../../services/client";
import SidebarWithHeader from "../shared/SideBar";
import '../../index.css';

const CustomerProfile = () => {
    const {userName} = useParams();
    const [customer, setCustomer] = useState(null);
    const [loading, setLoading] = useState(true);
    console.log("user" + userName);
    useEffect(() => {
        getCustomer(userName)
            .then(response => {
                setCustomer(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            }).finally(() => {
            setLoading(false);
        });
    }, [userName]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!customer) {
        return <div>Product not found</div>;
    }

    return (
        <SidebarWithHeader>
            <Box padding="6" boxShadow="lg" bg="white">
                <VStack spacing={4}>
                    <Avatar
                        size={'xl'}
                        src={customerProfilePictureUrl(customer.id)}
                        alt={'Author'}
                        css={{
                            border: '2px solid white',
                        }}
                    />
                    <Heading as="h2" size="xl">{customer.name}</Heading>
                    <Text fontSize="lg"><span style={{fontWeight: 'bold'}}>Email:</span> {customer.email}</Text>
                    <Text fontSize="md"><span style={{fontWeight: 'bold'}}>Gender:</span> {customer.gender}</Text>
                    <Text fontSize="md"><span style={{fontWeight: 'bold'}}>Age:</span> {customer.age}</Text>
                    <Text fontSize="md"><span style={{fontWeight: 'bold'}}>User Name:</span> {customer.username}</Text>
                </VStack>
            </Box>
        </SidebarWithHeader>
    );
};

export default CustomerProfile;
