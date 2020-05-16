package com.example.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FetchReputationUseCaseSyncTest {

    // region constants

    // endregion constants

    // region helper fields

    // endregion helper fields

    FetchReputationUseCaseSync SUT;
    @Mock
    FetchUserUseCaseSync fetchUserUseCaseSyncMock;
    private String UserId = "testId";
    private String FullName = "Tadese";
    private String ImageUrl = "";

    @Before
    public void setup() throws Exception {
        SUT = new FetchReputationUseCaseSync(fetchUserUseCaseSyncMock);
        FlowSuccessful();
    }

    @Test
    public void fetchReputationSync_success_successfulCompletionReturned() {
        //Arrange
        //Act
        FetchReputationUseCaseSync.FetchReputationResponse result = SUT.FetchReputation(UserId);
        //Assert
        Assert.assertThat(result.getFetchReputationStatus(), is(FetchReputationUseCaseSync.FetchReputationStatus.Success));
    }

    @Test
    public void fetchReputationSync_success_successfulCompletionAndReputationReturned() {
        //Arrange
        //Act
        FetchReputationUseCaseSync.FetchReputationResponse result = SUT.FetchReputation(UserId);
        //Assert
        Assert.assertThat(result.getFetchReputationStatus(), is(FetchReputationUseCaseSync.FetchReputationStatus.Success));
        Assert.assertThat(result.getReputation(), any(int.class));
    }

    @Test
    public void fetchReputationSync_serverFailure_failedAndFlowFailedReturned() {
        //Arrange
        FlowFailed();
        //Act
        FetchReputationUseCaseSync.FetchReputationResponse result = SUT.FetchReputation(UserId);
        //Assert
        Assert.assertThat(result.getFetchReputationStatus(), is(FetchReputationUseCaseSync.FetchReputationStatus.Failed));
    }

    @Test
    public void fetchReputationSync_serverFailure_failedWithReputationZeroReturned() {
        //Arrange
        FlowFailed();
        //Act
        FetchReputationUseCaseSync.FetchReputationResponse result = SUT.FetchReputation(UserId);
        //Assert
        Assert.assertThat(result.getFetchReputationStatus(), is(FetchReputationUseCaseSync.FetchReputationStatus.Failed));
        Assert.assertThat(result.getReputation(), is(0));
    }

    // region Helper method

    private void FlowFailed() {
        when(fetchUserUseCaseSyncMock.fetchUserSync(anyString())).
                thenReturn(new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.NETWORK_ERROR, null));
    }


    private void FlowSuccessful() {
        when(fetchUserUseCaseSyncMock.fetchUserSync(anyString())).
                thenReturn(new FetchUserUseCaseSync.UseCaseResult(FetchUserUseCaseSync.Status.SUCCESS,new User(UserId,FullName,ImageUrl)));
    }


    // endregion Helper method

}