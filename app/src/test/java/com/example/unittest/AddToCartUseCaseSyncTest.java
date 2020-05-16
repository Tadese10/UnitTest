package com.example.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTest {

    // region constants
    private static final String  EmptyOrderId = "";
    private static final String  OrderId = "OrderId";
    private static final int  ZeroAmount = 0;
    private static final int  Amount = 400;
    // endregion constants

    // region helper fields

    // endregion helper fields

    AddToCartUseCaseSync SUT;
    @Mock
    AddToCartHttpEndpointSync addToCartHttpEndpointSyncMock;

    @Before
    public void setup() throws Exception {
       SUT = new AddToCartUseCaseSync(addToCartHttpEndpointSyncMock);
    }

    @Test
    public void addToCart_emptyOrderId_failedResponseReturned() throws Exception {
        //Arrange
        //ArgumentCaptor<CartItemScheme> argumentCaptor = ArgumentCaptor.forClass(CartItemScheme.class);
        //Act
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR);
        AddToCartUseCaseSync.AddToCartEndpointResult result = SUT.addToCartUseCaseSync(EmptyOrderId, Amount);

        //Assert
        assertThat(result, is(AddToCartUseCaseSync.AddToCartEndpointResult.Failure));
    }

    @Test
    public void addToCart_zeroAmount_failureResponseReturned() throws Exception {
        //Arrange
        //Act
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR);
        AddToCartUseCaseSync.AddToCartEndpointResult result = SUT.addToCartUseCaseSync(OrderId, ZeroAmount);

        //Assert
        assertThat(result, is(AddToCartUseCaseSync.AddToCartEndpointResult.Failure));
    }

    @Test
    public void addToCart_correctParametersPassed_successReturned() throws Exception {
        //Arrange
        ArgumentCaptor<CartItemScheme> argumentCaptor = ArgumentCaptor.forClass(CartItemScheme.class);

        //Act
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(AddToCartHttpEndpointSync.EndpointResult.SUCCESS);
        AddToCartUseCaseSync.AddToCartEndpointResult result = SUT.addToCartUseCaseSync(OrderId, Amount);

        //Assert
        verify(addToCartHttpEndpointSyncMock, times(1)).addToCartSync(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getAmount(), is(Amount));
        assertThat(argumentCaptor.getValue().getOfferId(), is(OrderId));
        assertThat(result, is(AddToCartUseCaseSync.AddToCartEndpointResult.Success));
    }

    @Test
    public void addToCart_serverError_returnedFailedResponse() throws Exception {
        //Arrange
        //Act
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR);
        AddToCartUseCaseSync.AddToCartEndpointResult result = SUT.addToCartUseCaseSync(OrderId,Amount);
        //Assert
        assertThat(result, is(AddToCartUseCaseSync.AddToCartEndpointResult.Failure));
    }

    @Test
    public void addToCart_networkError_returnedFailedResponse() throws Exception {
        //Arrange
        //Act
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR);
        AddToCartUseCaseSync.AddToCartEndpointResult result = SUT.addToCartUseCaseSync(OrderId,Amount);
        //Assert
        assertThat(result, is(AddToCartUseCaseSync.AddToCartEndpointResult.Failure));
    }

}