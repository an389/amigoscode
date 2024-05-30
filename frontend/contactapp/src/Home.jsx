import {
    Wrap,
    WrapItem,
    Spinner,
    Text
} from '@chakra-ui/react';
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import { useEffect, useState } from 'react';
import {getBids, getCustomers, getProducts} from "./services/client.js";
import CardWithImage from "./components/product/ProductCard";
import {errorNotification} from "./services/notification.js";
import CreateProductDrawer from "./components/product/CreateProductDrawer";

const Home = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchProducts = () => {
        setLoading(true);
        getProducts().then(res => {
            setProducts(res.data)
        }).catch(err => {
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false)
        })
    }

    useEffect(() => {
        fetchProducts();
    }, [])

    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        )
    }

    if (err) {
        return (
            <SidebarWithHeader>
                <CreateProductDrawer
                    fetchProducts={fetchProducts}
                />
                <Text mt={5}>Ooops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    if(products.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateProductDrawer
                    fetchProducts={fetchProducts}
                />
                <Text mt={5}>No products available</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <CreateProductDrawer
                fetchProducts={fetchProducts}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {products.map((product, index) => (
                    <WrapItem key={index}>
                        <CardWithImage
                            {...product}
                            imageNumber={index}
                            fetchProducts={fetchProducts}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}

export default Home;