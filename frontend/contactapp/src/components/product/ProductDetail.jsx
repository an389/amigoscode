import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Box, Heading, Image, Text, VStack} from '@chakra-ui/react';
import {customerProfilePictureUrl, getProduct} from "../../services/client";

const ProductDetail = () => {
    const {id} = useParams();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getProduct(id)
            .then(response => {
                setProduct(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.log(err);
            }).finally(()=>{
                setLoading(false);
        });
    }, [id]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!product) {
        return <div>Product not found</div>;
    }

    return (
        <Box padding="6" boxShadow="lg" bg="white">
            <VStack spacing={4}>
                <Image
                    boxSize={'150px'}
                    objectFit={'cover'}
                    alt={"Product"}
                    src={customerProfilePictureUrl(product.id)}
                />
                <Heading as="h2" size="xl">{product.name}</Heading>
                <Text fontSize="lg"><span style={{fontWeight: 'bold'}}>Description:</span> {product.description}</Text>
                <Text fontSize="md"><span
                    style={{fontWeight: 'bold'}}>Price:</span> {product.startingPrice} {product.currency}</Text>
                <Text fontSize="md"><span style={{fontWeight: 'bold'}}>Seller:</span> {product.seller.name}</Text>
                <Text fontSize="md"><span style={{fontWeight: 'bold'}}>Email:</span> {product.seller.email}</Text>
                <Text fontSize="md"><span
                    style={{fontWeight: 'bold'}}>Created on:</span> {new Date(product.creationDate).toLocaleString()}
                </Text>
                <Text fontSize="md"><span
                    style={{fontWeight: 'bold'}}>Auction starts:</span> {new Date(product.startDate).toLocaleString()}
                </Text>
                <Text fontSize="md"><span
                    style={{fontWeight: 'bold'}}>Auction ends:</span> {new Date(product.endDate).toLocaleString()}
                </Text>
            </VStack>
        </Box>
    );
};

export default ProductDetail;
