package com.example.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FetchUserUseCaseSyncTdTest {

    // region constants

    // endregion constants

    // region helper fields
    FetchUserUseCaseSyncTd SUT;
    private String EmptyUserId;
    @Mock
    FetchUserUseCaseSync fetchUserUseCaseSync;
    private String wrongUserId;
    private String validUserId;
    // endregion helper fields

    @Before
    public void setup() throws Exception {
        SUT = new FetchUserUseCaseSyncTd(fetchUserUseCaseSync);
    }

    @Test
    public void fetchUser_emptyUserId_failureResponseReturned() throws Exception {
        //Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //Act
        EmptyUserId = "";
        FetchUserUseCaseSync.UseCaseResult useCaseResult = new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.FAILURE,null);
        when(fetchUserUseCaseSync.fetchUserSync(EmptyUserId)).thenReturn(useCaseResult);
        FetchUserUseCaseSyncTd.FetchUserStatus result = SUT.FetchUserUseCaseSync(EmptyUserId);
        verify(fetchUserUseCaseSync, Mockito.times(1)).fetchUserSync(argumentCaptor.capture());
        //Assert
        assertThat(result, is(FetchUserUseCaseSyncTd.FetchUserStatus.Failed));
        assertThat(argumentCaptor.getValue(),is(EmptyUserId));
        assertThat(useCaseResult.getUser(), nullValue());
        assertThat(useCaseResult.getStatus(), is(FetchUserUseCaseSync.Status.FAILURE));
    }

    @Test
    public void fetchUser_rongStringUserId_failureResponseReturned() throws Exception {
        //Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //Act
        wrongUserId = "WrongUserId";
        FetchUserUseCaseSync.UseCaseResult useCaseResult = new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.FAILURE,null);
        when(fetchUserUseCaseSync.fetchUserSync(wrongUserId)).thenReturn(useCaseResult);
        FetchUserUseCaseSyncTd.FetchUserStatus result = SUT.FetchUserUseCaseSync(wrongUserId);
        verify(fetchUserUseCaseSync, Mockito.times(1)).fetchUserSync(argumentCaptor.capture());
        //Assert
        assertThat(result, is(FetchUserUseCaseSyncTd.FetchUserStatus.Failed));
        assertThat(argumentCaptor.getValue(),is(wrongUserId));
        assertThat(useCaseResult.getUser(), nullValue());
        assertThat(useCaseResult.getStatus(), is(FetchUserUseCaseSync.Status.FAILURE));
    }

    @Test
    public void fetchUser_validStringUserId_successReturned() throws Exception {
        //Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //Act
        FetchUserUseCaseSync.UseCaseResult useCaseResult = new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.SUCCESS,new User(validUserId,"Tadese Teejay",""));
        validUserId = "ValidUserId";
        when(fetchUserUseCaseSync.fetchUserSync(validUserId)).thenReturn(useCaseResult);
        FetchUserUseCaseSyncTd.FetchUserStatus result = SUT.FetchUserUseCaseSync(validUserId);
        verify(fetchUserUseCaseSync, Mockito.times(1)).fetchUserSync(argumentCaptor.capture());
        //Assert
        assertThat(result, is(FetchUserUseCaseSyncTd.FetchUserStatus.Success));
        assertThat(argumentCaptor.getValue(),is(validUserId));
        assertThat(useCaseResult.getUser(), is(useCaseResult.getUser()));
        assertThat(useCaseResult.getStatus(), is(FetchUserUseCaseSync.Status.SUCCESS));
    }

    @Test
    public void fetchUser_networkError_returnedNetworkError() throws Exception {
        //Arrange
        //Act
        FetchUserUseCaseSync.UseCaseResult useCaseResult = new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.NETWORK_ERROR,null);
        validUserId = "ValidUserId";
        when(fetchUserUseCaseSync.fetchUserSync(validUserId)).thenReturn(useCaseResult);
        FetchUserUseCaseSyncTd.FetchUserStatus result = SUT.FetchUserUseCaseSync(validUserId);
        //Assert

        assertThat(result, is(FetchUserUseCaseSyncTd.FetchUserStatus.NetworkError));
    }
}