package com.example.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class GetContactUseCaseSyncTest {


    // region constants
    public static final String FILTERING_STRING = "FilteringString";
    public static final String ID = "Id";
    public static final String FULL_NAME = "FullName";
    public static final String PHONE_NUMBER = "PhoneNumber";
    public static final String IMAGE_URL = "ImageUrl";
    public static final int AGE = 10;
    // endregion constants

    // region helper fields

    @Mock
    GetContactsHttpEndpoint getContactsHttpEndpointMock;
    GetContactUseCaseSync SUT;
    @Mock
    GetContactUseCaseSync.Listener Listener1;
    @Mock
    GetContactUseCaseSync.Listener Listener2;
    @Captor
    ArgumentCaptor<List<ContactSchema>> argumentCaptor;
    // endregion helper fields

    @Before
    public void setup() throws Exception {
        SUT = new GetContactUseCaseSync(getContactsHttpEndpointMock);
        success();
    }

    @Test
    public void contactUseCase_success_validFilteringStringPassedAndSuccessReturned() {
        //Arrange
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        //Act
        SUT.getContacts(FILTERING_STRING);
        //Assert
        verify(getContactsHttpEndpointMock).getContacts(argumentCaptor.capture(),any(GetContactsHttpEndpoint.Callback.class));
        Assert.assertThat(argumentCaptor.getValue(),is(FILTERING_STRING));
    }

    @Test
    public void contactUseCase_success_newListenersAreRegisteredAndNotifiedWithData() {
        //Arrange
        //Act
        SUT.registerListener(Listener1);
        SUT.registerListener(Listener2);
        SUT.getContacts(FILTERING_STRING);
        //Assert
        verify(Listener1).getContactsSucceeded(argumentCaptor.capture());
        verify(Listener2).getContactsSucceeded(argumentCaptor.capture());
        List<List<ContactSchema>> contacts = argumentCaptor.getAllValues();
        List<ContactSchema> capture1 = contacts.get(0);
        List<ContactSchema> capture2 = contacts.get(1);
        Assert.assertThat(capture1, is(getContactItems()));
        Assert.assertThat(capture2, is(getContactItems()));
    }

    private List<ContactSchema> getContactItems() {
        List<ContactSchema> contactSchemas = new ArrayList<>();
        contactSchemas.add(new ContactSchema(ID, FULL_NAME, PHONE_NUMBER, IMAGE_URL, AGE));
        return  contactSchemas;
    }

    @Test
    public void contactUseCase_networkError_registeredListenersNotifiedWithNetworkErrorResponse() {
        //Arrange
        ArgumentCaptor<GetContactsHttpEndpoint.FailReason> argumentCaptor  = ArgumentCaptor.forClass(GetContactsHttpEndpoint.FailReason.class);
        setUpNetworkError();
        //Act
        SUT.registerListener(Listener1);
        SUT.registerListener(Listener2);
        SUT.getContacts(FILTERING_STRING);
        //Assert
        verify(Listener1).onGetContactsFailed(argumentCaptor.capture());
        verify(Listener2).onGetContactsFailed(argumentCaptor.capture());
        List<GetContactsHttpEndpoint.FailReason> reasons = argumentCaptor.getAllValues();
        Assert.assertThat(reasons.get(0), is(GetContactsHttpEndpoint.FailReason.NETWORK_ERROR));
        Assert.assertThat(reasons.get(1), is(GetContactsHttpEndpoint.FailReason.NETWORK_ERROR));
    }

    @Test
    public void contactUseCase_generalError_registeredListenersNotifiedWithGeneralErrorResponse() {
        //Arrange
        ArgumentCaptor<GetContactsHttpEndpoint.FailReason> argumentCaptor  = ArgumentCaptor.forClass(GetContactsHttpEndpoint.FailReason.class);
        setUpGeneralError();
        //Act
        SUT.registerListener(Listener1);
        SUT.registerListener(Listener2);
        SUT.getContacts(FILTERING_STRING);
        //Assert
        verify(Listener1).onGetContactsFailed(argumentCaptor.capture());
        verify(Listener2).onGetContactsFailed(argumentCaptor.capture());
        List<GetContactsHttpEndpoint.FailReason> reasons = argumentCaptor.getAllValues();
        Assert.assertThat(reasons.get(0), is(GetContactsHttpEndpoint.FailReason.GENERAL_ERROR));
        Assert.assertThat(reasons.get(1), is(GetContactsHttpEndpoint.FailReason.GENERAL_ERROR));
    }

    // region helper method
    private void setUpGeneralError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                GetContactsHttpEndpoint.Callback callback =(GetContactsHttpEndpoint.Callback) invocation.getArgument(1);
                callback.onGetContactsFailed(GetContactsHttpEndpoint.FailReason.GENERAL_ERROR);
                return callback;
            }

        }).when(getContactsHttpEndpointMock).getContacts(anyString(),any(GetContactsHttpEndpoint.Callback.class));
    }
    private void setUpNetworkError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                GetContactsHttpEndpoint.Callback callback =(GetContactsHttpEndpoint.Callback) invocation.getArgument(1);
                callback.onGetContactsFailed(GetContactsHttpEndpoint.FailReason.NETWORK_ERROR);
                return callback;
            }

        }).when(getContactsHttpEndpointMock).getContacts(anyString(),any(GetContactsHttpEndpoint.Callback.class));
    }

    private void success() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                GetContactsHttpEndpoint.Callback callback =(GetContactsHttpEndpoint.Callback) invocation.getArgument(1);
                callback.onGetContactsSucceeded(getSuccessContactsList());
                return callback;
            }

            private List<ContactSchema> getSuccessContactsList() {
                List<ContactSchema> contactSchemas = new ArrayList<>();
                contactSchemas.add(new ContactSchema(ID, FULL_NAME, PHONE_NUMBER, IMAGE_URL, AGE));
                return contactSchemas;
            }

        }).when(getContactsHttpEndpointMock).getContacts(anyString(),any(GetContactsHttpEndpoint.Callback.class));
    }
    // endregion helper method

}