import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {Box, Heading, Image, Text, VStack} from '@chakra-ui/react';
import {customerProfilePictureUrl, getProduct, getRating, productPictureUrl} from "../../services/client";

const ProductDetail = () => {
    const {id} = useParams();
    const [product, setProduct] = useState(null);
    const [rating, setRating] = useState(null);
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

    useEffect(() => {
        getRating(id)
            .then(response => {
                setRating(response.data);
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
                    style={{fontWeight: 'bold'}}>Starting price:</span> {product.startingPrice} {product.currency}</Text>
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
                <Text fontSize="md"><span style={{fontWeight: 'bold'}}>We found: </span> {rating ? rating.length : 0} ratings</Text>
                {rating && rating.map((r, index) => (
                    <Box key={index} padding="6" boxShadow="lg" bg="gray.100" marginTop="4">
                        <Text><span style={{fontWeight: 'bold'}}>Rating nr:</span> {++index}</Text>
                        <Text><span style={{fontWeight: 'bold'}}>Date:</span> {new Date(r.dateAndTime).toLocaleString()}</Text>
                        <Text><span style={{fontWeight: 'bold'}}>Rating Customer Name: </span> {r.ratingCustomerName}</Text>
                        <Text><span style={{fontWeight: 'bold'}}>Grade: </span> {r.grade}</Text>
                        <Text><span style={{fontWeight: 'bold'}}>Comment:</span> {r.comment}</Text>
                    </Box>
                ))}
            </VStack>
        </Box>
    );
};

export default ProductDetail;
