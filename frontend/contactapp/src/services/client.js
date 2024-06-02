import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL;

fetch(`${API_BASE_URL}/endpoint`, {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
    }
})
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));


const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getCustomers = async () => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/customers`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getCustomer = async (userName) => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/customers/${userName}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getCustomerSearch = async (searchParams) => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/customers/search?keyword=${searchParams}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getBids = async () => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/bids`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getProducts = async () => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/product`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getProductsSearch = async (searchParams) => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/product/search?keyword=${searchParams}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getProduct = async (id) => {
    try {
        return await axios.get(
            API_BASE_URL+`/api/v1/product/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const registerProduct = async (product) => {
    try {
        return await axios.post(
            API_BASE_URL+`/api/v1/product`,
            product,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const saveCustomer = async (customer) => {
    try {
        return await axios.post(
            API_BASE_URL+`/api/v1/customers`,
            customer
        )
    } catch (e) {
        throw e;
    }
}

export const updateCustomer = async (id, update) => {
    try {
        return await axios.put(
            API_BASE_URL+`/api/v1/customers/${id}`,
            update,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const deleteCustomer = async (id) => {
    try {
        return await axios.delete(
            API_BASE_URL+`/api/v1/customers/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    try {
        return await axios.post(
            API_BASE_URL+`/api/v1/auth/login`,
            usernameAndPassword
        )
    } catch (e) {
        console.log(e)

        throw e;
    }
}

export const uploadCustomerProfilePicture = async (id, formData) => {
    try {
        return axios.post(
            API_BASE_URL+`/api/v1/customers/${id}/profile-image`,
            formData,
            {
                ...getAuthConfig(),
                'Content-Type' : 'multipart/form-data'
            }
        );
    } catch (e) {
        throw e;
    }
}

export const customerProfilePictureUrl = (id) =>
    API_BASE_URL+`/api/v1/customers/${id}/profile-image`;


export const registerBid = async (bid) => {
    try {
        return await axios.post(
            API_BASE_URL+`/api/v1/bids`,
            bid,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}