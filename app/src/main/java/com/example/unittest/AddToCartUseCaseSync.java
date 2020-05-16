package com.example.unittest;

public class AddToCartUseCaseSync {

    private AddToCartHttpEndpointSync addToCartHttpEndpointSyncMock;

    public enum AddToCartEndpointResult{
        Failure,
        Success
    }

    public AddToCartUseCaseSync(AddToCartHttpEndpointSync addToCartHttpEndpointSyncMock) {

        this.addToCartHttpEndpointSyncMock = addToCartHttpEndpointSyncMock;
    }

    public AddToCartEndpointResult addToCartUseCaseSync(String orderId, int amount) throws Exception {
        AddToCartHttpEndpointSync.EndpointResult result = addToCartHttpEndpointSyncMock.addToCartSync(new CartItemScheme(orderId, amount));
        AddToCartEndpointResult response = AddToCartEndpointResult.Success;
        switch (result){
            case SUCCESS:
                response =  AddToCartEndpointResult.Success;
                break;
            case AUTH_ERROR:
            case GENERAL_ERROR:
                response = AddToCartEndpointResult.Failure;
                break;
        }
        return response;
    }
}
